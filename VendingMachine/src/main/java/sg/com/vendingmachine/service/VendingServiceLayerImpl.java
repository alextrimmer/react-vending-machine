/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.com.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.Drink;
import java.math.BigDecimal;
import com.sg.vendingmachine.dao.VendingDao;
import com.sg.vendingmachine.dao.VendingAuditDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Owner
 */
public class VendingServiceLayerImpl implements VendingServiceLayer {

    BigDecimal total;
    VendingDao dao;
    private VendingAuditDao auditDao;
    List<Drink> drinks = new ArrayList<Drink>();
   
    public VendingServiceLayerImpl(VendingDao dao, VendingAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public BigDecimal convertToBig(float x) throws VendingPersistenceException {
        return dao.convertToBig(x);
    }

    @Override
    public Drink getDrink(String name) throws VendingPersistenceException {
        return dao.getDrink(name);
    }
    
    @Override
    public List<Drink> getAllDrinks() throws VendingPersistenceException {
        return dao.getAllDrinks();
    }
    
    @Override
    public int getInventory(String name) throws VendingPersistenceException, ZeroInventoryException {
        return dao.getInventory(name);
    }  
    
    @Override
    public BigDecimal getPrice(String name) throws VendingPersistenceException {
        return dao.getPrice(name);
    }
    
    @Override
    public void setTotal(BigDecimal x) {
        total = x.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    @Override
    public BigDecimal getTotal() {
        return total;
    }
    
    // The big one: subtracts total, then adds it back if something goes wrong.
    // First checks inventory, then if there are enough funds.
    
    @Override
    public void buyDrink(String name) throws VendingPersistenceException, InsufficientFundsException, ZeroInventoryException {
        total = total.subtract(dao.getPrice(name));
        try {
            if (getInventory(name) == 0) {
                total = total.add(dao.getPrice(name));
                ChangeMaker cm = new ChangeMaker(total);
                System.out.println(cm);
                total = BigDecimal.ZERO;
                throw new ZeroInventoryException
                    ("Sorry, this drink is unavailable.");
            }
            if (total.compareTo(BigDecimal.ZERO) >= 0) {
                dao.buyDrink(name);
                System.out.println("Ding! Drink successfully purchased!");
                System.out.println("Change will be $" + total);
                ChangeMaker cm = new ChangeMaker(total);
                System.out.println(cm);
                total = BigDecimal.ZERO;
                auditDao.writeAuditEntry(name + " was purchased.");
            } else {
                total = total.add(dao.getPrice(name));
                ChangeMaker cm = new ChangeMaker(total);
                System.out.println(cm);
                total = BigDecimal.ZERO;
                throw new InsufficientFundsException("Not enough money.");
            } 
        } catch (InsufficientFundsException e) {
            throw new InsufficientFundsException("Insufficient funds. Please enter more money.", e);
        } catch (ZeroInventoryException f) {
            throw new ZeroInventoryException("Item is no longer available.", f);
        }
    }          
    
    @Override
    public void populateMachine() throws VendingPersistenceException {
        dao.populateMachine();
    } 
    
    
    public void displayTotal() {
        System.out.println("Inserted money is $" + total);
    }
    
    // Excessive, but left in to reference
    
    public String getChange(BigDecimal change) {
        ChangeMaker cm = new ChangeMaker(change);
        String coins = cm.toString();
        return coins;
    }
    
}
