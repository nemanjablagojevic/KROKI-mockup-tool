package adapt.resources;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;

import adapt.core.AppCache;
import adapt.enumerations.OpenedAs;
import adapt.enumerations.PanelType;
import adapt.model.ejb.AbstractAttribute;
import adapt.model.ejb.ColumnAttribute;
import adapt.model.ejb.EntityBean;
import adapt.model.ejb.JoinColumnAttribute;
import adapt.model.panel.AdaptStandardPanel;
import adapt.util.converters.ConverterUtil;
import adapt.util.ejb.EntityHelper;
import adapt.util.xml_readers.PanelReader;

/**
 * Restlet resource that prepares edit form
 * @author Milorad Filipovic
 */
public class ModifyResource extends BaseResource{

	public ModifyResource(Context context, Request request, Response response) {
		super(context, request, response);
	}

	@Override
	public void handlePost() {
		handleGet();
	}

	@Override
	public void handleGet() {
		String panelName = (String)getRequest().getAttributes().get("panelName");
		String modifyID =  (String)getRequest().getAttributes().get("mid");
		String parentID =  (String)getRequest().getAttributes().get("pid");

		if(panelName != null && modifyID != null) {
			AdaptStandardPanel stdPanel = (AdaptStandardPanel) PanelReader.loadPanel(panelName, PanelType.STANDARDPANEL, null, OpenedAs.DEFAULT);
			if(stdPanel != null) {
				Object o = getObjectFromDB(stdPanel.getEntityBean().getEntityClass().getName(), modifyID);
				EntityBean bean = stdPanel.getEntityBean();
				addToDataModel("panel", stdPanel);
				addToDataModel("entityClassName", stdPanel.getEntityBean().getEntityClass().getName());
				prepareInputForm(stdPanel);
				prepareEditMap(bean, o);
				addToDataModel("modid", modifyID);
			}else {
				addToDataModel("css", "messageError");
				addToDataModel("message", "Unable to modify entry. Panel NULL");
			}
		}else {
			// TODO Handle errors
		}

		super.handleGet();
	}

	@Override
	protected LinkedHashMap<String, String> prepareEditMap(EntityBean bean, Object object) {
		super.prepareEditMap(bean, object);
		addToDataModel("editMap", this.editMap);
		addToDataModel("zoomEditMap", this.zoomEditMap);
		return this.editMap;
	}

	@Override
	public Representation represent(Variant variant) throws ResourceException {
		return getHTMLTemplateRepresentation("editFormTemplate.html", dataModel);
	}

}
