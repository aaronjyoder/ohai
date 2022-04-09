package model.gpu.memory;

public class GpuMemoryModel {

  private final int size; // bytes
  private final String type;
  private final String vendor;
  private final int busWidth; // -bit

  private GpuMemoryModel(Builder builder) {
    this.size = builder.size;
    this.type = builder.type;
    this.vendor = builder.vendor;
    this.busWidth = builder.busWidth;
  }

  public int size() {
    return size;
  }

  public String type() {
    return type;
  }

  public String vendor() {
    return vendor;
  }

  public int busWidth() {
    return busWidth;
  }

  public static class Builder {

    private final int size;
    private String type = "Unknown";
    private String vendor = "Unknown";
    private int busWidth = 0;

    public Builder(int size) {
      this.size = size;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder vendor(String vendor) {
      this.vendor = vendor;
      return this;
    }

    public Builder busWidth(int busWidth) {
      this.busWidth = busWidth;
      return this;
    }

    public GpuMemoryModel build() {
      return new GpuMemoryModel(this);
    }

  }

}
