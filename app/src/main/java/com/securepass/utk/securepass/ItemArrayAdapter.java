package com.securepass.utk.securepass;

import android.app.Dialog;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by utk on 15-10-25.
 */
public class ItemArrayAdapter extends ArrayAdapter <Password> {


    private ArrayList<Password> list;
    private DatabaseHelper db;

    /*
    public ItemArrayAdapter(Context context, int textViewResourseId, ArrayList <Password> list) {
        super(context, textViewResourseId, list);
        this.list = list;

    }
    */


    public ItemArrayAdapter(Context context, int resource, ArrayList<Password> list, DatabaseHelper db) {
        super(context, resource, list);
        this.list = list;
        this.db = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //super.getView(position, convertView, parent);

        View v = convertView;

        if (v==null){
            LayoutInflater inflator = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflator.inflate (R.layout.item_view, parent, false);
        }
        if (list.get(position)!=null){

            TextView nameView = (TextView) v.findViewById (R.id.name_text);
            TextView passView = (TextView) v.findViewById(R.id.pass_text);

            nameView.setText(list.get(position).getName());
            passView.setText(list.get(position).getPass());

        }

        ImageButton optionsButton = (ImageButton) v.findViewById(R.id.options_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int index;
                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent();
                index = listView.getPositionForView(parentRow);

                final PopupMenu popup = new PopupMenu(getContext(), v);
                popup.getMenuInflater().inflate(R.menu.menu_options, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.edit_option:
                                final Dialog dialog = new Dialog(getContext());
                                dialog.setContentView(R.layout.new_pass_layout);


                                dialog.show();

                                return true;
                            case R.id.delete_option:
                                db.deleteItem(getItem(index).getName());
                                remove(getItem(index));
                                popup.dismiss();
                                notifyDataSetChanged();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();
            }
        });


        return v;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.e("ItemArrayAdapter", "Data change notified");
    }

}
