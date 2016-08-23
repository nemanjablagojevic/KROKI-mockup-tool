package kroki.profil.utils;

import kroki.mockup.model.Composite;
import kroki.mockup.model.layout.BorderLayoutManager;
import kroki.mockup.model.layout.LayoutManager;
import kroki.mockup.model.layout.VerticalLayoutManager;
import kroki.profil.ComponentType;
import kroki.profil.group.ElementsGroup;
import kroki.profil.group.GroupAlignment;
import kroki.profil.group.GroupLocation;
import kroki.profil.group.GroupOrientation;
import kroki.profil.panel.ParameterPanel;

/**
* Class contains <code>ParameterPanel</code> util methods 
* @author Kroki Team
*/
public class ParameterPanelUtil {

	  
	/**
	 * Sets default gui settings of the given panel.
	 * Creates toolbar, properties panel, option panel etc.
	 * @param panel Panel
	 */
   public static void defaultGuiSettings(ParameterPanel panel) {
       
       Composite root = ((Composite) panel.getComponent());
       root.setLayoutManager(new BorderLayoutManager());
       

       panel.setPropertiesPanel(new ElementsGroup("properties", ComponentType.PANEL));
       panel.getPropertiesPanel().setGroupLocation(GroupLocation.componentPanel);
       panel.getPropertiesPanel().setGroupOrientation(GroupOrientation.vertical);
       LayoutManager propertiesLayout = new VerticalLayoutManager(10, 10, VerticalLayoutManager.LEFT);
       ((Composite)  panel.getPropertiesPanel().getComponent()).setLayoutManager(propertiesLayout);
       ((Composite)  panel.getPropertiesPanel().getComponent()).setLocked(true);

       panel.setOperationsPanel(new ElementsGroup("operations", ComponentType.PANEL));
       panel.getOperationsPanel().setGroupLocation(GroupLocation.operationPanel);
       panel.getOperationsPanel().setGroupOrientation(GroupOrientation.vertical);
       panel.getOperationsPanel().setGroupAlignment(GroupAlignment.center);
       LayoutManager operationsLayout = new VerticalLayoutManager();
       operationsLayout.setAlign(LayoutManager.CENTER);
       ((Composite) panel.getOperationsPanel().getComponent()).setLayoutManager(operationsLayout);
       ((Composite) panel.getOperationsPanel().getComponent()).setLocked(true);

       UIPropertyUtil.addVisibleElement(panel, panel.getPropertiesPanel());
       UIPropertyUtil.addVisibleElement(panel, panel.getOperationsPanel());

       root.addChild(panel.getPropertiesPanel().getComponent(), BorderLayoutManager.CENTER);
       root.addChild(panel.getOperationsPanel().getComponent(), BorderLayoutManager.EAST);

       panel.update();

   }

}
