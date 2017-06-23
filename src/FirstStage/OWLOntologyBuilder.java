package FirstStage;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;


public class OWLOntologyBuilder {
    private String ontoName;
    private OWLOntologyManager  ontoManager;
    private OWLOntology ontology;
    private OWLDataFactory ontoFactory;
    private IRI ontologyIRI;
    private IRI documentIRI;
    private PrefixManager pm;

    public OWLOntologyBuilder(String ontoName,String ontoURL){
        // Create the manager that we will use to load ontologies.
        ontoManager = OWLManager.createOWLOntologyManager();
        ontologyIRI = IRI.create(ontoName);
        documentIRI = IRI.create(ontoURL);
        SimpleIRIMapper mapper = new SimpleIRIMapper(ontologyIRI, documentIRI);
        ontoManager.addIRIMapper(mapper);
        try {
            ontology = ontoManager.createOntology(ontologyIRI);
            ontoFactory = ontoManager.getOWLDataFactory();
            pm = new DefaultPrefixManager(ontologyIRI.toString());
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OWLOntologyBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void OntoCreateProtoType(Vector<ConceptStructure> _concepts){
        // Semantic Part
        OWLClass concept = ontoFactory.getOWLClass(IRI.create(ontologyIRI +"مفهوم"));
        OWLDeclarationAxiom conceptDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(concept);
        ontoManager.addAxiom(ontology, conceptDeclarationAxiom);
        OWLAnnotation commentAnno1 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("مفهوم", "ar"));
        OWLAxiom ax_concept = ontoFactory.getOWLAnnotationAssertionAxiom(concept.getIRI(), commentAnno1);
        ontoManager.applyChange(new AddAxiom(ontology, ax_concept));
        
        // Dictionary Part
        OWLClass word = ontoFactory.getOWLClass(IRI.create(ontologyIRI + "كلمة"));
        OWLDeclarationAxiom wordDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(word);
        ontoManager.addAxiom(ontology, wordDeclarationAxiom);
        OWLAnnotation commentAnno2 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("كلمة", "ar"));
        OWLAxiom ax_word = ontoFactory.getOWLAnnotationAssertionAxiom(word.getIRI(), commentAnno2);
        ontoManager.applyChange(new AddAxiom(ontology, ax_word));
        OWLClass noun = ontoFactory.getOWLClass(IRI.create(ontologyIRI + "اسم"));
        OWLDeclarationAxiom nounDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(noun);
        ontoManager.addAxiom(ontology, nounDeclarationAxiom);
        OWLAnnotation commentAnno3 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("اسم", "ar"));
        OWLAxiom ax_noun = ontoFactory.getOWLAnnotationAssertionAxiom(noun.getIRI(), commentAnno3);
        ontoManager.applyChange(new AddAxiom(ontology, ax_noun));
        OWLClass verb = ontoFactory.getOWLClass(IRI.create(ontologyIRI + "فعل"));
        OWLDeclarationAxiom verbDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(verb);
        ontoManager.addAxiom(ontology, verbDeclarationAxiom);
        OWLAnnotation commentAnno4 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("فعل", "ar"));
        OWLAxiom ax_verb = ontoFactory.getOWLAnnotationAssertionAxiom(verb.getIRI(), commentAnno4);
        ontoManager.applyChange(new AddAxiom(ontology, ax_verb));
        OWLClass tool = ontoFactory.getOWLClass(IRI.create(ontologyIRI + "أداة"));
        OWLDeclarationAxiom toolDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(tool);
        ontoManager.addAxiom(ontology, toolDeclarationAxiom);
        OWLAnnotation commentAnno5 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("أداة", "ar"));
        OWLAxiom ax_tool = ontoFactory.getOWLAnnotationAssertionAxiom(tool.getIRI(), commentAnno5);
        ontoManager.applyChange(new AddAxiom(ontology, ax_tool));
