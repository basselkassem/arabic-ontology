package ProjectGUIInterfaces;
import java.awt.*;
import javax.swing.*;

public class ShowTemplets extends JFrame {
	public ShowTemplets(String s) {
		initComponents(s);
	}

	private void initComponents(String s) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - bassel kassem
		showScrollPane = new JScrollPane();
		showTempTtextArea = new JTextArea();

		//======== this ========
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0639\u0631\u0636 \u0645\u062d\u062a\u0648\u064a\u0627\u062a \u0645\u062e\u0632\u0646 \u0627\u0644\u0642\u0648\u0627\u0644\u0628");
                setPreferredSize(new java.awt.Dimension(400, 750));
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

		//======== showScrollPane ========
		{

			//---- showTempTtextArea ----
			showTempTtextArea.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
			showTempTtextArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			showTempTtextArea.setEditable(false);
                        showTempTtextArea.setText(s);
			showScrollPane.setViewportView(showTempTtextArea);
		}
		contentPane.add(showScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - bassel kassem
	private JScrollPane showScrollPane;
	private JTextArea showTempTtextArea;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
