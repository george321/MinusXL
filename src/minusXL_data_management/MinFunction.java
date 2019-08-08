package minusXL_data_management;

import javax.swing.JOptionPane;

public class MinFunction extends StatisticFunction {

	public MinFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
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
		double array[] = new double[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = Double.parseDouble((String) inputs[i]);
		}
		double min=array[0];
		for (int i = 0; i < array.length; i++) {
			if(array[i]<min){
				min = array[i];
			}
		}
		return min;
	}

}
