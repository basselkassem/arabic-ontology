
package SecondStage;

import aranlp.ANLP;
import aranlp.Util.Words.Word;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringFormatter {
   
    private ANLP morph;
    // the tokenized line with all punctuation removed
    private Vector <String>tokenizedLine = new Vector( );

    // the files containing prefixes, suffixes and so on
    private Vector staticFiles;
    private StringBuffer word = new StringBuffer ( );
    // have the root, pattern, stopword or strange words been found
    private boolean stopwordFound;
    public StringFormatter(){
       
        this.readInStaticFiles();
    }
    public String formateSentece(String oneLine){
         Character character;
         String currentWord;
        
         Vector<String>res=new Vector<String>();
         String result="";
         
         for ( int i=0; i<oneLine.length ( ); i++ ){
            // if the character is not a space, append it to a word
            if( ( ! ( character = new Character ( oneLine.charAt ( i ) ) ).isWhitespace ( oneLine.charAt ( i ) ) ) )
            {
                word.append ( oneLine.charAt ( i ) );
                
            }
            // otherwise, if the word contains some characters, add it to the vector
            else
            {
                if ( word.length ( ) != 0 )
                {
                    tokenizedLine.addElement ( word.toString ( ) );
                    word.setLength ( 0 );
                }
            }

          }
          tokenizedLine.addElement ( word.toString ( ) );

        // now we have tokenized one line, we should stem it
        for ( int i = 0; i < tokenizedLine.size ( ); i++ ){
            // set the word in a string
            currentWord = tokenizedLine.elementAt ( i ) .toString ( );
            currentWord = formatWord ( currentWord, i );
            res.add(currentWord.toString());            
        }
        result=this.VectorToString(res);
        //System.out.println("formateSentece::  "  +result);
        return result;

    }
    public String DeleteDiacritics(String oneLine){
         Character character;
         String currentWord;
        
         Vector<String>res=new Vector<String>();
         String result="";
         
         for ( int i=0; i<oneLine.length ( ); i++ ){
            // if the character is not a space, append it to a word
            if( ( ! ( character = new Character ( oneLine.charAt ( i ) ) ).isWhitespace ( oneLine.charAt ( i ) ) ) )
            {
                word.append ( oneLine.charAt ( i ) );
                
            }
            // otherwise, if the word contains some characters, add it to the vector
            else
            {
                if ( word.length ( ) != 0 )
                {
                    tokenizedLine.addElement ( word.toString ( ) );
                    word.setLength ( 0 );
                }
            }

          }
          tokenizedLine.addElement ( word.toString ( ) );

        // now we have tokenized one line, we should stem it
        for ( int i = 0; i < tokenizedLine.size ( ); i++ ){
            // set the word in a string
            currentWord = tokenizedLine.elementAt ( i ) .toString ( );
            currentWord = DeleteWordDiacritics ( currentWord, i );
            res.add(currentWord.toString());            
        }
        result=this.VectorToString(res);
        //System.out.println("formateSentece::  "  +result);
        return result;

    }
    public Vector<String> StringToVector(String sentance){
        Vector<String> res= new Vector<String>();
        StringTokenizer st =new StringTokenizer(sentance);
        while(st.hasMoreElements()){
            res.add(st.nextElement().toString());
        }
        return res;
    }
    public String VectorToString(Vector<String> input){
        String res="";
        for(int i=0;i<input.size();i++){
            if(i==input.size()-1)
                res =res+input.elementAt(i);
            else
                res =res+input.elementAt(i)+" ";
        }
        return res;
    }
    public String formatWord ( String currentWord, int index ) {
        
        StringBuffer modifiedWord = new StringBuffer ( );
           // remove any punctuation from the word
        if ( removePunctuation( currentWord, modifiedWord ) )
        {
            tokenizedLine.setElementAt ( currentWord = modifiedWord.toString ( ), index );
        }
        // remove any diacritics (short vowels)
        if ( removeDiacritics( currentWord, modifiedWord ) )
        {
            tokenizedLine.setElementAt ( currentWord = modifiedWord.toString ( ), index );
        }
////        there could also be characters that aren't letters which should be removed
        if ( removeNonLetter ( currentWord, modifiedWord ) )
        {
            tokenizedLine.setElementAt ( currentWord = modifiedWord.toString ( ), index );
        }

        // check for stopwords
//        if( !checkStrangeWords ( currentWord ) ) {
//            if( !checkStopwords ( currentWord ) ) {
//               tokenizedLine.setElementAt ( currentWord = modifiedWord.toString ( ), index );
//            }
//        }
                //currentWord = stemWord ( currentWord );

        return currentWord;
    }
    public String DeleteWordDiacritics( String currentWord, int index ) {
        
        StringBuffer modifiedWord = new StringBuffer ( );
        if ( removeDiacritics( currentWord, modifiedWord ) )
        {
            tokenizedLine.setElementAt ( currentWord = modifiedWord.toString ( ), index );
        }
        return currentWord;
    }
    public boolean removeDiacritics(String currentWord, StringBuffer modifiedWord) {
        boolean diacriticFound = false;
        modifiedWord.setLength ( 0 );
        Vector diacritics = ( Vector ) staticFiles.elementAt ( 2 );
        for ( int i = 0; i < currentWord.length ( ); i++ )
            // if the character is not a diacritic, append it to modified word
            if ( ! ( diacritics.contains ( currentWord.substring ( i, i+1 ) ) ) )
                modifiedWord.append ( currentWord.substring ( i, i+1 ) );
            else
            {
                diacriticFound = true;
            }
        return diacriticFound;
    }
    public boolean removePunctuation(String currentWord, StringBuffer modifiedWord) {
       boolean punctuationFound = false;
        modifiedWord.setLength ( 0 );
        Vector punctuations = ( Vector ) staticFiles.elementAt ( 0 );

        // for every character in the current word, if it is a punctuation then do nothing
        // otherwise, copy this character to the modified word
        for ( int i = 0; i < currentWord.length ( ); i++ )
        {
            if ( ! ( punctuations.contains ( currentWord.substring ( i, i+1 ) ) ) )
            {
                modifiedWord.append ( currentWord.charAt ( i ) );
                
            }
            else
            {
                punctuationFound = true;
            }
        }

        return punctuationFound;
    }
    public boolean removeNonLetter(String currentWord, StringBuffer modifiedWord) {
       boolean nonLetterFound = false;
        modifiedWord.setLength ( 0 );

        // if any of the word is not a letter then remove it
        for( int i = 0; i < currentWord.length ( ); i++ )
        {
            if ( Character.isLetter ( currentWord.charAt ( i ) ) )
            {
                modifiedWord.append ( currentWord.charAt ( i ) );
               
            }
            else
            {
                nonLetterFound = true;
               
            }
        }
        return nonLetterFound;
    }
    public void readInStaticFiles ( ){
        // create a string buffer containing the path to the static files
        String pathToStemmerFiles = new StringBuffer ( System.getProperty ( "user.dir" ) + System.getProperty ( "file.separator" ) + "required files" + System.getProperty ( "file.separator" ) ).toString ( )+"StringModificationFiles/";

        // create the vector composed of vectors containing the static files
        staticFiles = new Vector ( );
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "punctuation.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "stopwords.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "diacritics.txt" ).toString ( ) ) )
        if ( addVectorFromFile ( new StringBuffer ( pathToStemmerFiles + "strange.txt" ).toString ( ) ) )
        {
            // the vector was successfully created
            System.out.println( "read in files successfully" );
        }
    }
    public boolean addVectorFromFile ( String fileName ){
        boolean returnValue;
        try
        {
            // the vector we are going to fill
            Vector vectorFromFile = new Vector ( );

            // create a buffered reader
            File file = new File ( fileName );
            FileInputStream fileInputStream = new FileInputStream ( file );
            InputStreamReader inputStreamReader = new InputStreamReader ( fileInputStream, "UTF-8" );

            //If the bufferedReader is not big enough for a file, I should change the size of it here
            BufferedReader bufferedReader = new BufferedReader ( inputStreamReader, 20000 );

            // read in the text a line at a time
            String part;
            StringBuffer word = new StringBuffer ( );
            while ( ( part = bufferedReader.readLine ( ) ) != null )
            {
                // add spaces at the end of the line
                part = part + "  ";

                // for each line
                for ( int index = 0; index < part.length ( ) - 1; index ++ )
                {
                    // if the character is not a space, append it to a word
                    if ( ! ( Character.isWhitespace ( part.charAt ( index ) ) ) )
                    {
                        word.append ( part.charAt ( index ) );
                    }
                    // otherwise, if the word contains some characters, add it
                    // to the vector
                    else
                    {
                        if ( word.length ( ) != 0 )
                        {
                            vectorFromFile.addElement ( word.toString ( ) );
                            word.setLength ( 0 );
                        }
                    }
                }
            }

            // trim the vector
            vectorFromFile.trimToSize ( );

            // destroy the buffered reader
            bufferedReader.close ( );
   	        fileInputStream.close ( );

            // add the vector to the vector composed of vectors containing the
            // static files
            staticFiles.addElement ( vectorFromFile );
            returnValue = true;
        }
        catch ( Exception exception )
        {
            //JOptionPane.showMessageDialog ( arabicStemmerGUI, "Could not open '" + fileName + "'.", " Error ", JOptionPane.ERROR_MESSAGE );
            returnValue = false;
        }
        return returnValue;
    }    
    public int CountMeaningTokens(String sentance){
        StringTokenizer st =new StringTokenizer(sentance);
        int res=0;
        while(st.hasMoreElements()){
            st.nextElement();
            res++;
        }
        return res;
    }
    public String RootingString(String input){
        String res="";
        Vector<String> vec =new Vector<String>();
        vec=this.StringToVector(input);
         
        for(int i=0;i<vec.size();i++){
            Word wordObj=morph.morphAnalysis(vec.elementAt(i));
            if(i==vec.size()-1)
                 res=res+((wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getRoot ():"#");
            else
                res=res+((wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getRoot ():"#")+" ";
        }
       
        
        return res;  
    }
    public String StemmString(String input){
        String res="";
        Vector<String> vec =new Vector<String>();
        vec=this.StringToVector(input);
         
        for(int i=0;i<vec.size();i++){
            Word wordObj=morph.morphAnalysis(vec.elementAt(i));
            if(i==vec.size()-1)
                 res=res+((wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getStem():vec.elementAt(i));
            else
                res=res+((wordObj.numberOfMorphSolutions () >0)? wordObj.getMorphSolution (0).getStem ():vec.elementAt(i))+" ";
        }
       
        
        return res;
    }
    public void StringFormatterReset(){
        this.tokenizedLine.clear();
        this.word=new StringBuffer();
    }
    public void SetMorphAnalyzer(aranlp.ANLP _morph){
        this.morph=_morph;
    }
    public static void main(String []args){
        aranlp.ANLP morph=new ANLP();
        
//        String test1="مَن تتَّخذه معتمدًا عليه من غير أَهلك.";
//        String test="<MyOntology:وليجة>";
        String test1="مَن تتَّخذه: معتمدًا عليه من غير أَهلك.";
        String test="الزَّعانِف من النَّاس.";
        String res=test1;
        
        StringFormatter r= new StringFormatter();
        r.SetMorphAnalyzer(morph);
        //res=r.DeleteDiacritics(test);
        //System.out.println(res);
           System.out.println(r.DeleteDiacritics(test));
       res=r.StemmString(test);
     
       System.out.println(res);
//        r.StringFormatterReset();
//        res=r.formateSentece(test1);
//        System.out.println(res);
//        res=r.StemmString(test);
//        res="العَدَد الكثير.";
//        System.out.println(res);
//        System.out.println(r.CountMeaningTokens(res));
//        System.out.println(r.StemmString(res));
    }

    public ANLP getMorph() {
        return morph;
    }

    public StringBuffer getWord() {
        return word;
    }

    public Vector <String> getTokenizedLine() {
        return tokenizedLine;
    }

    public Vector getStaticFiles() {
        return staticFiles;
    }

    public boolean isStopwordFound() {
        return stopwordFound;
    }

    public void setMorph(ANLP morph) {
        this.morph = morph;
    }

    public void setTokenizedLine(Vector <String> tokenizedLine) {
        this.tokenizedLine = tokenizedLine;
    }

    public void setStaticFiles(Vector staticFiles) {
        this.staticFiles = staticFiles;
    }

    public void setWord(StringBuffer word) {
        this.word = word;
    }

    public void setStopwordFound(boolean stopwordFound) {
        this.stopwordFound = stopwordFound;
    }
    
    
}
