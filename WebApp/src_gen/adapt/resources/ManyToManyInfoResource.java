package adapt.resources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;

import adapt.enumerations.OpenedAs;
import adapt.enumerations.PanelType;
import adapt.model.panel.AdaptStandardPanel;
import adapt.util.ejb.PersisenceHelper;
import adapt.util.staticnames.Tags;
import adapt.util.xml_readers.PanelReader;

/**
 * Restlet resource that fetches list of standard panels contained in many-to-many panel
 * and passes it to HTML page as JSON list
 * @author Nemanja Blagojevic
 */
public class ManyToManyInfoResource extends BaseResource {

	public ManyToManyInfoResource(Context context, Request request,Response response) {
		super(context, request, response);
	}

	@Override
	public void handleGet() {
		String panelName = (String) getRequest().getAttributes().get("mtmPanel");
		Integer zoomId = Integer.parseInt((String)getRequest().getAttributes().get("zoomId"));
		if(panelName != null) {
			ArrayList<String> panels = PanelReader.getJSONManyToManyPanelList(panelName);
			addToDataModel("panels", panels);
			
			List<String> zoomValues = PanelReader.getJSONZooms(panelName, Tags.MANY_TO_MANY);
			addToDataModel("zoomValues", zoomValues);
			
			addToDataModel("zoomId", zoomId);
			
//			if(!zoomValues.isEmpty() && zoomId!=null){
//				AdaptStandardPanel zoomPanel = (AdaptStandardPanel) PanelReader.loadPanel(zoomValues.get(0), PanelType.STANDARDPANEL, null, OpenedAs.DEFAULT);
//				EntityManager em = PersisenceHelper.createEntityManager();
//				em.getTransaction().begin();
//				String q = "FROM " + zoomPanel.getEntityBean().getName() + " x WHERE x.id=:did";
//				Object o = em.createQuery(q).setParameter("did", zoomId).getSingleResult();
//				addToDataModel("zoomId", zoomId);
//			}
			
			addToDataModel("panelName", "\""+panelName+"\"");
		}
		super.handleGet();
	}

	@Override
	public void handlePost() {
		handleGet();
	}

	@Override
	public Representation represent(Variant variant) throws ResourceException {
		return getHTMLTemplateRepresentation("manyToMany.JSON", dataModel);
	}

}
