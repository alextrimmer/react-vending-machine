/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Drink;
import java.math.BigDecimal;
import java.util.List;
import sg.com.vendingmachine.service.InsufficientFundsException;
import sg.com.vendingmachine.service.ZeroInventoryException;

/**
 *
 * @author Owner
 */
public interface VendingDao {

    void populateMachine() throws VendingPersistenceException;

    public Drink getDrink(String name) throws VendingPersistenceException;
    
    public List<Drink> getAllDrinks() throws VendingPersistenceException;
    
    BigDecimal convertToBig(float x) throws VendingPersistenceException;
    
    public int getInventory(String name) throws VendingPersistenceException, ZeroInventoryException;
    
    public void buyDrink(String name) throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException;
    
    public BigDecimal getPrice(String name) throws VendingPersistenceException;
    
    public Drink oneLessInventory(String name) throws VendingPersistenceException;
    
    void addDrink(Drink name) throws VendingPersistenceException;

    
}
