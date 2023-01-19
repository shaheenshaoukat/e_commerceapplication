package com.example.e_commerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.e_commerce.R;
import com.example.e_commerce.databinding.ActivityPaymentBinding;
import com.example.e_commerce.utils.Constants;

public class Payment extends AppCompatActivity {

    ActivityPaymentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String orderCode=getIntent().getStringExtra("orderCode");
        binding.webview.setMixedContentAllowed(true);
        binding.webview.loadUrl(Constants.PAYMENT_URL +orderCode);

    }


    @Override
    public void supportNavigateUpTo(@NonNull Intent upIntent) {
        finish();
        super.supportNavigateUpTo(upIntent);
    }
}