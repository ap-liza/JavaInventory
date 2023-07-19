package eappiah177.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController{ //implements Initializable {
    @FXML
    private PieChart sellingPricePieChart,buyingPricePieChart,quantityPieChart;

    ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList();
//    PieChart.

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
////        try {
////            getBeverageTotal();
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
//    }

    private void getBeverageTotal() throws SQLException {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String totalQuantity = "select sum(selling_price) as beverage_total from product_table where (product_category= 'Beverages')";
            double sum = 0;

            Statement statement = connectDB.createStatement();
            ResultSet queryTotal = statement.executeQuery(totalQuantity);
            while (queryTotal.next()) {
                double totalSP = queryTotal.getDouble("total_quantity");
                sum = sum + totalSP;
            }
        System.out.println(sum);
    }
}
