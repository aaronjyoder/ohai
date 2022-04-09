package compute;

public class CUDAInstance {

  public void run(Runnable runnable) { // TODO: Check if extension is available before using it blindly
    initCUDA();
    mainLoop(runnable);
    cleanup();
  }

  private void initCUDA() {
  }

  private void mainLoop(Runnable runnable) {
    runnable.run();
  }

  private void cleanup() {
  }

}
