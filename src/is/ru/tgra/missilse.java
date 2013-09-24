package is.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

public class missilse extends VisualItem {

	public missilse(float x, float y, int i) {
		super(x, y, i);
		// TODO Auto-generated constructor stub
	}

	public void draw(){
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glColor4f(0.6f,0.6f,0.6f, 1f);
			Gdx.gl11.glTranslatef(x, y, 0);
			Gdx.gl11.glScalef(0.5f, 0.5f, 1.0f);
			Gdx.gl11.glTranslatef(25, 25, 0);
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
			Gdx.gl11.glPopMatrix();
	}

	//gives missilse speed
	public void givespeed(float x, float y) {
		setSpeedx(-x*500);
		setSpeedy(y*500);
	}

}