package SrishtiPackage.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
//made it static so that other classes can use it without creating object
	// this method expects a JSON ARRAY at the top level (ie List<HashMap<String,
	// String>> )
	// where each element is a HashMap ie [{},{}]->see in json file
	//
	public static List<HashMap<String, String>> getDataJsonToMap(String filePath) throws IOException {

		
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// string to hashmap-- by jackson databind denpendency
		ObjectMapper mapper = new ObjectMapper();

		// read values from json file and we want that hashmap to be in list
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});// reading json string values and converting hashmap inside jason to List

		return data;
	}

	//Reads JSON and returns HashMap for specific scenario
	public static HashMap<String, String> getDataByScenario(String filePath, String scenarioName) throws IOException {

	    List<HashMap<String, String>> allData = getDataJsonToMap(filePath);

	    for (HashMap<String, String> map : allData) {
	        if (scenarioName.equals(map.get("Scenario"))) {
	            return map;
	        }
	    }
	    // scenario not found — return empty map, not null
	    // avoids NullPointerException in @Test
	    return new HashMap<>();
	}
	

}
