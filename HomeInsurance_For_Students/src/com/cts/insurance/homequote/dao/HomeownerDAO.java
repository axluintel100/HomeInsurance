/**
 * This DAO class is used to for Homeowner Information
 *
 * @author Cognizant
 * @contact Cognizant
 * @version 1.0
 */
package com.cts.insurance.homequote.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cts.insurance.homequote.exception.HomequoteSystemException;
import com.cts.insurance.homequote.model.Homeowner;
import com.cts.insurance.homequote.util.HomeInsuranceConstants;
import com.cts.insurance.homequote.util.SqlQueries;

public class HomeownerDAO {
	
	private final static Logger LOG = Logger.getLogger(HomeownerDAO.class);

	/**
	 * @param homeowner
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	
	public void saveHomeowner(final Homeowner homeowner) throws HomequoteSystemException, SQLException
	{
		LOG.info("HomeownerDAO.saveHomeowner - starts");
		
		Connection conn = null;
		PreparedStatement state = null;
		
		
		try {
			
			AbstractDAOFactory daoFactory = AbstractDAOFactory.getDaoFactory(HomeInsuranceConstants.ORACLE);
			conn = daoFactory.getConnection();
			
			//This is the Query Located in the SqlQueries.java class. 
			//This is located in the util package 
			//"INSERT INTO HomeownerInfo (QUOTE_ID, FIRST_NAME, LAST_NAME, " +
			//"DOB, IS_RETIRED, SSN, EMAIL_ADDRESS) VALUES (?, ?, ?, ?, ?, ?, ?)"
			
			state = conn.prepareStatement(SqlQueries.SAVE_HOMEOWNER);
			state.setInt(1, homeowner.getQuoteId());
			state.setString(2, homeowner.getFirstName());
			state.setString(3, homeowner.getLastName());
			state.setString(4, homeowner.getDob());
			state.setString(5, homeowner.getIsRetired());
			state.setString(6, homeowner.getSsn());
			state.setString(7, homeowner.getEmailAddress());
			state.executeUpdate();
			state.close();
			
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new HomequoteSystemException(e.getMessage());		
		}
		catch (Exception e) 
		{
			throw new HomequoteSystemException(e.getMessage());		
		}
		finally 
		{
			try
			{
				state.close();
				conn.close();
			}
			catch (SQLException e)
			{
				LOG.error("Error while trying to close Connection: " + e.getMessage());
			}
		}
		
		
		LOG.info("HomeownerDAO.saveHomeowner - ends");
	}
	/**
	 * @param homeowner
	 */
	public Homeowner getHomeowner(final int quoteId) throws HomequoteSystemException
	{
		LOG.info("HomeownerDAO.getHomeowner - starts");
		Connection conn = null;
		Homeowner homeowner = null;
		ResultSet resultSet = null;
		PreparedStatement stmt = null;
		
		
		try {
			final AbstractDAOFactory daoFactory = AbstractDAOFactory.getDaoFactory(HomeInsuranceConstants.ORACLE);
			conn = daoFactory.getConnection();
		
			
			stmt = conn.prepareStatement(SqlQueries.GET_HOMEOWNER);
			stmt.setInt(1, quoteId);
			resultSet = stmt.executeQuery();
			
			if(resultSet.next()) 
			{
				homeowner = new Homeowner();
				homeowner.setQuoteId(resultSet.getInt(1));
				homeowner.setFirstName(resultSet.getString(2));
				homeowner.setLastName(resultSet.getString(3));
				homeowner.setDob(resultSet.getString(4));
				homeowner.setIsRetired(resultSet.getString(5));
				homeowner.setSsn(resultSet.getString(6));
				homeowner.setEmailAddress(resultSet.getString(7));
			}
			
			
		} 
		
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			throw new HomequoteSystemException(e.getMessage());
		} 
		
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			throw new HomequoteSystemException(e.getMessage());
		} 
	
		finally
		{
			try
			{
				resultSet.close();
				stmt.close();
				conn.close();
			}
		
			catch (SQLException e)
			{
				LOG.error("Exception while trying to close Connection: " + e.getMessage() );
			}
		}
			
		LOG.info("HomeownerDAO.getHomeowner - ends");
		return homeowner; //return Object
	
	}

}
