package sripackage.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReaderProperties {
/*
	## Final Picture — How All 3 Classes Connect
	```
	GlobalData.properties
	        ↓
	ConfigReader.getProperties()  ← static, loads ONCE, available ANY time
	        ↓                  ↓
	   BaseTest            DataProviderUtility
	   @BeforeMethod        @DataProvider getDBData()
	   (browser setup)      (runs BEFORE @BeforeMethod —
	                         but prop is safe because
	                         ConfigReader is independent)
	                              ↓
	                     JdbcConnectionUtility(prop)
	                              ↓
	                     List<HashMap> from DB
	                              ↓
	                     TestDBUsecase @Test receives
	                     one row per execution
	                     
	                     */
    private static Properties prop;

    // static → callable from anywhere, no object needed
    // loads ONCE → reused on every subsequent call
    public static Properties getProperties() throws IOException {

        if (prop == null) {  // lazy initialization — loads only when first needed
            prop = new Properties();
            FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "java"
                + File.separator + "sripackage"
                + File.separator + "resources"
                + File.separator + "GlobalData.properties"
            );
            prop.load(fis);
        }
        return prop;
    }
}