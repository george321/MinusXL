package minusXL_data_management;

import javax.swing.JOptionPane;

public class XorFunction extends LogicalFunction {
	int r,c;
	public XorFunction(SpreadSheet sheet, int r, int c, String value, String formula) {
		super(sheet, r, c, formula, formula);
		this.r = r;
		this.c = c;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object calculateValue(Object[] inputs) {
		if (!checkValidity(inputs)) {
			JOptionPane.showMessageDialog(null,"�� �������� ������� ��� ����� �����");
			setHadFunc(false);
			return "ERROR!";
		}
		if (!(inputs.length == 2)) {
			JOptionPane.showMessageDialog(null,"��� ��� ��������� AND ���������� ��� �������� ��� ������.");
			setHadFunc(false);
			return "ERROR!";
		}
		
		int array[]= new int[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			array[i] = Integer.parseInt((String) inputs[i]);
		}
		int x = array[0];
		int y = array[1];
		if(x==1 && y==1){
			return 0;
		}else if(x==1 && y==0){
			return 1;
		}else if(x==0 && y==1){
			return 1;
		}else if(x==0 && y==0){
			return 0;
		}else{
			return null;
		}
	}


}
