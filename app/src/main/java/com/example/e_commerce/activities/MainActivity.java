package com.example.e_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_commerce.R;
import com.example.e_commerce.Search;
import com.example.e_commerce.adapters.CategoryAdapter;
import com.example.e_commerce.adapters.ProductAdapter;
import com.example.e_commerce.databinding.ActivityMainBinding;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.utils.Constants;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;
    ArrayList<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCategories();
        initProduct();
        getRecentOffer();

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                String query=text.toString();
                Intent intent=new Intent(MainActivity.this, Search.class);
                intent.putExtra("query",query);
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });







    }


    void getRecentProduct(){
        RequestQueue queue=Volley.newRequestQueue(this);
        String url= Constants.GET_PRODUCTS_URL + "?count=14";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    if (object.getString("status").equals("success")){
                        JSONArray productsArray=object.getJSONArray("products");
                        for (int i=0;i<productsArray.length();i++){
                            JSONObject childobj=productsArray.getJSONObject(i);
                            Product product=new Product(
                                    childobj.getString("name"),
                                Constants.PRODUCTS_IMAGE_URL+    childobj.getString("image"),
                                    childobj.getString("status"),
                                    childobj.getDouble("price"),
                                    childobj.getDouble("price_discount"),
                                    childobj.getInt("stock"),
                                    childobj.getInt("id")
                            );
                            products.add(product);
                        }
                        productAdapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    void getCategories()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("err",response);
                    JSONObject mainObj=new JSONObject(response);
                    if (mainObj.getString("status").equals("success")){

                        JSONArray categoriesArray=mainObj.getJSONArray("categories");
                        for (int i=0; i<categoriesArray.length();i++)
                        {

                            JSONObject object=categoriesArray.getJSONObject(i);
                            Category category=new Category(
                                    object.getString("name"),
                                 Constants.CATEGORIES_IMAGE_URL +   object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief"),
                                    object.getInt("id")
                            );
                            categories.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();

                    }

                    else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }

    void getRecentOffer(){
        RequestQueue queue=Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET,Constants.GET_OFFERS_URL,response -> {

            try {
                JSONObject object=new JSONObject(response);
                if (object.getString("status").equals("success")){
                    JSONArray offerarray=object.getJSONArray("news_infos");
                    for (int i=0;i<offerarray.length();i++){
                        JSONObject childobj=offerarray.getJSONObject(i);
                        binding.carousel.addData(new CarouselItem(
                               Constants.NEWS_IMAGE_URL+ childobj.getString("image"),
                                childobj.getString("title")
                        ));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        },error -> {

        });
        queue.add(request);
    }


    void initCategories(){

        categories = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(this, categories);

        getCategories();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }
    void initProduct(){

        products =new ArrayList<>();
        productAdapter=new ProductAdapter(this,products);
        getRecentProduct();
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        binding.productlist.setLayoutManager(layoutManager);
        binding.productlist.setAdapter(productAdapter);

    }
}