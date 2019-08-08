package minusXL_data_management;

import javax.swing.JOptionPane;

public class RemoveFunction extends AlphaNumFunction {


	public RemoveFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, formula, formula);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			JOptionPane.showMessageDialog(null,"�� �������� ������� ��� ����� ����� � �� ����� ������� ����� ����.");
			setHadFunc(false);
			return "ERROR!";
		}
		if (!(inputs.length == 2)) {
			JOptionPane.showMessageDialog(null,"� ������� ������ �� ���� 2 �����.");
			setHadFunc(false);
			return "ERROR!";
		}
		String array[] = new String[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = (String) inputs[i];
		}

		String tmp = array[0].replaceAll(array[1], "");
		return tmp;

	}

}
