
package SecondStage;

public class RuleJAPE {
    private String text;
    private String rulePath;
    public RuleJAPE(){
        text="";
    }
   public void ShowRuleText(){
       System.out.println("rule path: "+this.rulePath);
       System.out.println(this.text);
   }
    public String getText() {
        return text;
    }

    public void setText(String text) {
             this.text = text;
    }

    public String getRulePath() {
        return rulePath;
    }

    public void setRulePath(String rulePath) {
        this.rulePath = rulePath;
    }
    
}
