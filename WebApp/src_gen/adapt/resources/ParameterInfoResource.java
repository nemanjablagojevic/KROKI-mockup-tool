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
import adapt.model.ejb.JoinColumnAttribute;
import adapt.model.panel.AdaptStandardPanel;
import adapt.model.panel.configuration.operation.Operation;
import adapt.util.ejb.PersisenceHelper;
import adapt.util.enums.ReportParamType;
import adapt.util.html.TableModel;
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
				LinkedHashMap<String, String> singleTableData = null;
				
				if(dataId!=null){
					String query = "FROM " + panel.getEntityBean().getEntityClass().getName()+" bean WHERE bean.id="+dataId;
					EntityManager em = PersisenceHelper.createEntityManager();

					em.getTransaction().begin();
					Query q = em.createQuery(query);
					List<Object> results = q.getResultList();
					
					TableModel model = new TableModel(panel.getEntityBean());
					ArrayList<LinkedHashMap<String, String>> tableModel = model.getModel(results);
					singleTableData = tableModel.get(0);
				}
				
				Operation operation = panel.getStandardOperations().findByName(operationId);
				if(operation!=null && OperationType.VIEWREPORT.equals(operation.getType())){
					
					String panelData = new String();
					String standardPanelData = "\"standardPanelData\":[";
					
					for(AbstractAttribute attr: panel.getEntityBean().getAttributes()){
						if(operation.getDataFilter()!=null && operation.getDataFilter().containsKey(attr.getLabel())){
							String parameterType = operation.getDataFilter().get(attr.getLabel());
							if(ReportParamType.FORM_INPUT.toString().equals(parameterType)){
								standardPanelData+="{";
								standardPanelData += "\"parameterName\":\""+attr.getName()+"\""+",";
								if(singleTableData!=null && singleTableData.containsKey(attr.getName())){
									String tableData = singleTableData.get(attr.getName());
									standardPanelData += "\"parameterValue\":\""+tableData+"\"";
								}
							}
							standardPanelData+="},";
						}
					}
					if(standardPanelData.contains(",")){
						standardPanelData = standardPanelData.substring(0,standardPanelData.length()-1);
					}
					standardPanelData+="]";
					
					String additionalParameters = "\"additionalParameters\":[";
					for(String parameterName: operation.getDataFilter().keySet()){
						String parameterType = operation.getDataFilter().get(parameterName);
						if(!ReportParamType.FORM_INPUT.toString().equals(parameterType)){
							additionalParameters+="{\"parameterName\":\""+parameterName+"\",";
							additionalParameters+="\"parameterType\":\""+parameterType+"\"},";
						}
					}
					if(additionalParameters.endsWith(",")){
						additionalParameters = additionalParameters.substring(0,additionalParameters.length()-1);
					}
					additionalParameters+="]";
					
					panelData+="\n{"+standardPanelData+",\n"+additionalParameters+"}";
					
					addToDataModel("panelData", panelData);
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
