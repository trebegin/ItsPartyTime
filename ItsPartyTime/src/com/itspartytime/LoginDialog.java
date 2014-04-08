package com.itspartytime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginDialog extends DialogFragment
{
    private EditText nameField;
	private EditText emailField;
	private EditText passwordField;
    private CheckBox saveCheckBox;
    private SharedPreferences.Editor editor;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
    {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.login_dialog_layout, null);
		emailField = (EditText) mLinearLayout.findViewById(R.id.login_email_field);
		passwordField = (EditText) mLinearLayout.findViewById(R.id.login_password_field);
        nameField = (EditText) mLinearLayout.findViewById(R.id.login_name_field);
        saveCheckBox = (CheckBox) mLinearLayout.findViewById(R.id.saveCheckBox);

        // Editor for the Shared Preference Manager
        editor = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).edit();

        // Gets the savedEmail and savedPassword if they exist
        String savedEmail = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getString("savedEmail", "");
        String savedPassword = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getString("savedPass", "");

        // Checks if they are not empty and sets the fields
        if(savedEmail != "" && savedPassword != "")
        {
            emailField.setText(savedEmail);
            passwordField.setText(savedPassword);
            saveCheckBox.setChecked(true);
        }

		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(mLinearLayout);
		builder.setTitle(R.string.login_title_text);
		builder.setPositiveButton(R.string.login_button_text, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

                // Sets the email and password in party
                Party.setEmail(emailField.getText().toString());
				Party.setPassword(passwordField.getText().toString());

                // If save info is check, push the email and password to the saved preferences
                if(saveCheckBox.isChecked())
                {
                    editor.putString("savedEmail", emailField.getText().toString());
                    editor.putString("savedPass", passwordField.getText().toString());
                    editor.commit();
                }

                // Otherwise clear the preferences
                else
                {
                    editor.clear();
                    editor.commit();
                }

                // Sets the party name to entered string
                if(nameField.getText().toString() == "")
                    Party.setPartyName("The Party");
                else
                    Party.setPartyName(nameField.getText().toString());

				Party.login();
			}
		});
		return builder.create();
	}
	
}
