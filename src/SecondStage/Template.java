
package SecondStage;

public class Template {
    private RuleJAPE ruleJAPE;
    private RuleConfigaration ruleConfig;
    private int templateId;
    public Template(){
        ruleJAPE=new RuleJAPE();
        ruleConfig=new RuleConfigaration();       
    }
    public void ShowTemplate(){
        System.out.println("--->Template Id :"+this.templateId);
        this.ruleJAPE.ShowRuleText();
        System.out.println("--->>RuleConfigaration:");
        this.ruleConfig.ShowRuleConfig();
    }
    public String getTemplate(){
        String res="";
        res+="Template rule:"+"\r\n";
        res+="rule path : "+this.ruleJAPE.getRulePath()+"\r\n";
        res+="rule Text : "+this.ruleJAPE.getText()+"\r\n";
        res+="Template Configaration:"+"\r\n";
        res+="RuleName: "+this.ruleConfig.getRuleName()+"\r\n";
        res+="newConcept: "+this.ruleConfig.getNewConcept()+"\r\n";
        res+="meaning count: "+this.ruleConfig.getMeaningCount()+"\r\n";
        res+="Conditions:"+"\r\n";
        for(int i=0;i<this.ruleConfig.getTokens().size();i++){
            res+=this.ruleConfig.getTokens().elementAt(i)+","+this.ruleConfig.getCheckFun().elementAt(i)+","+this.ruleConfig.getRelation().elementAt(i)+"\r\n";
        } 
        return res;
    }
    public RuleJAPE getRuleJAPE() {
        return ruleJAPE;
    }

    public RuleConfigaration getRuleConfig() {
        return ruleConfig;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setRuleJAPE(RuleJAPE ruleJAPE) {
        this.ruleJAPE = ruleJAPE;
    }

    public void setRuleConfig(RuleConfigaration ruleConfig) {
        this.ruleConfig = ruleConfig;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    
    
}
