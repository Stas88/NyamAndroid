package com.st.nyam.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.st.nyam.R;
import com.st.nyam.util.Constants;

public class BaseDialogActivity extends SherlockActivity {
	
	private Dialog dialog = null;
	protected String login;
	protected String password;
	private EditText loginView;
	private EditText passwordView;
	private String TAG = "BaseDialogActivity";
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;
		switch(id){
			case Constants.DAILOG_INTERNET_UNAVAILABLE:
				builder = new AlertDialog.Builder(this);
				builder.setMessage("Интернет недоступен")
			       .setCancelable(false)
			       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	try {
			       	        dialog.dismiss();
			       	        dialog = null;
			       	    } catch (Exception e) {
			       	        Log.d(TAG, e.getMessage());
			       	    }
			           }
			       });
				dialog = builder.create();;
				break;
			case Constants.DAILOG_LOGINING: 
				builder  = new AlertDialog.Builder(this);
				LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.logining_layout, null);
				loginView = (EditText)layout.findViewById(R.id.loginView);
				passwordView = (EditText)layout.findViewById(R.id.passwordView);
				passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());

				builder.setView(layout);
				builder.setMessage("Авторизация...");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   login = loginView.getText().toString();
			        	   password = passwordView.getText().toString();
			        	   onOkPressed();
			           }
				});
				builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   onCancelPressed();
			           }
				});
				dialog = builder.create();
				Log.d(TAG, "Dialog built");
				break;
		}
		return dialog;
	}
	
	protected void onOkPressed() {
		try {
   	        dialog.dismiss();
   	        dialog = null;
   	    } catch (Exception e) {}
	}
	
	protected void onCancelPressed() {
		try {
   	        dialog.dismiss();
   	        dialog = null;
   	    } catch (Exception e) {}
	}


}
