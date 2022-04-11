package repository;

import static org.lwjgl.cuda.CU.CU_DEVICE_ATTRIBUTE_GLOBAL_MEMORY_BUS_WIDTH;
import static org.lwjgl.cuda.CU.CU_DEVICE_ATTRIBUTE_MULTIPROCESSOR_COUNT;
import static org.lwjgl.cuda.CU.cuDeviceComputeCapability;
import static org.lwjgl.cuda.CU.cuDeviceGet;
import static org.lwjgl.cuda.CU.cuDeviceGetAttribute;
import static org.lwjgl.cuda.CU.cuDeviceGetCount;
import static org.lwjgl.cuda.CU.cuInit;
import static org.lwjgl.system.MemoryStack.stackPush;

import compute.CUDAInstance;
import compute.OpenCLInstance;
import de.bommel24.nvmlj.NVMLJ;
import de.bommel24.nvmlj.NVMLJException;
import graphics.OpenGLInstance;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import model.gpu.GpuModel;
import model.gpu.compute.GpuComputeModel;
import model.gpu.driver.GpuDriverModel;
import model.gpu.general.GpuGeneralModel;
import model.gpu.memory.GpuMemoryModel;
import model.gpu.vendor.GpuVendor;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CL11;
import org.lwjgl.opengl.ATIMeminfo;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.NVXGPUMemoryInfo;
import org.lwjgl.system.MemoryStack;
import oshi.SystemInfo;
import util.graphics.NvidiaUtil;

public class GpuRepository {

  public List<GpuModel> getModels() {
    List<GpuModel> result = new ArrayList<>();
    var gpus = new SystemInfo().getHardware().getGraphicsCards();
    for (var gpu : gpus) {
      var vendor = getVendor(gpu.getVendor().toLowerCase());

      var general = new GpuGeneralModel.Builder(gpu.getName()).deviceId(gpu.getDeviceId())
          .vendor(vendor)
          .architecture(getArchitecture(vendor));

      var compute = generateComputeModel(vendor);
      var memory = new GpuMemoryModel.Builder(getFrameBufferSize(vendor)).busWidth(getMemoryBusWidth(vendor));
      var driver = new GpuDriverModel.Builder();

      if (vendor.equals(GpuVendor.NVIDIA)) {
        try { // TODO: Put this in Nvidia-specific area
          System.setProperty("nvml.path", "C:/Windows/System32/nvml.dll");
          NVMLJ.nvmlInit();
          var deviceHandle = NVMLJ.nvmlDeviceGetHandleByIndex(0);
          general.vbiosVersion(deviceHandle.nvmlDeviceGetVbiosVersion());
          driver.version(NVMLJ.nvmlSystemGetDriverVersion());
          NVMLJ.nvmlShutdown();
        } catch (NVMLJException e) {
          e.printStackTrace();
        }
      }

      result.add(new GpuModel(general.build(), compute, memory.build(), driver.build()));
    }
    return result;
  }

  private GpuVendor getVendor(String name) { // TODO: Use vendor ID instead
    var vendorName = name.toLowerCase(Locale.US);
    if (vendorName.contains("nvidia")) {
      return GpuVendor.NVIDIA;
    } else if (vendorName.contains("amd")) {
      return GpuVendor.AMD;
    } else if (vendorName.contains("intel")) {
      return GpuVendor.INTEL;
    }
    return GpuVendor.UNKNOWN;
  }

  private String getArchitecture(GpuVendor vendor) {
    AtomicReference<String> result = new AtomicReference<>("Unknown");

    switch (vendor) {
      case AMD -> {
        // TODO: Use AMD-specific APIs where possible
      }
      case INTEL -> {
        // TODO: Use Intel-specific APIs where possible
      }
      case NVIDIA -> {
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
      }
      case UNKNOWN -> {
        // TODO: Do not use any vendor-specific APIs
      }
    }

    return result.get();
  }

