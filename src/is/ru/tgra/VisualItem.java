package is.ru.tgra;

import java.awt.Rectangle;

public abstract class VisualItem {
	public float y;
	public float x;
	public float speedx;
	public float speedy;
	private Rectangle thisItem = new Rectangle();
	private Rectangle ohterItem = new Rectangle();
	
	public VisualItem(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void move(long delta){
		x += (delta * speedx) / 1000;
		y += (delta * speedy) / 1000;
	}

}
