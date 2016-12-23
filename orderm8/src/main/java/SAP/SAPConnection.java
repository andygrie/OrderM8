package SAP;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.ext.Environment;

public class SAPConnection {
	private static SAPDestinationDataProvider sapProvider = null;
	private String namedestination	= "ABAP_HTL";
	private String hostname = "127.0.0.1";
	
	public SAPConnection(String namedestination, String hostname) {
		this.namedestination = namedestination;
		this.hostname = hostname;
	}
	
	public void createConnection() throws Exception {
		sapProvider = new SAPDestinationDataProvider();
		Environment.registerDestinationDataProvider(sapProvider);
		sapProvider.setDestinationProperties(namedestination, hostname);
	}
	
    public void deleteConnection() throws Exception{
        com.sap.conn.jco.ext.Environment.unregisterDestinationDataProvider(sapProvider);
    }
    
    public JCoFunction getFunction(String nameOfFunction) throws Exception {
    	JCoDestination sapDest = JCoDestinationManager.getDestination(namedestination);
    	JCoRepository sapRepos = sapDest.getRepository();
        JCoFunctionTemplate ft = sapRepos.getFunctionTemplate(nameOfFunction.toUpperCase());

        if(ft == null){
            throw  new Exception("Function not found in the SAP Repository.");
        }
        
        return ft.getFunction();
    }
}
