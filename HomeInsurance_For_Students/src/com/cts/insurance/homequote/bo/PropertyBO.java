/**
 * This Business Object class is used to for Property Information
 * 
 * @author Cognizant
 * @contact Cognizant
 * @version 1.0
 */
package com.cts.insurance.homequote.bo;

import com.cts.insurance.homequote.dao.PropertyDAO;
import com.cts.insurance.homequote.exception.HomequoteBusinessException;
import com.cts.insurance.homequote.exception.HomequoteSystemException;
import com.cts.insurance.homequote.model.Property;

public class PropertyBO {

	/**
	 * @param quoteId
	 * @return
	 * @throws HomequoteBusinessException
	 */
	public Property getProperty(final int quoteId) throws HomequoteBusinessException{

		final PropertyDAO PropertyDAO = new PropertyDAO();
		//Fill code here
		
		Property userProperty = null;
		
		try {
			userProperty = PropertyDAO.getProperty(quoteId);
		} catch (HomequoteSystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		return userProperty; //return Object;
	}
	/**
	 * @param property
	 * @throws HomequoteBusinessException
	 */
	public void saveProperty(final Property property) throws HomequoteBusinessException{

		final PropertyDAO PropertyDAO = new PropertyDAO();
		try
		{	
			PropertyDAO.saveProperty(property);
		}
		catch(HomequoteSystemException e)
		{
			throw new HomequoteBusinessException(e.getMessage());
		}
	}
	
}
