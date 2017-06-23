package ProjectGUIInterfaces;
import FirstStage.*;
import SecondStage.*;
import aranlp.ANLP;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
public class MainApplication  extends JFrame{
    private int size;
    private DBConnector databaseConn;
    private ConceptRelationsBuilder kernal;
    private ConceptsBuilder dataPreparer;
    private OWLOntologyBuilder ontoBuilder;
    private TemplateManager templateStore;
    private aranlp.ANLP morph;
    private String ontologyPath;
    private StringFormatter sf=new StringFormatter();
    public static void main(String args[]) {
       
        final MainApplication mainApp=new MainApplication();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainApp.form.setVisible(true);
            }
        });
    }
    public MainApplication(){
        morph=new ANLP();
        kernal=new ConceptRelationsBuilder();
        kernal.getGateAnalyzers().InitGate();
        kernal.setDicDBconn(databaseConn);
        kernal.setTemplateVec(templateStore);
       
        sf.SetMorphAnalyzer(morph);
        kernal.SetStringFormatter(sf);
        kernal.setConcepts(dataPreparer);
       
            // setup the look and feel properties
            Properties props = new Properties();
            props.put("logoString", "my company");
////            props.put("logoString", "\u00A0");
//            props.put("backgroundPattern", "off");
//
//            props.put("windowTitleForegroundColor", "228 228 255");
//            props.put("windowTitleBackgroundColor", "0 0 96");
//            props.put("windowTitleColorLight", "0 0 96");
//            props.put("windowTitleColorDark", "0 0 64");
//            props.put("windowBorderColor", "96 96 64");
//
//            props.put("windowInactiveTitleForegroundColor", "228 228 255");
//            props.put("windowInactiveTitleBackgroundColor", "0 0 96");
//            props.put("windowInactiveTitleColorLight", "0 0 96");
//            props.put("windowInactiveTitleColorDark", "0 0 64");
//            props.put("windowInactiveBorderColor", "32 32 128");
//
//            props.put("menuForegroundColor", "228 228 255");
//            props.put("menuBackgroundColor", "0 0 64");
//            props.put("menuSelectionForegroundColor", "0 0 0");
//            props.put("menuSelectionBackgroundColor", "255 192 48");
//            props.put("menuColorLight", "32 32 128");
//            props.put("menuColorDark", "16 16 96");
//
//            props.put("toolbarColorLight", "32 32 128");
//            props.put("toolbarColorDark", "16 16 96");
//
//            props.put("controlForegroundColor", "228 228 255");
//            props.put("controlBackgroundColor", "16 16 96");
//            props.put("controlColorLight", "16 16 96");
//            props.put("controlColorDark", "8 8 64");
//            props.put("controlHighlightColor", "32 32 128");
//            props.put("controlShadowColor", "16 16 64");
//            props.put("controlDarkShadowColor", "8 8 32");
//
//            props.put("buttonForegroundColor", "0 0 32");
//            props.put("buttonBackgroundColor", "196 196 196");
//            props.put("buttonColorLight", "196 196 240");
//            props.put("buttonColorDark", "164 164 228");
//
//            props.put("foregroundColor", "228 228 255");
//            props.put("backgroundColor", "0 0 64");
//            props.put("backgroundColorLight", "16 16 96");
//            props.put("backgroundColorDark", "8 8 64");
//            props.put("alterBackgroundColor", "255 0 0");
//            props.put("frameColor", "96 96 64");
//            props.put("gridColor", "96 96 64");
//            props.put("focusCellColor", "240 0 0");
//
//            props.put("disabledForegroundColor", "128 128 164");
//            props.put("disabledBackgroundColor", "0 0 72");
//
//            props.put("selectionForegroundColor", "0 0 0");
//            props.put("selectionBackgroundColor", "196 148 16");
//
//            props.put("inputForegroundColor", "228 228 255");
//            props.put("inputBackgroundColor", "0 0 96");
//
//            props.put("rolloverColor", "240 168 0");
//            props.put("rolloverColorLight", "240 168 0");
//            props.put("rolloverColorDark", "196 137 0");
//  AluminiumLookAndFeel
            //GraphiteLookAndFeel
       com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green-Large-Font", "Bassel", "AlSheekhKassem");
//            // set your theme
          //com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme("Giant-Font", "Bassel", "AlSheekhKassem");
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
            
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                     UIManager.setLookAndFeel ( info.getClassName() );
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        this.initComponents();
    }
    

	private void exitPanelAncestorAdded(AncestorEvent e) {
            System.exit(1);
	}
	private void testConnButtonActionPerformed(ActionEvent e) {
            testConnLabel.setText("");
            databaseConn =new DBConnector("jdbc:mysql://"+serverText.getText()+":"+portText.getText()+"/",databaseText.getText() ,"com.mysql.jdbc.Driver", userText.getText(), passwordText.getText());
            boolean testConn=databaseConn.TestConnection();
            if(testConn==true){
                testConnLabel.setText("تم الاتصال بنجاح..");
                tableText.setEnabled(true);
                allDatabaseCheckBox.setEnabled(true);
                recordNumText.setEnabled(true);

            }
            else {
                JFrame fram=new JFrame();
                JOptionPane.showMessageDialog(fram,
                        databaseConn.getMessage(),
                            "خطأ في الاتصال",                       
                        JOptionPane.ERROR_MESSAGE);
            }
        }
	private void showDataButtonActionPerformed(ActionEvent e) {
            databaseConn.GetData(tableText.getText());
            dataPreparer= new ConceptsBuilder(databaseConn.getQueryDB(),size);
            dataPreparer.PreBuildConceptVec();
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new DicData(dataPreparer.getClassNodeVec()).setVisible(true);
                }
            });
            startPreparingButton.setEnabled(true);
            showPreparedDataButton.setEnabled(true);
        }
	private void allDatabaseCheckBoxActionPerformed(ActionEvent e) {
            showDataButton.setEnabled(true);
            size=databaseConn.GetDataBaseSize(tableText.getText());
            if(allDatabaseCheckBox.isSelected()){
                recordNumText.setEnabled(false);
                showDataButton.setEnabled(true);
            }
            else{
                recordNumText.setEnabled(true);
                showDataButton.setEnabled(false);
            }
	}
	private void recordNumTextKeyReleased(KeyEvent e) {
            String res=recordNumText.getText();
            if(res.compareTo("")!=0)
            {
                size=Integer.parseInt(res);
                showDataButton.setEnabled(true);
            }
            else
            {
                JFrame fram=new JFrame();
                JOptionPane.showMessageDialog(fram,
                        "أدخل عدد التسجيلات",                       
                            " عدد التسجيلات",                       
                        JOptionPane.WARNING_MESSAGE);
            }
	}
	private void startPreparingButtonActionPerformed(ActionEvent e) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    PrepareDataProgressBar.createAndShowGUI(dataPreparer);
                }
            });
	}
	private void showPreparedDataButtonActionPerformed(ActionEvent e) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new prepareDicData(dataPreparer.getClassNodeVec()).setVisible(true);
                }
            });
	}
	private void ontoSaveButtonActionPerformed(ActionEvent e) {
            String path="";
            FileSystemView s= FileSystemView.getFileSystemView();
            final JFileChooser fc = new JFileChooser(System.getProperty ( "user.dir" ) + System.getProperty ( "file.separator" ).toString(),s);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileFilter ff=new FileNameExtensionFilter("OWL Files", "owl");
            fc.setFileFilter(ff);
            int retu=fc.showDialog(fc,"حفظ");
            if(fc.getSelectedFile()!=null){
                path=fc.getSelectedFile().getPath();//+System.getProperty ( "file.separator" ).toString();
                if(path.compareTo("")==0) {
                    savedOntoPathTextField.setText("Enter the path of Input Files");
                }
                else {
                    savedOntoPathTextField.setText(path);
                    ontologyPath=path;
                }
            }
            String ontoName=ontoNameTextField.getText();
            if(ontoName.compareTo("")==0||ontoName.compareTo("http://")==0){
                JOptionPane.showMessageDialog(new JFrame(),
                        "أدخل اسم صحيح للأنطولوجيا",                        
                            "اسم الأنظولوجيا",                       
                        JOptionPane.WARNING_MESSAGE);
            }
            else{
                if(retu==JFileChooser.APPROVE_OPTION)
                {
                    ontoBuilder=new OWLOntologyBuilder(ontoName, path);
                    ontoBuilder.OntoCreateProtoType(dataPreparer.getClassNodeVec());
                    ontoBuilder.OntoSave(path);
                    String res="";
                    res+="اسم الأنطولوجيا:   ";
                    res+=ontoBuilder.getOntologyIRI().toQuotedString()+"\r\n";
                    res+="مسار الأنطولوجيا:   ";
                    res+="from :" + ontoBuilder.getDocumentIRI().toQuotedString()+"\r\n";
                    res+="عدد المفاهيم:   ";
                    res+=ontoBuilder.getOntology().getClassesInSignature().size()+"\r\n";
                    res+="عدد المستنسخات:   ";
                    res+=ontoBuilder.getOntology().getIndividualsInSignature().size()+"\r\n";
                    savedOntoStatTextArea.setText(res);
                    OWLOntologyModifier temp=new OWLOntologyModifier();
                    temp.setStrFormatter(sf);
                    kernal.setOntoModifier(temp);
                    kernal.getOntoModifier().OntoLoad(ontologyPath);
                }
            }
                
	}
	private void modifyOntoPanelAncestorAdded(AncestorEvent e) {
            templateStore= new TemplateManager();
            tempNumButton.setText(templateStore.getTemplateNum()+"");
            tempComboBox.removeAllItems();
            for(int i=1;i<=templateStore.getTemplateNum();i++){
                tempComboBox.addItem(i);
            }
            tempComboBox.addItem("الكل");
	}
	private void tempNumButtonActionPerformed(ActionEvent e) {
            tempComboBox.removeAllItems();
            templateStore.LoadTemplateNum();
            tempNumButton.setText(templateStore.getTemplateNum()+"");
            for(int i=1;i<=templateStore.getTemplateNum();i++){
                tempComboBox.addItem(i);
            }
            tempComboBox.addItem("الكل");
	}
	private void createNewTempMenuItemActionPerformed(ActionEvent e) {
            final InsertTemplate r =new InsertTemplate();
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                   r.getInsertTemplate().setVisible(true);
                }
            });
	}
        private void editTemplateItemActionPerformed(ActionEvent e) {
           final EditTemplate t=new EditTemplate(templateStore.getTemplateNum());
           java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    t.setVisible(true);
                }
            });
        }
	private void deleteTempMenuItemActionPerformed(ActionEvent e) {
            
            FileSystemView s= FileSystemView.getFileSystemView();
            final JFileChooser fc = new JFileChooser(System.getProperty ( "user.dir" ) + System.getProperty ( "file.separator" )+"RuleStore"+System.getProperty ( "file.separator" ).toString(),s);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileFilter ff=new FileNameExtensionFilter("JAPE Rules", "jape");
            fc.addChoosableFileFilter(ff);
            int reVal=fc.showDialog(fc,"حذف");
            if (reVal == JFileChooser.APPROVE_OPTION) {
                
                File file = fc.getSelectedFile();
                String path=file.getAbsolutePath();
                String ext=".jape";
                String confFile=path.replaceAll(ext, "Config.txt");
                File fileConfig= new File(confFile);
                file.delete();
                fileConfig.delete();
                String ruleId=System.getProperty ( "user.dir" ) + System.getProperty ( "file.separator" )+"RuleStore"+System.getProperty ( "file.separator" )+"RuleIds.txt";
                String res=MyFileHandler.getText(ruleId,"utf8");
                String replace = res.replace("\n", "");
                int templateNum=Integer.parseInt(replace);
                templateNum--;
                MyFileHandler.printToFile(templateNum+"", ruleId);                 
            }  
	}
	private void loadTempMenuItemActionPerformed(ActionEvent e) {
            templateStore=new TemplateManager();
            templateStore.LoadTemplateStore();
            String res="";
            res+="عدد القوالب: "+templateStore.getTemplateNum()+"\r\n";
            res+="--------------------------------------------\r\n";
            for(int i=0; i<templateStore.getTemplateVec().size();i++){
                res+="القالب رقم: "+(i+1)+"\r\n";
                res+=templateStore.getTemplateVec().elementAt(i).getTemplate()+"\r\n";
                res+="--------------------------------------------\r\n";
           
            }
            final ShowTemplets r = new ShowTemplets(res);
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                  r.setVisible(true);
                }
            });
	}
	private void exitMenuItemActionPerformed(ActionEvent e) {
            System.exit(1);
	}
	private void helpMenuItemActionPerformed(ActionEvent e) {
		// TODO add your code here
	}
	private void aboutMenuItemActionPerformed(ActionEvent e) {
		// TODO add your code here
	}
	private void aplayTempButtonActionPerformed(ActionEvent e) {
            
            if(tempComboBox.getSelectedItem().toString().compareTo("الكل")==0){
                templateStore.LoadTemplateStore();
                kernal.setTemplateVec(templateStore);
                kernal.setDicDBconn(databaseConn);
                kernal.setConcepts(dataPreparer);
                
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                      CreateGateDocsProgressBar.createAndShowGUI(kernal);
                    }
                });
              
            }
            else{
                int tempId=tempComboBox.getSelectedIndex()+1;
                templateStore.LoadTemplateId(tempId);
                kernal.setTemplateVec(templateStore);
                kernal.setDicDBconn(databaseConn);
                kernal.setConcepts(dataPreparer);
//               if(kernal.getOntoModifier()==null) {
                    //kernal.getOntoModifier().OntoLoad(ontologyPath);
//                }
               
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                      CreateGateDocsProgressBar.createAndShowGUI(kernal);
                    }
                });
            }
             meaningAfterButton.setEnabled(true);
             meaningBeforButton.setEnabled(true);
             enrichButton.setEnabled(true);
             ontoSaveButton2.setEnabled(true);
	}
	private void meaningBeforButtonActionPerformed(ActionEvent e) {
            kernal.setDicDBconn(databaseConn);
            kernal.setConcepts(dataPreparer);
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    CreateGateDocsBeforeRuleProgressBar.createAndShowGUI(kernal);
                }
            });
	}
	private void meaningAfterButtonActionPerformed(ActionEvent e) {
             java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MeaningsBeforeAfterRuleAnno(kernal.getMatchedConc(), kernal.getDocVecAfterAnoo(),"معاني المعجم بعد الوسم بخرج القوالب").setVisible(true);
                }
            });
	}
	private void enrichButtonActionPerformed(ActionEvent e) {
            String path="";
            FileSystemView s= FileSystemView.getFileSystemView();
            final JFileChooser fc = new JFileChooser(ontologyPath,s);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileFilter ff=new FileNameExtensionFilter("OWL Files", "owl");
            fc.setFileFilter(ff);
            int retu= fc.showDialog(fc,"اختر ملف");
            if(fc.getSelectedFile()!=null){
                path=fc.getSelectedFile().getPath();//+System.getProperty ( "file.separator" ).toString();
                if(path.compareTo("")==0) {
                    savedOntoPathTextField2.setText("أدخل مسار الأنطولوجيا");
                }
                else {
                    if(retu==JFileChooser.APPROVE_OPTION){
                        savedOntoPathTextField2.setText(path);
                        ontologyPath=path;
                    }
                }
            }
            OWLOntologyModifier temp=new OWLOntologyModifier();
            temp.setStrFormatter(sf);
            kernal.setOntoModifier(temp);
            kernal.getOntoModifier().OntoLoad(ontologyPath);
            final String res = null;
            java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                      EnrichOntoProgressBar.createAndShowGUI(kernal, res);
                    }
                });
           
            
            
	}
	private void ontoSaveButton2ActionPerformed(ActionEvent e) {
            String path="";
            FileSystemView s= FileSystemView.getFileSystemView();
            final JFileChooser fc = new JFileChooser(System.getProperty ( "user.dir" ) + System.getProperty ( "file.separator" ).toString(),s);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileFilter ff=new FileNameExtensionFilter("OWL Files", "owl");
            fc.setFileFilter(ff);
            int retu=fc.showDialog(fc,"حفظ");
            if(fc.getSelectedFile()!=null){
                path=fc.getSelectedFile().getPath();//+System.getProperty ( "file.separator" ).toString();
                 if(path.compareTo("")==0) {
                    savedOntoPathTextField2.setText("أدخل مسار الأنطولوجيا");
                }
                else {
                      if(retu==JFileChooser.APPROVE_OPTION){
                        savedOntoPathTextField2.setText(path);
                        ontologyPath=path;
                        kernal.getOntoModifier().OntoSave(path);
                      }
                }
            }
        
            
            String res = "";
               res+="اسم الأنطولوجيا:   ";
            res+= kernal.getOntoModifier().getOntologyIRI().toQuotedString()+"\r\n";
            res+="مسار الأنطولوجيا:   ";
            res+="from :" +  kernal.getOntoModifier().getDocumentIRI().toQuotedString()+"\r\n";
            res+="عدد المفاهيم:   ";
            res+= kernal.getOntoModifier().getOntology().getClassesInSignature().size()+"\r\n";
            res+="عدد المستنسخات:   ";
            res+= kernal.getOntoModifier().getOntology().getIndividualsInSignature().size()+"\r\n";
            savedOntoStatTextArea2.setText(res);
            savedOntoStatTextArea2.append("تمت عملية حفظ الأنطولوجيا بنجاح\r\n");
            savedOntoStatTextArea2.append(path+"\r\n");
	}
        private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - bassel kassem
		form = new JFrame();
		mainTabbedPanel = new JTabbedPane();
		databasePanel = new JPanel();
		DatabaseConn = new JPanel();
		severLabel = new JLabel();
		serverText = new JTextField();
		portLabel = new JLabel();
		portText = new JTextField();
		databaseLabel = new JLabel();
		databaseText = new JTextField();
		userLabel = new JLabel();
		userText = new JTextField();
		passwodLabel = new JLabel();
		passwordText = new JPasswordField();
		testConnButton = new JButton();
		testConnLabel = new JLabel();
		tabelLabel = new JLabel();
		tableText = new JTextField();
		recordNumLabel = new JLabel();
		allDatabaseCheckBox = new JCheckBox();
		recordNumText = new JTextField();
		showDataButton = new JButton();
		prepareDataPanel = new JPanel();
		startPreparingButton = new JButton();
		showPreparedDataButton = new JButton();
		firstOntoPanel = new JPanel();
		ontoNameLabel = new JLabel();
		ontoNameTextField = new JTextField();
		ontoSaveButton = new JButton();
		savedOntoPathTextField = new JTextField();
		savedOntoStatScrollPane = new JScrollPane();
		savedOntoStatTextArea = new JTextArea();
		modifyOntoPanel = new JPanel();
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		createNewTempMenuItem = new JMenuItem();
                editTemplateItem=new JMenuItem();
		deleteTempMenuItem = new JMenuItem();
		loadTempMenuItem = new JMenuItem();
		exitMenuItem = new JMenuItem();
		aboutMenu = new JMenu();
		helpMenuItem = new JMenuItem();
		aboutMenuItem = new JMenuItem();
		tempNumPanel = new JPanel();
		tempNumLabel = new JLabel();
		tempNumButton = new JButton();
		aplayTempPanel = new JPanel();
		tempIdLabel = new JLabel();
		tempComboBox = new JComboBox();
		aplayTempButton = new JButton();
		meaningBeforButton = new JButton();
		meaningAfterButton = new JButton();
		secondOntoPanel = new JPanel();
		enrichButton = new JButton();
		ontoSaveButton2 = new JButton();
		savedOntoPathTextField2 = new JTextField();
		savedOntoStatScrollPane2 = new JScrollPane();
		savedOntoStatTextArea2 = new JTextArea();
		exitPanel = new JPanel();

		//======== form ========
		{
                       // form.set
			form.setFont(new Font("Traditional Arabic", Font.BOLD, 16));                        
			form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			form.setTitle("\u0628\u0646\u0627\u0621 \u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627 \u0645\u0646 \u0627\u0644\u0645\u0639\u062c\u0645 \u0627\u0644\u0639\u0631\u0628\u064a \u0627\u0644\u062a\u0641\u0627\u0639\u0644\u064a");
                        form.setPreferredSize(new java.awt.Dimension(750, 750));
			form.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			Container formContentPane = form.getContentPane();
			formContentPane.setLayout(new GridBagLayout());
			((GridBagLayout)formContentPane.getLayout()).columnWidths = new int[] {0, 0};
			((GridBagLayout)formContentPane.getLayout()).rowHeights = new int[] {0, 0};
			((GridBagLayout)formContentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
			((GridBagLayout)formContentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

			//======== mainTabbedPanel ========
			{
				mainTabbedPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				mainTabbedPanel.setFont(new Font("Traditional Arabic", Font.BOLD, 20));
				mainTabbedPanel.setForeground(new Color(0, 13, 191));

				//======== databasePanel ========
				{
					databasePanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					databasePanel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));

					databasePanel.setLayout(new GridBagLayout());
					((GridBagLayout)databasePanel.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)databasePanel.getLayout()).rowHeights = new int[] {0, 0, 0};
					((GridBagLayout)databasePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)databasePanel.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

					//======== DatabaseConn ========
					{
						DatabaseConn.setBorder(new TitledBorder(new LineBorder(Color.blue, 2, true), "\u0627\u0644\u0627\u062a\u0635\u0627\u0644 \u0645\u0639 \u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0645\u0639\u0637\u064a\u0627\u062a", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
							new Font("Traditional Arabic", Font.BOLD, 16), Color.blue));
						DatabaseConn.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						DatabaseConn.setLayout(new GridBagLayout());
						((GridBagLayout)DatabaseConn.getLayout()).columnWidths = new int[] {75, 329, 48, 83, 0};
						((GridBagLayout)DatabaseConn.getLayout()).rowHeights = new int[] {36, 0, 0, 0, 0, 0, 0, 0, 0, 0};
						((GridBagLayout)DatabaseConn.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)DatabaseConn.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

						//---- severLabel ----
						severLabel.setText("\u0639\u0646\u0648\u0627\u0646 \u0627\u0644\u0645\u062e\u062f\u0645");
						severLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						severLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						severLabel.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(severLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- serverText ----
						serverText.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(serverText, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- portLabel ----
						portLabel.setText("\u0631\u0642\u0645 \u0627\u0644\u0628\u0648\u0627\u0628\u0629");
						portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						portLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						portLabel.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(portLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- portText ----
						portText.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(portText, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- databaseLabel ----
						databaseLabel.setText("\u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0645\u0639\u0637\u064a\u0627\u062a");
						databaseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						databaseLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						databaseLabel.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(databaseLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- databaseText ----
						databaseText.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(databaseText, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- userLabel ----
						userLabel.setText("\u0627\u0633\u0645 \u0627\u0644\u0645\u0633\u062a\u062e\u062f\u0645");
						userLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						userLabel.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(userLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- userText ----
						userText.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(userText, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- passwodLabel ----
						passwodLabel.setText("\u0643\u0644\u0645\u0629 \u0627\u0644\u0633\u0631");
						passwodLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						passwodLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						passwodLabel.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(passwodLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- passwordText ----
						passwordText.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(passwordText, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- testConnButton ----
						testConnButton.setText("\u0627\u062e\u062a\u0628\u0627\u0631 \u0627\u0644\u0627\u062a\u0635\u0627\u0644");
						testConnButton.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						testConnButton.setBorder(new LineBorder(new Color(0, 145, 237), 1, true));
						testConnButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								testConnButtonActionPerformed(e);
							}
						});
						DatabaseConn.add(testConnButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- testConnLabel ----
						testConnLabel.setText("..");
						testConnLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						testConnLabel.setForeground(Color.green);
						testConnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						DatabaseConn.add(testConnLabel, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- tabelLabel ----
						tabelLabel.setText("\u0623\u062f\u062e\u0644 \u0627\u0644\u062c\u062f\u0648\u0644");
						tabelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						tabelLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						tabelLabel.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(tabelLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- tableText ----
						tableText.setEnabled(false);
						tableText.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(tableText, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- recordNumLabel ----
						recordNumLabel.setText("\u0623\u062f\u062e\u0644 \u0639\u062f\u062f \u0627\u0644\u062a\u0633\u062c\u064a\u0644\u0627\u062a");
						recordNumLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						recordNumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
						recordNumLabel.setBorder(new LineBorder(Color.black, 1, true));
						DatabaseConn.add(recordNumLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- allDatabaseCheckBox ----
						allDatabaseCheckBox.setText("\u0643\u0627\u0645\u0644 \u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0645\u0639\u0637\u064a\u0627\u062a");
						allDatabaseCheckBox.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						allDatabaseCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
						allDatabaseCheckBox.setEnabled(false);
						allDatabaseCheckBox.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								allDatabaseCheckBoxActionPerformed(e);
							}
						});
						DatabaseConn.add(allDatabaseCheckBox, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
							GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- recordNumText ----
						recordNumText.setEnabled(false);
						recordNumText.setBorder(new LineBorder(Color.black, 1, true));
						recordNumText.addKeyListener(new KeyAdapter() {
							@Override
							public void keyReleased(KeyEvent e) {
								recordNumTextKeyReleased(e);
							}
						});
						DatabaseConn.add(recordNumText, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- showDataButton ----
						showDataButton.setText("\u0625\u0638\u0647\u0627\u0631 \u0627\u0644\u0645\u0639\u0637\u064a\u0627\u062a");
						showDataButton.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						showDataButton.setEnabled(false);
						showDataButton.setBorder(new LineBorder(new Color(0, 145, 237), 1, true));
						showDataButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								showDataButtonActionPerformed(e);
							}
						});
						DatabaseConn.add(showDataButton, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
					}
					databasePanel.add(DatabaseConn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== prepareDataPanel ========
					{
						prepareDataPanel.setBorder(new CompoundBorder(
							new TitledBorder(new LineBorder(Color.blue, 2, true), "\u062a\u062d\u0636\u064a\u0631 \u0627\u0644\u0645\u0639\u0637\u064a\u0627\u062a", TitledBorder.CENTER, TitledBorder.TOP,
								new Font("Traditional Arabic", Font.BOLD, 18), Color.blue),
							new BevelBorder(BevelBorder.RAISED)));
						prepareDataPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						prepareDataPanel.setLayout(new GridBagLayout());
						((GridBagLayout)prepareDataPanel.getLayout()).columnWidths = new int[] {178, 0, 169, 0};
						((GridBagLayout)prepareDataPanel.getLayout()).rowHeights = new int[] {70, 0, 0};
						((GridBagLayout)prepareDataPanel.getLayout()).columnWeights = new double[] {0.1, 0.1, 0.0, 1.0E-4};
						((GridBagLayout)prepareDataPanel.getLayout()).rowWeights = new double[] {0.5, 0.5, 1.0E-4};

						//---- startPreparingButton ----
						startPreparingButton.setText("\u0627\u0628\u062f\u0623 \u062a\u062d\u0636\u064a\u0631 \u0627\u0644\u0645\u0639\u0637\u064a\u0627\u062a");
						startPreparingButton.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						startPreparingButton.setEnabled(false);
						startPreparingButton.setBorder(new LineBorder(new Color(0, 145, 237), 1, true));
						startPreparingButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								startPreparingButtonActionPerformed(e);
							}
						});
						prepareDataPanel.add(startPreparingButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- showPreparedDataButton ----
						showPreparedDataButton.setText("\u0625\u0638\u0647\u0627\u0631 \u0627\u0644\u0645\u0639\u0637\u064a\u0627\u062a");
						showPreparedDataButton.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						showPreparedDataButton.setEnabled(false);
						showPreparedDataButton.setBorder(new LineBorder(new Color(0, 145, 237), 1, true));
						showPreparedDataButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								showPreparedDataButtonActionPerformed(e);
							}
						});
						prepareDataPanel.add(showPreparedDataButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
//                                                JLabel t=new JLabel("-------");
//                                                prepareDataPanel.add(t,new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
//							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//							new Insets(0, 0, 0, 5), 0, 0));
					}
					databasePanel.add(prepareDataPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				mainTabbedPanel.addTab("\u0645\u0631\u062d\u0644\u0640\u0640\u0629 \u062a\u062d\u0636\u064a\u0631 \u0627\u0644\u0645\u0639\u0637\u064a\u0627\u062a", databasePanel);


				//======== firstOntoPanel ========
				{
					firstOntoPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					firstOntoPanel.setBorder(new TitledBorder(new LineBorder(Color.blue, 2, true), "\u0628\u0646\u0627\u0621 \u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627 \u0623\u0648\u0644\u064a\u0629", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
						new Font("Traditional Arabic", Font.BOLD, 16), Color.blue));
					firstOntoPanel.setLayout(new GridBagLayout());
					((GridBagLayout)firstOntoPanel.getLayout()).columnWidths = new int[] {136, 201, 84, 201, 0};
					((GridBagLayout)firstOntoPanel.getLayout()).rowHeights = new int[] {0, 0, 184, 124, 0};
					((GridBagLayout)firstOntoPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
					((GridBagLayout)firstOntoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};

					//---- ontoNameLabel ----
					ontoNameLabel.setText("\u0623\u062f\u062e\u0644 \u0627\u0633\u0645 \u0627\u0644\u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627");
					ontoNameLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					ontoNameLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
					ontoNameLabel.setBorder(new LineBorder(Color.black, 1, true));
					firstOntoPanel.add(ontoNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- ontoNameTextField ----
					ontoNameTextField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
					ontoNameTextField.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
					ontoNameTextField.setText("http://");
					ontoNameTextField.setBorder(new LineBorder(Color.black, 1, true));
					firstOntoPanel.add(ontoNameTextField, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- ontoSaveButton ----
					ontoSaveButton.setText("\u0628\u0646\u0627\u0621 \u0648\u062d\u0641\u0638 \u0627\u0644\u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627");
					ontoSaveButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					ontoSaveButton.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
					ontoSaveButton.setBorder(new LineBorder(new Color(0, 154, 244), 1, true));
					ontoSaveButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ontoSaveButtonActionPerformed(e);
						}
					});
					firstOntoPanel.add(ontoSaveButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//---- savedOntoPathTextField ----
					savedOntoPathTextField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
					savedOntoPathTextField.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
					savedOntoPathTextField.setEditable(false);
					savedOntoPathTextField.setBorder(new LineBorder(Color.black, 1, true));
					firstOntoPanel.add(savedOntoPathTextField, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== savedOntoStatScrollPane ========
					{
						savedOntoStatScrollPane.setBorder(new TitledBorder(new EtchedBorder(Color.blue, Color.blue), "\u0645\u0639\u0644\u0648\u0645\u0627\u062a \u0639\u0646 \u0627\u0644\u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
							new Font("Traditional Arabic", Font.BOLD, 16), Color.blue));

						//---- savedOntoStatTextArea ----
						savedOntoStatTextArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						savedOntoStatTextArea.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						savedOntoStatTextArea.setEditable(false);
						savedOntoStatTextArea.setBorder(new LineBorder(Color.black, 1, true));
						savedOntoStatScrollPane.setViewportView(savedOntoStatTextArea);
					}
					firstOntoPanel.add(savedOntoStatScrollPane, new GridBagConstraints(0, 2, 4, 2, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				mainTabbedPanel.addTab("\u0645\u0631\u062d\u0644\u0629 \u0628\u0646\u0627\u0621 \u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627 \u0623\u0648\u0644\u064a\u0640\u0640\u0629", firstOntoPanel);


				//======== modifyOntoPanel ========
				{
					modifyOntoPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					modifyOntoPanel.addAncestorListener(new AncestorListener() {
						@Override
						public void ancestorMoved(AncestorEvent e) {}
						@Override
						public void ancestorAdded(AncestorEvent e) {
							modifyOntoPanelAncestorAdded(e);
						}
						@Override
						public void ancestorRemoved(AncestorEvent e) {}
					});
					modifyOntoPanel.setLayout(new GridBagLayout());
					((GridBagLayout)modifyOntoPanel.getLayout()).columnWidths = new int[] {0, 0};
					((GridBagLayout)modifyOntoPanel.getLayout()).rowHeights = new int[] {0, 24, 0, 368, 0};
					((GridBagLayout)modifyOntoPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
					((GridBagLayout)modifyOntoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};

					//======== menuBar ========
					{
						menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						menuBar.setFont(new Font("Traditional Arabic", Font.BOLD, 14));

						//======== fileMenu ========
						{
							fileMenu.setText("\u0645\u0644\u0641");
							fileMenu.setFont(new Font("Traditional Arabic", Font.BOLD, 18));

							//---- createNewTempMenuItem ----
							createNewTempMenuItem.setText("\u0625\u0646\u0634\u0627\u0621 \u0642\u0627\u0644\u0628 \u062c\u062f\u064a\u062f");
							createNewTempMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
							createNewTempMenuItem.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
							createNewTempMenuItem.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									createNewTempMenuItemActionPerformed(e);
								}
							});
							fileMenu.add(createNewTempMenuItem);
                                                        //===editMenu==============
                                                        editTemplateItem.setText("تحرير قالب");
							editTemplateItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
							editTemplateItem.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
							editTemplateItem.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									editTemplateItemActionPerformed(e);
								}
							});
                                                        fileMenu.add(editTemplateItem);
							//---- deleteTempMenuItem ----
							deleteTempMenuItem.setText("\u062d\u0630\u0641 \u0642\u0627\u0644\u0628");
							deleteTempMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
							deleteTempMenuItem.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
							deleteTempMenuItem.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									deleteTempMenuItemActionPerformed(e);
								}
							});
							fileMenu.add(deleteTempMenuItem);

							//---- loadTempMenuItem ----
							loadTempMenuItem.setText("\u062a\u062d\u0645\u064a\u0644 \u0627\u0644\u0642\u0648\u0627\u0644\u0628");
							loadTempMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
							loadTempMenuItem.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
							loadTempMenuItem.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									loadTempMenuItemActionPerformed(e);
								}
							});
							fileMenu.add(loadTempMenuItem);

							//---- exitMenuItem ----
							exitMenuItem.setText("\u062e\u0631\u0648\u062c");
							exitMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
							exitMenuItem.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
							exitMenuItem.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									exitMenuItemActionPerformed(e);
								}
							});
							fileMenu.add(exitMenuItem);
						}
						menuBar.add(fileMenu);

						//======== aboutMenu ========
						{
							aboutMenu.setText("\u0645\u0633\u0627\u0639\u062f\u0629");
							aboutMenu.setFont(new Font("Traditional Arabic", Font.BOLD, 18));

							//---- helpMenuItem ----
							helpMenuItem.setText("\u0645\u0633\u0627\u0639\u062f\u0629");
							helpMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
							helpMenuItem.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
							helpMenuItem.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									helpMenuItemActionPerformed(e);
								}
							});
							aboutMenu.add(helpMenuItem);

							//---- aboutMenuItem ----
							aboutMenuItem.setText("\u062d\u0648\u0644");
							aboutMenuItem.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
							aboutMenuItem.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
							aboutMenuItem.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									aboutMenuItemActionPerformed(e);
								}
							});
							aboutMenu.add(aboutMenuItem);
						}
						menuBar.add(aboutMenu);
					}
					modifyOntoPanel.add(menuBar, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== tempNumPanel ========
					{
						tempNumPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						tempNumPanel.setLayout(new GridBagLayout());
						((GridBagLayout)tempNumPanel.getLayout()).columnWidths = new int[] {94, 39, 0};
						((GridBagLayout)tempNumPanel.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)tempNumPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
						((GridBagLayout)tempNumPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- tempNumLabel ----
						tempNumLabel.setText("\u0639\u062f\u062f \u0627\u0644\u0642\u0648\u0627\u0644\u0628 \u0627\u0644\u0645\u0648\u062c\u0648\u062f\u0629 \u0641\u064a \u0627\u0644\u062e\u0632\u0646");
						tempNumLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						tempNumLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						tempNumLabel.setBorder(new LineBorder(Color.black, 1, true));
						tempNumPanel.add(tempNumLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- tempNumButton ----
						tempNumButton.setText("\u061f");
						tempNumButton.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
						tempNumButton.setForeground(Color.blue);
						tempNumButton.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
						tempNumButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								tempNumButtonActionPerformed(e);
							}
						});
						tempNumPanel.add(tempNumButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					modifyOntoPanel.add(tempNumPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== aplayTempPanel ========
					{
						aplayTempPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						aplayTempPanel.setBorder(new TitledBorder(new LineBorder(Color.blue, 2, true), "\u062a\u0637\u0628\u064a\u0642 \u0627\u0644\u0642\u0648\u0627\u0644\u0628 \u0639\u0646 \u0645\u0639\u0627\u0646\u064a \u0627\u0644\u0645\u0639\u062c\u0645", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
							new Font("Traditional Arabic", Font.BOLD, 16), Color.blue));
						aplayTempPanel.setLayout(new GridBagLayout());
						((GridBagLayout)aplayTempPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
						((GridBagLayout)aplayTempPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
						((GridBagLayout)aplayTempPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};
						((GridBagLayout)aplayTempPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

						//---- tempIdLabel ----
						tempIdLabel.setText("\u0627\u062e\u062a\u0631 \u0642\u0627\u0644\u0628\u0627\u064b");
						tempIdLabel.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						tempIdLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						tempIdLabel.setBorder(new LineBorder(Color.black, 1, true));
						aplayTempPanel.add(tempIdLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- tempComboBox ----
						tempComboBox.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						tempComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						tempComboBox.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
						aplayTempPanel.add(tempComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- aplayTempButton ----
						aplayTempButton.setText("\u062a\u0637\u0628\u064a\u0642 \u0627\u0644\u0642\u0627\u0644\u0628");
						aplayTempButton.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						aplayTempButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						aplayTempButton.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
						aplayTempButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								aplayTempButtonActionPerformed(e);
							}
						});
						aplayTempPanel.add(aplayTempButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//---- meaningBeforButton ----
						meaningBeforButton.setText("\u0627\u0644\u0645\u0639\u0627\u0646\u064a \u0642\u0628\u0644 \u062a\u0637\u0628\u064a\u0642 \u0627\u0644\u0642\u0627\u0644\u0628");
						meaningBeforButton.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						meaningBeforButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						meaningBeforButton.setEnabled(true);
						meaningBeforButton.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
						meaningBeforButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								meaningBeforButtonActionPerformed(e);
							}
						});
						aplayTempPanel.add(meaningBeforButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- meaningAfterButton ----
						meaningAfterButton.setText("\u0627\u0644\u0645\u0639\u0627\u0646\u064a \u0628\u0639\u062f \u062a\u0637\u0628\u064a\u0642 \u0627\u0644\u0642\u0648\u0627\u0644\u0628");
						meaningAfterButton.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						meaningAfterButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						meaningAfterButton.setEnabled(false);
						meaningAfterButton.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
						meaningAfterButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								meaningAfterButtonActionPerformed(e);
							}
						});
						aplayTempPanel.add(meaningAfterButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));
					}
					modifyOntoPanel.add(aplayTempPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 0), 0, 0));

					//======== secondOntoPanel ========
					{
						secondOntoPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						secondOntoPanel.setBorder(new TitledBorder(new LineBorder(Color.blue, 2, true), "\u0625\u063a\u0646\u0627\u0621 \u0627\u0644\u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627 \u0623\u0648\u0644\u064a\u0629", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
							new Font("Traditional Arabic", Font.BOLD, 16), Color.blue));
						secondOntoPanel.setLayout(new GridBagLayout());
						((GridBagLayout)secondOntoPanel.getLayout()).columnWidths = new int[] {136, 201, 84, 201, 0};
						((GridBagLayout)secondOntoPanel.getLayout()).rowHeights = new int[] {0, 0, 184, 124, 0};
						((GridBagLayout)secondOntoPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};
						((GridBagLayout)secondOntoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};

						//---- enrichButton ----
						enrichButton.setText("\u0625\u063a\u0646\u0627\u0621 \u0627\u0644\u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627");
						enrichButton.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						enrichButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						enrichButton.setEnabled(false);
						enrichButton.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
						enrichButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								enrichButtonActionPerformed(e);
							}
						});
						secondOntoPanel.add(enrichButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- ontoSaveButton2 ----
						ontoSaveButton2.setText("\u062d\u0641\u0638 \u0627\u0644\u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627");
						ontoSaveButton2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
						ontoSaveButton2.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						ontoSaveButton2.setEnabled(false);
						ontoSaveButton2.setBorder(new LineBorder(new Color(0, 137, 235), 1, true));
						ontoSaveButton2.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								ontoSaveButton2ActionPerformed(e);
							}
						});
						secondOntoPanel.add(ontoSaveButton2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 5), 0, 0));

						//---- savedOntoPathTextField2 ----
						savedOntoPathTextField2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
						savedOntoPathTextField2.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
						savedOntoPathTextField2.setEditable(false);
						savedOntoPathTextField2.setBorder(new LineBorder(Color.black, 1, true));
						secondOntoPanel.add(savedOntoPathTextField2, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 5, 0), 0, 0));

						//======== savedOntoStatScrollPane2 ========
						{
							savedOntoStatScrollPane2.setBorder(new TitledBorder(new EtchedBorder(), "\u0645\u0639\u0644\u0648\u0645\u0627\u062a \u0639\u0646 \u0627\u0644\u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION,
								new Font("Traditional Arabic", Font.BOLD, 16), Color.blue));

							//---- savedOntoStatTextArea2 ----
							savedOntoStatTextArea2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
							savedOntoStatTextArea2.setFont(new Font("Traditional Arabic", Font.BOLD, 14));
							savedOntoStatTextArea2.setEditable(false);
							savedOntoStatTextArea2.setBorder(new LineBorder(Color.black, 1, true));
							savedOntoStatScrollPane2.setViewportView(savedOntoStatTextArea2);
						}
						secondOntoPanel.add(savedOntoStatScrollPane2, new GridBagConstraints(0, 2, 4, 2, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 0), 0, 0));
					}
					modifyOntoPanel.add(secondOntoPanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
				}
				mainTabbedPanel.addTab("\u0645\u0631\u062d\u0644\u0629 \u0625\u063a\u0646\u0627\u0621 \u0627\u0644\u0623\u0646\u0637\u0648\u0644\u0648\u062c\u064a\u0627", modifyOntoPanel);


				//======== exitPanel ========
				{
					exitPanel.addAncestorListener(new AncestorListener() {
						@Override
						public void ancestorMoved(AncestorEvent e) {}
						@Override
						public void ancestorAdded(AncestorEvent e) {
							exitPanelAncestorAdded(e);
						}
						@Override
						public void ancestorRemoved(AncestorEvent e) {}
					});
					exitPanel.setLayout(new GridBagLayout());
					((GridBagLayout)exitPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)exitPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
					((GridBagLayout)exitPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
					((GridBagLayout)exitPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
				}
				mainTabbedPanel.addTab("\u062e\u0631\u0648\u062c", exitPanel);

			}
			formContentPane.add(mainTabbedPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
			form.pack();
			form.setLocationRelativeTo(form.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - bassel kassem
	private JFrame form;
	private JTabbedPane mainTabbedPanel;
	private JPanel databasePanel;
	private JPanel DatabaseConn;
	private JLabel severLabel;
	private JTextField serverText;
	private JLabel portLabel;
	private JTextField portText;
	private JLabel databaseLabel;
	private JTextField databaseText;
	private JLabel userLabel;
	private JTextField userText;
	private JLabel passwodLabel;
	private JPasswordField passwordText;
	private JButton testConnButton;
	private JLabel testConnLabel;
	private JLabel tabelLabel;
	private JTextField tableText;
	private JLabel recordNumLabel;
	private JCheckBox allDatabaseCheckBox;
	private JTextField recordNumText;
	private JButton showDataButton;
	private JPanel prepareDataPanel;
	private JButton startPreparingButton;
	private JButton showPreparedDataButton;
	private JPanel firstOntoPanel;
	private JLabel ontoNameLabel;
	private JTextField ontoNameTextField;
	private JButton ontoSaveButton;
	private JTextField savedOntoPathTextField;
	private JScrollPane savedOntoStatScrollPane;
	private JTextArea savedOntoStatTextArea;
	private JPanel modifyOntoPanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem createNewTempMenuItem;
	private JMenuItem deleteTempMenuItem;
        private JMenuItem editTemplateItem;
	private JMenuItem loadTempMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu aboutMenu;
	private JMenuItem helpMenuItem;
	private JMenuItem aboutMenuItem;
	private JPanel tempNumPanel;
	private JLabel tempNumLabel;
	private JButton tempNumButton;
	private JPanel aplayTempPanel;
	private JLabel tempIdLabel;
	private JComboBox tempComboBox;
	private JButton aplayTempButton;
	private JButton meaningBeforButton;
	private JButton meaningAfterButton;
	private JPanel secondOntoPanel;
	private JButton enrichButton;
	private JButton ontoSaveButton2;
	private JTextField savedOntoPathTextField2;
	private JScrollPane savedOntoStatScrollPane2;
	private JTextArea savedOntoStatTextArea2;
	private JPanel exitPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}