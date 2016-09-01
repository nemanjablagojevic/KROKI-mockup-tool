package kroki.app.gui.settings;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import kroki.profil.operation.Report;

/**
 * Simple GUI component that contains editable list which enables combo box values management in KROKI Mockup tool
 * @author Nemanja Blagojevic
 */
public class AdditionalReportParameterValuesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected final JTable valuesTable;
	protected final JScrollPane scrollPane;
	protected Report report;
	protected BussinessOperationSettings settingsPanel;
	
	public AdditionalReportParameterValuesPanel(BussinessOperationSettings settingsPanel, Report report) {
		setSize(200, 300);
		
		this.settingsPanel = settingsPanel;
		
		//Initialize table and table model
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Name");
		tableModel.addColumn("Datatype");
		
		
		valuesTable = new JTable(tableModel);
		valuesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel columnModel = valuesTable.getColumnModel();

	    TableColumn column0 = columnModel.getColumn(0);
	    TableColumn column1 = columnModel.getColumn(1);

	    column0.setHeaderValue("Param name");
	    column1.setHeaderValue("Param type");
	    
		setReport(report);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("Boolean");
		comboBox.addItem("String");
		comboBox.addItem("Integer");
		comboBox.addItem("Decimal");
		comboBox.addItem("Date");
		column1.setCellEditor(new DefaultCellEditor(comboBox));
		
		String[] row = {"...","String"};
		tableModel.addRow(row);
		initTableListeners();
		
		scrollPane = new JScrollPane(valuesTable);
		valuesTable.setFillsViewportHeight(true);
		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}
	
	private void initTableListeners() {
		//Click listener
		valuesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					//Click on last row adds new row
					if(row == target.getRowCount()-1) {
						String[] newRow = {"Param name" + ( row+1), "String"};
						DefaultTableModel model = (DefaultTableModel) target.getModel();
						model.insertRow(row, newRow);
						setVisiblePropertyEnum();
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		//DELETE button removes selected row
		valuesTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
		valuesTable.getActionMap().put("delete", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				if(row != target.getRowCount()-1) {
					DefaultTableModel model = (DefaultTableModel) target.getModel();
					model.removeRow(row);
				}
				setVisiblePropertyEnum();
			}
		});
		
		valuesTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				setVisiblePropertyEnum();
			}
			
		});
		
		valuesTable.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					setVisiblePropertyEnum();
				} catch (Exception e2) {
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}
	
	
	public Map<String,String> getValues() {
		Map<String,String> map = new HashMap<String, String>();
		int rows = valuesTable.getRowCount()-1;
		if(rows > 0) {
			for(int i=0; i<rows; i++) {
				String paramName =  valuesTable.getValueAt(i, 0).toString();
				String paramValue =  valuesTable.getValueAt(i, 1).toString();
				if(paramName!=null && paramValue!=null){
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}
	
	public void resetTable() {
		DefaultTableModel model = (DefaultTableModel)valuesTable.getModel();
		model.getDataVector().removeAllElements();
		String[] row = {"...","String"};
		model.addRow(row);
	}
	
	public void setValues() {
		if(report.getAdditionalParameters() != null) {
			DefaultTableModel model = (DefaultTableModel)valuesTable.getModel();
			model.getDataVector().removeAllElements();
			
			for(String paramName: report.getAdditionalParameters().keySet()){
				String paramType = report.getAdditionalParameters().get(paramName);
				String[] newRow = {paramName, paramType};
				model.addRow(newRow);
			}
			String[] row = {"...","String"};
			model.addRow(row);
		}else {
			resetTable();
		}
	}

	public void setVisiblePropertyEnum() {
		try {
			Map<String, String> parameters = getValues();
			if(parameters.size()>0) {
				report.setAdditionalParameters(parameters);
				settingsPanel.updatePreformed();
			}
		} catch (Exception e) {
		}
	}
	
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
		try {
			setValues();
		} catch (Exception e) {
		}
	}
	
	
}
