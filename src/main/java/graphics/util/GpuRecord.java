package graphics.util;

public record GpuRecord(String architecture, int shaderUnitsPerCU, int tensorUnitsPerCU, int raytraceUnitsPerCU) {

}
