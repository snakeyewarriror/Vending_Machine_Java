package myclasses;
import java.io.Serializable;
import java.text.SimpleDateFormat;  
import java.util.Date;  

abstract class Product implements Serializable{
    
    // constructor
    
    public Product(String name, int reference_num, Date expiration_date, double price) {
        this.name = name;
        this.reference_num = reference_num;
        this.expiration_date = expiration_date;
        this.price = price;
    }
    
    
    // atributes
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    protected String name;
    protected int reference_num;
    protected Date expiration_date;
    protected double price;
    
    
    // methods
    
    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Reference number
    public int getReference_num() {
        return reference_num;
    }

    public void setReference_num(int reference_num) {
        this.reference_num = reference_num;
    }
    
    // Expiration date
    public Date getExpiration_date() {
        return expiration_date;
    }

    public String getExpiration_date_str() {
        return formatter.format(expiration_date);
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    // Price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
}
