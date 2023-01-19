package com.example.e_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_commerce.Payment;
import com.example.e_commerce.R;
import com.example.e_commerce.adapters.CartAdapter;
import com.example.e_commerce.databinding.ActivityCheckoutBinding;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Checkout extends AppCompatActivity {

    ActivityCheckoutBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;
    double totalprice=0;
    final int tax=11 ;
    ProgressDialog progressDialog;

    Cart cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        binding= ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing......");


        products=new ArrayList<>();
       cart=TinyCartHelper.getCart();

        for (Map.Entry<Item, Integer> item:cart.getAllItemsWithQty().entrySet()){
            Product product=(Product) item.getKey();
            int quantity=item.getValue();
            product.setQuantity(quantity);
            products.add(product);

        }


        adapter=new CartAdapter(this, products, new CartAdapter.CartListner() {
            @Override
            public void onQuantityChange() {
                binding.subtotal.setText(String.format("PKR % .2f",cart.getTotalPrice()));

            }
        });

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOrder();
            }
        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);


        totalprice=(cart.getTotalPrice().doubleValue() * tax/100) + cart.getTotalPrice().doubleValue();
        binding.total.setText("PKR" + totalprice);


        binding.subtotal.setText(String.format("PKR % .2f",cart.getTotalPrice()));




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    void processOrder(){
        progressDialog.show();
        RequestQueue queue= Volley.newRequestQueue(this);

        JSONObject productOrder=new JSONObject();
        JSONObject dataobject=new JSONObject();
        try {

            productOrder.put("address",binding.addressBox.getText().toString());
            productOrder.put("buyer",binding.nameBox.getText().toString());
            productOrder.put("comment", binding.commentBox.getText().toString());
            productOrder.put("created_at", Calendar.getInstance().getTimeInMillis());
            productOrder.put("last_update", Calendar.getInstance().getTimeInMillis());
            productOrder.put("date_ship", Calendar.getInstance().getTimeInMillis());
            productOrder.put("email", binding.emailBox.getText().toString());
            productOrder.put("phone", binding.phoneBox.getText().toString());
            productOrder.put("serial", "cab8c1a4e4421a3b");
            productOrder.put("shipping", "");
            productOrder.put("shipping_location", "");
            productOrder.put("shipping_rate", "0.0");
            productOrder.put("status", "WAITING");
            productOrder.put("tax", tax);
            productOrder.put("total_fees", totalprice);
           /* productOrder.put("address",binding.addressBox.getText().toString());
            productOrder.put("buyer",binding.nameBox.getText().toString());
            productOrder.put("comment",binding.commentBox.getText().toString());
            productOrder.put("created_at", Calendar.getInstance().getTimeInMillis());
            productOrder.put("last_update", Calendar.getInstance().getTimeInMillis());
            productOrder.put("date_ship", Calendar.getInstance().getTimeInMillis());
            productOrder.put("email",binding.emailBox.getText().toString());
            productOrder.put("phone",binding.phoneBox.getText().toString());
            productOrder.put("serial","cab8c1a4e4421a3b");
            productOrder.put("shipping","");
            productOrder.put("shipping_location","");
            productOrder.put("shipping_rate","");
            productOrder.put("status","WAITING");
            productOrder.put("tax",tax);
            productOrder.put("total_fees",totalprice);*/


            JSONArray product_order_detail=new JSONArray();

            for (Map.Entry<Item, Integer> item:cart.getAllItemsWithQty().entrySet()){
                Product product=(Product) item.getKey();
                int quantity=item.getValue();
                product.setQuantity(quantity);


                JSONObject productobj=new JSONObject();
                productobj.put("amount",quantity);
                productobj.put("price_item",product.getPrice());
                productobj.put("product_id",product.getId());
                productobj.put("product_name",product.getName());
                product_order_detail.put(productobj);


            }
            dataobject.put("product_order",productOrder);
            dataobject.put("product_order_detail",product_order_detail);

            Log.e("err",dataobject.toString());

        }
        catch (JSONException e){

        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.POST_ORDER_URL, dataobject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("success")) {
                        Toast.makeText(Checkout.this, "Success order.", Toast.LENGTH_SHORT).show();
                        String orderNumber = response.getJSONObject("data").getString("code");
                        new AlertDialog.Builder(Checkout.this)
                                .setTitle("Order Successful")
                                .setCancelable(false)
                                .setMessage("Your order number is: " + orderNumber)
                                .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(Checkout.this, Payment.class);
                                        intent.putExtra("orderCode", orderNumber);
                                        startActivity(intent);
                                    }
                                }).show();
                    } else {
                        new AlertDialog.Builder(Checkout.this)
                                .setTitle("Order Failed")
                                .setMessage("Something went wrong, please try again.")
                                .setCancelable(false)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).show();
                        Toast.makeText(Checkout.this, "Failed order.", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    Log.e("res", response.toString());
                } catch (Exception e) {}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();
                headers.put("Security","secure_code");
                return headers;
            }
        };
        queue.add(request);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
