package android.tjuvochpolis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	SharedPreferences mPrefs;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menustate);
        activity = this;
        Button btnPlay = (Button)findViewById(R.id.Button01);
        Button btnContinue = (Button)findViewById(R.id.Button02);
        Button btnExit = (Button)findViewById(R.id.Button03);
       
        mPrefs = this.getSharedPreferences("gamePrefs", MODE_WORLD_READABLE);
        
        btnPlay.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SharedPreferences.Editor ed = mPrefs.edit();
				ed.clear();
				ed.commit();
				Intent intent = new Intent(activity, GameActivity.class);
				startActivity(intent);
			}
		});
        
        btnContinue.setOnClickListener(new OnClickListener() {
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
        
              
        
        
       /* // avkommentera när saved states blir aktuellt
       // get handles to the GameView from XML, and its GameThread
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