//        OWLClass prounoun = ontoFactory.getOWLClass(IRI.create(ontologyIRI + "ضمير"));
//        OWLDeclarationAxiom pronounDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(prounoun);
//        ontoManager.addAxiom(ontology, pronounDeclarationAxiom);
//        OWLAnnotation commentAnno6 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("ضمير", "ar"));
//        OWLAxiom ax_pronoun = ontoFactory.getOWLAnnotationAssertionAxiom(prounoun.getIRI(), commentAnno6);
//        ontoManager.applyChange(new AddAxiom(ontology, ax_pronoun));
        OWLClass khalfa = ontoFactory.getOWLClass(IRI.create(ontologyIRI + "خالفة"));
        OWLDeclarationAxiom khalfaDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(khalfa);
        ontoManager.addAxiom(ontology, khalfaDeclarationAxiom);
        OWLAnnotation commentAnno7 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("خالفة", "ar"));
        OWLAxiom ax_khalfa = ontoFactory.getOWLAnnotationAssertionAxiom(khalfa.getIRI(), commentAnno7);
        ontoManager.applyChange(new AddAxiom(ontology, ax_khalfa));
        OWLClass tarf = ontoFactory.getOWLClass(IRI.create(ontologyIRI + "ظرف"));
        OWLDeclarationAxiom tarfDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(tarf);
        ontoManager.addAxiom(ontology, tarfDeclarationAxiom);
        OWLAnnotation commentAnno8 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("ظرف", "ar"));
        OWLAxiom ax_tarf = ontoFactory.getOWLAnnotationAssertionAxiom(tarf.getIRI(), commentAnno8);
        ontoManager.applyChange(new AddAxiom(ontology, ax_tarf));
        OWLClass adjective = ontoFactory.getOWLClass(IRI.create(ontologyIRI + "صفة"));
        OWLDeclarationAxiom adjectiveDeclarationAxiom = ontoFactory.getOWLDeclarationAxiom(adjective);
        ontoManager.addAxiom(ontology, adjectiveDeclarationAxiom);
        OWLAnnotation commentAnno9 = ontoFactory.getOWLAnnotation(ontoFactory.getRDFSComment(),ontoFactory.getOWLLiteral("صفة", "ar"));
        OWLAxiom ax_adjective = ontoFactory.getOWLAnnotationAssertionAxiom(adjective.getIRI(), commentAnno9);
        ontoManager.applyChange(new AddAxiom(ontology, ax_adjective));
        
        OWLAxiom w_n_axiom = ontoFactory.getOWLSubClassOfAxiom(noun, word);
        OWLAxiom v_n_axiom = ontoFactory.getOWLSubClassOfAxiom(verb, word);
        OWLAxiom t_n_axiom = ontoFactory.getOWLSubClassOfAxiom(tool, word);
        OWLAxiom k_n_axiom = ontoFactory.getOWLSubClassOfAxiom(khalfa, word);
        OWLAxiom ta_n_axiom = ontoFactory.getOWLSubClassOfAxiom(tarf, word);
        OWLAxiom ad_n_axiom = ontoFactory.getOWLSubClassOfAxiom(adjective, word);
        AddAxiom addAxiom1 = new AddAxiom(ontology, w_n_axiom);
        AddAxiom addAxiom2 = new AddAxiom(ontology, v_n_axiom);
        AddAxiom addAxiom3 = new AddAxiom(ontology, t_n_axiom);
        AddAxiom addAxiom4 = new AddAxiom(ontology, k_n_axiom);
        AddAxiom addAxiom5 = new AddAxiom(ontology, ta_n_axiom);
        AddAxiom addAxiom6 = new AddAxiom(ontology, ad_n_axiom);
       
        ontoManager.applyChange(addAxiom1);
        ontoManager.applyChange(addAxiom2);
        ontoManager.applyChange(addAxiom3);
        ontoManager.applyChange(addAxiom4);
        ontoManager.applyChange(addAxiom5);
        ontoManager.applyChange(addAxiom6);
        
        // Adding Individuals to Both Parts
        for(int i=0;i<_concepts.size();i++)
            this.OntoAddClass(noun, concept, _concepts.elementAt(i));
       // this.OntoSave();
    }
    public void OntoAddClass(OWLClass word, OWLClass concept, ConceptStructure indiv) {
        OWLClass clsA = ontoFactory.getOWLClass(IRI.create(ontologyIRI + indiv.getName()));
        OWLDeclarationAxiom declarationAxiom = ontoFactory.getOWLDeclarationAxiom(clsA);
        ontoManager.addAxiom(ontology, declarationAxiom);
        OWLAnnotation commentAnno = ontoFactory.getOWLAnnotation(
                ontoFactory.getRDFSComment(),
                ontoFactory.getOWLLiteral(indiv.getMeaning(), "ar"));
        OWLAxiom ax = ontoFactory.getOWLAnnotationAssertionAxiom(clsA.getIRI(), commentAnno);
        ontoManager.applyChange(new AddAxiom(ontology, ax));
        
        OWLAxiom x_c_axiom = ontoFactory.getOWLSubClassOfAxiom(clsA, concept);
        AddAxiom addAxiom1 = new AddAxiom(ontology, x_c_axiom);
        ontoManager.applyChange(addAxiom1);
        //adding individuals
         for(int i=0;i<indiv.getObjectVec().size();i++) {
             this.OntoAddIndividualToClass(word,indiv.getObjectVec().elementAt(i));
         }
         for(int i=0;i<indiv.getObjectVec().size();i++) {
             this.OntoAddIndividualToClass(clsA,indiv.getObjectVec().elementAt(i));
         }
    }
    public void OntoAddIndividualToClass(OWLClass node,ObjectStructure indiv) {
        
        OWLNamedIndividual indivdual = ontoFactory.getOWLNamedIndividual(IRI.create(ontologyIRI +indiv.getWord()));
        OWLClassAssertionAxiom classAssertion = ontoFactory.getOWLClassAssertionAxiom(node, indivdual);
        ontoManager.addAxiom(ontology, classAssertion);
        ontoManager.applyChange(new AddAxiom(ontology, classAssertion));
        
   
        
        OWLDataProperty hasVocalized = ontoFactory.getOWLDataProperty(IRI.create(ontologyIRI +"#hasVocalized"));
        OWLAxiom   dataVoclaizedAssertion = ontoFactory
                .getOWLDataPropertyAssertionAxiom(hasVocalized, indivdual, indiv.getVocalizedWord());
        
        OWLDataProperty hasType = ontoFactory.getOWLDataProperty(IRI.create(ontologyIRI +"#hasType"));
        OWLAxiom   dataTypeAssertion = ontoFactory
                .getOWLDataPropertyAssertionAxiom(hasType, indivdual, indiv.getType());
        ontoManager.addAxiom(ontology, dataTypeAssertion);
        ontoManager.applyChange(new AddAxiom(ontology, dataVoclaizedAssertion));
        for(int i=0;i<indiv.getExampleVector().size();i++) {
            OWLDataProperty hasExample = ontoFactory.getOWLDataProperty(IRI.create(ontologyIRI +"#hasExample"));
            OWLDataPropertyAssertionAxiom dataExampleAssertion = ontoFactory
                .getOWLDataPropertyAssertionAxiom(hasExample, indivdual, indiv.getExampleVector().elementAt(i));
           ontoManager.addAxiom(ontology, dataExampleAssertion);  
           ontoManager.applyChange(new AddAxiom(ontology, dataExampleAssertion));
        }
     
    }
    public void OntoSave(String path)  {
        File file = new File(path);
        try {
            ontoManager.saveOntology(ontology, IRI.create(file.toURI()));
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(OWLOntologyBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String [] args) throws OWLOntologyStorageException{
        DBConnector cc = new DBConnector("jdbc:mysql://localhost:3306/","ardic_utf8","com.mysql.jdbc.Driver","root","root");
            boolean t = cc.TestConnection();
        System.out.println(cc.getMessage());
        ResultSet res = cc.GetData("finalview");
         ConceptsBuilder ss = null;
        try
        {
          ss = new ConceptsBuilder(res,500);
          ss.PreBuildConceptVec();
           ss.BuildingProcedure();
           System.out.println(ss.getConceptVec().size());
        }catch (Exception ex){

        }
        
       String name="http://www.HIAST.edu.sy/ArabicOntology/";
        //String name="ArabicOntology";
        //String patrh=System.getProperty("user.dir");
        //System.out.println(patrh);
        String patrh=" C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local10.owl";
        OWLOntologyBuilder c=new OWLOntologyBuilder(name, patrh);
        c.OntoCreateProtoType(ss.getConceptVec());
        c.OntoSave(patrh);
        System.out.println("=====================");
        System.out.println("Number of Concepts: "+ c.ontology.getClassesInSignature().size());
        System.out.println("Number of Individual: "+ c.ontology.getIndividualsInSignature().size());
//        
//        
//        
//        
//        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//        // Let's load an ontology from the web
//        
//        File file = new File("C:/Users/Abo_ADNAN/Documents/NetBeansProjects/Project9_10/local.owl");
//        OWLOntology pizzaOntology = null;
//        try {
//            pizzaOntology = manager.loadOntologyFromOntologyDocument(file);
//        } catch (OWLOntologyCreationException ex) {
//            Logger.getLogger(ConceptBuilder.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("Loaded ontology: " + pizzaOntology);
//        for (OWLClass cls : pizzaOntology.getClassesInSignature()) {
//            System.out.println("Referenced class: " + cls);
//            
//        }
//        
    }

    public String getOntoName() {
        return ontoName;
    }

    public OWLOntologyManager getOntoManager() {
        return ontoManager;
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    public OWLDataFactory getFactory() {
        return ontoFactory;
    }

    public IRI getOntologyIRI() {
        return ontologyIRI;
    }

    public IRI getDocumentIRI() {
        return documentIRI;
    }

    public PrefixManager getPm() {
        return pm;
    }

    public void setOntoName(String ontoName) {
        this.ontoName = ontoName;
    }

    public void setOntoManager(OWLOntologyManager ontoManager) {
        this.ontoManager = ontoManager;
    }

    public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
    }

    public void setFactory(OWLDataFactory factory) {
        this.ontoFactory = factory;
    }

    public void setOntologyIRI(IRI ontologyIRI) {
        this.ontologyIRI = ontologyIRI;
    }

    public void setDocumentIRI(IRI documentIRI) {
        this.documentIRI = documentIRI;
    }

    public void setPm(PrefixManager pm) {
        this.pm = pm;
    }

    public OWLDataFactory getOntoFactory() {
        return ontoFactory;
    }

    public void setOntoFactory(OWLDataFactory ontoFactory) {
        this.ontoFactory = ontoFactory;
    }
    
}


