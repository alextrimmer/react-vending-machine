/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Drink;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Owner
 */
public class VendingView {
    
    private UserIO io;
    
    public VendingView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection(List<Drink> x) {
        io.print("Choose a Drink");
        int numTimes = 0;
        for (Drink drink: x) {
            numTimes++;
            io.print(numTimes + ". " + drink.getName() + " ($" + drink.getPrice() + ")"); 
        }
        io.print(numTimes+1 + ". Exit");
        return io.readInt("Please select from the above choices.", 1, numTimes+1);
    }    
    
    // Asks user for money (between 0 and 5)
    
    public float enterMoney() {
        float x = io.readFloat("Please enter money, up to $5: ", 0, 5);
        return x;
    } 
    
    public void startMachine() {
        io.print("=== VENDING MACHINE ===");
    }
    
    public void displayExitBanner() {
        io.print("Thanks for using Alex's brilliant vending machine.");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print(errorMsg);
    }    
}
