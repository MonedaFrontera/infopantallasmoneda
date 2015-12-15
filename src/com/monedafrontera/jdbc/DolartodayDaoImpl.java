/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.monedafrontera.jdbc;

import com.monedafrontera.dao.*;
import com.monedafrontera.factory.*;
import com.monedafrontera.dto.*;
import com.monedafrontera.exceptions.*;
import java.sql.Connection;
import java.util.Collection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class DolartodayDaoImpl extends AbstractDAO implements DolartodayDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT epoch, usdtransferencia, usdefectivo, usdpromedio, usdpromedioreal, usdsicad2, usddolartoday, eurtransferencia, eurefectivo, eurpromedio, eurpromedioreal, eurdolartoday, bscolcompra, bscolventa, goldrate, usdvefrate, usdcolrate, usdratetrm, usdtrmfactor, eurusdrate, usdsicad1, usdcencoex FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( epoch, usdtransferencia, usdefectivo, usdpromedio, usdpromedioreal, usdsicad2, usddolartoday, eurtransferencia, eurefectivo, eurpromedio, eurpromedioreal, eurdolartoday, bscolcompra, bscolventa, goldrate, usdvefrate, usdcolrate, usdratetrm, usdtrmfactor, eurusdrate, usdsicad1, usdcencoex ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET epoch = ?, usdtransferencia = ?, usdefectivo = ?, usdpromedio = ?, usdpromedioreal = ?, usdsicad2 = ?, usddolartoday = ?, eurtransferencia = ?, eurefectivo = ?, eurpromedio = ?, eurpromedioreal = ?, eurdolartoday = ?, bscolcompra = ?, bscolventa = ?, goldrate = ?, usdvefrate = ?, usdcolrate = ?, usdratetrm = ?, usdtrmfactor = ?, eurusdrate = ?, usdsicad1 = ?, usdcencoex = ? WHERE epoch = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE epoch = ?";

	/** 
	 * Index of column epoch
	 */
	protected static final int COLUMN_EPOCH = 1;

	/** 
	 * Index of column usdtransferencia
	 */
	protected static final int COLUMN_USDTRANSFERENCIA = 2;

	/** 
	 * Index of column usdefectivo
	 */
	protected static final int COLUMN_USDEFECTIVO = 3;

	/** 
	 * Index of column usdpromedio
	 */
	protected static final int COLUMN_USDPROMEDIO = 4;

	/** 
	 * Index of column usdpromedioreal
	 */
	protected static final int COLUMN_USDPROMEDIOREAL = 5;

	/** 
	 * Index of column usdsicad2
	 */
	protected static final int COLUMN_USDSICAD2 = 6;

	/** 
	 * Index of column usddolartoday
	 */
	protected static final int COLUMN_USDDOLARTODAY = 7;

	/** 
	 * Index of column eurtransferencia
	 */
	protected static final int COLUMN_EURTRANSFERENCIA = 8;

	/** 
	 * Index of column eurefectivo
	 */
	protected static final int COLUMN_EUREFECTIVO = 9;

	/** 
	 * Index of column eurpromedio
	 */
	protected static final int COLUMN_EURPROMEDIO = 10;

	/** 
	 * Index of column eurpromedioreal
	 */
	protected static final int COLUMN_EURPROMEDIOREAL = 11;

	/** 
	 * Index of column eurdolartoday
	 */
	protected static final int COLUMN_EURDOLARTODAY = 12;

	/** 
	 * Index of column bscolcompra
	 */
	protected static final int COLUMN_BSCOLCOMPRA = 13;

	/** 
	 * Index of column bscolventa
	 */
	protected static final int COLUMN_BSCOLVENTA = 14;

	/** 
	 * Index of column goldrate
	 */
	protected static final int COLUMN_GOLDRATE = 15;

	/** 
	 * Index of column usdvefrate
	 */
	protected static final int COLUMN_USDVEFRATE = 16;

	/** 
	 * Index of column usdcolrate
	 */
	protected static final int COLUMN_USDCOLRATE = 17;

	/** 
	 * Index of column usdratetrm
	 */
	protected static final int COLUMN_USDRATETRM = 18;

	/** 
	 * Index of column usdtrmfactor
	 */
	protected static final int COLUMN_USDTRMFACTOR = 19;

	/** 
	 * Index of column eurusdrate
	 */
	protected static final int COLUMN_EURUSDRATE = 20;

	/** 
	 * Index of column usdsicad1
	 */
	protected static final int COLUMN_USDSICAD1 = 21;

	/** 
	 * Index of column usdcencoex
	 */
	protected static final int COLUMN_USDCENCOEX = 22;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 22;

	/** 
	 * Index of primary-key column epoch
	 */
	protected static final int PK_COLUMN_EPOCH = 1;

	/** 
	 * Inserts a new row in the dolartoday table.
	 */
	public DolartodayPk insert(Dolartoday dto) throws DolartodayDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			stmt = conn.prepareStatement( SQL_INSERT );
			int index = 1;
			stmt.setLong( index++, dto.getEpoch() );
			if (dto.isUsdtransferenciaNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdtransferencia() );
			}
		
			if (dto.isUsdefectivoNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdefectivo() );
			}
		
			if (dto.isUsdpromedioNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdpromedio() );
			}
		
			if (dto.isUsdpromediorealNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdpromedioreal() );
			}
		
			if (dto.isUsdsicad2Null()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdsicad2() );
			}
		
			if (dto.isUsddolartodayNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsddolartoday() );
			}
		
			if (dto.isEurtransferenciaNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurtransferencia() );
			}
		
			if (dto.isEurefectivoNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurefectivo() );
			}
		
			if (dto.isEurpromedioNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurpromedio() );
			}
		
			if (dto.isEurpromediorealNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurpromedioreal() );
			}
		
			if (dto.isEurdolartodayNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurdolartoday() );
			}
		
			if (dto.isBscolcompraNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getBscolcompra() );
			}
		
			if (dto.isBscolventaNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getBscolventa() );
			}
		
			if (dto.isGoldrateNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getGoldrate() );
			}
		
			if (dto.isUsdvefrateNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdvefrate() );
			}
		
			if (dto.isUsdcolrateNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdcolrate() );
			}
		
			if (dto.isUsdratetrmNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdratetrm() );
			}
		
			if (dto.isUsdtrmfactorNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdtrmfactor() );
			}
		
			if (dto.isEurusdrateNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurusdrate() );
			}
		
			if (dto.isUsdsicad1Null()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdsicad1() );
			}
		
			if (dto.isUsdcencoexNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdcencoex() );
			}
		
			//System.out.println( "Executing " + SQL_INSERT + " with DTO: " + dto );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			//System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new DolartodayDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the dolartoday table.
	 */
	public void update(DolartodayPk pk, Dolartoday dto) throws DolartodayDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			System.out.println( "Executing " + SQL_UPDATE + " with DTO: " + dto );
			stmt = conn.prepareStatement( SQL_UPDATE );
			int index=1;
			stmt.setLong( index++, dto.getEpoch() );
			if (dto.isUsdtransferenciaNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdtransferencia() );
			}
		
			if (dto.isUsdefectivoNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdefectivo() );
			}
		
			if (dto.isUsdpromedioNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdpromedio() );
			}
		
			if (dto.isUsdpromediorealNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdpromedioreal() );
			}
		
			if (dto.isUsdsicad2Null()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdsicad2() );
			}
		
			if (dto.isUsddolartodayNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsddolartoday() );
			}
		
			if (dto.isEurtransferenciaNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurtransferencia() );
			}
		
			if (dto.isEurefectivoNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurefectivo() );
			}
		
			if (dto.isEurpromedioNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurpromedio() );
			}
		
			if (dto.isEurpromediorealNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurpromedioreal() );
			}
		
			if (dto.isEurdolartodayNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurdolartoday() );
			}
		
			if (dto.isBscolcompraNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getBscolcompra() );
			}
		
			if (dto.isBscolventaNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getBscolventa() );
			}
		
			if (dto.isGoldrateNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getGoldrate() );
			}
		
			if (dto.isUsdvefrateNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdvefrate() );
			}
		
			if (dto.isUsdcolrateNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdcolrate() );
			}
		
			if (dto.isUsdratetrmNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdratetrm() );
			}
		
			if (dto.isUsdtrmfactorNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdtrmfactor() );
			}
		
			if (dto.isEurusdrateNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getEurusdrate() );
			}
		
			if (dto.isUsdsicad1Null()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdsicad1() );
			}
		
			if (dto.isUsdcencoexNull()) {
				stmt.setNull( index++, java.sql.Types.FLOAT );
			} else {
				stmt.setFloat( index++, dto.getUsdcencoex() );
			}
		
			stmt.setLong( 23, pk.getEpoch() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			//System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new DolartodayDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the dolartoday table.
	 */
	public void delete(DolartodayPk pk) throws DolartodayDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			//System.out.println( "Executing " + SQL_DELETE + " with PK: " + pk );
			stmt = conn.prepareStatement( SQL_DELETE );
			stmt.setLong( 1, pk.getEpoch() );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			//System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new DolartodayDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the dolartoday table that matches the specified primary-key value.
	 */
	public Dolartoday findByPrimaryKey(DolartodayPk pk) throws DolartodayDaoException
	{
		return findByPrimaryKey( pk.getEpoch() );
	}

	/** 
	 * Returns all rows from the dolartoday table that match the criteria 'epoch = :epoch'.
	 */
	public Dolartoday findByPrimaryKey(long epoch) throws DolartodayDaoException
	{
		Dolartoday ret[] = findByDynamicSelect( SQL_SELECT + " WHERE epoch = ?", new Object[] {  new Long(epoch) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the dolartoday table that match the criteria ''.
	 */
	public Dolartoday[] findAll() throws DolartodayDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY epoch", null );
	}

	/** 
	 * Returns all rows from the dolartoday table that match the criteria 'epoch = :epoch'.
	 */
	public Dolartoday[] findWhereEpochEquals(long epoch) throws DolartodayDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE epoch = ? ORDER BY epoch", new Object[] {  new Long(epoch) } );
	}

	/**
	 * Method 'DolartodayDaoImpl'
	 * 
	 */
	public DolartodayDaoImpl()
	{
	}

	/**
	 * Method 'DolartodayDaoImpl'
	 * 
	 * @param userConn
	 */
	public DolartodayDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows)
	{
		this.maxRows = maxRows;
	}

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "public.dolartoday";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected Dolartoday fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			Dolartoday dto = new Dolartoday();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected Dolartoday[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			Dolartoday dto = new Dolartoday();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		Dolartoday ret[] = new Dolartoday[ resultList.size() ];
		resultList.toArray( ret );
		
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(Dolartoday dto, ResultSet rs) throws SQLException
	{
		dto.setEpoch( rs.getLong( COLUMN_EPOCH ) );
		dto.setUsdtransferencia( rs.getFloat( COLUMN_USDTRANSFERENCIA ) );
		if (rs.wasNull()) {
			dto.setUsdtransferenciaNull( true );
		}
		
		dto.setUsdefectivo( rs.getFloat( COLUMN_USDEFECTIVO ) );
		if (rs.wasNull()) {
			dto.setUsdefectivoNull( true );
		}
		
		dto.setUsdpromedio( rs.getFloat( COLUMN_USDPROMEDIO ) );
		if (rs.wasNull()) {
			dto.setUsdpromedioNull( true );
		}
		
		dto.setUsdpromedioreal( rs.getFloat( COLUMN_USDPROMEDIOREAL ) );
		if (rs.wasNull()) {
			dto.setUsdpromediorealNull( true );
		}
		
		dto.setUsdsicad2( rs.getFloat( COLUMN_USDSICAD2 ) );
		if (rs.wasNull()) {
			dto.setUsdsicad2Null( true );
		}
		
		dto.setUsddolartoday( rs.getFloat( COLUMN_USDDOLARTODAY ) );
		if (rs.wasNull()) {
			dto.setUsddolartodayNull( true );
		}
		
		dto.setEurtransferencia( rs.getFloat( COLUMN_EURTRANSFERENCIA ) );
		if (rs.wasNull()) {
			dto.setEurtransferenciaNull( true );
		}
		
		dto.setEurefectivo( rs.getFloat( COLUMN_EUREFECTIVO ) );
		if (rs.wasNull()) {
			dto.setEurefectivoNull( true );
		}
		
		dto.setEurpromedio( rs.getFloat( COLUMN_EURPROMEDIO ) );
		if (rs.wasNull()) {
			dto.setEurpromedioNull( true );
		}
		
		dto.setEurpromedioreal( rs.getFloat( COLUMN_EURPROMEDIOREAL ) );
		if (rs.wasNull()) {
			dto.setEurpromediorealNull( true );
		}
		
		dto.setEurdolartoday( rs.getFloat( COLUMN_EURDOLARTODAY ) );
		if (rs.wasNull()) {
			dto.setEurdolartodayNull( true );
		}
		
		dto.setBscolcompra( rs.getFloat( COLUMN_BSCOLCOMPRA ) );
		if (rs.wasNull()) {
			dto.setBscolcompraNull( true );
		}
		
		dto.setBscolventa( rs.getFloat( COLUMN_BSCOLVENTA ) );
		if (rs.wasNull()) {
			dto.setBscolventaNull( true );
		}
		
		dto.setGoldrate( rs.getFloat( COLUMN_GOLDRATE ) );
		if (rs.wasNull()) {
			dto.setGoldrateNull( true );
		}
		
		dto.setUsdvefrate( rs.getFloat( COLUMN_USDVEFRATE ) );
		if (rs.wasNull()) {
			dto.setUsdvefrateNull( true );
		}
		
		dto.setUsdcolrate( rs.getFloat( COLUMN_USDCOLRATE ) );
		if (rs.wasNull()) {
			dto.setUsdcolrateNull( true );
		}
		
		dto.setUsdratetrm( rs.getFloat( COLUMN_USDRATETRM ) );
		if (rs.wasNull()) {
			dto.setUsdratetrmNull( true );
		}
		
		dto.setUsdtrmfactor( rs.getFloat( COLUMN_USDTRMFACTOR ) );
		if (rs.wasNull()) {
			dto.setUsdtrmfactorNull( true );
		}
		
		dto.setEurusdrate( rs.getFloat( COLUMN_EURUSDRATE ) );
		if (rs.wasNull()) {
			dto.setEurusdrateNull( true );
		}
		
		dto.setUsdsicad1( rs.getFloat( COLUMN_USDSICAD1 ) );
		if (rs.wasNull()) {
			dto.setUsdsicad1Null( true );
		}
		
		dto.setUsdcencoex( rs.getFloat( COLUMN_USDCENCOEX ) );
		if (rs.wasNull()) {
			dto.setUsdcencoexNull( true );
		}
		
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(Dolartoday dto)
	{
	}

	/** 
	 * Returns all rows from the dolartoday table that match the specified arbitrary SQL statement
	 */
	public Dolartoday[] findByDynamicSelect(String sql, Object[] sqlParams) throws DolartodayDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = sql;
		
			//permite ver la consulta generada
			//System.out.println( "Executing " + SQL );
		
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new DolartodayDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns all rows from the dolartoday table that match the specified arbitrary SQL statement
	 */
	public Dolartoday[] findByDynamicWhere(String sql, Object[] sqlParams) throws DolartodayDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;
		
		
			System.out.println( "Executing " + SQL );
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new DolartodayDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}
	
	/////////////////////////////////////////////////////
	//												   //		
	//      Metodos adicionales de Consultas    	   //
	//												   //
	/////////////////////////////////////////////////////

	
	/**
	 * Obetiene el ultimo valor grabado en la tabla dolartoday
	 * @return DolarToday object
	 */
	public Dolartoday getMaxEpoch()
	{
		Dolartoday temp = null;
		
		String sql = "SELECT dolartoday.* FROM dolartoday WHERE dolartoday.epoch IN " +
			"(SELECT MAX(epoch) FROM dolartoday)  AND 1=?";
		try {
			temp = findByDynamicSelect(sql,  new Integer[]{1} )[0];
		} catch (DolartodayDaoException e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	
        
	/**
	 * Obetiene el penultimo registro con base al epoch
	 * @return DolarToday object
	 */
	public Dolartoday getPenultimate()
	{
		Dolartoday temp = null;
		
		String sql = "SELECT public.dolartoday.* FROM public.dolartoday WHERE 1=? "
                        + "ORDER BY  dolartoday.epoch DESC LIMIT 1 OFFSET 1 ";

 		try {
			temp = findByDynamicSelect(sql,  new Integer[]{1} )[0];
		} catch (DolartodayDaoException e) {
                    	e.printStackTrace();
		}
		
		return temp;
	}
        
        
	/**
	 * Obetiene ultimo registro DolarToday del dia anterior registro en base 
	 * @return DolarToday object
	 */
	public Dolartoday getDolarTodayDiaAnterior()
	{
		Dolartoday temp = null;
	        String sql = "SELECT public.dolartoday.* FROM public.dolartoday WHERE "+ 
                             "1 = ? AND public.dolartoday.epoch IN ( "+
                             "SELECT max(dolartoday.epoch) FROM dolartoday "+
                             "INNER JOIN ( SELECT dolartoday.epoch as epoch, "+
                             "to_timestamp( max(dolartoday.epoch)/1000)::date "+
                             "FROM dolartoday GROUP BY dolartoday.epoch "+
                             ")maxepoch ON ( maxepoch.epoch = dolartoday.epoch) "+
                             "WHERE to_timestamp( maxepoch.epoch/1000)::date < CURRENT_DATE )";
 		try {
			temp = findByDynamicSelect(sql,  new Integer[]{1} )[0];
                        
		} catch (DolartodayDaoException e) {
                    	e.printStackTrace();
		}
		
		return temp;
	}
        
    /**
     * Retorna el ultimo registro DolarToday del dia habil anterior, registrado antes 
     * de las 4:40p.m. o las 16 Horas.
     * @return
     */
	public Dolartoday getDolarTodayDiaAnteriorHabil()
	{
		Dolartoday temp = null;
	       
                String sql = "SELECT public.dolartoday.* FROM public.dolartoday WHERE "+ 
                             "2 = ? AND public.dolartoday.epoch IN ( "+
                             "SELECT max(dolartoday.epoch) FROM dolartoday "+
                             "INNER JOIN ( SELECT dolartoday.epoch as epoch, "+
                             "to_timestamp( max(dolartoday.epoch)/1000)::date "+
                             "FROM dolartoday GROUP BY dolartoday.epoch "+
                             ")maxepoch ON ( maxepoch.epoch = dolartoday.epoch) "+
                             "WHERE to_timestamp( maxepoch.epoch/1000)::date < CURRENT_DATE   AND " +
                             "extract(hour from to_timestamp( maxepoch.epoch/1000) ) <= 16  AND " +
                             "extract(minute from to_timestamp( maxepoch.epoch/1000) ) < 45 )";
                             //CURRENT_DATE
 		try {
 			System.out.println(sql);
			temp = findByDynamicSelect(sql,  new Integer[]{2} )[0];
                        
		} catch (DolartodayDaoException e) {
                    	e.printStackTrace();
		}
                
		
		return temp;
	}
        
       
    public Dolartoday getDolarTodayActualTRM(){
    	
    	Dolartoday temp = null;
	       
        String sql = "SELECT public.dolartoday.* FROM public.dolartoday WHERE "+ 
                      "1 = ? AND public.dolartoday.epoch IN ( "+
                      "SELECT max(dolartoday.epoch) FROM dolartoday "+
                      "INNER JOIN ( SELECT dolartoday.epoch as epoch, "+
                      "to_timestamp( max(dolartoday.epoch)/1000)::date "+
                      "FROM dolartoday GROUP BY dolartoday.epoch "+
                      ")maxepoch ON ( maxepoch.epoch = dolartoday.epoch) "+
                      "WHERE to_timestamp( maxepoch.epoch/1000)::date = CURRENT_DATE   AND " +
                      "extract(hour from to_timestamp( maxepoch.epoch/1000) ) < 16 )";
                      //CURRENT_DATE
 		try {
			temp = findByDynamicSelect(sql,  new Integer[]{1} )[0];
                        
		} catch (DolartodayDaoException e) {
                    	e.printStackTrace();
		}		
		return temp;            
        }
	

}
