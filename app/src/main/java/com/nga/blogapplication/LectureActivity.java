package com.nga.blogapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class LectureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.lecture_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);

        int id = item.getItemId();
        switch (id){
            case R.id.action_create:
                Log.e("action","create");
                initiateDelete();
                return true;

            case R.id.action_delete:
                Log.e("action","delete");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initiateDelete(){

    }
}
