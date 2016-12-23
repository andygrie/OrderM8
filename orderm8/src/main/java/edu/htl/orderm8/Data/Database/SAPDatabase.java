package edu.htl.orderm8.Data.Database;

import SAP.SAPConnection;

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
	
	public static SAPDatabase getInstance() {
		if(_instance == null)
			_instance = new SAPDatabase();
		
		return _instance;
	}
	
	public SAPDatabase() {
		sapConnection = new SAPConnection(NAMEDESTINATION, "192.168.1.101");
	}
	
	/* Fields */
	private SAPConnection sapConnection = null;
	
	/* Methods */
	
	public void test() {
		try {
			sapConnection.createConnection();
			sapConnection.deleteConnection();
			
			System.out.println("Connection successfully closed etc!");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
