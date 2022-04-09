package model.gpu.driver;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class GpuDriverModel {

  private final String version;
  private final Instant date;
  private final String signature;

  private GpuDriverModel(Builder builder) {
    this.version = builder.version;
    this.date = builder.date;
    this.signature = builder.signature;
  }

  public String version() {
    return version;
  }

  public Instant date() {
    return date;
  }

  public String dateString() {
    return DateTimeFormatter.ofPattern("MMM d, yyyy").format(LocalDateTime.ofInstant(date, ZoneOffset.UTC));
  }

  public String signature() {
    return signature;
  }

  public static class Builder {

    private String version = "Unknown";
    private Instant date = Instant.EPOCH;
    private String signature = "Unknown";

    public Builder() {
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public Builder date(Instant date) {
      this.date = date;
      return this;
    }

    public Builder signature(String signature) {
      this.signature = signature;
      return this;
    }

    public GpuDriverModel build() {
      return new GpuDriverModel(this);
    }

  }

}
