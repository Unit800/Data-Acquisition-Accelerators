package com.acxiom.schemas.v201110.us.idod.comprehensivereport;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import com.acxiom.schemas.v201110.us.idod.common.IDODHeaderType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.request.RequestType;
import com.acxiom.schemas.v201110.us.idod.comprehensivereport.response.ResponseType;

public class ComprehensiveReportPortProxy{

    protected Descriptor _descriptor;

    public class Descriptor {
        private com.acxiom.schemas.v201110.us.idod.comprehensivereport.ComprehensiveReportService _service = null;
        private com.acxiom.schemas.v201110.us.idod.comprehensivereport.ComprehensiveReport _proxy = null;
        private Dispatch<Source> _dispatch = null;
        private boolean _useJNDIOnly = false;

        public Descriptor() {
            init();
        }

        public Descriptor(URL wsdlLocation, QName serviceName) {
            _service = new com.acxiom.schemas.v201110.us.idod.comprehensivereport.ComprehensiveReportService(wsdlLocation, serviceName);
            initCommon();
        }

        public void init() {
            _service = null;
            _proxy = null;
            _dispatch = null;
            try
            {
                InitialContext ctx = new InitialContext();
                _service = (com.acxiom.schemas.v201110.us.idod.comprehensivereport.ComprehensiveReportService)ctx.lookup("java:comp/env/service/ComprehensiveReportService");
            }
            catch (NamingException e)
            {
                if ("true".equalsIgnoreCase(System.getProperty("DEBUG_PROXY"))) {
                    System.out.println("JNDI lookup failure: javax.naming.NamingException: " + e.getMessage());
                    e.printStackTrace(System.out);
                }
            }

            if (_service == null && !_useJNDIOnly)
                _service = new com.acxiom.schemas.v201110.us.idod.comprehensivereport.ComprehensiveReportService();
            initCommon();
        }

        private void initCommon() {
            _proxy = _service.getComprehensiveReportPort();
        }

        public com.acxiom.schemas.v201110.us.idod.comprehensivereport.ComprehensiveReport getProxy() {
            return _proxy;
        }

        public void useJNDIOnly(boolean useJNDIOnly) {
            _useJNDIOnly = useJNDIOnly;
            init();
        }

        public Dispatch<Source> getDispatch() {
            if (_dispatch == null ) {
                QName portQName = new QName("", "ComprehensiveReportPort");
                _dispatch = _service.createDispatch(portQName, Source.class, Service.Mode.MESSAGE);

                String proxyEndpointUrl = getEndpoint();
                BindingProvider bp = (BindingProvider) _dispatch;
                String dispatchEndpointUrl = (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
                if (!dispatchEndpointUrl.equals(proxyEndpointUrl))
                    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, proxyEndpointUrl);
            }
            return _dispatch;
        }

        public String getEndpoint() {
            BindingProvider bp = (BindingProvider) _proxy;
            return (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        }

        public void setEndpoint(String endpointUrl) {
            BindingProvider bp = (BindingProvider) _proxy;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

            if (_dispatch != null ) {
                bp = (BindingProvider) _dispatch;
                bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
            }
        }

        public void setMTOMEnabled(boolean enable) {
            SOAPBinding binding = (SOAPBinding) ((BindingProvider) _proxy).getBinding();
            binding.setMTOMEnabled(enable);
        }
    }

    public ComprehensiveReportPortProxy() {
        _descriptor = new Descriptor();
        _descriptor.setMTOMEnabled(false);
    }

    public ComprehensiveReportPortProxy(URL wsdlLocation, QName serviceName) {
        _descriptor = new Descriptor(wsdlLocation, serviceName);
        _descriptor.setMTOMEnabled(false);
    }

    public Descriptor _getDescriptor() {
        return _descriptor;
    }

    public ResponseType processRequest(RequestType request, IDODHeaderType header) throws AcxiomServiceException, InputValidationFault, SystemUnavailableFault, TooManyRowsException {
        return _getDescriptor().getProxy().processRequest(request,header);
    }

}