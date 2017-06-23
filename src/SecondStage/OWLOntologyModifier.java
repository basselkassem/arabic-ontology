
package SecondStage;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

public class OWLOntologyModifier {
    private OWLOntologyManager  ontoManager;
    private OWLOntology ontology;
    private OWLDataFactory factory;
    private OWLReasonerFactory reasonerFactory ; 
    private OWLReasoner reasoner ; 
    private IRI ontologyIRI;
    private IRI documentIRI;
    private String log = "";
    private StringFormatter strFormatter;
    
    public OWLOntologyModifier(){
         ontoManager = OWLManager.createOWLOntologyManager();
         factory = ontoManager.getOWLDataFactory();
    }
    public void initReasoner(){
         reasonerFactory = new PelletReasonerFactory();
         reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
    }
    public void OntoLoad(String filePath){
        File file= new File(filePath);
        try {
            
            ontology=ontoManager.loadOntologyFromOntologyDocument(file);
            ontologyIRI=ontology.getOntologyID().getOntologyIRI();
            documentIRI= ontoManager.getOntologyDocumentIRI(ontology);
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OWLOntologyModifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void OntoSave(String filepath)  {
        File file = new File(filepath);
        try {
            ontoManager.saveOntology(ontology, IRI.create(file.toURI()));
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(OWLOntologyModifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void RemmoveIndividual(OWLNamedIndividual indiv){
        OWLEntityRemover remover = new OWLEntityRemover(this.ontoManager, Collections.singleton(this.ontology));
        indiv.accept(remover);
        this.ontoManager.applyChanges(remover.getChanges()); 
        remover.reset();        
    }
    public void RemoveClass(OWLClass cls){
         OWLEntityRemover remover = new OWLEntityRemover(this.ontoManager, Collections.singleton(this.ontology));
        ///System.out.println("Number of individuals: "+ this.ontology.getIndividualsInSignature().size());
        cls.accept(remover);
        this.ontoManager.applyChanges(remover.getChanges());
        //System.out.println("Number of individuals: "+ this.ontology.getIndividualsInSignature().size());
        // At this point, if we wanted to reuse the entity remover, we would
        // have to reset it
        remover.reset();
    }
    public OWLNamedIndividual GetIndiviByItsName(String name){
        OWLNamedIndividual res=null;
        name="<MyOntology:"+name+">";
        for(OWLNamedIndividual ind: this.ontology.getIndividualsInSignature()){
            String indS=ind.toString();
            if(name.compareTo(indS)==0){
                res=ind;
            }
        }
        return res;
    }
    public OWLClass GetClassByItsAnnotaion(String name){
        OWLClass res=null;
        OWLAnnotationProperty comment = factory.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI());
        for (OWLClass cls : this.ontology.getClassesInSignature()) {
            // Get the annotations on the class that use the label property
            for (OWLAnnotation annotation : cls.getAnnotations(this.ontology, comment)) {
                if (annotation.getValue() instanceof OWLLiteral) {
                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                    if (val.hasLang("ar")) {
                        if(name.compareTo(val.getLiteral().toString())==0){
                            res=cls;
                            //System.out.println( val.getLiteral());
                        }
                        
                    }
                }
            }
        }
        return res;
    }
    public Set<OWLIndividual> GetClassIndividuals(OWLClass cls){
        if(cls!=null){
            Set<OWLIndividual> indis=cls.getIndividuals(ontology);
            return indis;
        }
        return null;
    }
    public Vector<OWLClass> GetIndividualClass(OWLIndividual indi){
        Vector<OWLClass> res=new Vector<OWLClass>();        
        for(OWLClassExpression in:indi.getTypes(ontology)){
            res.add((OWLClass)in);
        }
        return res;
    }
    public Set<OWLNamedIndividual> GetAllIndividual() {
        Set<OWLNamedIndividual> res= ontology.getIndividualsInSignature();
        return res;
    }
    public void MeaningOneWordAlgo(String clsS,String indiS){
        
        OWLClass cls1=this.GetClassByItsAnnotaion(clsS);
        if(cls1!=null){
            Set<OWLIndividual> indiCls1=this.GetClassIndividuals(cls1);
            Set<OWLNamedIndividual>temp =this.GetAllIndividual();
            for(OWLIndividual in : temp){
                String inName=strFormatter.formateSentece(in.toString());
                 strFormatter.StringFormatterReset();
                inName=strFormatter.StemmString(inName);
                strFormatter.StringFormatterReset();
                if(indiS.compareTo(inName)==0){

                    Vector<OWLClass>cTemp= GetIndividualClass(in);
                    if(cTemp!=null){
                        for(int i=0;i<cTemp.size();i++){
                            AddIndividualToClass(cTemp.elementAt(i), indiCls1);
                            log += "Match Pattern 1: "+cTemp.elementAt(i)+" meaning: "+GetClassAnnotation(cTemp.elementAt(i)) +" with:\n[";
                            log+=cls1.toString()+" Meaning : "+GetClassAnnotation(cls1);
                            for(OWLIndividual indiv : indiCls1){
                                log += indiv.toString();
                            }
                            log +="]\n";

                        }
                    }
                    this.RemoveClass(cls1);

                }

            }
        }
    }
    public void GeneralAlgorithm(String newConc,String son,String relName){
         if(relName.compareTo("TypeOf")==0){
            OWLClass clsSon=GetClassByItsAnnotaion(son);
            OWLClass clsParent=GetClassByItsAnnotaion(newConc);
            if(clsParent==null){
                clsParent=CreateNewClass(newConc, newConc);
            }
            this.SetRelationTypeOf(clsSon,clsParent);
         }
         else if(relName.compareTo("Equivalent")==0){
            OWLClass clsSon=GetClassByItsAnnotaion(son);
            OWLClass clsParent=GetClassByItsAnnotaion(newConc);
            if(clsParent==null){
                clsParent=CreateNewClass(newConc, newConc);
            }
            this.SetRelationEquivalent(clsParent, clsSon);
         }
         else{
             
             OWLClass clsParent=GetClassByItsAnnotaion(newConc);
             OWLClass clsSon=GetClassByItsAnnotaion(son);
             if(clsParent==null){
                 clsParent=CreateNewClass(newConc, newConc);
             }
             System.out.println("new con: "+clsParent);
             System.out.println("son: "+clsSon);
             this.SetRelation( clsParent,clsSon,relName);             
         }
    }
    
  
    public void AddIndividualToClass(OWLClass cls,Set<OWLIndividual> indivs){
        if(indivs!=null && cls!=null) {
            for(OWLIndividual in:indivs){
                OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(cls, in);
                ontoManager.addAxiom(ontology, classAssertion);
                ontoManager.applyChange(new AddAxiom(ontology, classAssertion));
            }
        }
        
    }
    public void printOntologyAndImports(OWLOntologyManager manager,OWLOntology ontology) {
        System.out.println("Loaded ontology:");
        // Print ontology IRI and where it was loaded from (they will be the
        // same)
        printOntology(manager, ontology);
        // List the imported ontologies
        for (OWLOntology importedOntology : ontology.getImports()) {
            System.out.println("Imports:");
            printOntology(manager, importedOntology);
        }
    }
    public void printOntology(OWLOntologyManager manager, OWLOntology ontology) {
        IRI ontoIRI = ontology.getOntologyID().getOntologyIRI();
        IRI docIRI = manager.getOntologyDocumentIRI(ontology);
        System.out.println(ontoIRI == null ? "anonymous" : ontoIRI.toQuotedString());
        System.out.println("    from " + docIRI.toQuotedString());
    }
    public String GetClassAnnotation (OWLClass cls ) {
        String res="";
        OWLAnnotationProperty comment = factory
                .getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI());
       // for (OWLClass cls : this.ontology.getClassesInSignature()) {
            // Get the annotations on the class that use the label property
            for (OWLAnnotation annotation : cls.getAnnotations(this.ontology, comment)) {
                if (annotation.getValue() instanceof OWLLiteral) {
                    OWLLiteral val = (OWLLiteral) annotation.getValue();
                    if (val.hasLang("ar")) {
                        //System.out.println(cls + " ---> " + val.getLiteral());
                        res=val.getLiteral();
                    }
                }
            }
       // }
        return res;
    }
    ////////////////////////////////////////////////
    public OWLClass CreateNewClass(String name,String meaning){
        OWLClass word = factory.getOWLClass(IRI.create(ontologyIRI + name));
        OWLDeclarationAxiom wordDeclarationAxiom = factory.getOWLDeclarationAxiom(word);
        ontoManager.addAxiom(ontology, wordDeclarationAxiom);
        OWLAnnotation commentAnno2 = factory.getOWLAnnotation(factory.getRDFSComment(),factory.getOWLLiteral(meaning, "ar"));
        OWLAxiom ax_word = factory.getOWLAnnotationAssertionAxiom(word.getIRI(), commentAnno2);
        ontoManager.applyChange(new AddAxiom(ontology, ax_word));
        
        OWLClass concept=GetClassByItsAnnotaion("مفهوم");
        OWLClass noun = GetClassByItsAnnotaion("اسم");
        OWLAxiom w_n_axiom = factory.getOWLSubClassOfAxiom(word,noun);
        AddAxiom addAxiom1 = new AddAxiom(ontology, w_n_axiom);
        ontoManager.applyChange(addAxiom1);
        OWLAxiom w_c_axiom = factory.getOWLSubClassOfAxiom( word,concept);
        AddAxiom addAxiom2 = new AddAxiom(ontology, w_c_axiom);
        ontoManager.applyChange(addAxiom2);
        
       
        return word;
        
    }
    public void SetRelationTypeOf(OWLClass son,OWLClass parent){
        OWLAxiom w_c_axiom = factory.getOWLSubClassOfAxiom( son,parent);
        AddAxiom addAxiom2 = new AddAxiom(ontology, w_c_axiom);
        ontoManager.applyChange(addAxiom2);  
        OWLClass no=GetClassByItsAnnotaion("اسم");
        OWLClass con=GetClassByItsAnnotaion("مفهوم");
        DeleteSubClassProperty(no, son);
        DeleteSubClassProperty(con, son);
    }
    public void SetRelation(OWLClass parent,OWLClass son,String relName){
         OWLObjectProperty hasPart = factory.getOWLObjectProperty(IRI.create(ontologyIRI+ "#"+relName));
       
        // Now create a restriction to describe the class of individuals that
        // have at least one part that is a kind of nose
        OWLClassExpression hasPartSome = factory.getOWLObjectSomeValuesFrom(hasPart,parent);
        // Obtain a reference to the Head class so that we can specify that
        // Heads have noses
      
        // We now want to state that Head is a subclass of hasPart some Nose, to
        // do this we create a subclass axiom, with head as the subclass and
        // "hasPart some Nose" as the superclass (remember, restrictions are
        // also classes - they describe classes of individuals -- they are
        // anonymous classes).
        OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(son, hasPartSome);
        // Add the axiom to our ontology
        AddAxiom addAx = new AddAxiom(ontology, ax);
        //System.out.println(addAx);
        ontoManager.applyChange(addAx);
              
    }
    public void SetRelationEquivalent(OWLClass parent,OWLClass son){
        OWLEquivalentClassesAxiom hasPart;
        hasPart = factory.getOWLEquivalentClassesAxiom(parent,son);
        AddAxiom addAx = new AddAxiom(ontology, hasPart);
        ontoManager.applyChange(addAx);
    }
    public void DeleteSubClassProperty(OWLClass parent,OWLClass son){
      Set<OWLSubClassOfAxiom> temp=this.ontology.getAxioms(AxiomType.SUBCLASS_OF);
      //OWLEntityRemover rmove= new OWLEntityRemover(ontoManager, Collections.singleton(this.ontology));
      RemoveAxiom rmAx=null;
      for(OWLSubClassOfAxiom ax:temp){
         
          if(ax.getSubClass().compareTo(son)==0 && ax.getSuperClass().compareTo(parent)==0){
              //OWLAxiomChange rem=new RemoveAxiom(ontology, ax);
             rmAx =new RemoveAxiom(ontology, ax);
//              
//            System.out.println("sub : "+ax.getSubClass());
//            System.out.println("super : "+ax.getSuperClass());
          }               
      }
      if(rmAx!=null)
        ontoManager.applyChange(rmAx);
    }
    public void shouldCreateInferredAxioms() throws OWLOntologyCreationException,OWLOntologyStorageException {
          // Create a reasoner factory. In this case, we will use pellet, but we
        // could also use FaCT++ using the FaCTPlusPlusReasonerFactory. Pellet
        // requires the Pellet libraries (pellet.jar, aterm-java-x.x.jar) and
        // the XSD libraries that are bundled with pellet: xsdlib.jar and
        // relaxngDatatype.jar make sure these jars are on the classpath
        OWLReasonerFactory reasonerFactoryLocal = new StructuralReasonerFactory();
        // Uncomment the line below reasonerFactory = new
        // PelletReasonerFactory(); Load an example ontology - for the purposes
        // of the example, we will just load the pizza ontology.
      
        // Create the reasoner and classify the ontology
        OWLReasoner reasonerLocal = reasonerFactoryLocal.createNonBufferingReasoner(ontology);
        reasonerLocal.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        // To generate an inferred ontology we use implementations of inferred
        // axiom generators to generate the parts of the ontology we want (e.g.
        // subclass axioms, equivalent classes axioms, class assertion axiom
        // etc. - see the org.semanticweb.owlapi.util package for more
        // implementations). Set up our list of inferred axiom generators
        List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
        gens.add(new InferredSubClassAxiomGenerator());
        // Put the inferred axioms into a fresh empty ontology - note that there
        // is nothing stopping us stuffing them back into the original asserted
        // ontology if we wanted to do this.
        OWLOntology infOnt = ontoManager.createOntology();
        // Now get the inferred ontology generator to generate some inferred
        // axioms for us (into our fresh ontology). We specify the reasoner that
        // we want to use and the inferred axiom generators that we want to use.
        InferredOntologyGenerator iog = new InferredOntologyGenerator(reasonerLocal, gens);
        iog.fillOntology(ontoManager, infOnt);
        //ontology=infOnt;
        File file=new File("C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local3.owl");
        ontoManager.saveOntology(infOnt, IRI.create(file.toURI()));
        // ontoManager.saveOntology(infOnt, new StringDocumentTarget());
     }
 
    public static void main(String []args){
        String input="C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local.owl";
        //String output="C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/localout.owl";
        OWLOntologyModifier r=new OWLOntologyModifier();
        r.OntoLoad(input);
        OWLClass son= r.CreateNewClass("Bassel","bassel_m");
        OWLClass parent=r.CreateNewClass("Adnan","Adnan_m");
        OWLClass hiast=r.CreateNewClass("Hiast","Hiast_m");
         r.OntoLoad(input);
//        OWLClass no=r.GetClassByItsAnnotaion("اسم");
//        OWLClass con=r.GetClassByItsAnnotaion("مفهوم");
        //r.SetSubClass(r1, r2);
      // r.SetSubClass( parent,son);
     //   r.SetRelationEqu(son, parent);
       //r.GeneralAlgorithm("عدنان", "باسل", "TypeOf");
       r.GeneralAlgorithm("Hiast_m","خُضرةُ الأَرْض من الحشيش وليس من الورق.","Equivalent");
       // r.GeneralAlgorithm("عدنان", "باسل", "Equivalent");

//        r.DeleteSubClassProperty(no, r2);
//         r.DeleteSubClassProperty(con, r2);
//        try {
//            r.shouldCreateInferredAxioms();
//        } catch (OWLOntologyCreationException ex) {
//            Logger.getLogger(OntologyModifier.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (OWLOntologyStorageException ex) {
//            Logger.getLogger(OntologyModifier.class.getName()).log(Level.SEVERE, null, ex);
//        }
//         System.out.println("Number of individuals: "
//                + r.ontology.getIndividualsInSignature().size());
        r.printOntologyAndImports(r.ontoManager, r.ontology);
//        r.GetClassAnnotation();
//        String indiS="وليجة";
//        OWLNamedIndividual indi=r.GetIndiviByItsName(indiS);
//        r.RemmoveIndividual(indi);
//        System.out.println("Number of individuals: "
//                + r.ontology.getIndividualsInSignature().size());
        
//         System.out.println("======================");
//         String ipS="مَن تتَّخذه معتمدًا عليه من غير أَهلك.";
//         OWLClass cls=r.GetClassByItsAnnotaion(ipS);
//         System.out.println(cls.toString());
//         r.RemoveClass(cls);
     
//        String ipS="باسل";
//        OWLClass cls=r.GetClassByItsAnnotaion(ipS);
//        Set<OWLIndividual> res=r.GetClassIndividuals(cls);
//        for(OWLIndividual in : res){
//            System.out.println(in);
//            
//        }
//        String indiS="وليجة";
//        OWLIndividual indi=r.GetIndiviByItsName(indiS);
//        Vector<OWLClass> res=r.GetIndividualClass(indi);
//        for(OWLClass in : res){
//            System.out.println(in);
//        }
        
//        String clsS1="أداة";
//        String clsS2="اسم";
//        OWLClass cls2=r.GetClassByItsAnnotaion(clsS2);
//        Set<OWLIndividual> indiCls2=r.GetClassIndividuals(cls2);
//        r.RemoveClass(cls2);
//        OWLClass cls1=r.GetClassByItsAnnotaion(clsS1);
//        r.AddIndividualToClass(cls1, indiCls2);
          
//        Set<OWLNamedIndividual> res=r.GetAllIndividual();
//        for(OWLIndividual in : res){
//            System.out.println(in);
//        }
        r.OntoSave("C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local2.owl");
        //System.out.println(indi);
         
    }

    public OWLOntologyManager getOntoManager() {
        return ontoManager;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public OWLDataFactory getFactory() {
        return factory;
    }

    public OWLReasonerFactory getReasonerFactory() {
        return reasonerFactory;
    }

    public OWLReasoner getReasoner() {
        return reasoner;
    }

    public IRI getOntologyIRI() {
        return ontologyIRI;
    }

    public IRI getDocumentIRI() {
        return documentIRI;
    }

    public String getLog() {
        return log;
    }

    public StringFormatter getStrFormatter() {
        return strFormatter;
    }

    public void setOntoManager(OWLOntologyManager ontoManager) {
        this.ontoManager = ontoManager;
    }

    public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
    }

    public void setFactory(OWLDataFactory factory) {
        this.factory = factory;
    }

    public void setReasonerFactory(OWLReasonerFactory reasonerFactory) {
        this.reasonerFactory = reasonerFactory;
    }

    public void setReasoner(OWLReasoner reasoner) {
        this.reasoner = reasoner;
    }

    public void setOntologyIRI(IRI ontologyIRI) {
        this.ontologyIRI = ontologyIRI;
    }

    public void setDocumentIRI(IRI documentIRI) {
        this.documentIRI = documentIRI;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setStrFormatter(StringFormatter strFormatter) {
        this.strFormatter = strFormatter;
    }
}
