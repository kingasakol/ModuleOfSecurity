package org.safety.library.initializationModule.mappingFactories;

import org.json.simple.parser.JSONParser;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.JSONMappingFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EntityAccessJSONMapping implements JSONMappingFactory {
    @Override
    public JSONMapping read(String path) throws Exception {
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader(path)){
            List<List<String>> result = new LinkedList<>();

            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            //List of roles with addPriviledges is stored in "data" field
            JSONArray addPrivilegesList = (JSONArray) obj.get("data");
            addPrivilegesList.forEach(protectedRow -> result.add(parseRow((JSONObject) protectedRow)));

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
                throw new Exception("JSON does not have distinct entity names. It is neccessary to provice distinct entity names with allowed roles.");
            }
        }
        return jsonMapping;
    }

    private List<String> parseRow(JSONObject row){
        List<String> parsedRow = new LinkedList<>();
        parsedRow.add((String) row.get("entityName"));
        JSONArray allowedRoles = (JSONArray) row.get("allowedRoles");
        allowedRoles.forEach(role -> parsedRow.add((String)role));
        return parsedRow;
    }

}
