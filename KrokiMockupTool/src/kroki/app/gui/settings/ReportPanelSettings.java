package kroki.app.gui.settings;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import kroki.commons.camelcase.NamingUtil;
import kroki.intl.Intl;
import kroki.profil.VisibleElement;
import kroki.profil.panel.StandardPanel;
import kroki.profil.panel.mode.OperationMode;
import kroki.profil.panel.mode.ViewMode;
import kroki.profil.property.VisibleProperty;
import kroki.profil.utils.VisibleClassUtil;
import net.miginfocom.swing.MigLayout;

/**
 * Tabbed pane showing standard panel settings
 * @author Vladan Marsenić (vladan.marsenic@gmail.com)
 */
public class ReportPanelSettings extends VisibleClassSettings {

	private static final long serialVersionUID = 1L;
	
	protected NamingUtil cc = new NamingUtil();
	
	protected JTextField classTf;
	protected JTextField tableTf;
	protected JCheckBox labelToCodeCb;
    protected JCheckBox searchCb;
    protected JCheckBox changeModeCb;
    protected JCheckBox dataNavigationCb;
    protected JComboBox defaultViewModeCmb;
    protected JComboBox defaultOperationModeCmb;
    protected JCheckBox confirmDeleteCb;
    protected JCheckBox stayInAddModeCb;
    protected JCheckBox goToLastAddedCb;
    protected JTextArea dataFilterTa;
    protected JTextField sortByTf;
    protected JButton sortByBtn;
    protected JLabel classLb;
    protected JLabel tableLb;
    protected JLabel labelToCodeLb;
    protected JLabel searchLb;
    protected JLabel changeModeLb;
    protected JLabel dataNavigationLb;
    protected JLabel defaultViewModeLb;
    protected JLabel defaultOperatonModeLb;
    protected JLabel confirmDeleteLb;
    protected JLabel stayInAddModeLb;
    protected JLabel goToLastAddedLb;
    protected JLabel dataFilterLb;
    protected JLabel sortByLb;
    protected JScrollPane dataFilterSp;

    public ReportPanelSettings(SettingsCreator settingsCreator) {
        super(settingsCreator);
        initComponents();
        layoutComponents();
        addActions();
    }

    private void initComponents() {

    	classLb = new JLabel();
    	classLb.setText(Intl.getValue("stdPanelSettings.persistentClass"));
    	tableLb = new JLabel();
    	tableLb.setText(Intl.getValue("stfPanelSettings.tableName"));
    	labelToCodeLb = new JLabel();
    	labelToCodeLb.setText(Intl.getValue("stfPanelSettings.labelToCode"));
        searchLb = new JLabel();
        searchLb.setText(Intl.getValue("stdOperations.search"));
        changeModeLb = new JLabel();
        changeModeLb.setText(Intl.getValue("stdOperations.changeMode"));
        dataNavigationLb = new JLabel();
        dataNavigationLb.setText(Intl.getValue("stdOperations.dataNavigation"));
        defaultViewModeLb = new JLabel();
        defaultViewModeLb.setText(Intl.getValue("stdPanelSettings.defaultViewMode"));
        defaultOperatonModeLb = new JLabel();
        defaultOperatonModeLb.setText(Intl.getValue("stdPanelSettings.defaultOperationMode"));
        confirmDeleteLb = new JLabel();
        confirmDeleteLb.setText(Intl.getValue("stdPanelSettings.confirmDelete"));
        stayInAddModeLb = new JLabel();
        stayInAddModeLb.setText(Intl.getValue("stdPanelSettings.stayInAddMode"));
        goToLastAddedLb = new JLabel();
        goToLastAddedLb.setText(Intl.getValue("stdPanelSettings.goToLastAdded"));
        dataFilterLb = new JLabel();
        dataFilterLb.setText(Intl.getValue("stdDataSettings.dataFilter"));
        sortByLb = new JLabel();
        sortByLb.setText(Intl.getValue("stdDataSettings.sortBy"));

        classTf = new JTextField();
        labelToCodeCb = new JCheckBox();
        tableTf = new JTextField();
        searchCb = new JCheckBox();
        changeModeCb = new JCheckBox();
        dataNavigationCb = new JCheckBox();
        defaultViewModeCmb = new JComboBox(ViewMode.values());
        defaultOperationModeCmb = new JComboBox(OperationMode.values());
        confirmDeleteCb = new JCheckBox();
        stayInAddModeCb = new JCheckBox();
        goToLastAddedCb = new JCheckBox();
        dataFilterTa = new JTextArea(5, 30);
        dataFilterTa.setFont(this.getFont());
        dataFilterSp = new JScrollPane(dataFilterTa);
        dataFilterSp.setMinimumSize(dataFilterTa.getPreferredScrollableViewportSize());
        sortByTf = new JTextField(30);
        sortByTf.setEditable(false);
        sortByBtn = new JButton("...");
        sortByBtn.setPreferredSize(new Dimension(30, 20));
        sortByBtn.setMinimumSize(sortByBtn.getPreferredSize());
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
            this.addTab(Intl.getValue("group.INTERMEDIATE"), pane);
        }

