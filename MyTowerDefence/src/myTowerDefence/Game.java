package myTowerDefence;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import input.KeyboardHandler;
import input.MouseHandler;
import input.MouseClickHandler;
import static org.lwjgl.glfw.Callbacks.*;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.glBindTexture;



import static org.lwjgl.glfw.GLFW.*; // allows us to create windows
import static org.lwjgl.opengl.GL11.*; // gives us access to things like "GL_TRUE" which we'll need 
import static org.lwjgl.system.MemoryUtil.*; // allows us to use 'NULL' in our code, note this is slightly different from java's 'null'

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer; // Used for getting the primary monitor later on.
import java.nio.DoubleBuffer;

import org.lwjgl.glfw.GLFWvidmode; // again used for primary monitor stuff.

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Game implements Runnable
{
	private Thread thread;
	public boolean running = true;
	private long window;
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback   keyCallback;
	private GLFWCursorPosCallback mouseCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	protected Map map;
	private int width = 1200, height = 800, tileSize = 50;
	InputStream in = null;
	
	Texture empty;
	URL emptyURL;
	
	private int id;
	private URL towerURL;
	private Texture tower;
	public static void main(String args[])
	{
		Game game = new Game();
		game.start();
	}
	
	public void start(){
		running = true;
		thread = new Thread(this, "EndlessRunner");
		thread.start();
	}

	public void init(){
		// Initializes our window creator library - GLFW 
		// This basically means, if this glfwInit() doesn't run properlly
		// print an error to the console
		if(glfwInit() != GL_TRUE){
			// Throw an error.
			System.err.println("GLFW initialization failed!");
		}
		

		
		
//		Create THe map

		// Allows our window to be resizable
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		// Creates our window. You'll need to declare private long window at the
		// top of the class though. 
		// We pass the width and height of the game we want as well as the title for
		// the window. The last 2 NULL parameters are for more advanced uses and you
		// shouldn't worry about them right now.
		window = glfwCreateWindow(width, height, "Endless Runner", NULL, NULL);

		double xpos, ypos;
		// This code performs the appropriate checks to ensure that the
		// window was successfully created. 
		// If not then it prints an error to the console
		if(window == NULL){
			// Throw an Error
			System.err.println("Could not create our Window!");
		}
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
        glfwSetCursorPosCallback(window, mouseCallback = new MouseHandler());
        glfwSetMouseButtonCallback(window, mouseButtonCallback = new MouseClickHandler());
        glfwSetInputMode(window, GLFW_STICKY_MOUSE_BUTTONS, 1);
        	
		// creates a bytebuffer object 'vidmode' which then queries 
		// to see what the primary monitor is. 
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Sets the initial position of our game window. 
		glfwSetWindowPos(window, 100, 100);
		// Sets the context of GLFW, this is vital for our program to work.
		glfwMakeContextCurrent(window);
		// finally shows our created window in all it's glory.
		glfwShowWindow(window);
		GLContext.createFromCurrent();
		map = new Map(tileSize, width, height);
//		loadImages();
		try {
//			File emptyTile = new File()
			emptyURL = new URL("file:////home/tor/Desktop/MyJavaProjects/MyTowerDefence/openTile.png");
			towerURL = new URL("file:////home/tor/Desktop/MyJavaProjects/MyTowerDefence/tower.png");
			empty = new Texture(emptyURL);
			tower = new Texture(towerURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated empty = new Texture()catch block
			e.printStackTrace();
		}
		
		
		System.out.println("GLX CURR: " + GLX.glXGetCurrentContext());
		System.out.println("GLFW CTX: " + GLFWLinux.glfwGetGLXContext(window));
		System.out.println("GLFW DSP: " + GLFWLinux.glfwGetX11Display());
		
	}
	
	private void loadImages() {
		try 
		{
			in = new FileInputStream("/openTile.png");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try {
			
			PNGDecoder decoder = new PNGDecoder(in);

			System.out.println("width="+decoder.getWidth());
			System.out.println("height="+decoder.getHeight());

			ByteBuffer buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
			decoder.decode(buf, decoder.getWidth()*4, Format.RGBA);
			buf.flip();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void update(){
		// Polls for any window events such as the window closing etc.
		glfwPollEvents();
	}
	
	public void render(){
		// Swaps out our buffers
		glfwSwapBuffers(window);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		for (int i = 0; i < map.getLengthX();i ++)
		{
			for (int j = 0; j < map.getLengthY(); j ++)
			{
				char c = map.getTile(i, j);
				switch(c)
				{

				case 't':
					tower.bind();
					// set the color of the quad (R,G,B,A)
					GL11.glColor3f(1.0f,1.0f,1.0f);
					//					glBindTexture(empty.target, empty.id);
					// draw quad
					GL11.glBegin(GL11.GL_QUADS);
						GL11.glTexCoord2f(0,0);
						GL11.glVertex2f(i*tower.width ,j*tower.width);
						GL11.glTexCoord2f(1,0);
						GL11.glVertex2f(i*tower.width+tower.width,j*tower.width);
						GL11.glTexCoord2f(1,1);
						GL11.glVertex2f(i*tower.width+tower.width,j*tower.width+tower.height);
						GL11.glTexCoord2d(0,1);
						GL11.glVertex2f(i*tower.width,j*tower.width+tower.height);
					GL11.glEnd();
					break;
					
				default:
					// Clear the screen and depth buffer
					empty.bind();

					// set the color of the quad (R,G,B,A)
					GL11.glColor3f(1.0f,1.0f,1.0f);
					//					glBindTexture(empty.target, empty.id);
					// draw quad
					GL11.glBegin(GL11.GL_QUADS);
						GL11.glTexCoord2f(0,0);
						GL11.glVertex2f(i*empty.width ,j*empty.width);
						GL11.glTexCoord2f(1,0);
						GL11.glVertex2f(i*empty.width+empty.width,j*empty.width);
						GL11.glTexCoord2f(1,1);
						GL11.glVertex2f(i*empty.width+empty.width,j*empty.width+empty.height);
						GL11.glTexCoord2d(0,1);
						GL11.glVertex2f(i*empty.width,j*empty.width+empty.height);
					GL11.glEnd();
					break;
				}
			}
		}
		

		
		
		
	}
	
	@Override
	public void run() {
		// All our initialization code
		init();
		drawMap();
//		Rendering.renderMap(map, window);
		GLContext.createFromCurrent();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		

		// Our main game loop
		while(running)
		{

			int stateLeft = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT);
			int stateRight = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT);
			DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
			if (stateLeft == GLFW_PRESS)
			{
				
//				glColor3f(1.0f,1.0f,1.0f);
//				glfwGetCursorPos(window, x, y);
//				glBegin(GL_LINE_STRIP);
//				glVertex2f((float) 1,(float) 1);
//				System.out.println(x.remaining());
//				glVertex2f((float) 500,(float) 500);
//				System.out.println("Mouse button left was pressed " + x.get() + " " + y.get());
				
			}
			if (stateRight == GLFW_PRESS)
			{
				glfwGetCursorPos(window, x, y);
//				System.out.println("Mouse button Right was pressed "+ x.get() + " " + y.get());
			}
			update();
			render();
			// Checks to see if either the escape button or the
			// red cross at the top were pressed.
			// if so sets our boolean to false and closes the
			// thread.
			if(glfwWindowShouldClose(window) == GL_TRUE){
				running = false;
			}
		}
	}

	private void drawMap() 
	{
//		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		for (int i = 0; i < map.getLengthX(); i++)
		{
			for (int j = 0; j < map.getLengthY(); j++)
			{
//				System.out.println(map.getLengthX() + " " + map.getLengthY());
//				System.out.println("x = "+i+" y = "+j);
				char c = map.getTile(i, j);
				switch(c)
				{
				case ' ':
					
				}
					
					
			}
		}
	}
	
//	private void craftTexture()
//	{
//		//Generally a good idea to enable texturing first
//		glEnable(GL_TEXTURE_2D);
//
//		//generate a texture handle or unique ID for this texture
//		id = glGenTextures();
//
//		//bind the texture
//		glBindTexture(GL_TEXTURE_2D, id);
//
//		//use an alignment of 1 to be safe
//		//this tells OpenGL how to unpack the RGBA bytes we will specify
//		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
//
//		//set up our texture parameters
//		//Setup filtering, i.e. how OpenGL will interpolate the pixels when scaling up or down
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//		
//		//Setup wrap mode, i.e. how OpenGL will handle pixels outside of the expected range
//		//Note: GL_CLAMP_TO_EDGE is part of GL12
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
//
//		//upload our ByteBuffer to GL
//		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, empty.buf);  
//	}
}
