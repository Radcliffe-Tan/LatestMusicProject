package tp.edu.musicstream;

public class SongCollection
{
    public Song songs[] = new Song[5];

    public SongCollection()
    {
        Song theWayYouLookTonight = new Song ("S1001",
                "The Way You Look Tonight",
                "Michael Buble",
                "https://p.scdn.co/mp3-preview/a5b8972e764025020625bbf9c1c2bbb06e394a60?cid=2afe87a64b0042dabf51f37318616965",
                4.66,
                R.drawable.michael_buble_collection);

        Song billieJean = new Song("S1002",
                "Billie Jean",
                "Michael Jackson",
                "https://p.scdn.co/mp3-preview/14a1ddedf05a15ad0ac11ce28b40ea1a15fabd20?cid=2afe87a64b0042dabf51f37318616965",
                4.9,
                R.drawable.billie_jean);

        Song one = new Song ("S1003",
                "One",
                "Ed Sheeran",
                "https://p.scdn.co/mp3-preview/097c7b735ceb410943cbd507a6e1dfda272fd8a8?cid=2afe87a64b0042dabf51f37318616965",
                4.32,
                R.drawable.photograph);
        Song butter = new Song ("S1004",
                "Butter",
                "BTS",
                "https://p.scdn.co/mp3-preview/edf24f427483d886b640c5ed9944f9291e0976fc?cid=2afe87a64b0042dabf51f37318616965",
                2.74,
                R.drawable.butter);
        Song waybackhome = new Song ("S1005",
                "Way Back Home",
                "SHAUN",
                "https://p.scdn.co/mp3-preview/90960a71753aac7a571900182789d9e4bcad86c8?cid=2afe87a64b0042dabf51f37318616965",
                3.57,
                R.drawable.waybackhome);

        songs[0] = theWayYouLookTonight;
        songs[1] = billieJean;
        songs[2] = one;
        songs[3] = butter;
        songs[4] = waybackhome;
    }

    public Song getCurrentSong(int currentSongid)
    {
        return songs[currentSongid];
    }

    public int SearchSongById(String id)
    {

        for(int index = 0; index < songs.length; index++)
        {
            Song tempSong = songs[index];
            if (tempSong.getId().equals(id))
            {
                return index;
            }
        }
        return -1;
    }

    public int getNextSong(int currentSongIndex)
    {
        if (currentSongIndex >= songs.length-1)
        {
            return currentSongIndex;
        }
        else
        {
            return currentSongIndex +1;
        }
    }

    public int getPrevSong(int currentSongIndex)
    {
        if (currentSongIndex <= 0)
        {
            return currentSongIndex;
        }
        else
        {
            return currentSongIndex-1;
        }
    }
}
