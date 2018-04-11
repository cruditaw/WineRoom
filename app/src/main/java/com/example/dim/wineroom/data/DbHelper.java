package com.example.dim.wineroom.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dim.wineroom.data.entities.Cellar;
import com.example.dim.wineroom.data.entities.Grape;
import com.example.dim.wineroom.data.entities.Grower;
import com.example.dim.wineroom.data.entities.User;
import com.example.dim.wineroom.data.entities.Wine;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    /**
     * Singleton object
     */
    private static DbHelper database;


    // ---------------------------------------------------------------------------------------------
    // DATABASE INFORMATIONS
    // ---------------------------------------------------------------------------------------------
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "wineCave";

    // ---------------------------------------------------------------------------------------------
    // DATABASE TABLES NAMES
    // ---------------------------------------------------------------------------------------------
    public static final String TABLE_GRAPE = "grape";
    public static final String TABLE_USER = "wuser";
    public static final String TABLE_GROWER = "grower";
    public static final String TABLE_CELLAR = "cellar";
    public static final String TABLE_WINE = "wine";

    // ---------------------------------------------------------------------------------------------
    // DATABASE TABLES FIELDS
    // ---------------------------------------------------------------------------------------------
    // GENERIC
    public static final String KEY_ID = "id";

    // GRAPES
    public static final String KEY_GRAPE_LABEL = "grape_label";

    // USER
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_LASTNAME = "user_lastname";
    public static final String KEY_USER_MAIL = "user_mail";

    // GROWER
    public static final String KEY_GROWER_LABEL = "grower_label";
    public static final String KEY_GROWER_ADDR = "grower_addr";
    public static final String KEY_GROWER_ADDR1 = "grower_addr1";
    public static final String KEY_GROWER_ADDR2 = "grower_addr2";
    public static final String KEY_GROWER_CITY = "grower_city";
    public static final String KEY_GROWER_ZIP = "grower_zip"; // stands for postcode in the usa
    public static final String KEY_GROWER_PHONE = "grower_phone";
    public static final String KEY_GROWER_MAIL = "grower_mail";

    // WINE
    public static final String KEY_WINE_LABEL = "wine_label";
    public static final String KEY_WINE_YARD = "wine_yard"; // shorter than vineyard ..
    public static final String KEY_WINE_YEAR = "wine_year";
    public static final String KEY_WINE_GRAPE = "wine_grape";
    public static final String KEY_WINE_GROWER = "wine_grower";

    // CELLAR
    public static final String KEY_CELLAR_WINE = "cellar_wine";
    public static final String KEY_CELLAR_USER = "cellar_user";
    public static final String KEY_CELLAR_QTY = "cellar_qty";

    // ---------------------------------------------------------------------------------------------
    // DATABASE TABLES CREATE STATEMENTS
    // ---------------------------------------------------------------------------------------------
    private static final String CREATE_GRAPE_TABLE =
            "CREATE TABLE " + TABLE_GRAPE + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_GRAPE_LABEL + " TEXT);";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_USER_NAME + " TEXT, " +
                    KEY_USER_LASTNAME + " TEXT, " +
                    KEY_USER_MAIL + " TEXT);";

    private static final String CREATE_GROWER_TABLE =
            "CREATE TABLE " + TABLE_GROWER + "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_GROWER_LABEL + " TEXT, "
                    + KEY_GROWER_ADDR + " TEXT, "
                    + KEY_GROWER_ADDR1 + " TEXT, "
                    + KEY_GROWER_ADDR2 + " TEXT, "
                    + KEY_GROWER_CITY + " TEXT, "
                    + KEY_GROWER_ZIP + " TEXT, "
                    + KEY_GROWER_PHONE + " TEXT, "
                    + KEY_GROWER_MAIL + " TEXT);";

    private static final String CREATE_WINE_TABLE =
            "CREATE TABLE " + TABLE_WINE + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_WINE_LABEL + " TEXT, "
                    + KEY_WINE_YARD + " TEXT, "
                    + KEY_WINE_YEAR + " TEXT, "
                    + KEY_WINE_GRAPE + " INTEGER, "
                    + KEY_WINE_GROWER + " INTEGER, "
                    + " FOREIGN KEY (" + KEY_WINE_GRAPE + ") REFERENCES "
                    + TABLE_GRAPE + "(" + KEY_ID + "), "
                    + " FOREIGN KEY (" + KEY_WINE_GROWER + ") REFERENCES "
                    + TABLE_GROWER + "(" + KEY_ID + "));";

    private static final String CREATE_CELLAR_TABLE =
            "CREATE TABLE " + TABLE_CELLAR + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_CELLAR_WINE + " INTEGER, "
                    + KEY_CELLAR_USER + " INTEGER, "
                    + KEY_CELLAR_QTY + " INTEGER, "
                    + " FOREIGN KEY (" + KEY_CELLAR_WINE + ") REFERENCES "
                    + TABLE_WINE + "(" + KEY_ID + "), "
                    + " FOREIGN KEY (" + KEY_CELLAR_USER + ") REFERENCES "
                    + TABLE_USER + "(" + KEY_ID + "));";


    /**
     * Private Constructor to avoid mutiple instances to be created
     * @param context Application context
     */
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * get singleton database object
     * @param context Application context (ie. via getApplicationcontext())
     * @return nonNull database unique instance
     */
    public static synchronized DbHelper getInstance(Context context) {
        if (null == database) {
            database = new DbHelper(context);
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_GRAPE_TABLE);
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_GROWER_TABLE);
        sqLiteDatabase.execSQL(CREATE_WINE_TABLE);
        sqLiteDatabase.execSQL(CREATE_CELLAR_TABLE);
        Log.i("DB_HELPER", "onCreate passed");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CELLAR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WINE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROWER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GRAPE);

        onCreate(sqLiteDatabase);
    }

    // ---------------------------------------------------------------------------------------------
    // INSERT METHODS
    // ---------------------------------------------------------------------------------------------
    public long insertGrape(Grape grape) {
        SQLiteDatabase db = getWritableDatabase();

        long grapeId =-1;
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_GRAPE_LABEL, grape.getGrape_label());
            grapeId = db.insert(TABLE_GRAPE, null, values);
            Log.i("DB_HELPER", "insertGrape result : " + grapeId);
        } catch (SQLException exc) {
            Log.i("DEBUG_DBHELPER", "insertGrape: EXCEPTION\n"+exc.getMessage());
            // no setTransactionSucessfull !
        }
        return grapeId;
    }

    public long insertUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUser_name());
        values.put(KEY_USER_LASTNAME, user.getUser_lastname());
        values.put(KEY_USER_MAIL, user.getUser_mail());
        long userId = db.insert(TABLE_USER, null, values);
        return userId;
    }

    public long insertGrower(Grower grower) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROWER_LABEL, grower.getGrower_label());
        values.put(KEY_GROWER_ADDR, grower.getGrower_addr());
        values.put(KEY_GROWER_ADDR1, grower.getGrower_addr1());
        values.put(KEY_GROWER_ADDR2, grower.getGrower_addr2());
        values.put(KEY_GROWER_CITY, grower.getGrower_city());
        values.put(KEY_GROWER_ZIP, grower.getGrower_zip());
        values.put(KEY_GROWER_PHONE, grower.getGrower_phone());
        values.put(KEY_GROWER_MAIL, grower.getGrower_mail());
        long growerId = db.insert(TABLE_GROWER, null, values);
        return growerId;
    }

    public long insertWine(Wine wine) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WINE_LABEL, wine.getWine_label());
        values.put(KEY_WINE_YARD, wine.getWine_yard());
        values.put(KEY_WINE_YEAR, wine.getWine_year());

        if (wine.getWine_grape().getId() != null) {
            values.put(KEY_WINE_GRAPE, wine.getWine_grape().getId());
            Log.i("DB_HELPER", "insertWine: grape reference passed with id "+wine.getWine_grape().getId());
        } else {
            long grapeId = insertGrape(wine.getWine_grape());
            values.put(KEY_WINE_GRAPE, grapeId);
            Log.i("DB_HELPER", "insertWine: grape insertion passed with id "+grapeId);
        }

        if (wine.getWine_grower().getId() != null) {
            values.put(KEY_WINE_GROWER, wine.getWine_grower().getId());
            Log.i("DB_HELPER", "insertWine: grower reference passed with id "+wine.getWine_grower().getId());
        } else {
            long growId = insertGrower(wine.getWine_grower());
            values.put(KEY_WINE_GROWER, growId);
            Log.i("DB_HELPER", "insertWine: grower insertion passed with id "+growId);
        }

        long wineId = db.insert(TABLE_WINE, null, values);
        return wineId;
    }

    public long insertCellar(Cellar cellar) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        if (cellar.getCellar_wine().getId() != null) {
            values.put(KEY_CELLAR_WINE, cellar.getCellar_wine().getId());
            Log.i("DB_HELPER", "insertCellar: wine reference passed with id "+cellar.getCellar_wine().getId());
        } else {
            long growId = insertWine(cellar.getCellar_wine());
            values.put(KEY_CELLAR_WINE, growId);
            Log.i("DB_HELPER", "insertCellar: wine insertion passed with id "+growId);
        }

        if (cellar.getCellar_user().getId() != null) {
            values.put(KEY_CELLAR_USER, cellar.getCellar_user().getId());
            Log.i("DB_HELPER", "insertCellar: user reference passed with id "+cellar.getCellar_user().getId());
        } else {
            long growId = insertUser(cellar.getCellar_user());
            values.put(KEY_CELLAR_USER, growId);
            Log.i("DB_HELPER", "insertCellar: user insertion passed with id "+growId);
        }

        values.put(KEY_CELLAR_QTY, cellar.getCellar_qty());

        long wineId = db.insert(TABLE_CELLAR, null, values);
        return wineId;
    }

    // ---------------------------------------------------------------------------------------------
    // UPDATE METHODS
    // ---------------------------------------------------------------------------------------------
    public int updateGrape(Grape grape) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_GRAPE_LABEL, grape.getGrape_label());

        return db.update(TABLE_GRAPE, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(grape.getId())});
    }


    public int updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUser_name());
        values.put(KEY_USER_LASTNAME, user.getUser_lastname());
        values.put(KEY_USER_MAIL, user.getUser_mail());

        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public int updateGrower(Grower grower) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GROWER_LABEL, grower.getGrower_label());
        values.put(KEY_GROWER_ADDR, grower.getGrower_addr());
        values.put(KEY_GROWER_ADDR1, grower.getGrower_addr1());
        values.put(KEY_GROWER_ADDR2, grower.getGrower_addr2());
        values.put(KEY_GROWER_CITY, grower.getGrower_city());
        values.put(KEY_GROWER_ZIP, grower.getGrower_zip());
        values.put(KEY_GROWER_PHONE, grower.getGrower_phone());
        values.put(KEY_GROWER_MAIL, grower.getGrower_mail());

        return db.update(TABLE_GROWER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(grower.getId())});
    }

    public int updateWine(Wine wine) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WINE_LABEL, wine.getWine_label());
        values.put(KEY_WINE_YARD, wine.getWine_yard());
        values.put(KEY_WINE_YEAR, wine.getWine_year());

        if (wine.getWine_grape().getId() != null) {
            values.put(KEY_WINE_GRAPE, wine.getWine_grape().getId());
            Log.i("DB_HELPER", "updateWine: grape reference passed with id "+wine.getWine_grape().getId());
        } else {
            long grapeId = insertGrape(wine.getWine_grape());
            values.put(KEY_WINE_GRAPE, grapeId);
            Log.i("DB_HELPER", "updateWine: grape insertion passed with id "+grapeId);
        }

        if (wine.getWine_grower().getId() != null) {
            values.put(KEY_WINE_GROWER, wine.getWine_grower().getId());
            Log.i("DB_HELPER", "updateWine: grower reference passed with id "+wine.getWine_grower().getId());
        } else {
            long growId = insertGrower(wine.getWine_grower());
            values.put(KEY_WINE_GROWER, growId);
            Log.i("DB_HELPER", "updateWine: grower insertion passed with id "+growId);
        }

        return db.update(TABLE_WINE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(wine.getId())});
    }

    public int updateCellar(Cellar cellar) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        if (cellar.getCellar_wine().getId() != null) {
            values.put(KEY_CELLAR_WINE, cellar.getCellar_wine().getId());
            Log.i("DB_HELPER", "insertCellar: wine reference passed with id "+cellar.getCellar_wine().getId());
        } else {
            long growId = insertWine(cellar.getCellar_wine());
            values.put(KEY_CELLAR_WINE, growId);
            Log.i("DB_HELPER", "insertCellar: wine insertion passed with id "+growId);
        }

        if (cellar.getCellar_user().getId() != null) {
            values.put(KEY_CELLAR_USER, cellar.getCellar_user().getId());
            Log.i("DB_HELPER", "insertCellar: user reference passed with id "+cellar.getCellar_user().getId());
        } else {
            long growId = insertUser(cellar.getCellar_user());
            values.put(KEY_CELLAR_USER, growId);
            Log.i("DB_HELPER", "insertCellar: user insertion passed with id "+growId);
        }

        values.put(KEY_CELLAR_QTY, cellar.getCellar_qty());

        return db.update(TABLE_CELLAR, values, KEY_ID + " = ?",
                new String[]{String.valueOf(cellar.getId())});
    }


    // ---------------------------------------------------------------------------------------------
    // GETxFROMID METHODS
    // ---------------------------------------------------------------------------------------------
    public Grape getGrapeFromId(long grapeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GRAPE + " WHERE " + KEY_ID + " = " + grapeId+";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();

            Grape grape = new Grape();
            grape.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            grape.setGrape_label(c.getString(c.getColumnIndex(KEY_GRAPE_LABEL)));
            return grape;
        }


        return null;
    }


    public User getUserFromId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_ID + " = " + userId+";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();

            User user = new User();
            user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            user.setUser_name(c.getString(c.getColumnIndex(KEY_USER_NAME)));
            user.setUser_lastname(c.getString(c.getColumnIndex(KEY_USER_LASTNAME)));
            user.setUser_mail(c.getString(c.getColumnIndex(KEY_USER_MAIL)));
            return user;
        }

        return null;
    }


    public Grower getGrowerFromId(long growerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GROWER + " WHERE " + KEY_ID + " = " + growerId+";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
            Grower grower = new Grower();
            grower.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            grower.setGrower_label(c.getString(c.getColumnIndex(KEY_GROWER_LABEL)));
            grower.setGrower_addr(c.getString(c.getColumnIndex(KEY_GROWER_ADDR)));
            grower.setGrower_addr1(c.getString(c.getColumnIndex(KEY_GROWER_ADDR1)));
            grower.setGrower_addr2(c.getString(c.getColumnIndex(KEY_GROWER_ADDR2)));
            grower.setGrower_city(c.getString(c.getColumnIndex(KEY_GROWER_CITY)));
            grower.setGrower_zip(c.getString(c.getColumnIndex(KEY_GROWER_ZIP)));
            grower.setGrower_phone(c.getString(c.getColumnIndex(KEY_GROWER_PHONE)));
            grower.setGrower_mail(c.getString(c.getColumnIndex(KEY_GROWER_MAIL)));
            return grower;
        }

        return null;
    }

    public Wine getWineFromId(long growerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_WINE + " WHERE " + KEY_ID + " = " + growerId+";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
            Wine wine = new Wine();
            wine.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            wine.setWine_label(c.getString(c.getColumnIndex(KEY_WINE_LABEL)));
            wine.setWine_yard(c.getString(c.getColumnIndex(KEY_WINE_YARD)));
            wine.setWine_year(c.getString(c.getColumnIndex(KEY_WINE_YEAR)));

            Grape g = getGrapeFromId(c.getInt(c.getColumnIndex(KEY_WINE_GRAPE)));
            wine.setWine_grape(g);

            Grower gr = getGrowerFromId(c.getInt(c.getColumnIndex(KEY_WINE_GROWER)));
            wine.setWine_grower(gr);
            return wine;
        }

        return null;
    }

    public Cellar getCellarFromId(long growerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CELLAR + " WHERE " + KEY_ID + " = " + growerId+";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
            Cellar cellar = new Cellar();
            cellar.setId(c.getInt(c.getColumnIndex(KEY_ID)));

            Wine wine = getWineFromId(c.getInt(c.getColumnIndex(KEY_CELLAR_WINE)));
            cellar.setCellar_wine(wine);

            User user = getUserFromId(c.getInt(c.getColumnIndex(KEY_CELLAR_USER)));
            cellar.setCellar_user(user);

            cellar.setCellar_qty(c.getInt(c.getColumnIndex(KEY_CELLAR_QTY)));

            return cellar;
        }

        return null;
    }

    // ---------------------------------------------------------------------------------------------
    // GETALL METHODS
    // ---------------------------------------------------------------------------------------------
    public ArrayList<Grape> getAllGrapes() {
        ArrayList<Grape> grapesList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_GRAPE + " ORDER BY "
                +KEY_ID+ " ASC;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Grape grape = new Grape();
                grape.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                grape.setGrape_label((c.getString(c.getColumnIndex(KEY_GRAPE_LABEL))));

                grapesList.add(grape);
            } while (c.moveToNext());
        }

        return grapesList;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> usersList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_USER + " ORDER BY "
                +KEY_ID+ " ASC;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);


        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                user.setUser_name(c.getString(c.getColumnIndex(KEY_USER_NAME)));
                user.setUser_lastname(c.getString(c.getColumnIndex(KEY_USER_LASTNAME)));
                user.setUser_mail(c.getString(c.getColumnIndex(KEY_USER_MAIL)));

                usersList.add(user);
            } while (c.moveToNext());
        }

        return usersList;
    }


    public ArrayList<Grower> getAllGrowers() {
        ArrayList<Grower> growersList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_GROWER+ " ORDER BY "
                +KEY_ID+ " ASC;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Grower grower = new Grower();
                grower.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                grower.setGrower_label(c.getString(c.getColumnIndex(KEY_GROWER_LABEL)));
                grower.setGrower_addr(c.getString(c.getColumnIndex(KEY_GROWER_ADDR)));
                grower.setGrower_addr1(c.getString(c.getColumnIndex(KEY_GROWER_ADDR1)));
                grower.setGrower_addr2(c.getString(c.getColumnIndex(KEY_GROWER_ADDR2)));
                grower.setGrower_city(c.getString(c.getColumnIndex(KEY_GROWER_CITY)));
                grower.setGrower_zip(c.getString(c.getColumnIndex(KEY_GROWER_ZIP)));
                grower.setGrower_phone(c.getString(c.getColumnIndex(KEY_GROWER_PHONE)));
                grower.setGrower_mail(c.getString(c.getColumnIndex(KEY_GROWER_MAIL)));

                growersList.add(grower);
            } while (c.moveToNext());
        }

        return growersList;
    }

    public ArrayList<Wine> getAllWines() {
        ArrayList<Wine> winesList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_WINE+ " ORDER BY "
                +KEY_ID+ " ASC;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Wine wine = new Wine();
                wine.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                wine.setWine_label(c.getString(c.getColumnIndex(KEY_WINE_LABEL)));
                wine.setWine_yard(c.getString(c.getColumnIndex(KEY_WINE_YARD)));
                wine.setWine_year(c.getString(c.getColumnIndex(KEY_WINE_YEAR)));

                Grape g = getGrapeFromId(c.getInt(c.getColumnIndex(KEY_WINE_GRAPE)));
                wine.setWine_grape(g);

                Grower gr = getGrowerFromId(c.getInt(c.getColumnIndex(KEY_WINE_GROWER)));
                wine.setWine_grower(gr);

                winesList.add(wine);
            } while (c.moveToNext());
        }

        return winesList;
    }


    public ArrayList<Cellar> getAllCellars() {
        ArrayList<Cellar> winesList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_CELLAR+ " ORDER BY "
                +KEY_ID+ " ASC;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Cellar cellar = new Cellar();
                cellar.setId(c.getInt(c.getColumnIndex(KEY_ID)));

                Wine wine = getWineFromId(c.getInt(c.getColumnIndex(KEY_CELLAR_WINE)));
                cellar.setCellar_wine(wine);

                User user = getUserFromId(c.getInt(c.getColumnIndex(KEY_CELLAR_USER)));
                cellar.setCellar_user(user);

                cellar.setCellar_qty(c.getInt(c.getColumnIndex(KEY_CELLAR_QTY)));

                winesList.add(cellar);
            } while (c.moveToNext());
        }

        return winesList;
    }

    // ---------------------------------------------------------------------------------------------
    // DELETE METHODS
    // ---------------------------------------------------------------------------------------------
    public void deleteGrape(long grapeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GRAPE, KEY_ID + " = ?",
                new String[]{String.valueOf(grapeId)});
    }

    public void deleteUser(long userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(userId)});
    }

    public void deleteGrower(long growerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROWER, KEY_ID + " = ?",
                new String[]{String.valueOf(growerId)});
    }

    public void deleteWine(long wineId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WINE, KEY_ID + " = ?",
                new String[]{String.valueOf(wineId)});
    }

    public void deleteCellar(long cellarId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CELLAR, KEY_ID + " = ?",
                new String[]{String.valueOf(cellarId)});
    }


    // ---------------------------------------------------------------------------------------------
    // OTHER METHODS
    // ---------------------------------------------------------------------------------------------
    public Cursor getAllGrapesAsCursor() {
        ArrayList<Grape> grapesList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_GRAPE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }


    // ---------------------------------------------------------------------------------------------
    // CLOSE METHOD
    // ---------------------------------------------------------------------------------------------
    public void closeDB() {
        SQLiteDatabase db = getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
