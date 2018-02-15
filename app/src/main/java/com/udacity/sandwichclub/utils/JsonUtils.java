package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject myJson = new JSONObject(json);
        JSONObject nameObj = myJson.getJSONObject("name");
        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(nameObj.getString("mainName"));
        sandwich.setDescription(myJson.getString("description"));
        sandwich.setPlaceOfOrigin(myJson.getString("placeOfOrigin"));
        sandwich.setImage(myJson.getString("image"));
        JSONArray arrIng = myJson.getJSONArray("ingredients");
        List<String> listIng = new ArrayList<>();
        if (arrIng != null){
            for (int i=0;i<arrIng.length();i++)
                listIng.add(arrIng.getString(i));
        }
        sandwich.setIngredients(listIng);
        JSONArray arrKnown = nameObj.getJSONArray("alsoKnownAs");
        List<String> listknown = new ArrayList<>();
        if (arrKnown != null){
            for (int i=0;i<arrKnown.length();i++)
                listknown.add(arrKnown.getString(i));
        }
        sandwich.setAlsoKnownAs(listknown);
        return sandwich;
    }
}
