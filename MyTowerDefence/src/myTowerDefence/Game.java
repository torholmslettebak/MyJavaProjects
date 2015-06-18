package myTowerDefence;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
 
import input.KeyboardHandler;
import input.MouseHandler;
import input.MouseClickHandler;
import static org.lwjgl.glfw.Callbacks.*;

import static org.lwjgl.glfw.GLFW.*; // allows us to create windows
import static org.lwjgl.opengl.GL11.*; // gives us access to things like "GL_TRUE" which we'll need 
import static org.lwjgl.system.MemoryUtil.*; // allows us to use 'NULL' in our code, note this is slightly different from java's 'null'
import java.nio.ByteBuffer; // Used for getting the primary monitor later on.
import java.nio.DoubleBuffer;

import org.lwjgl.glfw.GLFWvidmode; // again used for primary monitor stuff.

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
	private int width = 1200, height = 800;
	
	public static void main(String args[]){
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
	}
	
	public void update(){
		// Polls for any window events such as the window closing etc.
		glfwPollEvents();
	}
	
	public void render(){
		// Swaps out our buffers
		glfwSwapBuffers(window);
	}
	
	@Override
	public void run() {
		// All our initialization code
		init();
		// Our main game loop
		while(running)
		{
			int stateLeft = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT);
			int stateRight = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT);
			DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
			if (stateLeft == GLFW_PRESS)
			{
				glfwGetCursorPos(window, x, y);
				System.out.println("Mouse button left was pressed " + x.get() + " " + y.get());
				
			}
			if (stateRight == GLFW_PRESS)
			{
				glfwGetCursorPos(window, x, y);
				System.out.println("Mouse button Right was pressed "+ x.get() + " " + y.get());
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
}
