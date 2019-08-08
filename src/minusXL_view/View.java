package minusXL_view;

import minusXL_data_management.*;
import minusXL_file_management.*;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentListener;
import javax.xml.stream.XMLStreamException;

import minusXL_charts_management.ChartsManager;

public class View extends JFrame implements Runnable, ActionListener {

	private static final long serialVersionUID = 1L;
	private WorkBook workbook;
	private final JToolBar toolbar;
	private final JPanel fbar;
	private final JTextField formulaBar;
	private JLabel barCaption;
	private JMenuBar menuBar;
	private JTextField dataCells;
	private JTextField data;

	private JComboBox<String> comboBox;
	private JList<String> list;
	private JScrollPane scrollPane;
	private Hashtable<String, String[]> subItems = new Hashtable<String, String[]>();

	public View(WorkBook book) {
		
		newBook(book);
		toolbar = new JToolBar();
		fbar = new JPanel();
		formulaBar = new JTextField(70);
		menuBar = new JMenuBar();
		
		ImageIcon img = new ImageIcon("Excel.png");
		setIconImage(img.getImage());
		setTitle("MinusXL");
		setName("MinusXL");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});

		toolbar.add(fbar);
		formulaBar.setText("Click on a cell");
		formulaBar.setSize(500, 25);
		formulaBar.setFont(new Font("Times", Font.PLAIN, 16));
		formulaBar.setBorder(BorderFactory.createCompoundBorder(formulaBar.getBorder(),
				BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		formulaBar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBook().getSpreadSheets().get(getBook().getSheetCollection().getSelectedIndex()).setFromFormulaBar();
			}
		});

		formulaBar.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			public void removeUpdate(DocumentEvent e) {
				update();
			}

			public void insertUpdate(DocumentEvent e) {
				update();
			}

			public void update() {
				getBook().getActiveSheet().repaint();
			}
		});

		fbar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		fbar.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 1;
		c1.gridy = 0;
		c1.ipadx = 0;
		barCaption = new JLabel(" Formula :", SwingConstants.CENTER);
		barCaption.setBorder(BorderFactory.createCompoundBorder(barCaption.getBorder(),
				BorderFactory.createEmptyBorder(0, 17, 0, 17)));
		fbar.add(barCaption, c1);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		fbar.add(formulaBar, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		JMenu menuFile = new JMenu("Αρχείο");
		menuBar.add(menuFile);
		// 0
		JMenuItem menuItemNew = new JMenuItem("Νέο", KeyEvent.VK_N);
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menuItemNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rowCol();
			}

		});
		menuFile.add(menuItemNew);
		// 1
		JMenuItem menuItemOpen = new JMenuItem("ʼνοιγμα", KeyEvent.VK_O);
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openWorkBook();
			}

		});
		menuFile.add(menuItemOpen);
		// 2
		JMenuItem menuItemSave = new JMenuItem("Αποθήκευση", KeyEvent.VK_S);
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuItemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveBook(true);
			}

		});
		menuFile.add(menuItemSave);
		// 3
		JMenuItem menuItemDeleteSheet = new JMenuItem("Διαγραφή Φύλλου", KeyEvent.VK_D);
		menuItemDeleteSheet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		menuItemDeleteSheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBook().closeSheet(-1);
			}
		});
		menuFile.add(menuItemDeleteSheet);

		// 4
		menuFile.addSeparator();
		// 5
		JMenuItem menuItemClose = new JMenuItem("Κλείσιμο WorkBook", KeyEvent.VK_C);
		menuItemClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menuItemClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeBook(0);
			}
		});
		menuFile.add(menuItemClose);

		JMenu mnGraph = new JMenu("Graph");
		menuBar.add(mnGraph);

		JMenuItem mntmLinechart = new JMenuItem("LineChart");
		mntmLinechart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createChart("LINECHART");
			}
		});
		mnGraph.add(mntmLinechart);

		JMenuItem mntmBarchart = new JMenuItem("BarChart");
		mntmBarchart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createChart("BARCHART");
			}
		});
		mnGraph.add(mntmBarchart);

		JButton function = new JButton("Συνάρτηση");
		menuBar.add(function);
		function.setBorderPainted(false);
		function.setFocusPainted(false);
		function.setContentAreaFilled(false);
		function.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseFunction();
			}
		});

		JButton about = new JButton("Βοήθεια");
		about.setBorderPainted(false);
		about.setFocusPainted(false);
		about.setContentAreaFilled(false);
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printHelp();
				
			}
		});

		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(about);

		if (this.workbook.getSheetCollection().getTabRunCount() < 1) {
			formulaBar.setVisible(false);
			barCaption.setVisible(false);
			menuBar.getMenu(0).getItem(2).setEnabled(false);
			menuBar.getMenu(0).getItem(3).setEnabled(false);
			// to 4 to separator
			menuBar.getMenu(0).getItem(5).setEnabled(false);
		}

		toolbar.setFloatable(false);
		setJMenuBar(menuBar);
		getContentPane().add(toolbar, BorderLayout.NORTH);
	}

	public void printHelp() {
		JPanel contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(600, 500));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		JEditorPane dtrpnminusxlHelp = new JEditorPane();
		dtrpnminusxlHelp.setContentType("text/html");
		dtrpnminusxlHelp.setEditable(false);
		InputStream in = null;
		try {
			in = new FileInputStream("online.html");
			dtrpnminusxlHelp.read(in, null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		scrollPane.setViewportView(dtrpnminusxlHelp);
		JOptionPane.showMessageDialog(null,contentPane);
		
	}

	public void createChart(String type) {
		

		JTextField chartName;
		JTextField xTitles;
		JTextField yTitles;
		JTextField data;
		JTextField category;
		JTextField rowCol;

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 590, 480);
		panel.setPreferredSize(new Dimension(590, 480));
		panel.setLayout(null);

		JLabel lblCreateBarchart = new JLabel("Δημιουργία " + type);
		lblCreateBarchart.setBounds(188, 11, 142, 30);
		panel.add(lblCreateBarchart);

		JLabel lblNewLabel = new JLabel("Όνομα γραφήματος:");
		lblNewLabel.setBounds(37, 55, 130, 14);
		panel.add(lblNewLabel);

		chartName = new JTextField();
		chartName.setBounds(177, 52, 127, 20);
		panel.add(chartName);
		chartName.setColumns(10);

		JLabel lblAxisTitles = new JLabel("Ονόματα άξονα Χ");
		lblAxisTitles.setBounds(37, 88, 107, 14);
		panel.add(lblAxisTitles);

		xTitles = new JTextField();
		xTitles.setBounds(176, 85, 128, 20);
		panel.add(xTitles);
		xTitles.setColumns(10);

		JLabel lblData = new JLabel("Μεμονομένα Κελιά:");
		lblData.setBounds(37, 213, 107, 14);
		panel.add(lblData);

		data = new JTextField();
		data.setBounds(177, 210, 127, 20);
		panel.add(data);
		data.setColumns(10);

		JLabel lblYAxisTitles = new JLabel("Όνομα άξονα Y");
		lblYAxisTitles.setBounds(37, 122, 107, 14);
		panel.add(lblYAxisTitles);

		yTitles = new JTextField();
		yTitles.setBounds(177, 119, 127, 20);
		panel.add(yTitles);
		yTitles.setColumns(10);

		JLabel lblCategory = new JLabel("Κατηγορίες:");
		lblCategory.setBounds(37, 152, 107, 14);
		panel.add(lblCategory);

		category = new JTextField();
		category.setBounds(177, 150, 127, 20);
		panel.add(category);
		category.setColumns(10);

		JLabel lblRowcolData = new JLabel("Σύνολο κελιών:");
		lblRowcolData.setBounds(37, 299, 107, 14);
		panel.add(lblRowcolData);

		rowCol = new JTextField();
		rowCol.setBounds(177, 296, 127, 20);
		panel.add(rowCol);
		rowCol.setColumns(10);

		JLabel lblOnomaGrafimatos = new JLabel("Όνομα γραφήματος");
		lblOnomaGrafimatos.setBounds(314, 52, 157, 14);
		panel.add(lblOnomaGrafimatos);

		JLabel lblLabelsDedomenwnSton = new JLabel(
				"<html>Ονόματα για τις τιμές στον άξονα Χ(Χώρισε τα ονόματα με \",\"</html>");
		lblLabelsDedomenwnSton.setBounds(314, 85, 252, 34);
		panel.add(lblLabelsDedomenwnSton);

		JLabel lblNewLabel_1 = new JLabel("Όνομα άξονα Υ");
		lblNewLabel_1.setBounds(314, 122, 206, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel(
				"<html>Ονόματα για τις κατηογρίες που θελεις να απεικονίσεις(Χώρισε τα ονόματα με \",\").</html>");
		lblNewLabel_2.setBounds(314, 133, 252, 66);
		panel.add(lblNewLabel_2);

		JLabel lbleisagwghKeliwnGia = new JLabel(
				"<html><center>ΠΡΟΣΟΧΗ</center></br>Το πλήθος των Labels στον άξονα X θα πρέπει να είναι ίσο με το πλήθος των δεδομένων των κατηγοριών. Επίσης το πληθος των συνόλων των δεδομένων θα πρεπει να είναι ίσο με το πλήθος τω κατηγοριών. Για παράδειγμα αν έχουμε 2 κατηγορίες και 3 τιμές θα δώσεις τα εξής: X Labels:Δευτερα,Τριτη,τεταρτη , Κατηγορίες: 2015,2016 , Συνολο κελιών: a1:a3,b1:b3 </html>");
		lbleisagwghKeliwnGia.setBounds(40, 375, 414, 105);
		panel.add(lbleisagwghKeliwnGia);

		JLabel lbledwVazeisTa = new JLabel(
				"<html>Εδώ βάζεις τα ξεχωριστά κελιά χωρίζοντάς τα με \",\" και τα σύνολα των κελιών τα χωρίζεις με \";\" Για παράδειγμα εαν έχεις 2 κατηγορίες χωρίζεις τα δεδομένα ετσι: a1,a2,a3:c1,c2,c3</html>");
		lbledwVazeisTa.setBounds(314, 181, 252, 114);
		panel.add(lbledwVazeisTa);

		JLabel lblEdwEisageisGia = new JLabel(
				"<html> Εδώ εισάγεις τα δεδομένα με τη μορφή ολόκληρης γραμμής ή στήλης και χωρίζεις τα σύνολα των δεδομέων με \",\". Για παράδειγμα αν έχεις 2 κατηγορίες εισάγεις τα δεδομένα έτσι: a1:a4,b1:b4</html>");
		lblEdwEisageisGia.setBounds(314, 269, 252, 116);
		panel.add(lblEdwEisageisGia);

		int reply = JOptionPane.showConfirmDialog(null, panel, "Δώσε δεδομένα για το γράφημα",
				JOptionPane.OK_CANCEL_OPTION);

		String categories = category.getText();
		String chartTitle = chartName.getText();
		String axisX = xTitles.getText();
		String axisY = yTitles.getText();
		String input = data.getText();
		String rowocol = rowCol.getText();

		if (reply == JOptionPane.OK_OPTION && (categories.length() != 0 && chartTitle.length() != 0
				&& axisX.length() != 0 && axisY.length() != 0 && (input.length() == 0 && rowocol.length() != 0)
				|| (input.length() != 0 && rowocol.length() == 0))) {
			// data here
			List<String> xAxisNames = Arrays.asList(axisX.split(","));
			List<String> categoriesList = Arrays.asList(categories.split(","));
			List<String> cellData = Arrays.asList(input.split(";"));
			List<String> rowColData = Arrays.asList(rowocol.split(","));

			int arrayILength = categoriesList.size();
			int arrayJLength = xAxisNames.size();

			
			int[][] array = new int[arrayILength][arrayJLength];
			for (int k = 0; k < arrayILength; k++) {
				String dataToDataCells = "";
				if (input.length() != 0) {
					dataToDataCells = stringCellEdit(cellData.get(k));
				}
				if (rowocol.length() != 0) {
					dataToDataCells = stringCellEdit(rowColData.get(k));
				}

				

				for (int i = 0; i < arrayJLength; i++) {
					Object[] values = cellToValue(dataToDataCells);
					for (int j = 0; j < values.length; j++) {
						array[k][j] = Integer.parseInt((String) values[j]);
					}
				}
			}
			ChartsManager chartMNGR = new ChartsManager(type, chartTitle, axisX, axisY, categories, array);

		} else {
			if ((input.length() != 0 && rowocol.length() != 0)) {
				JOptionPane.showMessageDialog(this,
						"Δεν μπορείς να δώσεις ταυτόχρονα ξεχωριστά κελια και ολοκληρες στηλες η γραμμες σαν είσοδο");

			} else {
				JOptionPane.showMessageDialog(this, "Έδωσες ελλιπή δεδομένα.");
			}
		}

	}

	public String stringCellEdit(String input) {
		String dataToDataCells = "";
		if (input.contains(",")) {
			return input;

		} else if (input.contains(":")) {
			List<String> rowCol1 = Arrays.asList(input.split(":"));
			if (rowCol1.get(0).length() == 2) {
				// Megethos 2
				String str1 = rowCol1.get(0), str2 = rowCol1.get(1);
				if (str1.charAt(1) == (str2.charAt(1))) {
					// Idia grammh
					int loops = str2.charAt(0) - str1.charAt(0) + 1;
					int tmp = str1.charAt(0);
					for (int i = 0; i < loops; i++) {
						String cell1 = Character.toString((char) tmp) + str1.charAt(1);
						if (!(loops - 1 == i)) {
							dataToDataCells = dataToDataCells + cell1 + ",";
							tmp++;
						} else {
							dataToDataCells = dataToDataCells + cell1;
						}
					}
				} else if (str1.charAt(1) != (str2.charAt(1))) {
					// idia sthlh
					int loops = str2.charAt(1) - str1.charAt(1) + 1;
					int x1 = Character.getNumericValue(str1.charAt(1));
					for (int i = 0; i < loops; i++) {
						String cell1 = "" + str1.charAt(0) + x1;
						if (!(loops - 1 == i)) {
							dataToDataCells = dataToDataCells + cell1 + ",";
							x1++;
						} else {
							dataToDataCells = dataToDataCells + cell1;
						}
					}
				}
			}
		}
		return dataToDataCells;

	}

	public Object[] cellToValue(String inputCells) {
		String numbers = inputCells.replaceAll("[^-?0-9]+", " ");
		List<String> numsPos = Arrays.asList(numbers.trim().split(" "));
		String characters = inputCells.replaceAll("\\d+", "");
		List<String> charsPos = Arrays.asList(characters.trim().toUpperCase().split("\\s*(=>|,|\\s)\\s*"));
		int[] row1 = new int[numsPos.size()];
		int[] column1 = new int[numsPos.size()];

		for (int i = 0; i < row1.length; i++) {
			row1[i] = Integer.parseInt(numsPos.get(i)) - 1;
			char c = charsPos.get(i).charAt(0);
			int ascii = (int) c;
			column1[i] = ascii - 65;
		}
		Object[] values = new Object[numsPos.size()];
		for (int i = 0; i < numsPos.size(); i++) {
			int grammi = row1[i];
			int sthlh = column1[i];
			Object value = getBook().getActiveSheet().getValueAt(grammi, sthlh);
			values[i] = value;
		}
		return values;
	}

	public void chooseFunction() {
		JPanel panel = new JPanel();
		panel.setBounds(100, 100, 450, 300);
		panel.setPreferredSize(new Dimension(450, 300));
		panel.setLayout(null);
		String[] items = { "Διάλεξε συνάρτηση", "Μαθηματικές και Τριγωνομετρικές", "Στατιστικές", "Αλφαριθμητικές",
				"Λογικές" };
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(this);
		comboBox.setModel(new DefaultComboBoxModel(items));
		comboBox.setBounds(159, 53, 127, 20);
		comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		panel.add(comboBox);
		String[] mathtimatikes = { "ABS", "COS", "TAN", "POW", "SUM", "MULT", "LOG", "LOG10" };
		subItems.put(items[1], mathtimatikes);
		String[] subItems2 = { "MAX", "MIN", "MEAN", "MEDIAN", "STDDEV" };
		subItems.put(items[2], subItems2);
		String[] subItems3 = { "CONCAT", "INCLUDES", "TRIM", "REMOVE" };
		subItems.put(items[3], subItems3);
		String[] subItems4 = { "AND", "OR", "NOT", "XOR" };
		subItems.put(items[4], subItems4);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(73, 108, 284, 104);
		panel.add(scrollPane);
		list = new JList<String>();
		scrollPane.setViewportView(list);

		JLabel lblEpelekseApoTi = new JLabel("Επέλεξε μια κατηγορία");
		lblEpelekseApoTi.setBounds(89, 28, 273, 14);
		panel.add(lblEpelekseApoTi);

		JLabel lblEpelekseKatigoria = new JLabel("Επέλεξε Κατηγορία");
		lblEpelekseKatigoria.setBounds(33, 53, 116, 14);
		panel.add(lblEpelekseKatigoria);

		JLabel lblEpelekseSinartisi = new JLabel("Επέλεξε Συνάρτηση");
		lblEpelekseSinartisi.setBounds(33, 84, 111, 14);
		panel.add(lblEpelekseSinartisi);

		JComboBox<String> comboBox_1 = new JComboBox<String>();
		panel.add(comboBox_1);
		list.setSelectedIndex(0);
		comboBox.setSelectedIndex(1);
		int reply = JOptionPane.showConfirmDialog(null, panel, "Επιλογή συνάρτησης", JOptionPane.OK_CANCEL_OPTION);
		if (reply == JOptionPane.OK_OPTION && list.isSelectionEmpty() == false) {
			String selFunc = list.getSelectedValue().toString();
			String selCatFunc = comboBox.getSelectedItem().toString();
			if (selFunc.length() != 0 && selCatFunc.length() != 0) {
				dataForFunction(selFunc, selCatFunc);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Δεν επέλεξες συνάρτηση.");
		}

	}

	public void dataForFunction(String func, String catFunc) {
		JPanel panel = new JPanel();
		panel.setBounds(100, 100, 450, 300);
		panel.setPreferredSize(new Dimension(450, 300));
		panel.setLayout(null);
		JLabel lblDwseOrismataSunarthshs = new JLabel("Δώσε ορίσματα για την συνάρτηση ", SwingConstants.CENTER);
		lblDwseOrismataSunarthshs.setBounds(77, 11, 276, 14);
		panel.add(lblDwseOrismataSunarthshs);

		JLabel lblKelia = new JLabel("Κελιά", SwingConstants.CENTER);
		lblKelia.setBounds(57, 150, 46, 14);
		panel.add(lblKelia);

		JLabel lblInfoGiaTa = new JLabel(
				"<html>Αν θέλεις να δώσεις μεμονομένα κελιά για είσοδο τότε πληκτρολόγησε τα κελιά χωρίζοντας τα με κόμμα. Παράδειγμα: A1,A2,B3 <br> <br> Αλλιώς αν θέλεις να δώσεις για είσοδο συνεχόμενα κελιά μιας γραμμής η μιας στήλης μπορείς να το κάνεις έτσι: Για γραμμή Α1:Α5 Για στήλη Α1:D1</html>");
		lblInfoGiaTa.setBounds(8, 36, 416, 85);
		panel.add(lblInfoGiaTa);

		dataCells = new JTextField();
		dataCells.setBounds(133, 147, 276, 20);
		panel.add(dataCells);
		dataCells.setColumns(10);

		JLabel lblSthlesHGrammes = new JLabel("<html>Σύνολο γραμμών ή στηλών</html>", SwingConstants.CENTER);
		lblSthlesHGrammes.setBounds(6, 191, 117, 45);
		panel.add(lblSthlesHGrammes);

		data = new JTextField();
		data.setBounds(133, 191, 276, 20);
		panel.add(data);
		data.setColumns(10);

		int reply = JOptionPane.showConfirmDialog(null, panel, "Ορίσματα για την συνάρτηση " + func,
				JOptionPane.OK_CANCEL_OPTION);

		String inputCells = dataCells.getText();
		String inputData = data.getText();
		if (reply == JOptionPane.OK_OPTION && (inputCells.length() == 0 && inputData.length() == 0)) {
			JOptionPane.showMessageDialog(this, "Δεν έδωσες δεδομένα.");
		}
		List<String> rowCol;
		int activeRow = getBook().getActiveSheet().getSelectedRow();
		int activeCol = getBook().getActiveSheet().getSelectedColumn();
		String dataToDataCells = "";
		if (inputData.contains(":")) {

			rowCol = Arrays.asList(inputData.split(":"));
			System.out.println(rowCol);
			if (rowCol.get(0).length() == 2) {
				// Megethos 2
				String str1 = rowCol.get(0), str2 = rowCol.get(1);
				if (str1.charAt(1) == (str2.charAt(1))) {
					// Idia grammh
					int loops = str2.charAt(0) - str1.charAt(0) + 1;
					int tmp = str1.charAt(0);
					for (int i = 0; i < loops; i++) {
						String cell = Character.toString((char) tmp) + str1.charAt(1);
						if (!(loops - 1 == i)) {
							dataToDataCells = dataToDataCells + cell + ",";
							tmp++;
						} else {
							dataToDataCells = dataToDataCells + cell;
						}
					}
				} else if (str1.charAt(1) != (str2.charAt(1))) {
					// idia sthlh
					int loops = str2.charAt(1) - str1.charAt(1) + 1;
					int x = Character.getNumericValue(str1.charAt(1));
					for (int i = 0; i < loops; i++) {
						String cell = "" + str1.charAt(0) + x;
						if (!(loops - 1 == i)) {
							dataToDataCells = dataToDataCells + cell + ",";
							x++;
						} else {
							dataToDataCells = dataToDataCells + cell;
						}
					}

				}
			}
		}
		if (inputCells.length() != 0 && inputData.length() != 0) {
			inputCells = inputCells + "," + dataToDataCells;
		} else if (inputCells.length() == 0 && inputData.length() != 0) {
			inputCells = dataToDataCells;
		}
		if (func.length() != 0 && inputCells.length() != 0) {
			getBook().getActiveSheet().getSheetModel().getCells()[activeRow][activeCol].Compute(inputCells, func);

		}
	}

	public void actionPerformed(ActionEvent e) {
		String item = (String) comboBox.getSelectedItem();
		Object o = subItems.get(item);

		if (o == null) {
			list.setModel(null);
		} else {
			list.setListData((String[]) o);
		}

	}

	public void run() {

		getContentPane().add(getBook(), BorderLayout.CENTER);
		setPreferredSize(new Dimension(850, 600));
		pack();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void closeWindow() {
		closeBook(1);
	}

	public void closeBook(int exit) {
		boolean confirm = !workbook.isSaved();
		boolean s = false;
		if (confirm) {
			int n = JOptionPane.showConfirmDialog(this, "Are you sure to save changes?", "jExcel",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (n) {
			case 1:
				s = true;
				break;
			case 2:
				s = false;
				break;
			case 0:
				s = saveBook(false);
			}
		} else {
			s = true;
		}
		if (s) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
				Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (exit == 1) {
				this.dispose();
			} else {
				getBook().getSheetCollection().removeAll();
				ArrayList<SpreadSheet> copy = new ArrayList<SpreadSheet>(getBook().getSpreadSheets());
				for (SpreadSheet sheet : copy) {
					getBook().getSpreadSheets().remove(sheet);
					getBook().setSheetCount(getBook().getSheetCount() - 1);
				}

				formulaBar.setVisible(false);
				barCaption.setVisible(false);
				menuBar.getMenu(0).getItem(2).setEnabled(false);
				menuBar.getMenu(0).getItem(4).setEnabled(false);
			}
		}

	}

	public void rowCol() {
		JTextField rows = new JTextField();
		JTextField cols = new JTextField();
		JTextField sheetName = new JTextField();
		rows.setText("");
		cols.setText("");
		sheetName.setText(" ");
		String info = "Αν πατησεις Cancel θα δωθουν προκαθορισμένες τιμές";
		Object[] message = { info, "Ονομα Φυλλου εργασίας:", sheetName, "Γραμμες:", rows, "Στήλες:", cols };
		Object[] options = { "Δημηιουργία", "Default" };

		int option = JOptionPane.showOptionDialog(null, message, "Δωσε πληθος στηλων και γραμμων",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (option == JOptionPane.OK_OPTION && (rows.getText().length() != 0 && cols.getText().length() != 0
				&& sheetName.getText().length() != 0)) {
			getBook().addSpreadSheet(Integer.parseInt(rows.getText()), Integer.parseInt(cols.getText()),
					sheetName.getText());
		} else if (option == JOptionPane.CANCEL_OPTION) {
			
			getBook().addSpreadSheet();
			JOptionPane.showMessageDialog(null, "Δώθηκαν default τιμές(60χ40");
		} else if (rows.getText().length() == 0 || cols.getText().length() == 0 || sheetName.getText().length() == 0) {
			getBook().addSpreadSheet();
			JOptionPane.showMessageDialog(null, "Δώθηκαν default τιμές(60x40)");
		}
	}

	public void newBook(WorkBook book) {

		if (this.workbook == null) {
			JTextField wbName = new JTextField();
			String info = "Δωσε ενα ονομα για το WorkBook αλλιως θα δωθει ενα προκαθορισμενο";
			Object[] message = { info, "Ονομα WorkBook:", wbName };

			Object[] options = { "Δημιουργία", "Default" };

			int option = JOptionPane.showOptionDialog(null, message, "Δωσε ενα ονομα για το WorkBook ",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (option == JOptionPane.OK_OPTION) {
				this.workbook = new WorkBook(this, wbName.getText());
			} else if (wbName.getText().length() == 0) {
				this.workbook = new WorkBook(this, "WorkBook1");
				JOptionPane.showMessageDialog(null, "Δωθηκε προκαθοριμενο ονομα");
			}

		} else {
			this.workbook = book;
		}

	}

	public void openWorkBook() {
		CSVFileReader aFileReader = new CSVFileReader();
		try {
			aFileReader.readFile(this);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean saveBook(boolean saveAs) {
		boolean ret;
		CSVFileGenerator aFileWriter = new CSVFileGenerator();
		ret = aFileWriter.createFile(this, true);
		return ret;
	}

	public WorkBook getBook() {
		return workbook;
	}

	public void setBook(WorkBook book) {
		this.workbook = book;
	}

	/**
	 * @return the toolbar
	 */
	public JToolBar getToolbar() {
		return toolbar;
	}

	/**
	 * @return the fbar
	 */
	public JPanel getFbar() {
		return fbar;
	}

	/**
	 * @return the formulaBar
	 */
	public JTextField getFormulaBar() {
		return formulaBar;
	}

	/**
	 * @return the barCaption
	 */
	public JLabel getBarCaption() {
		return barCaption;
	}

	public JMenuBar getmenuBar() {
		return menuBar;
	}

	/**
	 * @param barCaption
	 *            the barCaption to set
	 */
	public void setBarCaption(JLabel barCaption) {
		this.barCaption = barCaption;
	}
}
