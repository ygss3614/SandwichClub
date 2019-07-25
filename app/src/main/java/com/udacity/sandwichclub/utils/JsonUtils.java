package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        List<String> alsoKnownAsList = new ArrayList<String>();
        List<String> ingredientsList = new ArrayList<String>();

        JSONObject sandwichJson = new JSONObject(json);

        JSONObject sandwichNameObject = sandwichJson.getJSONObject("name");
        String sandwichMainName = sandwichNameObject.getString("mainName");
        JSONArray sandwichalsoKnownAs = sandwichNameObject.getJSONArray("alsoKnownAs");
        String sandwichPlaceOfOrigin = sandwichJson.getString("placeOfOrigin");
        String sandwichdescription = sandwichJson.getString("description");
        String sandwichImage = sandwichJson.getString("image");

        JSONArray sandwichIngredients = sandwichJson.getJSONArray("ingredients");
        for (int i=0; i < sandwichalsoKnownAs.length(); i++){
            alsoKnownAsList.add(sandwichalsoKnownAs.get(i).toString());
        }

        for (int i=0; i< sandwichIngredients.length(); i++){
            ingredientsList.add(sandwichIngredients.get(i).toString());
        }

        Sandwich sandwich = new Sandwich(sandwichMainName, alsoKnownAsList, sandwichPlaceOfOrigin,
                sandwichdescription, sandwichImage, ingredientsList);

        return sandwich;
    }
}
