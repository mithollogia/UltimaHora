package com.ultimahora.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.ultimahora.modelo.Articles;
import java.util.ArrayList;
import java.util.List;

public class Functions {
    private Database database;
    private SQLiteDatabase banco;

    public Functions(Context context) {
        database = new Database(context);
        banco = database.getWritableDatabase();
    }

    public long insert(Articles articles){
        ContentValues values = new ContentValues();
        values.put("url", articles.getUrl());
        values.put("title", articles.getTitle());
        values.put("published", articles.getPublished());
        values.put("description", articles.getDescription());
        values.put("urlToImage", articles.getUrlToImage());
        return banco.insert("noticias", null, values);
    }

    public boolean getTitle(String title){
        Cursor cursor = banco.rawQuery("SELECT title FROM noticias WHERE title = ?", new String[] {title});
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            return false;
        } else {
            return true;
        }
    }

    public List<Articles> getLista() {
        List<Articles> list = new ArrayList<>();
        list.clear();
        Cursor cursor = banco.query("noticias", new String[]{"_id", "url", "title", "published", "description", "urlToImage", "date"},
                null, null, null, null, "date DESC");

        while (cursor.moveToNext()) {
            Articles articles = new Articles();
            articles.setId(cursor.getInt(0));
            articles.setUrl(cursor.getString(1));
            articles.setTitle(cursor.getString(2));
            articles.setPublished(cursor.getString(3));
            articles.setDescription(cursor.getString(4));
            articles.setUrlToImage(cursor.getString(5));
            list.add(articles);
        }
        return list;
    }

    public boolean excluir(int id){
        return banco.delete("noticias", "_id = ?", new String[]{ id + "" }) > 0;
    }
}
