package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myusers.db";
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        Log.i("Database operations", "creating a table.");
        try {
            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_FOLLOWED + " INTEGER" + ")";
            db.execSQL(CREATE_USERS_TABLE);

            //Generate 20 users
            for (int i = 0; i < 20; i++){
                int name = new Random().nextInt(999999999);
                int description = new Random().nextInt(999999999);
                boolean followed = new Random().nextBoolean();


                User user = new User("Name" + String.valueOf(name), "Description" + String.valueOf(description), followed);

                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME, user.getName());
                values.put(COLUMN_DESCRIPTION, user.getDescription());
                if(followed){
                    values.put(COLUMN_FOLLOWED, 1);
                }else{
                    values.put(COLUMN_FOLLOWED, 0);
                }

                db.insert(TABLE_USERS, null, values);
            }
            Log.i("Database Operations", "Table created successfully.");
        }
        catch(SQLiteException e){
            Log.i("Database Operations", "Error creating table", e);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }


    //  READ all user records
    public ArrayList<User> getUsers() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt((int)cursor.getColumnIndex("id"));
            String name = cursor.getString((int)cursor.getColumnIndex("name"));
            String description = cursor.getString((int)cursor.getColumnIndex("description"));
            int f = cursor.getInt((int)cursor.getColumnIndex("followed"));

            boolean followed;
            if(f == 0){
                followed = false;
            }else {
                followed = true;
            }

            User user = new User(name, description, id, followed);
            userList.add(user);
        }
        cursor.close();
//      db.close();
        return userList;
    }

    public void updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        boolean followed = user.getFollowed();
        int f;
        if(followed){
            f = 1;
        }else{
            f = 0;
        }

        values.put(COLUMN_FOLLOWED, f);

        String clause = "id=?";
        String[] args = {String.valueOf(user.getId())};
        db.update(TABLE_USERS, values, clause, args);
//      db.close();
    }


    @Override
    public void close() {
        Log.i("Database Operations", "Database is closed.");
        super.close();
    }

}
