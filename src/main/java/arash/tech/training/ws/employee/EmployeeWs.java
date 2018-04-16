package arash.tech.training.ws.employee;




import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

import tech.arash.employee.Address;
import tech.arash.employee.CreateEmployeeRequest;
import tech.arash.employee.CreateEmployeeResponse;
import tech.arash.employee.DelEmployeeRequest;
import tech.arash.employee.DelEmployeeResponse;
import tech.arash.employee.Employee;
import tech.arash.employee.EmployeePortType;
import tech.arash.employee.GetAllEmployeesRequest;
import tech.arash.employee.GetAllEmployeesResponse;
import tech.arash.employee.GetEmployeeRequest;
import tech.arash.employee.GetEmployeeResponse;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class EmployeeWs implements EmployeePortType {
	Map<BigInteger, List<Employee>> employees = new HashMap<>();

	public EmployeeWs()   {
		insertData();
	}


	private void insertData()  {
		JSONParser parser = new JSONParser();

		List<Employee> emps= new ArrayList<>();
		Employee employee = null;
		Address address = null;
		
		Object obj = null;
		try {
			obj = parser.parse(new FileReader("/home/data.json"));
		} catch (IOException  e1) {
			e1.printStackTrace();
		}
		catch ( ParseException e1) {
			e1.printStackTrace();
	    }
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray e = (JSONArray) jsonObject.get("employeesdata");
        long id = 0,no,aptNo   = 0;
        String name, city, street =null;

        JSONObject addr= null;
        for (int i=0; i< e.size(); i++)
		  {
		    JSONObject emp = (JSONObject) e.get(i);
		    if (emp != null) {
				employee=new Employee();
				address = new Address();
			    id = (long)(emp.get("id"));
			    name = (String) emp.get("name");
			    addr =   (JSONObject) emp.get("address");
			    
			    city=(String) addr.get("city");
			    no= (long) addr.get("no");
			    aptNo=(long) addr.get("aptNo");
			    street=(String) addr.get("street");
				employee.setId(BigInteger.valueOf(id));
				employee.setName(name);
			
				address.setNo(BigInteger.valueOf(no));
				address.setApptNo(BigInteger.valueOf(aptNo));
				address.setCity(city);
				address.setStreet(street);
				
				employee.setAddress(address);

				
				emps.add(employee);
		    }
			employees.put(BigInteger.valueOf(id), emps); 

		  }
//	      Set set = employees.entrySet();
//	      Iterator iterator = set.iterator();
//	      while(iterator.hasNext()) {
//	         Map.Entry mentry = (Map.Entry)iterator.next();
//	         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
//	         System.out.println(mentry.getValue().toString());
//	      }

	}
	
	@Override
	public GetEmployeeResponse getEmployee(GetEmployeeRequest request) {
		BigInteger employeeId = request.getEmployeeId();
		List<Employee> emps = employees.get(employeeId);
    	GetEmployeeResponse response = new GetEmployeeResponse();
		
		for (Employee e: emps) {
			if (e.getId().equals(employeeId)) {
				response.getEmployee().add(e);
			}
		}
		
		return response;
	}


	@Override
	public GetAllEmployeesResponse getAllEmployees(GetAllEmployeesRequest parameters) {
		Map<BigInteger, List<Employee>> map = employees;
		 Entry<BigInteger, List<Employee>> entry = map.entrySet().iterator().next();
		 BigInteger key = entry.getKey();
		 List<Employee> orders = entry.getValue();
		 

		 GetAllEmployeesResponse response = new GetAllEmployeesResponse();
		response.getEmployee().addAll(orders);
		return response;
	}

}
