/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FirstStage;

import java.util.Vector;

public class ObjectStructure {
    private String word;
    private String vocalizedWord;
    private String type;
    //public String []examples;
    private  Vector <String> ExampleVector;
    
    public ObjectStructure(String _word,String _volcalized,String _type){
        this.word =_word;
        this.vocalizedWord=_volcalized;
        this.type=_type;
       // System.arraycopy(_examples, 0, examples, 0, _examples.length-1);
        this.ExampleVector = new Vector <String>();
        
    }
    public void Display() {
        System.out.println("Individual word: "+this.word);
        System.out.println("Individual volcalized: "+this.vocalizedWord);
        System.out.println("Individual type: "+this.type);
        for(int i=0;i<ExampleVector.size();i++){
            System.out.println("Individual Examples: "+this.ExampleVector.elementAt(i));
            
        }
        System.out.println("~~~~~~~");
    }

    public String getWord() {
        return word;
    }
    public String getVocalizedWord() {
        return vocalizedWord;
    }
    public String getType() {
        return type;
    }
    public Vector <String> getExampleVector() {
        return ExampleVector;
    }
    
    public void setWord(String word) {
        this.word = word;
    }
    public void setVocalizedWord(String vocalizedWord) {
        this.vocalizedWord = vocalizedWord;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setExampleVector(Vector <String> ExampleVector) {
        this.ExampleVector = ExampleVector;
    }
    
}