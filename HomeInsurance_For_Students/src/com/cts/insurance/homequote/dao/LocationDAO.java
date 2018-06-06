/**
 * This DAO class is used to for Location Information
 * 
 * @author Cognizant
 * @contact Cognizant
 * @version 1.0
 */
package com.cts.insurance.homequote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cts.insurance.homequote.exception.HomequoteSystemException;
import com.cts.insurance.homequote.model.Location;
import com.cts.insurance.homequote.util.HomeInsuranceConstants;
import com.cts.insurance.homequote.util.SqlQueries;

public class LocationDAO {
	private final static Logger LOGGER = Logger.getLogger(LocationDAO.class);
	/**
	 * @param location
	 */
	public int saveLocation(final Location location) throws HomequoteSystemException
	{
		LOGGER.info("LocationDAO.saveLocation - starts");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		int quoteId = 0;
		try
		{
			final AbstractDAOFactory daoFactory = AbstractDAOFactory.getDaoFactory(HomeInsuranceConstants.ORACLE);
			conn = daoFactory.getConnection();
			

			
			stmt = conn.prepareStatement(SqlQueries.SAVE_LOCATION);
			stmt.setString(1, location.getResidenceType());
			stmt.setString(2, location.getAddressLine1());
			stmt.setString(3, location.getAddressLine2());
			stmt.setString(4, location.getCity());
			stmt.setString(5, location.getState());
			stmt.setString(6, location.getZip());
			stmt.setString(7, location.getResidenceUse());
			stmt.setString(8, location.getUserName());
			stmt.executeUpdate();
			

			stmt = conn.prepareStatement(SqlQueries.GET_QUOTE_ID);
			stmt.setString(1, location.getResidenceType());
			stmt.setString(2, location.getAddressLine1());
			stmt.setString(3, location.getAddressLine2());
			stmt.setString(4, location.getCity());
			stmt.setString(5, location.getState());
			stmt.setString(6, location.getZip());
			stmt.setString(7, location.getResidenceUse());
			stmt.setString(8, location.getUserName());
			stmt.executeQuery();
			resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				quoteId = resultSet.getInt(1);
				
			}
		}
		catch (SQLException e)
		{
			throw new HomequoteSystemException(e.getMessage());
		}
		catch(Exception e)
		{
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
				LOGGER.error("Exception while trying to close Connection : " + e.getMessage() );
			}
		}
		LOGGER.info("LocationDAO.saveLocation - ends");
		return quoteId;
	}

	/**
	 * @return
	 */
	public Location getLocation(final int quoteId) throws HomequoteSystemException {
		LOGGER.info("LocationDAO.getLocation - starts");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Location loc = null;
		try
		{
			final AbstractDAOFactory daoFactory = AbstractDAOFactory.getDaoFactory(HomeInsuranceConstants.ORACLE);
			conn = daoFactory.getConnection();
			//select * from Location where QUOTE_ID = ?
			stmt = conn.prepareStatement(SqlQueries.GET_LOCATION);
			stmt.setInt(1, quoteId);
			resultSet = stmt.executeQuery();
			loc = new Location();
			while (resultSet.next()) {
				loc.setQuoteId(resultSet.getInt(1));//QUOTE_ID
				loc.setResidenceType(resultSet.getString(2));//RESIDENCE_TYPE
				loc.setAddressLine1(resultSet.getString(3));//ADDRESS_LINE_1
				loc.setAddressLine2(resultSet.getString(4));//ADDRESS_LINE_2
				loc.setCity(resultSet.getString(5));//CITY
				loc.setState(resultSet.getString(6));//STATE
				loc.setZip(resultSet.getString(7));//ZIP
				loc.setResidenceUse(resultSet.getString(8));//RESIDENCE_USE
				loc.setUserName(resultSet.getString(9));//USER_NAME
			}
			resultSet.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			throw new HomequoteSystemException(e.getMessage());
		}
		catch(Exception e)
		{
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
				LOGGER.error("Exception while trying to close Connection : " + e.getMessage() );
			}
		}
		LOGGER.info("LocationDAO.getLocation - ends");
		return loc;
	}

	/**
	 * @param userName
	 * @return
	 */
	public List<Location> getQuoteIds(final String userName) throws HomequoteSystemException
	{
		LOGGER.info("LocationDAO.getQuoteIds - starts");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		ArrayList<Location> locList = null;
		try
		{
			final AbstractDAOFactory daoFactory = AbstractDAOFactory.getDaoFactory(HomeInsuranceConstants.ORACLE);
			conn = daoFactory.getConnection();
			//select QUOTE_ID from Location where USER_NAME = ?
			stmt = conn.prepareStatement(SqlQueries.GET_LOCS_FOR_USER);
			stmt.setString(1, userName);
			resultSet = stmt.executeQuery();
			locList = new ArrayList<Location>();
			Location loc = null;
			while (resultSet.next()) {
				loc = new Location();
				loc.setQuoteId(resultSet.getInt(1));//QUOTE_ID
				loc.setResidenceType(resultSet.getString(2));//RESIDENCE_TYPE
				loc.setAddressLine1(resultSet.getString(3));//ADDRESS_LINE_1
				loc.setAddressLine2(resultSet.getString(4));//ADDRESS_LINE_2
				loc.setCity(resultSet.getString(5));//CITY
				loc.setState(resultSet.getString(6));//STATE
				loc.setZip(resultSet.getString(7));//ZIP
				loc.setResidenceUse(resultSet.getString(8));//RESIDENCE_USE
				loc.setUserName(resultSet.getString(9));//USER_NAME
				locList.add(loc);
			}
		}
		catch (SQLException e)
		{
			throw new HomequoteSystemException(e.getMessage());
		}
		catch(Exception e)
		{
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
				LOGGER.error("Exception while trying to close Connection : " + e.getMessage() );
			}
		}
		LOGGER.info("LocationDAO.getQuoteIds - ends");
		return locList;
	}
}
