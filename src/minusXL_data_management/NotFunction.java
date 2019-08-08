package minusXL_data_management;

import javax.swing.JOptionPane;

public class NotFunction extends LogicalFunction {

	public NotFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
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
		if (!(inputs.length == 1)) {
			JOptionPane.showMessageDialog(null,"Για την συνάρτηση ΝΟΤ χρειάζεσαι ένα αριθμό για είσοδο.");
			setHadFunc(false);
			return "ERROR!";
		}

		int array[] = new int[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = Integer.parseInt((String) inputs[i]);
		}
		int x = array[0];
		if (x == 1) {
			return 0;
		} else if (x == 0) {
			return 1;
		} else {
			return null;
		}
	}

}
