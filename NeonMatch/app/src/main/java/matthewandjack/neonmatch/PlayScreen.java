package matthewandjack.neonmatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by user on 22/04/2017.
 */

public class PlayScreen extends AppCompatActivity {
    private boolean red = false;
    private boolean blue = false;
    private boolean green = false;
    private boolean yellow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_screen);
    }

    public void main(String args[]){

    }

    public void redPressed(){
        red = true;
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText("RED");
    }

    public void bluePressed(){
        blue = true;
    }

    public void greenPressed(){
        green = true;
    }

    public void yellowPressed(){
        yellow = true;
    }
}
