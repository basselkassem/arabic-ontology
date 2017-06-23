
package SecondStage;

import FirstStage.DBConnector;

public class CheckFunction {
    
    public static boolean FuntionsChecker(String funName,String word){
        
        if (funName.compareTo("فحص اسم")==0){
            return CheckFunction.CheckNoun(word);
        }
        else if(funName.compareTo("فحص صفة")==0){
            return CheckFunction.CheckAdj(word);
        }
        else if(funName.compareTo("فحص جمع")==0){
            return CheckFunction.CheckPlural(word);
        }
        else if(funName.compareTo("فحص مبالغة")==0){
            return CheckFunction.CheckMobalga(word);
        }
        else if(funName.compareTo("فحص مصدر")==0){
            return CheckFunction.CheckMasdar(word);
        }
        else if(funName.compareTo("لا يوجد")==0){
            return true;
        }
        return false;
    }
    public static boolean CheckNoun(String word){
        String wordT=DBConnector.GetWordTypeS(word);
        if(wordT.compareTo("اسم")==0||wordT.compareTo("اسم منسوب")==0||wordT.compareTo("اسم تفضيل")==0||wordT.compareTo("اسم عمل")==0) {
            return true;
        }
        return false;
    }
    public static boolean CheckAdj(String word){
        if(DBConnector.GetWordTypeS(word).compareTo("صفة")==0) {
            return true;
        }
        return false;
    }
    public static boolean CheckPlural(String word){
        if(DBConnector.GetWordTypeS(word).compareTo("جمع")==0) {
            return true;
        }
        return false;
    }
    public static boolean CheckMasdar(String word){
        if(DBConnector.GetWordTypeS(word).compareTo("مصدر")==0) {
            return true;
        }
        return false;
    }
    public static boolean CheckMobalga(String word){
        if(DBConnector.GetWordTypeS(word).compareTo("مبالغة")==0) {
            return true;
        }
        return false;
    }
    public static void main(String []args){
          DBConnector c = new DBConnector("jdbc:mysql://localhost:3306/","ardic_utf8","com.mysql.jdbc.Driver","root","root");
        boolean t = c.TestConnection();
        System.out.println("check Noun --> "+ CheckFunction.CheckNoun("طويل"));
        System.out.println("check adj --> "+ CheckFunction.CheckAdj("طويل"));
        System.out.println("check Plural --> "+ CheckFunction.CheckPlural("طويل"));
        System.out.println("-------------------------");
        System.out.println("CheckFunSelector --> "+CheckFunction.FuntionsChecker("CheckAdj","طويل"));
    }

}
