package SrishtiPackage.data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.DataProvider;

import sripackage.resources.ConfigReaderProperties;
import sripackage.resources.DataGeneratorUtility;
import sripackage.resources.ExcelUtility;
import sripackage.resources.JdbcConnectionUtility;

public class DataProviderUtility {
	//this dataProvider is taking data from JSON file
	// this dataProvider takes value from json file->stores data in list<Hashmap>
	// and returns the list of hashmaps
	@DataProvider
	public Object[][] getDataFromExternalFile() throws IOException {
		//List<HashMap<String, String>> data = DataReader.getDataJsonToMap(System.getProperty("user.dir") + "\\src\\test\\java\\SrishtiPackage\\data\\PurchaseOrder.json");

		List<HashMap<String, String>> data = DataReader.getDataJsonToMap(System.getProperty("user.dir") 
				+ File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + "SrishtiPackage"
				+ File.separator + "data" + File.separator + "PurchaseOrder.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };

	}

	// this data provider, hashmap is created and data is added. Then the data is returned
	@DataProvider
	public Object[][] getDataHashMapInternal() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "dummyemailsrishti@gmail.com");
		map.put("password", "Dummyemail2@");
		map.put("prodName", "ADIDAS ORIGINAL");

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "anshika@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("prodName", "ZARA COAT 3");

