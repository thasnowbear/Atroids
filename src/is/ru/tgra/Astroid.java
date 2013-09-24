package is.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

import java.util.Random;

public class Astroid extends VisualItem {
	Random r = new Random();
	boolean plusOrMinus = false;
	float randomspeedX = r.nextInt(40);
	float randomspeedY = r.nextInt(40);

	public Astroid(float x, float y, int i) {
		super(x, y, i);
		// TODO Auto-generated constructor stub
	}

	//draw function for an astroid
	public void draw() {
		Gdx.gl11.glPushMatrix();
		// Gdx.gl11.glScalef(30, 30, 40);
		Gdx.gl11.glTranslatef(x, y, 0);
		Gdx.gl11.glColor4f(1f,1f,1f, 1f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glPopMatrix();
	}
	
	
	//give randomspeed and direction to given Astroid;
	public void giverandomspeedEasy() {
		int four = r.nextInt(4);
		float randomspeedX = r.nextInt(40);
		float randomspeedY = r.nextInt(40);
		if (randomspeedX < 10) {
			randomspeedX = 11;
		}
		if (randomspeedY < 10) {
			randomspeedY = 11;
		}
		if (four == 0) {
			setSpeedx(randomspeedX);
			setSpeedy(randomspeedY);
		} else if (four == 1) {
			setSpeedx(-randomspeedX);
			setSpeedy(randomspeedY);
		} else if (four == 2) {
			setSpeedx(randomspeedX);
			setSpeedy(-randomspeedY);
		} else {
			setSpeedx(-randomspeedX);
			setSpeedy(-randomspeedY);
		}
	}

}
