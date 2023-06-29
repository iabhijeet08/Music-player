package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView no_songs;
    SongAdapter adapter;
    ArrayList<Audio>songs_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        no_songs = findViewById(R.id.no_song_text);

        if(!check_permission()){
            request_permission();
            return;
        }

        String [] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,// path
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"; // want music only from database

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);

        while(cursor.moveToNext()){
            Audio audio_data = new Audio(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(audio_data.getPath()).exists()) {
                // songs deleted by user but it is still in our database so we check if it exists then we add
                songs_list.add(audio_data);
            }
        }

        if(songs_list.isEmpty()){
            no_songs.setVisibility(View.VISIBLE);
        }else{

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SongAdapter(songs_list,MainActivity.this);
            recyclerView.setAdapter(adapter);
        }

    }

    boolean check_permission(){
          int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
          if(result == PackageManager.PERMISSION_GRANTED){
              return true;
          }else{
              return false;
          }
    }

    void request_permission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"Read permission is Required,Please allow from Settings",Toast.LENGTH_SHORT).show();

        }else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerView != null){
            // when we are back on main activity we still want to show background color
            adapter = new SongAdapter(songs_list,MainActivity.this);
            recyclerView.setAdapter(adapter);
        }
    }
}