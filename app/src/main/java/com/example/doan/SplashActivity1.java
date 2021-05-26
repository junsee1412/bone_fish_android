package com.example.doan;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.common.sqlite;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SplashActivity1 extends AppCompatActivity {

    ProgressBar progressBar;
    sqlite db = new sqlite(SplashActivity1.this, "bone_fish.sqlite", null, 1);
    Cursor cursor;
    LoadProgress loadProgress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);

        progressBar = findViewById(R.id.progressBar1);
        setPermission();
    }
    private class LoadProgress extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i=1; i<=100; i++){
                SystemClock.sleep(10);
//                onProgressUpdate(i);
                publishProgress(i);

                db.QueryData("CREATE TABLE IF NOT EXISTS token(id INTEGER PRIMARY KEY AUTOINCREMENT, token TEXT)");
                db.QueryData("CREATE TABLE IF NOT EXISTS bill(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " id_product TEXT," +
                        " quantity INTEGER," +
                        " price INTEGER," +
                        " product TEXT," +
                        " image TEXT)");
                db.QueryData("CREATE TABLE IF NOT EXISTS cate(id TEXT PRIMARY KEY, " +
                        "id_user TEXT," +
                        "cate TEXT)");
                db.QueryData("CREATE TABLE IF NOT EXISTS bran(id TEXT PRIMARY KEY, " +
                        "id_user TEXT, " +
                        "bran TEXT)");
                db.QueryData("CREATE TABLE IF NOT EXISTS prod(" +
                        "id TEXT PRIMARY KEY," +
                        " id_brand TEXT," +
                        " id_category TEXT," +
                        " id_user TEXT," +
                        " product TEXT," +
                        " stock INTEGER," +
                        " price INTEGER," +
                        " image TEXT)");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            cursor = db.GetData("SELECT * FROM token");
            Intent i;
            if (cursor.moveToNext()) {
                i = new Intent(SplashActivity1.this, MainActivity.class);
            } else {
                i = new Intent(SplashActivity1.this, LoginActivity.class);
            }
            startActivity(i);
            finish();
        }
    }
    private void setPermission() {
        Dexter.withContext(SplashActivity1.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(SplashActivity1.this, "Permission granted",Toast.LENGTH_SHORT).show();
                loadProgress = new LoadProgress();
                loadProgress.execute();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(SplashActivity1.this, "Permission not granted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
}
