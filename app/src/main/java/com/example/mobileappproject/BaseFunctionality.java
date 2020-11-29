package com.example.mobileappproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public abstract class BaseFunctionality extends AppCompatActivity {

    protected interface OnSuccess {

        public void OnSuccessDo();

    }

    protected interface OnSelectElement {
        public void OnElementIterate(String animeName, String studio, String episodeCount, String licensedBy, String animeGenre, String ID);
    }

    protected interface OnSelectElementManga {
        public void OnElementIterateManga(String title, String mangaka, String chapters, String genre, String ID);
    }

    protected void initDb() throws SQLException {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath() + "/anime.db",
                null);


        String queryAnime = "CREATE TABLE if not exists ANIME( " +
                "ID integer PRIMARY KEY AUTOINCREMENT, " +
                "animeName text not null, " +
                "studio text not null, " +
                "episodeCount short not null, " +
                "licensedBy text not null, " +
                "animeGenre text not null, " +
                "unique(animeName) " +
                "); ";

        String queryUser = "CREATE TABLE if not exists USER(" +
                "ID integer PRIMARY KEY AUTOINCREMENT, " +
                "username text not null," +
                "password text not null," +
                "unique(username)" +
                ");";

        String queryManga = "CREATE TABLE if not exists MANGA( " +
                "ID integer PRIMARY KEY AUTOINCREMENT, " +
                "Title text not null, " +
                "Mangaka text not null, " +
                "Chapters short not null, " +
                "Genre text not null, " +
                "unique(Title, Mangaka) " +
                "); ";

        db.execSQL(queryUser);
        db.execSQL(queryAnime);
        db.execSQL(queryManga);
        db.close();

    }

    protected void ExecSQL(String SQL, Object[] args, OnSuccess success) throws SQLException {
        SQLiteDatabase db =
                SQLiteDatabase.openOrCreateDatabase(
                        getFilesDir().getPath() + "/anime.db",
                        null);
        db.execSQL(SQL, args);
        success.OnSuccessDo();
        db.close();
    }

    public boolean checkLogin(String username, String password) throws Exception {
        SQLiteDatabase db =
                SQLiteDatabase.openOrCreateDatabase(
                        getFilesDir().getPath() + "/anime.db",
                        null
                );

        String queryUser = "Select username, password FROM USER WHERE username= '" + username + "' AND password= '" + password +"'";
        Cursor cursor = db.rawQuery(queryUser, null);

        if ( cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }

    public void SelectSQLAnime(String SQL, String[] args, OnSelectElement iterate) throws Exception {
        SQLiteDatabase db =
                SQLiteDatabase.openOrCreateDatabase(
                        getFilesDir().getPath() + "/anime.db",
                        null
                );

        Cursor cursor = db.rawQuery(SQL, args);
        while (cursor.moveToNext()) {
            String aniID = cursor.getString(cursor.getColumnIndex("ID"));
            String aniName = cursor.getString(cursor.getColumnIndex("animeName"));
            String aniStudio = cursor.getString(cursor.getColumnIndex("studio"));
            String aniEpCount = cursor.getString(cursor.getColumnIndex("episodeCount"));
            String aniLicensedBy = cursor.getString(cursor.getColumnIndex("licensedBy"));
            String aniGenre = cursor.getString(cursor.getColumnIndex("animeGenre"));
            iterate.OnElementIterate(aniName, aniStudio, aniEpCount, aniLicensedBy, aniGenre, aniID);
        }
        cursor.close();
        db.close();
    }

    public void SelectSQLManga(String sqlManga, String[] args, OnSelectElementManga iterateM) throws Exception {
        SQLiteDatabase dbManga =
                SQLiteDatabase.openOrCreateDatabase(
                        getFilesDir().getPath() + "/anime.db",
                        null
                );

        Cursor c = dbManga.rawQuery(sqlManga, args);
        while (c.moveToNext()) {
            String mangaID = c.getString(c.getColumnIndex("ID"));
            String mangaTitle = c.getString(c.getColumnIndex("Title"));
            String mangaMangaka = c.getString(c.getColumnIndex("Mangaka"));
            String mangaChCount = c.getString(c.getColumnIndex("Chapters"));
            String mangaGenre = c.getString(c.getColumnIndex("Genre"));
            iterateM.OnElementIterateManga(mangaTitle, mangaMangaka, mangaChCount, mangaGenre, mangaID);
        }

        c.close();
        dbManga.close();
    }

}




