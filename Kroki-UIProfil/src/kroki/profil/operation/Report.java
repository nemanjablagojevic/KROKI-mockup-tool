package kroki.profil.operation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kroki.profil.ComponentType;

/**
 * Stereotype Reports represents an operation which shows reports.
 * Reports should created using one of the available report building tools (iReport). 
 * @author Vladan Marsenić (vladan.marsenic@gmail.com)
 */
public class Report extends BussinessOperation {

	private static final long serialVersionUID = 1L;
	
	/**Path to the report which is being shown*/
    private String reportName;
    /**OCL expression which defines the data shown within the report*/
    private String dataFilter;
    /**A way of sorting report data*/
    private String sortBy;
    private Set<String> standardParameters = new HashSet<String>();
    private Map<String, String> additionalParameters = new HashMap<String,String>();
    
    public Report(String label) {
        super(label, true, ComponentType.BUTTON);
    }

    public Report(String label, boolean visible, ComponentType componentType) {
        super(label, visible, componentType);
    }

    public Report() {
        super();
    }

    /**********************/
    /*GETTERS AND SETTERS*/
    /*********************/
    public String getDataFilter() {
        return dataFilter;
    }

    public void setDataFilter(String dataFilter) {
        this.dataFilter = dataFilter;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

	public Set<String> getStandardParameters() {
		return standardParameters;
	}

	public void setStandardParameters(Set<String> standardParameters) {
		this.standardParameters = standardParameters;
	}

	public Map<String, String> getAdditionalParameters() {
		return additionalParameters;
	}

	public void setAdditionalParameters(Map<String, String> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}

}
