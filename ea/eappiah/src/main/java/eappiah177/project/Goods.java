package eappiah177.project;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class Goods {
    Integer productid,quantity;
    double sellingprice,buyingprice;
    String productname,categoryname;
    Date date;

    public  Goods(Integer productid, String productname,String categoryname,double sellingprice,double buyingprice,Integer quantity,Date date){
        this.productid = productid;
        this.productname = productname;
        this.categoryname =categoryname;
        this.sellingprice = sellingprice;
        this.buyingprice = buyingprice;
        this.quantity = quantity;
        this.date = date;
    }

    public Integer getProductid() {
        return productid;
    }

    public double getSellingprice() {
        return sellingprice;
    }

    public double getBuyingprice() {
        return buyingprice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductname() {
        return productname;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public Date getDate() {
        return date;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public void setSellingprice(double sellingprice) {
        this.sellingprice = sellingprice;
    }

    public void setBuyingprice(double buyingprice) {
        this.buyingprice = buyingprice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
