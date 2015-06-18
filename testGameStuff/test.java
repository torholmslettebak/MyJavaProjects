package testGame;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class test extends Applet
{
	Canvas display_parent;

	/* Thread to run the main game loop*/
	Thread gameThread;

	/* Bool to control running of game loop thread*/
	boolean running = false;

	public void startLWJGL()
	{
		gameThread = new Thread()
		{
			public void run()
			{
				running = true;
				try 
				{
					Display.setParent(display_parent);
					Display.create();
					initGL();
				}
				catch (LWJGLException e)
				{
					e.printStackTrace();
					return;
				}
				gameLoop();
			}
		};
		gameThread.start();
	}

	/*Tell game loop to stop running, after which the LWJGL Display will 
	be destoryed. The main thread will wait for the Display.destroy().*/
	private void stopLWJGL() 
	{
		running = false;
		try 
		{
			gameThread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	public void init()
	{
		setLayout(new BorderLayout());
		try 
		{
			// A Canvas component represents a blank rectangular area of the screen onto which the application can draw or from which the application can trap input events from the user.
			display_parent = new Canvas()
			{
				public final void addNotify()
				{
					super.addNotify();
					startLWJGL();
				}
				public final void removeNotify()
				{
					stopLWJGL();
					super.removeNotify();
				}
			};
			display_parent.setSize(getWidth(), getHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
		// Because we do not want applet class to paint, but instead leave it to LWJGL later
			display_parent.setIgnoreRepaint(true);
			setVisible(true);
		}
		catch (exception e)
		{
			system.err.println(e);
			throw new RuntimeException("Unable to create display");
		}

	}

	public void start() 
	{

	}

	public void stop() 
	{

	}
	/*
	Applet Destroy method will remove the canvas, 
	before canvas is destroyed it will notify stopLWJGL()
	to stop the main game loop and to destroy the Display
	*/
	public void destroy() 
	{
		remove(display_parent);
		super.destroy();
	}

	protected void initGL()
	{

	}

	public void gameLoop() 
	{
		while(running) {
			Display.sync(60);
			Display.update();
		}
 
		Display.destroy();
	}
}