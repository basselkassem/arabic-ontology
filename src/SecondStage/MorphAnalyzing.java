
package SecondStage;


import aranlp.ANLP;
import aranlp.Util.Words.Word;
import gate.Annotation;
import gate.AnnotationSet;
import gate.ProcessingResource;
import gate.Resource;
import gate.creole.AbstractProcessingResource;
import java.util.Vector;

public class MorphAnalyzing extends AbstractProcessingResource implements ProcessingResource
{
    gate.Document document = null;
    private ANLP morph = null;

    @Override
    public Resource init()
    {
        //morph = new ANLP ();

        return this;
    }
    public void setDocument(gate.Document document)
    {
        this.document = document;
    }
    public gate.Document getDocument()
    {
        return document;
    }
    public void execute()
    {
        if(document != null)
        {
          
            String tokenValue = "";
            Word wordObj = null;
            AnnotationSet as1 = document.getAnnotations ("tokenizerAnnotationSet");
            AnnotationSet tokens = as1.get ( TOKEN_ANNOTATION_TYPE);
            for(Annotation annotation : tokens)
            {
                tokenValue = ( String ) annotation.getFeatures ().get ( TOKEN_STRING_FEATURE_NAME);
                wordObj = morph.morphAnalysis ( tokenValue);
                annotation.getFeatures ().put ( "root",(wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getRoot ():"#");
                annotation.getFeatures ().put ( "stem", (wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getStem ():"#");
                annotation.getFeatures ().put ( "patern", (wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getPattern ():"#");
                annotation.getFeatures ().put ( "prefix", (wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getPre ():"#");
                annotation.getFeatures ().put ( "suffix", (wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getSuf ():"#");
            }
        }
    }
    static public void main(String []args){
          ANLP m = new ANLP();
          
          String input="الأنظمة الاقتصادية";
          StringFormatter forM=new StringFormatter();
          Vector<String> inpV=forM.StringToVector(input);
          for(int i=0;i<inpV.size();i++){
              Word wo=m.morphAnalysis(inpV.elementAt(i));
              System.out.println(wo.getMorphSolution (0).getRoot ());
          }
          
    }

    public ANLP getMorph() {
        return morph;
    }

    public void setMorph(ANLP morph) {
        this.morph = morph;
    }
}
