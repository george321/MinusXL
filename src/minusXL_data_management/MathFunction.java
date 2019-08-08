package minusXL_data_management;

public abstract class MathFunction extends Function {

	public MathFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, value, formula);
		// TODO Auto-generated constructor stub
	}

	public abstract Object calculateValue(Object[] inputs);

	public boolean checkValidity(Object[] inputs) {

		String tmpStr;

		for (int i = 0; i < inputs.length; i++) {
			// tmpStr = (String) inputs[i];
			// System.out.println(tmpStr);
			// formatter.parse(tmpStr, pos);
			// if (tmpStr.length() == pos.getIndex()) {
			// array[i] = Integer.parseInt(tmpStr);
			// } else {
			// return false;
			// }
			tmpStr = (String) inputs[i];
			if (!tmpStr.matches("-?\\d+(\\.\\d+)?")) {
				return false;
			}
		}
		return true;
	}
}
