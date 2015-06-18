package input;
import org.lwjgl.glfw.GLFWCursorPosCallback;

//Our MouseHandler class extends the abstract class
//abstract classes should never be instantiated so here
//we create a concrete that we can instantiate
public class MouseHandler extends GLFWCursorPosCallback
{
	@Override
	public void invoke(long window, double xpos, double ypos) 
	{
		// this basically just prints out the X and Y coordinates 
		// of our mouse whenever it is in our window
		System.out.println("X: " + xpos + " Y: " + ypos);
	}


}
