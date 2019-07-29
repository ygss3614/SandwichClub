package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        List<String> alsoKnownAsList = new ArrayList<String>();
        List<String> ingredientsList = new ArrayList<String>();

        JSONObject sandwichJson = new JSONObject(json);

        JSONObject sandwichNameObject = sandwichJson.getJSONObject(KEY_NAME);
        String sandwichMainName = sandwichNameObject.getString(KEY_MAIN_NAME);
        JSONArray sandwichalsoKnownAs = sandwichNameObject.getJSONArray(KEY_ALSO_KNOW_AS);
        String sandwichPlaceOfOrigin = sandwichJson.getString(KEY_PLACE_OF_ORIGIN);
        String sandwichdescription = sandwichJson.getString(KEY_DESCRIPTION);
        String sandwichImage = sandwichJson.getString(KEY_IMAGE);

        JSONArray sandwichIngredients = sandwichJson.getJSONArray(KEY_INGREDIENTS);
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
