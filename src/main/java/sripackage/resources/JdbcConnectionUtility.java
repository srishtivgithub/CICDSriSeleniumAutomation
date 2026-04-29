package sripackage.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

//getting details from DB and then passing into our @Test, to use those data 
//intead of getting data ->writing in excel->selenium taking data from excel and using in @Test
//we directly connect our java selenium framwork to DB and retrived the value
public class JdbcConnectionUtility {
	
	private String host;
	private String port;
	private String dbName;
	private String user;
	private String password;
	
	
	public JdbcConnectionUtility(Properties prop) {
        this.host     = prop.getProperty("host");
        this.port     = prop.getProperty("port");
        this.dbName   = prop.getProperty("dbName");
        this.user     = prop.getProperty("dbUser");
        this.password = prop.getProperty("dbPassword");
    }
	

	// get all details from config file instead of hardcoding
	//String host = "localhost";
	//String port = "3306";

	 // Returns DB rows as List<HashMap> — same structure as your JSON DataProvider
    public List<HashMap<String, String>> getDataFromDB(String query) throws SQLException {

    	Connection con=null;
    	
        List<HashMap<String, String>> dataList = new ArrayList<>();

        try {
          con= DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName, user, password
        );
        
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            HashMap<String, String> row = new HashMap<>();
            row.put("username", resultSet.getString("username"));
            row.put("password", resultSet.getString("password"));
            // Add more columns as needed
            dataList.add(row);
          }
        }
        finally {
        
        con.close(); //connection close
        }
        return dataList;

  }
}
