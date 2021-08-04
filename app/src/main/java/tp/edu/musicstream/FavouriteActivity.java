package tp.edu.musicstream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {
RecyclerView favList;
SongAdapter songAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        favList = findViewById(R.id.recycleView);

        songAdapter = new SongAdapter(HomeActivity.favList);
        favList.setAdapter(songAdapter);
        favList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void ClearAll(View view)
    {
        HomeActivity.favList.clear();
        songAdapter.notifyDataSetChanged();
    }
}