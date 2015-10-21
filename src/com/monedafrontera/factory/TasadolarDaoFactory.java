/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package com.monedafrontera.factory;

import java.sql.Connection;
import com.monedafrontera.dao.*;
import com.monedafrontera.jdbc.*;

public class TasadolarDaoFactory
{
	/**
	 * Method 'create'
	 * 
	 * @return TasadolarDao
	 */
	public static TasadolarDao create()
	{
		return new TasadolarDaoImpl();
	}

	/**
	 * Method 'create'
	 * 
	 * @param conn
	 * @return TasadolarDao
	 */
	public static TasadolarDao create(Connection conn)
	{
		return new TasadolarDaoImpl( conn );
	}

}
