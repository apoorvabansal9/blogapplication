package com.nga.blogapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nga.blogapplication.model.BlogModel;
import com.nga.blogapplication.utilities.DatabaseHelper;

public class PostActivity extends AppCompatActivity {
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        


    }
}
