package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        Sandwich mSandwich = new Sandwich();
        final String NAME = "name";
        final String MAIN_NAME= "mainName" ;
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        JSONObject sandwichJson = new JSONObject(json);

                /**
                 {
                 "name":
                    {"mainName":"Medianoche",
                     "alsoKnownAs":["Cuban Sandwich"]
                    },
                 "placeOfOrigin":"Cuba",
                 "description":"Medianoche (\"midnight\" in Spanish) is a type of sandwich which originated in Cuba. It is served in many Cuban communities in the United States. It is so named because of the sandwich's popularity asa staple served in Havana's night clubs right around or after midnight.",
                 "image":"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Sandwich_de_Medianoche.jpg/800px-Sandwich_de_Medianoche.jpg",
                 "ingredients":["Egg bread","Roast pork","Ham","Mustard","Swiss cheese","Dill pickles"]

                 }

                 **/

                JSONObject name = sandwichJson.getJSONObject(NAME);
                mSandwich.setMainName(name.optString(MAIN_NAME));
                mSandwich.setPlaceOfOrigin(sandwichJson.optString(PLACE_OF_ORIGIN));
                mSandwich.setImage(sandwichJson.optString(IMAGE));
                mSandwich.setDescription(sandwichJson.optString(DESCRIPTION));

                Log.v("dollar","name= :" + mSandwich.getDescription());


                JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);

                List<String> IngredientsList = new ArrayList<>();
                List<String> AlsoKnownAsList= new ArrayList<>();
                for(int i=0;i<alsoKnownAs.length();i++)
                {
                    String Aas = alsoKnownAs.optString(i);
                AlsoKnownAsList.add(Aas);
                }

                mSandwich.setAlsoKnownAs(AlsoKnownAsList);

                JSONArray ingred = sandwichJson.getJSONArray(INGREDIENTS);

                for(int x=0;x<ingred.length();x++)
                {
                    String ing = ingred.optString(x);
                    IngredientsList.add(ing);
                }

                mSandwich.setIngredients(IngredientsList);


        return mSandwich;

    }
}
