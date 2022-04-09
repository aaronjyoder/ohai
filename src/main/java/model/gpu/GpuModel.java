package model.gpu;

import model.gpu.compute.GpuComputeModel;
import model.gpu.driver.GpuDriverModel;
import model.gpu.general.GpuGeneralModel;
import model.gpu.memory.GpuMemoryModel;

public record GpuModel(GpuGeneralModel general, GpuComputeModel compute, GpuMemoryModel memory, GpuDriverModel driver) {

}
