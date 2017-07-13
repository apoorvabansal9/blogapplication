package com.nga.blogapplication.utilities;

import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nga.blogapplication.R;
import com.nga.blogapplication.model.BlogModel;

import java.util.ArrayList;


public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.MyViewHolder> {

    private ArrayList<BlogModel> blogArrayList;



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, shortDescription, postedDataTime, author;
        ImageView postedImage,del;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.blog_title);
            shortDescription = (TextView) view.findViewById(R.id.blog_short_description);
            postedDataTime = (TextView) view.findViewById(R.id.blog_posted_time);
            author = (TextView) view.findViewById(R.id.blog_author);
            postedImage = (ImageView)view.findViewById(R.id.blog_image);
            del=(ImageView)view.findViewById(R.id.delete);
        }
    }


    public BlogListAdapter(ArrayList<BlogModel> blogArrayList) {
        this.blogArrayList = blogArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        BlogModel blogModel = blogArrayList.get(position);

        holder.title.setText(blogModel.getTitle());
        holder.shortDescription.setText(blogModel.getShortDescription());
        holder.author.setText(blogModel.getAuthor());

        String dateTime = GlobalConfig.convertRelativeTime(blogModel.getPostedDateTime());


        holder.postedDataTime.setText(dateTime);


        holder.postedImage.setImageBitmap(blogModel.getImage());
         holder.del.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

//                 AlertDialogBuil

             }
         });
    }

    @Override
    public int getItemCount() {
        return blogArrayList.size();
    }
}