
package SecondStage;

import ProjectGUIInterfaces.MainApplication;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.creole.ANNIEConstants;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.Transducer;
import gate.creole.splitter.SentenceSplitter;
import gate.creole.tokeniser.DefaultTokeniser;
import gate.util.GateException;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 *
 * @author Abo_ADNAN
 */
public class GateOperations {
    private StringFormatter stformatter;
    
    public void InitGate(){
        try {
            Gate.setGateHome ( new File ( System.getProperty ( "user.dir" ) ) );
            Gate.setPluginsHome ( new File ( System.getProperty ( "user.dir" ) + "/plug_ins" ) );
            Gate.setUserSessionFile ( new File ( System.getProperty ( "user.dir" ) + "/gate.session" ) );
            Gate.setSiteConfigFile ( new File ( System.getProperty ( "user.dir" ) + "/gate.xml" ) );
            Gate.setUrlBase ( new File ( System.getProperty ( "user.dir" ) ).toURI ().toURL () );
            //Gate.setBuiltinCreoleDir (new File(System.getProperty ("user.dir")+"/built_in_creole").toURI ().toURL ());
            Gate.init ();
            Gate.getCreoleRegister ().registerDirectories ( new File (Gate.getPluginsHome () , "arabic" ).toURI ().toURL () );
            Gate.getCreoleRegister ().registerDirectories ( new File (Gate.getPluginsHome () , ANNIEConstants.PLUGIN_DIR ).toURI ().toURL () );
        } catch (GateException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Document CreateGateDoc(String text){
//        Document newDocument = null;
//        try {
//            File file = new File (filePath );
//            FeatureMap parameter = gate.Factory.newFeatureMap ();
//            parameter.put ( Document.DOCUMENT_URL_PARAMETER_NAME , file.toURI ().toURL ().toExternalForm () );
//            parameter.put ( Document.DOCUMENT_ENCODING_PARAMETER_NAME , "utf-8" );
//            parameter.put ( Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, true);
//            Resource newResource = Factory.createResource ( "gate.corpora.DocumentImpl" , parameter );
//            newDocument = ( Document ) newResource;      
            gate.Document document = new DocumentImpl();
            document.setContent(new DocumentContentImpl(text));
            FeatureMap docFeatures = gate.Factory.newFeatureMap();
            docFeatures.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, "utf-8");
            docFeatures.put ( Document.DOCUMENT_MARKUP_AWARE_PARAMETER_NAME, true);
            document.setFeatures(docFeatures);
            
//        } catch (ResourceInstantiationException ex) {
//            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return newDocument;
          return document;
    }
    public void TokenizeGateDoc(Document newDocument){
        try {
            DefaultTokeniser tokenizer = ( DefaultTokeniser ) Factory.createResource ( "arabic.ArabicTokeniser" , Factory.newFeatureMap () );
            tokenizer.setAnnotationSetName ( "tokenizerAnnotationSet" );
            tokenizer.setDocument ( newDocument );
            tokenizer.execute ();
        } catch (ExecutionException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ResourceInstantiationException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void MorphAnalyizeGateDoc(Document newDocument,aranlp.ANLP morph){
           MorphAnalyzing newMorph = new MorphAnalyzing ();
           newMorph.setMorph(morph);
           newMorph.setDocument ( newDocument );
           newMorph.init ();
           newMorph.execute ();
     }
    public void SentenceSplitterGateDoc(Document newDocument){
        try {
            SentenceSplitter sentenceSplitter = ( SentenceSplitter ) Factory.createResource ( "gate.creole.splitter.SentenceSplitter" , Factory.newFeatureMap () );
            sentenceSplitter.setDocument ( newDocument );
            sentenceSplitter.setInputASName ( "tokenizerAnnotationSet" );
            sentenceSplitter.setOutputASName ( "tokenizerAnnotationSet" );
            sentenceSplitter.execute ();
        } catch (ExecutionException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ResourceInstantiationException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    public void POSAnalyizeGateDoc(Document newDocument,aranlp.ANLP morph){
        try {
            POSAnalyzing arabicPos = new POSAnalyzing ();
            arabicPos.setPos(morph);
            arabicPos.setInputASName ( "tokenizerAnnotationSet");
            arabicPos.setBaseSentenceAnnotationType ( "Sentence");
            arabicPos.setBaseTokenAnnotationType ( "Token");
            arabicPos.setOutputASName ( "tokenizerAnnotationSet");
            arabicPos.setOutputAnnotationType ( "Token");
            arabicPos.setDocument ( newDocument );
            arabicPos.init ();
            arabicPos.execute ();
        } catch (ExecutionException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ResourceInstantiationException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void SaveGateDoc(Document newDocument,String outputPath){
        MyFileHandler.printToFile ( newDocument.toXml () , outputPath);
    }
    public void ExcuteExpression(Document newDocument,String rulePath) throws MalformedURLException, ResourceInstantiationException, ExecutionException{
      
            gate.creole.Transducer transducer = new Transducer ();
            transducer.setInputASName ( "tokenizerAnnotationSet");
            transducer.setEncoding ( "utf-8");
            transducer.setGrammarURL ( new File(Gate.getGateHome (),rulePath).toURI ().toURL ());
            transducer.setDocument ( newDocument );
            transducer.setEnableDebugging ( true);
            transducer.setOutputASName ( "tokenizerAnnotationSet");
            transducer.init ();
            transducer.execute ();
       
    }
    public void DisplayGUIGateDoc(Document newDocument){
        try {
            JFrame editorForm = new JFrame ( "Annotation Editor" );
            editorForm.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
            editorForm.setSize ( 700 , 600 );
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green-Large-Font", "Bassel", "AlSheekhKassem");
//         
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
                String[] textComponentsKeys =
                        new String[]
                {
                    "EditorPanet" ,
                    "TextArea" ,
                    "TextField" ,
                    "TextPane"
                };
                for ( String key : textComponentsKeys )
                {
                    UIManager.put ( key+".font" , new Font ( "Arial" , 1 , 14 ) );

                }

                gate.gui.docview.DocumentEditor newDocumentEditor = new gate.gui.docview.DocumentEditor ();
                
                newDocumentEditor.setTarget ( newDocument );
                newDocumentEditor.setFont ( new java.awt.Font ( "Arial" , 1 , 14 ) );
                newDocumentEditor.setLocale ( new Locale ( "ar" ) );
                newDocumentEditor.setComponentOrientation ( ComponentOrientation.RIGHT_TO_LEFT );
                //setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                newDocumentEditor.init ();

                newDocumentEditor.setVisible ( true );
                editorForm.getContentPane ().add ( newDocumentEditor , BorderLayout.CENTER );


                editorForm.setVisible ( true );
        } catch (ResourceInstantiationException ex) {
            Logger.getLogger(GateOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String []args){
//        String path="/GateRule/test1.jape";
//        GateOperations r=new GateOperations();
//        r.InitGate();
//        Document doc=r.CreateGateDoc(System.getProperty ( "user.dir" )+"/TestInput/1.txt");
//        
//        r.TokenizeGateDoc(doc);
//        r.MorphAnalyizeGateDoc(doc);
//        r.SentenceSplitterGateDoc(doc);
//        r.POSAnalyizeGateDoc(doc);
//        r.DisplayGUIGateDoc(doc);
        //r.ExcuteExpression(doc, path);
    }

    public StringFormatter getStformatter() {
        return stformatter;
    }

    public void setStformatter(StringFormatter stformatter) {
        this.stformatter = stformatter;
    }
}
