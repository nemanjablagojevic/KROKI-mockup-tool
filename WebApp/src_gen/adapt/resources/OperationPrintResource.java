package adapt.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.OutputRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import adapt.core.AppCache;
import adapt.enumerations.OpenedAs;
import adapt.enumerations.PanelType;
import adapt.model.ejb.AbstractAttribute;
import adapt.model.ejb.ColumnAttribute;
import adapt.model.ejb.EntityBean;
import adapt.model.ejb.JoinColumnAttribute;
import adapt.model.panel.AdaptStandardPanel;
import adapt.util.ejb.PersisenceHelper;
import adapt.util.html.TableModel;
import adapt.util.pdf.AdaptPDFEvent;
import adapt.util.repository_utils.RepositoryPathsUtil;
import adapt.util.xml_readers.PanelReader;
import aj.org.objectweb.asm.Attribute;

public class OperationPrintResource extends BaseResource {

	String pdfLocation = RepositoryPathsUtil.getRepositoryRootPath() + File.separator + "static_files";
	
	public OperationPrintResource(Context context, Request request, Response response) {
		super(context, request, response);
//		getVariants().add(new Variant(MediaType.APPLICATION_PDF));
	}

	@Override
	public void handleGet() {
		String reportName = (String)getRequest().getAttributes().get("reportName");
		String standardReportData = (String)getRequest().getAttributes().get("standardReportData");
		String additionalReportData = (String)getRequest().getAttributes().get("additionalReportData");
		Map<String, String> standardReportDataValues = new HashMap<String, String>();
		Map<String, String[]> additionalReportDataValues = new HashMap<String, String[]>();
		
		String[] srdParams = standardReportData.split(";");
		for(int i=0; i<srdParams.length; i++){
			if(srdParams[i].contains(",")){
				String[] srdParam = srdParams[i].split(",");
				if(srdParam.length==2){
					String paramName = srdParam[0];
					String value = srdParam[1];
					standardReportDataValues.put(paramName, value);
				}
			}
		}
		
		String[] addParams = additionalReportData.split(";");
		for(int i=0; i<addParams.length; i++){
			if(addParams[i].contains(",")){
				String[] addParam = addParams[i].split(",");
				if(addParam.length==3){
					String paramName = addParam[0];
					String value = addParam[1];
					String dataType = addParam[2];
					String[] valuePair = {value, dataType};
					additionalReportDataValues.put(paramName, valuePair);
				}
			}
		}
		
		
		buildReport(reportName, standardReportDataValues, additionalReportDataValues);
		
		addToDataModel("css", "messageOk");
		addToDataModel("message", "Cao cao.");
		super.handleGet();
	}

