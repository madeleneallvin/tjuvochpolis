package android.tjuvochpolis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {
    /** Called when the activity is first created. */
	Context context;
	Activity activity;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menustate);
        activity = this;
        Button btnPlay = (Button)findViewById(R.id.Button01);
        Button btnExit = (Button)findViewById(R.id.Button02);
       
        btnPlay.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				Intent intent = new Intent(activity, GameActivity.class);
				startActivity(intent);
			}
		});
        
        btnExit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				finish();
			}
		});
        
              
        
        
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