package minusXL_data_management;

import javax.swing.JOptionPane;

public class TrimFunction extends AlphaNumFunction {


	public TrimFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
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
		if (!(inputs.length == 1)) {
			JOptionPane.showMessageDialog(null,"Η είσοδος πρέπει να έχει 1 κελί.");
			setHadFunc(false);
			return "ERROR!";
		}
		String input = (String) inputs[0];
		input =input.replaceAll("\\s","");
		return input;
	}

}
