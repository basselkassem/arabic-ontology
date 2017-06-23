package SecondStage;

import aranlp.ANLP;
import aranlp.Util.POSSolutions.Token;
import aranlp.Util.Sentence;
import aranlp.Util.Words.Word;
import gate.*;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.util.GateRuntimeException;
import gate.util.OffsetComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class POSAnalyzing extends AbstractLanguageAnalyser
{

    private static final String TAG_DOCUMENT_PARAMETER_NAME = "document";
    private static final String TAG_INPUT_AS_PARAMETER_NAME = "inputASName";
    private static final String TAG_LEXICON_URL_PARAMETER_NAME = "lexiconURL";
    private static final String TAG_RULES_URL_PARAMETER_NAME = "rulesURL";
    private static final String TAG_ENCODING_PARAMETER_NAME = "encoding";
    private static final String BASE_TOKEN_ANNOTATION_TYPE_PARAMETER_NAME = "baseTokenAnnotationType";
    private static final String OUTPUT_ANNOTATION_TYPE_PARAMETER_NAME = "outputAnnotationType";
    private static final String BASE_SENTENCE_ANNOTATION_TYPE_PARAMETER_NAME = "baseSentenceAnnotationType";
    private static final String TAG_OUTPUT_AS_PARAMETER_NAME = "outputASName";

    public static String getTAG_DOCUMENT_PARAMETER_NAME() {
        return TAG_DOCUMENT_PARAMETER_NAME;
    }

    public static String getTAG_INPUT_AS_PARAMETER_NAME() {
        return TAG_INPUT_AS_PARAMETER_NAME;
    }

    public static String getTAG_LEXICON_URL_PARAMETER_NAME() {
        return TAG_LEXICON_URL_PARAMETER_NAME;
    }

    public static String getTAG_RULES_URL_PARAMETER_NAME() {
        return TAG_RULES_URL_PARAMETER_NAME;
    }

    public static String getTAG_ENCODING_PARAMETER_NAME() {
        return TAG_ENCODING_PARAMETER_NAME;
    }

    public static String getBASE_TOKEN_ANNOTATION_TYPE_PARAMETER_NAME() {
        return BASE_TOKEN_ANNOTATION_TYPE_PARAMETER_NAME;
    }

    public static String getOUTPUT_ANNOTATION_TYPE_PARAMETER_NAME() {
        return OUTPUT_ANNOTATION_TYPE_PARAMETER_NAME;
    }

    public static String getBASE_SENTENCE_ANNOTATION_TYPE_PARAMETER_NAME() {
        return BASE_SENTENCE_ANNOTATION_TYPE_PARAMETER_NAME;
    }

    public static String getTAG_OUTPUT_AS_PARAMETER_NAME() {
        return TAG_OUTPUT_AS_PARAMETER_NAME;
    }

    public POSAnalyzing ()
    {
    }

    public Resource init () throws ResourceInstantiationException
    {

//        if ( lexiconURL == null )
//        {
//            throw new ResourceInstantiationException (
//                    "NoURL provided for the lexicon!" );
//        }
//        if ( rulesURL == null )
//        {
//            throw new ResourceInstantiationException (
//                    "No URL provided for the rules!" );
//        }
//        try
//        {
//            tagger = new hepple.postag.POSTagger ( lexiconURL , rulesURL , encoding );
//        }
//        catch ( Exception e )
//        {
//            throw new ResourceInstantiationException ( e );
//        }
        //pos = new ANLP ();
        return this;
    }

    public void execute () throws ExecutionException
    {
        //check the parameters
        if ( document == null )
        {
            throw new ExecutionException (
                    "No document to process!" );
        }
        if ( inputASName != null && inputASName.equals ( "" ) )
        {
            inputASName = null;
        }
        AnnotationSet inputAS = ( inputASName == null ) ? document.getAnnotations () : document.getAnnotations ( inputASName );
        //AnnotationSet inputAS =  document.getAnnotations ();

        if ( baseTokenAnnotationType == null || baseTokenAnnotationType.trim ().length () == 0 )
        {
            throw new ExecutionException ( "No base Token Annotation Type provided!" );
        }

        if ( outputASName != null && outputASName.equals ( "" ) )
        {
            outputASName = null;
        }
        AnnotationSet outputAS = ( outputASName == null ) ? document.getAnnotations () : document.getAnnotations ( outputASName );

        if ( baseSentenceAnnotationType == null || baseSentenceAnnotationType.trim ().length () == 0 )
        {
            throw new ExecutionException ( "No base Sentence Annotation Type provided!" );
        }

        if ( outputAnnotationType == null || outputAnnotationType.trim ().length () == 0 )
        {
            throw new ExecutionException ( "No AnnotationType provided to store the new feature!" );
        }

        AnnotationSet sentencesAS = inputAS.get ( baseSentenceAnnotationType );
        AnnotationSet tokensAS = inputAS.get ( baseTokenAnnotationType );
        if ( sentencesAS != null && sentencesAS.size () > 0 && tokensAS != null && tokensAS.size () > 0 )
        {
            long startTime = System.currentTimeMillis ();
//            fireStatusChanged ( "POS tagging " + document.getName () );
//            fireProgressChanged ( 0 );
            //prepare the input for HepTag
            String posSentence = "";
            List sentenceForTagger = new ArrayList ();
            List sentencesForTagger = new ArrayList ( 1 );
            sentencesForTagger.add ( sentenceForTagger );

            //define a comparator for annotations by start offset
            Comparator offsetComparator = new OffsetComparator ();

            //read all the tokens and all the sentences
            List sentencesList = new ArrayList ( sentencesAS );
            Collections.sort ( sentencesList , offsetComparator );
            List tokensList = new ArrayList ( tokensAS );
            Collections.sort ( tokensList , offsetComparator );

            Iterator sentencesIter = sentencesList.iterator ();
            ListIterator tokensIter = tokensList.listIterator ();

            List tokensInCurrentSentence = new ArrayList ();
            Annotation currentToken = ( Annotation ) tokensIter.next ();
            int sentIndex = 0;
            int sentCnt = sentencesAS.size ();
            while ( sentencesIter.hasNext () )
            {
                try
                {
                    Annotation currentSentence = ( Annotation ) sentencesIter.next ();
                    tokensInCurrentSentence.clear ();
                    sentenceForTagger.clear ();
                    posSentence = "";
                    while ( currentToken != null && currentToken.getEndNode ().getOffset ().compareTo ( currentSentence.getEndNode ().getOffset () ) <= 0 )
                    {
                        tokensInCurrentSentence.add ( currentToken );
                        sentenceForTagger.add ( currentToken.getFeatures ().get ( TOKEN_STRING_FEATURE_NAME ) );
                        posSentence += currentToken.getFeatures ().get ( TOKEN_STRING_FEATURE_NAME ) + " ";
                        currentToken = ( Annotation ) ( tokensIter.hasNext () ? tokensIter.next () : null );
                    }
                    //run the POS tagger
                    Sentence sentence = pos.posAnalysis ( posSentence );
//                    List taggerList = tagger.runTagger ( sentencesForTagger );

                    if ( sentence != null && sentence.size () > 0 )
                    {
                        if ( sentence.size () != tokensInCurrentSentence.size () )
                        {
                            throw new ExecutionException ( "POS Tagger malfunction: the output size (" + sentence.size () + ") is different from the input size (" + tokensInCurrentSentence.size () + ")!" );
                        }
                        Iterator tokIter = tokensInCurrentSentence.iterator ();
                        for ( int i = 0 ; i < sentence.size () ; i ++ )
                        {
                            Annotation annot = ( Annotation ) tokIter.next ();
                            Word wordObj = sentence.getWordPOS ( i );
                            addFeatures ( annot , "root" , ( wordObj.numberOfMorphSolutions () > 0 ) ? wordObj.getMorphSolution ( 0 ).getRoot () : "#" );
                            addFeatures ( annot , "stem" , ( wordObj.numberOfMorphSolutions () > 0 ) ? wordObj.getMorphSolution ( 0 ).getStem () : "#" );
                            addFeatures ( annot , "patern" , ( wordObj.numberOfMorphSolutions () > 0 ) ? wordObj.getMorphSolution ( 0 ).getPattern () : "#" );
                            addFeatures ( annot , "prefix" , ( wordObj.numberOfMorphSolutions () > 0 ) ? wordObj.getMorphSolution ( 0 ).getPre () : "#" );
                            addFeatures ( annot , "suffix" , ( wordObj.numberOfMorphSolutions () > 0 ) ? wordObj.getMorphSolution ( 0 ).getSuf () : "#" );
                            Token[] tokens = sentence.getWordPOS ( i ).getPOSSolution ( 0 ).getTokensArr ();
                            for ( int j = 0 ; j < tokens.length ; j ++ )
                            {

                                addFeatures ( annot , "POS" , tokens[j].getTag () );
                            }

                        }

                    }
//                    if ( taggerList != null && taggerList.size () > 0 )
//                    {
//                        List taggerResults = ( List ) taggerList.get ( 0 );
//                        //add the results
//                        //make sure no malfunction occurred
//                        if ( taggerResults.size () != tokensInCurrentSentence.size () )
//                        {
//                            throw new ExecutionException ( "POS Tagger malfunction: the output size (" + taggerResults.size () + ") is different from the input size (" + tokensInCurrentSentence.size () + ")!" );
//                        }
//                        Iterator resIter = taggerResults.iterator ();
//                        Iterator tokIter = tokensInCurrentSentence.iterator ();
//                        while ( resIter.hasNext () )
//                        {
//                            Annotation annot = ( Annotation ) tokIter.next ();
//                            addFeatures ( annot , TOKEN_CATEGORY_FEATURE_NAME , ( ( String[] ) resIter.next () )[1] );
//                        }
//                    }
//                    fireProgressChanged ( sentIndex ++ * 100 / sentCnt );
                }
                catch ( Exception ex )
                {
                    Logger.getLogger ( POSAnalyzing.class.getName () ).log ( Level.SEVERE , null , ex );
                }
            }//while(sentencesIter.hasNext())

////            if ( currentToken != null )
////            {
////                //we have remaining tokens after the last sentence
////                tokensInCurrentSentence.clear ();
////                sentenceForTagger.clear ();
////                posSentence = "";
////                while ( currentToken != null )
////                {
////                    tokensInCurrentSentence.add ( currentToken );
////                    sentenceForTagger.add ( currentToken.getFeatures ().
////                            get ( TOKEN_STRING_FEATURE_NAME ) );
////                    currentToken = ( Annotation ) ( tokensIter.hasNext () ? tokensIter.next () : null );
////                }
////                //run the POS tagger
////                List taggerResults = ( List ) tagger.runTagger ( sentencesForTagger ).get ( 0 );
////                //add the results
////                //make sure no malfunction accured
////                if ( taggerResults.size () != tokensInCurrentSentence.size () )
////                {
////                    throw new ExecutionException (
////                            "POS Tagger malfunction: the output size (" +
////                            taggerResults.size () +
////                            ") is different from the input size (" +
////                            tokensInCurrentSentence.size () + ")!" );
////                }
////                Iterator resIter = taggerResults.iterator ();
////                Iterator tokIter = tokensInCurrentSentence.iterator ();
////                while ( resIter.hasNext () )
////                {
////                    Annotation annot = ( Annotation ) tokIter.next ();
////                    addFeatures ( annot , TOKEN_CATEGORY_FEATURE_NAME , ( ( String[] ) resIter.next () )[1] );
////                }
////            }//if(currentToken != null)
//            fireProcessFinished ();
//            fireStatusChanged (
//                    document.getName () + " tagged in " +
//                    NumberFormat.getInstance ().format (
//                    ( double ) ( System.currentTimeMillis () - startTime ) / 1000 ) +
//                    " seconds!" );
        }
        else
        {
            throw new ExecutionException ( "No sentences or tokens to process!\n" +
                    "Please run a sentence splitter " +
                    "and tokeniser first!" );
        }

//OLD version
/*
    AnnotationSet as = inputAS.get(SENTENCE_ANNOTATION_TYPE);
    if(as != null && as.size() > 0){
    List sentences = new ArrayList(as);
    Collections.sort(sentences, offsetComparator);
    Iterator sentIter = sentences.iterator();
    int sentIndex = 0;
    int sentCnt = sentences.size();
    long startTime= System.currentTimeMillis();
    while(sentIter.hasNext()){
    start = System.currentTimeMillis();
    Annotation sentenceAnn = (Annotation)sentIter.next();
    AnnotationSet rangeSet = inputAS.get(
    sentenceAnn.getStartNode().getOffset(),
    sentenceAnn.getEndNode().getOffset());
    if(rangeSet == null) continue;
    AnnotationSet tokensSet = rangeSet.get(TOKEN_ANNOTATION_TYPE);
    if(tokensSet == null) continue;
    List tokens = new ArrayList(tokensSet);
    Collections.sort(tokens, offsetComparator);

    //          List tokens = (List)sentenceAnn.getFeatures().get("tokens");
    List sentence = new ArrayList(tokens.size());
    Iterator tokIter = tokens.iterator();
    while(tokIter.hasNext()){
    Annotation token = (Annotation)tokIter.next();
    String text = (String)token.getFeatures().get(TOKEN_STRING_FEATURE_NAME);
    sentence.add(text);
    }//while(tokIter.hasNext())

    //run the ArabicPOS over this sentence
    List sentences4tagger = new ArrayList(1);
    sentences4tagger.add(sentence);
    prepTime += System.currentTimeMillis() - start;
    start = System.currentTimeMillis();
    List taggerResults = tagger.runTagger(sentences4tagger);
    posTime += System.currentTimeMillis() - start;
    start = System.currentTimeMillis();
    //add the results to the output annotation set
    //we only get one sentence
    List sentenceFromTagger = (List)taggerResults.get(0);
    if(sentenceFromTagger.size() != sentence.size()){
    String taggerResult = "";
    for(int i = 0; i< sentenceFromTagger.size(); i++){
    taggerResult += ((String[])sentenceFromTagger.get(i))[1] + ", ";
    }
    throw new GateRuntimeException(
    "POS Tagger malfunction: the output size (" +
    sentenceFromTagger.size() +
    ") is different from the input size (" +
    sentence.size() + ")!" +
    "\n Input: " + sentence + "\nOutput: " + taggerResult);
    }
    for(int i = 0; i< sentence.size(); i++){
    String category = ((String[])sentenceFromTagger.get(i))[1];
    Annotation token = (Annotation)tokens.get(i);
    token.getFeatures().
    put(TOKEN_CATEGORY_FEATURE_NAME, category);
    }//for(i = 0; i<= sentence.size(); i++)
    postTime += System.currentTimeMillis() - start;
    fireProgressChanged(sentIndex++ * 100 / sentCnt);
    }//while(sentIter.hasNext())
    Out.prln("POS preparation time:" + prepTime);
    Out.prln("POS execution time:" + posTime);
    Out.prln("POS after execution time:" + postTime);
    fireProcessFinished();
    long endTime = System.currentTimeMillis();
    fireStatusChanged(document.getName() + " tagged in " +
    NumberFormat.getInstance().format(
    (double)(endTime - startTime) / 1000) + " seconds!");
    }else{
    throw new GateRuntimeException("No sentences to process!\n" +
    "Please run a sentence splitter first!");
    }//if(as != null && as.size() > 0)
     */
    }

    protected void addFeatures ( Annotation annot , String featureName , String featureValue ) throws GateRuntimeException
    {
        String tempIASN = inputASName == null ? "" : inputASName;
        String tempOASN = outputASName == null ? "" : outputASName;
        if ( outputAnnotationType.equals ( baseTokenAnnotationType ) && tempIASN.equals ( tempOASN ) )
        {
            annot.getFeatures ().put ( featureName , featureValue );
            return;
        }
        else
        {
            int start = annot.getStartNode ().getOffset ().intValue ();
            int end = annot.getEndNode ().getOffset ().intValue ();

            // get the annotations of type outputAnnotationType
            AnnotationSet outputAS = ( outputASName == null ) ? document.getAnnotations () : document.getAnnotations ( outputASName );
            AnnotationSet annotations = outputAS.get ( outputAnnotationType );
            if ( annotations == null || annotations.size () == 0 )
            {
                // add new annotation
                FeatureMap features = Factory.newFeatureMap ();
                features.put ( featureName , featureValue );
                try
                {
                    outputAS.add ( new Long ( start ) , new Long ( end ) , outputAnnotationType , features );
                }
                catch ( Exception e )
                {
                    throw new GateRuntimeException ( "Invalid Offsets" );
                }
            }
            else
            {
                // search for the annotation if there is one with the same start and end offsets
                ArrayList tempList = new ArrayList ( annotations.get () );
                boolean found = false;
                for ( int i = 0 ; i < tempList.size () ; i ++ )
                {
                    Annotation annotation = ( Annotation ) tempList.get ( i );
                    if ( annotation.getStartNode ().getOffset ().intValue () == start && annotation.getEndNode ().getOffset ().intValue () == end )
                    {
                        // this is the one
                        annotation.getFeatures ().put ( featureName , featureValue );
                        found = true;
                        break;
                    }
                }

                if (  ! found )
                {
                    // add new annotation
                    FeatureMap features = Factory.newFeatureMap ();
                    features.put ( featureName , featureValue );
                    try
                    {
                        outputAS.add ( new Long ( start ) , new Long ( end ) , outputAnnotationType , features );
                    }
                    catch ( Exception e )
                    {
                        throw new GateRuntimeException ( "Invalid Offsets" );
                    }
                }
            }
        }
    }

//    public void setLexiconURL ( java.net.URL newLexiconURL )
//    {
//        lexiconURL = newLexiconURL;
//    }

//    public java.net.URL getLexiconURL ()
//    {
//        return lexiconURL;
//    }

//    public void setRulesURL ( java.net.URL newRulesURL )
//    {
//        rulesURL = newRulesURL;
//    }
    public void setEncoding ( String encoding )
    {
        this.encoding = encoding;
    }

//    public java.net.URL getRulesURL ()
//    {
//        return rulesURL;
//    }
    public void setInputASName ( String newInputASName )
    {
        inputASName = newInputASName;
    }

    public String getInputASName ()
    {
        return inputASName;
    }

    public String getEncoding ()
    {
        return this.encoding;
    }

    public String getBaseTokenAnnotationType ()
    {
        return this.baseTokenAnnotationType;
    }

    public String getBaseSentenceAnnotationType ()
    {
        return this.baseSentenceAnnotationType;
    }

    public String getOutputAnnotationType ()
    {
        return this.outputAnnotationType;
    }

    public void setBaseTokenAnnotationType ( String baseTokenAnnotationType )
    {
        this.baseTokenAnnotationType = baseTokenAnnotationType;
    }

    public void setBaseSentenceAnnotationType ( String baseSentenceAnnotationtype )
    {
        this.baseSentenceAnnotationType = baseSentenceAnnotationtype;
    }

    public void setOutputAnnotationType ( String outputAnnotationType )
    {
        this.outputAnnotationType = outputAnnotationType;
    }

    public String getOutputASName ()
    {
        return this.outputASName;
    }

    public void setOutputASName ( String outputASName )
    {
        this.outputASName = outputASName;
    }
    private hepple.postag.POSTagger tagger;
    private java.net.URL lexiconURL;
    private java.net.URL rulesURL;
    private String inputASName;
    private String encoding;
    private String baseTokenAnnotationType;
    private String baseSentenceAnnotationType;
    private String outputAnnotationType;
    private String outputASName;
    private ANLP pos;

    public hepple.postag.POSTagger getTagger() {
        return tagger;
    }

    public java.net.URL getLexiconURL() {
        return lexiconURL;
    }

    public java.net.URL getRulesURL() {
        return rulesURL;
    }

    public ANLP getPos() {
        return pos;
    }

    public void setTagger(hepple.postag.POSTagger tagger) {
        this.tagger = tagger;
    }

    public void setLexiconURL(java.net.URL lexiconURL) {
        this.lexiconURL = lexiconURL;
    }

    public void setRulesURL(java.net.URL rulesURL) {
        this.rulesURL = rulesURL;
    }

    public void setPos(ANLP pos) {
        this.pos = pos;
    }
    
    
}
