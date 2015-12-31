package com.securepass.utk.securepass;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DatabaseHelper db = new DatabaseHelper(this, 1);
        ArrayList<Password> list;

//
//        if (getIntent().getExtras() == null) {
//            Log.e("msg", "extras are null");
//
//        }
//        //fix issue
//        // blank record created evertime app is opened
//        else {
//            Bundle extras = getIntent().getExtras();  //fix issue
//            db.insertItem (extras.getString("name"), extras.getString("pass"));
//        }
        list = db.getAllItems();

        if (savedInstanceState == null){

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        final ItemArrayAdapter listAdapter = new ItemArrayAdapter(this,
                R.layout.item_view, list);
        listView.setAdapter(listAdapter);


        Button newButton = (Button) findViewById(R.id.new_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.new_pass_layout);
                dialog.setTitle("New Password");
                Log.e("MainActivity", "title set");


                Button saveButton = (Button) dialog.findViewById(R.id.save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText nameEditText = (EditText) dialog.findViewById(R.id.name_editText);
                        EditText passEditText = (EditText) dialog.findViewById(R.id.pass_editText);
                        db.insertItem(nameEditText.getText().toString(), passEditText.getText().toString());
                        listAdapter.notifyDataSetChanged();
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
                Log.e("MainActivity", "on clicks set");

                dialog.show();
            }
        });
        Button deleteAllButton = (Button) findViewById(R.id.delete_all);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllItems();
            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
