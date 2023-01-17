package com.example.e_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.GridLayout;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.CategoryAdapter;
import com.example.e_commerce.adapters.ProductAdapter;
import com.example.e_commerce.databinding.ActivityMainBinding;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

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
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCategories();
        initProduct();
        initslider();

    }

    void initslider(){
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/special%20offer.jpg","Some Caption"));
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/Announcement.jpg","Some Caption"));
        binding.carousel.addData(new CarouselItem("https://tutorials.mianasad.com/ecommerce/uploads/news/Bridal%20dress.jpg","Some Caption"));


    }
    void initCategories(){

        categories = new ArrayList<>();
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#4db151", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#685242", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#FD0E35", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#4db151", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#685242", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#FD0E35", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#4db151", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#685242", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836546571.png", "#FD0E35", "some description", 1));


        categoryAdapter = new CategoryAdapter(this, categories);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }

    void initProduct(){

        products =new ArrayList<>();
        products.add(new Product("Bridal red lehnga","https://tutorials.mianasad.com/ecommerce/uploads/product/1673839815297.jpg","",12,12,1,1));
        products.add(new Product("Bridal red lehnga","https://tutorials.mianasad.com/ecommerce/uploads/product/1673839815297.jpg","",12,12,1,1));
        products.add(new Product("Bridal red lehnga","https://tutorials.mianasad.com/ecommerce/uploads/product/1673839815297.jpg","",12,12,1,1));
        products.add(new Product("Bridal red lehnga","https://tutorials.mianasad.com/ecommerce/uploads/product/1673839815297.jpg","",12,12,1,1));
        products.add(new Product("Bridal red lehnga","https://tutorials.mianasad.com/ecommerce/uploads/product/1673839815297.jpg","",12,12,1,1));
        products.add(new Product("Bridal red lehnga","https://tutorials.mianasad.com/ecommerce/uploads/product/1673839815297.jpg","",12,12,1,1));
        products.add(new Product("Bridal red lehnga","https://tutorials.mianasad.com/ecommerce/uploads/product/1673839815297.jpg","",12,12,1,1));
        products.add(new Product("Bridal red lehnga","https://tutorials.mianasad.com/ecommerce/uploads/product/1673839815297.jpg","",12,12,1,1));
        productAdapter=new ProductAdapter(this,products);

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        binding.productlist.setLayoutManager(layoutManager);
        binding.productlist.setAdapter(productAdapter);

    }
}