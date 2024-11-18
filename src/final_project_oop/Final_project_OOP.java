package final_project_oop;
import java.text.ParseException;
import java.util.Scanner;
import myclasses.VendingMachine;

// TODO:
//      check so that when selecting a specific product it checks the amount that is inside of that slot not the stock in general
//      change the option to allow exit
//      change diagram

public class Final_project_OOP {
    public static void main(String[] args) throws ParseException {
        // Reader initializer
        Scanner reader = new Scanner(System.in);
        
        // Vending machine constructor
        VendingMachine vendingMachine = new VendingMachine();
        
        
        vendingMachine.printMachine();
        vendingMachine.waitSeconds(2000);
        
        
        
        
        // Imports the slots and history from the .ser
        vendingMachine.importData();
        
        
        boolean on = true;
        while(on){
            
            // Add machine art here
            
            int functionality = vendingMachine.checkNumInt("""
                               Cliente(1)
                               Gestor(2)
                               Sair(0)""");
            
            switch (functionality) {
                
                // -------- Client Menu -------- //
                
                
                case 1 -> {
                    boolean client_on = true;
                    while(client_on){
                        
                        int client_choice = vendingMachine.checkNumInt("""
                                                                        \nAdicionar fundos(1)
                                                                        Comprar produtos(2)
                                                                        Sair(0)""");
                        
                        switch(client_choice){
                            
                            // Exit
                            case 0 -> {
                                client_on = false;
                                vendingMachine.checkFunds(vendingMachine.getClient_funds(), true);
                                System.out.println("Tenha um bom dia.\n\n");
                                break; }
                            
                            // Add funds to client
                            case 1 -> {
                                vendingMachine.addFunds();
                                System.out.println("\n");
                            }
                            
                            // Buy product
                            case 2 -> {
                                
                                
                                // Asks the user what product they want to buy
                                System.out.println("Quer comprar um chocolate(c), uma sandes(s) ou um refrigerante(r)? ");
                                String product_choice = reader.nextLine().toLowerCase();
                                
                                if("cafe".equals(product_choice) || "café".equals(product_choice)){
                                    System.out.println("\nVeja este link e depois volte a tentar.\nhttps://www.wikihow.com/Teach-Yourself-to-Read");
                                    break;
                                }
                                
                                product_choice = vendingMachine.checkProdcutName(product_choice);
                                
                                // Shows the first product of each category
                                vendingMachine.printProductsCategory(product_choice);
                                
                                // Sells the product
                                vendingMachine.sellProduct(product_choice);
                                System.out.println("\n");
                            }
                            
                            // Default case
                            default -> { System.out.println("\nPor favor insira uma opção válida.\n"); break; }
                            
                        }
                    }
                }
                
                
                // -------- Manager Menu -------- //
                
                
                case 2 -> {
                    boolean manager_on = true;
                    // Checks for password
                    int tries = 0;
                    int password = vendingMachine.checkNumInt("Insira a palavra passe");
                    while(password != 1234) {
                        
                        tries += 1;
                        if( tries == 3){
                            System.out.println("\ná não tem mais tentativas. Por favor tente novamente mais tarde.\n");
                            manager_on = false;
                            break;
                        }
                        password = vendingMachine.checkNumInt("\nA palavra passe que inseriu está incorreta tem mais " + (3 - tries) + " tentativas.");
                    }
                    
                    while(manager_on){
                        
                        int colaborator_choice = vendingMachine.checkNumInt("""
                                                                            \nAdicionar produtos(1)
                                                                            Retirar produtos(2)
                                                                            Consultar o total de vendas(3)
                                                                            Ver histórico de vendas(4)
                                                                            Sair(0)""");
                        
                        switch(colaborator_choice){
                            
                            // Exit
                            case 0 -> { manager_on = false; System.out.println("\nTenha um bom dia.\n\n"); break;}
                            
                            
                            // Add products to the machine
                            case 1 -> {
                                
                                // Shows the first product of all the slots
                                vendingMachine.showFirstProductSlots();
                                
                                boolean more = true;
                                
                                while(more){

                                    System.out.println("\nQuer inserir chocolate(c), uma sandes(s) ou um refrigerante(r)?");
                                    String product_choice = reader.nextLine().toLowerCase();

                                    product_choice = vendingMachine.checkProdcutName(product_choice);
                                    if(product_choice != null){
                                        vendingMachine.addProduct(product_choice);

                                    }
                                    
                                    System.out.println(vendingMachine.checkAnswer("\nQuer retirar mais algum produto (s/n)?"));
                                    
                                    if(!vendingMachine.checkAnswer("\nQuer retirar mais algum produto (s/n)?")){ more = false; }
                                    
                                    System.out.println("\n");
                                }
                                break;
                            }
                            
                            
                            // Remove product from machine
                            case 2 -> {
                                
                                
                                boolean more = true;
                                
                                while(more){
                                
                                    // Asks for the name of the product and checks if it is valid
                                    System.out.println("\nQuer retirar chocolate(c), uma sandes(s) ou um refrigerante(r)? ");
                                    String product_choice = reader.nextLine().toLowerCase();


                                    product_choice = vendingMachine.checkProdcutName(product_choice);
                                    
                                    if(vendingMachine.checkStock(product_choice)){


                                        vendingMachine.printFirstProductByCategory(product_choice);
                                        
                                        // Removes the product if there is any in stock
                                        if(product_choice != null){
                                            int ref_num = vendingMachine.checkNumInt("\nInsira o número de referência do produto: ");
                                            vendingMachine.removeProduct(product_choice, ref_num);
                                        }
                                    }
                                    
                                    
                                    if(!vendingMachine.checkAnswer("\nQuer retirar mais algum produto (s/n)?")){ more = false; }
                                    System.out.print("\n");
                                }
                                break;
                            }
                            
                            
                            // Prints the sell total
                            case 3 -> { System.out.println("\nTotal de vendas: " + vendingMachine.getSell_total()); System.out.println("\n"); break; }
                            
                            
                            // Check history
                            case 4 -> { vendingMachine.sellHistory(); System.out.println("\n"); break; }
                            
                            
                            // Default case
                            default -> { System.out.println("\nPor favor insira uma opção válida.\n"); break; }
                            
                        }
                    }
                }
                case 0 -> on = false;
                default -> System.out.println("\nPor favor insira uma opção válida.\n");
            }
        }
        
        
        // Exports the slots and history to a .ser to be read later
        vendingMachine.exportData();
        
        
        // Close the readers
        vendingMachine.closeReader();
        reader.close();
    }
}
