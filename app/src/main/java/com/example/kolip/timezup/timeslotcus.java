package com.example.kolip.timezup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by kolip on 29-03-2018.
 */

public class timeslotcus extends BaseAdapter {
    Context context;
    int n1;
    private static LayoutInflater inflater = null;

    public timeslotcus(Context context, int n1) {
        this.context = context;
        this.n1 = n1;
    }


    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = view;


        return rowView;
    }
}
