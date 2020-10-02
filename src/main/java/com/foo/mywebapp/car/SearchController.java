package com.foo.mywebapp.car;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.foo.mywebapp.service.CarService;
import com.foo.mywebapp.service.CarServiceImpl;

public class SearchController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	private Textbox keywordBox; // el nombre igual que el ID del HTML
	@Wire
	private Listbox carListbox;
	@Wire
	private Label modelLabel;
	@Wire
	private Label makeLabel;
	@Wire
	private Label priceLabel;
	@Wire
	private Label descriptionLabel;
	@Wire
	private Image previewImage;

	private CarService carService = new CarServiceImpl();
	private Listitem selectedCar = null;
	//private DBApi dbApi = new DBApi();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		List<Car> carModel = carService.findAll();
		carListbox.setModel(new ListModelList<Car>(carModel));
		//carListbox.setSelectedIndex(0);
	}

	@Listen("onClick = #searchButton")
	public void search(){
		String keyword = keywordBox.getValue();
		List<Car> carModel = carService.search(keyword);
		carListbox.setModel(new ListModelList<Car>(carModel));
		if(carModel.size() == 0) {
			carListbox.setSelectedItem(null);
			this.showDetail();
		}else if(selectedCar != null && carModel.contains(selectedCar.getValue())) {
			carListbox.setSelectedIndex(carModel.indexOf(selectedCar.getValue()));
		}
	}

	@Listen("onSelect = #carListbox")
	public void showDetail(){
		selectedCar = carListbox.getSelectedItem();
		Car selected = selectedCar != null ? selectedCar.getValue() : null;
		previewImage.setSrc(selected != null ? selected.getPreview(): "");
		modelLabel.setValue(selected != null ?selected.getModel() : "");
		makeLabel.setValue(selected != null ?selected.getMake(): "");
		priceLabel.setValue(selected != null ?selected.getPrice().toString(): "");
		descriptionLabel.setValue(selected != null ? selected.getDescription(): "");
	}

	@Listen("onDelete=#carListbox")
	public void delete(ForwardEvent evt){
		selectedCar = carListbox.getSelectedItem();
		int id = Integer.parseInt(evt.getOrigin().getTarget().getId().replace("delete", ""));
		if(carService.deleteCar(id)) {				
			List<Car> carModel = carService.findAll();
			carListbox.setModel(new ListModelList<Car>(carModel));
			carListbox.setSelectedItem(null);
			selectedCar = null;
		}
	}

	@Listen("onEdit=#carListbox")
	public void edit(ForwardEvent evt){
		selectedCar = carListbox.getSelectedItem();
		int id = Integer.parseInt(evt.getOrigin().getTarget().getId().replace("edit", ""));
		Sessions.getCurrent().setAttribute("editCarId", id);
		Executions.sendRedirect("/addcar.zul");
	}
}