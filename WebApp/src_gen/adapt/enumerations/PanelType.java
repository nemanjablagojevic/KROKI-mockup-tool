package adapt.enumerations;

public enum PanelType {
	STANDARDPANEL("STANDARDPANEL"),
	PARENTCHILDPANEL("PARENTCHILDPANEL"),
	MANYTOMANYPANEL("MANYTOMANYPANEL"),
	PARAMETERPANEL("PARAMETERPANEL");
	
	String label;
	
	PanelType() {
	}
	
	PanelType(String label) {
		this.label = label;
	}
}
