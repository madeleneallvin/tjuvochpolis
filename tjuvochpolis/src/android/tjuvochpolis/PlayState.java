package android.tjuvochpolis;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

public class PlayState implements GameState
{
    private Grid grid;
    private Context mContext;
    private Bitmap mBackgroundImage;
    private CopObject cop;
	private ThiefObject thief;
	
	
	public PlayState(Context context)
	{
		grid = new Grid();
		
		cop = new CopObject(grid.gridArray[2][4]);		//ska kontrolleras av vart tjuvn�ste och polisstation ligger
		thief = new ThiefObject(grid.gridArray[4][7]);
		
		
		Resources res = context.getResources();
		
		mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.lofi_map);
	}
	
	public void handleState(Canvas canvas)
	{
		draw(canvas);
	}
	
	public void nextState(GameThread gt)
	{
		
	}
	
	private void draw(Canvas c)
	{
		c.drawBitmap(mBackgroundImage, -192, -192, null);
		cop.doDraw(c);
		thief.doDraw(c);
	}
	
	//Denna metoden ska g�ras abstrakt h�r och sedan implementeras i de underliggande staten. (copTurnState, thiefRollState osv.)
	public void moveTo(float x, float y)
	{
		int column = (int)Math.floor(x/30); //30 �r "lagom" storlek f�r punkterna som ritas ut
		int row = (int)Math.floor(y/30);
		cop.moveTo(grid.gridArray[column][row]);
	}
	//movement of a game object
	public void doTouch(View v, MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();
				
		
		moveTo(x, y);		//moveTo ska l�mpligen anropas med currentState och ska vara implementerad ide underliggande staten. (copTurnState, thiefRollState osv.)
	}
}
