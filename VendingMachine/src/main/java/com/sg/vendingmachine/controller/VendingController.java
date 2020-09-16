/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dao.VendingDaoFileImpl;
import com.sg.vendingmachine.ui.VendingView;
import java.math.BigDecimal;
import sg.com.vendingmachine.service.InsufficientFundsException;
import sg.com.vendingmachine.service.ZeroInventoryException;
import sg.com.vendingmachine.service.VendingServiceLayer;
import com.sg.vendingmachine.dao.VendingDao;
import com.sg.vendingmachine.dto.Drink;
import java.util.ArrayList;
import java.util.List;
import sg.com.vendingmachine.service.ChangeMaker;

/**
 *
 * @author Owner
 */
public class VendingController {

    // Initialize view and service layers + instantiate controller
    
    private VendingView view;
    private VendingServiceLayer service;
    List<Drink> drinks = new ArrayList<Drink>();
    
    public VendingController(VendingServiceLayer service, VendingView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws VendingPersistenceException, InsufficientFundsException, ZeroInventoryException {
        boolean keepGoing = true;
        int menuSelection = 0;
        
        // Populate machine from drinks.txt
        
        try {
        service.populateMachine();
        } catch (VendingPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        
        // Open menu
        
        try {

        while (keepGoing) {
            insertMoney();
            displayTotal();
            List<Drink> drinks = service.getAllDrinks();
            menuSelection = getMenuSelection();
            if (menuSelection <= drinks.size()) {
                buyDrink(drinks.get(menuSelection-1).getName());
            } else {
                keepGoing = false;
            }
     
            /*
            switch (menuSelection) {
                case 1:
                    buyDrink(drinks.get(0).getName());
                    break;
                case 2:
                    buyDrink(drinks.get(1).getName());
                    break;
                case 3:
                    buyDrink(drinks.get(2).getName());
                    break;
                case 4:
                    buyDrink(drinks.get(3).getName());
                    break;
                case 5:
                    buyDrink(drinks.get(4).getName());
                    break;
                case 6:
                    keepGoing = false;
                default:
                    unknownCommand();
            }
*/
        }
        exitMessage();
        } catch (VendingPersistenceException | ZeroInventoryException | InsufficientFundsException e) {
            view.displayErrorMessage(e.getMessage());
            keepGoing = true;
        }
    }

    private int getMenuSelection() throws VendingPersistenceException {
        List<Drink> drinks = service.getAllDrinks();
        return view.printMenuAndGetSelection(drinks);
    }
    
    // Grab money from user
    
    private void insertMoney() throws VendingPersistenceException {
        view.startMachine();
        float x = view.enterMoney();
        BigDecimal big = service.convertToBig(x);
        service.setTotal(big);
    }
    
    // Purchase a specific drink
    
    private void buyDrink(String name) throws VendingPersistenceException, InsufficientFundsException, ZeroInventoryException {
        try {
            service.buyDrink(name);        
        }
        catch (InsufficientFundsException | ZeroInventoryException e) {
            view.displayErrorMessage(e.getMessage());
        } 
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    // Exits and spits back your change
    
    private void exitMessage() throws VendingPersistenceException {
        view.displayExitBanner();
        BigDecimal x = service.getTotal();
        ChangeMaker cm = new ChangeMaker(x);
        System.out.println(cm);
    }
    
    // Shows total cash
    
    private void displayTotal() throws VendingPersistenceException {
        service.displayTotal();
    } 
}
