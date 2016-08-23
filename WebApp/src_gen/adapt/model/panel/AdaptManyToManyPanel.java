package adapt.model.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adapt.model.ejb.EntityBean;
import adapt.model.panel.configuration.Zoom;

public class AdaptManyToManyPanel extends AdaptPanel {

	protected EntityBean entityBean;
	protected List<AdaptStandardPanel> panels = new ArrayList<AdaptStandardPanel>();
	protected Zoom zoom = new Zoom();
	
	public void add(AdaptStandardPanel spanel) {
		panels.add(spanel);
	}
	
	public AdaptStandardPanel findByLevel(Integer level) {
		Iterator<AdaptStandardPanel> it = panels.iterator();
		while(it.hasNext()) {
			AdaptStandardPanel panel = it.next();
			if(panel.getLevel().intValue() == level.intValue()) {
				return panel;
			}
		}
		return null;
	}

	public List<AdaptStandardPanel> getPanels() {
		return panels;
	}

	public void setPanels(List<AdaptStandardPanel> panels) {
		this.panels = panels;
	}

	public Zoom getZoom() {
		return zoom;
	}

	public void setZoom(Zoom zoom) {
		this.zoom = zoom;
	}

	public EntityBean getEntityBean() {
		return entityBean;
	}

	public void setEntityBean(EntityBean entityBean) {
		this.entityBean = entityBean;
	}
	
	
	
}
