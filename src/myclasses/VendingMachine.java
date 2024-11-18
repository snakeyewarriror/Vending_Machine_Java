package myclasses;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class VendingMachine{
    
    // Constructor
    public VendingMachine() { }
    
    
    // Atributes
    private int stock_chocolate= 0;
    private int stock_refrigerante= 0;
    private int stock_sandes= 0;
    private double sell_total= 0;
    private double client_funds = 0;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static final DecimalFormat decfor = new DecimalFormat("#.##");
    
    // Arrays
    private ArrayList<Product> history = new ArrayList<>();
    private ArrayList<VendingMachineSlots> slots = new ArrayList<>();
    
    
    // Methods
    Scanner reader = new Scanner(System.in);
    
    
    // Wait timer
    public void waitSeconds(int amount){
        try {
            Thread.sleep(amount);
        }
        catch (InterruptedException e) {
            e.getMessage();
        }
    }
    
    
    // Checks if the user input is a double number
    public double checkNumDouble(String prompt){
        boolean notInt = true;
        double num = 0;
        while(notInt){
            System.out.println(prompt);
            try{
                num = reader.nextDouble();
                reader.nextLine();
                notInt = false;
            }
            catch(InputMismatchException e){
                System.out.println("\nPor favor insira um numero.\n");
                reader.nextLine();
            }
        }
        return num;
    }

    // Checks if the user input is a int number
    public int checkNumInt(String prompt){
        boolean notInt = true;
        int num = 0;
        while(notInt){
            System.out.println(prompt);
            String input = reader.nextLine();
            
            try{
                num = Integer.parseInt(input);
                notInt = false;
            }
            
            catch(NumberFormatException e){
                
                // Try parsing as a double if it's not an integer
                try {
                Double.valueOf(input);
                System.out.println("\nPor favor insira um número inteiro, não um decimal.\n");
                }
                
                // If it fails to parse as both int and double
                catch (NumberFormatException ex) {
                System.out.println("\nPor favor insira um número.\n");
                }
            }
            
            catch(InputMismatchException e){
                System.out.println("\nPor favor insira um numero válido.\n");
                reader.nextLine();
            }
            
        }
        return num;
    }
    
    
    // Checks if product name is valid
    public String checkProdcutName(String choice_product){
        boolean product_choice = true;
        
        while(product_choice){
            if("sair".equals(choice_product)){ product_choice = false; System.out.println("\n"); }
            
            else if("chocolate".equals(choice_product) || "c".equals(choice_product)){
                product_choice = false;
                choice_product = "chocolate";
                return choice_product;
            }

            else if("sandes".equals(choice_product) || "s".equals(choice_product)){
                product_choice = false;
                choice_product = "sandes";
                return choice_product;
            }

            else if("refrigerante".equals(choice_product) || "r".equals(choice_product)){
                product_choice = false;
                choice_product = "refrigerante";
                return choice_product;
            }

            else{
                System.out.println("Por favor escolha entre um chocolate(c), uma sandes(s) ou um refrigerante(r). Se quiser voltar atrás "
                    + "prima sair.");
                    
                choice_product = reader.nextLine().toLowerCase();
            }
        }
        return null;
    }
    
    
    // Checks if there is any funds in the machine
    public void checkFunds(double amount, boolean exit){
        
        
        if(exit){
            // If there is any chance left it gives it back to the client
            if(this.getClient_funds() > 0){
            System.out.println("Não se esquça do seu dinheiro " + decfor.format(amount) + "€.\n");
            this.setClient_funds(0);
            }
        }
        
        else{
            // If there is any chance left it gives it back to the client
            if(this.getClient_funds() > 0){
                System.out.println("Não se esquça do seu troco de " + decfor.format(amount) + "€.\n");
                this.setClient_funds(0);
            }
        }
    }
    
    
    // Checks for the user answer
    public boolean checkAnswer(String question){
        
        boolean undecided = true;
        
        System.out.println(question);
        while(undecided){
            String answer = reader.nextLine().toLowerCase();

            if("s".equals(answer)) { undecided = false; return true;}

            else if("n".equals(answer)) {undecided = false; return false;}
            
            
            else { System.out.println("Por favor escolha uma opção válida\n"); }
        }
        return false;
    }
    
    
    // Find or Create Slot method
    public VendingMachineSlots findOrCreateSlot(int ref_number, boolean removal, int... log){
        
        // If the program is loading data from a file it will add new slots accordingly
        
        boolean logValue = log.length > 0 && log[0] != 0;
        
        if(logValue){
            for (VendingMachineSlots slot : slots){
                if(slot.getReferenceNumber() == ref_number){ return slot; }
            }
            VendingMachineSlots newSlot = new VendingMachineSlots(ref_number);
            slots.add(newSlot);
        }
        
        
        // Searches trough the all the slots inside of the slots array and if it finds one with the same reference number
        // it returns that slot
        
        else{
            for (VendingMachineSlots slot : slots){
                if(slot.getReferenceNumber() == ref_number){ return slot; }
            }

            if(removal){
                System.out.println("Não existe nehum produto com essa referência, por favor tente novamente.");
            }

            // If a slot doesn´t exist it asks the user if they want to add one
            else{

                if(checkAnswer("Não existe nehum slot com essa referência, quer criar um?(s/n)")){
                    VendingMachineSlots newSlot = new VendingMachineSlots(ref_number);
                    slots.add(newSlot);
                    return newSlot;
                }
            }
        }
        return null;
    }
    
    
    // Calls the findOrCreateSlot method and then adds the product to the slot that the method returns
    public void addProductSlot(int ref_num, Product product){
        VendingMachineSlots slot = findOrCreateSlot(ref_num, false);
        slot.addProduct(product);
    }
    
    
    // ---------------- Manager Methods ---------------- //
    
    
    //  Add Product Method
    public void addProduct(String choice_product, int... loading) throws ParseException{
        
        boolean loading_value = loading.length > 0 && loading[0] != 0;
        
        if(loading_value){
            // add loading option here
        }
        
        // Not loading for first time
        else{
            String product_name = "";

            // Checks if the user input for the product choice and if it isn´t any of the options it keeps asking for the right choice
            // and updates the product_name variable

                if("chocolate".equals(choice_product)){ product_name = "o " + choice_product; }

                else if("sandes".equals(choice_product)){ product_name = "a " + choice_product;}

                else if("refrigerante".equals(choice_product)){ product_name = "o " + choice_product;}


            // Asks for the reference number for the slot
            int ref_num = checkNumInt("\nInsira o numero de referência do produto: ");

            // Goes trhougth all the slots that exist to see if it matches the reference number given and if it doesn´t
            // it asks if the user wants to create one, if they do not it returns a null and exits the method

            VendingMachineSlots slot = findOrCreateSlot(ref_num, false);
            if(slot != null){
                
                if(slot.isEmpty()){

                    // Initiates the date formater
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    formatter.setLenient(false);

                    // Asks for the name of the product
                    System.out.println("\nInsira o nome d" + product_name + ": ");
                    String name = reader.nextLine();

                    // Asks for the date of expiration of the product and then checks it against the current date
                    // if the input date is after the curretn date is asks for a new expieraiton date

                    Date date_input = null;
                    boolean rot = true;
                    while(rot){

                        System.out.println("\nInsira a data de validade d" + product_name + ": ");

                        // Checks if the input is a valid date
                        while(date_input == null){
                            try{
                                date_input = formatter.parse(reader.nextLine());
                            }
                            catch(ParseException e){
                                System.out.println("\nA data de validade que inseriu é inválida por favor tente novamente.");
                            }
                        }

                        // Checks input date against the current date
                        Date today = new Date();
                        if(date_input.before(today)){
                            System.out.println("\nA data que inseriu já expirou.");
                            date_input = null;
                        }
                        else{ rot = false; }
                    }


                    // Asks for the price of the product
                    double price = checkNumDouble("\nInsira o preço d" + product_name + ": ");

                    // Asks what the type the product is
                    System.out.println("\nInsira o tipo d" + product_name + ": ");
                    System.out.flush();
                    String type = reader.nextLine();

                    // Asks for the brand of the product
                    if("sandes".equals(choice_product)){ System.out.println("\nInsira a produtor d" + product_name + ": "); System.out.flush(); }
                    
                    else{ System.out.println("\nInsira a marca d" + product_name + ": "); System.out.flush(); }
                    String brand = reader.nextLine();


                    // Depending on the choice_product it asks the user the amount of one of the products
                    int amount = 0;
                    if("chocolate".equals(choice_product)){
                        amount = checkNumInt("\nQuantos chocolates quer inserir? ");
                        while(amount + this.stock_chocolate  > 20){
                            amount = checkNumInt("\nA quantidade que inseriu ultrapassa o limite maximo de " + choice_product + "s permitido, por favor tente novamente.");
                        }
                    }

                    else if("sandes".equals(choice_product)){
                        amount = checkNumInt("\nQuantas sandes quer inserir? ");
                        while(amount + this.stock_sandes  > 10){
                            amount = checkNumInt("\nA quantidade que inseriu ultrapassa o limite maximo de " + choice_product + "s permitido, por favor tente novamente.");
                        }
                    }

                    else if("refrigerante".equals(choice_product)){
                        amount = checkNumInt("\nQuantos refrigerantes quer inserir? ");
                        while(amount + this.stock_refrigerante  > 15){
                            amount = checkNumInt("\nA quantidade que inseriu ultrapassa o limite maximo de " + choice_product + "s permitido, por favor tente novamente.");
                        }
                    }

                    // Adds the chosen product to the specified slot number and adds the
                    // amount chosen to the specified product´s stock
                    for(int i=0; i<amount; i++){

                        // Chocolate
                        if("chocolate".equals(choice_product)){
                            addProductSlot(ref_num, new Chocolate(name, ref_num, date_input, price, type, brand));
                            this.stock_chocolate +=1;
                        }

                        // Sandes
                        else if("sandes".equals(choice_product)){
                            addProductSlot(ref_num, new Sandes(name, ref_num, date_input, price, type, brand));
                            this.stock_sandes +=1;
                        }

                        // Refrigerante
                        else if("refrigerante".equals(choice_product)){
                            addProductSlot(ref_num, new Refrigerante(name, ref_num, date_input, price, type, brand));
                            this.stock_refrigerante +=1;
                        }
                    }
                }
                
                else{
                    // Compares the product_choice with the name product class name from the slot
                    if(!choice_product.equals(slot.getProductClassName())){
                        System.out.println("\nO numero de referência que inseriu não consta com o tipo produto que" +
                                " inseriu por favor tente novamente. ");
                    }
                    
                    else{
                        if(checkAnswer("\nJá existe produtos com essa referência na maquina deseja adicionar mais na mesma referência (s/n)?")){

                            Product product = slot.getProduct();

                            // Depending on the choice_product it asks the user the amount of one of the products
                            int amount = 0;
                            if("chocolate".equals(choice_product)){
                                amount = checkNumInt("\nQuantos chocolates quer inserir? ");
                                while(amount + this.stock_chocolate  > 20){
                                    amount = checkNumInt("\nA quantidade que inseriu ultrapassa o limite maximo de " + choice_product + "s permitido, por favor tente novamente.");
                                }
                            }

                            else if("sandes".equals(choice_product)){
                                amount = checkNumInt("\nQuantas sandes quer inserir? ");
                                while(amount + this.stock_sandes  > 10){
                                    amount = checkNumInt("\nA quantidade que inseriu ultrapassa o limite maximo de " + choice_product + " permitido, por favor tente novamente.");
                                }
                            }

                            else if("refrigerante".equals(choice_product)){
                                amount = checkNumInt("\nQuantos refrigerantes quer inserir? ");
                                while(amount + this.stock_refrigerante  > 15){
                                    amount = checkNumInt("\nA quantidade que inseriu ultrapassa o limite maximo de " + choice_product + "s permitido, por favor tente novamente.");
                                }
                            }

                            // Adds the chosen product to the specified slot number and adds the
                            // amount chosen to the specified product´s stock
                            for(int i=0; i<amount; i++){

                                // Chocolate
                                if("chocolate".equals(choice_product)){
                                    slot.addProduct(product);
                                    this.stock_chocolate +=1;
                                }

                                // Sandes
                                else if("sandes".equals(choice_product)){
                                    slot.addProduct(product);
                                    this.stock_sandes +=1;
                                }

                                // Refrigerante
                                else if("refrigerante".equals(choice_product)){
                                    slot.addProduct(product);
                                    this.stock_refrigerante +=1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    //  Remove Product Method
    public void removeProduct(String choice_product, int ref_num, int... amount_buy) throws ParseException{
        
        boolean amount_buy_bool = amount_buy.length > 0 && amount_buy[0] != 0;
        int amount = 0;
                
        VendingMachineSlots slot = findOrCreateSlot(ref_num, true);
        
        if(slot != null){
            
            if(slot.isEmpty()) { System.out.println("O slot que escolheu não contém nenhum produto por favor tente novamente."); }
            
            else{
                
                if(!amount_buy_bool){
                    
                    if("chocolate".equals(choice_product)){ amount = checkNumInt("\nQuantos chocolates quer retirar? "); }

                    else if("sandes".equals(choice_product)){ amount = checkNumInt("\nQuantas sandes quer retirar? "); }

                    else if("refrigerante".equals(choice_product)){ amount = checkNumInt("\nQuantos refrigerantes retirar? "); }
                }

                
                // If buying sets the amount that the user has put in as the amount
                else{ amount = amount_buy[0]; }


                // Removes the products from the slot and reduces the stock accordingly
                slot.removeProduct(amount);

                if(slot.isEmpty()) { slots.remove(slot); }

                if("chocolate".equals(choice_product)){ this.stock_chocolate -= amount; }

                else if("sandes".equals(choice_product)){ this.stock_sandes -= amount; }

                else if("refrigerante".equals(choice_product)){ this.stock_refrigerante -= amount; }
            }
        }
    }
    
    
    //  Check Stock Method
    public boolean checkStock(String choice_product) throws ParseException{
            
            // Chocolate stock check
            if("chocolate".equals(choice_product)){
                
                // Checks if the chocolate stock is already at max capacity if it isn´t it lets the user add a product
                // and if it is it asks the user if they want to remove some
                
                if(this.getStock_chocolate() >= 20){
                    if(checkAnswer("A maquina já atingiu o limite de 20 chocolates. Deseja retirar algum? (s/n)")){
                        this.printFirstProductByCategory(choice_product);
                        int ref_num = checkNumInt("\nInsira o numero de referência do produto: ");
                        this.removeProduct(choice_product, ref_num);
                        return true;
                    }
                    
                    else{ System.out.println("\n"); return false; }
                }
                
                else if(this.getStock_chocolate() == 0) {
                    System.out.println("Neste momento não temos nehum " + choice_product + " por favor tente novamente mais tarde.");
                    return false;
                }
                
                // Returns true if there is enough stock
                else{ return true; }
            }

            // Sandes stock check
            else if("sandes".equals(choice_product)){
                
                // Checks if the sandes stock is already at max capacity if it isn´t it lets the user add a product
                // and if it is it asks the user if they want to remove some
                 
                if(this.getStock_sandes() >= 10){
                    if(checkAnswer("A maquina já atingiu o limite de 10 sandes. Deseja retirar algum? (s/n)")){
                        this.printFirstProductByCategory(choice_product);
                        int ref_num = checkNumInt("\nInsira o numero de referência do produto: ");
                        this.removeProduct(choice_product, ref_num);
                        return true;
                    }
                    
                    else{ System.out.println("\n"); return false; }
                }
                
                
                else if(this.getStock_sandes() == 0) {
                    System.out.println("Neste momento não temos nehuma " + choice_product + " por favor tente novamente mais tarde.");
                    return false;
                }
                
                // Returns true if there is enough stock
                else{ return true; }
            }

            // Refrigerante stock check
            else if("refrigerante".equals(choice_product)){
                
                
                // Checks if the refrigerante stock is already at max capacity if it isn´t it lets the user add a product
                // and if it is it asks the user if they want to remove some
                 
                if(this.getStock_refrigerante() >= 10){
                    if(checkAnswer("A maquina já atingiu o limite de 15 refrigerantes. Deseja retirar algum? (s/n)")){
                        this.printFirstProductByCategory(choice_product);
                        int ref_num = checkNumInt("\nInsira o numero de referência do produto: ");
                        this.removeProduct(choice_product, ref_num);
                        return true;
                    }
                    
                    else{ System.out.println("\n"); return false; }
                }
                
                
                else if(this.getStock_refrigerante() == 0) {
                    System.out.println("Neste momento não temos nehum " + choice_product + " por favor tente novamente mais tarde.");
                    return false;
                }
                
                // Returns true if there is enough stock
                else{ return true;}
            }
        return false;
    }
    
    
    // Deletes all the product objects inside of the history array
    public void deleteHistory(){
        int i=0;
        while(i < history.size()){ history.remove(i); }
    }
    
    
    // Shows all the products inside of the hsitory array and asks the user if they want to clear them
    public void sellHistory(){
        System.out.println("\nHistórico de vendas:\n");
        for(Product product : history){
            System.out.println("\nNumero de referência: " + product.getReference_num() 
                    + "- " + product.getClass().getSimpleName()
                    + "\nNome: " + product.getName()
                    + "\nPrazo de valdiade: " + formatter.format(product.getExpiration_date())
                    + "\nPreço: " + product.getPrice() + "\n");
        }
        if(checkAnswer("\nQuer apagar o histórico de vendas?(s/n)")){ deleteHistory(); }
    }
    
    
    // ---------------- Client Methods ---------------- //
    
    
    public void addFunds(){
        double amount = checkNumDouble("\nQuanto quer inserir? ");
        while (amount <= 0){ amount = checkNumDouble("\nPor favor insira uma quantia maior que 0. "); }
        this.client_funds += amount;
    }
    
    public void sellProduct(String product_choice) throws ParseException{
        
        boolean exit = false;
        if(checkStock(product_choice)){
            
            // Checks for the product slot by reference number
            
            VendingMachineSlots slot = null;
            
            // Checks if the user 
            boolean different = true;
            
            int ref_num = checkNumInt("\nPor favor insira o numero de referência do produto: ");
            while(different){
                
                if(ref_num == -1){exit = true; break; }
                slot = findOrCreateSlot(ref_num, true);
                
                try{
                    // Compares the product_choice with the name product class name from the slot
                    if(!product_choice.equals(slot.getProductClassName())){
                        ref_num = checkNumInt("\nO numero de referência que inseriu não consta com o produto que" +
                                " foi inserido por favor insira o numero de referência válido ou prima -1 para sair: ");
                    }
                    
                    else{ different = false; }
                }
                
                catch (NullPointerException e){
                    different = false;
                }
                
                catch (IndexOutOfBoundsException e){
                    ref_num = checkNumInt("\nO numero de referência que inseriu não consta com o produto que" +
                                " foi inserido por favor insira o numero de referência válido ou prima -1 para sair: ");
                }
                
            }
            
            if(slot != null && ref_num != -1){
                
                // Asks how many products to buy
                int amount = 0;
                
                boolean not_enough = true;
                
                while(not_enough){
                    
                
                    if("chocolate".equals(product_choice)){ amount = checkNumInt("\nQuantos chocolates quer comprar? "); }

                    else if("sandes".equals(product_choice)){ amount = checkNumInt("\nQuantas sandes quer comprar? "); }

                    else if("refrigerante".equals(product_choice)){ amount = checkNumInt("\nQuantos refrigerantes quer comprar? "); }
                    
                    // Checks if the amount is bigger then the stock of chocolate
                    if(amount > slot.getAmount() && "chocolate".equals(product_choice)) {
                        exit = !checkAnswer("\nInseriu uma quantidade maior o que existe de stock quer tentar novamente (s/n)?");
                        if(exit){ not_enough = false; }
                    }
                    
                    // Checks if the amount is bigger then the stock of sandes
                    else if(amount > slot.getAmount() && "sandes".equals(product_choice)){
                        exit = !checkAnswer("\nInseriu uma quantidade maior o que existe de stock quer tentar novamente (s/n)?");
                        if(exit){ not_enough = false; }
                    }
                    
                    // Checks if the amount is bigger then the stock of refrigerantes
                    else if(amount > slot.getAmount() && "refrigerante".equals(product_choice)){
                        exit = !checkAnswer("\nInseriu uma quantidade maior o que existe de stock quer tentar novamente (s/n)?");
                        if(exit){ not_enough = false; }
                    }
                    
                    
                    // If 0 exit
                    else if(amount == 0) { exit = true; break; }
                    
                    else{ not_enough = false; }
                }
                
                if(!exit){
                    
                    // Calculates the total amount to pay
                    double total = slot.getPrice() * amount;
                    
                    
                    // Checks if the user wants to pay by card if they haven´t put any money in the machine before
                    if(this.getClient_funds() <= 0){
                        boolean deciding = true;
                        while(deciding){
                                System.out.println("\nQuer pagar por multibanco(m) ou dinheiro(d)?");
                                String answer = reader.nextLine().toLowerCase();

                                if("multibanco".equals(answer) || "m".equals(answer)){

                                    checkNumInt("\nInsira o seu pin");

                                    System.out.println("\nA processar o seu pagamento...\n");
                                    waitSeconds(4000);
                                    this.setClient_funds(total);
                                    deciding = false;
                                }

                                else if("dinheiro".equals(answer) || "d".equals(answer)) { deciding = false; }

                                else { System.out.println("\nPor favor escolha uma opção válida.\n"); }
                        }  
                    }
                
                
                    // Checks if the user as enough funds to buy the product and if they dont ask them to input more
                    while(this.getClient_funds() < total){
                        if(this.getClient_funds() < total){
                            if(checkAnswer("\nFaltam-lhe " + (total - this.getClient_funds()) + "€ deseja inserir mais dinheiro(s/n)?")) { this.addFunds(); }

                            else { exit = true; break; }
                        }
                    }
                    
                    
                    
                    if(!exit){
                        
                        // Adds all the products that were bought to the newProducts list
                        ArrayList<Product> newProducts = slot.getProducts(amount);

                        // Add all the products from the list newProducts to history
                        history.addAll(newProducts); 

                        // Add the total to the sell_total
                        this.sell_total += total;


                        // Removes the select 
                        this.removeProduct(product_choice, ref_num, amount);

                        System.out.println("A retirar o produto...\n");
                        waitSeconds(2000);

                        if("chocolate".equals(product_choice)){

                            
                            // ASCII Art
                            for(int i=0; i<amount; i++){ printChocolate(); }
                            
                            
                            if(amount > 1){ System.out.println("\nNão se esqueça dos seus chocolates.\n"); }

                            else{ System.out.println("\nNão se esqueça do seu chocolate.\n"); }
                            
                            this.stock_chocolate -= amount;

                        }

                        else if("sandes".equals(product_choice)){

                            // ASCII Art
                            for(int i=0; i<amount; i++){ printSandes(); }
                            
                            
                            if(amount > 1){ System.out.println("\nNão se esqueça das suas sandes.\n"); }

                            else{ System.out.println("\nNão se esqueça da sua sandes.\n"); }
                            
                            this.stock_sandes -= amount;
                        }

                        else if("refrigerante".equals(product_choice)){
                            
                            // ASCII Art
                            for(int i=0; i<amount; i++){ printRefrigerante(); }
                            

                            if(amount > 1){ System.out.println("\nNão se esqueça dos seus refrigerantes.\n"); }

                            else{ System.out.println("\nNão se esqueça do seu refrigerante.\n"); }
                            
                            this.stock_refrigerante -= amount;
                        }

                        // If there is any chance left it gives it back to the client
                        if((this.getClient_funds() - total) > 0){
                            this.checkFunds((this.getClient_funds() - total), false);
                        }

                        else{ this.setClient_funds(0);}
                    }
                }
            }
        }
    }
    
    
    //------- Print methods -------
    
    
    // Prints the first product in all the slots
    public void showFirstProductSlots(){
        for (VendingMachineSlots slot : slots){
            slot.displayFirstProduct();
        }
    }
    
    
    // Prints all the products in all the slots
    
    public void showProductSlots(){
        for (VendingMachineSlots slot : slots){
            slot.displayProduts();
        }
    }
    
    
    // Prints all products by class name
    
    public void printProductsCategory(String product_category){
        for(VendingMachineSlots slot : slots){
            slot.displayProductByCategory(product_category);
        }
    }
    
    
    // Prints all products by class name
    
    public void printFirstProductByCategory(String product_category){
        for(VendingMachineSlots slot : slots){
            slot.displayFirstProductByCategory(product_category);
        }
    }
    
    
    
    //------- Get / Set methods -------
    
    public int getStock_chocolate() {
        return stock_chocolate;
    }

    public void setStock_chocolate(int stock_chocolate) {
        this.stock_chocolate = stock_chocolate;
    }

    public int getStock_refrigerante() {
        return stock_refrigerante;
    }

    public void setStock_refrigerante(int stock_refrigerante) {
        this.stock_refrigerante = stock_refrigerante;
    }

    public int getStock_sandes() {
        return stock_sandes;
    }

    public void setStock_sandes(int stock_sandes) {
        this.stock_sandes = stock_sandes;
    }

    public double getSell_total() {
        return sell_total;
    }

    public void setSell_total(int sell_total) {
        this.sell_total = sell_total;
    }

    public double getClient_funds() {
        return client_funds;
    }

    public void setClient_funds(double client_funds) {
        this.client_funds = client_funds;
    }
    
    
    //------- Export / Import methods -------
    
    
    //Export data
    public void exportData(){
        try {
            FileOutputStream fileout = new FileOutputStream("VendingMachine_data.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            
            out.writeObject(slots);
            out.writeObject(history);
        }
        
        catch (IOException e) {
            System.out.println("\nHouve um problema a exportar os dados. Tente novamente.\n");
        }
    }
    
    
    //Import data
    public void importData(){
        
        
        // Imports the file if it exits
        File file = new File("VendingMachine_data.ser");
                
        
        // Checks if the file exists if not it returns early and sends a message
        if (!file.exists()) { System.out.println("Não existe nehum ficheiro para importar."); return; }
        
        try {
            FileInputStream filein = new FileInputStream("VendingMachine_data.ser");
            ObjectInputStream in = new ObjectInputStream(filein);
            
            slots = (ArrayList<VendingMachineSlots>)in.readObject();
            history = (ArrayList<Product>)in.readObject();
            
        }
        
        catch (IOException | ClassNotFoundException e) {
            System.out.println("\nHouve um problema a importar os dados. Tente novamente.\n");
        }
        
        // Goes trhougth all the slots and adds the amount of products inside of them to their respective stock
        for(VendingMachineSlots slot : slots){

            if(!slot.isEmpty()){
                
                // Adds chocolates to stock
                if("chocolate".equals(slot.getProductClassName())){ this.stock_chocolate += slot.getAmount(); }

                // Adds sandes to stock
                else if("sandes".equals(slot.getProductClassName())){ this.stock_sandes += slot.getAmount(); }

                // Adds refrigerante to stock
                else if("refrigerante".equals(slot.getProductClassName())){ this.stock_refrigerante += slot.getAmount(); }
            }
                
        }
    }
    
    
    //------- Close Reader method -------
    
    public void closeReader(){
        reader.close();
    }
    
    
    
    //------- ASCII ART method -------
    
    
    // ASCII Vending Machine
    public void printMachine(){
        System.out.println("\n" +
"⠀⠀⠀⠀⠀⠀⣿⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⣿⡿⠿⢿⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠀⢠⣤⠀⠀⣴⠀⠀⠀⠀⠀⣿⠀⣶⠀⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠰⠾⠿⠶⠾⠿⠶⠶⠶⠶⠀⣿⣀⣉⣀⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⢀⣤⡀⠀⠀⣿⣏⣉⣹⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠐⠒⠒⠒⠒⠒⠚⠛⠓⠒⠀⣿⣯⣉⣹⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠀⢠⡀⠀⣾⠀⠀⣶⡆⠀⠀⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠘⠛⠛⠛⠛⠛⠛⠛⠛⠛⠀⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠀⣶⣦⠀⣶⣶⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠈⠉⠉⠉⠉⠉⠉⠉⠉⠉⠀⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣏⣉⣹⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⠶⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⠀⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⡄⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⣿⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("Olá. Eu sou a HAL 9000 maquina de vendas.\n");
    }
    
    
    // ASCII Soda
    public void printRefrigerante(){
        System.out.println("\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠚⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠓⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⢤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⡤⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠿⠿⠿⠿⠿⠿⠿⠿⠿⠿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
    
    }
    
    
    // ASCII Chocolate
    public void printChocolate(){
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠤⢒⣉⢲.\n" +
"⠀⠀⠀⠀⠀⠀⣀⠴⠾⡅⠂⠁⠀⠱⢻⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⣀⡔⠪⠐⠉⢳⡛⣆⠀⣀⠀⣀⠽⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⣀⠔⡋⠴⣝⣆⠀⠀⠀⠁⢹⣷⡔⠊⠔⠳⢿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⣷⡄⠀⠀⠈⠊⢣⡐⣢⢔⡫⢌⢳⡄⠀⠀⠀⢙⡧⠤⢀⣀⡀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠘⣿⡄⠦⢄⡠⠜⠿⡄⠀⠀⠀⠀⠻⣌⡠⠐⣁⠀⠰⣄⣈⡈⠭⠂⠀⠀⠀⠀⠀\n" +
"⠀⠈⢿⣶⠁⠈⠉⠃⠹⣆⠒⢂⠄⠊⠙⠛⠲⠈⠲⣴⡞⢫⡀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⢻⣧⡐⠄⠀⡀⡜⠪⢷⣦⣄⣤⣀⠀⢀⠜⠋⠀⠀⣿⣄⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⢹⣷⣔⡡⠴⠻⠶⠋⠏⠓⠛⠙⠧⠎⠀⠀⡠⠂⠀⣿⣦⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⢧⣼⣿⣅⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠊⢀⠔⠈⠈⢻⣧⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠈⠉⢻⣿⣆⠀⠀⠀⠀⠀⠀⠀⣠⠎⡠⢊⠀⠀⠀⠀⠀⠘⢷⡀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠹⣿⣦⠀⠀⠀⠀⢀⢼⡣⢊⠔⠁⠀⠀⠀⠀⠀⠀⠀⢳⡀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣷⡀⠀⡐⡡⠋⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠱⡀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⡄⠀⠔⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠴⠁\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣿⣆⠀⠀⠀⠀⠀⠀⠀⢠⣤⡲⠖⠊⠁⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣿⣧⠀⠀⠀⣠⣴⡿⠛⠉⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣧⣀⡶⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
    }
    
    
    // ASCII Sandwich
    public void printSandes(){
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣤\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣠⡤⠤⠶⠖⠒⠛⠉⠉⣩⠟⢸⡇⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣤⠤⠤⠶⠖⠚⠛⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⢠⠞⠁⣠⠟⠁⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣤⡤⠴⠶⠖⠚⠛⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠋⣠⣾⡃⠀⠀⠀\n" +
"⠀⢠⡦⠤⢴⣶⣞⡛⠛⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡴⠋⢀⣴⡯⢾⢤⠀⠀⠀\n" +
"⠀⠘⣿⣠⠀⠀⠉⠉⠛⠲⠶⢦⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠞⠁⣴⢿⣟⠔⡹⠛⠉⢙⡇\n" +
"⠀⠀⠈⠉⢻⡶⢶⣦⣤⣀⡀⠀⠀⠈⠉⠛⠓⠶⢦⣤⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠞⠁⣠⠞⣡⢟⣵⠋⠀⠀⡠⡟⠁\n" +
"⠀⠀⠀⣾⠟⣛⣋⢹⣤⠾⠻⠿⣷⠶⠦⣤⣀⣀⠀⠀⠈⠉⠙⠛⠶⠦⣤⣄⣀⡀⠀⠀⢀⡴⠃⢠⣾⠷⢚⣵⠟⠁⣀⣴⡿⢁⡇⠀\n" +
"⠀⣠⣤⠽⠷⠶⢤⣭⣍⣒⡂⠀⠸⢦⣤⣤⠬⣭⡛⠓⠶⠤⣤⣀⣀⠀⠀⠈⠉⠛⠛⣶⠋⢀⣴⣿⢁⣴⠟⢁⣠⡾⠟⢋⣠⣞⣿⠀\n" +
"⠀⣵⣤⣄⣀⣀⣀⠀⠀⠉⠉⠉⠓⠒⠢⠤⢭⣉⣓⡒⠲⢶⣄⠈⠉⠙⠓⠲⢤⣤⣀⣸⡶⠛⢛⣵⠟⢁⣠⡾⡧⠴⣾⣿⣽⣯⡿⠀\n" +
"⠸⣟⠷⣌⣉⠈⠛⠿⠟⠳⢶⣶⣦⡤⣄⣀⣀⠀⠈⠉⠉⠒⠻⣦⠀⠀⢀⣠⠞⠃⠜⢛⣡⠴⠋⠀⣠⣾⡟⢠⠇⢻⣏⢀⡾⠋⠀⠀\n" +
"⠀⣿⣶⣄⣈⣉⣉⣉⣉⣉⣉⣁⣠⡴⠿⣇⠙⢿⣝⡻⢶⢦⣤⣌⣙⣚⠋⠉⠙⠛⠛⠋⠁⠀⣠⠾⠟⣩⣴⣋⡤⢾⡿⠋⠀⠀⠀⠀\n" +
"⠀⠻⢮⣍⣉⡉⠙⠿⣍⡉⠉⠉⠀⠀⠀⠙⢷⣄⠉⠛⠶⢤⣉⣛⡛⠻⠛⠓⣶⣤⣄⣀⣠⣾⡵⠾⠛⣽⠋⢀⡴⠋⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠉⠙⠛⠶⢬⣽⣗⡲⠦⠤⠴⠶⠶⠾⢿⣶⣤⣤⣀⣈⠉⠉⠉⢉⣉⣠⡴⠛⠁⠀⠀⣤⠞⢃⣴⠟⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠛⠶⠦⣤⣀⡀⠀⠈⠙⠳⣭⣉⣉⣉⣉⣉⣍⡁⠀⠀⠀⠀⣸⢏⣴⠏⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠛⠲⠦⣤⣀⡀⠉⠉⠉⠙⠛⠿⣦⣤⣤⢾⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠛⠳⠶⣤⣀⣀⠈⣇⣴⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
"⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⣛⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
    }
}
