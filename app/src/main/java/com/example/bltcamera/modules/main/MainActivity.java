package com.example.bltcamera.modules.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.bltcamera.R;
import com.example.bltcamera.modules.mobile.MobileActivity;
import com.example.bltcamera.modules.watch.WatchActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest();
        }
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            setAlertVisible();
        } else {
            setSelectionVisible();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 101:
                if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Log.d("makeRequest","denied");

                } else {
                    Log.d("makeRequest","granted");
                }
                break;
        }
    }
    private void setAlertVisible() {
        View view = findViewById(R.id.activity_main_exit);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(this);
        view = findViewById(R.id.bluetooth_not_avilable_text);
        view.setVisibility(View.VISIBLE);
    }

    private void setSelectionVisible() {
        findViewById(R.id.selection_container).setVisibility(View.VISIBLE);
        findViewById(R.id.activity_main_watch).setOnClickListener(this);
        findViewById(R.id.activity_main_mobile).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_mobile:
                startActivity(new Intent(this, MobileActivity.class));
                break;
            case R.id.activity_main_watch:
                startActivity(new Intent(this, WatchActivity.class));
                break;
            case R.id.activity_main_exit:
                finish();
                break;
        }
    }
}
