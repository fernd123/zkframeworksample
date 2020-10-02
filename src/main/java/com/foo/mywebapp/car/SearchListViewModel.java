package com.foo.mywebapp.car;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

import com.foo.mywebapp.db.DBApi;
import com.foo.mywebapp.service.CarService;
import com.foo.mywebapp.service.CarServiceImpl;

public class SearchListViewModel {

	private DBApi dbApi  = new DBApi();
	private CarService carService = new CarServiceImpl();
	private List<Car> carList = new ArrayList<Car>();
	private Car selectedCar;
	private String keyword;

	@Init // @Init annotates a initial method
	public void init(){
		System.out.println("searchlist cargado..");
		try {
			carList = dbApi.getCars();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Car> getCarList(){
		return carList;
	}
	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}
	public Car getSelectedCar() {
		return selectedCar;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeyword() {
		return keyword;
	}

	@Command
	@NotifyChange({"carList", "selectedCar"})
	public void search() {
		this.carList = this.carService.search(keyword);
		this.selectedCar = null;
	}


	@Command
	public void edit(@BindingParam("id") Integer id) {
		System.out.println("edit id: "+id);
		Sessions.getCurrent().setAttribute("editCarId", id);
		Executions.sendRedirect("/addcar.zul");
	}

	@SuppressWarnings({ "unchecked" })
	//@NotifyChange({"carList", "selectedCar"})
	@Command
	public void delete(@BindingParam("id") Integer id) {
		System.out.println("delete id: "+id);
		String message = "Are you sure to delete the selected record?";
		EventListenerMsg eventlmsg = new EventListenerMsg();
		eventlmsg.setId(id);

		Messagebox.show(message, 
				"Question", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION,
				eventlmsg
				);
	}

	@SuppressWarnings("rawtypes")
	private class EventListenerMsg implements org.zkoss.zk.ui.event.EventListener{
		
		private int id;
		public void setId(int id) {
			this.id = id;
		}
		
		public void onEvent(Event e){
			if(Messagebox.ON_OK.equals(e.getName())){
				try {
					if(dbApi.deleteCar(id)) {				
						carList = dbApi.getCars();
						selectedCar = null;

						if(carList.size() == 1) {
							selectedCar = carList.get(0);
						}
						BindUtils.postNotifyChange(null, null, SearchListViewModel.this, "carList");
						BindUtils.postNotifyChange(null, null, SearchListViewModel.this, "selectedCar");

					}
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(Messagebox.ON_CANCEL.equals(e.getName())){
				//Cancel is clicked
			}
		}
	}


}
