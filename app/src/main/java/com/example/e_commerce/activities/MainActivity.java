package com.example.e_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.GridLayout;

import com.example.e_commerce.R;
import com.example.e_commerce.adapters.CategoryAdapter;
import com.example.e_commerce.databinding.ActivityMainBinding;
import com.example.e_commerce.model.Category;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categories = new ArrayList<>();
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#4db151", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#685242", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#FD0E35", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#4db151", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#685242", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#FD0E35", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#4db151", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#685242", "some description", 1));
        categories.add(new Category("Bridal Dress", "https://tutorials.mianasad.com/ecommerce/uploads/category/1673836493901.png", "#FD0E35", "some description", 1));


        categoryAdapter = new CategoryAdapter(this, categories);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }
}