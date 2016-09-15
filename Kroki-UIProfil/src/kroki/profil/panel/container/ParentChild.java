package kroki.profil.panel.container;

import kroki.profil.group.ElementsGroup;


/**
 * <code>ParentChild</code> represents a parent-child panel whose contained panels 
 * are organized in a hierarchical structure.
 * @author Vladan Marsenić (vladan.marsenic@gmail.com)
 * @author Renata
 */
public class ParentChild extends HierarchyBasedForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -209916544742961717L;
	
	private ElementsGroup operationsPanel;


	public ParentChild() {
		super("Parent child");
	}
	
	/*******************/
	/*GETTERS AND SETTERS**/
	/*******************/
	public ElementsGroup getOperationsPanel() {
		return operationsPanel;
	}

	public void setOperationsPanel(ElementsGroup operationsPanel) {
		this.operationsPanel = operationsPanel;
	}

}
