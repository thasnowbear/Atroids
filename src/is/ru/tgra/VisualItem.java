package is.ru.tgra;

import java.awt.Rectangle;

public abstract class VisualItem {
	float y;
	float x;
	float speedx;
	float speedy;
	int type;
	int width = 50;
	int height = 50;
	boolean isAlive = true;
	
	private Rectangle thisItem = new Rectangle();
	private Rectangle otherItem = new Rectangle();
	
	public VisualItem(float x, float y, int i){
		this.x = x;
		this.y = y;
		this.type = i;
	}

	//Constructor for a VisualItem./w height&width (Asteroids, Ship, Bullets)	
	public VisualItem(float x, float y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	//OverWrite in Missilse
	public boolean OutOfBounds(float x, float y) {
		if (y > 600)
			return true;
		if (x > 800)
			return true;
		if (y < -50)
			return true;
		if (x < -50)
			return true;
		return false;
	}
	
	//OverWrite in Missilse
	public void moveBackOnScreen(float x, float y) {
		if (y > 600) {
			setY(-49);
		}
		if (x > 800) {
			setX(-49);
		}
		if (y < -50) {
			setY(600);
		}
		if (x < -50) {
			setX(800);
		}
	}
	
	//Is there a collision of foreign object X;
	public boolean isCollision(VisualItem X){
		thisItem.setBounds((int)x, (int)y, width, height);
		otherItem.setBounds((int)X.x, (int)X.y, X.width, X.height);
		return thisItem.intersects(otherItem);
	}
	
	public void collidedWith(VisualItem X){
		System.out.println("derp");
	}

	//Changes the location of item, given the delta.
	public void move(long delta) {
		x += (delta * speedx) / 1000;
		y += (delta * speedy) / 1000;
	}
	
	public void draw(){
		
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}

	public float getSpeedx() {
		return speedx;
	}

	public void setSpeedx(float speedx) {
		this.speedx = speedx;
	}

	public float getSpeedy() {
		return speedy;
	}

	public void setSpeedy(float speedy) {
		this.speedy = speedy;
	}



}
