package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView desc = findViewById(R.id.description_tv);
        TextView ing = findViewById(R.id.ingredients_tv);
        TextView known = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
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

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
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

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        origin.setText(sandwich.getPlaceOfOrigin());
        desc.setText(sandwich.getDescription());
        List<String> list = sandwich.getIngredients();
        StringBuilder builder = new StringBuilder();
        for (String ingr : list){
           builder.append(ingr).append(", ");
        }
        builder.setLength(builder.length()-2);
        ing.setText(builder.toString());
        list.clear();
        list = sandwich.getAlsoKnownAs();
        if (!list.isEmpty()){
        builder.setLength(0);
        for (String known2 : list){
            builder.append(known2).append(", ");
        }
        builder.setLength(builder.length()-2);
        known.setText(builder.toString());
        }
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
