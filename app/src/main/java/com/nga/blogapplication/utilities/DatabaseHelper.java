package com.nga.blogapplication.utilities;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.nga.blogapplication.model.BlogModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_BLOG = "blogs";
//    private static final String TABLE_TAG = "tags";
//    private static final String TABLE_TODO_TAG = "blog_tags";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column names
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SHORT_DESCRIPTION = "short_description";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_STATUS = "status";

//    // TAGS Table - column names
//    private static final String KEY_TAG_NAME = "tag_name";
//
//    // NOTE_TAGS Table - column names
//    private static final String KEY_TODO_ID = "blog_id";
//    private static final String KEY_TAG_ID = "tag_id";

    // Table Create Statements
    // BlogModel table create statement
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_BLOG + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_SHORT_DESCRIPTION + " TEXT,"
            + KEY_AUTHOR + " TEXT,"
            + KEY_IMAGE + " BLOB,"
            + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME"
            + ")";

//    // Tag table create statement
//    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG
//            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
//            + KEY_CREATED_AT + " DATETIME" + ")";
//
//    // blog_tag table create statement
//    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
//            + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
//            + KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_TODO);
//        db.execSQL(CREATE_TABLE_TAG);
//        db.execSQL(CREATE_TABLE_TODO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOG);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);

        // create new tables
        onCreate(db);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    // ------------------------ "blogs" table methods ----------------//

    /**
     * Creating a blog
     */
    public long createBlog(BlogModel blog) {
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] data = getBitmapAsByteArray(blog.getImage());

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, blog.getTitle());
        values.put(KEY_DESCRIPTION, blog.getDescription());
        values.put(KEY_SHORT_DESCRIPTION, blog.getShortDescription());
        values.put(KEY_AUTHOR, blog.getAuthor());
        values.put(KEY_IMAGE, data);
        values.put(KEY_STATUS, blog.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        return db.insert(TABLE_BLOG, null, values);
    }

    /**
     * get single blog
     */
    public BlogModel getBlog(long blog_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BLOG + " WHERE "
                + KEY_ID + " = " + blog_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        BlogModel blogObject = new BlogModel();

        if (c != null) {
            c.moveToFirst();
            blogObject.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            blogObject.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
            blogObject.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            blogObject.setShortDescription(c.getString(c.getColumnIndex(KEY_SHORT_DESCRIPTION)));
            blogObject.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
            blogObject.setImage(getImage(c.getBlob(5)));
            blogObject.setStatus(c.getInt(c.getColumnIndex(KEY_STATUS)));
            blogObject.setPostedDateTime(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
            c.close();
        }

        return blogObject;
    }

    /**
     * getting all blogs
     */
    public ArrayList<BlogModel> getAllBlogs() {
        ArrayList<BlogModel> blogs = new ArrayList<BlogModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_BLOG;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                BlogModel blogObject = new BlogModel();
                blogObject.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                blogObject.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                blogObject.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                blogObject.setShortDescription(c.getString(c.getColumnIndex(KEY_SHORT_DESCRIPTION)));
                blogObject.setAuthor(c.getString(c.getColumnIndex(KEY_AUTHOR)));
                blogObject.setImage(getImage(c.getBlob(5)));
                blogObject.setStatus(c.getInt(c.getColumnIndex(KEY_STATUS)));
                blogObject.setPostedDateTime(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to blog list
                blogs.add(blogObject);
            } while (c.moveToNext());
        }
        c.close();

        return blogs;
    }
