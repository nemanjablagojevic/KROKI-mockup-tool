package adapt.resources;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;

import adapt.core.AppCache;
import adapt.enumerations.OpenedAs;
import adapt.enumerations.OperationType;
import adapt.enumerations.PanelType;
import adapt.exceptions.OperationNotFoundException;
import adapt.model.ejb.AbstractAttribute;
import adapt.model.ejb.ColumnAttribute;
import adapt.model.ejb.JoinColumnAttribute;
import adapt.model.panel.AdaptStandardPanel;
import adapt.model.panel.configuration.operation.Operation;
import adapt.model.panel.configuration.operation.SpecificOperations;
import adapt.util.ejb.PersisenceHelper;
import adapt.util.enums.ReportParamType;
import adapt.util.html.TableModel;
import adapt.util.xml_readers.PanelReader;

/**
 * Restlet resource that fetches list of standard panels contained in many-to-many panel
 * and passes it to HTML page as JSON list
 * @author Nemanja Blagojevic
 */
public class GetReportOperationResource extends BaseResource {

	public GetReportOperationResource(Context context, Request request,Response response) {
		super(context, request, response);
	}

	@Override
	public void handleGet() {
		String panelName = (String) getRequest().getAttributes().get("panelName");
		String operationName = "";
		if(panelName != null) {
			AdaptStandardPanel panel = (AdaptStandardPanel) PanelReader.loadPanel(panelName, PanelType.STANDARDPANEL, null, OpenedAs.DEFAULT);
			SpecificOperations os = panel.getStandardOperations();
			if(os!=null){
				List<Operation> operations = os.getOperations();
				for(Operation o: operations){
					if(OperationType.VIEWREPORT.equals(o.getType())){
						operationName = o.getName();
					}
				}
			}
		}
		addToDataModel("operationName", operationName);
		
		super.handleGet();
	}

	@Override
	public void handlePost() {
		handleGet();
	}

	@Override
	public Representation represent(Variant variant) throws ResourceException {
		return getHTMLTemplateRepresentation("getReportOperation.JSON", dataModel);
	}

}
