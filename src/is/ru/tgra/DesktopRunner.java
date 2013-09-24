package is.ru.tgra;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopRunner {
	public static void main(String[] args) {
		new LwjglApplication(new Astroids(), "nice", 800, 600, false);
	}
}