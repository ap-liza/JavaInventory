package eappiah177.project;

import java.sql.Date;

public class Vendors {
    Integer vendorid;
    String vendorname,vendorlocation,category,productname;
    Date date;

    public Vendors(Integer vendorid, String vendorname, String vendorlocation, String category, String productname, Date date) {
        this.vendorid = vendorid;
        this.vendorname = vendorname;
        this.vendorlocation = vendorlocation;
        this.category = category;
        this.productname = productname;
        this.date = date;
    }

    public Integer getVendorid() {
        return vendorid;
    }

    public String getVendorname() {
        return vendorname;
    }

    public String getVendorlocation() {
        return vendorlocation;
    }

    public String getCategory() {
        return category;
    }

    public String getProductname() {
        return productname;
    }

    public Date getDate() {
        return date;
    }

    public void setVendorid(Integer vendorid) {
        this.vendorid = vendorid;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public void setVendorlocation(String vendorlocation) {
        this.vendorlocation = vendorlocation;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
