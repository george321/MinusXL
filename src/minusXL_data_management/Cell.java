package minusXL_data_management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {
	private String formula, suna;
	private String rformula;
	private ArrayList<Cell> cellTake = new ArrayList<>(), cellGive = new ArrayList<>();
	private String inputCells;

	private ArrayList<Cell> references, listeners;
	private int row;
	private int column;
	private Object value;
	private boolean editing;
	private Cell[][] cells;
	private SpreadSheet sheet;
	private boolean rawvalued, hadFunc = false;

	Cell(SpreadSheet sheet, int r, int c) {
		row = r;
		column = c;
		value = "";
		formula = "";
		rformula = "";
		listeners = new ArrayList<>();
		editing = false;
		rawvalued = false;
		this.sheet = sheet;
	}

	public Cell(SpreadSheet sheet, int r, int c, String value, String formula) {
		this(sheet, r, c);
		this.value = value;
		this.formula = formula;
	}

	public String getValue() {

		if (getFormula().length() < 1 || !isEditing()) {
			String strV = getValueObject().toString();
			if (strV.matches("[-+]?([0-9]+\\.[0])")) {
				strV = strV.split("\\.")[0]; // removes unwanted zeros after
												// decimal pont.
			}
			if (strV.equals(formula.toUpperCase())) {
				strV = formula;
			}
			return strV;
		} else {
			setEditing(false);
			return "=" + getFormula();
		}
	}

	public void Compute(int n) {
		System.out.println("Empty Compute: " + n);
	}

	public void reCompute() {
		if (hadFunc) {

			Compute(inputCells, suna);
		}
	}

	public void Compute(String inputCells, String func) {

		this.inputCells = inputCells;
		suna = func;
		String numbers = inputCells.replaceAll("[^-?0-9]+", " ");
		List<String> numsPos = Arrays.asList(numbers.trim().split(" "));
		String characters = inputCells.replaceAll("\\d+", "");
		List<String> charsPos = Arrays.asList(characters.trim().toUpperCase().split("\\s*(=>|,|\\s)\\s*"));
		int[] row1 = new int[numsPos.size()];
		int[] column1 = new int[numsPos.size()];

		for (int i = 0; i < row1.length; i++) {
			row1[i] = Integer.parseInt(numsPos.get(i)) - 1;
			char c = charsPos.get(i).charAt(0);
			int ascii = (int) c;
			column1[i] = ascii - 65;
		}
		Object[] values = new Object[numsPos.size()];
		for (int i = 0; i < numsPos.size(); i++) {
			int grammi = row1[i];
			int sthlh = column1[i];
			Object value = sheet.getValueAt(grammi, sthlh);
			values[i] = value;
			cellTake.add(sheet.getSheetModel().getCells()[grammi][sthlh]);
			if (!sheet.getSheetModel().getCells()[grammi][sthlh].getCellGives().contains(this)) {
				sheet.getSheetModel().getCells()[grammi][sthlh].getCellGives().add(this);
			}
		}
		System.out.println("Compute");
		

		Object tmp = "";
		if (func.equals("ABS")) {
			tmp = String.valueOf(new AbsFunction(sheet, row, column, "", "ABS").calculateValue(values));
			
		} else if (func.equals("SUM")) {
			tmp = String.valueOf(new SumFunction(sheet, row, column, "", "ABS").calculateValue(values));
			
		} else if (func.equals("COS")) {
			tmp = String.valueOf(new CosFunction(sheet, row, column, "", "ABS").calculateValue(values));
			
		} else if (func.equals("SIN")) {
			tmp = String.valueOf(new SinFunction(sheet, row, column, "", "ABS").calculateValue(values));
			
		} else if (func.equals("TAN")) {
			tmp = String.valueOf(new TanFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("POW")) {
			tmp = String.valueOf(new PowFunction(sheet, row, column, "", "ABS").calculateValue(values));

		} else if (func.equals("MULT")) {
			tmp = String.valueOf(new MultFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("LOG")) {
			tmp = String.valueOf(new LogFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("LOG10")) {
			tmp = String.valueOf(new Log10Function(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("AND")) {
			tmp = String.valueOf(new AndFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("OR")) {
			tmp = String.valueOf(new OrFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("NOT")) {
			tmp = String.valueOf(new NotFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("XOR")) {
			tmp = String.valueOf(new XorFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("MAX")) {
			tmp = String.valueOf(new MaxFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("MIN")) {
			tmp = String.valueOf(new MinFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("MEAN")) {
			tmp = String.valueOf(new MeanFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("MEDIAN")) {
			tmp = String.valueOf(new MedianFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("STDDEV")) {
			tmp = String.valueOf(new StddevFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("CONCAT")) {
			tmp = String.valueOf(new ConcatFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("INCLUDES")) {
			tmp = String.valueOf(new IncludesFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("TRIM")) {
			tmp = String.valueOf(new TrimFunction(sheet, row, column, "", "ABS").calculateValue(values));
		} else if (func.equals("REMOVE")) {
			tmp = String.valueOf(new RemoveFunction(sheet, row, column, "", "ABS").calculateValue(values));
		}

		try {
			this.setValueObject(tmp);
			sheet.getSheetModel().setValueAt(tmp, row, column);
			hadFunc = true;
		} catch (Exception e) {
			this.setValueObject("#ERROR");
		}

		
		this.setRawvalued(false);

	}

	

	public String getFormula() {
		return formula;
	}

	/**
	 * @param formula
	 *            the formula to set
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}

	/**
	 * @return the rformula
	 */
	public String getRformula() {
		return rformula;
	}

	public void setHadFunc(boolean value) {
		if (value == false) {
			hadFunc = value;
			this.inputCells = "";
			suna = "";
			cellGive.clear();
			for(int i=0; i<cellGive.size(); i++){
				cellGive.get(i).getCellTake().remove(this);
//				cellGive.get(i).getSunartisi().replaceAll(array[1], "");
			}
			
			cellTake.clear();
		}

	}

	/**
	 * @param rformula
	 *            the rformula to set
	 */
	public String getSunartisi() {
		return  suna;
	}

	/**
	 * @return the listeners
	 */
	public ArrayList<Cell> getListeners() {
		return listeners;
	}

	/**
	 * @param listeners
	 *            the listeners to set
	 */
	public void setListeners(ArrayList<Cell> listeners) {
		this.listeners = listeners;
	}

	/**
	 * @return the references
	 */
	public ArrayList<Cell> getReferences() {
		return references;
	}

	/**
	 * @param references
	 *            the references to set
	 */
	public void setReferences(ArrayList<Cell> references) {
		this.references = references;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the editing
	 */
	public boolean isEditing() {
		return editing;
	}

	/**
	 * @param editing
	 *            the editing to set
	 */
	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	/**
	 * @return the cells
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * @param cells
	 *            the cells to set
	 */
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	/**
	 * @return the sheet
	 */
	public SpreadSheet getSheet() {
		return sheet;
	}

	/**
	 * @param sheet
	 *            the sheet to set
	 */
	public void setSheet(SpreadSheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * @return the rawvalued
	 */
	public boolean isRawvalued() {
		return rawvalued;
	}

	/**
	 * @param rawvalued
	 *            the rawvalued to set
	 */
	public void setRawvalued(boolean rawvalued) {
		this.rawvalued = rawvalued;
	}

	public void setFunction(String function) {
		this.suna = function;
	}

	public void setInputCells(String input) {
		this.inputCells = input;
	}

	/**
	 * @return the value
	 */
	public Object getValueObject() {
		return value;
	}

	public ArrayList<Cell> getCellTake() {
		return cellTake;
	}

	public ArrayList<Cell> getCellGives() {
		return cellGive;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValueObject(Object value) {
		this.value = value;
	}

	
}
