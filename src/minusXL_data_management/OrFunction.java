package minusXL_data_management;

import javax.swing.JOptionPane;

public class OrFunction extends LogicalFunction {

	public OrFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, formula, formula);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			JOptionPane.showMessageDialog(null, "Τα δεδομένα εισόδου δεν είναι σωστά");
			setHadFunc(false);
			return "ERROR!";
		}
		if (!(inputs.length == 2)) {
			JOptionPane.showMessageDialog(null, "Για την συνάρτηση AND χρειάζεσαι δύο αριθμούς για είσοδο.");
			setHadFunc(false);
			return "ERROR!";
		}

		int array[] = new int[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = Integer.parseInt((String) inputs[i]);
		}
		int x = array[0];
		int y = array[1];
		if (x == 1 && y == 1) {
			return 1;
		} else if (x == 1 && y == 0) {
			return 1;
		} else if (x == 0 && y == 1) {
			return 1;
		} else if (x == 0 && y == 0) {
			return 0;
		} else {
			return null;
		}
	}

}
