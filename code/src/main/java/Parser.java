package main.java;

import java.util.*;
import java.io.*;

public class Parser {
	public static Order parseJSONOrder(String file, int orderId) {
		String type = "";
		long date = 0;
		List<FoodItem> foodItems = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			
	}catch (IOException e) {
		e.printStackTrace();
		System.out.println("Error: " + e.getMessage());
	}
		// Just so I won't get an error
        return new Order(orderId, type, date, foodItems);
	}


    /**
     * Main test method for parser class
     */
    public static void main(String[] args){
        Order myOrder = Parser.parseJSONOrder("code/src/main/java/Resources/order_09-16-2025_10-00.json", 12);
        System.out.println(myOrder);
    }
}
		
