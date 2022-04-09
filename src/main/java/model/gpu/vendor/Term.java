package model.gpu.vendor;

public record Term(String singular, String plural, String shortSingular, String shortPlural) {

  public Term(String singular, String plural) {
    this(singular, plural, singular, plural);
  }

  public Term(String singular) {
    this(singular, singular + "s", singular, singular + "s");
  }

}
