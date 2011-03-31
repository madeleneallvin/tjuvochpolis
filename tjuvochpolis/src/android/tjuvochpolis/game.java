package android.tjuvochpolis;

import android.app.Activity;
import android.os.Bundle;

public class game extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       
        //hej
        //tja, old style chat
        
        
        // avkommentera när saved states blir aktuellt
        /*// get handles to the GameView from XML, and its GameThread
        mGameView = (GameView) findViewById(R.id.tjuvochpolis);
        mGameThread = mGameView.getThread();

        if (savedInstanceState == null) {
            // we were just launched: set up a new game
            mGameThread.setState(GameThread.STATE_READY);
            Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
            mGamehread.restoreState(savedInstanceState);
            Log.w(this.getClass().getName(), "SIS is nonnull");
        }
        */
    }
}