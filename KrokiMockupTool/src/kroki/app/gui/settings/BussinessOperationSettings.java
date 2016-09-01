package kroki.app.gui.settings;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import kroki.intl.Intl;
import kroki.profil.VisibleElement;
import kroki.profil.operation.BussinessOperation;
import kroki.profil.operation.Report;
import net.miginfocom.swing.MigLayout;

/**
 * Tabbed pane with business operation settings
 * @author Vladan Marsenić (vladan.marsenic@gmail.com)
 */
public class BussinessOperationSettings extends VisibleElementSettings {

	private static final long serialVersionUID = 1L;
	
	protected JLabel hasParamsFormLb;
	protected JLabel filteredByKeyLb;
	protected JLabel persistentOperationLb;
	protected JLabel standardParameterValuesLb;
	protected JLabel additionalParameterValuesLb;
	protected JCheckBox hasParamsFormCb;
	protected JCheckBox filteredByKeyCb;
	protected JTextField persistentOperationTf;
	protected JButton persistentOperationBtn;
	protected StandardReportParameterValuesPanel standardParameterValuesPanel;
	protected AdditionalReportParameterValuesPanel additionalParameterValuesPanel;

    public BussinessOperationSettings(SettingsCreator settingsCreator) {
        super(settingsCreator);
        initComponents();
        layoutComponents();
        addActions();
    }

    private void initComponents() {
        hasParamsFormLb = new JLabel(Intl.getValue("bussinessOperation.hasParametersForm"));
        filteredByKeyLb = new JLabel(Intl.getValue("bussinessOperation.filteredByKey"));
        persistentOperationLb = new JLabel(Intl.getValue("bussinessOperation.persistentOperation"));
        hasParamsFormCb = new JCheckBox();
        filteredByKeyCb = new JCheckBox();
        persistentOperationTf = new JTextField();
        persistentOperationTf.setEditable(false);
        persistentOperationBtn = new JButton("...");
        persistentOperationBtn.setPreferredSize(new Dimension(30, 20));
        persistentOperationBtn.setMinimumSize(persistentOperationBtn.getPreferredSize());
        standardParameterValuesLb = new JLabel("Standard parameters");
        standardParameterValuesPanel = new StandardReportParameterValuesPanel(this, (Report) visibleElement);
        standardParameterValuesPanel.setReport((Report)visibleElement);
        standardParameterValuesPanel.setFont(this.getFont());
        additionalParameterValuesLb = new JLabel("Additional parameters");
        additionalParameterValuesPanel = new AdditionalReportParameterValuesPanel(this, (Report) visibleElement);
        additionalParameterValuesPanel.setReport((Report)visibleElement);
        additionalParameterValuesPanel.setFont(this.getFont());
    }

    private void layoutComponents() {
        JPanel intermediate = null;
        JScrollPane pane;
        if (panelMap.containsKey("group.INTERMEDIATE")) {
            intermediate = panelMap.get("group.INTERMEDIATE");
        } else {
            intermediate = new JPanel();
            intermediate.setLayout(new MigLayout("wrap 2,hidemode 3", "[right, shrink][fill, 200]"));
            panelMap.put("group.INTERMEDIATE", intermediate);
            pane = new JScrollPane(intermediate);
            addTab(Intl.getValue("group.INTERMEDIATE"), pane);
        }
        intermediate.add(hasParamsFormLb);
        intermediate.add(hasParamsFormCb);
        intermediate.add(filteredByKeyLb);
        intermediate.add(filteredByKeyCb);
        intermediate.add(persistentOperationLb);
        intermediate.add(persistentOperationTf, "split 2, grow");
        intermediate.add(persistentOperationBtn, "shrink");
        if(standardParameterValuesPanel!=null){
        	intermediate.add(standardParameterValuesLb);
        	intermediate.add(standardParameterValuesPanel, "height ::100");
        }
        if(additionalParameterValuesPanel!=null){
        	intermediate.add(additionalParameterValuesLb);
        	intermediate.add(additionalParameterValuesPanel, "height ::100");
        }
        
    }

    private void addActions() {
    	if(additionalParameterValuesPanel!=null){
	    	additionalParameterValuesPanel.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent arg0) {
					Report report = (Report) visibleElement;
					Set<String> standardParameters = standardParameterValuesPanel.getValues();
					report.setStandardParameters(standardParameters);
					Map<String,String> additionalValues = additionalParameterValuesPanel.getValues();
					report.setAdditionalParameters(additionalValues);
					updatePreformed();
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
				}
			});
    	}
    	
        hasParamsFormCb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                boolean value = checkBox.isSelected();
                BussinessOperation bussinessOperation = (BussinessOperation) visibleElement;
                bussinessOperation.setHasParametersForm(value);
                updatePreformed();
            }
        });
        filteredByKeyCb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                boolean value = checkBox.isSelected();
                BussinessOperation bussinessOperation = (BussinessOperation) visibleElement;
                bussinessOperation.setFilteredByKey(value);
                updatePreformed();
            }
        });

        persistentOperationBtn.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Not supported yet.");
            }
        });

    }

    @Override
    public void updateComponents() {
        super.updateComponents();
        BussinessOperation bussinessOperation = (BussinessOperation) visibleElement;
        hasParamsFormCb.setSelected(bussinessOperation.isHasParametersForm());
        filteredByKeyCb.setSelected(bussinessOperation.isFilteredByKey());
        String persistentOperationValue = "";
        if (bussinessOperation.getPersistentOperation() != null) {
            persistentOperationValue = bussinessOperation.getPersistentOperation().toString();
        }
        if(bussinessOperation instanceof Report){
        	Report report = (Report) bussinessOperation;
        	if(report.getStandardParameters() != null) {
            	standardParameterValuesPanel.setReport(report);
            }
        	if(report.getAdditionalParameters() != null) {
            	additionalParameterValuesPanel.setReport(report);
            }
        }
        
        persistentOperationTf.setText(persistentOperationValue);
    }

    @Override
    public void updateSettings(VisibleElement visibleElement) {
        super.updateSettings(visibleElement);
        if(visibleElement instanceof Report) {
        	Report report = (Report) visibleElement;
        	standardParameterValuesPanel.setReport(report);
        	standardParameterValuesLb.setVisible(true);
        	standardParameterValuesPanel.setVisible(true);
            additionalParameterValuesPanel.setReport(report);
        	additionalParameterValuesLb.setVisible(true);
        	additionalParameterValuesPanel.setVisible(true);
        }else{
        	standardParameterValuesLb.setVisible(false);
        	standardParameterValuesPanel.setVisible(false);
        	additionalParameterValuesLb.setVisible(false);
        	additionalParameterValuesPanel.setVisible(false);
        }
    }

    @Override
    public void updatePreformed() {
        super.updatePreformed();
    }
}
