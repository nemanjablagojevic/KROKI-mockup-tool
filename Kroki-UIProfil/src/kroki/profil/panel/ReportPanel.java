package kroki.profil.panel;

import kroki.mockup.model.Composite;
import kroki.mockup.model.components.Button;
import kroki.mockup.model.components.TitledContainer;
import kroki.profil.ComponentType;
import kroki.profil.group.ElementsGroup;
import kroki.profil.panel.std.StdDataSettings;
import kroki.profil.panel.std.StdPanelSettings;
import kroki.profil.persistent.PersistentClass;
import kroki.profil.subsystem.BussinesSubsystem;
import kroki.uml_core_basic.UmlPackage;

/**
 * <code>StandardPanel</code> represents a standard panel which is connected to
 * a persistent class (in case of a three-layered application) or a table
 * created when mapping an object model to a relational (in case of a two-layered application).
 * The visual properties and functionalities of the standard panel respects the HCI standards.
 * @author Vladan Marsenic (vladan.marsenic@gmail.com)
 */
public class ReportPanel extends StandardPanel {

    /*****************/
    /*Constructors   */
    /*****************/
    public ReportPanel() {
        super();
        component = new TitledContainer("ReportPanelForm");
        component.getRelativePosition().setLocation(5, 5);
        component.getAbsolutePosition().setLocation(5, 5);
        component.getDimension().setSize(800, 500);
        component.getElementPainter().update();
        persistentClass = new PersistentClass();
    }

}
