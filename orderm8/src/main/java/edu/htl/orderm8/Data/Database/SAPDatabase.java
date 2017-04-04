package edu.htl.orderm8.Data.Database;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import SAP.SAPConnection;
import SAP.BABI.OrderM8BAPI;
import SAP.Models.ZOrder;

public class SAPDatabase {
	/* Constants */
	public static final String SAP_IP 			= "192.168.0.150";
	public static final String SAP_SYSNR 		= "00";
	public static final String SAP_CLIENT		= "000";
	public static final String SAP_USER			= "BCUSER";
	public static final String SAP_PASSWD		= "MINISAP";
	public static final String SAP_LANG			= "EN";
	private static final String NAMEDESTINATION	= "ABAP_HTL";
	
	/* Singelton */
	private static SAPDatabase _instance = null;
	
	public static SAPDatabase getInstance() throws Exception {
		if(_instance == null)
			_instance = new SAPDatabase();
		
		return _instance;
	}
	
	public SAPDatabase() throws Exception {
		sapConnection = new SAPConnection(NAMEDESTINATION, SAP_IP);
	}
	
	/* Fields */
	private SAPConnection sapConnection = null;
	
	/* Methods */
	
	public void commitOrder(ZOrder order) throws Exception {
		sapConnection.createConnection();
		OrderM8BAPI.execute(sapConnection, order);
		sapConnection.deleteConnection();
	}
}
