package minusXL_data_management;

import java.util.Arrays;

import javax.swing.JOptionPane;

public class MedianFunction extends StatisticFunction {

	public MedianFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
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
		Arrays.sort(array);
		// Calculate median (middle number)
	      double median = 0;
	      double pos1 = Math.floor((array.length - 1.0) / 2.0);
	      double pos2 = Math.ceil((array.length - 1.0) / 2.0);
	      if (pos1 == pos2 ) {
	         median = array[(int)pos1];
	      } else {
	         median = (array[(int)pos1] + array[(int)pos2]) / 2.0 ;
	      }
	    return median;
	}

}
