package com.foo.mywebapp.service;

import java.util.List;

import com.foo.mywebapp.car.Car;

public interface CarService {
	 
    /**
     * Retrieve all cars in the catalog.
     * @return all cars
     */
    public List<Car> findAll();
     
    /**
     * search cars according to keyword in  model and make.
     * @param keyword for search
     * @return list of car that match the keyword
     */
    public List<Car> search(String keyword);
    
    public Car addCar(Car car);

	public Car updateCar(Car car);

	public Car getCarById(Integer carId);

	public boolean deleteCar(int id);
    
}