//
//    /**
//     * getting all blogs under single tag
//     * */
//    public List<BlogModel> getAllBlogsByTag(String tag_name) {
//        List<BlogModel> blogs = new ArrayList<BlogModel>();
//
//        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " blogObject, "
//                + TABLE_TAG + " tg, " + TABLE_TODO_TAG + " tt WHERE tg."
//                + KEY_TAG_NAME + " = '" + tag_name + "'" + " AND tg." + KEY_ID
//                + " = " + "tt." + KEY_TAG_ID + " AND blogObject." + KEY_ID + " = "
//                + "tt." + KEY_TODO_ID;
//
//        Log.e(LOG, selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                BlogModel blogObject = new BlogModel();
//                blogObject.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                blogObject.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
//                blogObject.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
//
//                // adding to blog list
//                blogs.add(blogObject);
//            } while (c.moveToNext());
//        }
//
//        return blogs;
//    }

    /**
     * getting blog count
     */
    public int getBlogCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BLOG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * Updating a blog
     */
    public int updateBlog(BlogModel blog) {
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] data = getBitmapAsByteArray(blog.getImage());
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, blog.getTitle());
        values.put(KEY_DESCRIPTION, blog.getDescription());
        values.put(KEY_SHORT_DESCRIPTION, blog.getShortDescription());
        values.put(KEY_AUTHOR, blog.getAuthor());
        values.put(KEY_IMAGE, data);
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_STATUS, blog.getStatus());

        // updating row
        return db.update(TABLE_BLOG, values, KEY_ID + " = ?",
                new String[]{String.valueOf(blog.getId())});
    }

    /**
     * Deleting a blog
     */
    public void deleteBlog(long blog_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BLOG, KEY_ID + " = ?",
                new String[]{String.valueOf(blog_id)});
    }
//
//    // ------------------------ "tags" table methods ----------------//
//
//    /**
//     * Creating tag
//     */
//    public long createTag(Tag tag) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TAG_NAME, tag.getTagName());
//        values.put(KEY_CREATED_AT, getDateTime());
//
//        // insert row
//        long tag_id = db.insert(TABLE_TAG, null, values);
//
//        return tag_id;
//    }
//
//    /**
//     * getting all tags
//     * */
//    public List<Tag> getAllTags() {
//        List<Tag> tags = new ArrayList<Tag>();
//        String selectQuery = "SELECT  * FROM " + TABLE_TAG;
//
//        Log.e(LOG, selectQuery);
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Tag t = new Tag();
//                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                t.setTagName(c.getString(c.getColumnIndex(KEY_TAG_NAME)));
//
//                // adding to tags list
//                tags.add(t);
//            } while (c.moveToNext());
//        }
//        return tags;
//    }
//
//    /**
//     * Updating a tag
//     */
//    public int updateTag(Tag tag) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TAG_NAME, tag.getTagName());
//
//        // updating row
//        return db.update(TABLE_TAG, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(tag.getId()) });
//    }
//
//    /**
//     * Deleting a tag
//     */
//    public void deleteTag(Tag tag, boolean should_delete_all_tag_blogs) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // before deleting tag
//        // check if blogs under this tag should also be deleted
//        if (should_delete_all_tag_blogs) {
//            // get all blogs under this tag
//            List<BlogModel> allTagBlogs = getAllBlogsByTag(tag.getTagName());
//
//            // delete all blogs
//            for (BlogModel blog : allTagBlogs) {
//                // delete blog
//                deleteBlog(blog.getId());
//            }
//        }
//
//        // now delete the tag
//        db.delete(TABLE_TAG, KEY_ID + " = ?",
//                new String[] { String.valueOf(tag.getId()) });
//    }
//
//    // ------------------------ "blog_tags" table methods ----------------//
//
//    /**
//     * Creating blog_tag
//     */
//    public long createBlogTag(long blog_id, long tag_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TODO_ID, blog_id);
//        values.put(KEY_TAG_ID, tag_id);
//        values.put(KEY_CREATED_AT, getDateTime());
//
//        long id = db.insert(TABLE_TODO_TAG, null, values);
//
//        return id;
//    }
//
//    /**
//     * Updating a blog tag
//     */
//    public int updateNoteTag(long id, long tag_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TAG_ID, tag_id);
//
//        // updating row
//        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(id) });
//    }
//
//    /**
//     * Deleting a blog tag
//     */
//    public void deleteBlogTag(long id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_TODO, KEY_ID + " = ?",
//                new String[] { String.valueOf(id) });
//    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }
}