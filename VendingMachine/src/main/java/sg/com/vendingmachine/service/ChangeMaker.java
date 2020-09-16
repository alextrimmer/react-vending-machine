/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.com.vendingmachine.service;

import com.sg.vendingmachine.dto.BigDecimalCoin;
import java.math.BigDecimal;

/**
 *
 * @author Owner
 */
public class ChangeMaker {
    
    // Fields
    
    private int numQuarters;
    private int numDimes;
    private int numNickels;
    private int numPennies;
    
    // Math for breaking a number down into parts
    
    public ChangeMaker(BigDecimal value) {
       while(value.compareTo(BigDecimalCoin.QUARTER.value()) >= 0) {
           numQuarters++;
           value = value.subtract(BigDecimalCoin.QUARTER.value());
       }
       while(value.compareTo(BigDecimalCoin.DIME.value()) >= 0) {
           numDimes++;
           value = value.subtract(BigDecimalCoin.DIME.value());
       }
       while(value.compareTo(BigDecimalCoin.NICKEL.value()) >= 0) {
           numNickels++;
           value = value.subtract(BigDecimalCoin.NICKEL.value());
       }
       while(value.compareTo(BigDecimalCoin.PENNY.value()) >= 0) {
           numPennies++;
           value = value.subtract(BigDecimalCoin.PENNY.value());
       }
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public int getNumPennies() {
        return numPennies;
    }

    @Override
    public String toString() {
        return "Your change is " + numQuarters + " quarters, " + numDimes + " dimes, " + numNickels + " nickels, " + numPennies + " pennies.";
    }    
}
