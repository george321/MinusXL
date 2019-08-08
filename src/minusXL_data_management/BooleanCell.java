package minusXL_data_management;

public class BooleanCell extends Cell {
	String value;

	public BooleanCell(int i, int j, String value) {
		super(null, i, j);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString(){
		return null;
		
	}

}
