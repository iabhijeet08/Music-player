package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

   ArrayList<Audio>songs_list;
    Context context;
    public SongAdapter(ArrayList<Audio> songs_list, Context context) {
        this.songs_list = songs_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.song,parent,false);
        return new SongAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          Audio audio_data = songs_list.get(position);
        int redColorValue = Color.RED;
        int blackColorValue = Color.BLACK;
          holder.title.setText(audio_data.getTitle());
          if(myMediaPlayer.current_index == position){
              holder.title.setTextColor(redColorValue);
          }else{
              holder.title.setTextColor(blackColorValue);
          }
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  // to show music in another activity we need position of song and list of songs
                  myMediaPlayer.getInstance().reset(); // when any particular song is clicked we reset the media_player
                  myMediaPlayer.current_index = holder.getAdapterPosition();
                  Intent i = new Intent(context,Particular_music_player.class);
                  i.putExtra("SONGS_LIST",songs_list);
                  context.startActivity(i);
              }
          });
    }

    @Override
    public int getItemCount() {
        return songs_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView music_icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          title = itemView.findViewById(R.id.music_title);
          music_icon = itemView.findViewById(R.id.icon);

        }
    }
}
