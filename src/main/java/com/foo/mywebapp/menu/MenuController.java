package com.foo.mywebapp.menu;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

public class MenuController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);

	}
	
	@Listen("onClick = #searchList")
	public void searchList(){
		removeAttributes();
		Executions.sendRedirect("/search.zul");
	}
	
	@Listen("onClick = #addCar")
	public void addCar(){
		removeAttributes();
		Executions.sendRedirect("/addcar.zul");
	}
	
	@Listen("onClick = #exit")
	public void exit(){
		removeAttributes();
		Executions.sendRedirect("/index.zul");
	}
	
	private void removeAttributes() {
		Sessions.getCurrent().removeAttribute("editCarId");
	}
	
	

}
