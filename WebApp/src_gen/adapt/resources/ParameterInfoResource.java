package adapt.resources;

import java.util.ArrayList;

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
import adapt.model.panel.AdaptStandardPanel;
import adapt.model.panel.configuration.operation.Operation;
import adapt.util.enums.ReportParamType;
import adapt.util.xml_readers.PanelReader;

/**
 * Restlet resource that fetches list of standard panels contained in many-to-many panel
 * and passes it to HTML page as JSON list
 * @author Nemanja Blagojevic
 */
public class ParameterInfoResource extends BaseResource {

	public ParameterInfoResource(Context context, Request request,Response response) {
		super(context, request, response);
	}

	@Override
	public void handleGet() {
		String panelName = (String) getRequest().getAttributes().get("panelName");
		String operationId = (String) getRequest().getAttributes().get("operationId");
		String dataId = (String)getRequest().getAttributes().get("dataId");
		
		try{
			if(panelName != null) {
				AdaptStandardPanel panel = (AdaptStandardPanel) PanelReader.loadPanel(panelName, PanelType.STANDARDPANEL, null, OpenedAs.DEFAULT);
				
				Operation operation = panel.getStandardOperations().findByName(operationId);
				if(operation!=null && OperationType.VIEWREPORT.equals(operation.getType())){
					
					ArrayList<String> panelDataList = new ArrayList<String>();
					String standardPanelData = "\"standardPanelData\":[";
					
					for(AbstractAttribute attr: panel.getEntityBean().getAttributes()){
						if(operation.getDataFilter()!=null && operation.getDataFilter().containsKey(attr.getLabel())){
							String parameterType = operation.getDataFilter().get(attr.getLabel());
							if(ReportParamType.FORM_INPUT.toString().equals(parameterType)){
								standardPanelData += "\""+attr.getName()+"\""+",";
							}
						}
					}
					if(standardPanelData.contains(",")){
						standardPanelData = standardPanelData.substring(0,standardPanelData.length()-1);
					}
					standardPanelData+="]";
					
					panelDataList.add(standardPanelData);
					addToDataModel("panelDataList", panelDataList);
					addToDataModel("parentPanelName", "\""+panelName+"\"");
				}
			}
			super.handleGet();
		}catch(OperationNotFoundException e){
			e.printStackTrace();
			AppCache.displayTextOnMainFrame("Error fetching parameter panel.", 1);
		}
	}

	@Override
	public void handlePost() {
		handleGet();
	}

	@Override
	public Representation represent(Variant variant) throws ResourceException {
		return getHTMLTemplateRepresentation("parameter.JSON", dataModel);
	}

}
