/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Drink;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Owner
 */
public class VendingDaoFileImplTest {
    
    VendingDao testDao;
    
    public VendingDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException, VendingPersistenceException {
        String testFile = "test.txt";
        new FileWriter(testFile);
        testDao = new VendingDaoFileImpl(testFile); 
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllDrinks() throws Exception {
        
        // Create our first drink
        Drink coke = new Drink("Coke", new BigDecimal("1.5"), 5);

        // Create our second drink
        Drink sprite = new Drink("Sprite", new BigDecimal("1.5"), 3);

        // Add both drinks to the DAO
        testDao.addDrink(coke);
        testDao.addDrink(sprite);

        // Retrieve the list of all drinks within the DAO
        List<Drink> allDrinks = testDao.getAllDrinks();

        // See if list has 2 drinks
        assertEquals(2, allDrinks.size(),"List of drinks should have 2 drinks.");
    }
    
    @Test
    public void findSpecificDrinkInFile() throws Exception {
        // Create a drink
        Drink coke = new Drink("Coke", new BigDecimal("1.5"), 5);
        // Add drink
        testDao.addDrink(coke);
        // Call list
        List<Drink> allDrinks = testDao.getAllDrinks();
        // Look to see if it contains specifically coke
        assertTrue(allDrinks.contains(coke));
    }
    
    @Test
    public void checkInventoryTest() throws Exception {
        // Create a drink
        Drink coke = new Drink("Coke", new BigDecimal("1.5"), 5);
        // Add drink
        testDao.addDrink(coke);
        // Get drink
        Drink x = testDao.getDrink("Coke");
        // Look to see if inventory is 5
        assertEquals(x.getInventory(), 5);
    }
    
    @Test
    public void checkPriceTest() throws Exception {
        // Create a drink
        Drink coke = new Drink("Coke", new BigDecimal("1.5"), 5);
        // Add drink
        testDao.addDrink(coke);
        // Get drink
        Drink x = testDao.getDrink("Coke");
        // Look to see if price is $1.50
        assertEquals(x.getPrice(), BigDecimal.valueOf(1.5));
    }
    
    @Test
    public void streamTester() throws Exception {
        // Create 4 drinks
        Drink coke = new Drink("Coke", new BigDecimal("1.5"), 5);
        Drink sprite = new Drink("Sprite", new BigDecimal("1.5"), 3);
        Drink fanta = new Drink("Fanta", new BigDecimal("2"), 0);
        Drink water = new Drink("Water", new BigDecimal("1"), 10);
        // Add drinks
        testDao.addDrink(coke);
        testDao.addDrink(sprite);
        testDao.addDrink(fanta);
        testDao.addDrink(water);
        // Create list
        List<Drink> allDrinks = testDao.getAllDrinks();
        // Stream list to filter out drink without an E in its name
        List<Drink> onlyEs = allDrinks.stream().filter((d) -> d.getName().contains("e")).collect(Collectors.toList());
        // Three elements should remain
        assertEquals(3, onlyEs.size());
    }
    
}
