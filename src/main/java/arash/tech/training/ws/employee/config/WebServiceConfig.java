package arash.tech.training.ws.employee.config;


import java.io.IOException;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;

import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import arash.tech.training.ws.employee.EmployeeWs;
import net.minidev.json.parser.ParseException;




@Configuration
public class WebServiceConfig {
	@Autowired
	private Bus bus;
	
	@Bean
	public Endpoint endpoint() throws ParseException, IOException, org.json.simple.parser.ParseException {
		Endpoint endpoint = new EndpointImpl(bus, new EmployeeWs());
		endpoint.publish("/employeeservice");
		
//		SOAPBinding binding = (SOAPBinding) endpoint.getBinding();
//		ArrayList<Handler> handlerChain = new ArrayList<>();
//		handlerChain.add(new SiteHandler());
		
//		binding.setHandlerChain(handlerChain);
		return endpoint;
		
	}
}

