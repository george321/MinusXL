package minusXL_data_management;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import minusXL_view.*;

public class WorkBook extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private View container;
	private int sheetCount;
	private boolean saved;
	private String savedPath;
	private String workBookName;
	private JTabbedPane sheetCollection;
	private ArrayList<SpreadSheet> spreadSheets;

	public WorkBook(View view, String name) {
		super();
		this.workBookName = name;
		saved = true;
		this.container = view;
		this.savedPath = "";
		spreadSheets = new ArrayList<>();
		sheetCollection = new JTabbedPane();
		sheetCollection.setTabPlacement(JTabbedPane.BOTTOM);

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				if (getSheetCollection().getTabCount() > 0) {

					getContainer().getFormulaBar().setVisible(true);
					getContainer().getBarCaption().setVisible(true);
					getContainer().getmenuBar().getMenu(0).getItem(2).setEnabled(true);
					getContainer().getmenuBar().getMenu(0).getItem(3).setEnabled(true);
					getContainer().getmenuBar().getMenu(0).getItem(5).setEnabled(true);
					try {
						if (getSpreadSheets().toArray().length > getSheetCollection().getSelectedIndex()) {
							int r, c;
							r = getSpreadSheets().get(getSheetCollection().getSelectedIndex()).getSelectedRow();
							c = getSpreadSheets().get(getSheetCollection().getSelectedIndex()).getSelectedColumn();
							getContainer().getFormulaBar()
									.setText(getSpreadSheets().get(getSheetCollection().getSelectedIndex())
											.getSheetModel().getFormularBarText(r, c));
						}
					} catch (Exception e) {
					}

				} else {
					getContainer().getFormulaBar().setVisible(false);
					getContainer().getBarCaption().setVisible(false);
					getContainer().getmenuBar().getMenu(0).getItem(2).setEnabled(false);
					getContainer().getmenuBar().getMenu(0).getItem(3).setEnabled(false);
					getContainer().getmenuBar().getMenu(0).getItem(5).setEnabled(true);

				}
			}
		};
		sheetCollection.addChangeListener(changeListener);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(sheetCollection, BorderLayout.CENTER);

	}

	public SpreadSheet getActiveSheet() {
		if (getSheetCollection().getTabCount() == 0) {
			return null;
		}
		return getSpreadSheets().get(getSheetCollection().getSelectedIndex());
	}

	public void addSpreadSheet() {
		String name = "SHEET";
		for (int i = 1; i < 1000; i++) {
			ArrayList<String> titles = new ArrayList<>();
			for (int j = 0; j < getSheetCollection().getTabCount(); j++) {
				titles.add(getSheetCollection().getTitleAt(j));
			}
			if (titles.contains(name + i)) {
				continue;
			}
			name += i;
			break;
		}
		SpreadSheet s = new SpreadSheet(this, 60, 40, name);
		getSpreadSheets().add(s);
		setSheetCount(getSheetCount() + 1);
		getSheetCollection().add(getSpreadSheets().get(getSheetCount() - 1).getSpreadSheetName(),
				getSpreadSheets().get(getSheetCount() - 1).getSheet());
	}

	public void addSpreadSheet(int row, int col, String name) {

		for (int i = 1; i < 1000; i++) {
			ArrayList<String> titles = new ArrayList<>();
			for (int j = 0; j < getSheetCollection().getTabCount(); j++) {
				titles.add(getSheetCollection().getTitleAt(j));
			}
			if (titles.contains(name)) {
				name += i;
				break;
			}
			
		}
		SpreadSheet s = new SpreadSheet(this, row, col, name);
		getSpreadSheets().add(s);
		setSheetCount(getSheetCount() + 1);
		getSheetCollection().add(getSpreadSheets().get(getSheetCount() - 1).getSpreadSheetName(),
				getSpreadSheets().get(getSheetCount() - 1).getSheet());
	}

	public void closeSheet(int i) {
		if (i < 0) {
			i = getSheetCollection().getSelectedIndex();
		}
		if (i < getSheetCount()) {
			if (i >= getSheetCollection().getTabCount()) {
				getSheetCollection().repaint();
			} else {
				if (getSheetCollection().getTabCount() == 1) {
					getSheetCollection().removeAll();
					getContainer().getFormulaBar().setVisible(false);
					getContainer().getBarCaption().setVisible(false);
					getContainer().getmenuBar().getMenu(0).getItem(2).setEnabled(false);
					getContainer().getmenuBar().getMenu(0).getItem(3).setEnabled(false);
					getContainer().getmenuBar().getMenu(0).getItem(5).setEnabled(false);
				} else {
					getSheetCollection().removeTabAt(i);
				}
				getSpreadSheets().remove(i);
			}
		}
		setSheetCount(getSheetCollection().getTabCount());
	}

	public ArrayList<SpreadSheet> getSpreadSheets() {
		return spreadSheets;
	}

	public WorkBook getWorkbook() {
		return this;
	}

	public View getContainer() {
		return container;
	}

	public boolean isSaved() {
		return saved;
	}

	public String getSavedPath() {
		return savedPath;
	}
	
	public void setSavedPath(String savedPath) {
        this.savedPath = savedPath;
    }

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public void setSheetCollection(JTabbedPane sheetCollection) {
		this.sheetCollection = sheetCollection;
	}

	public void setContainer(View container) {
		this.container = container;
	}

	public JTabbedPane getSheetCollection() {
		return sheetCollection;
	}

	public int getSheetCount() {
		return sheetCount;
	}

	public void setSheetCount(int sheetCount) {
		this.sheetCount = sheetCount;
	}

}
