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
	
	public SAPConnection(String namedestination, String hostname) throws Exception {
		this.namedestination = namedestination;
		this.hostname = hostname;
	}
	
	public void createConnection() throws Exception {
		sapProvider = new SAPDestinationDataProvider();
		this.deleteConnection();
		
		if(!Environment.isDestinationDataProviderRegistered()) {
			Environment.registerDestinationDataProvider(sapProvider);
		}
		sapProvider.setDestinationProperties(namedestination, hostname);
	}
	
    public void deleteConnection(){
    	try {
    		com.sap.conn.jco.ext.Environment.unregisterDestinationDataProvider(sapProvider);
    	} catch (Exception ex) {
    		System.out.println("deleteConnection - Exception: " + ex.getMessage());
    	}
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
    
    public void executeFunction(JCoFunction function) throws Exception {
    	JCoDestination sapDest = JCoDestinationManager.getDestination(namedestination);
    	function.execute(sapDest);
    }
}
