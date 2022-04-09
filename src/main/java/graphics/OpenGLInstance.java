package graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import constant.Strings;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class OpenGLInstance {

  private static final int WIDTH = 400;
  private static final int HEIGHT = 300;

  private long window;

  public void run(Runnable runnable) { // TODO: Check if extension is available before using it blindly
    initWindow();
    initOpenGL();
    mainLoop(runnable);
    cleanup();
  }

  private void initWindow() {
    GLFWErrorCallback.createPrint(System.err).set();

    if (!glfwInit()) {
      throw new RuntimeException("OpenGL: Cannot initialize GLFW");
    }

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

    window = glfwCreateWindow(WIDTH, HEIGHT, Strings.APP_NAME + " OpenGL Instance Window", NULL, NULL);

    if (window == NULL) {
      throw new RuntimeException("OpenGL: Cannot create window");
    }
  }

  private void initOpenGL() {
    createInstance();
  }

  private void mainLoop(Runnable runnable) {
    runnable.run();
  }

  private void cleanup() {
    glfwFreeCallbacks(window);
    glfwDestroyWindow(window);
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  private void createInstance() {
    try (MemoryStack stack = stackPush()) {
      glfwMakeContextCurrent(window);
      glfwSwapInterval(1);

      GL.createCapabilities();
    }
  }

}
