package hanu.a2_2001040209.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import hanu.a2_2001040209.models.Product;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "products.db";
    private static final int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT,  content TEXT NOT NULL)");
//        db.execSQL("INSERT INTO notes(id,content) VALUES (1,'Example note')");

//            db.execSQL("CREATE TABLE " + DBSchema.ProductsTable.NAME + " (" + DBSchema.ProductsTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + DBSchema.ProductsTable.Cols.THUMBNAIL + "TEXT NOT NULL, "
//                    + DBSchema.ProductsTable.Cols.NAME + " TEXT NOT NULL, "
//                    + DBSchema.ProductsTable.Cols.QUANTITY + "INTEGER NOT NULL, "
//                    + DBSchema.ProductsTable.Cols.UNIT_PRICE + "INTEGER NOT NULL" + ")");
//            db.execSQL("INSERT INTO " + DBSchema.ProductsTable.NAME + " (name, quantity, unitprice) VALUES ('" + product.getName() + "', " + product.getNumberInCart() + ", " + product.getUnitPrice() + ")");

        db.execSQL("CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "thumbnail TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "unitprice INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE productsCart (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "thumbnail TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "quantity INTERGER NOT NULL, " +
                "unitprice INTEGER NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS products";
        String sql1 = "DROP TABLE IF EXISTS productsCart";
        db.execSQL(sql);
        db.execSQL(sql1);

        onCreate(db);
    }

}
