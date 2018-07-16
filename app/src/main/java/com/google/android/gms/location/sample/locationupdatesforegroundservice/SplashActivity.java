package com.google.android.gms.location.sample.locationupdatesforegroundservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        callThread();
    }

    public void callThread() {
        if (checkOrAskForMultipleRunTimePermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }, 1000);
        }
    }

    public boolean checkOrAskForMultipleRunTimePermission(String... permission) {
        List<String> permissionsNeeded = new ArrayList<String>();

        for (String getPermission : permission) {
            int askPermission = ActivityCompat.checkSelfPermission(getApplicationContext(),
                    getPermission);

            if (askPermission != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(getPermission);
            }

        }
        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsNeeded.toArray(new String[permissionsNeeded.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return false;
        }


        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    callThread();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
