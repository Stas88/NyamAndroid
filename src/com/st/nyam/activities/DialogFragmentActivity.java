package com.st.nyam.activities;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.st.nyam.R;

public class DialogFragmentActivity extends SherlockDialogFragment implements OnEditorActionListener{
	
	 public interface EditNameDialogListener {
	        void onFinishEditDialog(String inputText, String inputText2);
	    }

	private EditText loginView;
	private EditText passwordView;
	public View view;
	public View bar;

    public DialogFragmentActivity() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.logining_layout, container);
        loginView = (EditText) view.findViewById(R.id.loginView);
        passwordView = (EditText) view.findViewById(R.id.passwordView);
        passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        
        Button ok = (Button) view.findViewById(R.id.ok_button);
        ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
            	
        		bar = (ProgressBar)view.findViewById(R.id.progressbar_dialoig1);
        		bar.setVisibility(View.VISIBLE);
            	EditNameDialogListener activity = (EditNameDialogListener)getActivity();
            	if(loginView.getText() != null && passwordView.getText()!= null) {
            		activity.onFinishEditDialog(loginView.getText().toString(),passwordView.getText().toString());
            	} else {
            		//Error Dialog
            	}
            } 
        });

        Button cancel = (Button) view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
            	DialogFragmentActivity.this.dismiss();
            } 
        });
        getDialog().setTitle("Введите логин и пароль");
        return view;
    }

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
            activity.onFinishEditDialog(loginView.getText().toString(),passwordView.getText().toString());
            this.dismiss();
            return true;
        }
		return false;
	}
	
	public void clearFialds() {
		loginView.setText("");
		passwordView.setText("");
		bar.setVisibility(View.GONE);
	}
}