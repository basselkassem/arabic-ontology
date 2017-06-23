package ProjectGUIInterfaces;
import SecondStage.*;
import aranlp.ANLP;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.border.*;

public class InsertTemplate extends JFrame{
    private TemplateManager tempManager;
    private Template temp;
    private JTextField tokens[];
    private JComboBox checkfun[];
    private JTextField rels[];
    private int condsNum;
    private GateOperations gateOp;
    private  aranlp.ANLP morph ;
    private  gate.Document doc;
	public InsertTemplate() {
            this.tempManager=new TemplateManager();
            this.temp=new Template();
            this.condsNum=0;
            this.initComponents();
            this.gateOp= new GateOperations();
            //this.gateOp.InitGate();
            this.morph=new ANLP();
            String input="الطويل من الرجال";
            this.doc = gateOp.CreateGateDoc(input);
	}

	private void PaseButtonActionPerformed(ActionEvent e) {
            String path=   System.getProperty ( "user.dir" )+System.getProperty ( "file.separator" )+"RuleStore"+System.getProperty ( "file.separator" )+"Temp"+System.getProperty ( "file.separator" )+"temp.jape";
             String pString="Phase: All\r\n" +
                        "Input:\r\n" +
                        "Options:\r\n";
            String res=ruleTextArea.getText();
            MyFileHandler.printToFile(pString+res,path,"utf-8");    
           
           
            gateOp.TokenizeGateDoc(doc);
            gateOp.MorphAnalyizeGateDoc(doc, morph);
            gateOp.SentenceSplitterGateDoc(doc);
            gateOp.POSAnalyizeGateDoc(doc, morph);
          
            try {
                parseResTextArea.setForeground(Color.GREEN);
                gateOp.ExcuteExpression(doc, "RuleStore/Temp/Temp.jape");
                parseResTextArea.setText("there are no errors");
                saveRuleButton.setEnabled(true);
             } 
            catch (ExecutionException ex) {
                parseResTextArea.setForeground(Color.RED);
                parseResTextArea.setText(ex.getCause().toString());
                saveRuleButton.setEnabled(false);
            }
             catch (ResourceInstantiationException ex) {
                 parseResTextArea.setForeground(Color.RED);
                 parseResTextArea.setText(ex.getCause().toString());
                 saveRuleButton.setEnabled(false);
            }
            catch (MalformedURLException ex) {
                parseResTextArea.setForeground(Color.RED);
                parseResTextArea.setText(ex.getCause().toString());
                saveRuleButton.setEnabled(false);
            }
           
            
          
	}

	private void saveRuleButtonActionPerformed(ActionEvent e) {
            RuleJAPE r=new RuleJAPE();
            String res=ruleTextArea.getText();
            if(res.compareTo("")!=0){
                r.setText(res);
                this.temp.setRuleJAPE(r);
                JFrame fram=new JFrame();
                    JOptionPane.showMessageDialog(fram,
                            "تم حفظ الملف",                            
                            "تمت العملية بنجاح",                             
                        JOptionPane.PLAIN_MESSAGE);
            
            }
            else{
                JOptionPane.showMessageDialog(new JFrame(),
                  " تأكد من كتابة اسم الوسمة",                          
                        "خطأ في كتابة القاعد ة ",                       
                        JOptionPane.ERROR_MESSAGE);
            }      
		
	}

	private void tokenButtonActionPerformed(ActionEvent e) {
            String res=tokenTextField.getText();
            if(res.compareTo("")!=0){
                ruleConfigDynamiqPanel = new JPanel();
                ruleConfigDynamiqPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                ruleConfigDynamiqPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)ruleConfigDynamiqPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                ((GridBagLayout)ruleConfigDynamiqPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)ruleConfigDynamiqPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
                ((GridBagLayout)ruleConfigDynamiqPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //---- tokensLabel ----
                tokensLabel.setText("\u0627\u0644\u0643\u0644\u0645\u0629");
                tokensLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
                tokensLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                ruleConfigDynamiqPanel.add(tokensLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- checkFuncLabel ----
                checkFuncLabel.setText("\u062a\u0627\u0628\u0639 \u0627\u0644\u0641\u062d\u0635");
                checkFuncLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
                checkFuncLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                ruleConfigDynamiqPanel.add(checkFuncLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));

                //---- relationLabel ----
                relationLabel.setText("\u0627\u0644\u0639\u0644\u0627\u0642\u0629 \u0627\u0644\u062f\u0644\u0627\u0644\u064a\u0629");
                relationLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
                relationLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                ruleConfigDynamiqPanel.add(relationLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 0), 0, 0));

