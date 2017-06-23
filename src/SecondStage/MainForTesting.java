package SecondStage;
import aranlp.ANLP;
import gate.Document;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import java.net.MalformedURLException;
public class MainForTesting {

    public static void main(String[] args)  {
        
        GateOperations r = new GateOperations();

        r.InitGate();
        aranlp.ANLP morph = new ANLP();
        String input="الفلسفة من الرجال";

        Document doc = r.CreateGateDoc(input);

        r.TokenizeGateDoc(doc);
        r.MorphAnalyizeGateDoc(doc, morph);

        r.SentenceSplitterGateDoc(doc);
        r.POSAnalyizeGateDoc(doc, morph);
        try {
            r.ExcuteExpression(doc, "RuleStore/Rule2.jape");
        } catch (MalformedURLException ex) {
            //System.out.println(ex.getStackTrace());
            //Logger.getLogger(MainForTesting.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ResourceInstantiationException ex) {
            System.out.println(ex.getCause());
           //Logger.getLogger(MainForTesting.class.getName()).log(Level.SEVERE, null, ex);
         
        } catch (ExecutionException ex) {
            //System.out.println(ex.fillInStackTrace());
            //Logger.getLogger(MainForTesting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        r.SaveGateDoc(doc, "final.xml");
        r.DisplayGUIGateDoc(doc);
//        AnnotationSet an=doc.getAnnotations("tokenizerAnnotationSet");
//        FeatureMap fm=gate.Factory.newFeatureMap();
//        for(gate.Annotation a : an){
//            fm=a.getFeatures();
//                System.out.print(fm.get("string"));
//            if(a.getType().compareTo("Mina")==0){
//                System.out.println();
//                System.out.println("------");
//                System.out.println(fm.get("parent"));
//              
//               
//            }
//            
//        }
//        
        
    }
}
