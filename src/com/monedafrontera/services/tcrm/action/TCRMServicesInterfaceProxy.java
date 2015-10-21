package com.monedafrontera.services.tcrm.action;

public class TCRMServicesInterfaceProxy implements com.monedafrontera.services.tcrm.action.TCRMServicesInterface {
  private String _endpoint = null;
  private com.monedafrontera.services.tcrm.action.TCRMServicesInterface tCRMServicesInterface = null;
  
  public TCRMServicesInterfaceProxy() {
    _initTCRMServicesInterfaceProxy();
  }
  
  public TCRMServicesInterfaceProxy(String endpoint) {
    _endpoint = endpoint;
    _initTCRMServicesInterfaceProxy();
  }
  
  private void _initTCRMServicesInterfaceProxy() {
    try {
      tCRMServicesInterface = (new com.monedafrontera.services.tcrm.action.TCRMServicesWebServiceLocator()).getTCRMServicesWebServicePort();
      if (tCRMServicesInterface != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)tCRMServicesInterface)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)tCRMServicesInterface)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (tCRMServicesInterface != null)
      ((javax.xml.rpc.Stub)tCRMServicesInterface)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.monedafrontera.services.tcrm.action.TCRMServicesInterface getTCRMServicesInterface() {
    if (tCRMServicesInterface == null)
      _initTCRMServicesInterfaceProxy();
    return tCRMServicesInterface;
  }
  
  public com.monedafrontera.services.tcrm.action.TcrmResponse queryTCRM(java.util.Calendar tcrmQueryAssociatedDate) throws java.rmi.RemoteException{
    if (tCRMServicesInterface == null)
      _initTCRMServicesInterfaceProxy();
    return tCRMServicesInterface.queryTCRM(tcrmQueryAssociatedDate);
  }
  
  
}