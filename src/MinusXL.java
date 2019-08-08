import javax.swing.*;

import minusXL_view.*;

public class MinusXL {
	public static void main(String[] args) {

		View instance = new View(null);
		SwingUtilities.invokeLater(instance);
		instance.rowCol();

	}
}
