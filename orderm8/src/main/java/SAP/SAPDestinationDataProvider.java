package SAP;

import java.util.HashMap;
import java.util.Properties;

import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import edu.htl.orderm8.Data.Database.SAPDatabase;

public class SAPDestinationDataProvider implements DestinationDataProvider {
	private DestinationDataEventListener eL = null;
	private HashMap<String, Properties> collSecureDBStorage = new HashMap<String, Properties>();

	public Properties getDestinationProperties(String destinationName) {
		Properties p = null;
		try {
			p = collSecureDBStorage.get(destinationName);

			if (p != null) {
				if (p.isEmpty())
					throw new DataProviderException(
							DataProviderException.Reason.INVALID_CONFIGURATION,
							"destination configuration is incorrect", null);
			}

			
		} catch (RuntimeException re) {
			throw new DataProviderException(DataProviderException.Reason.INTERNAL_ERROR, re);
		}
		return p;
	}

	public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {
		this.eL = eventListener;
	}

	public boolean supportsEvents() {
		return true;
	}

	void changeProperties(String destName, Properties properties) {
		synchronized (collSecureDBStorage) {
			if (properties == null) {
				if (collSecureDBStorage.remove(destName) != null)
					eL.deleted(destName);
			} else {
				collSecureDBStorage.put(destName, properties);
				eL.updated(destName);
			}
		}
	}
	
    public void setDefaultDestinationProperties(String destName)
    {	setDestinationProperties(destName, SAPDatabase.SAP_IP);
    }
    
    public void setDestinationProperties(String destName, String hostname)
    {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, 	hostname);
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  	SAPDatabase.SAP_SYSNR);
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, 	SAPDatabase.SAP_USER);
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,   SAPDatabase.SAP_CLIENT);
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, 	SAPDatabase.SAP_PASSWD);
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   	SAPDatabase.SAP_LANG);
        changeProperties(destName, connectProperties);
    }

}