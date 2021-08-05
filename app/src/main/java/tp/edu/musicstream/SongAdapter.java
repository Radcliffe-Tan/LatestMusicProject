package tp.edu.musicstream;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyView>
{
    //SongAdapter class will pass through an array list
    public SongAdapter(List<Song> songs) { this.songs = songs;}
    //setting the array list to passing array list by another class

    private Activity activity;
    List<Song> songs;
    Context context;

    public SongAdapter(Activity activity)
    {
        this.activity = activity;
        SongCollection songCollection = new SongCollection();
        songs = songCollection.getSongs();
    }

    @NonNull
    @Override
    // to get a handle of item_song
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View songView = inflater.inflate(R.layout.item_song, parent,false);
        MyView viewHolder = new MyView(songView);
        return viewHolder;
    }

    @Override
    //tell android which of the attributes is going to bind into the different UI
    public void onBindViewHolder(@NonNull MyView holder, int position)
    {
        Song song = songs.get(position);

        holder.titleText.setText(song.getTitle());
        holder.artisteText.setText(song.getArtiste());
        holder.coverImage.setImageResource(song.getDrawable());
        holder.removeBtn.setOnClickListener(v ->
            {
                HomeActivity.favList.remove(position);
                notifyDataSetChanged();
            }
        );
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
    public class MyView extends RecyclerView.ViewHolder
    {
        public View itemView;
        public ImageView coverImage;
        public TextView titleText, artisteText;
        public View removeBtn;

        public MyView(@NonNull View itemView)
        {
            super(itemView);
            this.itemView = itemView;

            coverImage = itemView.findViewById(R.id.image);
            titleText = itemView.findViewById(R.id.titleTxt);
            artisteText = itemView.findViewById(R.id.ArtisteTxt);
            removeBtn = itemView.findViewById(R.id.removeBtn);
        }
    }
}
