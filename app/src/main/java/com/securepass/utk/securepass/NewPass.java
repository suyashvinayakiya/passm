package com.securepass.utk.securepass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by utk on 15-10-16.
 */

/* Activity not being used
Changed to dialog box
 */

public class NewPass extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_pass_layout);

        final EditText nameInputEditText = (EditText) findViewById(R.id.name_editText);
        final EditText passInputEditText = (EditText) findViewById(R.id.pass_editText);


        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameInputEditText.getText().toString().equals("") ||
                        passInputEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),R.string.blank_field_error,
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name", nameInputEditText.getText().toString());
                    intent.putExtra("pass", passInputEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
