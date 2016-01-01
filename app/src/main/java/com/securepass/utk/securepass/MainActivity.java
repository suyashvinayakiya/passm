package com.securepass.utk.securepass;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Password> list;
    private DatabaseHelper db;
    private ItemArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this, 1);

        list = db.getAllItems();

        ListView listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ItemArrayAdapter(this,
                R.layout.item_view, list, db);
        listView.setAdapter(listAdapter);


        Button newButton = (Button) findViewById(R.id.new_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.new_pass_layout);
                dialog.setTitle("New Password");

                Button saveButton = (Button) dialog.findViewById(R.id.save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText nameEditText = (EditText) dialog.findViewById(R.id.name_editText);
                        EditText passEditText = (EditText) dialog.findViewById(R.id.pass_editText);
                        db.insertItem(nameEditText.getText().toString(), passEditText.getText().toString());
                        list.add(new Password(nameEditText.getText().toString(), passEditText.getText().toString()));
                        dialog.dismiss();
                    }
                });
                Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_destroySelf) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setMessage("This will clear all passwords and lead you to uninstall the app. Are you Sure?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                    db.deleteAllItems();
                    list.clear();
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "It was a pleasure helping you manage passwords. Goodbye, dear user.", Toast.LENGTH_LONG).show();

                    Uri packageURI = Uri.parse("package:" + getApplication().getPackageName());
                    Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                    startActivity(uninstallIntent);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener()

                    {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Knew you wouldn't let me go!", Toast.LENGTH_LONG).show();
                            }
                        }

            );

                AlertDialog dialog = builder.create();

                dialog.show();


                return true;
            }

            return super.onOptionsItemSelected(item);
    }


}
