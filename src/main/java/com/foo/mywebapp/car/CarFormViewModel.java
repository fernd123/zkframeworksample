package com.foo.mywebapp.car;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.foo.mywebapp.service.CarService;
import com.foo.mywebapp.service.CarServiceImpl;

public class CarFormViewModel extends CarForm {
	private CarService carService = new CarServiceImpl();

	@Init // @Init annotates a initial method
	public void init(){
		//get data from service and wrap it to model for the view
		Integer carId = (Integer) Sessions.getCurrent().getAttribute("editCarId");
		if (carId != null)
			setCar(carService.getCarById(carId));
	}

	@Command
	public void submit() {
		String message;
		if (this.getCar() != null && this.getCar().getId() != null) {
			carService.updateCar(this.getCar());
			message = "Car updated succesfully";
		}else {
			carService.addCar(this.getCar());
			message = "Car added succesfully";
		}
		Messagebox.show(message, "Information", null ,Messagebox.INFORMATION,
				new org.zkoss.zk.ui.event.EventListener<ClickEvent>(){
			public void onEvent(ClickEvent e){
				switch (e.getButton()) {
				case OK: //OK is clicked
					Executions.sendRedirect("/search.zul");
					break;
				default: //if the Close button is clicked, e.getButton() returns null
				}
			}
		});
	}

}
