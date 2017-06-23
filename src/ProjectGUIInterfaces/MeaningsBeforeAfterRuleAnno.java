package ProjectGUIInterfaces;
import FirstStage.ConceptStructure;
import FirstStage.ConceptsBuilder;
import FirstStage.DBConnector;
import SecondStage.ConceptRelationsBuilder;
import SecondStage.GateOperations;
import SecondStage.StringFormatter;
import SecondStage.TemplateManager;
import aranlp.ANLP;
import gate.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.*;
/*
 * Created by JFormDesigner on Wed Oct 31 08:38:31 CAT 2012
 */



/**
 * @author bassel kassem
 */
public class MeaningsBeforeAfterRuleAnno extends JFrame {
    private void meaningActionPerformed(ActionEvent e,gate.Document doc) {
         GateOperations r = new GateOperations();
         r.DisplayGUIGateDoc(doc);
    }
	public MeaningsBeforeAfterRuleAnno(Vector<ConceptStructure> concVec,Vector <gate.Document>docVec,String ti) {
		initComponents(concVec,docVec,ti);
               
	}

	private void initComponents(Vector<ConceptStructure> concVec ,Vector <gate.Document>docVec,String tit) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - bassel kassem
                InsertTemplate=new JFrame();
		meaningScrollPane = new JScrollPane();
		meaningPanel = new JPanel();
                meaningsButton=new JButton[concVec.size()];

		//======== this ========
		setTitle(tit);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
               
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

		//======== meaningScrollPane ========
		{

			//======== meaningPanel ========
			{
				meaningPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);		
				meaningPanel.setLayout(new GridBagLayout());
				((GridBagLayout)meaningPanel.getLayout()).columnWidths = new int[] {0, 0};
				((GridBagLayout)meaningPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
				((GridBagLayout)meaningPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
				((GridBagLayout)meaningPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};
                                for(int i=0;i<concVec.size();i++){
                                  meaningsButton[i]=new JButton();
                                  meaningsButton[i].setText(concVec.elementAt(i).getMeaning());
				  meaningsButton[i].setFont(new Font("Traditional Arabic", Font.BOLD, 14));
                                  final gate.Document doc=docVec.elementAt(i);
                                  meaningsButton[i].addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                      meaningActionPerformed(e,doc);
                                    }
                                  });
                                  meaningPanel.add(meaningsButton[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
                                }
			}
			meaningScrollPane.setViewportView(meaningPanel);
		}
		contentPane.add(meaningScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
                this.setSize(new java.awt.Dimension(500, 200));
                setPreferredSize(new java.awt.Dimension(500, 200));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - bassel kassem
        private JFrame InsertTemplate;
	private JScrollPane meaningScrollPane;
	private JPanel meaningPanel;
        private JButton[] meaningsButton;
//           public static void main(String args[]) {
//               TemplateManager rr=new TemplateManager();
//                rr.LoadTemplateStore();
//               rr.ShowStore();
//               DBConnector c = new DBConnector("jdbc:mysql://localhost:3306/","ardic_utf8","com.mysql.jdbc.Driver","root","root");
//               boolean t = c.TestConnection();
//               c.GetData("finalview");
//               ConceptsBuilder cocBuilder=new ConceptsBuilder(c.getQueryDB(), 30);
//
//                final ConceptRelationsBuilder r=new ConceptRelationsBuilder();
//               r.setTemplateVec(rr);
//               r.setDicDBconn(c);
//               String input="C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local.owl";
//               String JapePath="GateRules/FirstRule.jape";
//               //String output="C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/localout.owl";
//               StringFormatter sf=new StringFormatter();
//               //sf.readInStaticFiles();
//               aranlp.ANLP morph=new ANLP();
//               sf.morph=morph;
//               r.setMeaningFormatter(sf) ;
//               r.getOntoModifier().setStrFormatter(sf) ;
//               //r.SetStringFormatter(null);
//               r.getOntoModifier().OntoLoad(input);
//               r.getGateAnalyzers().InitGate();
//               r.InitClassNode(cocBuilder);
//               Vector<gate.Document>  res=new Vector<Document>();
//               res=r.StartGateProcs();
//            
//            java.awt.EventQueue.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    new MeaningsBeforeAfterRuleAnno(r.getMatchedConc(), r.getDocBeforRuleAnno(),"").setVisible(true);
//                }
//            });
//    }
//        
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
