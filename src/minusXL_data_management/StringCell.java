package minusXL_data_management;

public class StringCell extends Cell {
	String value;

	public StringCell(int i, int j, String value) {
		super(null, i, j);
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public String toString(){
		return value;
		
	}

}