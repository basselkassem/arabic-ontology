package SecondStage;

import java.util.Vector;

public class RuleConfigaration {
    private String ruleName;
    private String newConcept;
    private int meaningCount;
    private Vector<String> tokens;
    private Vector<String> checkFun;
    private Vector<String> relation;
    
    public RuleConfigaration(){
        ruleName="";
        newConcept="";
        meaningCount=0;
        tokens=new Vector<String>();
        checkFun=new Vector<String>();
        relation=new Vector<String>();
    }

    public void ShowRuleConfig(){
        System.out.println("RuleName: "+ruleName);
        System.out.println("newConcept: "+newConcept);
        System.out.println("meaning count: "+meaningCount);
        System.out.println("Conditions:");
        for(int i=0;i<tokens.size();i++){
            System.out.println(tokens.elementAt(i)+","+checkFun.elementAt(i)+","+relation.elementAt(i));
        } 
    }
    public String getRuleName() {
        return ruleName;
    }

    public String getNewConcept() {
        return newConcept;
    }

    public Vector<String> getTokens() {
        return tokens;
    }

    public Vector<String> getCheckFun() {
        return checkFun;
    }

    public Vector<String> getRelation() {
        return relation;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setNewConcept(String newConcept) {
        this.newConcept = newConcept;
    }

    public void setTokens(Vector<String> tokens) {
        this.tokens = tokens;
    }

    public void setCheckFun(Vector<String> checkFun) {
        this.checkFun = checkFun;
    }

    public void setRelation(Vector<String> relation) {
        this.relation = relation;
    }

    public int getMeaningCount() {
        return meaningCount;
    }

    public void setMeaningCount(int meaningCount) {
        this.meaningCount = meaningCount;
    }
    
}
