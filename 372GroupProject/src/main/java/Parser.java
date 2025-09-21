import java.util.*;
import java.io.*;

public class Parser {
	public static Order parseOrder(String file, int orderId) {
		String type = "";
		long date = 0;
		List<FoodItem> foodItems = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			
	}catch (IOException e) {
		e.printStackTrace();
		System.out.println("Error: " + e.getMessage());
	}
		
        return new Order(orderId, type, date, foodItems);
	}
}
		
