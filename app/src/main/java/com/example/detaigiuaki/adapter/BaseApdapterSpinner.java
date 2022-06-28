package com.example.detaigiuaki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.detaigiuaki.R;

import java.util.ArrayList;

public class BaseApdapterSpinner extends BaseAdapter {
    Context context;
    int images[];
    ArrayList<String> text;
    LayoutInflater inflter;

    public BaseApdapterSpinner(Context applicationContext, int[] flags, ArrayList<String> fruit) {
        this.context = applicationContext;
        this.images = flags;
        this.text = fruit;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return text.size();
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
        view = inflter.inflate(R.layout.layout_custom_spinner, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.tvSpinner);
        icon.setImageResource(images[i]);
        names.setText(text.get(i));
        return view;
    }
}
