package matthewandjack.neonmatch;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.InputFilter;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 22/04/2017.
 */

public class PlayScreen extends AppCompatActivity {
    private int speed = 15;
    private int timeBetweenBalls = 700;
    private int score = 0;
    private int currentBall = 0;
    private int lastBallActive = 0;
    private int playerHearts = 3;
    private int time = 0;
    private int [] ballImages = {R.drawable.red, R.drawable.blue, R.drawable.green, R.drawable.yellow};
    private ArrayList<ImageView> ballImageViews = new ArrayList<ImageView>();
    private ArrayList<Boolean> ballImageViewsActive = new ArrayList<Boolean>();
    private ArrayList<Integer> ballImageViewsColor = new ArrayList<Integer>();
    private boolean gameOver = false;
    private boolean waiting = false;
    private MediaPlayer backgroundMusic;
    private boolean mute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_screen);
        mute = ((MyApplication) this.getApplication()).getMute();

        //Adding all balls to array list
        ballImageViews.add((ImageView)findViewById(R.id.ball1));
        ballImageViews.add((ImageView)findViewById(R.id.ball2));
        ballImageViews.add((ImageView)findViewById(R.id.ball3));
        ballImageViews.add((ImageView)findViewById(R.id.ball4));
        ballImageViews.add((ImageView)findViewById(R.id.ball5));
        ballImageViews.add((ImageView)findViewById(R.id.ball6));
        ballImageViews.add((ImageView)findViewById(R.id.ball7));
        ballImageViews.add((ImageView)findViewById(R.id.ball8));
        ballImageViews.add((ImageView)findViewById(R.id.ball9));
        ballImageViews.add((ImageView)findViewById(R.id.ball10));
        ballImageViews.add((ImageView)findViewById(R.id.ball11));
        ballImageViews.add((ImageView)findViewById(R.id.ball12));
        ballImageViews.add((ImageView)findViewById(R.id.ball13));
        ballImageViews.add((ImageView)findViewById(R.id.ball14));
        ballImageViews.add((ImageView)findViewById(R.id.ball15));

        Random randNum = new Random();
        for(int i = 0; i < ballImageViews.size(); i++) {
            ballImageViewsActive.add(false);
            ballImageViewsColor.add(ballImages[randNum.nextInt(4)]);
            ballImageViews.get(i).setImageResource(ballImageViewsColor.get(i));
        }

        final MediaPlayer tap = MediaPlayer.create(this, R.raw.tap8);
        final MediaPlayer miss = MediaPlayer.create(this, R.raw.tap6);
        backgroundMusic = MediaPlayer.create(this, R.raw.backgroundmusic);
        if(!mute) {
            backgroundMusic.start();
            backgroundMusic.setLooping(true);
        }
        final ImageButton redButton = (ImageButton) findViewById(R.id.redButton);
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ballImageViewsColor.get(currentBall) == ballImages[0]) {
                    if(!mute)
                        tap.start();
                    score += 10;
                    updateScore();
                    changeBall();
                }
                else {
                    playerHearts--;
                    if(!mute)
                        miss.start();
                }
            }
        });
        final ImageButton blueButton = (ImageButton) findViewById(R.id.blueButton);
        blueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ballImageViewsColor.get(currentBall) == ballImages[1]) {
                    if(!mute)
                        tap.start();
                    score += 10;
                    updateScore();
                    changeBall();
                }
                else {
                    playerHearts--;
                    if(!mute)
                        miss.start();
                }
            }
        });
        final ImageButton greenButton = (ImageButton) findViewById(R.id.greenButton);
        greenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ballImageViewsColor.get(currentBall) == ballImages[2]) {
                    if(!mute)
                        tap.start();
                    score += 10;
                    updateScore();
                    changeBall();
                }
                else {
                    playerHearts--;
                    if(!mute)
                        miss.start();
                }
            }
        });
        final ImageButton yellowButton = (ImageButton) findViewById(R.id.yellowButton);
        yellowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ballImageViewsColor.get(currentBall) == ballImages[3]) {
                    if(!mute)
                        tap.start();
                    score += 10;
                    updateScore();
                    changeBall();
                }
                else {
                    playerHearts--;
                    if(!mute)
                        miss.start();
                }
            }
        });

        //Game time timer
        new CountUpTimer(1000) {
            TextView timeText = (TextView)findViewById(R.id.timeText);

            public void onTick(long elapsedTime) {
                if(!gameOver) {
                    time++;
                    timeText.setText("Time: " + time);
                }
            }
        }.start();

        //Spawning balls timer
        new CountUpTimer(timeBetweenBalls) {
            public void onTick(long elapsedTime) {
                if(!gameOver) {
                    if(!waiting) {
                        new CountDownTimer(timeBetweenBalls , 10) {
                            @Override
                            public void onTick(long l) {
                                waiting = true;
                            }

                            @Override
                            public void onFinish() {
                                waiting = false;
                                if (lastBallActive >= ballImageViews.size())
                                    lastBallActive = 0;
                                ballImageViewsActive.set(lastBallActive, true);
                                lastBallActive++;
                            }
                        }.start();
                    }
                }
            }
        }.start();

        //Moving balls timer
        new CountUpTimer(33) {
            public void onTick(long elapsedTime) {
                if(!gameOver)
                    moveBalls();
            }
        }.start();

        //Speed increase timer
        new CountUpTimer(5000) {
            public void onTick(long elapsedTime) {
                if(speed <= 50)
                    speed += 3;
                if(timeBetweenBalls >= 70)
                    timeBetweenBalls -= 70;
            }
        }.start();
    }

    public void popUp(){
        gameOver = true;
        final DBHandler db = new DBHandler(this);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        int maxLength = 3;
        et.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(et);

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("Submit Name", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String name = et.getText().toString();
                int nID = db.getPlayerMaxID();
                if(name.length() != 0) {
                    db.addPlayer(new Player(nID, score, name));
                    makeToast("Score Submitted!");
                }
                else
                {
                    makeToast("Failed to submit. Invalid input.");
                }
                onBackPressed();
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void makeToast(String toasty){
        Toast.makeText(this, toasty,
                Toast.LENGTH_LONG).show();
    }
    public void updateScore(){
        TextView scoreText = (TextView)findViewById(R.id.scoreText);
        scoreText.setText("Score: " + score);
    }

    private void changeBall(){
        Random randNum = new Random();
        ballImageViewsActive.set(currentBall, false);
        int ranNum = randNum.nextInt(4);
        ballImageViews.get(currentBall).setImageResource(ballImages[ranNum]);
        ballImageViewsColor.set(currentBall, ballImages[ranNum]);
        updateCurrentBall();
    }

    private void updateCurrentBall() {
        currentBall++;
        if(currentBall >= 15)
            currentBall = 0;
    }

    private void moveBalls(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        TextView debugText = (TextView)findViewById(R.id.debugInfo);
        debugText.setText(" ");

        /*
        debugText.setText("Ball 1 " + ballImageViewsActive.get(0) + " " + ballImageViews.get(0).getY() +
                "\nBall 2 " + ballImageViewsActive.get(1) + " " + ballImageViews.get(1).getY() +
                "\nBall 3 " + ballImageViewsActive.get(2) + " " + ballImageViews.get(2).getY() +
                "\nBall 4 " + ballImageViewsActive.get(3) + " " + ballImageViews.get(3).getY() +
                "\nBall 5 " + ballImageViewsActive.get(4) + " " + ballImageViews.get(4).getY() +
                "\nBall 6 " + ballImageViewsActive.get(5) + " " + ballImageViews.get(5).getY() +
                "\nBall 7 " + ballImageViewsActive.get(6) + " " + ballImageViews.get(6).getY() +
                "\nBall 8 " + ballImageViewsActive.get(7) + " " + ballImageViews.get(7).getY() +
                "\nBall 9 " + ballImageViewsActive.get(8) + " " + ballImageViews.get(8).getY() +
                "\nBall 10 " + ballImageViewsActive.get(9) + " " + ballImageViews.get(9).getY() +
                "\nBall 11 " + ballImageViewsActive.get(10) + " " + ballImageViews.get(10).getY() +
                "\nBall 12 " + ballImageViewsActive.get(11) + " " + ballImageViews.get(11).getY() +
                "\nBall 13 " + ballImageViewsActive.get(12) + " " + ballImageViews.get(12).getY() +
                "\nBall 14 " + ballImageViewsActive.get(13) + " " + ballImageViews.get(13).getY() +
                "\nBall 15 " + ballImageViewsActive.get(14) + " " + ballImageViews.get(14).getY() +
                "\nSpeed: " + speed + "\nCurrent ball: " + (currentBall+1) + "\n timeBetweenBalls: " + timeBetweenBalls);
        */
        for (int i = 0; i < ballImageViews.size(); i++) {
            if (ballImageViewsActive.get(i)) {
                ballImageViews.get(i).setY(ballImageViews.get(i).getY() + speed);
                if (ballImageViews.get(i).getY() >= size.y - 100) {
                    ballImageViewsActive.set(i, false);
                    playerHearts--;
                    updateCurrentBall();
                }
            } else
                ballImageViews.get(i).setY(0xffffff38);
        }

        TextView heartsText = (TextView)findViewById(R.id.heartstext);
        heartsText.setText("Hearts: " + playerHearts);
        if (playerHearts <= 0) {
            playerHearts = 0;
            gameOver = true;
            popUp();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        gameOver = true;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(backgroundMusic.isPlaying())
            backgroundMusic.stop();
        else
            return;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(backgroundMusic.isPlaying())
            backgroundMusic.stop();
        else
            return;
    }

}
