package com.st.nyam.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.st.nyam.NyamApplication;
import com.st.nyam.R;
import com.st.nyam.factories.DataBaseFactory;
import com.st.nyam.models.Recipe;
import com.st.nyam.models.Step;

public class StepActivity extends SherlockActivity {

	private Recipe recipe = null;
	private DataBaseFactory db = null;
	private NyamApplication application = null;
	private String TAG = "StepActivity";
	private ArrayList<Step> steps;
	private int caret = 0;
	private int currentStep = 1;
	private int stepQuantity;
	private TextView viewStepBody = null;
	private TextView stepNumber = null;
	private String bodyText;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.step_page);
		viewStepBody = (TextView)findViewById(R.id.step_body);
		stepNumber = (TextView)findViewById(R.id.step_number);
		TextView recipeTitle = (TextView)findViewById(R.id.recipe_title);
		try {
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
			int id = recipe.getId();
			Log.d(TAG,"id: " + id);

			application = (NyamApplication)getApplication();
			db = application.getDB();
			Log.d(TAG,"GetDB done ");
			steps = db.getStepsByRecipe(id);
			stepQuantity = steps.size();

			Log.d(TAG,"steps: " + steps.size());
			if (stepQuantity > 0) {
				Step step = steps.get(0);
				bodyText =  step.getBody();
				recipeTitle.setText(recipe.getTitle());
			}
			updateInfo();
		} catch (Exception e) {
			Toast.makeText(this, "Error occured" + e.getMessage(), 200);
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.step_menu, (com.actionbarsherlock.view.Menu) menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void updateInfo() {
		viewStepBody.setText(bodyText);
		stepNumber.setText(currentStep + "/" + stepQuantity);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())  {
		case R.id.next: 
			if (currentStep != stepQuantity) {
				caret++;
				currentStep++;
				Step newStep = steps.get(caret);
				bodyText = newStep.getBody();
				updateInfo();
			} else {
				caret = 0;
				currentStep = 1;
				Step newStep = steps.get(caret);
				bodyText = newStep.getBody();
				updateInfo();
			}
			return true;
		case R.id.previous: 
			if (currentStep != 1) {
				caret--;
				currentStep--;
				Step newStep = steps.get(caret);
				bodyText = newStep.getBody();
				updateInfo();
			}
			else {
				caret = stepQuantity - 1;
				currentStep = stepQuantity;
				Step newStep = steps.get(caret);
				bodyText = newStep.getBody();
				updateInfo();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
