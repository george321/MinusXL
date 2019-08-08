package minusXL_file_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.xml.stream.XMLStreamException;
import minusXL_view.View;

public class CSVFileReader {
	private String filename;

	public void readFile(View aView) throws XMLStreamException {
		String userDir = System.getProperty("user.home");
		JFileChooser fc = new JFileChooser(userDir + "/Desktop");
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
		int returnVal = fc.showDialog(aView, "Open");
		if (returnVal == 0) {
			String path = fc.getSelectedFile().getPath();
			
			filename = fc.getSelectedFile().getName();
			filename = filename.replaceAll(".csv", "");
			

			if (aView.getBook().getSheetCollection().getTabCount() > 0) {
				aView.getBook().setSavedPath(path);
				parseCSV(path, aView, true);
				aView.getBook().getSheetCollection().revalidate();
				aView.getBook().getSheetCollection().repaint();

				aView.getFormulaBar().setEditable(true);
				aView.getFormulaBar().setText("Click on a cell");

			} else {
				aView.getBook().setSavedPath(path);
				parseCSV(path, aView, false);
				// refresh jtabbed pane
				aView.getBook().getSheetCollection().revalidate();
				aView.getBook().getSheetCollection().repaint();

				aView.getFormulaBar().setEditable(true);
				aView.getFormulaBar().setText("Click on a cell");

			}
		}
	}

	public void parseCSV(String aCSVFile, View aView, boolean flag) {
		// Input file which needs to be parsed
		String fileToParse = aCSVFile;
		BufferedReader fileReader = null;

		// Delimiter used in CSV file
		final String DELIMITER = ";";
		try {
			String line = "";
			// Create the file reader
			fileReader = new BufferedReader(new FileReader(fileToParse));
			if (flag == false) {
				aView.getBook().addSpreadSheet(100, 100, filename);
			}else{
				aView.getBook().getActiveSheet().setName(filename);
			}
			// Read the file line by line
			int row = 0;
			while ((line = fileReader.readLine()) != null) {
				// Get all tokens available in line
				String[] tokens = line.split(DELIMITER);
				for (int i = 0; i < tokens.length; i++) {
					aView.getBook().getActiveSheet().getSheetModel().setValueAt(tokens[i], row, i);
				}
				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