        JPanel persistent = null;
        JScrollPane persistentPane;
		if(panelMap.containsKey("group.PERSISTENT")) {
			persistent = panelMap.get("group.PERSISTENT");
		}else {
			persistent = new JPanel();
			persistent.setLayout(new MigLayout("wrap 2,hidemode 3", "[right, shrink][fill, 200]"));
			persistentPane = new JScrollPane(persistent);
			addTab("Persistent", persistentPane);
			panelMap.put("group.PERSISTENT", persistent);
		}
        
        intermediate.add(classLb);
        intermediate.add(classTf);
        intermediate.add(searchLb);
        intermediate.add(searchCb);
        intermediate.add(changeModeLb);
        intermediate.add(changeModeCb);
        intermediate.add(dataNavigationLb);
        intermediate.add(dataNavigationCb);
        intermediate.add(defaultViewModeLb);
        intermediate.add(defaultViewModeCmb);
        intermediate.add(defaultOperatonModeLb);
        intermediate.add(defaultOperationModeCmb);
        intermediate.add(confirmDeleteLb);
        intermediate.add(confirmDeleteCb);
        intermediate.add(stayInAddModeLb);
        intermediate.add(stayInAddModeCb);
        intermediate.add(goToLastAddedLb);
        intermediate.add(goToLastAddedCb);
        intermediate.add(dataFilterLb);
        intermediate.add(dataFilterSp);
        intermediate.add(sortByLb);
        intermediate.add(sortByTf, "split 2, grow");
        intermediate.add(sortByBtn, "shrink");
        
