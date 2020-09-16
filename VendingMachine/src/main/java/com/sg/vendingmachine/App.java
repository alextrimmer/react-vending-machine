/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingController;
import com.sg.vendingmachine.dao.VendingAuditDaoFileImpl;
import com.sg.vendingmachine.dao.VendingDaoFileImpl;
import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.ui.VendingView;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;
import sg.com.vendingmachine.service.VendingServiceLayerImpl;
import sg.com.vendingmachine.service.InsufficientFundsException;
import sg.com.vendingmachine.service.ZeroInventoryException;
import sg.com.vendingmachine.service.VendingServiceLayer;
import com.sg.vendingmachine.dao.VendingDao;
import com.sg.vendingmachine.dao.VendingAuditDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Owner
 */
public class App {
    public static void main(String[] args) throws VendingPersistenceException, ZeroInventoryException, InsufficientFundsException {
    // Instantiate the UserIO implementation
    // UserIO myIo = new UserIOConsoleImpl();
    // Instantiate the View and wire the UserIO implementation into it
    // VendingView myView = new VendingView(myIo);
    // Instantiate the DAO
    // VendingDao myDao = new VendingDaoFileImpl();
    // Instantiate the Audit DAO
    // VendingAuditDao myAuditDao = new VendingAuditDaoFileImpl();
    // Instantiate the Service Layer and wire the DAO and Audit DAO into it
    // VendingServiceLayer myService = new VendingServiceLayerImpl(myDao, myAuditDao);
    // Instantiate the Controller and wire the Service Layer into it
    // VendingController controller = new VendingController(myService, myView);
    // Kick off the Controller
    // controller.run();
    
    // XML Controller
    
        ApplicationContext ctx = 
           new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingController controller = 
           ctx.getBean("controller", VendingController.class);
        controller.run();    
    } 
}
