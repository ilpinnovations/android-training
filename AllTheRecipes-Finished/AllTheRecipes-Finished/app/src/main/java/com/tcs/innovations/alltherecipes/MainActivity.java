package com.tcs.innovations.alltherecipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = MainActivity.class.getSimpleName();

  private ListView mListView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final Context context = this;

    // Get data to display
    final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);

    // Create adapter
    RecipeAdapter adapter = new RecipeAdapter(this, recipeList);

    // Create list view
    mListView = (ListView) findViewById(R.id.recipe_list_view);
    mListView.setAdapter(adapter);

    // Set what happens when a list view item is clicked
    mListView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Recipe selectedRecipe = recipeList.get(position);

        Intent detailIntent = new Intent(context, RecipeDetailActivity.class);
        detailIntent.putExtra("title", selectedRecipe.title);
        detailIntent.putExtra("url", selectedRecipe.instructionUrl);

        startActivity(detailIntent);
      }

    });
  }


}
