/**
 * This Business Object class is used to for Homeowner Information
 * 
 * @author Cognizant
 * @contact Cognizant
 * @version 1.0
 */
package com.cts.insurance.homequote.bo;

import java.sql.SQLException;

import com.cts.insurance.homequote.dao.HomeownerDAO;
import com.cts.insurance.homequote.exception.HomequoteBusinessException;
import com.cts.insurance.homequote.exception.HomequoteSystemException;
import com.cts.insurance.homequote.model.Homeowner;

public class HomeownerBO {

	/**
	 * @param quoteId
	 * @param lastName
	 * @param dob
	 * @param emailAddress
	 * @return
	 * @throws HomequoteBusinessException
	 * @throws HomequoteSystemException 
	 */
	public Homeowner getHomeownerInfo(final int quoteId) throws HomequoteBusinessException, HomequoteSystemException{

		Homeowner Homeowner1 = null; //MA
		
		final HomeownerDAO HomeownerDAO = new HomeownerDAO();
 
		Homeowner1 = HomeownerDAO.getHomeowner(quoteId); //MA
		
		return Homeowner1; //return Object
	}
	/**
	 * @param homeowner
	 * @throws HomequoteBusinessException
	 */
	public void saveHomeownerInfo(final Homeowner homeowner) throws HomequoteBusinessException{

		final HomeownerDAO HomeownerDAO = new HomeownerDAO();
		
		try {
			try {
				HomeownerDAO.saveHomeowner(homeowner);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		} catch (HomequoteSystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}//MA
		
	}
}
