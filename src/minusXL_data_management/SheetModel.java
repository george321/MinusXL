package minusXL_data_management;

import javax.swing.table.*;

public class SheetModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private SpreadSheet guiTable;
	private int guiRow;
	private int guiColumn;
	protected Cell[][] cells;

	SheetModel(Cell[][] cells, SpreadSheet table) {
		guiTable = table;
		guiRow = cells.length;
		guiColumn = cells[0].length;
		this.cells = cells;
	}

	public String getFormularBarText(int r, int c) {
		String s = "Click on a cell";
		if ((r + 1) * (c + 1) > 0) {
			if (cells[r][c].getFormula().length() < 1) {
				s = cells[r][c].getValue();
			} else {
				s = "=" + cells[r][c].getFormula();
			}
			guiTable.getBook().getContainer().getFormulaBar().setEditable(true);
		} else {
			guiTable.getBook().getContainer().getFormulaBar().setEditable(false);
		}
		return s;
	}

	public int getRowCount() {
		return guiRow;
	}

	public int getColumnCount() {
		return guiColumn;
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public Object getValueAt(int row, int column) {
		String r = cells[row][column].getValue();
		cells[row][column].setEditing(false);
		return r;

	}

	public void setValueAt(Object value, int row, int column) {
		String input = (String) value;
		if (input.equals("")) {
			
			cells[row][column].setHadFunc(false);

			return;
		}
		guiTable.getBook().setSaved(false);
		if (value != null) {
			cells[row][column].setValueObject((String) value);
			if (cells[row][column].getCellGives().isEmpty() == false) {
				for (Cell temp : cells[row][column].getCellGives()) {
					temp.reCompute();
				}
			}
		} else {
			cells[row][column].setValueObject("");
			cells[row][column].setFormula("");
		}
		guiTable.repaint();
	}

	public Cell[][] getCells() {
		return cells;
	}

}
