package FirstStage;

import java.util.Vector;

public class ConceptStructure {
    private String meaning;
    private Vector <ObjectStructure> ObjectVec;
    private String name;
    
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void SetObjectVec(Vector<ObjectStructure> IndividualNodeVector) {
        this.ObjectVec = IndividualNodeVector;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public Vector<ObjectStructure> getIndividualNodeVector() {
        return ObjectVec;
    }

    public String getName() {
        return name;
    }
    
    
    public ConceptStructure(String _meaning ,String _name ) {
        meaning=_meaning;
        name=_name;
        this.ObjectVec = new Vector <ObjectStructure>();
        
    }
    public void Display(){
        System.out.println("class word: "+this.meaning);
        System.out.println("class volcalized: "+this.name);
         System.out.println("-----");
        for(int i=0;i<ObjectVec.size();i++){
            this.ObjectVec.elementAt(i).Display();
        }
    }

    public Vector <ObjectStructure> getObjectVec() {
        return ObjectVec;
    }

    public void setObjectVec(Vector <ObjectStructure> ObjectVec) {
        this.ObjectVec = ObjectVec;
    }
}