package ba.unsa.etf.rma.elvircrn.rma17_17455;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;


/**
 * Created by Amar.B on 16.05.2017..
 */

public class MyResultReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public MyResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
