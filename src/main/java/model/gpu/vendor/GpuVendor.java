package model.gpu.vendor;

public enum GpuVendor { // Vendor IDs: https://pcisig.com/membership/member-companies

  UNKNOWN("Unknown", -1, MarketingTerms.UNKNOWN),
  AMD("AMD", 0x1022,
      new MarketingTerms(
          new Term("Compute Unit", "Compute Units", "CU", "CUs"),
          new Term("Stream Processor", "Stream Processors", "SP", "SPs"),
          new Term("Tensor Unit", "Tensor Units", "TU", "TUs"), // AMD doesn't have these yet, so just using the neutral singular.
          new Term("Ray Accelerator", "Ray Accelerators", "RA", "RAs")
      )
  ),
  INTEL("Intel", 8086,
      new MarketingTerms(
          new Term("Xe Core"),
          new Term("Vector Engine", "Vector Engines", "VE", "VEs"), // Not sure if this is an accurate analogue
          new Term("Matrix Engine", "Matrix Engines", "ME", "MEs"), // Not sure if Matrix Engines are the same as XMX Cores
          new Term("Ray Tracing Unit", "Ray Tracing Units", "RT Unit", "RT Units")
      )
  ),
  NVIDIA("NVIDIA", 0x10DE,
      new MarketingTerms(
          new Term("Stream Multiprocessor", "Stream Multiprocessors", "SM", "SMs"),
          new Term("CUDA Core"),
          new Term("Tensor Core"),
          new Term("RT Core")
      )
  );

  private final String vendorName;
  private final int id;
  private final MarketingTerms terms;

  GpuVendor(String vendorName, int id, MarketingTerms terms) {
    this.vendorName = vendorName;
    this.id = id;
    this.terms = terms;
  }

  public String vendorName() {
    return vendorName;
  }

  public int id() {
    return id;
  }

  public MarketingTerms terms() {
    return terms;
  }

}
