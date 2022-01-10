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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RolesForUsersJSONMapping implements JSONMappingFactory {
    @Override
    public JSONMapping read(String path) throws Exception {
        JSONParser jsonParser = new JSONParser();
        try(FileReader fileReader = new FileReader(path)) {
            List<List<String>> result = new LinkedList<>();

            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            JSONArray jsonArray = (JSONArray) jsonObject.get("data");

            List<String> firstElement = new LinkedList<>(Arrays.asList((String) jsonObject.get("name")));
            result.add(firstElement);

            jsonArray.forEach(row -> result.add(parseOneRow((JSONObject) row)));

            return testIfRowsContainsDistinctIDs(() -> result);
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

    private JSONMapping testIfRowsContainsDistinctIDs(JSONMapping jsonMapping) throws Exception{
        String[] ids = jsonMapping.getMappedData().stream().map(row -> row.get(0)).toArray(String[]::new);
        Arrays.sort(ids);
        for(int i = 0; i < ids.length - 1; i++){
            if(ids[i].equals(ids[i + 1])){
                throw new Exception("JSON does not have distinct ids. It is neccessary to provice distinct ids with allowed role.");
            }
        }
        return jsonMapping;
    }

    private List<String> parseOneRow(JSONObject row){
        List<String> parsedRow = new LinkedList<>();
        parsedRow.add((String) row.get("id"));
        parsedRow.add((String) row.get("role"));

        return parsedRow;
    }

}
