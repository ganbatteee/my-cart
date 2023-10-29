package hanu.a2_2001040209;


import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hanu.a2_2001040209.adapters.CartAdapter;
import hanu.a2_2001040209.db.ProductManager;
import hanu.a2_2001040209.models.Product;

public class CartActivity extends AppCompatActivity {
    int totalPrice = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        List<Product> products;

        ProductManager productManager = ProductManager.getInstance(this);
        products = productManager.allInCart();
        CartAdapter cartAdapter = new CartAdapter(products);

        RecyclerView recyclerViewCart = findViewById(R.id.addedProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.setAdapter(cartAdapter);

        TextView tvTotalPrice = findViewById(R.id.totalAllProducts);
        for (int i = 0; i < products.size(); i++) {
            int quantity = products.get(i).getNumberInCart();
            int price = products.get(i).getUnitPrice();
            totalPrice += quantity * price;
            tvTotalPrice.setText(String.valueOf(totalPrice));
        }
    }
}
