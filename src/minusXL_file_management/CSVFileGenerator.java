package minusXL_file_management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import minusXL_view.View;

public class CSVFileGenerator {

	private View aView;
	private boolean saveAs;
	private String path;
	private int row = 0, column = 0;

	public boolean createFile(View aView, boolean save) {
		this.aView = aView;
		this.saveAs = save;
		if (aView.getBook().getSavedPath().length() < 1 || saveAs) {
			String userDir = System.getProperty("user.home");
			JFileChooser fc = new JFileChooser(userDir + "/Desktop");
			int saved;
			fc.setAcceptAllFileFilterUsed(false);
			fc.addChoosableFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					return f.getPath().endsWith(".csv") | f.getPath().endsWith(".CSV") | f.isDirectory();
				}

				@Override
				public String getDescription() {
					return "CSV file (.csv)";
				}
			});
			saved = fc.showSaveDialog(aView);
			if (saved != 0) {
				return false;
			}
			path = fc.getSelectedFile().toString();
			aView.getBook().setSavedPath(path);
		} else {
			path = aView.getBook().getSavedPath();
		}

		if (!path.endsWith(".csv")) {
			path += ".csv";
		}
		
		// call for save
		generateCsvFile(path, aView);
		aView.getBook().setSaved(true);
		return true;
	}

	private void generateCsvFile(String sFileName, View aView) {
		row = aView.getBook().getActiveSheet().getSheetModel().getRowCount();
		column = aView.getBook().getActiveSheet().getSheetModel().getColumnCount();
		String value;
		String valueTMP = "";
		lastValue();

		try {
			FileWriter writer = new FileWriter(sFileName);

			for (int i = 0; i <= row; i++) {
				value = "";
				for (int j = 0; j <=column; j++) {
					valueTMP = "" + aView.getBook().getActiveSheet().getSheetModel().getValueAt(i, j);
					if (valueTMP.length() > 0) {
						value = value + valueTMP + ";";

					} else if (valueTMP.length() == 0) {
						value = value + ";";
					}
				}
				if (value.endsWith(";")) {
					value = value.substring(0, value.length() - 1);
				}
				writer.append(value);
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Η εγγραφή του αρχείου στο δίσκο δεν μπόρεσε να γίνει.");
			e.printStackTrace();
		}
	}

	private void lastValue() {
		row = aView.getBook().getActiveSheet().getSheetModel().getRowCount();
		column = aView.getBook().getActiveSheet().getSheetModel().getColumnCount();
		int lastrow = 0, lastcolumn = 0;
		String tmpValue;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				tmpValue = "" + aView.getBook().getActiveSheet().getSheetModel().getValueAt(i, j);
				if (tmpValue.length() > 0) {
					if (lastrow < i) {
						lastrow = i;
					}
					if (lastcolumn < j) {
						lastcolumn = j;
					}
					
				}
			}
		}
		row = lastrow;
		column = lastcolumn;
	}
}
