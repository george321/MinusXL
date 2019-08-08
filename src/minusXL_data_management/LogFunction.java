package minusXL_data_management;

import javax.swing.JOptionPane;

public class LogFunction extends MathFunction {


	public LogFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, formula, formula);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			JOptionPane.showMessageDialog(null, "�� �������� ������� ��� ����� �����");
			setHadFunc(false);

			return "ERROR!";
		}
		if (!(inputs.length == 1)) {
			JOptionPane.showMessageDialog(null, "������ �� ������ ���� ���� ������ ��� ������.");
			setHadFunc(false);

			return "ERROR!";
		}

		double array[] = new double[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			if (Double.parseDouble((String) inputs[i]) <= 0) {
				JOptionPane.showMessageDialog(null, "� ������� �� ������ �� ����� >0.");
				return "ERROR!";
			}
			array[i] = Double.parseDouble((String) inputs[i]);
		}
		return Math.log10(array[0]);
	}
}
