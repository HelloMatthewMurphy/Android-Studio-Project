package matthewandjack.neonmatch;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.util.Random;

/**
 * Created by user on 22/04/2017.
 */

public class PlayScreen extends AppCompatActivity {
    private boolean red = false;
    private boolean blue = false;
    private boolean green = false;
    private boolean yellow = false;
    private int score = 0;
    private long timeTellNewColor = 5;
    private long lastTime = 0;
    private int [] balls = {0, 0, 0};
    private int [] ballImages = {R.drawable.red, R.drawable.blue, R.drawable.green, R.drawable.yellow};
    private int currentBall = 0;
    //public Button redButton = (Button)findViewById(R.id.redButton);
    //int[] imageArray = { R.drawable.red, R.drawable.blue};
    //private ImageView ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_screen);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.tap);
        final ImageButton redButton = (ImageButton) findViewById(R.id.redButton);
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(PlayScreen.this, "RED", Toast.LENGTH_LONG).show();
                mp.start();
                if(balls [0] == 0) {
                    score++;
                    updateScore();
                    changeBall();
                }
            }
        });
        final ImageButton blueButton = (ImageButton) findViewById(R.id.blueButton);
        blueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(PlayScreen.this, "BLUE", Toast.LENGTH_LONG).show();
                mp.start();
                if(balls [0] == 1) {
                    score++;
                    updateScore();
                    changeBall();
                }
            }
        });
        final ImageButton greenButton = (ImageButton) findViewById(R.id.greenButton);
        greenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(PlayScreen.this, "GREEN", Toast.LENGTH_LONG).show();
                mp.start();
                if(balls [0] == 2) {
                    score++;
                    updateScore();
                    changeBall();
                }
            }
        });
        final ImageButton yellowButton = (ImageButton) findViewById(R.id.yellowButton);
        yellowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(PlayScreen.this, "YELLOW", Toast.LENGTH_LONG).show();
                mp.start();

                if(balls [0] == 3) {
                    score++;
                    updateScore();
                    changeBall();
                }
            }
        });

        new CountUpTimer(1000) {
            TextView timeText = (TextView)findViewById(R.id.timeText);
            public void onTick(long elapsedTime) {
                timeText.setText("Time: " + elapsedTime / 1000);
                /*new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        changeBall();
                    }
                }.start();*/
            }
        }.start();
    }

    public void updateScore(){
        TextView scoreText = (TextView)findViewById(R.id.scoreText);
        scoreText.setText("Score: " + score);
    }

    private void changeBall(){
        Random randNum = new Random();
        ImageView ball3Image = (ImageView)findViewById(R.id.ball3);
        ImageView ball2Image = (ImageView)findViewById(R.id.ball2);
        ImageView ball1Image = (ImageView)findViewById(R.id.ball1);
        balls [0] = balls [1];
        balls [1] = balls [2];
        balls [2] = randNum.nextInt(4);
        ball1Image.setImageResource(ballImages[balls [0]]);
        ball2Image.setImageResource(ballImages[balls [1]]);
        ball3Image.setImageResource(ballImages[balls [2]]);
    }

}
