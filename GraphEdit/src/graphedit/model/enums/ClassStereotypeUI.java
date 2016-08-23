package graphedit.model.enums;

public enum ClassStereotypeUI {
	STANDARD_PANEL ("StandardPanel"), PARENT_CHILD ("ParentChild"), MANY_TO_MANY("ManyToMany");
	
	private final String name;
	
	private ClassStereotypeUI(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
}
