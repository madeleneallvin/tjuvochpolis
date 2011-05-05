package android.tjuvochpolis;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class ThiefHudFactory extends AbstractHudFactory {

	public ThiefHudFactory(PlayState ps)
	{
		super(ps);
		mBottomHudBackgroundImage = BitmapFactory.decodeResource(ps.getContext().getResources(), R.drawable.htc_desire_tjuv);
	}
	
	public void generateBottomHud()
	{
		mBottomHud = mBottomHudBackgroundImage;
	}
}