/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Drink;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sg.com.vendingmachine.service.InsufficientFundsException;
import sg.com.vendingmachine.service.ZeroInventoryException;

/**
 *
 * @author Owner
 */
public class VendingDaoFileImpl implements VendingDao {

    private final String DRINKS_FILE;
    public static final String DELIMITER = "::";
    
    public VendingDaoFileImpl() {
        DRINKS_FILE = "drinks.txt";
    }
    
    public VendingDaoFileImpl(String drinkTextFile) {
        DRINKS_FILE = drinkTextFile;
    }
    
    private List<Drink> drinks = new ArrayList<Drink>();
    
    @Override
    public void addDrink(Drink name) throws VendingPersistenceException {
        drinks.add(name);
        writeRoster2();
    }
    
    @Override
    public List<Drink> getAllDrinks() throws VendingPersistenceException {
        return drinks;
    }
    
    @Override
    public Drink getDrink(String name) throws VendingPersistenceException {
        loadRoster2();
        for (Drink x: drinks) {
            if (x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }
    
    @Override
    public int getInventory(String name) throws VendingPersistenceException, ZeroInventoryException {
        loadRoster2();
        for (Drink x: drinks) {
            if (x.getName().equals(name)) {
                return x.getInventory();
            }
        }
        return 0;
    }
    
    @Override
    public void buyDrink(String name) throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException {
        loadRoster2();
        for (Drink x: drinks) {
            if (x.getName().equals(name)) {
                int current = x.getInventory();
                current--;
                x.setInventory(current);      
            }
        }
        writeRoster2();
    }
    
    @Override
    public BigDecimal convertToBig(float x) throws VendingPersistenceException {
        BigDecimal big = BigDecimal.valueOf(x);
        return big;
    }
    
    @Override
    public BigDecimal getPrice(String name) throws VendingPersistenceException {
        loadRoster2();
        for (Drink x: drinks) {
            if (x.getName().equals(name)) {
                BigDecimal biggy = x.getPrice();
                return biggy;
            }
        }
        return null;
    }
    
    // Decrement inventory (never ended up using this, but left the code in to have around for later)
    @Override
    public Drink oneLessInventory(String name) throws VendingPersistenceException {
        loadRoster2();
        for (Drink x: drinks) {
            if (x.getName().equals(name)) {
                    Drink editedDrink = new Drink(name, x.getPrice(), x.getInventory()-1);
                    writeRoster2();
                    return editedDrink;
            }
        }
        return null;
    }    
    
    // Fills machine with items
    
    @Override
    public void populateMachine() throws VendingPersistenceException {
        loadRoster2();
        writeRoster2();
    }
   
    // Data Marshalling/Unmarshalling
      
    private Drink unmarshallDrink(String drinkAsText){

        String[] drinkTokens = drinkAsText.split(DELIMITER);


        String drinkName = drinkTokens[0];
        String drinkPrice = drinkTokens[1];
        String inventory = drinkTokens[2];
        
        BigDecimal big = new BigDecimal(drinkPrice);
        int inventory2 = Integer.parseInt(inventory);

        Drink drinkFromFile = new Drink(drinkName, big, inventory2);

        return drinkFromFile;
    }
 
    private void loadRoster2() throws VendingPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DRINKS_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingPersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }

        String currentLine;

        Drink currentDrink;

        drinks.clear();
        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
        
            currentDrink = unmarshallDrink(currentLine);


            drinks.add(currentDrink);
        }
        // close scanner
        scanner.close();
    }  
    
    private String marshallDrink(Drink aDrink){

        // Name
        String drinkAsText = aDrink.getName()+ DELIMITER;

        // Price
        drinkAsText += aDrink.getPrice()+ DELIMITER;

        // Inventory
        drinkAsText += aDrink.getInventory();


        return drinkAsText;
    }
    
    private void writeRoster2() throws VendingPersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(DRINKS_FILE));
        } catch (IOException e) {
            throw new VendingPersistenceException(
                    "Could not save drink data.", e);
        }

        String drinkAsText;
        List<Drink> drinkList = new ArrayList(drinks);
        for (Drink currentDrink : drinkList) {

            drinkAsText = marshallDrink(currentDrink);

            out.println(drinkAsText);

            out.flush();
        }
        // Clean up
        out.close();
    }
    
}
