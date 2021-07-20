package tp.edu.musicstream;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlaySongActivity extends AppCompatActivity
{
    private String title = "";
    private String artiste = "";
    private String fileLink = "";
    private int drawable;
    private int currentIndex = -1;

    private MediaPlayer player = new MediaPlayer();
    private ImageButton btnPlayPause = null;
    private SongCollection songCollection = new SongCollection();
    SeekBar seekbar;
    Handler handler = new Handler();
    SongCollection originalSongCollection = new SongCollection();

    List<Song> shuffleList = Arrays.asList(songCollection.songs);

    Button repeatBtn;
    Button shuffleBtn;
    Boolean repeatFlag = false;
    Boolean shuffleFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        Bundle songData = this.getIntent().getExtras();
        currentIndex = songData.getInt("index");
        Log.d("temasek", "Retrieved position is: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);
        seekbar = findViewById(R.id.SeekBar);
        seekbar.setMax(player.getDuration());
        handler.removeCallbacks(p_bar);
        handler.postDelayed(p_bar,1000);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                if (player != null && player.isPlaying())
                {
                    player.seekTo(seekBar.getProgress());
                }
            }
        });
        repeatBtn = findViewById(R.id.repeatBtn);
        shuffleBtn = findViewById(R.id.shuffleBtn);
    }

    //Repeats to call itself forever every 1000 ms
    Runnable p_bar = new Runnable() {
        @Override
        public void run() {
            if(player != null && player.isPlaying())
            {
            seekbar.setProgress(player.getCurrentPosition());
            }
            handler.postDelayed(this, 1000);
        }
    };

        public void displaySongBasedOnIndex ( int selectedIndex)
        {
            Song song = songCollection.getCurrentSong(currentIndex);
            title = song.getTitle();
            artiste = song.getArtiste();
            fileLink = song.getFileLink();
            drawable = song.getDrawable();
            TextView txtTitle = findViewById(R.id.txtSongTItle);
            txtTitle.setText(title);
            TextView txtArtiste = findViewById(R.id.txtArtist);
            txtArtiste.setText(artiste);
            ImageView iCoverArt = findViewById(R.id.imgCoverArt);
            iCoverArt.setImageResource(drawable);
        }

        public void playSong (String songUrl)
        {
            try {
                player.reset();
                player.setDataSource(songUrl);
                player.prepare();
                player.start();
                gracefullyStopWhenMusicEnds();

                //btnPlayPause.setText("Pause" Button);
                setTitle(title);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void playOrPauseMusic (View view)
        {
            if (player.isPlaying())
            {
                player.pause();
                //change the "pause" image to "play" image
                btnPlayPause.setImageResource(R.drawable.playbutton);
            }
            else {
                player.start();
                //Change the "play" image to "pause" image
                btnPlayPause.setImageResource(R.drawable.pausebutton);
            }
        }
        private void gracefullyStopWhenMusicEnds ()
        {
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    if (repeatFlag)
                    {
                        playOrPauseMusic(null);
                    }
                    else
                    {
                    Toast.makeText(getBaseContext(), "The song had ended and the OnCompleteListener is activated\n" + "The button Text is changed to 'PLAY'", Toast.LENGTH_LONG).show();
                    btnPlayPause.setImageResource(R.drawable.playbutton);
                    playNext(null);
                    }
                }
            });
        }

        public void playNext (View view)
        {
            currentIndex = songCollection.getNextSong(currentIndex);
            Toast.makeText(this, "After Clicking playNext,\nthe current index of this song\n" +
                    "in the SongCollection array is now :" + currentIndex, Toast.LENGTH_LONG).show();
            Log.d("temasek", "After playNext, the index is now :" + currentIndex);
            displaySongBasedOnIndex(currentIndex);
            playSong(fileLink);
            btnPlayPause.setImageResource(R.drawable.pausebutton);
            seekbar.setMax(player.getDuration());
            handler.removeCallbacks(p_bar);
            handler.postDelayed(p_bar,1000);
        }

        public void playPrevious (View view)
        {
            currentIndex = songCollection.getPrevSong(currentIndex);
            Toast.makeText(this, "After clicking playPrevious, \nthe current index of this song\n" +
                    "in the SongCollect ion array is now :" + currentIndex, Toast.LENGTH_LONG).show();
            Log.d("temasek", "After playPrevious, the index is now :" + currentIndex);
            displaySongBasedOnIndex(currentIndex);
            playSong(fileLink);
            btnPlayPause.setImageResource(R.drawable.pausebutton);
            seekbar.setMax(player.getDuration());
            handler.removeCallbacks(p_bar);
            handler.postDelayed(p_bar,1000);
        }

        public void onBackPressed ()
        {
            super.onBackPressed();
            player.release();
            //To prevent app from crashing because handler might be calling at the same time
            handler.removeCallbacks(p_bar);
        }

    public void RepeatSong(View view)
    {
        if (repeatFlag)
        {
            repeatBtn.setBackgroundResource(R.drawable.repeat_off);
        }
        else
        {
            repeatBtn.setBackgroundResource(R.drawable.repeat_on);
        }
        repeatFlag = !repeatFlag;
    }
    public void shuffleSong(View view)
    {
        if (shuffleFlag)
        {
            shuffleBtn.setBackgroundResource(R.drawable.shuffle_off);
            //Helps to revert the shuffled Song list to its original song list
            songCollection = new SongCollection();
        }
        else
        {
            shuffleBtn.setBackgroundResource(R.drawable.shuffle_on);
            //Shuffles the original song list
            Collections.shuffle(shuffleList);
            //Takes the shuffled list to override the song list so that when it plays next, it plays the shuffled list
            shuffleList.toArray(songCollection.songs);
        }
        shuffleFlag = !shuffleFlag;
    }
}

