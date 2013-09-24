package is.ru.tgra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.BufferUtils;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;

public class Astroids implements ApplicationListener {
	boolean maxX = false;
	boolean maxY = false;
	int mode = 0;
	int angle = 0;
	float position_x;
	float position_y;
    private FloatBuffer vertexBuffer;
    private float boxWidth = 50;
    float a, b = 0;
    int type = 4;
    float missilespeed = 10; 

    private List<Box> boxes = new ArrayList<Box>();
    private List<Box> misslies = new ArrayList<Box>();
    private List<Box> oldboxes = new ArrayList<Box>();
    
    
    @Override
    public void create() {
        Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        // Create vertex buffer for a box.
        this.initVertexBuffer();
        
        // Specify the location of data in the vertex buffer that we will draw when
        // we call the glDrawArrays function.
        Gdx.gl11.glVertexPointer(2, GL11.GL_FLOAT, 0, this.vertexBuffer);
        
        // Set the color that we want to clear the screen with.
        Gdx.gl11.glClearColor(0f, 0f, 0f, 1);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void render() {
        this.display();
        this.update();
    }
    
    private void update(){
        // Check if the mouse has been pressed.
        if(Gdx.input.justTouched()) {
            this.boxes.add(new Box(Gdx.input.getX()- (this.boxWidth/2.0f), (Gdx.graphics.getHeight() - Gdx.input.getY()) - (this.boxWidth/2.0f),type));
        }
        if(Gdx.input.isKeyPressed(Keys.B) == true)
        	type = 4;
        if(Gdx.input.isKeyPressed(Keys.T) == true)
        	type = 3;
		// Selfmove
    	if(Gdx.input.isKeyPressed(Keys.SPACE)){
    		this.misslies.add(new Box(position_x, position_y,4));
    	}
		if(Gdx.input.isKeyPressed(Keys.A)){
			this.angle += 2;
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			this.angle -= 2;
		}
			if (Gdx.input.isKeyPressed(Keys.RIGHT) && position_x + 50 != 800) {
				this.position_x += 2;
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT) && position_x != 0) {
				this.position_x -= 2;
			}
			if (Gdx.input.isKeyPressed(Keys.UP) && position_y + 50 != 600) {
				this.position_y += 2;
			}
			if (Gdx.input.isKeyPressed(Keys.DOWN) && position_y != 0) {
				this.position_y -= 2;
			}
			
    }
    
    private void display() {
        // On each render we clear the screen!
        Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
        Gdx.gl11.glLoadIdentity(); 
        
        Gdx.gl11.glColor4f(0.6f+(a/100), 0.2f+(b/100), 0.2f+(b/100), 1f);
        Gdx.gl11.glPushMatrix();
        Gdx.gl11.glTranslatef(position_x, position_y, 0);
        Gdx.gl11.glRotatef(angle, 0, 0 ,1);
        Gdx.gl11.glTranslatef(-25,-25,0);
        Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
        Gdx.gl11.glPopMatrix();
        
        for(Box b : this.boxes){
            Gdx.gl11.glPushMatrix();
            //Gdx.gl11.glScalef(30, 30, 40);
            Gdx.gl11.glTranslatef(b.x, b.y, 0);
            Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, b.t);
            Gdx.gl11.glPopMatrix(); 
        }
        
        for(Box b : this.misslies){
            Gdx.gl11.glPushMatrix();
            Gdx.gl11.glScalef(0.5f, 0.5f, 1.0f);
            Gdx.gl11.glTranslatef(position_x, position_y+missilespeed, 0);
            Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
            Gdx.gl11.glPopMatrix(); 
        }

        a++;
        b++;
        a = a%100;
        b = b%100;
    }
    
    @Override
    public void resize(int width, int height) { 
        // Load the Project matrix. Next commands will be applied on that matrix.
        Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
        Gdx.gl11.glLoadIdentity();
        
        // Set up a two-dimensional orthographic viewing region.
        Gdx.glu.gluOrtho2D(Gdx.gl11, 0, width, 0, height);
        
        // Set up affine transformation of x and y from world coordinates to window coordinates
        Gdx.gl11.glViewport(0, 0, width, height);
        
        // Set the Modelview matrix back.
        Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW_MATRIX);
    }

    @Override
    public void resume() {  
    }
    
    
    private void initVertexBuffer(){
        this.vertexBuffer = BufferUtils.newFloatBuffer(14);
    
        float[] box = new float[] {0,0, 0,boxWidth,  boxWidth,0,  boxWidth,boxWidth};
        float triangle[] = new float[] {0,0, 10,0, 10,10};
        
        for(int i=0; i< box.length; i++)
            this.vertexBuffer.put(box[i]);
        
        for(int i=0; i< triangle.length; i++)
            this.vertexBuffer.put(triangle[i]);

        this.vertexBuffer.rewind();
    }   
}