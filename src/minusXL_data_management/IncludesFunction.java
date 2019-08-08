package minusXL_data_management;

import javax.swing.JOptionPane;

public class IncludesFunction extends AlphaNumFunction {

	public IncludesFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, formula, formula);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			JOptionPane.showMessageDialog(null,"Τα δεδομένα εισόδου δεν είναι σωστά ή τα κελιά εισόδου είναι κενά.");
			setHadFunc(false);
			return "ERROR!";
		}
		if (!(inputs.length == 2)) {
			JOptionPane.showMessageDialog(null,"Η είσοδος πρέπει να έχει 2 κελιά.");
			setHadFunc(false);
			return "ERROR!";
		}
		String array[] = new String[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = (String) inputs[i];
		}

		if (array[0].toUpperCase().contains(array[1].toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}

}
