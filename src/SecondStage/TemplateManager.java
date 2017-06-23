
package SecondStage;

import java.util.StringTokenizer;
import java.util.Vector;

public class TemplateManager {
    private Vector<Template> templateVec;
    private int templateNum;
    private String templateStorePath;
    
    public TemplateManager(){
        templateVec=new Vector<Template>();
        templateStorePath=System.getProperty ( "user.dir" ) + System.getProperty ( "file.separator" )+"RuleStore"+System.getProperty ( "file.separator" );
        templateNum=this.LoadTemplateNum();
       
    }
    public int LoadTemplateNum(){
        String path= this.templateStorePath+"RuleIds.txt";
        String res=this.ReadFile(path);
        String replace = res.replace("\n", "");
        templateNum=Integer.parseInt(replace);
        return templateNum;
    }    
    public void ShowStore(){
        for(Template temp:templateVec){
            temp.ShowTemplate();
            System.out.println("=======================");
        }
    }
    public void LoadTemplateStore(){
        templateVec.clear();
        for(int i=1;i<=templateNum;i++){
            LoadTemplate(i);
        }
    }
    public Template LoadTemplate(int Id){
        
        Template res=new Template();
        res.setTemplateId(Id);
        String rulePath=this.templateStorePath+"Rule"+Id+".jape";
        String ruleConfigPath=this.templateStorePath+"Rule"+Id+"Config.txt";
        RuleJAPE rTemp =new RuleJAPE();
        rTemp.setRulePath("RuleStore/Rule"+Id+".jape");
        rTemp.setText(ReadFile(rulePath));
        res.setRuleJAPE(rTemp); 
        RuleConfigaration rcTemp= new RuleConfigaration();
        BuildRuleConf(rcTemp,ReadFile(ruleConfigPath));
        res.setRuleConfig(rcTemp);
        templateVec.add(res);
        return res;
    } 
    public void LoadTemplateId(int Id){
        templateVec.clear();
        Template res=new Template();
        res.setTemplateId(Id);
        String rulePath=this.templateStorePath+"Rule"+Id+".jape";
        String ruleConfigPath=this.templateStorePath+"Rule"+Id+"Config.txt";
        RuleJAPE rTemp =new RuleJAPE();
        rTemp.setRulePath("RuleStore/Rule"+Id+".jape");
        rTemp.setText(ReadFile(rulePath));
        res.setRuleJAPE(rTemp); 
        RuleConfigaration rcTemp= new RuleConfigaration();
        BuildRuleConf(rcTemp,ReadFile(ruleConfigPath));
        res.setRuleConfig(rcTemp);
        templateVec.add(res);
    }
    public void BuildRuleConf(RuleConfigaration conf,String fileTxt){
        StringTokenizer st= new StringTokenizer(fileTxt,"\n" );
        conf.setRuleName(st.nextToken());
        conf.setNewConcept(st.nextToken());
        String count=st.nextToken();
        if(count.compareTo("any")==0) {
            conf.setMeaningCount(-1);
        }
        else {
            conf.setMeaningCount(Integer.parseInt(count));
        }
        while(st.hasMoreTokens()){
             StringTokenizer stst=new StringTokenizer(st.nextToken(),",");
             while(stst.hasMoreTokens()){
                 conf.getTokens().add(stst.nextToken());
                 conf.getCheckFun().add(stst.nextToken());
                 conf.getRelation().add(stst.nextToken());
             }
        }
    }
    public String ReadFile(String path){
        String res="";
        res=  MyFileHandler.getText(path,"utf8");
        return res;
    }
    
