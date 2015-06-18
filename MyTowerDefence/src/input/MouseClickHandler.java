package input;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.*;
public class MouseClickHandler extends GLFWMouseButtonCallback
{

	@Override
	public void invoke(long window, int button, int action, int mods) {
//	window - the window that received the event
//	button - the mousebutton that was pressed or released
//	action - the mouse button action, one of : GLFW.GLFW_PRESS, GLFW.GLFW_RELEASE, GLFW.GLFW_REPEAT
//	mods - bitfield describing which modifiers keys were held down
//		System.out.println(button);
//		System.out.println(action);
	}
//    public boolean isKeyPressed(int key, long window)
//    {
//        return glfwGetKey(window, key) != GLFW_RELEASE;
//    }
//	void mouse_button_callback(GLFWwindow* window, int button, int action, int mods)
//	{
//	    if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS)
//	        popup_menu();
//	}
}
