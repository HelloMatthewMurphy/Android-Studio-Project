package matthewandjack.neonmatch;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by user on 25/04/2017.
 */

public abstract class CountUpTimer {

    private final long interval;
    private long base;
    private static final int MSG = 1;

    public CountUpTimer(long interval) {
        this.interval = interval;
    }

    public void start() {
        base = SystemClock.elapsedRealtime();
        handler.sendMessage(handler.obtainMessage(MSG));
    }

    abstract public void onTick(long elapsedTime);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (CountUpTimer.this) {
                long elapsedTime = SystemClock.elapsedRealtime() - base;
                onTick(elapsedTime);
                sendMessageDelayed(obtainMessage(MSG), interval);
            }
        }
    };
}