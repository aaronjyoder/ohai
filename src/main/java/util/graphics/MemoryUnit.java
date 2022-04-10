package util.graphics;

public enum MemoryUnit {

  BYTES(MemoryUnit.BYTE_SCALE),

  KILOBYTES(MemoryUnit.KILO_SCALE),
  MEGABYTES(MemoryUnit.MEGA_SCALE),
  GIGABYTES(MemoryUnit.GIGA_SCALE),
  TERABYTES(MemoryUnit.TERA_SCALE),
  PETABYTES(MemoryUnit.PETA_SCALE),

  KIBIBYTES(MemoryUnit.KIBI_SCALE),
  MEBIBYTES(MemoryUnit.MEBI_SCALE),
  GIBIBYTES(MemoryUnit.GIBI_SCALE),
  TEBIBYTES(MemoryUnit.TEBI_SCALE),
  PEBIBYTES(MemoryUnit.PEBI_SCALE);


  private static final long BYTE_SCALE = 1L;
  private static final long KILO_SCALE = 1000L;
  private static final long MEGA_SCALE = 1000L * KILO_SCALE;
  private static final long GIGA_SCALE = 1000L * MEGA_SCALE;
  private static final long TERA_SCALE = 1000L * GIGA_SCALE;
  private static final long PETA_SCALE = 1000L * TERA_SCALE;

  private static final long KIBI_SCALE = 1024L;
  private static final long MEBI_SCALE = 1024L * KIBI_SCALE;
  private static final long GIBI_SCALE = 1024L * MEBI_SCALE;
  private static final long TEBI_SCALE = 1024L * GIBI_SCALE;
  private static final long PEBI_SCALE = 1024L * TEBI_SCALE;

  private final long scale;

  MemoryUnit(long s) {
    this.scale = s;
  }

  public long convert(long sourceBytes, MemoryUnit sourceUnit) {
    return icvt(sourceBytes, scale, sourceUnit.scale);
  }

  private static long icvt(long bytes, long dstScale, long srcScale) {
    double ratio;
    long max;
    if (srcScale == dstScale) {
      return bytes;
    } else if (srcScale < dstScale) {
      double scaleRatio = (double) dstScale / srcScale;
      return (long) (bytes / scaleRatio);
    } else if (bytes > (max = (long) ((double) Long.MAX_VALUE / (ratio = (double) srcScale / dstScale)))) {
      return Long.MAX_VALUE;
    } else if (bytes < -max) {
      return Long.MIN_VALUE;
    } else {
      double result = bytes * ratio;
      return (long) result;
    }
  }


}
