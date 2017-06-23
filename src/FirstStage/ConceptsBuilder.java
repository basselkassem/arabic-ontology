/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FirstStage;

import java.sql.ResultSet;
import java.util.Vector;


public class ConceptsBuilder
{
    private ResultSet resultSet;  /// the resultset retrieved from the database to be processed
    private Vector<ConceptStructure> ConceptVec; /// out put of processed data 
    private int Size;// numer of rows to be processed

    public ResultSet getResultSet() {
        return resultSet;
    }
    public Vector<ConceptStructure> getClassNodeVec() {
        return ConceptVec;
    }
    public int getSize() {
        return Size;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
    public void setClassNodeVec(Vector<ConceptStructure> classNodeVec) {
        this.ConceptVec = classNodeVec;
    }
    public void setSize(int Size) {
        this.Size = Size;
    }
    
    public ConceptsBuilder(ResultSet res ,int size)
    {
        this.resultSet = res;
        this.Size = size;
        this.ConceptVec = new Vector<ConceptStructure>();
    }
    public boolean PreBuildConceptVec(){
         ///// copy his result set in to vectors
        Vector <String> Meaning = new Vector<String>();
        Vector <String> VocalizedNoun = new Vector<String>();
        Vector <String> Noun = new Vector<String>();
        Vector <String> Example = new Vector<String>();
        Vector <String> Type = new Vector<String>();
        try
        {
            while(this.resultSet.next())
            {
               
                // 1 = noun itself
                 //2 type
                // 3 = vocalized noun
                // 4= meaning
                // 5= example
                
                try
                {
                    Noun.add(this.resultSet.getString("rawWord"));
                    Type.add(this.resultSet.getString("type"));
                    VocalizedNoun.add(this.resultSet.getString("vocalizedNoun"));
                    Meaning.add(this.resultSet.getString("meaning"));
                    Example.add(this.resultSet.getString("example"));
                }
                catch(Exception tt)
                {
                    return false;
                }

            }
        }
        catch(Exception tt){return false;}
        for(int i = 0 ; i<this.Size ; i++)
        {
               ConceptStructure t = new ConceptStructure(Meaning.elementAt(i)  , VocalizedNoun.elementAt(i));
               ObjectStructure in = new ObjectStructure(Noun.elementAt(i),VocalizedNoun.elementAt(i),Type.elementAt(i));
               in.getExampleVector().add(Example.elementAt(i));
               t.getObjectVec().add(in);
               this.ConceptVec.add(t);
               
        }
        return true;
    }
    public boolean BuildingProcedure(){
        for(int i=0;i<this.ConceptVec.size();i++){
            String meaning=ConceptVec.elementAt(i).getMeaning();
            for(int j=i;j<this.ConceptVec.size();j++){
                if(j!=i) {
                    if(this.ConceptVec.elementAt(j).getMeaning().compareTo(meaning)==0){
                        if(ExistIndiInConcept(ConceptVec.elementAt(i), ConceptVec.elementAt(j).getIndividualNodeVector().elementAt(0))){
                            ConceptVec.elementAt(i).getIndividualNodeVector().elementAt(0).getExampleVector().add(ConceptVec.elementAt(j).getIndividualNodeVector().elementAt(0).getExampleVector().elementAt(0));
                        }
                        else{
                            ConceptVec.elementAt(i).getIndividualNodeVector().add(ConceptVec.elementAt(j).getIndividualNodeVector().elementAt(0));
                        }
                       ConceptVec.remove(j); 
                       j=j-1;
                    }
                }
            }
        }        
        
        
        
        
        
      // this.PreBuildConceptVec();
//        int n=this.ConceptVec.size();
//        for(int i = 0 ; i < this.ConceptVec.size() ; i++)
//        {
//            ConceptStructure tempI=this.ConceptVec.elementAt(i);
//            for(int k = i+1 ; k <  this.ConceptVec.size() ; k++)
//            {
//                
//                ConceptStructure tempk=this.ConceptVec.elementAt(k);
//                if(tempI.meaning.compareTo(tempk.meaning)==0 )
//                {                     
//                    if(tempI.name.compareTo(tempk.name)==0) //same meaning same word difreent contexts
//                    {
//                        this.ConceptVec.elementAt(i).ObjectVec.elementAt(0).getExampleVector().add(tempk.ObjectVec.elementAt(0).getExampleVector().elementAt(0));
//                        this.ConceptVec.remove(k);
//                    }
//                    else  
//                    {
//                        this.ConceptVec.elementAt(i).ObjectVec.add(this.ConceptVec.elementAt(k).ObjectVec.elementAt(0));
//                        this.ConceptVec.remove(k);
//                    }
//                }
////                if(ConceptVec.elementAt(i).name.compareTo(ConceptVec.elementAt(k).name)==0){
////                    ConceptVec.elementAt(k).name=ConceptVec.elementAt(k).name+"1";                    
////                }
//            }
//            
//               
//        }
        return false;
    }
    
    public boolean ExistIndiInConcept(ConceptStructure con,ObjectStructure obje){ 
        for(ObjectStructure ob:con.getIndividualNodeVector()){
            if(ob.getWord().compareTo(obje.getWord())==0){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
         
        DBConnector c = new DBConnector("jdbc:mysql://localhost:3306/","ardic_utf8","com.mysql.jdbc.Driver","root","root");
        boolean t = c.TestConnection();
            System.out.println(c.getMessage());
            ResultSet res = c.GetData("finalview");
//            System.out.println(c.GetDataBaseSize("finalview"));
//            String res1 = c.GetWordType("اشفاق");
//           System.out.println(res1);
            
            try
            {
               ConceptsBuilder ss = new ConceptsBuilder(res,500);
               ss.PreBuildConceptVec();
               ss.BuildingProcedure();
               System.out.println(ss.ConceptVec.size());
               for(int i = 0 ; i < ss.ConceptVec.size() ; i++)
               {
                   ss.ConceptVec.elementAt(i).Display();
                    System.out.println("========================================");
               }
              
             System.out.println("Done");
             res.close();
        //     c.conn.close();
            }
            catch(Exception tt)
            {
                
                System.out.println("Error");
                System.out.println(tt.getMessage());
            }
    }

    public Vector<ConceptStructure> getConceptVec() {
        return ConceptVec;
    }

    public void setConceptVec(Vector<ConceptStructure> ConceptVec) {
        this.ConceptVec = ConceptVec;
    }

}
