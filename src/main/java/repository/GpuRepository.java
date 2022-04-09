package repository;

import static org.lwjgl.cuda.CU.CU_DEVICE_ATTRIBUTE_MULTIPROCESSOR_COUNT;
import static org.lwjgl.cuda.CU.cuDeviceComputeCapability;
import static org.lwjgl.cuda.CU.cuDeviceGet;
import static org.lwjgl.cuda.CU.cuDeviceGetAttribute;
import static org.lwjgl.cuda.CU.cuDeviceGetCount;
import static org.lwjgl.cuda.CU.cuInit;
import static org.lwjgl.system.MemoryStack.stackPush;

import compute.CUDAInstance;
import graphics.util.NvidiaUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import model.gpu.GpuModel;
import model.gpu.compute.GpuComputeModel;
import model.gpu.driver.GpuDriverModel;
import model.gpu.general.GpuGeneralModel;
import model.gpu.memory.GpuMemoryModel;
import org.lwjgl.system.MemoryStack;
import oshi.SystemInfo;

public class GpuRepository {

  public List<GpuModel> getModels() {
    List<GpuModel> result = new ArrayList<>();
    var gpus = new SystemInfo().getHardware().getGraphicsCards();
    for (var gpu : gpus) {
      var general = new GpuGeneralModel.Builder(gpu.getName()).vendor(gpu.getVendor()).deviceId(gpu.getDeviceId()).architecture(getArchitecture()).build();
      var compute = generateComputeModel();
      var memory = new GpuMemoryModel.Builder(0).build();
      var driver = new GpuDriverModel.Builder().build();
      result.add(new GpuModel(general, compute, memory, driver));
    }
    return result;
  }

  private String getArchitecture() {
    AtomicReference<String> result = new AtomicReference<>("Unknown");
    new CUDAInstance().run(() -> {
      try (MemoryStack stack = stackPush()) {
        var pi = stack.mallocInt(1);
        cuInit(0);
        cuDeviceGetCount(pi);
        cuDeviceGet(pi, 0);
        var device = pi.get(0);

        var pt = stack.mallocInt(1);
        cuDeviceComputeCapability(pi, pt, device);

        var major = pi.get(0);
        var minor = pt.get(0);

        result.set(NvidiaUtil.getArchitecture(major, minor));
      }
    });
    return result.get();
  }

  private GpuComputeModel generateComputeModel() {
    var builder = new GpuComputeModel.Builder();

    new CUDAInstance().run(() -> {
      try (MemoryStack stack = stackPush()) {
        var pi = stack.mallocInt(1);
        cuInit(0);
        cuDeviceGetCount(pi);
        cuDeviceGet(pi, 0);
        var device = pi.get(0);

        var pt = stack.mallocInt(1);
        cuDeviceComputeCapability(pi, pt, device);

        var major = pi.get(0);
        var minor = pt.get(0);

        cuDeviceGetAttribute(pi, CU_DEVICE_ATTRIBUTE_MULTIPROCESSOR_COUNT, device);
        var cuCount = pi.get(0);

        var shaderUnitCount = NvidiaUtil.getShaderUnitsPerSM(major, minor) * cuCount;
        var tensorUnitCount = NvidiaUtil.getTensorUnitsPerSM(major, minor) * cuCount;
        var raytraceUnitCount = NvidiaUtil.getRaytraceUnitsPerSM(major, minor) * cuCount;

        builder.computeUnits(cuCount);
        builder.shaderUnits(shaderUnitCount);
        builder.tensorUnits(tensorUnitCount);
        builder.raytracingUnits(raytraceUnitCount);

      }
    });

    return builder.build();
  }

}
