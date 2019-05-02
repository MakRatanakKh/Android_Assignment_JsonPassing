package com.example.mak_ratanak_assign_jsonpassing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView imgView;
    TextView tvId,tvName,tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvId = findViewById(R.id.tv_id);
        tvName = findViewById(R.id.tv_name);
        tvAddress = findViewById(R.id.tv_address);
        imgView = findViewById(R.id.img_profile);

        Intent intent = getIntent();

        tvId.setText("ID : " + intent.getStringExtra("ID"));
        tvName.setText("Name : " + intent.getStringExtra("NAME"));
        tvAddress.setText("Address : " + intent.getStringExtra("ADDRESS"));
        Picasso.get().load(intent.getStringExtra("IMG")).into(imgView);
    }
}