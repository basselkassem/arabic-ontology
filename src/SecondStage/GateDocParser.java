
package SecondStage;

import aranlp.ANLP;
import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import java.net.MalformedURLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GateDocParser {
    private gate.Document doc;
    
    public GateDocParser(){
        
    }
    public String ExtractFeature(String annoType,String featName){
        String res="";
        AnnotationSet an=this.doc.getAnnotations("tokenizerAnnotationSet");
        FeatureMap fm=gate.Factory.newFeatureMap();        
        for(gate.Annotation a : an){
            fm=a.getFeatures();
            if(a.getType().compareTo(annoType)==0){
                try{
                    res=fm.get(featName).toString();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(new JFrame(),
                    featName  +  " تأكد من كتابة اسم الوسمة",                          
                      annoType   +   "خطأ في كتابة القاعد ة ",                       
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }        
        return res;
    }
    public boolean MatchedRule(String annoType){
        AnnotationSet an=this.doc.getAnnotations("tokenizerAnnotationSet");
        for(gate.Annotation a : an){         
            if(a.getType().compareTo(annoType)==0){
              return true;
            }
        }
        return false;
    }
//    private Vector<Token> currToken;
//    private Vector<RuleTags> currRules;
//    public GateDocParser(){
//        currToken=new Vector<Token>();
//        currRules=new Vector< RuleTags>();
//    }
    public gate.Document getDoc(){
        return this.doc;
    }
    public void SetDoc(gate.Document _doc){
        this.doc=_doc;
    }
//    public Vector<RuleTags> GetRulesVec(){
//        return this.currRules;
//    }
//    public Vector<Token> GetTokenVec(){
//        return this.currToken;
//    }
//    public void SetRulesVec(Vector<RuleTags> rv){
//        this.currRules=rv;
//    }
//    public void SetTokenVec(Vector<Token> tv){
//        this.currToken=tv;
//    }
//    public void Parse(Vector<String>rules){
//        AnnotationSet an=this.doc.getAnnotations("tokenizerAnnotationSet");
//        FeatureMap fm=gate.Factory.newFeatureMap();
//        for(gate.Annotation a : an){
//            fm=a.getFeatures();
//            if(a.getType().compareTo("Token")==0){
//                Token t=new Token();
//                t.SetPos(fm.get("POS").toString());
//                t.SetRoot(fm.get("root").toString());
//                t.SetStem(fm.get("stem").toString());
//                t.SetStrg(fm.get("string").toString());       
//                t.SetStrgLenght(Integer.parseInt( fm.get("length").toString()));
//                currToken.add(t);
//            }
//            else {
//                for (String s:rules){
//                    if(a.getType().compareTo(s)==0){
//                        RuleTags r=new RuleTags();
//                        r.SetRuleName(s);
//                        if(fm.get("son")!=null)
//                            r.addValue(fm.get("son").toString());
//                        r.addValue(fm.get("parent").toString());
//                        currRules.add(r);
//                    }
//                }
//            }
//
//        }
//    }
//    public Token GetToken(String name){
//        Token res=null;
//        for(Token t:currToken){
//            if(t.GetStrg().compareTo(name)==0){
//                res=t;
//            }
//            
//        }
//        return res;
//    }
//    public String ExtractMeaning(){
//        String res="";
//        for(Token t:this.currToken){
//            res+=t.GetStrg()+" ";
//        }
//        return res;
//    }
//    public RuleTags GetRules(String name){
//        RuleTags res=null;
//        for(RuleTags t:currRules){
//            if(t.GetRuleName().compareTo(name)==0){
//                res=t;
//            }
//            break;
//        }
//        return res;
//    }
//    class Token{
//        private String pos;
//        private String root;
//        private String stem;
//        private String strg;
//        private int strgLenght;
//        
//        public Token(){
//            this.pos="";
//            this.root="";
//            this.stem="";
//            this.strg="";
//            this.strgLenght=0;
//        }
//        public void SetPos (String _pos){
//            this.pos=_pos;
//        }
//        public void SetRoot (String _root){
//            this.root=_root;
//        }
//        public void SetStem (String _stem){
//            this.stem=_stem;
//        }
//        public void SetStrg (String _strg){
//            this.strg=_strg;
//        }
//        public void SetStrgLenght (int len){
//            this.strgLenght=len;
//        }
//        
//        public String GetPos (){
//           return this.pos; 
//        }
//        public String GetRoot (){
//           return this.root; 
//        }
//        public String GetStem (){
//           return this.stem; 
//        }
//        public String GetStrg (){
//           return this.strg; 
//        }
//        public int GetStrgLenght (){
//           return this.strgLenght; 
//        }
//        
//        public void Display(){
//            System.out.println("POS: "+this.pos);
//            System.out.println("root: "+this.root);
//            System.out.println("Stem: "+this.stem);
//            System.out.println("String: "+this.strg);
//            System.out.println("lenght: "+this.strgLenght);
//            
//        }
//    }
//    class RuleTags {
//        private String ruleName;
//        private Vector<String> ruleValue;
//     
//        public RuleTags(){
//            ruleName="";
//            ruleValue=new Vector<String>();
//        }
//        public void SetRuleName(String rn){
//            this.ruleName=rn;
//        }
//        public void SetRuleValue(Vector<String> rv){
//            this.ruleValue = rv;
//        }
//        public String GetRuleName(){
//            return this.ruleName;
//        }
//        public Vector<String> GetRuleVale(){
//            return this.ruleValue;
//        }
//        public void addValue(String val){
//            ruleValue.add(val);
//        }
//        public void Display(){
//            System.out.println("rule name: "+this.ruleName);
//            for(String s:this.ruleValue)
//            System.out.println("rule Value: "+s);
//        }
//    }
//    
    public static void main(String []args){
        
        GateOperations r = new GateOperations();
        
        r.InitGate();
        aranlp.ANLP morph = new ANLP();
        String input="الحياء الإشفاق من الذم واللوم.";
        Document doc = r.CreateGateDoc(input);
        
        r.TokenizeGateDoc(doc);
        r.MorphAnalyizeGateDoc(doc, morph);
        
        r.SentenceSplitterGateDoc(doc);
        r.POSAnalyizeGateDoc(doc, morph);
        try {
            r.ExcuteExpression(doc, "GateRules/ReportDe.jape");
        } catch (MalformedURLException ex) {
            Logger.getLogger(GateDocParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ResourceInstantiationException ex) {
            Logger.getLogger(GateDocParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(GateDocParser.class.getName()).log(Level.SEVERE, null, ex);
        }
       // r.SaveGateDoc(doc, "final.xml");
        r.DisplayGUIGateDoc(doc);
       GateDocParser mr=new GateDocParser();
       mr.SetDoc(doc);
       System.out.println(mr.ExtractFeature("Rule1", "concept"));
       System.out.println(mr.ExtractFeature("Rule1", "token1"));
         System.out.println(mr.MatchedRule("Rule1"));
//       Vector<String> vec=new Vector<String>();
//       vec.add("Rule3");
//       mr.Parse(vec);
////       for(Token t:mr.currToken){
////           t.Display();
////       }
////       for(Rules rr:mr.currRules){
////           rr.Display();
////       }
//        //mr.GetToken("الطيب").Display();
//        mr.GetRules("Rule3").Display();
    }

    public void setDoc(gate.Document doc) {
        this.doc = doc;
    }
    
}

