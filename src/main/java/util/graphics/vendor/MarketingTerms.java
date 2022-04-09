package util.graphics.vendor;

public record MarketingTerms(Term computeUnitTerm, Term shaderUnitTerm, Term tensorUnitTerm, Term raytracingUnitTerm) {

  public static final MarketingTerms UNKNOWN = new MarketingTerms(
      new Term("Compute Unit", "Compute Units", "CU", "CUs"),
      new Term("Shader Unit"),
      new Term("Tensor Unit"),
      new Term("Raytracing Unit", "Raytracing Units", "RT Unit", "RT Units")
  );

}
