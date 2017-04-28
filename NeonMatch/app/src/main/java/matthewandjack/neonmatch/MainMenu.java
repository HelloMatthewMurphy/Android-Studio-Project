package matthewandjack.neonmatch;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.List;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        final MediaPlayer backgroundMusic = MediaPlayer.create(this, R.raw.backgroundmusic);

        final ImageButton splashScreen = (ImageButton) findViewById(R.id.splashScreen);
        splashScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                splashScreen.setX(2000);
                backgroundMusic.start();
                backgroundMusic.setLooping(true);
            }
        });
    }

    public void openPlayActivity(View view) {
        Intent intent = new Intent(MainMenu.this, PlayScreen.class);
        startActivity(intent);
    }

    public void openLeaderboardActivity(View view) {
        //Displays leaderboard method
        DBHandler db = new DBHandler(this);

        //Inserting Players
        db.addPlayer(new Player(1 , 1234, "LOL"));
        db.addPlayer(new Player(2 , 1000, "ABC"));
        db.addPlayer(new Player(3 , 950, "MAT"));
        db.addPlayer(new Player(4 , 900, "BRI"));
        db.addPlayer(new Player(5 , 800, "SOM"));
        db.addPlayer(new Player(6 , 775, "JAK"));
        db.addPlayer(new Player(7 , 750, "HAH"));
        db.addPlayer(new Player(8 , 700, "LAD"));
        db.addPlayer(new Player(9 , 660, "KUL"));
        db.addPlayer(new Player(10 , 630, "DAN"));

        //Reading all shops
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
        System.exit(0);
    }
}
