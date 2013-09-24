package is.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

public class spaceShip extends VisualItem{
	float angle = 0;
	
	public spaceShip(float x, float y, int i) {
		super(x, y, i);
		// TODO Auto-generated constructor stub
	}

	public float getAngle() {
		return angle;
	}
	
	public void throttle(){
		double xVector = Math.sin(Math.toRadians(angle));
		double yVector = Math.cos(Math.toRadians(angle));
		double magnitude = Math.sqrt(xVector*xVector + yVector*yVector);
		double unitVecX = xVector/magnitude;
		double unitVecY = yVector/magnitude;
		float newPositionX = (float) (getX() - unitVecX * 5);
		float newPositionY = (float) (getY() + unitVecY * 5);
		setY(newPositionY);
		setX(newPositionX);
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public void draw(){
			Gdx.gl11.glPushMatrix();
			Gdx.gl11.glTranslatef(25, 25, 0);
			Gdx.gl11.glTranslatef(x, y, 0);
			Gdx.gl11.glRotatef(getAngle(), 0, 0, 1);
			Gdx.gl11.glTranslatef(-25, -25, 0);
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, type);
			Gdx.gl11.glPopMatrix();
	}

}
