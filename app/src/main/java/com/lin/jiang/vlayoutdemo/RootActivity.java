package com.lin.jiang.vlayoutdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author jianglin
 */
public class RootActivity extends ListActivity {

    private String[] mTitles = {
            VxLayoutActivity.class.getSimpleName(),
            MainActivity.class.getSimpleName(),
            TestActivity.class.getSimpleName(),
            OnePlusNLayoutActivity.class.getSimpleName()
    };

    private Class[] mActivities = {
            VxLayoutActivity.class,
            MainActivity.class,
            TestActivity.class,
            OnePlusNLayoutActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mTitles));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(this, mActivities[position]));
    }
}
