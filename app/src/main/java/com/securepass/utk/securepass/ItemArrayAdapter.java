package com.securepass.utk.securepass;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by utk on 15-10-25.
 */
public class ItemArrayAdapter extends ArrayAdapter <Password> {

    ArrayList<Password> list;

    /*
    public ItemArrayAdapter(Context context, int textViewResourseId, ArrayList <Password> list) {
        super(context, textViewResourseId, list);
        this.list = list;

    }
    */

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.e("ItemArrayAdapter", "Data change notified");
    }

    public ItemArrayAdapter(Context context, int resource, ArrayList<Password> list) {
        super(context, resource, list);
        this.list = list;
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

        return v;
    }

}
