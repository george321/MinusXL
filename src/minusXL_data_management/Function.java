package minusXL_data_management;

public abstract class Function extends FunctionCell {

	public Function(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, value, formula);
	}

	public abstract Object calculateValue(Object[] inputs);

	public abstract boolean checkValidity(Object[] inputs);

}
