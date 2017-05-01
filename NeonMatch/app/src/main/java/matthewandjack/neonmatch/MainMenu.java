package matthewandjack.neonmatch;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 *
 */

public class MainMenu extends AppCompatActivity {

    private MediaPlayer backgroundMusic;
    private boolean mute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        backgroundMusic = MediaPlayer.create(this, R.raw.backgroundmusic);
        mute = ((MyApplication) this.getApplication()).getMute();
        if (!mute) {
            backgroundMusic.start();
            backgroundMusic.setLooping(true);
        }
    }

    public void openPlayActivity(View view) {
        Intent intent = new Intent(MainMenu.this, PlayScreen.class);
        startActivity(intent);
    }

    public void openLeaderboardActivity(View view) {
        //Displays leaderboard method
        DBHandler db = new DBHandler(this);
        if(!(db.checkDatabase())) {
            //Inserting Players
            db.addPlayer(new Player(1, 1234, "LOL"));
            db.addPlayer(new Player(2, 1000, "ABC"));
            db.addPlayer(new Player(3, 950, "MAT"));
            db.addPlayer(new Player(4, 900, "BRI"));
            db.addPlayer(new Player(5, 800, "SOM"));
            db.addPlayer(new Player(6, 775, "JAK"));
            db.addPlayer(new Player(7, 750, "HAH"));
            db.addPlayer(new Player(8, 700, "LAD"));
            db.addPlayer(new Player(9, 660, "KUL"));
            db.addPlayer(new Player(10, 420, "DAN"));
        }
        List<Player> players = db.getAllPlayers();

        String result = "";
        for(int j = 0; j < players.size(); j++){
            result += "Rank:   " + (j+1) + "\nName: " + players.get(j).getName() + "\n" + "Score:  " + players.get(j).getScore() + "\n\n";
        }

        displayMessage("Leaderboards", result);
    }

    public void displayMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void exitApp(View view) {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void soundToggle(View v)
    {
        ImageButton sound = (ImageButton) findViewById(R.id.sound);
        if(!mute){
            sound.setImageResource(R.drawable.soundoff);
            ((MyApplication) this.getApplication()).setMute(true);
            mute = ((MyApplication) this.getApplication()).getMute();
        }
        else{
            sound.setImageResource(R.drawable.soundon);
            ((MyApplication) this.getApplication()).setMute(false);
            mute = ((MyApplication) this.getApplication()).getMute();
        }
        updateMusic();
    }

    private void updateMusic(){
        if(!mute){
            backgroundMusic.start();
            backgroundMusic.setLooping(true);
        }
        else
            backgroundMusic.pause();
    }

    public void watchYoutubeVideo(View view){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:va67yTSMkFU"));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=va67yTSMkFU"));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(backgroundMusic.isPlaying())
            backgroundMusic.stop();
        else
            return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!backgroundMusic.isPlaying() && !mute)
            backgroundMusic.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(backgroundMusic.isPlaying())
            backgroundMusic.stop();
        else
            return;
    }
}
