package hanu.a2_2001040209;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import hanu.a2_2001040209.adapters.ProductAdapter;
import hanu.a2_2001040209.api.FetchApi;
import hanu.a2_2001040209.db.ProductManager;
import hanu.a2_2001040209.models.Product;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProduct;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private ProductManager productManager;
    String name, image;
    int unitPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadProducts();
        recyclerViewProduct = findViewById(R.id.products);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2));

        productManager = ProductManager.getInstance(this);

        productList = new ArrayList<>();
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
        Constants.executor.execute(() -> {
            String json = FetchApi.loadJSON("https://hanu-congnv.github.io/mpr-cart-api/products.json");
            handler.post(() -> {
                if (json == null)
                    return;
                try {
                    JSONArray array = new JSONArray(json);
                    String[] products = new String[array.length()];
                    for (int i = 0; i < products.length; i++) {
                        JSONObject obj = array.getJSONObject(i);
                        image = obj.getString("thumbnail");
                        name = obj.getString("name");
                        unitPrice = (int) obj.getLong("unitPrice");
                        Product product = new Product(image, name, unitPrice);
                        productList.add(product);
                        productManager.insertProduct(product);
                    }
                    productAdapter = new ProductAdapter(productList);
                    recyclerViewProduct.setAdapter(productAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        searchProducts();

        ImageButton showCart = findViewById(R.id.showCart);
        showCart.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CartActivity.class)));

    }

    private void searchProducts() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });
        }

    private void filter(String s) {
        List<Product> filteredList = new ArrayList<>();
        for (Product item : productList) {
            if (item.getName().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(item);
            }
        }
        productAdapter.filterList(filteredList);
        recyclerViewProduct.setAdapter(productAdapter);
    }
    private void loadProducts() {
//        recyclerViewProduct = findViewById(R.id.products);
//        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2));
//
//        productManager = ProductManager.getInstance(this);
//
//        productList = new ArrayList<>();
//        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
//        Constants.executor.execute(() -> {
//            String json = FetchApi.loadJSON("https://hanu-congnv.github.io/mpr-cart-api/products.json");
//            handler.post(() -> {
//                if (json == null)
//                    return;
//                try {
//                    JSONArray array = new JSONArray(json);
//                    String[] products = new String[array.length()];
//                    for (int i = 0; i < products.length; i++) {
//                        JSONObject obj = array.getJSONObject(i);
//                        image = obj.getString("thumbnail");
//                        name = obj.getString("name");
//                        unitPrice = (int) obj.getLong("unitPrice");
//                        Product product = new Product(image, name, unitPrice);
//                        productList.add(product);
//                        productManager.insertProduct(product);
//                    }
//                    productAdapter = new ProductAdapter(productList);
//                    recyclerViewProduct.setAdapter(productAdapter);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        });
    }
}