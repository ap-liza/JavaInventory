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
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewGoodsController implements Initializable {
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
    private TableView<Goods> addgoodsTableView;
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

    int totalGoods = 0;
    ObservableList<Goods> goodsObservableList = FXCollections.observableArrayList();

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
        String name = goodnameTextField.getText();
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
            goodsObservableList.add(new Goods(totalGoods+1,name,category,sprice,bprice,qty, date));
            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));
            goodnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productname"));
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryname"));
            sellingpriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));
            buyingpriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("buyingprice"));
            quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

            addgoodsTableView.setItems(goodsObservableList);
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
                    }else if (newval == "Meat") {
                        goodsNameChoiceBox.getItems().addAll(Meat);
                    }else if (newval == "Dairy Products") {
                        goodsNameChoiceBox.getItems().addAll(dairyChoices);
                    }else if (newval == "Dry/Baking Goods") {
                        goodsNameChoiceBox.getItems().addAll(bakeryChoices);
                    }

                }
        );
        populatingTable();
        obtainGrandSellingPrice();
        obtainGrandBuyingPrice();
        obtainGrandQuantiy();
    }

    private void populatingTable() {
        try{
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String productQuery = "select product_id,product_name,product_category,selling_price,buying_price,quantity,date from product_table";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(productQuery);


            while (queryOutput.next()){
                totalGoods++;
                Integer queryProductID = queryOutput.getInt("product_id");
                String queryProductName = queryOutput.getString("product_name");
                String queryProductCategory = queryOutput.getString("product_category");
                Integer queryProductSellingPrice = queryOutput.getInt("selling_price");
                Integer queryProductBuyingPrice = queryOutput.getInt("buying_price");
                Integer queryProductQuantity = queryOutput.getInt("quantity");
                Date queryProductDate = queryOutput.getDate("date");
                goodsObservableList.add(new Goods(queryProductID,queryProductName,queryProductCategory,queryProductSellingPrice,queryProductBuyingPrice,queryProductQuantity,queryProductDate));
            }
            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("productid"));
            goodnameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productname"));
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("categoryname"));
            sellingpriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));
            buyingpriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("buyingprice"));
            quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

            addgoodsTableView.setItems(goodsObservableList);

        }catch (SQLException e){
            Logger.getLogger(Goods.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
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


