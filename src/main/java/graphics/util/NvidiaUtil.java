package graphics.util;

import java.util.Map;

public class NvidiaUtil { // Check this for updates: https://github.com/NVIDIA/cuda-samples/blob/master/Common/helper_cuda.h#L699

  // TODO: ROPs can possibly be grabbed via NVApi, as can SMs and possibly shader units (cuda cores). SMs can also be grabbed via OpenCL and CUDA, though.
  // Hex notation: 0xMm, M = SM major version, m = SM minor version
  // SM and CU are interchangeable terms here. Shader units may also be known as SPs.
  private static final Map<Integer, GpuRecord> smDataMap = Map.ofEntries(
      Map.entry(0x10, new GpuRecord("Tesla", 8, 0, 0)), // Tesla
      Map.entry(0x11, new GpuRecord("Tesla", 8, 0, 0)), // Tesla
      Map.entry(0x12, new GpuRecord("Tesla", 8, 0, 0)), // Tesla
      Map.entry(0x13, new GpuRecord("Tesla", 8, 0, 0)), // Tesla
      Map.entry(0x20, new GpuRecord("Fermi", 32, 0, 0)), // Fermi
      Map.entry(0x21, new GpuRecord("Fermi", 48, 0, 0)), // Fermi
      Map.entry(0x30, new GpuRecord("Kepler", 192, 0, 0)), // Kepler
      Map.entry(0x32, new GpuRecord("Kepler", 192, 0, 0)), // Kepler
      Map.entry(0x35, new GpuRecord("Kepler", 192, 0, 0)), // Kepler
      Map.entry(0x37, new GpuRecord("Kepler", 192, 0, 0)), // Kepler
      Map.entry(0x50, new GpuRecord("Maxwell", 128, 0, 0)), // Maxwell
      Map.entry(0x52, new GpuRecord("Maxwell", 128, 0, 0)), // Maxwell
      Map.entry(0x53, new GpuRecord("Maxwell", 128, 0, 0)), // Maxwell
      Map.entry(0x60, new GpuRecord("Pascal", 64, 0, 0)), // Pascal
      Map.entry(0x61, new GpuRecord("Pascal", 128, 0, 0)), // Pascal
      Map.entry(0x62, new GpuRecord("Pascal", 128, 0, 0)), // Pascal
      Map.entry(0x70, new GpuRecord("Volta", 64, 8, 0)), // Volta
      Map.entry(0x72, new GpuRecord("Xavier", 64, 8, 0)), // Xavier
      Map.entry(0x75, new GpuRecord("Turing", 64, 8, 1)), // Turing
      Map.entry(0x80, new GpuRecord("Ampere", 64, 4, 0)), // Ampere, specifically GA100, so no RT cores
      Map.entry(0x86, new GpuRecord("Ampere", 128, 4, 1)), // Ampere
      Map.entry(0x87, new GpuRecord("Orin", 128, 4, 1)), // Ampere? Seems to refer to an Orin SoC which has Ampere architecture GPU
      Map.entry(0x90, new GpuRecord("Hopper", -1, 4, 0)) // Hopper, 128? 256?, specifically Gh100, so no RT cores
  );

  public static int getShaderUnitsPerSM(int major, int minor) {
    int versionEncoding = ((major << 4) + minor); // Convert major and minor version
    for (Map.Entry<Integer, GpuRecord> entry : smDataMap.entrySet()) {
      if (versionEncoding == entry.getKey()) {
        return entry.getValue().shaderUnitsPerCU();
      }
    }
    return -1; // Not in the table, so it's unknown.
  }

  public static int getTensorUnitsPerSM(int major, int minor) {
    int versionEncoding = ((major << 4) + minor); // Convert major and minor version
    for (Map.Entry<Integer, GpuRecord> entry : smDataMap.entrySet()) {
      if (versionEncoding == entry.getKey()) {
        return entry.getValue().tensorUnitsPerCU();
      }
    }
    return -1; // Not in the table, so it's unknown.
  }

  public static int getRaytraceUnitsPerSM(int major, int minor) {
    int versionEncoding = ((major << 4) + minor); // Convert major and minor version
    for (Map.Entry<Integer, GpuRecord> entry : smDataMap.entrySet()) {
      if (versionEncoding == entry.getKey()) {
        return entry.getValue().raytraceUnitsPerCU();
      }
    }
    return -1; // Not in the table, so it's unknown.
  }

  public static String getArchitecture(int major, int minor) {
    int versionEncoding = ((major << 4) + minor); // Convert major and minor version
    for (Map.Entry<Integer, GpuRecord> entry : smDataMap.entrySet()) {
      if (versionEncoding == entry.getKey()) {
        return entry.getValue().architecture();
      }
    }
    return "Unknown"; // Not in the table, so it's unknown.
  }

}
