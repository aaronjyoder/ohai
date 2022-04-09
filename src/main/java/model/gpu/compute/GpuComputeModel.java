package model.gpu.compute;

public class GpuComputeModel {

  private final int rasterOperationUnits;
  private final int textureMappingUnits;
  private final int computeUnits;
  private final int shaderUnits;
  private final int raytracingUnits;
  private final int tensorUnits;

  private GpuComputeModel(Builder builder) {
    this.rasterOperationUnits = builder.rasterOperationUnits;
    this.textureMappingUnits = builder.textureMappingUnits;
    this.computeUnits = builder.computeUnits;
    this.shaderUnits = builder.shaderUnits;
    this.raytracingUnits = builder.raytracingUnits;
    this.tensorUnits = builder.tensorUnits;
  }

  public int rasterOperationUnits() {
    return rasterOperationUnits;
  }

  public int textureMappingUnits() {
    return textureMappingUnits;
  }

  public int computeUnits() {
    return computeUnits;
  }

  public int shaderUnits() {
    return shaderUnits;
  }

  public int raytracingUnits() {
    return raytracingUnits;
  }

  public int tensorUnits() {
    return tensorUnits;
  }

  public static class Builder {

    private int rasterOperationUnits = 0;
    private int textureMappingUnits = 0;
    private int computeUnits = 0;
    private int shaderUnits = 0;
    private int raytracingUnits = 0;
    private int tensorUnits = 0;

    public Builder() {
    }

    public Builder rasterOperationUnits(int rasterOperationUnits) {
      this.rasterOperationUnits = rasterOperationUnits;
      return this;
    }

    public Builder textureMappingUnits(int textureMappingUnits) {
      this.textureMappingUnits = textureMappingUnits;
      return this;
    }

    public Builder computeUnits(int computeUnits) {
      this.computeUnits = computeUnits;
      return this;
    }

    public Builder shaderUnits(int shaderUnits) {
      this.shaderUnits = shaderUnits;
      return this;
    }

    public Builder raytracingUnits(int raytracingUnits) {
      this.raytracingUnits = raytracingUnits;
      return this;
    }

    public Builder tensorUnits(int tensorUnits) {
      this.tensorUnits = tensorUnits;
      return this;
    }

    public GpuComputeModel build() {
      return new GpuComputeModel(this);
    }

  }

}