                ruleConfigDynamiqScrollPanel.setViewportView(ruleConfigDynamiqPanel);
                this.condsNum=Integer.parseInt(res);
                initRuleConfigDynamiqPanel();
            }
            else{
                
            }
	}
        private void initRuleConfigDynamiqPanel(){
            tokens=new JTextField[condsNum];
            checkfun=new JComboBox[condsNum];
            rels=new JTextField[condsNum];
            for(int i= 0; i<this.condsNum;i++){
                //tokens
                tokens[i]=new JTextField("لا يوجد");
                tokens[i].setFont(new Font("Traditional Arabic", Font.BOLD, 14));
                tokens[i].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                ruleConfigDynamiqPanel.add(tokens[i], new GridBagConstraints(0, i+1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                //checkfun
                checkfun[i]=new JComboBox();
                buildCompoBox(checkfun[i]);
                checkfun[i].setFont(new Font("Traditional Arabic", Font.BOLD, 14));
                checkfun[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                ruleConfigDynamiqPanel.add(checkfun[i], new GridBagConstraints(1, i+1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                //relations
                rels[i]=new JTextField("لا يوجد");
                rels[i].setFont(new Font("Traditional Arabic", Font.BOLD, 14));
                rels[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                ruleConfigDynamiqPanel.add(rels[i], new GridBagConstraints(2, i+1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                
            }
           

            //---- checkFuncLabel ----
            checkFuncLabel.setText("\u062a\u0627\u0628\u0639 \u0627\u0644\u0641\u062d\u0635");
            checkFuncLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
            checkFuncLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            ruleConfigDynamiqPanel.add(checkFuncLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

            //---- relationLabel ----
            relationLabel.setText("\u0627\u0644\u0639\u0644\u0627\u0642\u0629 \u0627\u0644\u062f\u0644\u0627\u0644\u064a\u0629");
            relationLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
            relationLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            ruleConfigDynamiqPanel.add(relationLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
            //InsertTemplate.pack();
            ruleConfigDynamiqPanel.repaint();
	   // InsertTemplate.setLocationRelativeTo(InsertTemplate.getOwner());
        }
        private void buildCompoBox(JComboBox cb){
            String path=   System.getProperty ( "user.dir" )+System.getProperty ( "file.separator" )+"CheckFunctions"+System.getProperty ( "file.separator" )+"CheckFunctions.txt";
            String res=MyFileHandler.getText(path);    
            StringTokenizer st=new StringTokenizer(res, "\n");
            while(st.hasMoreTokens()){
                 cb.addItem(st.nextToken());
            }
           
        }
	private void insertRuleConfigButtonActionPerformed(ActionEvent e) {
            this.condsNum=Integer.parseInt(tokenTextField.getText());
            if(ruleNameTextField.getText().compareTo("")!=0){
                temp.getRuleConfig().setRuleName(ruleNameTextField.getText());
                if(conceptTextField.getText().compareTo("")!=0){
                    temp.getRuleConfig().setNewConcept(conceptTextField.getText());
                    if(meaningCountTextField.getText().compareTo("any")==0){
                        temp.getRuleConfig().setMeaningCount(-1);
                    }
                    else{
                        temp.getRuleConfig().setMeaningCount(Integer.parseInt(meaningCountTextField.getText()));
                    }
                    for(int i=0;i<this.condsNum;i++){
                        temp.getRuleConfig().getTokens().add(tokens[i].getText());
                        temp.getRuleConfig().getCheckFun().add(checkfun[i].getSelectedItem().toString());
                        temp.getRuleConfig().getRelation().add(rels[i].getText());
                    }
                }
            }
            tempManager.SaveTemplate(temp);
             JFrame fram=new JFrame();
                    JOptionPane.showMessageDialog(fram,
                            "تم حفظ الملف",                            
                            "تمت العملية بنجاح",                             
                        JOptionPane.PLAIN_MESSAGE);
            temp=new Template();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - bassel kassem
		InsertTemplate = new JFrame();
		rulePanelScrollPane = new JScrollPane();
		rulePanel = new JPanel();
		ruleScrollPane = new JScrollPane();
		ruleTextArea = new JTextArea();
		ruleParsePanel = new JPanel();
		PaseButton = new JButton();
		parseScrollPane = new JScrollPane();
		parseResTextArea = new JTextArea();
		saveRuleButton = new JButton();
                
		ruleConfigPanel = new JPanel();
		ruleConfigStaticPartScrollPane = new JScrollPane();
		ruleConfigStaticPartPanel = new JPanel();
		ruleNameLabel = new JLabel();
		ruleNameTextField = new JTextField();
		conceptLabel = new JLabel();
		conceptTextField = new JTextField();
		meaningCountLabel = new JLabel();
		meaningCountTextField = new JTextField();
		tokenLabel = new JLabel();
		tokenTextField = new JTextField();
		tokenButton = new JButton();
		ruleConfigDynamiqScrollPanel = new JScrollPane();
		ruleConfigDynamiqPanel = new JPanel();
		tokensLabel = new JLabel();
		checkFuncLabel = new JLabel();
		relationLabel = new JLabel();
		insertRuleConfigButton = new JButton();

		//======== InsertTemplate ========
		{
			InsertTemplate.setTitle("\u0625\u062f\u062e\u0627\u0644 \u0642\u0627\u0644\u0628 \u062c\u062f\u064a\u062f");
			InsertTemplate.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			Container InsertTemplateContentPane = InsertTemplate.getContentPane();
			InsertTemplateContentPane.setLayout(new GridBagLayout());
			((GridBagLayout)InsertTemplateContentPane.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)InsertTemplateContentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
			((GridBagLayout)InsertTemplateContentPane.getLayout()).columnWeights = new double[] {1.5, 1.0E-4};
			((GridBagLayout)InsertTemplateContentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

			//======== rulePanelScrollPane ========
			{

				//======== rulePanel ========
				{
					rulePanel.setBorder(new TitledBorder(new LineBorder(Color.blue, 2), "\u0625\u062f\u062e\u0627\u0644 \u0627\u0644\u0642\u0627\u0639\u062f\u0629", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
						new Font("Traditional Arabic", Font.BOLD, 18), Color.blue));
					rulePanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

					// JFormDesigner evaluation mark
//					rulePanel.setBorder(new javax.swing.border.CompoundBorder(
//						new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//							"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//							javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//							java.awt.Color.red), rulePanel.getBorder())); rulePanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

					rulePanel.setLayout(new GridBagLayout());
					((GridBagLayout)rulePanel.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)rulePanel.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)rulePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)rulePanel.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

					//======== ruleScrollPane ========
					{

						//---- ruleTextArea ----
						ruleTextArea.setFont(new Font("Courier New", Font.BOLD, 16));
						ruleScrollPane.setViewportView(ruleTextArea);
					}
					rulePanel.add(ruleScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== ruleParsePanel ========
					{
						ruleParsePanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleParsePanel.setLayout(new GridBagLayout());
						((GridBagLayout)ruleParsePanel.getLayout()).columnWidths = new int[] {0, 0, 0};
						((GridBagLayout)ruleParsePanel.getLayout()).rowHeights = new int[] {0, 0, 0};
						((GridBagLayout)ruleParsePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
						((GridBagLayout)ruleParsePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

						//---- PaseButton ----
						PaseButton.setText("\u0627\u062e\u062a\u0628\u0627\u0631 \u0635\u062d\u0629 \u0627\u0644\u0642\u0627\u0639\u062f\u0629");
						PaseButton.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						PaseButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								PaseButtonActionPerformed(e);
							}
						});
						ruleParsePanel.add(PaseButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//======== parseScrollPane ========
						{

							//---- parseResTextArea ----
							parseResTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
							parseResTextArea.setForeground(Color.green);
							parseScrollPane.setViewportView(parseResTextArea);
						}
						ruleParsePanel.add(parseScrollPane, new GridBagConstraints(1, 0, 1, 2, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));

						//---- saveRuleButton ----
						saveRuleButton.setText("\u062d\u0641\u0638 \u0627\u0644\u0642\u0627\u0639\u062f\u0629");
						saveRuleButton.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
                                                saveRuleButton.setEnabled(false);
						saveRuleButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								saveRuleButtonActionPerformed(e);
							}
						});
						ruleParsePanel.add(saveRuleButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
					}
					rulePanel.add(ruleParsePanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
                                rulePanelScrollPane.setAutoscrolls(true);
				rulePanelScrollPane.setViewportView(rulePanel);
			}
			InsertTemplateContentPane.add(rulePanelScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 5, 0), 0, 0));

			//======== ruleConfigPanel ========
			{
				ruleConfigPanel.setBorder(new TitledBorder(new LineBorder(Color.blue, 2), "\u0625\u062f\u062e\u0627\u0644 \u0627\u0644\u0634\u0631\u0648\u0637", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
					new Font("Traditional Arabic", Font.BOLD, 18), Color.blue));
				ruleConfigPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				ruleConfigPanel.setLayout(new GridBagLayout());
				((GridBagLayout)ruleConfigPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)ruleConfigPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
				((GridBagLayout)ruleConfigPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)ruleConfigPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

				//======== ruleConfigStaticPartScrollPane ========
				{

					//======== ruleConfigStaticPartPanel ========
					{
						ruleConfigStaticPartPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigStaticPartPanel.setBorder(null);
						ruleConfigStaticPartPanel.setLayout(new GridBagLayout());
						((GridBagLayout)ruleConfigStaticPartPanel.getLayout()).columnWidths = new int[] {0, 117, 129, 0};
						((GridBagLayout)ruleConfigStaticPartPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
						((GridBagLayout)ruleConfigStaticPartPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)ruleConfigStaticPartPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

						//---- ruleNameLabel ----
						ruleNameLabel.setText("\u0623\u062f\u062e\u0644 \u0627\u0633\u0645 \u0627\u0644\u0642\u0627\u0639\u062f\u0629");
						ruleNameLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						ruleNameLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigStaticPartPanel.add(ruleNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- ruleNameTextField ----
						ruleNameTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
						ruleConfigStaticPartPanel.add(ruleNameTextField, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- conceptLabel ----
						conceptLabel.setText("\u0623\u062f\u062e\u0644 \u0627\u0644\u0645\u0641\u0647\u0648\u0645 \u0627\u0644\u062c\u062f\u064a\u062f");
						conceptLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						conceptLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigStaticPartPanel.add(conceptLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- conceptTextField ----
						conceptTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
						ruleConfigStaticPartPanel.add(conceptTextField, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- meaningCountLabel ----
						meaningCountLabel.setText("\u0623\u062f\u062e\u0644 \u0639\u062f\u062f \u0643\u0644\u0645\u0627\u062a \u0627\u0644\u0645\u0639\u0646\u0649");
						meaningCountLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						meaningCountLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigStaticPartPanel.add(meaningCountLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- meaningCountTextField ----
						meaningCountTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
                                                meaningCountTextField.setText("any");
						ruleConfigStaticPartPanel.add(meaningCountTextField, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- tokenLabel ----
						tokenLabel.setText("أدخل عدد الشروط");
						tokenLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						tokenLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigStaticPartPanel.add(tokenLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- tokenTextField ----
						tokenTextField.setFont(new Font("Tahoma", Font.BOLD, 12));
						ruleConfigStaticPartPanel.add(tokenTextField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- tokenButton ----
						tokenButton.setText("\u0645\u0648\u0627\u0641\u0642");
						tokenButton.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						tokenButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						tokenButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								tokenButtonActionPerformed(e);
							}
						});
						ruleConfigStaticPartPanel.add(tokenButton, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					ruleConfigStaticPartScrollPane.setViewportView(ruleConfigStaticPartPanel);
				}
				ruleConfigPanel.add(ruleConfigStaticPartScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//======== ruleConfigDynamiqScrollPanel ========
				{

					//======== ruleConfigDynamiqPanel ========
					{
						ruleConfigDynamiqPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigDynamiqPanel.setLayout(new GridBagLayout());
						((GridBagLayout)ruleConfigDynamiqPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)ruleConfigDynamiqPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)ruleConfigDynamiqPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
						((GridBagLayout)ruleConfigDynamiqPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

						//---- tokensLabel ----
						tokensLabel.setText("\u0627\u0644\u0643\u0644\u0645\u0629");
						tokensLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						tokensLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigDynamiqPanel.add(tokensLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- checkFuncLabel ----
						checkFuncLabel.setText("\u062a\u0627\u0628\u0639 \u0627\u0644\u0641\u062d\u0635");
						checkFuncLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						checkFuncLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigDynamiqPanel.add(checkFuncLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- relationLabel ----
						relationLabel.setText("\u0627\u0644\u0639\u0644\u0627\u0642\u0629 \u0627\u0644\u062f\u0644\u0627\u0644\u064a\u0629");
						relationLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						relationLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ruleConfigDynamiqPanel.add(relationLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));
					}
					ruleConfigDynamiqScrollPanel.setViewportView(ruleConfigDynamiqPanel);
				}
				ruleConfigPanel.add(ruleConfigDynamiqScrollPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 5, 0), 0, 0));

				//---- insertRuleConfigButton ----
				insertRuleConfigButton.setText("\u062d\u0641\u0638 \u0627\u0644\u0634\u0631\u0648\u0637");
				insertRuleConfigButton.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
				insertRuleConfigButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				insertRuleConfigButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						insertRuleConfigButtonActionPerformed(e);
					}
				});
				ruleConfigPanel.add(insertRuleConfigButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			InsertTemplateContentPane.add(ruleConfigPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
                        InsertTemplate.setPreferredSize(new java.awt.Dimension(800, 750));
                        
			InsertTemplate.pack();
			InsertTemplate.setLocationRelativeTo(InsertTemplate.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - bassel kassem
	private JFrame InsertTemplate;
	private JScrollPane rulePanelScrollPane;
	private JPanel rulePanel;
	private JScrollPane ruleScrollPane;
	private JTextArea ruleTextArea;
	private JPanel ruleParsePanel;
	private JButton PaseButton;
	private JScrollPane parseScrollPane;
	private JTextArea parseResTextArea;
	private JButton saveRuleButton;
	private JPanel ruleConfigPanel;
	private JScrollPane ruleConfigStaticPartScrollPane;
	private JPanel ruleConfigStaticPartPanel;
	private JLabel ruleNameLabel;
	private JTextField ruleNameTextField;
	private JLabel conceptLabel;
	private JTextField conceptTextField;
	private JLabel meaningCountLabel;
	private JTextField meaningCountTextField;
	private JLabel tokenLabel;
	private JTextField tokenTextField;
	private JButton tokenButton;
	private JScrollPane ruleConfigDynamiqScrollPanel;
	private JPanel ruleConfigDynamiqPanel;
	private JLabel tokensLabel;
	private JLabel checkFuncLabel;
	private JLabel relationLabel;
	private JButton insertRuleConfigButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables


        
        public static void main(String args[]) {
            final InsertTemplate r=new InsertTemplate();

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    r.getInsertTemplate().setVisible(true);
                }
            });
    }

    public JFrame getInsertTemplate() {
        return InsertTemplate;
    }
}
