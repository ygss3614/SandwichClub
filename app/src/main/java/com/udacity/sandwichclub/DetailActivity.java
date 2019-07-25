package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    private TextView originTextView;
    private TextView alsoKnownTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private ImageView ingredientsIv;
    private ProgressBar imageLoadingProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTextView = findViewById(R.id.origin_tv);
        alsoKnownTextView = findViewById(R.id.also_known_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        imageLoadingProgressBar = findViewById(R.id.pb_image_loading);

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

        populateUI(sandwich);
        imageLoadingProgressBar.setVisibility(View.VISIBLE);
        // reference: https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#showing-progressbar-with-picasso
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess() {
                        if (imageLoadingProgressBar != null){
                            imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onError() {
                        if (imageLoadingProgressBar != null){
                            imageLoadingProgressBar.setVisibility(View.INVISIBLE);
                        }

                        Toast.makeText(DetailActivity.this, R.string.load_image_error_message, Toast.LENGTH_SHORT).show();
                    }
                });


        setTitle(sandwich.getMainName());
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        for (String alsoKnownas: sandwich.getAlsoKnownAs()){
            alsoKnownTextView.append(alsoKnownas + "\n");
        }

        for (String ingredient: sandwich.getIngredients()){
            ingredientsTextView.append(ingredient + "\n");
        }

        descriptionTextView.setText(sandwich.getDescription());
        originTextView.setText(sandwich.getPlaceOfOrigin());

    }
}
