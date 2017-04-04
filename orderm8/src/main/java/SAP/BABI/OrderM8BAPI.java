package SAP.BABI;

import com.sap.conn.jco.JCoFunction;

import SAP.SAPConnection;
import SAP.Models.ZOrder;

public class OrderM8BAPI {
	private static final String FUNCTION_NAME = "ZBAPI_ORDERM8";
	
	public static void execute(SAPConnection sapConnection, ZOrder order) throws Exception {
		JCoFunction function = sapConnection.getFunction(FUNCTION_NAME);
		setInputParameters(function, order);
		sapConnection.executeFunction(function);
	}
	
	private static void setInputParameters(JCoFunction function, ZOrder order) {
		function.getImportParameterList().setValue("WAITER", order.getWaiter());
		function.getImportParameterList().setValue("ORDERDATE", order.getDateBAPI());
		function.getImportParameterList().setValue("PROFIT", order.getProfit());
		function.getImportParameterList().setValue("PRODUCT", order.getProduct());
		function.getImportParameterList().setValue("ORDERINFO", order.getOrderinfo());
	}
}
