package com.foo.mywebapp.car;

public class Car {
    private Integer id;
    private String model;
    private String make;
    private String preview;
    private String description;
    private Integer price;
    
    
	public Car(Integer id, String model, String make, String description, String preview, Integer price) {
		super();
		this.id = id;
		this.model = model;
		this.make = make;
		this.description = description;
		this.preview = preview;
		this.price = price;
	}
	
	public Car() {
		this.id = null;
		this.model = null;
		this.make = null;
		this.description = null;
		this.preview = null;
		this.price = 0;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
    
    
    
}