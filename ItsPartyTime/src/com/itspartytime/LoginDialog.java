package com.itspartytime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginDialog extends DialogFragment
{
    private EditText nameField;
	private EditText emailField;
	private EditText passwordField;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
    {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.login_dialog_layout, null);
		emailField = (EditText) mLinearLayout.findViewById(R.id.login_email_field);
		passwordField = (EditText) mLinearLayout.findViewById(R.id.login_password_field);
        nameField = (EditText) mLinearLayout.findViewById(R.id.login_name_field);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(mLinearLayout);
		builder.setTitle(R.string.login_title_text);
		builder.setPositiveButton(R.string.login_button_text, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Party.setEmail(emailField.getText().toString());
				Party.setPassword(passwordField.getText().toString());

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
