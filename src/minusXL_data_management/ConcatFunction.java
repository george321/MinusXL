package minusXL_data_management;

import javax.swing.JOptionPane;

public class ConcatFunction extends AlphaNumFunction {

	public ConcatFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
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
		String enwmeno = (String) inputs[0] + (String) inputs[1];
		return enwmeno;
	}

}
