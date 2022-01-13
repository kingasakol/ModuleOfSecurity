package org.safety.library.initializationModule.mappingFactories;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.safety.library.initializationModule.JSONMapping;
import org.safety.library.initializationModule.JSONMappingFactory;
import org.safety.library.initializationModule.abstractMappingObjects.RolesList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RolesListJSONMapping implements JSONMappingFactory {

    @Override
    public JSONMapping read(String path) throws Exception {
        JSONParser jsonParser = new JSONParser();
        try(FileReader fileReader = new FileReader(path)) {
            List<List<String>> result = new LinkedList<>();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            List<String> defaults = this.getDefaults(jsonObject);

            List<String> firstElement = new LinkedList<>(Arrays.asList((String) jsonObject.get("name")));
            result.add(firstElement);

            jsonArray.forEach(row -> result.add(parseOneRow((JSONObject) row, defaults)));

            return ()->result;

        }catch (FileNotFoundException e) {
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




    private List<String> parseOneRow(JSONObject row, List<String> defaults) {
        List<String> parsedRow = new LinkedList<>();
        parsedRow.add((String) row.get("id"));
        parsedRow.add((String) row.get("role"));
        Object abilities = row.get("abilities");
        if (abilities != null){
            for(String abil :(String[]) abilities){
                parsedRow.add(abil);
            }
        }else{
            for(String elem: defaults){
                parsedRow.add(elem);
            }
        }
        return parsedRow;
    }


    private List<String> getDefaults(JSONObject handler){
        List<String> result = new LinkedList<>();
        JSONArray jsonArray = (JSONArray) handler.get("defaults");
        for(Object elem: jsonArray){
            result.add((String) elem);
        }
        return result;
    }

}