    public void SaveTemplate(Template tem){
        templateNum=this.LoadTemplateNum()+1;
        SaveTemplateNumber(templateNum);
        
        tem.setTemplateId(templateNum);
        tem.getRuleJAPE().setRulePath("RuleStore/Rule"+templateNum+".jape");
        String rPath=templateStorePath+"Rule"+templateNum+".jape";
        this.SaveRule(tem.getRuleJAPE(), rPath);
        String rcPath=templateStorePath+"Rule"+templateNum+"Config.txt";
        this.SaveRuleConfig(tem.getRuleConfig(), rcPath);
    }
    public void SaveEditedTemplate(Template tem){
               
        //tem.setTemplateId(templateNum);
        tem.getRuleJAPE().setRulePath("RuleStore/Rule"+tem.getTemplateId()+".jape");
        String rPath=templateStorePath+"Rule"+tem.getTemplateId()+".jape";
        this.SaveEditRule(tem.getRuleJAPE(), rPath);
        String rcPath=templateStorePath+"Rule"+tem.getTemplateId()+"Config.txt";
        this.SaveRuleConfig(tem.getRuleConfig(), rcPath);
    }
    public void SaveTemplateNumber(int num){
        String path=templateStorePath+"RuleIds.txt";
        String s_num= String.valueOf(num);
        this.PrintToFile(s_num,path);
    }
    public void SaveRule(RuleJAPE ru ,String path){
        //ru.setRulePath(path);
        String pString="Phase: All\r\n" +
                        "Input:\r\n" +
                        "Options:\r\n";
        this.PrintToFile(pString+ru.getText(), path);        
    }
     public void SaveEditRule(RuleJAPE ru ,String path){
        //ru.setRulePath(path);
        
        this.PrintToFile(ru.getText(), path);        
    }
    public void SaveRuleConfig(RuleConfigaration rc,String path){
        String res=rc.getRuleName()+"\r\n";
        res+=rc.getNewConcept()+"\r\n";
        res+=rc.getMeaningCount()+"\r\n";
        for(int i=0;i<rc.getTokens().size();i++){
            res+=rc.getTokens().elementAt(i)+","+rc.getCheckFun().elementAt(i)+","+rc.getRelation().elementAt(i)+"\r\n";
        }
        this.PrintToFile(res,path);
    }
    public void PrintToFile(String txt,String path){
        MyFileHandler.printToFile(txt, path, "utf8");
    }
    public static void main(String []args){
        TemplateManager r=new TemplateManager();
      
        Template t=new Template();
        RuleJAPE rule=new RuleJAPE();
        rule.setText("Phas body باسل");
        t.setRuleJAPE(rule);
//        RuleConfigaration ruleconf=new RuleConfigaration();
//        ruleconf.setRuleName("Rule3");
//        ruleconf.setNewConcept("concept");
//        ruleconf.setMeaningCount(5);
//        Vector<String> to=new Vector<String>();
//        to.add("token1");
//        to.add("token2");
//        to.add("token3");
//        Vector<String> chF=new Vector<String>();
//        chF.add("chF1");
//        chF.add("chF2");
//        chF.add("chF3");
//        Vector<String> rel=new Vector<String>();
//        rel.add("rel1");
//        rel.add("rel2");
//        rel.add("rel3");
//        ruleconf.setCheckFun(chF);
//        ruleconf.setRelation(rel);
//        ruleconf.setTokens(to);
//        t.setRuleConfig(ruleconf);
//        r.SaveTemplate(t);
        r.LoadTemplateStore();
        r.ShowStore();
//        String path= r.templateStore+"Rule1Config.txt";
//        RuleConfigaration ru=new RuleConfigaration();
//        r.BuildRuleConf(ru,r.readFile(path));
//        ru.ShowRuleConfig();
    }

    public Vector<Template> getTemplateVec() {
        return templateVec;
    }

    public int getTemplateNum() {
        return templateNum;
    }

    public String getTemplateStorePath() {
        return templateStorePath;
    }

    public void setTemplateVec(Vector<Template> templateVec) {
        this.templateVec = templateVec;
    }

    public void setTemplateNum(int templateNum) {
        this.templateNum = templateNum;
    }

    public void setTemplateStorePath(String templateStorePath) {
        this.templateStorePath = templateStorePath;
    }
}
