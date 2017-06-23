package ProjectGUIInterfaces;
import SecondStage.Template;
import SecondStage.TemplateManager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class EditTemplate extends JFrame {
    
	public EditTemplate(int i) {
		initComponents(i);
                
	}

	private void aplayTempButtonActionPerformed(ActionEvent e) {
            int tempId=Integer.parseInt(tempComboBox.getSelectedItem().toString());
            TemplateManager tm=new TemplateManager();
            Template t=tm.LoadTemplate(tempId);
            t.setTemplateId(tempId);
            final TemplateInteface intr=new TemplateInteface(t);
             java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    intr.getInsertTemplate().setVisible(true);
                }
            });
            
	}

	private void initComponents(int ii) {
		
		tempComboBox = new JComboBox();
		tempIdLabel = new JLabel();
		aplayTempButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("تحرير قالب");
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
                
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 183, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0, 1.0E-4};

		//---- tempComboBox ----
		tempComboBox.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
		tempComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		tempComboBox.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
                for(int i=1;i<=ii;i++){
                    tempComboBox.addItem(i);
                }
    		contentPane.add(tempComboBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//---- tempIdLabel ----
		tempIdLabel.setText("\u0627\u062e\u062a\u0631 \u0642\u0627\u0644\u0628\u0627\u064b");
		tempIdLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
		tempIdLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		tempIdLabel.setBorder(new LineBorder(Color.black, 1, true));
		contentPane.add(tempIdLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//---- aplayTempButton ----
		aplayTempButton.setText("تحرير");
		aplayTempButton.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
		aplayTempButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		aplayTempButton.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
		aplayTempButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aplayTempButtonActionPerformed(e);
			}
		});
		contentPane.add(aplayTempButton, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - bassel kassem
	private JComboBox tempComboBox;
	private JLabel tempIdLabel;
	private JButton aplayTempButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
