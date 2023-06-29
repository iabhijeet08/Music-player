package com.example.musicplayer;
// to access media_player  from any activity

import android.media.MediaPlayer;

public class myMediaPlayer {
    static MediaPlayer instance; // instance of media_player to play the music
    // only once  media_player will be created
   public static MediaPlayer getInstance(){
       if(instance == null){
           instance = new MediaPlayer();

       }
       return instance;
   }

   public static int current_index = -1; // tells us that song is not clicked yet

}
