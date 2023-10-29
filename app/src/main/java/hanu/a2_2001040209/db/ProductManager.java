package hanu.a2_2001040209.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import hanu.a2_2001040209.models.Product;

public class ProductManager {

    private static final String INSERT_PRODUCT = "INSERT INTO " + DBSchema.ProductsTable.NAME + "(thumbnail, name, unitprice) VALUES(?, ?, ?)";
    private static final String INSERT_INTO_CART = "INSERT INTO " + DBSchema.ProductsCart.NAME + "(thumbnail, name, quantity, unitprice) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT = "UPDATE " + DBSchema.ProductsCart.NAME + " SET quantity = ? WHERE id = ?";

    private static ProductManager instance;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private ProductManager(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static ProductManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProductManager(context);
        }
        return instance;
    }

    public List<Product> all() {
        String sql = "SELECT * FROM products";
        Cursor cursor = db.rawQuery(sql, null);
        ProductCursorWrapper productCursorWrapper = new ProductCursorWrapper(cursor);
        return productCursorWrapper.getProducts();
    }

    public List<Product> allInCart() {
        String sql = "SELECT * FROM productsCart";
        Cursor cursor = db.rawQuery(sql, null);
        ProductCursorWrapper productCursorWrapper = new ProductCursorWrapper(cursor);
        return productCursorWrapper.getProductsInCart();
    }

    public Product findById(long id) {
        String sql = "SELECT * FROM products WHERE id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        ProductCursorWrapper productCursorWrapper = new ProductCursorWrapper(cursor);
        productCursorWrapper.moveToNext();
        return productCursorWrapper.getProduct();
    }
    public boolean insertProduct (Product product) {
        SQLiteStatement statement = db.compileStatement(INSERT_PRODUCT);
        statement.bindString(1, product.getThumbnail());
        statement.bindString(2, product.getName());
        statement.bindLong(3, product.getUnitPrice());
        int id = (int) statement.executeInsert();
        if (id > 0) {
            product.setId(id);
            return true;
        }
        return false;
    }

    public boolean insertProductToCart (Product product) {
        SQLiteStatement statement = db.compileStatement(INSERT_INTO_CART);
        statement.bindString(1, product.getThumbnail());
        statement.bindString(2, product.getName());
        statement.bindLong(3, product.getNumberInCart());
        statement.bindLong(4, product.getUnitPrice());
        int id = (int) statement.executeInsert();
        if (id > 0) {
            product.setId(id);
            return true;
        }
        return false;
    }

    public boolean updateQuantity(Product product) {
        SQLiteStatement statement = db.compileStatement(UPDATE_PRODUCT);
        statement.bindLong(1, product.getNumberInCart());
        statement.bindLong(2, product.getId());
        int result = statement.executeUpdateDelete();
        return result > 0;
    }

    public boolean delete (int id) {
        int result = db.delete(DBSchema.ProductsCart.NAME, "id = ?", new String[]{id + ""});
        return result > 0;
    }
}
