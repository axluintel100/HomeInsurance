/**
 * This DAO class is used to for Property Information
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

import org.apache.log4j.Logger;

import com.cts.insurance.homequote.exception.HomequoteSystemException;
import com.cts.insurance.homequote.model.Property;
import com.cts.insurance.homequote.util.HomeInsuranceConstants;
import com.cts.insurance.homequote.util.SqlQueries;

public class PropertyDAO {
	private final static Logger LOG =  Logger.getLogger(PropertyDAO.class);
	
	public Property getProperty(final int quoteId) throws HomequoteSystemException
	{
		LOG.info("PropertyDAO.getProperty - starts");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Property property = null;
		try
		{
			final AbstractDAOFactory daoFactory = AbstractDAOFactory.getDaoFactory(HomeInsuranceConstants.ORACLE);
			conn = daoFactory.getConnection();
			//select * from Property where QUOTE_ID = ?
			//INSERT INTO Property (QUOTE_ID, MARKET_VALUE, YEAR_BUILT, SQUARE_FOOTAGE, DWELLING_STYLE, ROOF_MATERIAL,
			//GARAGE_TYPE, NUM_FULL_BATHS, NUM_HALF_BATHS, HAS_SWIMMING_POOL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			stmt = conn.prepareStatement(SqlQueries.GET_PROPERTY);
			stmt.setInt(1, quoteId);
			resultSet = stmt.executeQuery();
			property = new Property();
			while (resultSet.next()) {
				property.setQuoteId(resultSet.getInt(1));
				property.setMarketValue(resultSet.getInt(2));
				property.setYearBuilt(resultSet.getInt(3));
				property.setSquareFootage(resultSet.getInt(4));
				property.setDwellingStyle(resultSet.getDouble(5)); // Changed to Double to Fix Ticket 4
				property.setRoofMaterial(resultSet.getString(6));
				property.setGarageType(resultSet.getString(7));
				property.setNumFullBaths(resultSet.getInt(8));
				property.setNumHalfBaths(resultSet.getInt(9));
				property.setHasSwimmingPool(Boolean.parseBoolean(resultSet.getString(10)));
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
				LOG.error("Exception while trying to close Connection : " + e.getMessage() );
			}
		}
		LOG.info("PropertyDAO.getProperty - ends");
		return property;
	}
	/**
	 * @param property
	 */
	public void saveProperty(final Property property) throws HomequoteSystemException
	{
		LOG.info("PropertyDAO.saveProperty - starts");
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			final AbstractDAOFactory daoFactory = AbstractDAOFactory.getDaoFactory(HomeInsuranceConstants.ORACLE);
			conn = daoFactory.getConnection();
			//INSERT INTO Property (QUOTE_ID, MARKET_VALUE, YEAR_BUILT, SQUARE_FOOTAGE, DWELLING_STYLE, ROOF_MATERIAL,
			//GARAGE_TYPE, NUM_FULL_BATHS, NUM_HALF_BATHS, HAS_SWIMMING_POOL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			stmt = conn.prepareStatement(SqlQueries.SAVE_PROPERTY);
			stmt.setInt(1, property.getQuoteId());
			stmt.setInt(2, property.getMarketValue());
			stmt.setInt(3, property.getYearBuilt());
			stmt.setInt(4, property.getSquareFootage());
			stmt.setDouble(5, property.getDwellingStyle()); // Changed to Double to fix incident/problem
			stmt.setString(6, property.getRoofMaterial());
			stmt.setString(7, property.getGarageType());
			stmt.setInt(8, property.getNumFullBaths());
			stmt.setInt(9, property.getNumHalfBaths());
			stmt.setString(10, Boolean.toString(property.isHasSwimmingPool()));
			stmt.executeUpdate();
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
				stmt.close();
				conn.close();
			}
			catch (SQLException e)
			{
				LOG.error("Exception while trying to close Connection : " + e.getMessage() );
			}
		}
		LOG.info("PropertyDAO.saveProperty - ends");
	}
}
