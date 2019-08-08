package minusXL_data_management;

import java.util.Arrays;

public class StddevFunction extends StatisticFunction {


	public StddevFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, formula, formula);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			System.out.println("Not number");
			return null;
		}
		double array[] = new double[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = Double.parseDouble((String) inputs[i]);
		}
		Arrays.sort(array);
		double sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum = sum + array[i];
		}
		double average = sum / array.length;

		double sd = 0;
		for (int i = 0; i < array.length; i++) {
			sd += ((array[i] - average) * (array[i] - average)) / (array.length - 1);

		}

		double standardDeviation = Math.sqrt(sd);
		return standardDeviation;

	}

}
