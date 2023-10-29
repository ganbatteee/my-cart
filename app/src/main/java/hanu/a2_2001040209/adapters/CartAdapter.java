package hanu.a2_2001040209.adapters;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hanu.a2_2001040209.Constants;
import hanu.a2_2001040209.R;
import hanu.a2_2001040209.TotalPrice;
import hanu.a2_2001040209.api.FetchApi;
import hanu.a2_2001040209.db.ProductManager;
import hanu.a2_2001040209.models.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<Product> products;
    int sum = 0, total = 0;
    TextView tvNoProducts, sumPrice, totalPrice;
    private ProductManager productManager;
    private Context context;
//    private TotalPrice totalPrice;
    public CartAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_view, parent, false);
        context = parent.getContext();
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
        productManager = ProductManager.getInstance(context);

        holder.plus.setOnClickListener(view -> {
            int quantity = product.getNumberInCart();
            quantity += 1;
            product.setNumberInCart(quantity);
            productManager.updateQuantity(product);
            notifyDataSetChanged();
            updatePrice();
        });

        holder.minus.setOnClickListener(view -> {
            int quantity = product.getNumberInCart();
            quantity -= 1;
            product.setNumberInCart(quantity);
            productManager.updateQuantity(product);
            notifyDataSetChanged();
            updatePrice();
                if (quantity == 0) {
                    productManager.delete(product.getId());
                    notifyDataSetChanged();
                    products.remove(product);
                }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        ImageView imvCart;
        ImageButton plus, minus;
        TextView nameCart, tvUnitPrice;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);

        }
        public void bind(Product product) {

            imvCart = itemView.findViewById(R.id.imvCart);
            Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
            Constants.executor.execute(() -> {
                Bitmap bitmap = FetchApi.downloadImage(product.getThumbnail());
                handler.post(() -> imvCart.setImageBitmap(bitmap));
            });

            nameCart = itemView.findViewById(R.id.titleCart);
            nameCart.setText(product.getName());
            tvUnitPrice = itemView.findViewById(R.id.priceCart);
            tvUnitPrice.setText(String.valueOf(product.getUnitPrice()));
            tvNoProducts = itemView.findViewById(R.id.noProducts);
            tvNoProducts.setText(String.valueOf(product.getNumberInCart()));

            sumPrice = itemView.findViewById(R.id.totalPricesEachProduct);
            sum = product.getNumberInCart() * product.getUnitPrice();
            sumPrice.setText(String.valueOf(sum));
        }

    }
    public int totalPrice() {
        for (int i = 0; i < products.size(); i++) {
            sum = (products.get(i).getUnitPrice() * products.get(i).getNumberInCart());
            total += sum;
        }
        Log.i(TAG, "Total price: " + total);
        return total;
    }


    public void updatePrice() {
        for (int i = 0; i < products.size(); i++) {
            sum = (products.get(i).getUnitPrice() * products.get(i).getNumberInCart());
            sumPrice.setText(String.valueOf(sum));
        }
    }
}

