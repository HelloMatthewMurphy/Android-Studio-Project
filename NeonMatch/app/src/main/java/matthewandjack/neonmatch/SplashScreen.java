package matthewandjack.neonmatch;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by user on 28/04/2017.
 */

public class SplashScreen extends AppCompatActivity {

    private boolean movingDown = true;
    private ImageView text;
    private float startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        final ImageButton lonelyButton = (ImageButton) findViewById(R.id.lonelyButton);
        lonelyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreen.this, MainMenu.class);
                startActivity(intent);
            }
        });
        text = (ImageView)findViewById(R.id.text);
        startY = text.getY();

        textBounce();
    }

    private void textBounce() {
        new CountUpTimer(33) {
            public void onTick(long elapsedTime) {
                if(movingDown) {
                    text.setY(text.getY() + 4);
                    if(text.getY() >= 500)
                        movingDown = false;
                }
                else {
                    text.setY(text.getY() - 4);
                    if(text.getY() <= 400)
                        movingDown = true;
                }
            }
        }.start();

    }
}
