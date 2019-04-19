package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        Log.v("dollar","position= :" + position);

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        Log.v("dollar","json= :" + json);


        try {

            sandwich = JsonUtils.parseSandwichJson(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich s) {
        ImageView sandwichImage = (ImageView)findViewById(R.id.image_iv);
        TextView sandwichName = (TextView)findViewById(R.id.name_tv);
        TextView sandwichOrigin = (TextView)findViewById(R.id.origin_tv);
        TextView sandwichDescription = (TextView)findViewById(R.id.description_tv);
        TextView sandwichIngredients = (TextView)findViewById(R.id.ingredients_tv);
        TextView sandwichAlsoKnownAs = (TextView)findViewById(R.id.also_known_tv);

        sandwichImage.setImageURI(Uri.parse(s.getImage()));
        sandwichName.setText(s.getMainName());
        sandwichOrigin.setText(s.getPlaceOfOrigin());
        sandwichDescription.setText(s.getDescription());

        for (String toyName : s.getAlsoKnownAs()){
            sandwichAlsoKnownAs.append(toyName + "\n");
        }

        for (String toyName : s.getIngredients()){
            sandwichIngredients.append(toyName + "\n");
        }

    }
}
