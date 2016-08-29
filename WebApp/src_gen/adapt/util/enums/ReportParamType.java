package adapt.util.enums;

public enum ReportParamType {
	FORM_INPUT("FormInput"),
    BOOLEAN("Boolean"),
    DATE("Date"),
    INTEGER("Integer"),
    DECIMAL("Decimal"),
    STRING("String");

    private final String text;

    private ReportParamType(final String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return text;
    }
}
