package myclasses;

import java.util.Date;

public class Refrigerante extends Product{

    // constructor
    public Refrigerante(String name, int reference_num, Date expiration_date, double price, String type, String brand) {
        super(name, reference_num, expiration_date, price);
        this.type = type;
        this.brand = brand;
    }
    
    // atributes
    private String type; // Com acuçar, Sem acuçar
    private String brand;
    
    // methods
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
