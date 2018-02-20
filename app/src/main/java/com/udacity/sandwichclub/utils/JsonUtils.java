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
            String mainName = nameJsonObject.optString("mainName");

            JSONArray nameAlsoKnownAsJSONArray = nameJsonObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = getListString(nameAlsoKnownAsJSONArray);

            String placeOfOrigin = baseJsonResponse.optString("placeOfOrigin");
            String description = baseJsonResponse.optString("description");
            String image = baseJsonResponse.optString("image");

            JSONArray ingredientsJSONArray = baseJsonResponse.getJSONArray("ingredients");
            List<String> ingredients = getListString(ingredientsJSONArray);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing the sandwich JSON results", e);
        }

        return null;
    }

    private static List<String> getListString(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add((String) jsonArray.get(i));
        }
        return list;
    }
}
