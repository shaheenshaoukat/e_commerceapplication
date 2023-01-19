package com.example.e_commerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityProductdetailsBinding;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.utils.Constants;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class
Productdetails extends AppCompatActivity {

    ActivityProductdetailsBinding binding;
    Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductdetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        int id = getIntent().getIntExtra("id",0);
        double price = getIntent().getDoubleExtra("price",0);
        getSupportActionBar().setTitle(name);

        Glide.with(this)
                .load(image)
                .into(binding.productImage);
        getproductDetails(id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        com.hishd.tinycart.model.Cart cart = TinyCartHelper.getCart();
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct,1);
                binding.addToCartBtn.setEnabled(false);
                binding.addToCartBtn.setText("Added To Cart");


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         getMenuInflater().inflate(R.menu.cart,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cart){
            startActivity(new Intent(this, Cart.class));
        }
        return super.onOptionsItemSelected(item);
    }

    void getproductDetails(int id){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url= Constants.GET_PRODUCT_DETAILS_URL+id;
        StringRequest request=new StringRequest(Request.Method.GET,url,response ->
        {

            try {
                JSONObject object=new JSONObject(response);
                if (object.getString("status").equals("success"))
                {
                    JSONObject product=object.getJSONObject("product");
                    String description=product.getString("description");
                    binding.productDescription.setText(
                            Html.fromHtml(description)
                    );

                    currentProduct= new Product(
                        product.getString("name"),
                        Constants.PRODUCTS_IMAGE_URL+    product.getString("image"),
                            product.getString("status"),
                            product.getDouble("price"),
                            product.getDouble("price_discount"),
                            product.getInt("stock"),
                            product.getInt("id")
                );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {

        });
        queue.add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}