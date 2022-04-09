package graphics;

import static org.lwjgl.glfw.GLFW.GLFW_CLIENT_API;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_NO_API;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.VK10.VK_API_VERSION_1_0;
import static org.lwjgl.vulkan.VK10.VK_MAKE_VERSION;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO;
import static org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
import static org.lwjgl.vulkan.VK10.VK_SUCCESS;
import static org.lwjgl.vulkan.VK10.vkCreateInstance;
import static org.lwjgl.vulkan.VK10.vkDestroyInstance;

import constant.Strings;
import java.util.function.Consumer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;

public class VulkanInstance {

  private static final int WIDTH = 400;
  private static final int HEIGHT = 300;

  private long window;
  private VkInstance instance;

  public void run(Consumer<VkInstance> consumer) { // TODO: Check if extension is available before using it blindly
    initWindow();
    initVulkan();
    mainLoop(consumer);
    cleanup();
  }

  private void initWindow() {
    if (!glfwInit()) {
      throw new RuntimeException("Vulkan: Cannot initialize GLFW");
    }

    glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

    window = glfwCreateWindow(WIDTH, HEIGHT, Strings.APP_NAME + " Vulkan Instance Window", NULL, NULL);

    if (window == NULL) {
      throw new RuntimeException("Vulkan: Cannot create window");
    }
  }


  private void initVulkan() {
    createInstance();
  }

  private void mainLoop(Consumer<VkInstance> consumer) {
    consumer.accept(instance);
  }

  private void cleanup() {
    vkDestroyInstance(instance, null);
    glfwDestroyWindow(window);
    glfwTerminate();
  }

  private void createInstance() {
    try (MemoryStack stack = stackPush()) {

      VkApplicationInfo appInfo = VkApplicationInfo.calloc(stack);

      appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO);
      appInfo.pApplicationName(stack.UTF8Safe(Strings.APP_NAME + " Vulkan Instance Window"));
      appInfo.applicationVersion(VK_MAKE_VERSION(1, 0, 0));
      appInfo.pEngineName(stack.UTF8Safe("No Engine"));
      appInfo.engineVersion(VK_MAKE_VERSION(1, 0, 0));
      appInfo.apiVersion(VK_API_VERSION_1_0);

      VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.calloc(stack);

      createInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
      createInfo.pApplicationInfo(appInfo);
      createInfo.ppEnabledExtensionNames(glfwGetRequiredInstanceExtensions());
      createInfo.ppEnabledLayerNames(null);

      PointerBuffer instancePtr = stack.mallocPointer(1);

      if (vkCreateInstance(createInfo, null, instancePtr) != VK_SUCCESS) {
        throw new RuntimeException("Failed to create instance");
      }

      instance = new VkInstance(instancePtr.get(0), createInfo);
    }
  }

}
