package util.graphics;

public enum MemoryUnit {

  BYTES(MemoryUnit.BYTE_SCALE),

  /**
   * Memory unit representing one thousand bytes.
   */
  KILOBYTES(MemoryUnit.KILO_SCALE),

  /**
   * Memory unit representing one million bytes.
   */
  MEGABYTES(MemoryUnit.MEGA_SCALE),

  /**
   * Memory unit representing one billion bytes.
   */
  GIGABYTES(MemoryUnit.GIGA_SCALE),

  /**
   * Memory unit representing one trillion bytes.
   */
  TERABYTES(MemoryUnit.TERA_SCALE),

  /**
   * Memory unit representing one quadrillion bytes.
   */
  PETABYTES(MemoryUnit.PETA_SCALE),

  /**
   * Memory unit representing 2^10 bytes.
   */
  KIBIBYTES(MemoryUnit.KIBI_SCALE),

  /**
   * Memory unit representing 2^20 bytes.
   */
  MEBIBYTES(MemoryUnit.MEBI_SCALE),

  /**
   * Memory unit representing 2^30 bytes.
   */
  GIBIBYTES(MemoryUnit.GIBI_SCALE),

  /**
   * Memory unit representing 2^40 bytes.
   */
  TEBIBYTES(MemoryUnit.TEBI_SCALE),

  /**
   * Memory unit representing 2^50 bytes.
   */
  PEBIBYTES(MemoryUnit.PEBI_SCALE);


  // Scale units
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

  public static String convertReadable(long srcBytes) {
    return convertReadable(srcBytes, false);
  }

  public static String convertReadable(long srcBytes, boolean siUnits) {
    long absBytes = Math.abs(srcBytes);
    if (siUnits) {
      if (absBytes >= PETA_SCALE) {
        return (long) (srcBytes / (double) PETA_SCALE) + " PB";
      } else if (absBytes >= TERA_SCALE) {
        return (long) (srcBytes / (double) TERA_SCALE) + " TB";
      } else if (absBytes >= GIGA_SCALE) {
        return (long) (srcBytes / (double) GIGA_SCALE) + " GB";
      } else if (absBytes >= MEGA_SCALE) {
        return (long) (srcBytes / (double) MEGA_SCALE) + " MB";
      } else if (absBytes >= KILO_SCALE) {
        return (long) (srcBytes / (double) KILO_SCALE) + " kB";
      } else {
        return srcBytes + " bytes";
      }
    } else {
      if (absBytes >= PEBI_SCALE) {
        return (long) (srcBytes / (double) PEBI_SCALE) + " PiB";
      } else if (absBytes >= TEBI_SCALE) {
        return (long) (srcBytes / (double) TEBI_SCALE) + " TiB";
      } else if (absBytes >= GIBI_SCALE) {
        return (long) (srcBytes / (double) GIBI_SCALE) + " GiB";
      } else if (absBytes >= MEBI_SCALE) {
        return (long) (srcBytes / (double) MEBI_SCALE) + " MiB";
      } else if (absBytes >= KIBI_SCALE) {
        return (long) (srcBytes / (double) KIBI_SCALE) + " KiB";
      } else {
        return srcBytes + " bytes";
      }
    }
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
