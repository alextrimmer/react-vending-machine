/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.com.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.Drink;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Owner
 */
public interface VendingServiceLayer {
    
    BigDecimal convertToBig(float x) throws VendingPersistenceException;
    
    public Drink getDrink(String name) throws VendingPersistenceException;
    
    public List<Drink> getAllDrinks() throws VendingPersistenceException;
    
    public int getInventory(String name) throws VendingPersistenceException, ZeroInventoryException;
    
    public void setTotal(BigDecimal x) throws VendingPersistenceException;
    
    void displayTotal() throws VendingPersistenceException;
    
    void buyDrink(String name) throws VendingPersistenceException, InsufficientFundsException, ZeroInventoryException;
    
    void populateMachine() throws VendingPersistenceException;
    
    BigDecimal getTotal() throws VendingPersistenceException;

    public BigDecimal getPrice(String name) throws VendingPersistenceException;

 
}
