
package SecondStage;

//import SecondStage.GateDocParser.RuleTags;
import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;
import java.util.Vector;

public class PattrenStatistices {
    private Vector<String> rules;
    private Vector<gate.Document> docs;
    public Vector<Integer> rulesFreq;

    public PattrenStatistices() {
        rulesFreq=new Vector<Integer>();
    }

    public Vector<Document> getDocs() {
        return docs;
    }

   
    public void initRulesFreq(){
        
        for(String s:rules){
            int counter1=0;
            CountRuleqDoc(s,counter1);
            rulesFreq.add(counter1);
           
        }
           
    }
            
    public void CountRuleqDoc(String rule,int res){
        
        for(gate.Document d:docs){
           AnnotationSet an=d.getAnnotations("tokenizerAnnotationSet");
            FeatureMap fm=gate.Factory.newFeatureMap();
            for(gate.Annotation a : an){
                if(a.getType().compareTo(rule)==0){
                    res++;
                }
                    
            }
      
        }
        
    }
    public Vector<String> getRules() {
        return rules;
    }
    public Vector<gate.Document> getDoc() {
        return docs;
    }

    public void setRules(Vector<String> rules) {
        this.rules = rules;
    }
    public void setDoc(Vector<gate.Document> doc) {
        this.docs = doc;
    }
    public void setDocs(Vector<gate.Document> docs) {
        this.docs = docs;
    }


}
