package compute;

import org.lwjgl.opencl.CL;

public class OpenCLInstance {

  public void run(Runnable runnable) { // TODO: Check if extension is available before using it blindly
//    initOpenCL();
    mainLoop(runnable);
//    cleanup();
  }

  private void initOpenCL() {
    CL.create();
  }

  private void mainLoop(Runnable runnable) {
    runnable.run();
  }

  private void cleanup() {
    CL.destroy();
  }

}
