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
				
				if(dataId!=null){
					String query = "FROM " + panel.getEntityBean().getEntityClass().getName()+" bean WHERE bean.id="+dataId;
					EntityManager em = PersisenceHelper.createEntityManager();

					em.getTransaction().begin();
					Query q = em.createQuery(query);
					Object result = q.getSingleResult();
					
					prepareEditMap(panel.getEntityBean(), result);
				}
				
				Operation operation = panel.getStandardOperations().findByName(operationId);
				if(operation!=null && OperationType.VIEWREPORT.equals(operation.getType())){
					
					String panelData = new String();
					String standardPanelData = "\"standardPanelData\":[";
					
					for(AbstractAttribute attr: panel.getEntityBean().getAttributes()){
						String formattedAttr = "";
						if(attr instanceof ColumnAttribute){
							if(!"id".equals(attr.getName())){
								formattedAttr = attr.getName().substring(3).toUpperCase();
							}
						}else if(attr instanceof JoinColumnAttribute){
							formattedAttr = attr.getName().substring(5).toUpperCase();
						}
						if(operation.getDataFilter()!=null && operation.getDataFilter().containsKey(formattedAttr)){
							String parameterType = operation.getDataFilter().get(formattedAttr);
							if(ReportParamType.FORM_INPUT.toString().equals(parameterType)){
								standardPanelData+="{";
								standardPanelData += "\"parameterName\":\""+attr.getName()+"\"";
								
								if(attr instanceof ColumnAttribute){
									if(this.editMap!=null && this.editMap.containsKey(attr.getName())){
										String tableData = this.editMap.get(attr.getName());
//										if(Boolean.class.getCanonicalName().equals(((ColumnAttribute) attr).getDataType())){
//											tableData="Yes".equals(tableData)?Boolean.TRUE.toString():Boolean.FALSE.toString();
//										}
										standardPanelData += ", \"parameterValue\":\""+tableData+"\"";
									}
								}else if(attr instanceof JoinColumnAttribute){
									if(this.zoomEditMap!=null && this.zoomEditMap.containsKey(attr.getName())){
										String tableData="{";
										LinkedHashMap<String, String> zooms = this.zoomEditMap.get(attr.getName());
										for(String key: zooms.keySet()){
											tableData+="\""+key+"\":\""+zooms.get(key)+"\",";
										}
										if(tableData.contains(",")){
											tableData = tableData.substring(0,tableData.length()-1);
										}
										tableData+="}";
										standardPanelData += ", \"parameterValue\":"+tableData;
									}
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
					
					addToDataModel("reportName", "\""+operation.getReportName()+"\"");
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
