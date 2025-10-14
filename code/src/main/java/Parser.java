package main.java;

import org.json.simple.JSONArray;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Parser adapter class that reads a JSON file and creates a corresponding Order object.
 * Populates the Order with data from the JSON file.
 * Created for ICS 372-01
 * @author Joseph Murtha hw4546dw
 */
public class Parser {
    private static int nextOrderNumber = 1;
    /**
     * Parser method creates order object with data populated from given JSON file.
     * Generates a random orderID for the Order.
     *
     * @param file              JSON file to be read
     * @return                  Order object populated with data from the JSON file
     * @throws IOException      if the file cannot be read
     * @throws ParseException   if the JSON file is invalid or incorrectly formatted
     */
	public static Order parseJSONOrder(File file) throws IOException, ParseException {
        long orderDate;
        String orderType;
        List<FoodItem> foodItemList = new ArrayList<>();

        JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(file));
        JSONObject orderJson = (JSONObject) jsonObject.get("order");
        orderDate = (long) orderJson.get("order_date");
        orderType = (String) orderJson.get("type");
        JSONArray itemArray = (JSONArray) orderJson.get("items");
        for (Object o : itemArray) {
            int quantity = (int) (long) ((JSONObject) o).get("quantity");
            double price = (double) ((JSONObject) o).get("price");
            String name = (String) ((JSONObject) o).get("name");
            foodItemList.add(new FoodItem(name, quantity, price));

        }
        return new Order(getNextOrderNumber(),orderType,orderDate,foodItemList);
    }

    /**
     * Static helper method
     * returns next order number and increments the counter
     * @return int, next Order ID number
     */
    private static int getNextOrderNumber(){
        return nextOrderNumber++;
    }
    /**
     * Main test method for the Parser class.
     * Uses a hardcoded JSON file to test the parser method.
     * Prints to console.
     */
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("code/src/main/java/Resources/order_09-16-2025_10-00.json");
        Order myOrder = Parser.parseJSONOrder(file);
        System.out.println(myOrder);
    }
}
