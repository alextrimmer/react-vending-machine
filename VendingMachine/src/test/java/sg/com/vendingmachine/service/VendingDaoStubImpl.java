/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.com.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingDao;
import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.Drink;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Owner
 */
public class VendingDaoStubImpl implements VendingDao {

    public Drink onlyDrink;
    
    public VendingDaoStubImpl() {
        onlyDrink = new Drink("Coke", new BigDecimal("1.5"), 5);
    }
    
    public VendingDaoStubImpl(Drink testDrink) {
        this.onlyDrink = testDrink;
    }
    
    @Override
    public void populateMachine() throws VendingPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Drink getDrink(String name) throws VendingPersistenceException {
        if (name.equals(onlyDrink.getName())) {
            return onlyDrink;
        }
        else {
            return null;
        }
    }

    @Override
    public List<Drink> getAllDrinks() throws VendingPersistenceException {
        List<Drink> drinks = new ArrayList<Drink>();
        drinks.add(onlyDrink);
        return drinks;
    }

    @Override
    public BigDecimal convertToBig(float x) throws VendingPersistenceException {
        BigDecimal y = new BigDecimal(x);
        return y;
    }

    @Override
    public int getInventory(String name) throws VendingPersistenceException, ZeroInventoryException {
        if (name.equals(onlyDrink.getName())) {
            return onlyDrink.getInventory();
        }
        else {
            return -1;
        }
    }

    @Override
    public void buyDrink(String name) throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException {
        if (name.equals(onlyDrink.getName())) {
            int x = onlyDrink.getInventory() - 1;
            onlyDrink.setInventory(x);
        }
    }

    @Override
    public BigDecimal getPrice(String name) throws VendingPersistenceException {
        if (name.equals(onlyDrink.getName())) {
            return onlyDrink.getPrice();
        }
        else {
            return null;
        }
    }

    @Override
    public void addDrink(Drink name) throws VendingPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Drink oneLessInventory(String name) throws VendingPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
