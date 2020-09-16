/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.com.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingAuditDao;
import com.sg.vendingmachine.dao.VendingDao;
import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.Drink;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Owner
 */
public class VendingServiceLayerImplTest {
    
    private VendingServiceLayer service;

    public VendingServiceLayerImplTest() {
    //    VendingDao dao = new VendingDaoStubImpl();
    //    VendingAuditDao auditDao = new VendingAuditDaoStubImpl();
    //
    //    service = new VendingServiceLayerImpl(dao, auditDao);
    
    ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("applicationContext.xml");
    service = 
        ctx.getBean("serviceLayer", VendingServiceLayer.class);    
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // See if inventory is correct
    
    @Test
    public void testInventory() throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException {
        assertEquals(5, service.getInventory("Coke"));
    }
    
    // Checks to see if the drink is properly inserted
    
    @Test
    public void getDrink() throws VendingPersistenceException {
        Drink testClone = new Drink("Coke", new BigDecimal("1.5"), 5);
        Drink shouldBeCoke = service.getDrink("Coke");
        assertEquals(testClone, shouldBeCoke);
    }
    
    // Checks if price is correct
    
    @Test
    public void getPrice() throws VendingPersistenceException {
        assertEquals(BigDecimal.valueOf(1.5), service.getPrice("Coke"));
    }
    
    // Checks if numbers are properly converting to BigDecimal
    
    @Test 
    public void convertTest() throws VendingPersistenceException {
        float x = 1;
        BigDecimal y = service.convertToBig(x);
        assertEquals(y, BigDecimal.valueOf(1));
    }
    
    // Checks if inventory decreases when an item is bought
    
    @Test
    public void buyOne() throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException {
        service.setTotal(BigDecimal.valueOf(3));
        service.buyDrink("Coke");
        assertEquals(4, service.getInventory("Coke"));
    }
    
    // Checks to see if exception is thrown when there aren't enough funds
    
    @Test
    public void notEnoughDough() throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException {
        service.setTotal(BigDecimal.valueOf(1));
        assertThrows(InsufficientFundsException.class, () -> service.buyDrink("Coke"));
    }
    
    // Checks drink is bought if the inserted money is exactly the price of the drink
    
    @Test
    public void rightOnTheMoney() throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException {
        service.setTotal(BigDecimal.valueOf(1.5));
        service.buyDrink("Coke");
        assertEquals(4, service.getInventory("Coke"));        
    }
    
    // Checks to see if getTotal returns a correct BigDecimal total value
    
    @Test
    public void setsCorrectTotal() throws VendingPersistenceException {
        service.setTotal(BigDecimal.valueOf(5));
        BigDecimal bd = new BigDecimal("5.00");
        assertEquals(bd, service.getTotal());
    }
    
    // Checks if the total is zero after change is returned
    
    @Test
    public void totalShouldBeZeroAfterChangeReturned() throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException {
        service.setTotal(BigDecimal.valueOf(3));
        service.buyDrink("Coke");
        assertEquals(BigDecimal.ZERO, service.getTotal());
    }
    
}
