package hanu.a2_2001040209.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_2001040209.db.DBSchema;
import hanu.a2_2001040209.models.Product;

public class ProductCursorWrapper extends CursorWrapper {
    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Product getProductCart() {
        int id = (int) getLong(getColumnIndex(DBSchema.ProductsCart.Cols.ID));
        String thumbnail = getString(getColumnIndex(DBSchema.ProductsCart.Cols.THUMBNAIL));
        String name = getString(getColumnIndex(DBSchema.ProductsCart.Cols.NAME));
        int quantity = (int) getLong(getColumnIndex(DBSchema.ProductsCart.Cols.QUANTITY));
        int price = (int) getLong(getColumnIndex(DBSchema.ProductsCart.Cols.UNIT_PRICE));

        Product product = new Product(id, thumbnail, name, quantity, price);
        return product;
    }

    public Product getProduct() {
        int id = (int) getLong(getColumnIndex(DBSchema.ProductsTable.Cols.ID));
        String thumbnail = getString(getColumnIndex(DBSchema.ProductsTable.Cols.THUMBNAIL));
        String name = getString(getColumnIndex(DBSchema.ProductsTable.Cols.NAME));
        int price = (int) getLong(getColumnIndex(DBSchema.ProductsTable.Cols.UNIT_PRICE));

        Product product = new Product(id, thumbnail, name, price);
        return product;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        moveToFirst();
        while (!isAfterLast()) {
            Product product = getProduct();
            products.add(product);
            moveToNext();
        }
        return products;
    }

    public List<Product> getProductsInCart() {
        List<Product> products = new ArrayList<>();
        moveToFirst();
        while (!isAfterLast()) {
            Product product = getProductCart();
            products.add(product);
            moveToNext();
        }
        return products;
    }

}
