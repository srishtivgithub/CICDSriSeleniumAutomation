package SrishtiPackage.Tests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SrishtiPackage.TestComponents.BaseTest;
import SrishtiPackage.data.DataProviderUtility;
import sripackage.resources.JdbcConnectionUtility;

public class TestDBUsecase extends BaseTest{
	/*
	## 🔄 How It All Flows
	```
	GlobalData.properties
	    ↓ (db.host, db.port, db.name, db.user, db.password)
	BaseTest.prop  (class-level field, loaded in initializeDriver())
	    ↓
	JdbcConnectionUtility(prop)  (reads DB config, runs SQL query)
	    ↓ returns List<HashMap<String,String>>
	@DataProvider getDBData()  (converts list → Object[][])
	
	    ↓
	@Test submitOrder(HashMap value)  (receives one DB row per test run)
	
	*/
	
	@Test(dataProvider = "getDBData", dataProviderClass = DataProviderUtility.class)
    public void submitOrder(HashMap<String, String> value) throws IOException, InterruptedException {

        // prop is now accessible from BaseTest
        // value.get("username") and value.get("password") come from DB
        landingPage.loginApplication(value.get("username"), value.get("password"));

        // ... rest of your test steps
    }
	
	/*@DataProvider
    public Object[][] getDBData() throws IOException, SQLException {

        // prop is initialized in @BeforeMethod → initializeDriver() → launchApplication()
        JdbcConnectionUtility jdbc = new JdbcConnectionUtility(prop);

        List<HashMap<String, String>> data = jdbc.getDataFromDB(
            "SELECT * FROM EmployeeTable WHERE scenario='zero balance'"
        );
        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;*/


}
