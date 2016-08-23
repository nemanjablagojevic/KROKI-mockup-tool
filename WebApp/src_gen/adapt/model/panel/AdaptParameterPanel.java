package adapt.model.panel;

import java.util.ArrayList;
import java.util.List;

import adapt.model.ejb.EntityBean;
import adapt.model.panel.configuration.DataSettings;
import adapt.model.panel.configuration.Next;
import adapt.model.panel.configuration.PanelSettings;
import adapt.model.panel.configuration.Zoom;
import adapt.model.panel.configuration.operation.SpecificOperations;

public class AdaptParameterPanel extends AdaptPanel {

	protected PanelSettings panelSettings;
	protected SpecificOperations standardOperations;
	protected DataSettings dataSettings;
	protected String associationEnd;
	protected List<Zoom> zoomPanels = new ArrayList<Zoom>();
	protected Boolean reportPanel;
	
	public PanelSettings getPanelSettings() {
		return panelSettings;
	}
	public void setPanelSettings(PanelSettings panelSettings) {
		this.panelSettings = panelSettings;
	}
	public SpecificOperations getStandardOperations() {
		return standardOperations;
	}
	public void setStandardOperations(SpecificOperations standardOperations) {
		this.standardOperations = standardOperations;
	}
	public DataSettings getDataSettings() {
		return dataSettings;
	}
	public void setDataSettings(DataSettings dataSettings) {
		this.dataSettings = dataSettings;
	}
	public List<Zoom> getZoomPanels() {
		return zoomPanels;
	}
	public void setZoomPanels(List<Zoom> zoomPanels) {
		this.zoomPanels = zoomPanels;
	}
	public String getAssociationEnd() {
		return associationEnd;
	}
	public void setAssociationEnd(String associationEnd) {
		this.associationEnd = associationEnd;
	}
	public Boolean getReportPanel() {
		return reportPanel;
	}
	public void setReportPanel(Boolean reportPanel) {
		this.reportPanel = reportPanel;
	}
	
}
