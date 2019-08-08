package minusXL_data_management;

public abstract class AlphaNumFunction extends Function {

	public AlphaNumFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, value, formula);
	}

	public abstract Object calculateValue(Object[] inputs);

	public boolean checkValidity(Object[] inputs) {
		if (inputs.length == 0) {
			return false;
		}
		return true;
	}
}
