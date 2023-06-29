package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Particular_music_player extends AppCompatActivity {
  TextView title,currentTime,totalTime;
  SeekBar seekbar;
  ImageView pausePlay,Next,Previous,background_icon;
   Audio current_song;
   int rotate = 0;
    public ArrayList<Audio> songs_list;
    MediaPlayer mediaPlayer = myMediaPlayer.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_music_player);
         title = findViewById(R.id.song_title);
         currentTime = findViewById(R.id.current_time);
         seekbar = findViewById(R.id.seek_bar);
         totalTime = findViewById(R.id.total_time);
         pausePlay = findViewById(R.id.pause_play);
         Next = findViewById(R.id.next);
         Previous = findViewById(R.id.previous);
         background_icon = findViewById(R.id.music_icon_big);
         title.setSelected(true);
        songs_list = (ArrayList<Audio>) getIntent().getSerializableExtra("SONGS_LIST");
        set_values();

        Particular_music_player.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTime.setText(convertToMMSS(mediaPlayer.getCurrentPosition() + ""));


                    if (mediaPlayer.isPlaying()) {
                        background_icon.setRotation(rotate);
                        rotate++;
                        pausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);

                    } else {
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                        background_icon.setRotation(0);
                    }

                }
                new Handler().postDelayed(this, 100);
                // update currentTime and seek bar every 100ms according to current media_player position
            }

        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer.isPlaying() && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        pausePlay.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                   if(mediaPlayer.isPlaying()){

                           mediaPlayer.pause();
                   }else{
                       mediaPlayer.start();
                   }

            }

        });

       Next.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                int length = songs_list.size();
                if(myMediaPlayer.current_index == length-1){
                    myMediaPlayer.current_index = 0;
                }else {
                    myMediaPlayer.current_index += 1;
                }

                mediaPlayer.reset();
                set_values();
               playMusic();

            }

        });

        Previous.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                int length = songs_list.size();
                if(myMediaPlayer.current_index == 0){
                    myMediaPlayer.current_index = length-1;
                }else {
                    myMediaPlayer.current_index -= 1;
                }

                mediaPlayer.reset();
                set_values();
                playMusic();

            }

        });

        playMusic();

    }


    void set_values(){
        current_song = songs_list.get(myMediaPlayer.current_index);
        title.setText(current_song.getTitle());
        // time will be as string  in milli second format so we need to convert it
        totalTime.setText(convertToMMSS(current_song.getDuration()));

    }

    @SuppressLint("DefaultLocale")
    public static String convertToMMSS(String duration){
        long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }


    void playMusic()  {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(current_song.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekbar.setProgress(0);
            seekbar.setMax(mediaPlayer.getDuration());


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}