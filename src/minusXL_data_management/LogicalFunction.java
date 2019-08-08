package minusXL_data_management;

public abstract class LogicalFunction extends Function {

	public LogicalFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, value, formula);
		// TODO Auto-generated constructor stub
	}

	public abstract Object calculateValue(Object[] inputs);

	public boolean checkValidity(Object[] inputs) {
		String tmpStr;
		int x;
		for (int i = 0; i < inputs.length; i++) {
			tmpStr = (String) inputs[i];
			if (!tmpStr.matches("-?\\d+(\\.\\d+)?")) {
				return false;
			}
			x = Integer.parseInt(tmpStr);
			if (!(x == 0 || x == 1) ){
				return false;
			}
		}
		return true;
	}

}
