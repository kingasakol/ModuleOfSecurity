package org.safety.library.initializationModule.mappingFactories;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.JSONMappingFactory;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DataAccessJSONMapping implements JSONMappingFactory {
    @Override
    public JSONMapping read(String path) throws Exception {
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader(path)){
            List<List<String>> result = new LinkedList<>();

            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            //List of protected rows is defined in the JSON file field "data"
            JSONArray protectedDataList = (JSONArray) obj.get("data");

            //Name of the protected entity is defined in the JSON file field "name"
            //and will be saved as a first row of the result
            List<String> firstRowWithEntityName = new LinkedList<>(Arrays.asList((String) obj.get("name")));
            result.add(firstRowWithEntityName);

            //Next rows in the result will be saved in the list with schema [id, allowed_role_1, allowed_role_2, allowed_role_3, ...]
            protectedDataList.forEach(protectedRow -> result.add(parseOneRow((JSONObject) protectedRow)));

            return testIfRowsContainsDistinctIDs(() -> result);
        }
        catch (FileNotFoundException e){
            System.out.println("JSON file was not found");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("An error occurred while reading file");
            e.printStackTrace();
        }
        catch (ParseException e){
            System.out.println("An error occurred while parsing JSON file");
            e.printStackTrace();
        }


        return null;
    }

    private JSONMapping testIfRowsContainsDistinctIDs(JSONMapping jsonMapping) throws Exception{
        String[] ids = jsonMapping.getMappedData().stream().map(row -> row.get(0)).toArray(String[]::new);
        Arrays.sort(ids);
        for(int i = 0; i < ids.length - 1; i++){
            if(ids[i].equals(ids[i+1])){
                throw new Exception("JSON does not have distinct ids. It is neccessary to provice distinct ids with allowed roles.");
            }
        }
        return jsonMapping;
    }

    private List<String> parseOneRow(JSONObject row){
        List<String> parsedRow = new LinkedList<>();
        parsedRow.add((String) row.get("id"));
        JSONArray allowedRoles = (JSONArray) row.get("allowedRoles");
        allowedRoles.forEach(role -> parsedRow.add((String)role));
        return parsedRow;
    }
}
