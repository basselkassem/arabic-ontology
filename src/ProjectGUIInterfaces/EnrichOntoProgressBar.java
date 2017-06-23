
package ProjectGUIInterfaces;

import SecondStage.ConceptRelationsBuilder;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.Random;
import javax.swing.*;

public class EnrichOntoProgressBar extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {

    private JProgressBar progressBar;
    private JButton startButton;
    private JTextArea taskOutput;
    private Task task;
    public ConceptRelationsBuilder coB;
    public String res;
    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress<100) {            
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            taskOutput.append("انتهى التنفيذ!\r\n");
         
        }
   
        public void setProgressMy(int i){
            setProgress(i);
        }
    }

    public EnrichOntoProgressBar(ConceptRelationsBuilder cons, String rers) {
        
       super(new BorderLayout());
        //Create the demo's UI.
        this.coB=cons;
        startButton = new JButton("إبدأ");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);
        startButton.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        taskOutput.setFont(new Font("Traditional Arabic", Font.BOLD, 16));
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        panel.add(startButton);
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
        startButton.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
      
        
        task.addPropertyChangeListener(this);
        long startTime = System.currentTimeMillis();
        coB.OntologyEnricher(coB.getDocVecAfterAnoo()); 
        long endTime = System.currentTimeMillis();        
        taskOutput.append("استغرق التنفيذ زمن:  " + (endTime- startTime)+"ميلي ثانية"+"\r\n");   
        task.execute();
//     
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt ) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            
            
        } 
    }

    public static void createAndShowGUI(ConceptRelationsBuilder cpns,String res) {
        //Create and set up the window.
        JFrame frame = new JFrame("إغناء الأنطولوجيا");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new EnrichOntoProgressBar(cpns,res );
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        final EnrichOntoProgressBar pr=null;
//         TemplateManager rr=new TemplateManager();
//        rr.LoadTemplateStore();
//        rr.ShowStore();
//        DBConnector c = new DBConnector("jdbc:mysql://localhost:3306/","ardic_utf8","com.mysql.jdbc.Driver","root","root");
//        boolean t = c.TestConnection();
//        c.GetData("finalview");
//        ConceptsBuilder cocBuilder=new ConceptsBuilder(c.getQueryDB(), 50);
//        
//        final ConceptRelationsBuilder r=new ConceptRelationsBuilder();
//        r.setTemplateVec(rr);
//        r.setDicDBconn(c);
//        String input="C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local.owl";
//        String JapePath="GateRules/FirstRule.jape";
//        //String output="C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/localout.owl";
//        StringFormatter sf=new StringFormatter();
//        //sf.readInStaticFiles();
//        aranlp.ANLP morph=new ANLP();
//        sf.morph=morph;
//        r.SetStringFormatter(sf);
//        r.getOntoModifier().setStrFormatter(sf) ;
//        r.getGateAnalyzers().InitGate();
//        //r.SetStringFormatter(null);
//        r.getOntoModifier().OntoLoad(input);
////        System.out.println("Number of Concepts: "+ r.ontoModifier.getOntology().getClassesInSignature().size());
////        System.out.println("Number of Individual: "+ r.ontoModifier.getOntology().getIndividualsInSignature().size());
//        r.InitClassNode(cocBuilder);
//        
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//              //  pr.createAndShowGUI(r);
//            }
//        });
//     
//    }
}
