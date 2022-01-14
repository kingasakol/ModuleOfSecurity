package org.safety.library.initializationModule.mappingFactories;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.JSONMappingFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RolesListJSONMapping implements JSONMappingFactory {

    @Override
    public JSONMapping read(String path) throws Exception{
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(path);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");

            List<List<String>> result = new LinkedList<>();
            result.add(new LinkedList<>(List.of((String) jsonObject.get("name"))));
            jsonArray.forEach(row -> result.add(parseOneRow((JSONObject) row)));

            return checkIfOnlyUniqueNames(() -> result);

        } catch (FileNotFoundException e) {
            System.out.println("JSON file was not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while reading file");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("An error occurred while parsing JSON file");
            e.printStackTrace();
        }
        return null;
    }

    private List<String> parseOneRow(JSONObject row){
        List<String> parsedRow = new LinkedList<>();
        parsedRow.add((String) row.get("role"));
        return parsedRow;
    }

    private JSONMapping checkIfOnlyUniqueNames(JSONMapping jsonMapping) throws Exception{
        String[] names = jsonMapping.getMappedData().stream().map(row -> row.get(0)).toArray(String[]::new);
        Map<String, Boolean> checkMap = new HashMap<>();
        Arrays.sort(names);

        for(String n : names){
            if(checkMap.containsKey(n)){
                throw new Exception("Duplicate role name.");
            }
            checkMap.put(n, true);
        }
        return jsonMapping;
    }
}
