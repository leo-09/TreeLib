package com.hrg.treelib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hrg.treelib.bean.BaseResponse;
import com.hrg.treelib.bean.EquipCategory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_SELECT_DEVICE_CODE = 0x1111;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = getJson("tree.json");

                Gson gson = new Gson();
                BaseResponse response = gson.fromJson(json, BaseResponse.class);
                MyApplication.setCategories(response.data);
            }
        }).start();

        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getCategories() != null) {
                    Intent intent = new Intent(MainActivity.this, EquipCategoryTreeActivity.class);
                    startActivityForResult(intent, REQUEST_SELECT_DEVICE_CODE);
                }
            }
        });
    }

    private String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            AssetManager assetManager = getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));

            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_DEVICE_CODE) {
            EquipCategory equipCategory = null;

            if (data != null) {
                equipCategory = (EquipCategory) data.getSerializableExtra("category");
            }
            if (equipCategory != null) {
                tv.setText(equipCategory.getName());
            }
        }
    }
}
