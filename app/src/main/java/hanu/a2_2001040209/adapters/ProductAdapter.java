package hanu.a2_2001040209.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import hanu.a2_2001040209.Constants;
import hanu.a2_2001040209.R;
import hanu.a2_2001040209.api.FetchApi;
import hanu.a2_2001040209.db.ProductManager;
import hanu.a2_2001040209.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<Product> products;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Context context;
    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product, parent, false);
        context = parent.getContext();
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);

        holder.imbCart.setOnClickListener(view -> {
            String thumbnail = product.getThumbnail();
            String name = product.getName();
            int numberInCart = product.getNumberInCart() + 1;
            int price = product.getUnitPrice();

            Product product1 = new Product(thumbnail, name, numberInCart, price);
            ProductManager manager = ProductManager.getInstance(context);
            if (product.getId() == product1.getId()) {
                numberInCart += 1;
                product1.setNumberInCart(numberInCart);
                manager.updateQuantity(product1);
                notifyDataSetChanged();
            }
            if (manager.insertProductToCart(product1)) {
                if (product.getId() == product1.getId()) {
                    numberInCart += 1;
                    product1.setNumberInCart(numberInCart);
                    manager.updateQuantity(product1);
                    notifyDataSetChanged();
                }
                Toast.makeText(context, "Added to Cart Successfully ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added to Cart Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        ImageButton imbCart;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Product product) {
            TextView tvName = itemView.findViewById(R.id.name1);
            tvName.setText(product.getName());
            TextView tvPrice = itemView.findViewById(R.id.unitPrice1);
            tvPrice.setText(String.valueOf(product.getUnitPrice()));

            ImageView imvProduct = itemView.findViewById(R.id.imv1);
            Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
            Constants.executor.execute(() -> {
                Bitmap bitmap = FetchApi.downloadImage(product.getThumbnail());
                handler.post(() -> imvProduct.setImageBitmap(bitmap));
            });
            imbCart = itemView.findViewById(R.id.imbCart);
        }

    }

    public void filterList(List<Product> filteredList) {
        products = filteredList;
        notifyDataSetChanged();
    }
}
