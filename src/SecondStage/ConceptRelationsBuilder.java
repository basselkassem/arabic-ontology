
package SecondStage;
import FirstStage.*;
import aranlp.ANLP;
import gate.Document;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConceptRelationsBuilder {
    private DBConnector dicDBconn;
    private ConceptsBuilder concepts;
    private GateOperations gateAnalyzers;
    private OWLOntologyModifier ontoModifier;
    private StringFormatter meaningFormatter;
    private GateDocParser docParser;
    private PattrenStatistices stat;
    private TemplateManager templateVec;
    private Vector<ConceptStructure> matchedConc;
    private Vector<gate.Document> docBeforRuleAnno;
    private Vector<gate.Document> docVecAfterAnoo;
    public ConceptRelationsBuilder(){
        this.gateAnalyzers=new GateOperations();
        this.ontoModifier=new OWLOntologyModifier();
        
        this.docParser=new GateDocParser();
        this.templateVec=new TemplateManager();
        this.matchedConc=new Vector<ConceptStructure>();
        this.docBeforRuleAnno=new Vector<Document>();
        this.docVecAfterAnoo=new Vector<gate.Document>();
    }
    public void InitClassNode(ConceptsBuilder conc){
        this.concepts=conc;
        this.concepts.PreBuildConceptVec();
        this.concepts.BuildingProcedure();
    }
    public void GateDocBuild(){
        if(docBeforRuleAnno.size()>0){
            
        }
        else {
            this.matchedConc=new Vector<ConceptStructure>();
            this.docBeforRuleAnno=new Vector<Document>();
            //this.docVecAfterAnoo=new Vector<gate.Document>();
            for(int i=0;i<this.concepts.getConceptVec().size();i++){
                String meaning = this.concepts.getConceptVec().elementAt(i).getMeaning(); 
                int meaningCount=this.meaningFormatter.CountMeaningTokens(meaning);
                if(meaningCount>1){
                    String MeaningFo=meaningFormatter.DeleteDiacritics(meaning);
                    this.meaningFormatter.StringFormatterReset();
                    Document doco=this.gateAnalyzers.CreateGateDoc(MeaningFo);
                    this.gateAnalyzers.TokenizeGateDoc(doco);
                    this.gateAnalyzers.MorphAnalyizeGateDoc(doco, meaningFormatter.getMorph());
                    this.gateAnalyzers.SentenceSplitterGateDoc(doco);
                    this.gateAnalyzers.POSAnalyizeGateDoc(doco, meaningFormatter.getMorph());
                    docBeforRuleAnno.add(doco);
                    matchedConc.add(concepts.getClassNodeVec().elementAt(i));
                }
            }
        }
    }
    
    public Vector<gate.Document> StartGateProcs(){
        //this.gateAnalyzers.InitGate();
       
        //this.docBeforRuleAnno=new Vector<Document>();
        this.docVecAfterAnoo=new Vector<gate.Document>();
        for(int i=0;i<this.concepts.getConceptVec().size();i++){
            String meaning = this.concepts.getConceptVec().elementAt(i).getMeaning(); 
            int meaningCount=this.meaningFormatter.CountMeaningTokens(meaning);
            if(meaningCount==1){
                String MeaningFo=this.meaningFormatter.formateSentece(meaning);
                this.meaningFormatter.StringFormatterReset();
                String MeaningStemm=this.meaningFormatter.StemmString(MeaningFo);
                this.meaningFormatter.StringFormatterReset();
                this.ontoModifier.MeaningOneWordAlgo(meaning, MeaningStemm);                
            }
        }
       if(docBeforRuleAnno.size()>0)
       {
            for(gate.Document doc: this.docBeforRuleAnno)
            {                   
                for(Template t:this.templateVec.getTemplateVec()){
                    try {
                        this.gateAnalyzers.ExcuteExpression(doc,t.getRuleJAPE().getRulePath());
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(ConceptRelationsBuilder.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ResourceInstantiationException ex) {
                        Logger.getLogger(ConceptRelationsBuilder.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(ConceptRelationsBuilder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } 
                this.gateAnalyzers.SaveGateDoc(doc, "GateOutputFiles"+"\\"+doc.getName()+".xml");
                docVecAfterAnoo.add(doc);                    
            }
       }
       else{
            this.matchedConc=new Vector<ConceptStructure>();
            for(int i=0;i<this.concepts.getConceptVec().size();i++){
                String meaning = this.concepts.getConceptVec().elementAt(i).getMeaning(); 
                int meaningCount=this.meaningFormatter.CountMeaningTokens(meaning);
                if(meaningCount>1){
                    String MeaningFo=meaningFormatter.DeleteDiacritics(meaning);
                    this.meaningFormatter.StringFormatterReset();
                    Document doc=this.gateAnalyzers.CreateGateDoc(MeaningFo);
                    this.gateAnalyzers.TokenizeGateDoc(doc);
                    this.gateAnalyzers.MorphAnalyizeGateDoc(doc, meaningFormatter.getMorph());
                    this.gateAnalyzers.SentenceSplitterGateDoc(doc);
                    this.gateAnalyzers.POSAnalyizeGateDoc(doc, meaningFormatter.getMorph());
                   
                    matchedConc.add(concepts.getClassNodeVec().elementAt(i));
                    for(Template t:this.templateVec.getTemplateVec()){
                        try {
                            this.gateAnalyzers.ExcuteExpression(doc,t.getRuleJAPE().getRulePath());
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(ConceptRelationsBuilder.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ResourceInstantiationException ex) {
                            Logger.getLogger(ConceptRelationsBuilder.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(ConceptRelationsBuilder.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    this.gateAnalyzers.SaveGateDoc(doc, "GateOutputFiles"+"\\"+i+".xml");
                    docVecAfterAnoo.add(doc);
                }
            }
           
       }
        return docVecAfterAnoo;       
    }
    public void OntologyEnricher(Vector<gate.Document> docVec ){
        for(Template temp :this.templateVec.getTemplateVec()){
            for(int i=0;i<docVec.size();i++) {
                this.DocDetectConfig(docVec.elementAt(i),temp,this.matchedConc.elementAt(i));
            }
        }
    } 
    public void DocDetectConfig(gate.Document doc, Template temp,ConceptStructure conc){
        this.docParser.SetDoc(doc);
        if(docParser.MatchedRule(temp.getRuleConfig().getRuleName())){
            int count=this.meaningFormatter.CountMeaningTokens(conc.getMeaning());
            this.meaningFormatter.StringFormatterReset();
            if(temp.getRuleConfig().getMeaningCount()>0 && temp.getRuleConfig().getMeaningCount()==count ){
                String newConept=docParser.ExtractFeature(temp.getRuleConfig().getRuleName(), temp.getRuleConfig().getNewConcept());
//                if(newConept.compareTo("##")==0)
//                    newConept=con.
                for(int i=0;i<temp.getRuleConfig().getTokens().size();i++){
                    String token=temp.getRuleConfig().getTokens().elementAt(i);
                    String checkfun=temp.getRuleConfig().getCheckFun().elementAt(i);
                    String relName=temp.getRuleConfig().getRelation().elementAt(i);
                    if(token.compareTo("word")==0){
                        if(CheckFunction.FuntionsChecker(checkfun,conc.getIndividualNodeVector().elementAt(0).getWord())){
                            this.ontoModifier.GeneralAlgorithm(newConept, conc.getMeaning(), relName);
                        }
                    }
                    else if(token.compareTo("لا يوجد")==0){
                        this.ontoModifier.GeneralAlgorithm(newConept, conc.getMeaning(), relName);
                    }
                    else{
                        if(CheckFunction.FuntionsChecker(checkfun,token)){
                            this.ontoModifier.GeneralAlgorithm(newConept, conc.getMeaning(), relName);
                        }
                    }
                }
            }
            else{
                String newConept=docParser.ExtractFeature(temp.getRuleConfig().getRuleName(), temp.getRuleConfig().getNewConcept());
                for(int i=0;i<temp.getRuleConfig().getTokens().size();i++){
                    String token=temp.getRuleConfig().getTokens().elementAt(i);
                    String checkfun=temp.getRuleConfig().getCheckFun().elementAt(i);
                    String relName=temp.getRuleConfig().getRelation().elementAt(i);
                    if(token.compareTo("word")==0){
                        if(CheckFunction.FuntionsChecker(checkfun,conc.getIndividualNodeVector().elementAt(0).getWord())){
                            this.ontoModifier.GeneralAlgorithm(newConept, conc.getMeaning(), relName);
                        }
                    }
                    else if(token.compareTo("لا يوجد")==0){
                        this.ontoModifier.GeneralAlgorithm(newConept, conc.getMeaning(), relName);
                    }
                    else{
                        if(CheckFunction.FuntionsChecker(checkfun,token)){
                            this.ontoModifier.GeneralAlgorithm(newConept, conc.getMeaning(), relName);
                        }
                    }
                }
            }
          
        }
    }
//    public boolean CheckFunSelector(String funName,String word){
//        
//        if (funName.compareTo("CheckNoun")==0){
//            return this.CheckNoun(word);
//        }
//        else if(funName.compareTo("CheckAdj")==0){
//            return this.CheckAdj(word);
//        }
//        else if(funName.compareTo("CheckPlural")==0){
//            return this.CheckPlural(word);
//        }
//        else if(funName.compareTo("لا يوجد")==0){
//            return true;
//        }
//        return false;
//    }
//    public boolean CheckNoun(String word){
//        if(dicDBconn.GetWordType(word).compareTo("اسم")==0) {
//            return true;
//        }
//        return false;
//    }
//    public boolean CheckAdj(String word){
//        if(dicDBconn.GetWordType(word).compareTo("صفة")==0) {
//            return true;
//        }
//        return false;
//    }
//    public boolean CheckPlural(String word){
//        if(dicDBconn.GetWordType(word).compareTo("جمع")==0) {
//            return true;
//        }
//        return false;
//    }

    public void SaveMeaningOnFile(String data,String path){
       Writer out = null;
       //File file =new File(path);
       
        try {
            out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(path), "UTF-8"));
            out.write(data);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ConceptRelationsBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void SetStringFormatter(StringFormatter sf){
        this.meaningFormatter=sf;
        this.ontoModifier.setStrFormatter(sf) ;
    }
    public static void main(String []args){
        TemplateManager rr=new TemplateManager();
        rr.LoadTemplateStore();
        for(Template t:rr.getTemplateVec())
            t.ShowTemplate();
        System.out.println("=====================================");
        DBConnector c = new DBConnector("jdbc:mysql://localhost:3306/","ardic_utf8","com.mysql.jdbc.Driver","root","root");
        boolean t = c.TestConnection();
        c.GetData("finalview");
        ConceptsBuilder cocBuilder=new ConceptsBuilder(c.getQueryDB(), 500);
        
        ConceptRelationsBuilder r=new ConceptRelationsBuilder();
        r.setTemplateVec(rr);
        r.setDicDBconn(c);
        String input="C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local.owl";
        String JapePath="GateRules/FirstRule.jape";
        //String output="C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/localout.owl";
        StringFormatter sf=new StringFormatter();
        //sf.readInStaticFiles();
        aranlp.ANLP morph=new ANLP();
        sf.setMorph(morph);
        r.meaningFormatter= sf;
        r.ontoModifier.setStrFormatter(sf) ;
        //r.SetStringFormatter(null);
        r.ontoModifier.OntoLoad(input);
        System.out.println("Number of Concepts: "+ r.ontoModifier.getOntology().getClassesInSignature().size());
        System.out.println("Number of Individual: "+ r.ontoModifier.getOntology().getIndividualsInSignature().size());
        r.InitClassNode(cocBuilder);
        Vector<gate.Document>  res=new Vector<Document>();
        r.gateAnalyzers.InitGate();
        res=r.StartGateProcs();
        System.out.println("finishing Building gate docs");
        r.OntologyEnricher(res);

        r.ontoModifier.OntoSave("C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local2.owl");
        System.out.println("=====================");
        System.out.println("Number of Concepts: "+ r.ontoModifier.getOntology().getClassesInSignature().size());
        System.out.println("Number of Individual: "+ r.ontoModifier.getOntology().getIndividualsInSignature().size());
        
       
        
    }

    public DBConnector getDicDBconn() {
        return dicDBconn;
    }

    public ConceptsBuilder getConcepts() {
        return concepts;
    }

    public GateOperations getGateAnalyzers() {
        return gateAnalyzers;
    }

    public OWLOntologyModifier getOntoModifier() {
        return ontoModifier;
    }

    public StringFormatter getMeaningFormatter() {
        return meaningFormatter;
    }

    public GateDocParser getDocParser() {
        return docParser;
    }

    public void setDicDBconn(DBConnector dicDBconn) {
        this.dicDBconn = dicDBconn;
    }

    public void setConcepts(ConceptsBuilder concepts) {
        this.concepts = concepts;
    }

    public void setGateAnalyzers(GateOperations gateAnalyzers) {
        this.gateAnalyzers = gateAnalyzers;
    }

    public void setOntoModifier(OWLOntologyModifier ontoModifier) {
        this.ontoModifier = ontoModifier;
    }

    public void setMeaningFormatter(StringFormatter meaningFormatter) {
        this.meaningFormatter = meaningFormatter;
    }

    public void setDocParser(GateDocParser docParser) {
        this.docParser = docParser;
    }

    public PattrenStatistices getStat() {
        return stat;
    }

    public TemplateManager getTemplateVec() {
        return templateVec;
    }

    public void setStat(PattrenStatistices stat) {
        this.stat = stat;
    }

    public void setTemplateVec(TemplateManager templateVec) {
        this.templateVec = templateVec;
    }

    public Vector<ConceptStructure> getMatchedConc() {
        return matchedConc;
    }

    public void setMatchedConc(Vector<ConceptStructure> matchedConc) {
        this.matchedConc = matchedConc;
    }

    public void setDocBeforRuleAnno(Vector<gate.Document> docBeforRuleAnno) {
        this.docBeforRuleAnno = docBeforRuleAnno;
    }

    public Vector<gate.Document> getDocBeforRuleAnno() {
        return docBeforRuleAnno;
    }

    public Vector<gate.Document> getDocVecAfterAnoo() {
        return docVecAfterAnoo;
    }

    public void setDocVecAfterAnoo(Vector<gate.Document> docVecAfterAnoo) {
        this.docVecAfterAnoo = docVecAfterAnoo;
    }
    
}
