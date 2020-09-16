/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.com.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingAuditDao;
import com.sg.vendingmachine.dao.VendingPersistenceException;

/**
 *
 * @author Owner
 */
public class VendingAuditDaoStubImpl implements VendingAuditDao
{
    @Override
    public void writeAuditEntry(String entry) throws VendingPersistenceException {
        //do nothing . . .
    }    
}
