package kroki.profil.panel;

import kroki.mockup.model.Composite;
import kroki.mockup.model.components.Button;
import kroki.mockup.model.components.TitledContainer;
import kroki.profil.ComponentType;
import kroki.profil.group.ElementsGroup;
import kroki.profil.panel.mode.OperationMode;
import kroki.profil.panel.mode.ViewMode;
import kroki.profil.panel.std.StdDataSettings;
import kroki.profil.panel.std.StdPanelSettings;
import kroki.profil.persistent.PersistentClass;
import kroki.profil.subsystem.BussinesSubsystem;
import kroki.uml_core_basic.UmlPackage;

/**
 * <code>ParameterPanel</code> represents a panel used to input parameters
 * of some visible operation (<code>VisibleOperation</code>) which is connected to
 * a button or a many item which the user can use to activate the operation
 * @author Vladan Marsenić (vladan.marsenic@gmail.com)
 */
public class ParameterPanel extends VisibleClass{

	private static final long serialVersionUID = 1L;
	/**Is it permitted or forbidden to enter new data*/  
    protected boolean add = true;
    /**Is it permitted or forbidden modify existing data*/
    protected boolean update = true;
    /**Is it permitted or forbidden to copy data*/
    protected boolean copy = true;
    /**Is it permitted or forbidden to delete data*/ 
    protected boolean delete = true;
    /**Is it permitted or forbidden to perform data search*/
    protected boolean search = true;
    /**Is it permitted or forbidden to change data view (from tabular to one record one screen and vice versa*/
    protected boolean changeMode = true;
    /**Is it permitted or forbidden to perform row navigation (first, previous, next, last)*/ 
    protected boolean dataNavigation = true;
    /**parameter panel setting*/
    protected transient StdPanelSettings stdPanelSettings = new StdPanelSettings();
    /**Setting connected to data which the panel shows*/
    protected transient StdDataSettings stdDataSettings = new StdDataSettings();
    /**Persistent class connected to the standard panel*/
    protected PersistentClass persistentClass;

    //panel with the components*/
    private ElementsGroup propertiesPanel;
    //panel on the right which contains the operations (transactions, reports...)
    private ElementsGroup operationsPanel;
    
    /*****************/
    /*Constructors   */
    /*****************/
    public ParameterPanel() {
        super();
        component = new TitledContainer("ParameterForm");
        component.getRelativePosition().setLocation(5, 5);
        component.getAbsolutePosition().setLocation(5, 5);
        component.getDimension().setSize(800, 500);
        component.getElementPainter().update();
        persistentClass = new PersistentClass();
        this.stdPanelSettings.setDefaultOperationMode(OperationMode.ADD_MODE);
        this.stdPanelSettings.setDefaultViewMode(ViewMode.INPUT_PANEL_MODE);
    }

    public ParameterPanel(String tableName, String label, boolean visible, ComponentType componentType, boolean modal) {
        super(label, visible, componentType, modal);
        persistentClass = new PersistentClass(tableName);
    }

    public ParameterPanel(boolean modal) {
        super(modal);
        persistentClass = new PersistentClass();
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
    
    public BussinesSubsystem project(){
    	UmlPackage currentPack = umlPackage;
    	while (currentPack.nestingPackage() != null)
    		currentPack = currentPack.nestingPackage();
    	return (BussinesSubsystem) currentPack;
    }

    /**********************/
    /*GETTERS AND SETTERS*/
    /*********************/
    
    
    public StdDataSettings getStdDataSettings() {
    	if (stdDataSettings == null)
    		stdDataSettings = new StdDataSettings();
        return stdDataSettings;
    }

    public void setStdDataSettings(StdDataSettings stdDataSettings) {
        this.stdDataSettings = stdDataSettings;
    }

    public StdPanelSettings getStdPanelSettings() {
    	if (stdPanelSettings == null)
    		stdPanelSettings = new StdPanelSettings();
        return stdPanelSettings;
    }

    public void setStdPanelSettings(StdPanelSettings stdPanelSettings) {
        this.stdPanelSettings = stdPanelSettings;
    }

    public boolean isAdd() {
        return add;
    }

    public boolean isChangeMode() {
        return changeMode;
    }


    public boolean isCopy() {
        return copy;
    }

    public boolean isDataNavigation() {
        return dataNavigation;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isSearch() {
        return search;
    }

    public boolean isUpdate() {
        return update;
    }


    public ElementsGroup getOperationsPanel() {
        return operationsPanel;
    }

    public void setOperationsPanel(ElementsGroup operationsPanel) {
        this.operationsPanel = operationsPanel;
    }

    public ElementsGroup getPropertiesPanel() {
        return propertiesPanel;
    }

    public void setPropertiesPanel(ElementsGroup propertiesPanel) {
        this.propertiesPanel = propertiesPanel;
    }

    public PersistentClass getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass(PersistentClass persistentClass) {
        this.persistentClass = persistentClass;
    }
    
}
