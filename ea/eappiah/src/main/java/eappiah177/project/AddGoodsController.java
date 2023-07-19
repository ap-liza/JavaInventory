package eappiah177.project;

import com.mysql.jdbc.Driver;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AddGoodsController implements Initializable {
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private ChoiceBox<String> goodsNameChoiceBox;
    @FXML
    private Button addButton;
    @FXML
    private TextField goodnameTextField;
    @FXML
    private TextField sellingpriceTextField;
    @FXML
    private TextField buyingpriceTextField;
    @FXML
    private TextField quantityTextField,totalSellingPriceTextView,totalBuyingPriceTextField,overallQuantityTextField;
    @FXML
    private Label messageLabel;
    @FXML
    private TableView<Goodsdata> addgoodsTableView;
    @FXML
    private TableColumn<Goods, Integer> idTableColumn;
    @FXML
    private TableColumn<Goods,String> goodnameTableColumn;
    @FXML
    private TableColumn<Goods,String> categoryTableColumn;
    @FXML
    private TableColumn<Goods,Integer> sellingpriceTableColumn;
    @FXML
    private TableColumn<Goods, Integer> buyingpriceTableColumn;
    @FXML
    private TableColumn<Goods,Integer> quantityTableColumn;
    @FXML
    private TableColumn<Goods, Date> dateTableColumn;

    private Stack<Goodsdata> beveragesStore;
    private Stack<Goodsdata> breadBakeryStore;
    private Stack<Goodsdata> cannedJarredStore;
    private Stack<Goodsdata> diaryProductStore;
    private Stack<Goodsdata> meatStore;
//    private Stack<Goodsdata> beveragesStore;
//    private Stack<Goodsdata> beveragesStore;
//    private Stack<Goodsdata> beveragesStore;
//    private Stack<Goodsdata> beveragesStore;
    int totalGoods = 0;
    ObservableList<Goodsdata> goodsObservableList = FXCollections.observableArrayList();

    String[] beverageChoices = {"coffee/tea", "juice","soda"};
    String[] bakeryChoices = {"sandwich loaves", "dinner rolls", "tortillas", "bagels"};
    String[] cannedChoices = {"vegetables", "spaghetti sauce", "ketchup"};
    String[] dairyChoices = {"cheeses", "eggs", "milk", "yoghurt","butter"};
    String[] bakingChoices = {"cereals", "flour", "sugar", "pasta", "mixes"};
    String[] Meat = {"lunch meat", "poultry", "beef", "pork"};
    String[] categoryChoices = {"Beverages","Bread/Bakery","Canned/Jarred Goods","Dairy Products"
            ,"Dry/Baking Goods","Frozen Products","Meat","Farm Produce","Home Cleaners",
            "Paper Goods","Home Care"};

    public void addButtonOnAction(ActionEvent event){
        String category = categoryChoiceBox.getValue();
        String name = goodsNameChoiceBox.getValue();
        double sprice = Double.parseDouble(sellingpriceTextField.getText());
        double bprice = Double.parseDouble(buyingpriceTextField.getText());
        int qty =Integer.parseInt(quantityTextField.getText());
        Date date = new Date(System.currentTimeMillis());
        System.out.println(category + name + sprice + bprice + qty);
        Connection conn = null;
        Statement statement = null;
        ArrayStack<String> arrayStack = new ArrayStack<>();
        try{
            try {
                Class.forName("com.mysql.jdbc.Driver");

            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
                e.getMessage();
            }
            conn =(Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/demo_db","root","hunberry143");
            System.out.println("Connected to database");
            statement = (Statement) conn.createStatement();
            String query1 = "insert product_table (product_name,product_category,selling_price,buying_price,quantity,date) values ("
                    +"'" + goodnameTextField.getText() + "'" + ","+ "'"+ categoryChoiceBox.getValue().toString() + "'" + ","+Double.parseDouble(sellingpriceTextField.getText()) +","
                    +Double.parseDouble(buyingpriceTextField.getText()) +","+Integer.parseInt(quantityTextField.getText())+ "," + "curtime())";
            statement.executeUpdate(query1);
            insertProductIntoTable(new Goodsdata(bprice,sprice,category,name,qty,totalGoods+1,date));
            totalGoods++;
            categoryChoiceBox.setValue("");
            goodsNameChoiceBox.setValue("");
            sellingpriceTextField.setText(null);
            buyingpriceTextField.setText(null);
            quantityTextField.setText(null);

            messageLabel.setText("Product Added Successfully");
            messageLabel.setTextFill(Color.GREEN);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
            e.getMessage();
        }
        obtainGrandSellingPrice();
        obtainGrandBuyingPrice();
        obtainGrandQuantiy();
    }

    public void retrieveButtonOnAction(ActionEvent event){
        messageLabel.setText("retrieved");

    }

    public  void onChange(){

    }
    public void categoryChoiceBoxOnAction(ActionEvent event){
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener)(ov,old,newval)->{

                }
        );
        //System.out.println(option);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoiceBox.getItems().addAll(categoryChoices);
        goodsNameChoiceBox.getItems().addAll(beverageChoices);
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener(

                (ChangeListener)(ov,old,newval)->{
                    if (newval == "Bread/Bakery"){
                        goodsNameChoiceBox.getItems().addAll(bakeryChoices);
                    } else if (newval == "Canned/Jarred Goods") {
                        goodsNameChoiceBox.getItems().addAll(cannedChoices);
                    }
                }
        );
        populatingTable("Beverages");
        obtainGrandSellingPrice();
        obtainGrandBuyingPrice();
        obtainGrandQuantiy();
    }

    private void populatingTable(String category) {
        String categoryLowerCase = category.toLowerCase();
        boolean fetchDataFromDB = false;
       try {
           switch (categoryLowerCase){
               // Stack stores
               case  "beverages":
                   fetchDataFromDB = beveragesStore.size()==0;
                   break;
               case "bread/bakery":
                   fetchDataFromDB = breadBakeryStore.size()==0;
                   break;
               case "canned/jarred goods":
                   fetchDataFromDB = cannedJarredStore.size()==0;
                   break;
               case "dairy products":
                   fetchDataFromDB = diaryProductStore.size()==0;
                   break;
               // Queue stores

           }
       }catch (Exception e){}
        //Let's clear the table first
        addgoodsTableView.getItems().clear();
        // We have not fetched this category's data from the DB
        // So we fetch for the first time and store in our stores
        if(Boolean.compare(fetchDataFromDB,true)==-1){

            try{
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                String productQuery = "select product_id,product_name,product_category,selling_price,buying_price,quantity,date from product_table where "
                        +"(product_category = '"+category+"')";
                Statement statement = connectDB.createStatement();
                ResultSet rs = statement.executeQuery(productQuery);
                Goodsdata goods;
                try {
                    while(rs.next()){
                        totalGoods++;
                        System.out.println("K K K 3");
                        goods = new Goodsdata();
                        System.out.println("K K K 4");
                        goods.buyingPrice = rs.getDouble("buying_price");
                        goods.sellingPrice = rs.getDouble("selling_price");
                        goods.categoryName = rs.getString("category_name");
                        goods.productName = rs.getString("product_name");
                        goods.quantity = rs.getInt("quantity");
                        goods.id = rs.getInt("product_id");
                        goods.date = rs.getDate("date");
                        System.out.println("K K K 2");
                        switch (categoryLowerCase){
                            // Stack stores
                            case  "beverages":
                               beveragesStore.push(goods);
                                break;
                            case "bread/bakery":
                                breadBakeryStore.push(goods);
                                break;
                            case "canned/jarred goods":
                                cannedJarredStore.push(goods);
                                break;
                            case "dairy products":
                                diaryProductStore.push(goods);
                                break;
                            // Queue stores
                        }
                        System.out.println("K K K 1");
                        insertProductIntoTable(goods);
                    }

                }catch (Exception e){
                    System.out.println("No no no Error");

                }
//                while (queryOutput.next()){
//
//                    totalGoods++;
//                    Integer queryProductID = queryOutput.getInt("product_id");
//                    String queryProductName = queryOutput.getString("product_name");
//                    String queryProductCategory = queryOutput.getString("product_category");
//                    Integer queryProductSellingPrice = queryOutput.getInt("selling_price");
//                    Integer queryProductBuyingPrice = queryOutput.getInt("buying_price");
//                    Integer queryProductQuantity = queryOutput.getInt("quantity");
//                    Date queryProductDate = queryOutput.getDate("date");
//                    goodsObservableList.add(new Goods(queryProductID,queryProductName,queryProductCategory,queryProductSellingPrice,queryProductBuyingPrice,queryProductQuantity,queryProductDate));
//                }
//                idTableColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));
//                goodnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productname"));
//                categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryname"));
//                sellingpriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));
//                buyingpriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("buyingprice"));
//                quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//                dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//
//                addgoodsTableView.setItems(goodsObservableList);

            }catch (SQLException e){
                //Logger.getLogger(Goods.class.getName()).log(Level.SEVERE,null,e);
                e.printStackTrace();
            }
        }
        // Data is already available. No need to fetch from DB again
        else {
            switch (categoryLowerCase){
                // Stack stores
                case  "beverages":
                    insertStackProductsIntoTable(beveragesStore);
                    break;
                case "bread/bakery":
                    insertStackProductsIntoTable(breadBakeryStore);
                    break;
                case "canned/jarred goods":
                    insertStackProductsIntoTable(cannedJarredStore);
                    break;
                case "dairy products":
                    insertStackProductsIntoTable(diaryProductStore);
                    break;
                // Queue stores
            }
        }
    }
    private void  insertProductIntoTable(Goodsdata product){
        goodsObservableList.add(product);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        goodnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        sellingpriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        buyingpriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        addgoodsTableView.setItems(goodsObservableList);
    }
    private void  insertStackProductsIntoTable(Stack<Goodsdata> products){
        for(int i=0;i<products.size();i++) {
            insertProductIntoTable(products.get(i));
        }
    }
    private void obtainGrandSellingPrice() {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String totalSellingPrice = "select sum(selling_price) as total_selling_price from product_table";
            double sum = 0;

            Statement statement = connectDB.createStatement();
            ResultSet queryTotal = statement.executeQuery(totalSellingPrice);
            while (queryTotal.next()){
                double totalSP = queryTotal.getDouble("total_selling_price");
                sum = sum + totalSP;
            }
            totalSellingPriceTextView.setText(String.valueOf(sum));
        } catch (SQLException e) {
            Logger.getLogger(Goods.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
    }

    private void obtainGrandBuyingPrice() {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String totalBuyingPrice = "select sum(buying_price) as total_buying_price from product_table";
            double sum = 0;

            Statement statement = connectDB.createStatement();
            ResultSet queryTotal = statement.executeQuery(totalBuyingPrice);
            while (queryTotal.next()){
                double totalSP = queryTotal.getDouble("total_buying_price");
                sum = sum + totalSP;
            }
            totalBuyingPriceTextField.setText(String.valueOf(sum));
        } catch (SQLException e) {
            Logger.getLogger(Goods.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
        
    }

    private void obtainGrandQuantiy() {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String totalQuantity = "select sum(quantity) as total_quantity from product_table";
            double sum = 0;

            Statement statement = connectDB.createStatement();
            ResultSet queryTotal = statement.executeQuery(totalQuantity);
            while (queryTotal.next()){
                double totalSP = queryTotal.getDouble("total_quantity");
                sum = sum + totalSP;
            }
            overallQuantityTextField.setText(String.valueOf(sum));
        } catch (SQLException e) {
            Logger.getLogger(Goods.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
    }

}
class Goodsdata{
    String productName = null;
    String categoryName = null;
    double sellingPrice = 0;
    int quantity = 0;
    double buyingPrice = 0;
    Date date = null;
    int id = 0;
    Goodsdata(){}
    Goodsdata(double buyingPrice,double sellingPrice,String categoryName,String productName,int quantity,int id,Date date){
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.categoryName = categoryName;
        this.productName = productName;
        this.quantity = quantity;
        this.id = id;
        this.date = date;
    }
    private ArrayList<Goodsdata> getData(ResultSet rs) throws Exception{
        ArrayList<Goodsdata> alldata = new ArrayList<>();
        try {

            Goodsdata goods;
            while(rs.next()){
                //for(int i = 1; i<= c; i++){
                goods = new Goodsdata();
                goods.buyingPrice = rs.getInt("buying_price");
                goods.sellingPrice = rs.getInt("selling_price");
                goods.categoryName = rs.getString("product_category");
                goods.productName = rs.getString("product_name");
                goods.quantity = rs.getInt("quantity");
                goods.id = rs.getInt("product_id");
                goods.date = rs.getDate("date");
                alldata.add(goods);
                //}
            }

        }catch (Exception e){
            System.out.println("No no no Error");

        }
        return alldata;
    }
}