	private Document buildReport(String reportName, Map<String, String> standardReportDataValues, Map<String, String[]> additionalReportDataValues) {
		try {
			Font tileFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			Font textFont = new Font(Font.FontFamily.HELVETICA, 14);
			
			Document pdfDocument = new Document();
			PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream(pdfLocation + File.separator + reportName + ".pdf"));
			AdaptPDFEvent event = new AdaptPDFEvent();
			writer.setPageEvent(event);
			pdfDocument.open();

			AdaptStandardPanel panel = (AdaptStandardPanel) PanelReader.loadPanel(reportName, PanelType.STANDARDPANEL, null, OpenedAs.DEFAULT);
			
			
			Paragraph preface = new Paragraph();
			addEmptyLine(preface, 3);
			Paragraph title = new Paragraph(panel.getLabel(), tileFont);
			title.setAlignment(Element.ALIGN_CENTER);
			preface.add(title);
			
			Paragraph parameterParagraph = new Paragraph();
			for(String key: additionalReportDataValues.keySet()){
				String value = additionalReportDataValues.get(key)[0];
				value = URLDecoder.decode(value, "UTF-8");
				parameterParagraph.add(URLDecoder.decode(key, "UTF-8")+": "+value+"\n");
			}
			
			Paragraph dataParagraph = new Paragraph();
			addEmptyLine(dataParagraph, 3);
			
			buildPDFTable(panel, standardReportDataValues, additionalReportDataValues, dataParagraph);
			
			preface.add(parameterParagraph);
			preface.add(dataParagraph);

			pdfDocument.add(preface);
			pdfDocument.close();
			return pdfDocument;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void buildPDFTable(AdaptStandardPanel panel, Map<String, String> standardReportDataValues, Map<String, String[]> additionalReportDataValues, Paragraph tableParagraph) throws UnsupportedEncodingException {
		
		EntityBean bean = panel.getEntityBean();
		PdfPTable table = new PdfPTable(bean.getAttributes().size());
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 12);
		Font cellFont = new Font(Font.FontFamily.HELVETICA, 12);
		headerFont.setColor(BaseColor.WHITE);
		
		String query = "FROM " + panel.getEntityBean().getEntityClass().getName()+" bean WHERE 1=1";
		for (AbstractAttribute attribute : bean.getAttributes()) {
			PdfPCell c1 = new PdfPCell(new Phrase(attribute.getLabel(), headerFont));
		    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		    c1.setBackgroundColor(new BaseColor(52, 103, 137));
		    table.addCell(c1);
		    if(standardReportDataValues.containsKey(attribute.getName())){
		    	if(attribute instanceof ColumnAttribute){
		    		String attributeValue = standardReportDataValues.get(attribute.getName());
		    		ColumnAttribute ca = (ColumnAttribute) attribute;
		    		if(String.class.getCanonicalName().equals(ca.getDataType())){
		    			attributeValue = "'"+URLDecoder.decode(attributeValue, "UTF-8")+"'";
		    		}
		    		if(Boolean.class.getCanonicalName().equals(ca.getDataType())){
		    			attributeValue = "'"+attributeValue.toUpperCase()+"'";
		    		}
		    		
		    		if(!attributeValue.isEmpty()){
		    			query+=" AND bean."+attribute.getFieldName()+"="+attributeValue;
		    		}
		    	}else if(attribute instanceof JoinColumnAttribute){
		    		String attributeValue = standardReportDataValues.get(attribute.getName());
		    		if(!attributeValue.isEmpty() && !attributeValue.equalsIgnoreCase("ID")){
						query+=" AND bean."+ attribute.getFieldName()+".id = "+attributeValue;
		    		}
		    	}
		    }
		}
		AppCache.displayTextOnMainFrame("QUERY: " + query, 0);
		
		EntityManager em = PersisenceHelper.createEntityManager();

		em.getTransaction().begin();
		Query q = em.createQuery(query);
		List<Object> results = q.getResultList();
		
		AppCache.displayTextOnMainFrame("QUERY RESULT SIZE: " + results.size(), 0);
		
		TableModel model = new TableModel(panel.getEntityBean());
		
		em.getTransaction().commit();
		em.close();
		
		ArrayList<LinkedHashMap<String, String>> tableRows = model.getModel(results);

		if(tableRows.size() > 0) {
			for (LinkedHashMap<String, String> rowMap : tableRows) {
				for(int i=0; i<bean.getAttributes().size(); i++) {
					PdfPCell cell = new PdfPCell(new Phrase(rowMap.get(bean.getAttributes().get(i).getName())));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
			}
		}else {
			Paragraph message = new Paragraph("No data");
			message.setAlignment(Element.ALIGN_CENTER);
			tableParagraph.add(message);
		}
		
		table.setHeaderRows(1);
		tableParagraph.add(table);
	}
	
	@Override
	public Representation represent(Variant variant) throws ResourceException {
		addToDataModel("message", "OK");
		addToDataModel("css", "messageOk");
		return getHTMLTemplateRepresentation("popupTemplate.html", dataModel);
	}
	
	private static void addEmptyLine(Paragraph paragraph, int number) {
	    for (int i = 0; i < number; i++) {
	      paragraph.add(new Paragraph(" "));
	    }
	  }
}