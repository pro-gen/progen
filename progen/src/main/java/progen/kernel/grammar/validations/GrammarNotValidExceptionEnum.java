package progen.kernel.grammar.validations;

public enum GrammarNotValidExceptionEnum {
  NON_TERMINAL_SYMBOLS_IN_PRODUCTIONS_ERROR(33),
  INACCESIBLE_PRODUCTIONS_ERROR(34),
  SUPERFLUOUS_PRODUCTION_ERROR(35);
  
  private final int error;
  
  GrammarNotValidExceptionEnum(int error){
    this.error=error;
  }
  
  public int error(){return error;}

}
