package eappiah177.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class vendorsViewController implements Initializable {
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TableView<Vendors> vendorsTableView;
    @FXML
    private TableColumn<Vendors, Integer> idTableColumn;
    @FXML
    private TableColumn<Vendors,String>vendorNameTableColumn;
    @FXML
    private TableColumn<Vendors,String>vendorsLocationTableColumn;
    @FXML
    private TableColumn<Vendors,String> categoryProducedTableColumn;
    @FXML
    private TableColumn<Vendors,String>productNameTableColumn;
    @FXML
    private TableColumn<Vendors,String> dateTableColumn;
    ObservableList<Vendors> vendorsObservableList = FXCollections.observableArrayList();

    int totalvendors = 0;

    String[] categoryChoices = {"","Beverages","Bread/Bakery","Canned/Jarred Goods","Dairy Products"
            ,"Dry/Baking Goods","Frozen Products","Meat","Farm Produce","Home Cleaners",
            "Paper Goods","Home Care"};

//    @FXML
//    private void initialize(){
//        categoryChoiceBox.getItems().addAll(categoryChoices);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoiceBox.getItems().addAll(categoryChoices);
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String vendorQuery = "select * from vendors_table";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(vendorQuery);

            while (queryOutput.next()){
                totalvendors++;
                Integer queryVendorID = queryOutput.getInt("vendor_id");
                String queryVendorName = queryOutput.getString("vendor_name");
                String queryVendorLocation = queryOutput.getString("vendor_location");
                String queryCategoryProduced = queryOutput.getString("category_produced");
                String queryProductName = queryOutput.getString("product_name");
                Date queryDate =queryOutput.getDate("date");
                vendorsObservableList.add(new Vendors(queryVendorID,queryVendorName,queryVendorLocation,queryCategoryProduced,queryProductName,queryDate));
            }
            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("vendorid"));
            vendorNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("vendorname"));
            vendorsLocationTableColumn.setCellValueFactory(new PropertyValueFactory<>("vendorlocation"));
            categoryProducedTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("productname"));
            dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            vendorsTableView.setItems(vendorsObservableList);
        } catch (SQLException e) {
            Logger.getLogger(Goods.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }
    }
}