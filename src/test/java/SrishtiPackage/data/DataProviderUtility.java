package SrishtiPackage.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.DataProvider;

import sripackage.resources.ConfigReaderProperties;
import sripackage.resources.JdbcConnectionUtility;

public class DataProviderUtility {
	//this dataProvider is taking data from JSON file
	// this dataProvider takes value from json file->stores data in list<Hashmap>
	// and returns the list of hashmaps
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = DataReader.getDataJsonToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\SrishtiPackage\\data\\PurchaseOrder.json");

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
        List<HashMap<String, String>> data = jdbc.getDataFromDB(
            "SELECT * FROM EmployeeTable WHERE scenario='zero balance'"
        );
        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;

}
}
