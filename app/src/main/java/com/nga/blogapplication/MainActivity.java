package com.nga.blogapplication;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nga.blogapplication.model.BlogModel;
import com.nga.blogapplication.utilities.AndroidDatabaseManager;
import com.nga.blogapplication.utilities.BlogListAdapter;
import com.nga.blogapplication.utilities.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private BlogListAdapter mAdapter;
    private ArrayList<BlogModel> blogArrayList = new ArrayList<BlogModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new BlogListAdapter(blogArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        TextView post=(TextView) findViewById(R.id.post1);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent n=new Intent(MainActivity.this,PostActivity.class);
                startActivity(n);
            }
        });

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);




    }



    private void addData(){
        BlogModel blogModel = new BlogModel();
        blogModel.setTitle("Blog title 1 !!");
        blogModel.setShortDescription("This is a simple blog !! This is a simple blog !!");
        blogModel.setAuthor("Asad Abbas");
        blogModel.setImage(BitmapFactory.decodeResource(getResources(),
                R.drawable.nav_bar_header));

        BlogModel blogModel1 = new BlogModel();
        blogModel1.setTitle("Blog title 2 !!");
        blogModel1.setShortDescription("This is a simple blog 2 !! This is a simple blog !!");
        blogModel1.setAuthor("Shabi Abbas");
        blogModel1.setImage(BitmapFactory.decodeResource(getResources(),
                R.drawable.nav_bar_header));

        long todo1_id = db.createBlog(blogModel);
        long todo2_id = db.createBlog(blogModel1);
    }

    private void prepareBlogData() {
        ArrayList<BlogModel> arrayList = db.getAllBlogs();
        for (int i = 0; i < arrayList.size(); i++) {
            blogArrayList.add(arrayList.get(i));
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(getApplicationContext());

        if (blogArrayList.size() > 0) {
            blogArrayList.clear();
        }
        prepareBlogData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Don't forget to close database connection
        db.closeDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.closeDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        int pos = 1;
        switch (id) {
            case R.id.action_options:
                Intent n = new Intent(MainActivity.this, AndroidDatabaseManager.class);
                startActivity(n);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
