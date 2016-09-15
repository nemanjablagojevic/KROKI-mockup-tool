package kroki.profil.panel.container;

import kroki.mockup.model.Composite;
import kroki.mockup.model.components.TitledContainer;
import kroki.profil.group.ElementsGroup;
import kroki.profil.panel.ContainerPanel;


public class HierarchyBasedForm extends ContainerPanel {
	
	
	private static final long serialVersionUID = 1L;

	private ElementsGroup propertiesPanel;

	public HierarchyBasedForm(String formName) {
		super();
		component = new TitledContainer(formName);
		component.getRelativePosition().setLocation(5, 5);
		component.getAbsolutePosition().setLocation(5, 5);
		component.getDimension().setSize(800, 500);
	}

	@Override
	public void update() {
		component.updateComponent();
		((Composite) component).layout();
	}

	@Override
	public String toString() {
		return label;
	}

	public ElementsGroup getPropertiesPanel() {
		return propertiesPanel;
	}

	public void setPropertiesPanel(ElementsGroup propertiesPanel) {
		this.propertiesPanel = propertiesPanel;
	}
}
