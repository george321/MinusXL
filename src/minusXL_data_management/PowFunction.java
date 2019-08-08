package minusXL_data_management;

import javax.swing.JOptionPane;

public class PowFunction extends MathFunction {


	public PowFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, formula, formula);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			JOptionPane.showMessageDialog(null,"Τα δεδομένα εισόδου δεν είναι σωστά");
			setHadFunc(false);
			return "ERROR!";
		}
		if (!(inputs.length == 2)) {
			JOptionPane.showMessageDialog(null,"Πρέπει να δώσεις 2 αριθμούς για είσοδο.");
			setHadFunc(false);
			return "ERROR!";
		}

		double array[] = new double[inputs.length];
		for (int i = 0; i < inputs.length; i++) {

			array[i] = Double.parseDouble((String) inputs[i]);
		}
		return Math.pow(array[0], array[1]);
	}

}
