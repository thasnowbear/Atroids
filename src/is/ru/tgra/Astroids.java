package is.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Calendar;

import com.badlogic.gdx.ApplicationListener;

public class Astroids implements ApplicationListener {
	long lastfire = System.currentTimeMillis();
	long firingInterval = 1000;
	private FloatBuffer vertexBuffer;
	private float boxWidth = 50;
	float missilespeed = 10;
	int level = 1;
	int astroidsperlevel = 4;
	int score = 0;
	long lastframe;
	long delta;
	private ArrayList<VisualItem> items = new ArrayList<VisualItem>();
	private ArrayList<VisualItem> deletelist = new ArrayList<VisualItem>();
	private spaceShip s = new spaceShip(300, 200, 4);
	SpriteBatch spriteBatch;
	BitmapFont font;
	CharSequence str;

	@Override
	public void create() { // create instance of the game alot taken from first
							// lab
		Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		// Create vertex buffer for a box.
		this.initVertexBuffer();
		this.spriteBatch = new SpriteBatch();
		this.font = new BitmapFont();

		// Specify the location of data in the vertex buffer that we will draw
		// when
		// we call the glDrawArrays function.
		Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, this.vertexBuffer);

		// Set the color that we want to clear the screen with.
		Gdx.gl11.glClearColor(0f, 0f, 0f, 1);
		items.add(s);
		for (int i = 0; i < astroidsperlevel; i++) {
			Astroid a = new Astroid(Gdx.input.getX() - (this.boxWidth / 2.0f),
					(Gdx.graphics.getHeight() - Gdx.input.getY())
							- (this.boxWidth / 2.0f), 4);
			a.giverandomspeedEasy();
			this.items.add(a);
		}
	}

	public void checkWin() {// checks if all astroids are elminated if true
							// calls next level
		boolean allAstroidsDead = true;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof Astroid)
				allAstroidsDead = false;
		}
		if (allAstroidsDead) {
			this.nextLevel();
		}
	}

	@Override
	public void render() {
		this.checkWin(); // checks for win every render
		Calendar now = Calendar.getInstance();
		long i = now.getTimeInMillis();
		delta = i - lastframe;
		lastframe = i;
		this.display();
		// moving all items;
		for (VisualItem item : items) {
			item.move(delta);
		}
		// Drawing all items;
		for (VisualItem item : items) {
			item.draw();
			if (item.OutOfBounds(item.x, item.y))
				if (item instanceof missilse) {
					deletelist.add(item);
				} else
					item.moveBackOnScreen(item.x, item.y);
		}// Checks for collisions and acts if collisions are found
		for (int p = 0; p < items.size(); p++) {
			for (int s = p + 1; s < items.size(); s++) {
				VisualItem one = items.get(p);
				VisualItem two = items.get(s);
				// if colission with spaceship accours GameOver
				if (one.isCollision(two) && one instanceof spaceShip
						&& two instanceof Astroid) {
					one.isAlive = false;
					this.reset();
				}
				// Colission with missilse and astroids
				if (one.isCollision(two) && one instanceof Astroid
						&& two instanceof missilse) {
					deletelist.add(one);
					deletelist.add(two);
					score += 100;
				}
			}
		}
		// removes all instances that collided
		items.removeAll(deletelist);
		deletelist.clear();
		this.update();
	}

	private void reset() { // reset function if player dies;
		level = 1;
		astroidsperlevel = 4;
		items.clear();
		score = 0;
		s = new spaceShip(300, 200, 4);
		s.isAlive = true;
		items.add(s);
		for (int i = 0; i < astroidsperlevel; i++) {
			Astroid a = new Astroid(Gdx.input.getX() - (this.boxWidth / 2.0f),
					(Gdx.graphics.getHeight() - Gdx.input.getY())
							- (this.boxWidth / 2.0f), 4);
			a.giverandomspeedEasy();
			this.items.add(a);
		}
	}

	private void nextLevel() {// increasing diffculty with each level
		level += 1;
		astroidsperlevel = 5 * level;
		for (int i = 0; i < astroidsperlevel; i++) {
			Astroid a = new Astroid(Gdx.input.getX() - (this.boxWidth / 2.0f),
					(Gdx.graphics.getHeight() - Gdx.input.getY())
							- (this.boxWidth / 2.0f), 4);
			a.giverandomspeedEasy();
			this.items.add(a);
		}
	}

	private void update() {
		//Spawns a Box where the mouse gets pressed;
		//used for testing purposes
		// Check if the mouse has been pressed.
		if (Gdx.input.justTouched()) {
			Astroid a = new Astroid(Gdx.input.getX() - (this.boxWidth / 2.0f),
					(Gdx.graphics.getHeight() - Gdx.input.getY())
							- (this.boxWidth / 2.0f), 4);
			a.giverandomspeedEasy();
			this.items.add(a);
		}
		// Selfmove
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			if (System.currentTimeMillis() - lastfire > firingInterval) {
				lastfire = System.currentTimeMillis();
				missilse m = new missilse(s.getX(), s.getY(), 4);
				// formula for new possition = current X - unitvector times
				// movement speed
				float x = (float) (s.unitVectorX());
				float y = (float) (s.unitVectorY());
				m.givespeed(x, y);
				this.items.add(m);
			}
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			float angle = s.getAngle();
			if (angle == 360)
				angle = 0;
			s.setAngle(angle += 5);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			float angle = s.getAngle();
			if (angle == 0)
				angle = 360;
			s.setAngle(angle -= 5);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			s.throttle();
		}
	}

	private void display() {
		// On each render we clear the screen!
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		Gdx.gl11.glLoadIdentity();
		/*
		 * //Uncomment this for Score and level; font.setColor(1.0f, 1.0f, 1.0f,
		 * 1.0f); spriteBatch.begin(); str = ("Level: " + level + " Score: " +
		 * score); font.draw(spriteBatch, str, 20, 30); spriteBatch.end();
		 */
	}

	@Override
	public void resize(int width, int height) {
		// Load the Project matrix. Next commands will be applied on that
		// matrix.
		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();

		// Set up a two-dimensional orthographic viewing region.
		Gdx.glu.gluOrtho2D(Gdx.gl11, 0, width, 0, height);

		// Set up affine transformation of x and y from world coordinates to
		// window coordinates
		Gdx.gl11.glViewport(0, 0, width, height);

		// Set the Modelview matrix back.
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW_MATRIX);
	}

	@Override
	public void resume() {
	}

	private void initVertexBuffer() {
		this.vertexBuffer = BufferUtils.newFloatBuffer(14);

		float[] box = new float[] { 0, 0, 0, boxWidth, boxWidth, 0, boxWidth,
				boxWidth };
		float triangle[] = new float[] { 0, 0, 10, 0, 10, 10 };

		for (int i = 0; i < box.length; i++)
			this.vertexBuffer.put(box[i]);

		for (int i = 0; i < triangle.length; i++)
			this.vertexBuffer.put(triangle[i]);

		this.vertexBuffer.rewind();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
}
