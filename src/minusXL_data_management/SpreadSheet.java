package minusXL_data_management;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

public class SpreadSheet extends JTable {

	private static final long serialVersionUID = 1L;
	private JScrollPane sheet;
	private Cell array[][];
	private SheetModel model;
	private String spreadSheetName;
	private WorkBook workbook;
	private String brush;

	public SpreadSheet(WorkBook workbook, int numRow, int numColumn, String name) {
		sheet = new JScrollPane(this);
		this.workbook = workbook;
		this.spreadSheetName = name;
		brush = "";
		this.setDefaultRenderer(Object.class, new myCellModel(workbook.getContainer().getFormulaBar()));
		setCellSelectionEnabled(true);
		Cell foo[][];
		foo = new Cell[numRow][numColumn];
		for (int ii = 0; ii < numRow; ii++) {
			for (int jj = 0; jj < numColumn; jj++) {
				foo[ii][jj] = new Cell(this, ii, jj, "", "");
			}
		}

		model = new SheetModel(foo, this);
		setModel(model);
		setFont(new Font("Times", Font.PLAIN, 16));

		setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionListener selectionChanged = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				int r, c;
				r = getSelectedRow();
				c = getSelectedColumn();
				 if (getBrush().length() > 0) {
				 getSheetModel().cells[r][c].reCompute();
				 }
				// vgazei ta A B B2 klp
				String add = (c / 26 < 1 ? "" : Character.valueOf((char) (c / 26 + 64)).toString())+ Character.valueOf((char) (c % 26 + 65)).toString() + (r + 1);
				workbook.getContainer().getFormulaBar().setText(getSheetModel().getFormularBarText(r, c));
				workbook.getContainer().getBarCaption().setText("Formula at " + add + " is: ");

				TableCellEditor tce = getCellEditor();

				if (tce != null) {

					JTextField d = (JTextField) (((DefaultCellEditor) tce).getComponent());
					if (d.getText().matches("(=)|(=[A-Za-z]+\\([:,]$)|(=.*[\\+\\-\\/\\(]$)")) {
						d.setText(d.getText() + add);
					}
				}
				repaint();

			}
		};
		getColumnModel().getSelectionModel().addListSelectionListener(selectionChanged);
		getSelectionModel().addListSelectionListener(selectionChanged);
		rowHeight = 25;

		JPanel pnl = new JPanel((LayoutManager) null);
		Dimension dim = new Dimension(50, 200);
		pnl.setPreferredSize(dim);
		((JTextField) ((DefaultCellEditor) getDefaultEditor(Object.class)).getComponent()).getDocument()
				.addDocumentListener(new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						try {
							String t = e.getDocument().getText(0, e.getDocument().getLength());
							workbook.getContainer().getFormulaBar().setText(t);
						} catch (BadLocationException ex) {
							Logger.getLogger(SpreadSheet.class.getName()).log(Level.SEVERE, null, ex);
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						try {
							String t = e.getDocument().getText(0, e.getDocument().getLength());
							workbook.getContainer().getFormulaBar().setText(t);
						} catch (BadLocationException ex) {
							Logger.getLogger(SpreadSheet.class.getName()).log(Level.SEVERE, null, ex);
						}
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						try {
							String t = e.getDocument().getText(0, e.getDocument().getLength());
							workbook.getContainer().getFormulaBar().setText(t);
						} catch (BadLocationException ex) {
							Logger.getLogger(SpreadSheet.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				});

		// Adding the row headers
		dim.height = rowHeight;
		for (int ii = 0; ii < numRow; ii++) {
			JLabel lbl = new JLabel(Integer.toString(ii + 1), SwingConstants.CENTER);

			lbl.setFont(this.getTableHeader().getFont());
			lbl.setBorder((Border) UIManager.getDefaults().get("TableHeader.cellBorder"));
			lbl.setBounds(0, ii * dim.height, dim.width, dim.height);
			pnl.add(lbl);
		}

		JViewport vp = new JViewport();
		dim.height = rowHeight * numRow;
		vp.setViewSize(dim);
		vp.setView(pnl);

		sheet.setRowHeader(vp);

		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Dimension dimScpViewport = getPreferredScrollableViewportSize();
		if (numRow > 30) {
			dimScpViewport.height = 30 * rowHeight;
		} else {
			dimScpViewport.height = numRow * rowHeight;
		}
		if (numColumn > 15) {
			dimScpViewport.width = 15 * getColumnModel().getTotalColumnWidth() / numColumn;
		} else {
			dimScpViewport.width = getColumnModel().getTotalColumnWidth();
		}
		setPreferredScrollableViewportSize(dimScpViewport);
		resizeAndRepaint();

	}

	@Override
	public void editingStopped(ChangeEvent e) {
		int r, c;
		r = getSelectedRow();
		c = getSelectedColumn();

		TableCellEditor tce = this.getCellEditor();

		if (tce != null && tce.getCellEditorValue().toString().equals("")) {
			getSheetModel().setValueAt("", r, c);
			model.cells[r][c].reCompute();
			model.cells[r][c].Compute(170);
			model.cells[r][c].reCompute();
			model.cells[r][c].setValueObject("");
			tce.cancelCellEditing();

			tce.stopCellEditing(); // should accept partial edit
		} else {
			if (tce.getCellEditorValue().toString().matches("(=)|(=[A-Za-z]+\\([:,]$)|(=.*[\\+\\-\\/\\(]$)")) {
				return;
			}
			super.editingStopped(e); // To change body of generated methods,
										// choose Tools | Templates.
		}

		getBook().getContainer().getFormulaBar().setText(getSheetModel().getFormularBarText(r, c));

	}

	public void setFromFormulaBar() {
		int r, c;
		r = getSelectedRow();
		c = getSelectedColumn();
		String s = getBook().getContainer().getFormulaBar().getText();
		if ((r + 1) * (c + 1) > 0) {
			if (s.length() < 1) {
				getSheetModel().setValueAt("", r, c);
			} else if (s.charAt(0) == '=') {
				getSheetModel().cells[r][c].setFormula(s.replaceFirst("=", ""));

			} else {
				getSheetModel().cells[r][c].setValueObject(s);
			}

			this.editCellAt(r, c);

			JTextComponent jtc = (JTextComponent) this.getEditorComponent();
			this.getEditorComponent().requestFocus();
			jtc.selectAll();

		}
	}

	@Override
	protected void processKeyEvent(KeyEvent e) {
		if (e.isShiftDown()) {

			int r;
			int c;
			r = getSelectedRow();
			c = getSelectedColumn();
			if (getBrush().length() < 1) {
				this.setBrush(this.getSheetModel().cells[r][c].getRformula());
			} else {
				//this.getSheetModel().cells[r][c].Compute("=" + getBrush(), workbook);
				this.getSheetModel().cells[r][c].reCompute();
				this.getSheetModel().cells[r][c].setValueObject(getBrush());
			}
		} else {
			setBrush("");
		}
		super.processKeyEvent(e); // To change body of generated methods, choose
									// Tools | Templates.
	}

	@Override
	public boolean editCellAt(int row, int column, EventObject e) {
		getSheetModel().cells[row][column].setEditing(true);
		boolean r = super.editCellAt(row, column, e);
		Component editor = getEditorComponent();

		if (editor == null || e == null) {
			return r;
		} else if (e.toString().contains("KEY_PRESSED")) {
			((JTextComponent) editor).selectAll();

		}
		return r;

	}

	public String getSpreadSheetName() {
		return spreadSheetName;
	}

	public Cell[][] getArray() {
		return array;
	}

	public WorkBook getBook() {
		return getWorkbook();
	}
	public Cell getSelectedCell(){
		int r = getSelectedRow();
		int c = getSelectedColumn();
		System.out.println("Row:"+r+" Cell:"+c);
		return array[r][c];
	}

	public JScrollPane getSheet() {
		return sheet;
	}

	public SheetModel getSheetModel() {
		return model;
	}

	public WorkBook getWorkbook() {
		return workbook;
	}

	/**
	 * @return the brush
	 */
	public String getBrush() {
		return brush;
	}

	/**
	 * @param brush
	 *            the brush to set
	 */
	public void setBrush(String brush) {
		this.brush = brush;
	}
	public void setName(String name) {
		this.spreadSheetName = name;
	}
	public void setWorkbook(WorkBook workbook) {
		this.workbook = workbook;
	}

}

class myCellModel extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	JTextField formulaBar;

	public myCellModel(JTextField formulaBar) {
		this.formulaBar = formulaBar;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		String add = (column / 26 < 1 ? "" : Character.valueOf((char) (column / 26 + 64)).toString())
				+ Character.valueOf((char) (column % 26 + 65)).toString() + (row + 1);
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		ArrayList<String> cells = new ArrayList<String>();
		cells.addAll(Arrays.asList(formulaBar.getText().toUpperCase().split("[=\\+\\-\\/\\(\\)\\,\\:]")));
		if (value.toString().equals("#ERROR")) {
			label.setForeground(Color.RED);
		} else {
			label.setForeground(Color.BLACK);
		}
		if (!isSelected && cells.contains(add)) {
			Color randomColor = Color.BLUE;
			label.setBorder(BorderFactory.createLineBorder(randomColor));

		} else if (isSelected) {
			// System.out.println(""+label.getBackground());

		} else {
		}

		return label;

	}
}
