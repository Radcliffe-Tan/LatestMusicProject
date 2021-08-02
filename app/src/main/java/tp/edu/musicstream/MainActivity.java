package tp.edu.musicstream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity

{
    SongCollection songCollection = new SongCollection();
    static ArrayList<Song> favList = new ArrayList<Song>();

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendDataToActivity(int index)
    {
        Intent intent = new Intent(this, PlaySongActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    public void handleSelection(View myView)
    {
        String resourceId = getResources().getResourceEntryName(myView.getId());
        int currentArrayIndex = songCollection.SearchSongById(resourceId);
        Log.d("temasek", "The id of the pressed ImageButton is : " + resourceId);
        sendDataToActivity(currentArrayIndex);
    }

    public void addToFavorite(View view)
    {
        String songID = view.getContentDescription().toString();
        Song song = songCollection.searchSongById(songID);
        favList.add(song);
        Toast.makeText(this, "button is clicked", Toast.LENGTH_SHORT).show();
    }

    public void gotoFavoriteActivity(View view)
    {
        Intent intent = new Intent(this,FavouriteActivity.class);
        startActivity(intent);
    }
}

