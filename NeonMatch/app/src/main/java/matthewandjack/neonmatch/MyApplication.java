package matthewandjack.neonmatch;

import android.app.Application;

/**
 * Created by user on 30/04/2017.
 */

public class MyApplication extends Application {
    private boolean mute;

    public boolean getMute(){
        return mute;
    }

    public void setMute(boolean mute){
        this.mute = mute;
    }
}
