package main.java;

import org.json.simple.JSONArray;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	public static Order parseJSONOrder(File file, int orderId) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("code/src/main/java/Resources/order_09-16-2025_10-00.json"));
        List<FoodItem> myList= new ArrayList<>();
        Order order = new Order(12,"hello",123123123123123l,myList);

        JSONObject orderJson = (JSONObject) jsonObject.get("order");
        String orderIdS = (String) orderJson.get("orderId");


        return order;
    }


    /**
     * Main test method for parser class
     */
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("code/src/main/java/Resources/order_09-16-2025_10-00.json");
        Order myOrder = Parser.parseJSONOrder(file, 12);
        System.out.println(myOrder);
    }
}
		
