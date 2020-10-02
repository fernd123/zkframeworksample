package com.foo.mywebapp.service;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import com.foo.mywebapp.car.Car;
import com.foo.mywebapp.db.DBApi;

public class CarServiceImpl implements CarService{

	//data model
	private static List<Car> carList= new LinkedList<Car>();
	private DBApi dbApi = new DBApi();
	//initialize book data
	public CarServiceImpl() {
		findAll();
	}
	
	public Car addCar(Car car) {
		try {
			dbApi.insertCar(car);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return car;
	}
	
	
	@Override
	public Car updateCar(Car car) {
		try {
			dbApi.updateCar(car);
		}catch(Exception e) {
			
		}
		return car;
	}

	public Car getCarById(Integer id) {
		Car car = null;
		try {
			car = dbApi.getCarById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return car;
	}
	
	@Override
	public boolean deleteCar(int id) {
		boolean deleted = false;
		try {
			deleted = dbApi.deleteCar(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deleted;
	}
	
	public List<Car> findAll(){
		try {
			carList = dbApi.getCars();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return carList;
	}
	
	public List<Car> search(String keyword){
		List<Car> result = new LinkedList<Car>();
		if (keyword==null || "".equals(keyword)){
			result = carList;
		}else{
			for (Car c: carList){
				if (c.getModel().toLowerCase().contains(keyword.toLowerCase())
					||c.getMake().toLowerCase().contains(keyword.toLowerCase())){
					result.add(c);
				}
			}
		}
		return result;
	}

}