package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mOriginLabelTextView;
    private TextView mOriginTextView;
    private TextView mAlsoKnownLabelTextView;
    private TextView mAlsoKnownTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;

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

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

    private void populateUI(Sandwich sandwich) {
        mOriginLabelTextView = findViewById(R.id.detail_place_of_origin_label);
        mOriginTextView = findViewById(R.id.origin_tv);
        mAlsoKnownLabelTextView = findViewById(R.id.detail_also_known_as_label);
        mAlsoKnownTextView = findViewById(R.id.also_known_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);

        String alsoKnownAs = fromListToString(sandwich.getAlsoKnownAs());
        if (checkStringLength(alsoKnownAs)) {
            mAlsoKnownTextView.setText(alsoKnownAs);
        } else {
            mAlsoKnownLabelTextView.setVisibility(View.GONE);
            mAlsoKnownTextView.setVisibility(View.GONE);
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (checkStringLength(placeOfOrigin)) {
            mOriginTextView.setText(placeOfOrigin);
        } else {
            mOriginLabelTextView.setVisibility(View.GONE);
            mOriginTextView.setVisibility(View.GONE);
        }

        String descriptio = sandwich.getDescription();
        if (checkStringLength(descriptio)) {
            mDescriptionTextView.setText(descriptio);
        }

        String ingredients = fromListToString(sandwich.getIngredients());
        if (checkStringLength(ingredients)) {
            mIngredientsTextView.setText(ingredients);
        }
    }

    private String fromListToString(List<String> list) {
        return TextUtils.join(", ", list);
    }

    private boolean checkStringLength(String item) {
        return item.length() > 0;
    }
}
