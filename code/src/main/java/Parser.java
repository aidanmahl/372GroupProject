package main.java;

import org.json.simple.JSONArray;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Parser {
	public static Order parseJSONOrder(File file) throws IOException, ParseException {
        long orderDate;
        String orderType;
        List<FoodItem> foodItemList = new ArrayList<>();

        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(file));
        JSONObject orderJson = (JSONObject) jsonObject.get("order");
        orderDate = (long) orderJson.get("order_date");
        orderType = (String) orderJson.get("type");
        JSONArray itemArray = (JSONArray) orderJson.get("items");
        for(int i=0; i< itemArray.size(); i++){
            int quantity =  (int) (long) ((JSONObject) itemArray.get(i)).get("quantity");
            double price = (double) ((JSONObject) itemArray.get(i)).get("price");
            String name = (String) ((JSONObject) itemArray.get(i)).get("name");
            foodItemList.add(new FoodItem(name,quantity,price));

        }

        Random r = new Random();
        return new Order(r.nextInt(100),orderType,orderDate,foodItemList);
    }


    /**
     * Main test method for parser class
     */
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("code/src/main/java/Resources/order_09-16-2025_10-00.json");
        Order myOrder = Parser.parseJSONOrder(file);
        System.out.println(myOrder);
    }
}
		
