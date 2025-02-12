# Vending Machine Simulation - Java Project

## Overview
This project is a **Java-based vending machine simulation** that allows users to purchase snacks such as chocolates, soft drinks, and sandwiches. The system simulates payment processing, stock management, and transaction history.

## Features

### **Products Available**
The vending machine sells three types of products, each with unique characteristics:
- **Chocolates**
  - Type of cocoa (Dark, White, or Milk)
  - Brand associated
  - Stock capacity: **20 units**
- **Soft Drinks**
  - Sugar content (Regular or Sugar-Free)
  - Brand (Pepsi, Sumol, or Lipton)
  - Stock capacity: **15 units**
- **Sandwiches**
  - Type (Mixed, Ham, or Cheese)
  - Producer name
  - Stock capacity: **10 units**

### **Purchase Process**
1. The customer **inserts money** into the machine.
2. The customer **selects a product category** (Chocolate, Soft Drink, or Sandwich).
3. A list of available products in that category is displayed. If out of stock, a message is shown.
4. The customer **chooses a product** by its reference.
   - If the money inserted is **insufficient**, an error message displays the amount needed.
   - If the money is **sufficient**, the product is dispensed, and change (if applicable) is returned.
   - The customer can cancel the transaction if they don’t have more money.
5. The **stock is updated** after a successful purchase.

### **Machine Management (Admin Features)**
A collaborator (admin) has access to:
- **Add new products** to the vending machine.
- **Remove products** from the machine.
- **Check total revenue** generated from sales.
- **View sales history**, including product names and prices.
- **Clear sales history** when needed.

## Technical Details
- **Persistent Storage:**
  - Product data is stored in `stock.dat`.
  - Data is automatically loaded when the machine starts.
- **Exception Handling:**
  - Ensures smooth error handling for invalid inputs and insufficient funds.
  - Prevents crashes due to file read/write issues.
- **Menu-based Interface:**
  - Users interact with the system via a text-based menu.

## Project Structure
```
├── src/                    # Java source code
│   ├── models/             # Product classes (Chocolate, Drink, Sandwich)
│   ├── vendingmachine/     # Core vending machine logic
│   ├── utils/              # Helper classes (file handling, validation, etc.)
├── OOP_Diagrama_Classes.drawio  # Class diagram (editable)
├── OOP_Diagrama_Classes.pdf     # Class diagram (PDF)
├── stock.dat              # Persistent storage file
├── build.xml              # Build configuration
├── manifest.mf            # Manifest file for execution
```

## License
This project is licensed under the MIT License.

---
**Developed as part of an Object-Oriented Programming assignment.**

