package suriyon.cs.ubru.mycontact.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import suriyon.cs.ubru.mycontact.model.Contact;

public class SQLiteHelper extends SQLiteOpenHelper {
    private Context context;
    private static String DATABASE_NAME = "contactdb";
    private static int DATABASE_VERSION = 1;
    private static String TABLE_NAME = "contact";
    private static String COLUMN_ID = "id";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_MOBILE = "mobile";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_MOBILE + " TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }

    public boolean insert(Contact contact) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_MOBILE, contact.getMobile());
        long row = db.insert(TABLE_NAME, null, values);
        if(row>0)
            result = true;
        db.close();
        return result;
    }
    public boolean update(Contact contact) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_MOBILE, contact.getMobile());
        int row = db.update(TABLE_NAME,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        if(row>0)
            result = true;
        db.close();
        return result;
    }
    public boolean delete(int id) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        int row = db.delete(TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        if(row>0)
            result = true;
        db.close();
        return result;
    }
    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME;
        db.execSQL(sql);
        db.close();
    }
    public List<Contact> select() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do{
                contacts.add(new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }
    public List<Contact> select(String name) {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE '%" + name + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do{
                contacts.add(new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public Contact select(int id) {
        Contact contact = new Contact();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            contact.setId(cursor.getInt(0));
            contact.setName(cursor.getString(1));
            contact.setMobile(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return contact;
    }
}
