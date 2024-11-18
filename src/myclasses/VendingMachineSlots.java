package myclasses;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class VendingMachineSlots implements Serializable{
    
    // Constructors
    public VendingMachineSlots(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        this.products = new ArrayList<>();
    }
    
    
    // Atributes
    private int referenceNumber;
    private final ArrayList<Product> products;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static final DecimalFormat decfor = new DecimalFormat("#.##");
    
    // Methods
    
    // Returns true if no products are in the slot
    public boolean isEmpty() {
        return products.isEmpty();
    }
    
    
    // Gets all the products and prints their information
    public void displayProduts(){
        
        if(!products.isEmpty()){
            for(Product product: products){
                System.out.println("\nNumero de referencia: " + product.getReference_num() 
                        + " - " + product.getClass().getSimpleName()
                        + "\nNome: " + product.getName()
                        + "\nData de valdiade: " + formatter.format(product.getExpiration_date())
                        + "\nPreço: " + decfor.format(product.getPrice()));
            }
            System.out.println("\n");
        }
    }
    
    
     // Gets the first product and prints it´s information
    public void displayFirstProduct() {
        
        if(!products.isEmpty()){
            Product product = products.get(0);
            System.out.println("\nNumero de referencia: " + product.getReference_num()
                    + " - " + product.getClass().getSimpleName()
                    + "\nNome: " + product.getName()
                    + "\nPreço: " + decfor.format(product.getPrice())
                    + "\nQuantidade:" + products.size() + "\n\n");
        }
    }

    
    // Prints all the products that have the same class name as the product_choice(user_input)
    public void displayProductByCategory(String product_choice){
        if(!products.isEmpty()){
            for(Product product: products){
                if(product.getClass().getSimpleName().toLowerCase().equals(product_choice)){
                    System.out.print("\nNumero de referencia: " + product.getReference_num() 
                            + " - " + product.getClass().getSimpleName()
                            + "\nNome: " + product.getName()
                            + "\nData de valdiade: " + formatter.format(product.getExpiration_date())
                            + "\nPreço: " + decfor.format(product.getPrice()) + "\n\n");
                }
            }
        }
    }
    
    
    // Prints all the products that have the same class name as the product_choice(user_input)
    public void displayFirstProductByCategory(String product_choice){
        if(!products.isEmpty()){
            Product product = products.get(0);
            if(product.getClass().getSimpleName().toLowerCase().equals(product_choice)){
                System.out.print("\nNumero de referencia: " + product.getReference_num() 
                        + " - " + product.getClass().getSimpleName()
                        + "\nNome: " + product.getName()
                        + "\nData de valdiade: " + formatter.format(product.getExpiration_date())
                        + "\nPreço: " + decfor.format(product.getPrice()) 
                        + "\nQuantidade: " + products.size() + "\n\n");
            }
        }
    }
    
    
    // Removes a product depending on its reference number
    public void removeProduct(int amount){
        if(amount > products.size()) { System.out.println("Inseriu um numero maior do que existe de produtos, por favor tente outra vez."); }
        else{
            for(int i=0; i<amount; i++){
                products.remove(products.size() - 1);
            }

        }
    }
    
    public String getProductClassName(){
        return products.get(0).getClass().getSimpleName().toLowerCase();
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public void addProduct(Product product){
        products.add(product);
    }
    
    public double getPrice(){
        return products.get(0).getPrice();
    }
    
    public Product getProduct(){
        return products.get(0);
    }
    
    public int getAmount(){
        return products.size();
    }
    
    public ArrayList<Product>  getProducts(int amount){
       ArrayList<Product> selectedProducts = new ArrayList<>();
       int aux = products.size() - 1;
       for(int i=0; i<amount; i++){
           
            // Check to avoid out-of-bounds
            if (aux < 0) { break; }
            selectedProducts.add(products.get(aux--));
        }
       return selectedProducts;
    }
}
