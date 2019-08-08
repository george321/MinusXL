package minusXL_data_management;

import javax.swing.JOptionPane;

public class AbsFunction extends MathFunction {


	public AbsFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, formula, formula);
	}

	@Override
	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			JOptionPane.showMessageDialog(null,"Τα δεδομένα εισόδου δεν είναι σωστά");
			setHadFunc(false);
			return "ERROR!";
		}
		if (!(inputs.length == 1)) {
			JOptionPane.showMessageDialog(null,"Πρέπει να δώσεις μόνο έναν αριθμό για είσοδο.");
			setHadFunc(false);

			return "ERROR!";
		}
		double array[]= new double[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = Double.parseDouble((String) inputs[i]);
		}
		return Math.abs(array[0]);

	}

}
