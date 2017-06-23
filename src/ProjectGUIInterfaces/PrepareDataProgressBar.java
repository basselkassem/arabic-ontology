

package ProjectGUIInterfaces;

import FirstStage.ConceptsBuilder;
import FirstStage.DBConnector;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.*;

public class PrepareDataProgressBar extends JPanel
                             implements ActionListener, 
                                        PropertyChangeListener {

    private JProgressBar progressBar;
    private JButton startButton;
    private JTextArea taskOutput;
    private Task task;
    public ConceptsBuilder coB;
   
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
            while (progress < 100) {
                
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
            setProgress(100);
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            setCursor(null); //turn off the wait cursor
            taskOutput.append("انتهى التنفيذ!\n");
        }
   
        public void setProgressMy(int i){
            setProgress(i);
        }
    }

    public PrepareDataProgressBar(ConceptsBuilder cons ) {
        
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
        
       task.execute();
       long startTime = System.currentTimeMillis();
       coB.BuildingProcedure();     
       task.cancel(true);
       long endTime = System.currentTimeMillis();
       taskOutput.append("استغرق التنفيذ زمن:  " + (endTime- startTime)+"ميلي ثانية  "+"\r\n");  
       
        
     
    }

    /**
     * Invoked when task's progress property changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt ) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
            //taskOutput.append(String.format("Completed %d%% of task.\n", task.getProgress()));
            
        } 
    }


    /**
     * Create the GUI and show it. As with all GUI code, this must run
     * on the event-dispatching thread.
     */
    public static void createAndShowGUI(ConceptsBuilder cpns) {
        //Create and set up the window.
        JFrame frame = new JFrame("عملية تحضير المعطيات");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new PrepareDataProgressBar(cpns);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        
//           
//            DBConnector c = new DBConnector("jdbc:mysql://localhost:3306/","ardic_utf8","com.mysql.jdbc.Driver","root","root");
//                boolean t = c.TestConnection();
//                    System.out.println(c.getMessage());
//                    ResultSet res = c.GetData("finalview");
//        //            System.out.println(c.GetDataBaseSize("finalview"));
//        //            String res1 = c.GetWordType("اشفاق");
//        //           System.out.println(res1);
//                    final ConceptsBuilder ss = new ConceptsBuilder(res,5000);;
//            try
//            {
//              
//               ss.PreBuildConceptVec();
//               //ss.BuildingProcedure();
//               System.out.println(ss.ConceptVec.size());
//               
//             System.out.println("Done");
//             res.close();
//        //     c.conn.close();
//            }
//            catch(Exception tt)
//            {
//                
//                System.out.println("Error");
//                System.out.println(tt.getMessage());
//            }
//             final PrepareDataProgressBar pr = null;//=new PrepareDataProgressBar(ss);
//          //pr.coB=ss;
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                pr.createAndShowGUI(ss);
//            }
//        });
//    }
}