        persistent.add(labelToCodeLb);
        persistent.add(labelToCodeCb);
        persistent.add(tableLb);
        persistent.add(tableTf);

    }

    @Override
    public void updateComponents() {
        super.updateComponents();
        StandardPanel visibleClass = (StandardPanel) visibleElement;
        if(visibleClass.getPersistentClass().name() == null) {
        	classTf.setText(cc.toCamelCase(visibleClass.getLabel(), false));
        	visibleClass.getPersistentClass().setName(classTf.getText());
        }else {
        	classTf.setText(visibleClass.getPersistentClass().name());
        }
        searchCb.setSelected(visibleClass.isSearch());
        changeModeCb.setSelected(visibleClass.isChangeMode());
        dataNavigationCb.setSelected(visibleClass.isDataNavigation());
        defaultViewModeCmb.setSelectedItem(visibleClass.getStdPanelSettings().getDefaultViewMode());
        defaultOperationModeCmb.setSelectedItem(visibleClass.getStdPanelSettings().getDefaultOperationMode());
        confirmDeleteCb.setSelected(visibleClass.getStdPanelSettings().isConfirmDelete());
        stayInAddModeCb.setSelected(visibleClass.getStdPanelSettings().isStayInAddMode());
        goToLastAddedCb.setSelected(visibleClass.getStdPanelSettings().isGoToLastAdded());
        dataFilterTa.setText(visibleClass.getStdDataSettings().getDataFilter());
        String sortByValue = "";
        if (visibleClass.getStdDataSettings().getSortBy() != null) {
            sortByValue = visibleClass.getStdDataSettings().getSortBy().toString();
        }
        sortByTf.setText(sortByValue);
        tableTf.setText(visibleClass.getPersistentClass().getTableName());
        labelToCodeCb.setSelected(visibleClass.getPersistentClass().isLabelToCode());
        
        if (visibleClass.getPersistentClass().isLabelToCode()){
        	tableTf.setEditable(false);
        	labelToCodeCb.setSelected(true);
        }
        else{
        	tableTf.setEditable(true);
        	labelToCodeCb.setSelected(false);
        }
    }

    private void addActions() {
    	
    	this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				updateComponents();
			}
		});
    	
    	tableTf.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                contentChanged(e);
            }

            public void removeUpdate(DocumentEvent e) {
                contentChanged(e);
            }

            public void changedUpdate(DocumentEvent e) {
                //nothing
            }

            private void contentChanged(DocumentEvent e) {
                Document doc = e.getDocument();
                String text = "";
                try {
                    text = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                }
                StandardPanel visibleClass = (StandardPanel) visibleElement;
				visibleClass.getPersistentClass().setTableName(tableTf.getText().trim());
				updatePreformed();
            }
        });
			
    	
    	classTf.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				StandardPanel visibleClass = (StandardPanel) visibleElement;
				visibleClass.getPersistentClass().setName(classTf.getText().trim());
				updatePreformed();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
    	
    	labelToCodeCb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				  JCheckBox checkBox = (JCheckBox) e.getSource();
	               boolean value = checkBox.isSelected();
	               StandardPanel visibleClass = (StandardPanel) visibleElement;
	               visibleClass.getPersistentClass().setLabelToCode(value);
	               
	               
	           	if(value) {
				    tableTf.setEditable(false);
				    visibleClass.getPersistentClass().setTableName(cc.toDatabaseFormat(visibleClass.project().getLabel(), visibleClass.getLabel()));
					tableTf.setText(visibleClass.getPersistentClass().getTableName());
				}else {
					tableTf.setEditable(true);
				}
				
			}
		});
    	
        searchCb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                boolean value = checkBox.isSelected();
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.setSearch(value);
                updatePreformed();
            }
        });

        changeModeCb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                boolean value = checkBox.isSelected();
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.setChangeMode(value);
                updatePreformed();
            }
        });

        dataNavigationCb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                boolean value = checkBox.isSelected();
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.setDataNavigation(value);
                updatePreformed();
            }
        });

        defaultViewModeCmb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                Object value = comboBox.getSelectedItem();
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.getStdPanelSettings().setDefaultViewMode((ViewMode) value);
                updatePreformed();
            }
        });

        defaultOperationModeCmb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                Object value = comboBox.getSelectedItem();
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.getStdPanelSettings().setDefaultOperationMode((OperationMode) value);
                updatePreformed();
            }
        });

        confirmDeleteCb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                boolean value = checkBox.isSelected();
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.getStdPanelSettings().setConfirmDelete(value);
                updatePreformed();
            }
        });


        stayInAddModeCb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                boolean value = checkBox.isSelected();
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.getStdPanelSettings().setStayInAddMode(value);
                updatePreformed();
            }
        });

        goToLastAddedCb.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                boolean value = checkBox.isSelected();
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.getStdPanelSettings().setGoToLastAdded(value);
                updatePreformed();
            }
        });

        dataFilterTa.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                contentChanged(e);
            }

            public void removeUpdate(DocumentEvent e) {
                contentChanged(e);
            }

            public void changedUpdate(DocumentEvent e) {
                //nothing
            }

            private void contentChanged(DocumentEvent e) {
                Document doc = e.getDocument();
                String text = "";
                try {
                    text = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                }
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                visibleClass.getStdDataSettings().setDataFilter(text);
                updatePreformed();
            }
        });

        sortByBtn.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                //stdDataSettings.choose.sortBy
                StandardPanel visibleClass = (StandardPanel) visibleElement;
                List<VisibleProperty> list = VisibleClassUtil.containedProperties(visibleClass);
                VisibleProperty sortBy = (VisibleProperty) ListDialog.showDialog(list.toArray(), Intl.getValue("stdDataSettings.choose.sortBy"));
                if (sortBy != null) {
                    visibleClass.getStdDataSettings().setSortBy(sortBy);
                    sortByTf.setText(sortBy.toString());
                    updatePreformed();
                }
            }
        });

    }

    @Override
    public void updateSettings(VisibleElement visibleElement) {
        super.updateSettings(visibleElement);
    }

    @Override
    public void updatePreformed() {
        super.updatePreformed();
    }
}