		return new Object[][] { { map }, { map1 } };

	}
	//this dataProvider is taking data from DB
	@DataProvider(name = "getDBData")
    public Object[][] getDBData() throws IOException, SQLException {

        // ✅ ConfigReader fires independently — no @BeforeMethod dependency
        // ✅ prop is never null here regardless of TestNG execution order
         Properties prop= ConfigReaderProperties.getProperties();

        JdbcConnectionUtility jdbc = new JdbcConnectionUtility(prop);
        //receiving data from DB as per the query provided
        List<HashMap<String, String>> data = jdbc.getDataFromDB(
            "SELECT * FROM EmployeeTable WHERE scenario='zero balance'"
        );
        
        Object[][] result = new Object[data.size()][1];
        //iterating the list="data" and story the details into 2D Array Object=result[][]
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;

      }
	//this data provider returns object and does not takes data from external file and not used any hashmap
	@DataProvider
	public Object[][] getData() {
		
		return new Object[][] {{"dummyemailsrishti@gmail.com", "Dummyemail2@", "ADIDAS ORIGINAL", "India"}, {"anshika@gmail.com","Iamking@000","ZARA COAT 3", "India"}};
	}
	
	@DataProvider
	public Object[][] getDataFromExternalFileForInvalidCredential() throws IOException {
		//List<HashMap<String, String>> data = DataReader.getDataJsonToMap(System.getProperty("user.dir") + "\\src\\test\\java\\SrishtiPackage\\data\\PurchaseOrder.json");

		List<HashMap<String, String>> data = DataReader.getDataJsonToMap(System.getProperty("user.dir") 
				+ File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + "SrishtiPackage"
				+ File.separator + "data" + File.separator + "InvalidCredential.json");

		return new Object[][] { { data.get(0)  } };

	}
	
	// this dataProvider fetches data from Excel file
	// ExcelUtility reads the file → returns List<HashMap>
	// same structure as JSON and DB — consistent across all data sources
	@DataProvider(name = "getExcelData")
	public Object[][] getExcelData() throws IOException {

	    String filePath = System.getProperty("user.dir")
	        + File.separator + "src"
	        + File.separator + "test"
	        + File.separator + "java"
	        + File.separator + "SrishtiPackage"
	        + File.separator + "data"
	        + File.separator + "ExcelTestData.xlsx";

	    // ✅ ExcelUtility does the reading — DataProviderUtility only converts and returns
	    List<HashMap<String, String>> data =
	        ExcelUtility.getDataFromExcel(filePath, "SheetTestdata1");

	    Object[][] result = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        result[i][0] = data.get(i);
	    }
	    return result;
	}
	/*
	Excel File Structure (`TestData.xlsx`)
	```
	Sheet name: PurchaseData

	| email                        | password      | prodName        | country |
	|------------------------------|---------------|-----------------|---------|
	| dummyemailsrishti@gmail.com  | Dummyemail2@  | ADIDAS ORIGINAL | India   |
	| anshika@gmail.com            | Iamking@000   | ZARA COAT 3     | India   |
	
	*/
	
	//data for all fields registration
	@DataProvider(name = "getRegistrationData")
	public Object[][] getFullRegistrationData() throws IOException{
		// call the generator (DataGeneratorUtility class)→ get fresh HashMap
	    HashMap<String, String> mapData = DataGeneratorUtility.generateRegistrationData();

	    // wrap in Object[][] so TestNG can pass it to @Test
	    // Object[0]    = row 1 → one test execution
	    // Object[0][0] = the HashMap itself → one argument to @Test method
	    return new Object[][] {
	        { mapData }   // → one row, one HashMap
	    };
	}
	/*If you wanted 2 registrations in same run:
		Object[][] {
		    { DataGeneratorUtility.generateRegistrationData() },  // run 1
		    { DataGeneratorUtility.generateRegistrationData() }   // run 2
		}
		→ @Test executes TWICE with different data each time
		*/
	
	@DataProvider(name = "getRegistrationMandatoryValidationFieldData")
	public Object[][] getRegistrationMandatoryValidationFieldData() throws IOException{
		// call the generator (DataGeneratorUtility class)→ get fresh HashMap
	    List<HashMap<String, String>> mapData =   DataReader.getDataJsonToMap(System.getProperty("user.dir") 
				+ File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + "SrishtiPackage"
				+ File.separator + "data" + File.separator + "RegistrationMandatoryValidationFields.json");


	    return new Object[][] {
	        { mapData.get(0) }   
	    };
	}
	@DataProvider(name="getInvalidRegistrationData")
	public Object[][] getInvalidRegistrationData() throws IOException {

		List<HashMap<String, String>> data = DataReader.getDataJsonToMap(System.getProperty("user.dir") 
				+ File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + "SrishtiPackage"
				+ File.separator + "data" + File.separator + "InvalidRegistrationDetails.json");

		Object[][] result = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        result[i][0] = data.get(i);
	    }
	    return result;

	}
	//Toast scenarios — pass scenario names explicitly
	@DataProvider(name="getLoginToastErrorData")
	public Object[][] getLoginToastErrorData() throws IOException {

		String filepath=System.getProperty("user.dir") 
				+ File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + "SrishtiPackage"
				+ File.separator + "data" + File.separator + "LoginNegativeTestData.json";

		return new Object[][]{
			{ DataReader.getDataByScenario(filepath, "LOG_TC_004") },
			{ DataReader.getDataByScenario(filepath, "LOG_TC_005") }
		};

	}
	
	// Inline scenarios — pass scenario names explicitly
	@DataProvider(name = "getLoginInlineErrorData")
	public Object[][] getLoginInlineErrorData() throws IOException {
		
		String filepath=System.getProperty("user.dir") 
				+ File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + "SrishtiPackage"
				+ File.separator + "data" + File.separator + "LoginNegativeTestData.json";
		
	    return new Object[][] {
	        { DataReader.getDataByScenario(filepath, "LOG_TC_006") },
	       // { DataReader.getDataByScenario(filepath, "LOG_TC_007") },
	        { DataReader.getDataByScenario(filepath, "LOG_TC_008") },
	        { DataReader.getDataByScenario(filepath, "LOG_TC_009") }
	    };
	}
	@DataProvider(name = "getLoginInlineErrorEmailPassword")
	public Object[][] getLoginInlineErrorEmailPassword() throws IOException {
		
		String filepath=System.getProperty("user.dir") 
				+ File.separator + "src" + File.separator + "test"
				+ File.separator + "java" + File.separator + "SrishtiPackage"
				+ File.separator + "data" + File.separator + "LoginNegativeTestData.json";
		
	    return new Object[][] {
	        
	        { DataReader.getDataByScenario(filepath, "LOG_TC_007") }
	        
	    };
	}
	
	@DataProvider(name = "getLoginCredentialFromDataGenerator")
	public Object[][] getLoginCredentialFromDataGenerator() throws IOException {
		
		String email=DataGeneratorUtility.generateEmail();
		String password=DataGeneratorUtility.generatePassword();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		
	    return new Object[][] {
	        
	        { map }
	        
	    };
	}


	
	
}
