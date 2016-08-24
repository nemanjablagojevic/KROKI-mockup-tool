package adapt.util.enums;

public enum ReportParamType {
	FORM_INPUT("FormInput"),
    DATE("Date"),
    INTEGER("Integer"),
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
