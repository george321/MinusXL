package minusXL_data_management;

import javax.swing.JOptionPane;

public class MultFunction extends MathFunction {

	public MultFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, value, formula);
		// TODO Auto-generated constructor stub
	}

	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			JOptionPane.showMessageDialog(null,"Τα δεδομένα εισόδου δεν είναι σωστά");
			return "ERROR!";
		}
		
		double array[] = new double[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = Double.parseDouble((String) inputs[i]);
		}
		double sum = 1;
		for (int i = 0; i < array.length; i++) {
			sum = sum * array[i];
		}
		return sum;

	}

}
