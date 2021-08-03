package tp.edu.musicstream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity

{
    SongCollection songCollection = new SongCollection();
    static ArrayList<Song> favList = new ArrayList<Song>();
    SharedPreferences sharedPreferences;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("Favourites", MODE_PRIVATE);
        String albums = sharedPreferences.getString("list", "");
        //this will not be triggered if there is no song in the file
        if (!albums.equals(""))
        {
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>(){};
            Gson gson = new Gson();
            //Gets the favlist from the file.
            favList = gson.fromJson(albums, token.getType());
        }
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
        Gson gson = new Gson();
        String json = gson.toJson(favList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("list", json);
        editor.apply();
        Log.d("gson", json);
        Toast.makeText(this, "Added to Favourites", Toast.LENGTH_SHORT).show();
    }

    public void gotoFavoriteActivity(View view)
    {
        Intent intent = new Intent(this,FavouriteActivity.class);
        startActivity(intent);
    }
}

