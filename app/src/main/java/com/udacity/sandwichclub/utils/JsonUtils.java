package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static Sandwich parseSandwichJson(String json) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(json);

            JSONObject nameJsonObject = baseJsonResponse.getJSONObject("name");
            String mainName = nameJsonObject.getString("mainName");

            JSONArray nameAlsoKnownAsJSONArray = nameJsonObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < nameAlsoKnownAsJSONArray.length(); i++) {
                alsoKnownAs.add((String) nameAlsoKnownAsJSONArray.get(i));
            }

            String placeOfOrigin = baseJsonResponse.getString("placeOfOrigin");
            String description = baseJsonResponse.getString("description");
            String image = baseJsonResponse.getString("image");

            JSONArray ingredientsJSONArray = baseJsonResponse.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                ingredients.add((String) ingredientsJSONArray.get(i));
            }

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

            return sandwich;
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing the sandwich JSON results", e);
        }

        return null;
    }
}