  private GpuComputeModel generateComputeModel(GpuVendor vendor) {
    var builder = new GpuComputeModel.Builder();

    switch (vendor) {
      case AMD -> {
        // TODO: Use AMD-specific APIs where possible
        new OpenCLInstance().run(() -> {
          try (MemoryStack stack = stackPush()) {
            IntBuffer pi = stack.mallocInt(1);
            CL11.clGetPlatformIDs(null, pi);

            PointerBuffer platforms = stack.mallocPointer(pi.get(0));
            CL11.clGetPlatformIDs(platforms, (IntBuffer) null);

            for (int p = 0; p < platforms.capacity(); p++) {
              long platform = platforms.get(p);

              CL11.clGetDeviceIDs(platform, CL10.CL_DEVICE_TYPE_GPU, null, pi);

              PointerBuffer devices = stack.mallocPointer(pi.get(0));
              CL11.clGetDeviceIDs(platform, CL10.CL_DEVICE_TYPE_GPU, devices, (IntBuffer) null);

              for (int d = 0; d < devices.capacity(); d++) {
                long device = devices.get(d);

                // TODO: Check device info before doing this so that multi-gpu setups can be supported

                CL11.clGetDeviceInfo(device, CL11.CL_DEVICE_MAX_COMPUTE_UNITS, pi, null);
                var computeUnits = pi.get(0);
                builder.computeUnits(computeUnits);
                builder.raytracingUnits(-1);
                builder.tensorUnits(0);
              }
            }
          }
        });
      }
      case INTEL -> {
        // TODO: Use Intel-specific APIs where possible
      }
      case NVIDIA -> {
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
            var raytracingUnitCount = NvidiaUtil.getRaytracingUnitsPerSM(major, minor) * cuCount;

            builder.computeUnits(cuCount);
            builder.unifiedShaderUnits(shaderUnitCount);
            builder.tensorUnits(tensorUnitCount);
            builder.raytracingUnits(raytracingUnitCount);
            builder.rasterOperationUnits(-1); // Don't know how to get these yet
            builder.textureMappingUnits(-1); // Don't know how to get these yet

          }
        });
      }
      case UNKNOWN -> {
        // TODO: Do not use any vendor-specific APIs
      }
    }

    return builder.build();
  }

  private long getFrameBufferSize(GpuVendor vendor) { // TODO: Might be better to do a lookup table, not sure.
    AtomicReference<Long> result = new AtomicReference<>(-1L);

    switch (vendor) {
      case AMD -> {
        // TODO: Use AMD-specific APIs where possible
        new OpenGLInstance().run(() -> {
          long dedicatedMem = GL11.glGetInteger(ATIMeminfo.GL_VBO_FREE_MEMORY_ATI); // This is in kilobytes
          result.set(dedicatedMem * 1024);
        });
      }
      case INTEL -> {
        // TODO: Use Intel-specific APIs where possible
      }
      case NVIDIA -> {
        try {
          System.setProperty("nvml.path", "C:/Windows/System32/nvml.dll");
          NVMLJ.nvmlInit();
          var deviceHandle = NVMLJ.nvmlDeviceGetHandleByIndex(0);
          result.set(deviceHandle.nvmlDeviceGetMemoryInfo().total);
          NVMLJ.nvmlShutdown();
        } catch (NVMLJException e) {
          new OpenGLInstance().run(() -> {
            long dedicatedMem = GL11.glGetInteger(NVXGPUMemoryInfo.GL_GPU_MEMORY_INFO_DEDICATED_VIDMEM_NVX); // This is in kilobytes
            result.set(dedicatedMem * 1024);
          });
        }
      }
      case UNKNOWN -> {
        // TODO: Do not use any vendor-specific APIs
      }
    }

    return result.get();
  }

  private int getMemoryBusWidth(GpuVendor vendor) {
    AtomicReference<Integer> result = new AtomicReference<>(-1);

    switch (vendor) {
      case AMD -> {
        // TODO: Use AMD-specific APIs where possible
      }
      case INTEL -> {
        // TODO: Use Intel-specific APIs where possible
      }
      case NVIDIA -> {
        new CUDAInstance().run(() -> {
          try (MemoryStack stack = stackPush()) {
            var pi = stack.mallocInt(1);
            cuInit(0);
            cuDeviceGetCount(pi);
            cuDeviceGet(pi, 0);
            var device = pi.get(0);

            cuDeviceGetAttribute(pi, CU_DEVICE_ATTRIBUTE_GLOBAL_MEMORY_BUS_WIDTH, device);
            result.set(pi.get(0));
          }
        });
      }
      case UNKNOWN -> {
        // TODO: Do not use any vendor-specific APIs
      }
    }

    return result.get();
  }

}
