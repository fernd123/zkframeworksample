package com.foo.mywebapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.foo.mywebapp.car.Car;


public class DBApi {

	private static Connection getConnection() throws NamingException {
		//DataSource ds = (DataSource)new InitialContext().lookup("java:comp/env/jdbc/Mywebapp");
		String dbConection = "jdbc:mysql://localhost:3306/mywebapp?user=root&password=root1234&autoReconnect=true&useSSL=false";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbConection);
//			conn = ds.getConnection();
		} catch (SQLException ex) {

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public boolean insertCar(Car car) throws Exception {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		int rows = 0;

		try {
			stmt = conn.prepareStatement("INSERT INTO car (id, model, make, description, price, preview) values(default, ?, ?, ?, ?, ?)");

			//insert what end user entered into database table
			stmt.setString(1, car.getModel());
			stmt.setString(2, car.getMake());
			stmt.setString(3, car.getDescription());
			stmt.setInt(4, car.getPrice());
			stmt.setString(5, car.getPreview());

			//execute the statement
			rows = stmt.executeUpdate();
			//conn.commit();
			stmt.close(); 
			stmt = null;
		}catch(SQLException ex) {
			System.out.println("abc");
		}
		return rows > 0;
	}
	
	public boolean updateCar(Car car) throws NamingException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		int rows = 0;

		try {
			stmt = conn.prepareStatement("UPDATE mywebapp.car SET model = ?, make = ?, description = ?, price = ?, preview = ? WHERE (id = ?)"); 

			//insert what end user entered into database table
			stmt.setString(1, car.getModel());
			stmt.setString(2, car.getMake());
			stmt.setString(3, car.getDescription());
			stmt.setInt(4, car.getPrice());
			stmt.setString(5, car.getPreview());
			stmt.setInt(6, car.getId());

			//execute the statement
			rows = stmt.executeUpdate();
			//conn.commit();
			stmt.close(); 
			stmt = null;
		}catch(SQLException ex) {
			System.out.println("abc");
		}
		return rows > 0;
		
	}

	public List<Car> getCars() throws NamingException {
		List<Car> carList = new ArrayList<Car>();
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("Select * from car");


			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String model = resultSet.getString("model");
				String make = resultSet.getString("make");
				String description = resultSet.getString("description");
				int price = resultSet.getInt("price");
				String preview = resultSet.getString("preview");

				Car car = new Car(id, model, make, description, preview, price);
				carList.add(car);
			}

			stmt.close(); 
			stmt = null;
			//optional because the finally clause will close it
			//However, it is a good habit to close it as soon as done, especially 
			//you might have to create a lot of statement to complete a job
		}catch(SQLException ex) {
			System.out.println("abc");
		}
		return carList;
	}
	
	public boolean deleteCar(int id) throws NamingException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("delete from car where id = ?");

			//insert what end user entered into database table
			stmt.setInt(1, id);
			//execute the statement
			stmt.execute();
			//conn.commit();
			stmt.close(); 
			stmt = null;
			
		}catch(SQLException ex) {
			System.out.println("abc");
			return false;
		}
		return true;
	}

	public Car getCarById(int carId) throws NamingException {
		Car car = null;
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("Select * from car where id = ?");
			stmt.setInt(1, carId);

			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String model = resultSet.getString("model");
				String make = resultSet.getString("make");
				String description = resultSet.getString("description");
				int price = resultSet.getInt("price");
				String preview = resultSet.getString("preview");

				car = new Car(id, model, make, description, preview, price);
			}

			stmt.close(); 
			stmt = null;
			//optional because the finally clause will close it
			//However, it is a good habit to close it as soon as done, especially 
			//you might have to create a lot of statement to complete a job
		}catch(SQLException ex) {
			System.out.println("abc");
		}
		return car;
	}

	
}
