package main.java;

import org.json.simple.JSONArray;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Parser adapter class that takes a JSON file input and creates a new Order.java object. This object is populated with data from JSON file.
 * Created for ICS 372-01
 * @author Joseph Murtha hw4546dw
 */
public class Parser {

    /**
     * Parser method creates order object with data populated from given JSON file. Creates a RANDOM orderID when created.
     *
     * @param file JSON file to be read
     * @return  Order object correctly populated
     * @throws IOException File not found
     * @throws ParseException JSON simple could not properly parse the file. Possibly not JSON file or incorrectly formatted.
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
     * The exportOrdersToJSON will create A JSONArray of the completedOrders list
     * and put them in a file in the directory code/src/main/java/export
     *
     * note: export is not pretty to do that we need libraries GSON or Jackson
     * @param filename
     * @param orderDriver
     * @return
     */
    public static boolean exportOrdersToJSON(String filename, OrderDriver orderDriver) {
        boolean exportSuccess;

        JSONArray ordersArray = new JSONArray();
        for (Order order : orderDriver.getCompleteOrders()) {
            JSONObject ordersJSON = new JSONObject();
            ordersJSON.put("orderID", order.getOrderID());
            ordersJSON.put("date", order.getDate());
            ordersJSON.put("type", order.getType());
            ordersJSON.put("completeTime", System.currentTimeMillis()); //should we add a complete time attribute to orders? - Rocky

            JSONArray orderFoodsList = new JSONArray();
            for (FoodItem food : order.getFoodList()){
                JSONObject foodJSON = new JSONObject();
                foodJSON.put("name", food.getName());
                foodJSON.put("quantity", food.getQuantity());
                foodJSON.put("price", food.getPrice());
                orderFoodsList.add(foodJSON);
            }

            ordersJSON.put("foodList", orderFoodsList);

            ordersArray.add(ordersJSON);
        }

        //The literal file path
        String fileDirectory = "code/src/main/java/Export";
        String filePath = fileDirectory + "/" + filename;

        //checks if directory exists and if it successfully created the directory
        File fileDir = new File(fileDirectory);
        if (!fileDir.exists()) {
            boolean created = fileDir.mkdir();
            if (!created) {
                System.out.println("Error creating directory: " + fileDirectory);
            } else {
                System.out.println("Directory created: " + fileDirectory);
            }
        }

        //write ordersArray to a file
        try(FileWriter fw = new FileWriter(filePath)) {
            fw.write(ordersArray.toJSONString());
            fw.flush();
            exportSuccess = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return exportSuccess; //to be implemented
    }

    /**
     * Main test method for parser class
     * Uses hardcoded JSON file to check parser method.
     * prints to console
     */
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("code/src/main/java/Resources/order_09-16-2025_10-00.json");
        Order myOrder = Parser.parseJSONOrder(file);
        System.out.println(myOrder);
    }
}
		
