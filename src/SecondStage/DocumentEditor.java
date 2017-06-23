/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SecondStage;
/*
 *  Copyright (c) 1998-2007, The University of Sheffield.
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan 13/11/2000
 *
 *  $Id: DocumentEditor.java 10039 2008-11-18 17:27:08Z valyt $
 *
 */


import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.print.*;
import java.beans.*;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.*;
import javax.swing.tree.*;
import gate.*;
import gate.corpora.DocumentContentImpl;
import gate.creole.*;
import gate.event.*;
import gate.gui.Handle;
import gate.gui.MainFrame;
import gate.gui.OkCancelDialog;
import gate.gui.ResizableVisualResource;
import gate.gui.TextAttributesChooser;
import gate.print.JComponentPrinter;
import gate.swing.*;
import gate.util.*;

/**
 * This class implements a viewer/editor for the annotations on a document.
 * As a viewer, this visual resource will display all the annotations found on
 * the document. The editor needs to have some data about annotation types in
 * order to allow the editing of annotations. This data comes from the
 * {@link gate.creole.AnnotationSchema} objects that are loaded in the Gate
 * system at a given moment. If there are no such objects the editing of
 * annotations will be restricted to a very crude method allowing the user to
 * add any type of annotations having any features with any String values.
 *
 * This class has been deprecated! The document editing functionality is now
 * provided by the {@link gate.gui.docview.DocumentEditor} class.
 *
 * @deprecated
 */
public class DocumentEditor extends AbstractVisualResource
                            implements ANNIEConstants{
  //properties
  private transient PropertyChangeSupport propertyChangeListeners =
                                          new PropertyChangeSupport(this);
  /**
   * The {@link gate.Document} currently displayed.
   */
  private gate.Document document;

  /**
   * A random colour generator used to generate initial default colours for
   * highlighting various types of annotations.
   */
  protected ColorGenerator colGenerator = new ColorGenerator();

  //GUI components
  /** The text display.*/
  protected JTextPane textPane;

  /** Scroller used for the text diaplay*/
  protected JScrollPane textScroll;

  /** The table placed below the text display used for showing annotations*/
  protected XJTable annotationsTable;

  /**Model for the annotations table*/
  protected AnnotationsTableModel annotationsTableModel;

  /** Scroller for the annotations table*/
  protected JScrollPane tableScroll;

  /*The split that contains the text(top) and the annotations table(bottom)*/
  protected JSplitPane leftSplit;

  /**
   * The split that contains the styles tree and the coreference viewer.
   */
  protected JSplitPane rightSplit;

  /**
   * The main horizontal split that contains all the contents of this viewer
   */
  protected JSplitPane mainSplit;

  /**
   * The right hand side tree with all  the annotation sets and types of
   * annotations
   */
  protected JTree stylesTree;

  /**
   * The toolbar displayed on the top part of the component
   */
  protected JToolBar toolbar;

  /**Scroller for the styles tree*/
  protected JScrollPane stylesTreeScroll;

  /**The root for the styles tree*/
  protected DefaultMutableTreeNode stylesTreeRoot;

  /**The model for the styles tree*/
  protected DefaultTreeModel stylesTreeModel;

  /**The dialog used for text search*/
  protected SearchDialog searchDialog;

  /**The dialog used for editing the styles used to highlight annotations*/
  protected TextAttributesChooser styleChooser;


  /**
   * The Jtree that displays the coreference data
   */
  protected JTree corefTree;
  /**
   * The root for the coref tree
   */
  protected DefaultMutableTreeNode corefTreeRoot;

  /**
   * The model for the coref tree
   */
  protected DefaultTreeModel corefTreeModel;

  /** The scroller for the coref list*/
  protected JScrollPane corefScroll;

  /**
   * A box containing a {@link javax.swing.JProgressBar} used to keep the user
   * entertained while the text display is being updated
   */
  protected Box progressBox;

  /**The progress bar used during updating the text*/
  protected JProgressBar progressBar;

  /**
   * The highlighter used to help the user select annotations that overlap
   * and for highligting in the text the annotations selected in the lower
   * table.
   */
  protected Highlighter highlighter;

  /**
   * This highlighter is actually used as a data structure. It is used to keep
   * the data for the selected annotations; the actual highlighting will be
   * done by the {@link #highlighter} as using two different
   * highlighters on the same text component is looking for trouble.
   */
  protected Highlighter selectionHighlighter;

  /**
   * The object responsible with blinking the selected annotations.
   */
  protected SelectionBlinker selectionBlinker;


  protected Handle myHandle;

  /**
   * holds the data for the  annotations table: a list of Annotation objects
   */
  protected java.util.List data;

  /**
   * a list containing {@link Range} objects. These are the
   * ranges in the {@link #data} structure. A range is a bunch
   * of annotations belonging to the same annotation set that are contiguous
   * in the {@link #data} structure.
   */
  protected java.util.List ranges;

  /**
   * A composed map used to get the metadata for an annotation type starting
   * from the annotation set name and the type name.
   * Annotation set name -> Annotation type -> {@link TypeData}
   * Maps from String to Map to {@link TypeData}.
   */
  protected Map typeDataMap;

  /**
   * The listener for the events coming from the document (annotations and
   * annotation sets added or removed).
   */
  protected EventsHandler eventHandler;


  /**
   * Object used to sychronise all the various threads involved in GUI
   * updating;
   */
  protected Object lock;

  /**Should the table be visible*/

  /**Should the text be visible*/

  /**
   * Should the right hand side tree be visible. That tree is used to select
   * what types of annotations are visible in the text display, hence the name
   * filters.
   */

  /**Should this component bahave as an editor as well as an viewer*/
  private boolean editable = true;



  private JToggleButton textVisibleBtn;
  private JToggleButton typesTreeVisibleBtn;
  private JToggleButton annotationsTableVisibleBtn;
  private JToggleButton coreferenceVisibleBtn;
  private boolean annotationsTableVisible = false;
  private boolean coreferenceVisible = false;
  private boolean textVisible = true;
  private boolean typesTreeVisible = false;
  private boolean corefOptionAvailable = false;

  /**
   * Default constructor. Creats all the components and initialises all the
   * internal data to default values where possible.
   */
  public DocumentEditor() {
  }

  public Resource init(){
    initLocalData();
    initGuiComponents();
    initListeners();
    return this;
  }

  /**
   * Initialises all the listeners that this component has to register with
   * other classes.
   */
  protected void initListeners() {
    //listen for our own properties change events
    this.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equals("annotationsTableVisible") ||
           e.getPropertyName().equals("coreferenceVisible") ||
           e.getPropertyName().equals("textVisible") ||
           e.getPropertyName().equals("typesTreeVisible")){
          layoutComponents();
        }else if(e.getPropertyName().equals("corefOptionAvailable")){
          if(((Boolean)e.getNewValue()).booleanValue()){
            if(toolbar.getComponentIndex(coreferenceVisibleBtn) == -1)
              toolbar.add(coreferenceVisibleBtn, 3);
          }else{
            toolbar.remove(coreferenceVisibleBtn);
          }
          layoutComponents();
        }
      }
    });

    textVisibleBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setTextVisible(textVisibleBtn.isSelected());
      }
    });

    annotationsTableVisibleBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setAnnotationsTableVisible(annotationsTableVisibleBtn.isSelected());
      }
    });


    typesTreeVisibleBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setTypesTreeVisible(typesTreeVisibleBtn.isSelected());
      }
    });


    coreferenceVisibleBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setCoreferenceVisible(coreferenceVisibleBtn.isSelected());
      }
    });

    stylesTree.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
          //where inside the tree?
          int x = e.getX();
          int y = e.getY();
          TreePath path = stylesTree.getPathForLocation(x, y);
          if(path != null){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.
                                         getLastPathComponent();
            TypeData nData = (TypeData)node.getUserObject();
            //where inside the cell?
            Rectangle cellRect = stylesTree.getPathBounds(path);
            x -= cellRect.x;
            y -= cellRect.y;
            Component cellComp = stylesTree.getCellRenderer().
                                 getTreeCellRendererComponent(stylesTree,
                                                              node, true,
                                                              false, false,
                                                              0, true);
//            cellComp.setSize(cellRect.width, cellRect.height);
            cellComp.setBounds(cellRect);
            Component clickedComp = cellComp.getComponentAt(x, y);

            if(clickedComp instanceof JCheckBox){
              nData.setVisible(! nData.getVisible());
//              stylesTree.repaint(cellRect);
              stylesTreeModel.nodeChanged(node);
            // Check if the click indicates a shortcut to create an annotation
            }else if( e.getClickCount() == 1 &&
                      clickedComp instanceof JLabel &&
                      isTextSelected()){
              // Here creates an annotation with the selected text into the
              // target annotation set

              if(!editable) return;
              Long startOffset = new Long(textPane.getSelectionStart());
              Long endOffset = new Long(textPane.getSelectionEnd());
              TreePath treePath = stylesTree.getSelectionPath();
              TypeData typeData = (TypeData)((DefaultMutableTreeNode)
                              treePath.getLastPathComponent()).getUserObject();
              String setName = typeData.getSet();
              if(typeData.getType() == null){
                // The set is empty. It will not create an annotation.
                // Loose the selection and return
                textPane.setSelectionStart(startOffset.intValue());
                textPane.setSelectionEnd(startOffset.intValue());
                return;
              }// End if
              try{
                if ("Default".equals(setName)){
                  document.getAnnotations().add(startOffset,
                                                endOffset,
                                                typeData.getType(),
                                                Factory.newFeatureMap());
                }else{
                  document.getAnnotations(setName).add( startOffset,
                                                        endOffset,
                                                        typeData.getType(),
                                                       Factory.newFeatureMap());
                }// End if
              } catch(gate.util.InvalidOffsetException ioe){
                throw new GateRuntimeException(ioe.getMessage());
              }// End try
              // Loose the selection
              textPane.setSelectionStart(startOffset.intValue());
              textPane.setSelectionEnd(startOffset.intValue());
            }else if(clickedComp instanceof JLabel &&
                     e.getClickCount() == 2){
              if(styleChooser == null){
                Window parent = SwingUtilities.getWindowAncestor(
                                  DocumentEditor.this);
                styleChooser = parent instanceof Frame ?
                               new TextAttributesChooser((Frame)parent,
                                                         "Please select your options",
                                                         true) :
                               new TextAttributesChooser((Dialog)parent,
                                                         "Please select your options",
                                                         true);

              }

              styleChooser.setLocationRelativeTo(stylesTree);
              nData.setAttributes(
                    styleChooser.show(nData.getAttributes().copyAttributes()));
              stylesTreeModel.nodeChanged(node);
//              stylesTree.repaint(cellRect);
            }
          }
        }
      }
    });

    stylesTree.addComponentListener(new ComponentAdapter() {
      public void componentHidden(ComponentEvent e) {

      }

      public void componentMoved(ComponentEvent e) {
      }

      public void componentResized(ComponentEvent e) {
        SwingUtilities.invokeLater(new Runnable(){
          public void run(){
            Enumeration nodes = stylesTreeRoot.depthFirstEnumeration();
            while(nodes.hasMoreElements()){
              stylesTreeModel.nodeChanged((TreeNode)nodes.nextElement());
            }
          }
        });
      }

      public void componentShown(ComponentEvent e) {
      }
    });

    //clear selection in table on outside clicks
    tableScroll.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        Point location = e.getPoint();
        if(!tableScroll.getViewport().getView().getBounds().contains(location)){
          //deselect everything in the table
          annotationsTable.clearSelection();
        }
      }
    });

    annotationsTable.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        int row = annotationsTable.rowAtPoint(e.getPoint());
        Annotation ann = (Annotation)annotationsTable.getModel().
                                                      getValueAt(row, -1);
        //find the annotation set
        String setName = (String)annotationsTable.getModel().
                                                    getValueAt(row, 1);
        AnnotationSet set = setName.equals("Default")?
                            document.getAnnotations() :
                            document.getAnnotations(setName);

        EditAnnotationAction editAnnAct = new EditAnnotationAction(set, ann);
        if(SwingUtilities.isLeftMouseButton(e)){
          if(e.getClickCount() == 1){
          }else if(e.getClickCount() == 2){
            //double left click -> edit the annotation
            if(editable) editAnnAct.actionPerformed(null);
          }
        } else if(SwingUtilities.isRightMouseButton(e)) {
          //right click
          //add select all option
          JPopupMenu popup = new XJPopupMenu();
          popup.add(new AbstractAction(){
            {
              putValue(NAME, "Select all");
            }
            public void actionPerformed(ActionEvent evt){
              annotationsTable.selectAll();
            }
          });

//          popup.addSeparator();
          //add save preserving format
//          popup.add(new DumpPreserveFormatAction());
          if(editable){
            //add delete option
            popup.addSeparator();
            popup.add(new DeleteSelectedAnnotationsAction(annotationsTable));
            popup.addSeparator();
            popup.add(new XJMenuItem(editAnnAct, myHandle));
          }
          popup.show(annotationsTable, e.getX(), e.getY());
        }
      }
    });//annotationsTable.addMouseListener(new MouseAdapter()



    annotationsTable.getInputMap().put(
                  KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0),
                  "Delete");
    annotationsTable.getActionMap().put(
                        "Delete",
                        new DeleteSelectedAnnotationsAction(annotationsTable));

    stylesTree.getInputMap().put(
                  KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0),
                  "Delete");
    stylesTree.getActionMap().put(
                        "Delete",
                        new DeleteSelectedAnnotationsAction(stylesTree));

    corefTree.getInputMap().put(
                  KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0),
                  "Delete");
    corefTree.getActionMap().put(
                        "Delete",
                        new DeleteSelectedAnnotationsAction(corefTree));


    //takes care of highliting the selected annotations
    annotationsTable.getSelectionModel().addListSelectionListener(
      new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent e){
          int[] rows = annotationsTable.getSelectedRows();
          synchronized(selectionHighlighter){
            selectionHighlighter.removeAllHighlights();
          }
          for(int i = 0; i < rows.length; i++){
            int start = ((Long)annotationsTable.getModel().
                         getValueAt(rows[i], 2)
                        ).intValue();
            int end = ((Long)annotationsTable.getModel().
                       getValueAt(rows[i], 3)
                      ).intValue();

            // compute correction for new line breaks in long lines
            start += longLinesCorrection(start);
            end += longLinesCorrection(end);

            //bring the annotation in view
            try{
              Rectangle startRect = textPane.modelToView(start);
              Rectangle endRect = textPane.modelToView(end);
              SwingUtilities.computeUnion(endRect.x, endRect.y,
                                          endRect.width, endRect.height,
                                          startRect);
              textPane.scrollRectToVisible(startRect);
              annotationsTable.requestFocus();
            }catch(BadLocationException ble){
              throw new GateRuntimeException(ble.toString());
            }
            //start blinking the annotation
            try{
              synchronized (selectionHighlighter){
                selectionHighlighter.addHighlight(start, end,
                            DefaultHighlighter.DefaultPainter);
              }
            }catch(BadLocationException ble){
              throw new GateRuntimeException(ble.toString());
            }
          }//for(int i = 0; i < rows.length; i++)
          //start the blinker
          selectionBlinker.testAndStart();
        }
      });


    textPane.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
          int position = textPane.viewToModel(e.getPoint());
          if(textPane.getSelectionStart() ==  textPane.getSelectionEnd()){
            //no selection -> select an annotation
            JPopupMenu popup = new XJPopupMenu("Select:");
            //find annotations at this position
            Iterator<Annotation> annIter = document.getAnnotations().
                                        get(new Long(position),
                                            new Long(position)
                                        ).iterator();
            if(annIter.hasNext()){
              JMenu menu = new XJMenu("Default");
              popup.add(menu);
              while(annIter.hasNext()){
                Annotation ann = annIter.next();
                menu.add(new HighlightAnnotationMenu(ann,
                                                     document.getAnnotations()));
              }
            }
            Map namedASs = document.getNamedAnnotationSets();
            if(namedASs != null){
              Iterator namedASiter = namedASs.values().iterator();
              while(namedASiter.hasNext()){
                //find annotations at this position
                AnnotationSet set = (AnnotationSet)namedASiter.next();
                annIter = set.get(new Long(position), new Long(position)).
                              iterator();
                if(annIter.hasNext()){
                  JMenu menu = new XJMenu(set.getName());
                  popup.add(menu);
                  while(annIter.hasNext()){
                    Annotation ann = annIter.next();
                    menu.add(new HighlightAnnotationMenu(ann,set));
                  }
                }
              }
            }
            popup.show(textPane, e.getPoint().x, e.getPoint().y);
          } else {
            //there is selected text -> create a new annotation
            if(!editable) return;
            Long startOffset = new Long(textPane.getSelectionStart());
            Long endOffset = new Long(textPane.getSelectionEnd());
            JPopupMenu popup = new XJPopupMenu();
            //add new annotation in the Default AS
            JMenu menu = new XJMenu("Add annotation to \"Default\"");
            menu.add(new XJMenuItem(
                         new NewAnnotationAction(document.getAnnotations(),
                                                 startOffset, endOffset),
                         myHandle));
            java.util.List customisedAnnTypes = Gate.getCreoleRegister().
                                                getVREnabledAnnotationTypes();
            if(!customisedAnnTypes.isEmpty()){
              menu.addSeparator();
              Iterator typesIter = customisedAnnTypes.iterator();
              while(typesIter.hasNext()){
                menu.add(new XJMenuItem(
                             new NewAnnotationAction(document.getAnnotations(),
                                                     (String)typesIter.next(),
                                                     startOffset, endOffset),
                             myHandle));
              }
            }//if(!customisedAnnTypes.isEmpty())
            popup.add(menu);

            //add a new annotation to a named AnnotationSet
            if(document.getNamedAnnotationSets() != null){
              Iterator annSetsIter = document.getNamedAnnotationSets().
                                              keySet().iterator();
              if(annSetsIter.hasNext()) popup.addSeparator();
              while(annSetsIter.hasNext()){
                AnnotationSet set = document.getAnnotations(
                                             (String)annSetsIter.next());


                menu = new XJMenu("Add annotation to \"" + set.getName() + "\"");
                menu.add(new XJMenuItem(
                             new NewAnnotationAction(set, startOffset, endOffset),
                             myHandle));
                if(!customisedAnnTypes.isEmpty()){
                  menu.addSeparator();
                  Iterator typesIter = customisedAnnTypes.iterator();
                  while(typesIter.hasNext()){
                    menu.add(new XJMenuItem(
                                 new NewAnnotationAction(set,
                                                         (String)typesIter.next(),
                                                         startOffset, endOffset),
                                 myHandle));
                  }
                }//if(!customisedAnnTypes.isEmpty())
                popup.add(menu);
              }//while(annSetsIter.hasNext())
            }

            //add to a new annotation set
            menu = new XJMenu("Add annotation to a new set");
            menu.add(new XJMenuItem(
                         new NewAnnotationAction(null, startOffset, endOffset),
                         myHandle));
            if(!customisedAnnTypes.isEmpty()){
              menu.addSeparator();
              Iterator typesIter = customisedAnnTypes.iterator();
              while(typesIter.hasNext()){
                menu.add(new XJMenuItem(
                             new NewAnnotationAction(null,
                                                     (String)typesIter.next(),
                                                     startOffset, endOffset),
                             myHandle));
              }
            }//if(!customisedAnnTypes.isEmpty())
            popup.add(menu);
            //show the popup
            popup.show(textPane, e.getPoint().x, e.getPoint().y);
          }//there is selected text
        }//if(SwingUtilities.isRightMouseButton(e))
      }//mouse clicked

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }
    });

    //when the highlighter changes we need to get a hold of the new one
    textPane.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equals("highlighter")){
          highlighter = textPane.getHighlighter();
          selectionHighlighter.install(textPane);
        }
      }
    });

    corefTree.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
          //where inside the tree?
          int x = e.getX();
          int y = e.getY();
          TreePath path = corefTree.getPathForLocation(x, y);
          if(path != null){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.
                                         getLastPathComponent();
            //where inside the cell?
            Rectangle cellRect = corefTree.getPathBounds(path);
            x -= cellRect.x;
            y -= cellRect.y;
            Component cellComp = corefTree.getCellRenderer().
                                 getTreeCellRendererComponent(corefTree,
                                                              node, true,
                                                              false, false,
                                                              0, true);
            cellComp.setBounds(cellRect);
            Component clickedComp = cellComp.getComponentAt(x, y);
            if(clickedComp instanceof LazyJPanel)
              clickedComp = clickedComp.getComponentAt(x, y);
            if(node.getUserObject() instanceof CorefData &&
               clickedComp instanceof JCheckBox){
              CorefData cData = (CorefData)node.getUserObject();
              cData.setVisible(!cData.getVisible());
              corefTreeModel.nodeChanged(node);
            }
          }
        }
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }
    });



    corefTree.addComponentListener(new ComponentAdapter() {
      public void componentHidden(ComponentEvent e) {

      }

      public void componentMoved(ComponentEvent e) {
      }

      public void componentResized(ComponentEvent e) {
        SwingUtilities.invokeLater(new Runnable(){
          public void run(){
            Enumeration nodes = corefTreeRoot.depthFirstEnumeration();
            while(nodes.hasMoreElements()){
              corefTreeModel.nodeChanged((TreeNode)nodes.nextElement());
            }
          }
        });
      }

      public void componentShown(ComponentEvent e) {
      }
    });
  }//protected void initListeners()

  /**
   * Initialises the local variables to their default values
   */
  protected void initLocalData(){
    //init local vars
    lock = new Object();

    data = Collections.synchronizedList(new ArrayList());
    //dataAsAS = new gate.annotation.AnnotationSetImpl(document);
    ranges = new ArrayList();

    typeDataMap = new HashMap();

    eventHandler = new EventsHandler();

  }//protected void initLocalData()

  /**Builds all the graphical components*/
  protected void initGuiComponents(){
    //initialise GUI components
//    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setLayout(new BorderLayout());

    //the toolbar
    toolbar = new JToolBar(JToolBar.HORIZONTAL);
    toolbar.setAlignmentX(Component.LEFT_ALIGNMENT);
    toolbar.setAlignmentY(Component.TOP_ALIGNMENT);
    toolbar.setFloatable(false);
    this.add(toolbar, BorderLayout.NORTH);

    textVisibleBtn = new JToggleButton("Text", textVisible);
    toolbar.add(textVisibleBtn);

    annotationsTableVisibleBtn = new JToggleButton("Annotations",
                                                   annotationsTableVisible);
    toolbar.add(annotationsTableVisibleBtn);

    typesTreeVisibleBtn = new JToggleButton("Annotation Sets", typesTreeVisible);
    toolbar.add(typesTreeVisibleBtn);


    coreferenceVisibleBtn = new JToggleButton("Coreference", coreferenceVisible);
    if(isCorefOptionAvailable()) toolbar.add(coreferenceVisibleBtn);


    //printing
    toolbar.add(Box.createHorizontalStrut(20));
    toolbar.add(new PrintAction());
    toolbar.add(new SearchAction());



    toolbar.add(Box.createHorizontalGlue());

    //The text
    textPane = new XJTextPane();
//    textPane.setEditable(false);
    textPane.setEnabled(true);
    textPane.setEditorKit(new CustomStyledEditorKit());
    Style defaultStyle = textPane.getStyle("default");
    StyleConstants.setBackground(defaultStyle, Color.white);
    StyleConstants.setFontFamily(defaultStyle, "Arial Unicode MS");
    textScroll = new JScrollPane(textPane);
    textScroll.setAlignmentY(Component.TOP_ALIGNMENT);
    textScroll.setAlignmentX(Component.LEFT_ALIGNMENT);


    //The table
    annotationsTableModel = new AnnotationsTableModel();
    annotationsTable = new XJTable(annotationsTableModel);
//    annotationsTable.setIntercellSpacing(new Dimension(10, 5));

    tableScroll = new JScrollPane(annotationsTable);
    tableScroll.setOpaque(true);
    tableScroll.setAlignmentY(Component.TOP_ALIGNMENT);
    tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);


    //RIGHT SIDE - the big tree
    stylesTreeRoot = new DefaultMutableTreeNode(null, true);
    stylesTreeModel = new DefaultTreeModel(stylesTreeRoot, true);
    stylesTree = new JTree(stylesTreeModel){
      public void updateUI(){
        super.updateUI();
        setRowHeight(0);
      }
    };

    stylesTree.setRootVisible(false);
    stylesTree.setCellRenderer(new NodeRenderer());
    //TIP: setting rowHeight to 0 tells the tree to query its renderer for each
    //row's size
    stylesTree.setRowHeight(0);
    stylesTree.setShowsRootHandles(true);
    stylesTree.setToggleClickCount(0);
    stylesTreeScroll = new JScrollPane(stylesTree);
    stylesTreeScroll.setAlignmentY(Component.TOP_ALIGNMENT);
    stylesTreeScroll.setAlignmentX(Component.LEFT_ALIGNMENT);


    //coreference
    corefTreeRoot = new DefaultMutableTreeNode("Co-reference data", true);
    corefTree = new JTree(corefTreeModel = new DefaultTreeModel(corefTreeRoot,
                                                                true));
    corefTree.setCellRenderer(new CorefNodeRenderer());
    corefTree.setRowHeight(0);
    corefTree.setRootVisible(true);
    corefTree.setShowsRootHandles(false);
    corefScroll = new JScrollPane(corefTree);
    corefScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    corefScroll.setAlignmentY(Component.TOP_ALIGNMENT);
    updateCorefTree();

    //various containers
    leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);
    leftSplit.setOneTouchExpandable(true);
    leftSplit.setOpaque(true);
    leftSplit.setAlignmentY(Component.TOP_ALIGNMENT);
    leftSplit.setAlignmentX(Component.LEFT_ALIGNMENT);
    leftSplit.setResizeWeight((double)0.75);

    rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);
    rightSplit.setOneTouchExpandable(true);
    rightSplit.setOpaque(true);
    rightSplit.setAlignmentY(Component.TOP_ALIGNMENT);
    rightSplit.setAlignmentX(Component.LEFT_ALIGNMENT);
    rightSplit.setResizeWeight((double)0.75);


    mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false);
    mainSplit.setOneTouchExpandable(true);
    mainSplit.setOpaque(true);
    mainSplit.setAlignmentY(Component.TOP_ALIGNMENT);
    mainSplit.setAlignmentX(Component.LEFT_ALIGNMENT);
    mainSplit.setResizeWeight((double)0.75);

    //put everything together
    layoutComponents();

    //Extra Stuff

    progressBox = new Box(BoxLayout.X_AXIS);
    progressBox.add(Box.createHorizontalStrut(5));
    progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
    progressBox.add(progressBar);
    progressBox.add(Box.createHorizontalStrut(5));

    highlighter = textPane.getHighlighter();
    if(highlighter instanceof javax.swing.text.DefaultHighlighter){
      ((javax.swing.text.DefaultHighlighter)highlighter).
      setDrawsLayeredHighlights(true);
    }

    selectionHighlighter = new DefaultHighlighter();
    selectionHighlighter.install(textPane);
    selectionBlinker = new SelectionBlinker();

  }//protected void initGuiComponents()


  /** This method returns true if a text is selected in the textPane*/
  private boolean isTextSelected(){
    return !(textPane.getSelectionStart()==textPane.getSelectionEnd());
  }// isTextSelected()
  /**
   * Gets all the {@link gate.creole.AnnotationSchema} objects currently
   * loaded in the system.
   */
  protected Set getAnnotationSchemas(){
    Set result = new HashSet();
    ResourceData rData = (ResourceData)Gate.getCreoleRegister().
                                            get("gate.creole.AnnotationSchema");
    if(rData != null){
      result.addAll(rData.getInstantiations());
    }
    return result;
  }//protected Set getAnnotationSchemas()

  public synchronized void removePropertyChangeListener(
                                                    PropertyChangeListener l) {
    super.removePropertyChangeListener(l);
    propertyChangeListeners.removePropertyChangeListener(l);
  }

  public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
    super.addPropertyChangeListener(l);
    propertyChangeListeners.addPropertyChangeListener(l);
  }

  public synchronized void addPropertyChangeListener(String propertyName,
                                                     PropertyChangeListener l) {
    super.addPropertyChangeListener(propertyName, l);
    propertyChangeListeners.addPropertyChangeListener(propertyName, l);
  }


  /** Return the current selected document */
  public gate.Document getDocument() {
    return document;
  } // Document getDocument()

  /**
   * Sets the document to be displayed
   */
  public void setTarget(Object target){
    if(target == null){
      document = null;
      return;
    }
    if(!(target instanceof gate.Document)){
      throw new IllegalArgumentException(
        "The document editor can only display GATE documents!\n" +
        "The provided resource is not a document but a: " +
        target.getClass().toString() + "!");
    }
    gate.Document  oldDocument = document;
    document = (gate.Document)target;
    //this needs to be executed even if the new document equals(oldDocument)
    //in order to update the pointers
    if(oldDocument != document) this_documentChanged();

    propertyChangeListeners.firePropertyChange("document", oldDocument,
                                               target);
  }//public void setTarget(Object target)

  public void setHandle(Handle handle){
    myHandle = handle;
  }

  public void cleanup(){
    document = null;
    stylesTreeRoot.removeAllChildren();
    data.clear();
    ranges.clear();
    myHandle = null;
  }

  /**
   * This method returns a list of annotations which are currently shown in
   * the annotations table or null of the table is empty.
   */
  public java.util.Set getDisplayedAnnotations() {
    //if the annotations table is empty, then return null
    if (annotationsTableModel == null||annotationsTableModel.getRowCount() == 0)
      return null;

    // Read the displayed annotations and insert them into a list
    java.util.Set shownAnnots = new HashSet();
    for(int i = 0; i < annotationsTableModel.getRowCount(); i++){
      //Find an annotation and add it to the annotationsToDump set.
      Annotation ann = (Annotation)annotationsTableModel.getValueAt(i, -1);
      shownAnnots.add(ann);
    }// End for

    return shownAnnots;
  }

  /**
   * Get positions of cut points inside a very large text without new line
   */
  private Vector getBreakPositions(String content) {
    Vector breakPositions = new Vector();

    int lastNewLinePos = -1;
    int spacePos = -1;
    int unbreakedLineSize = 0;
    char ch;
    int contentSize = content.length();

    for(int i=0; i<contentSize; ++i) {
      ch = content.charAt(i);

      switch(ch) {
        case '\n' :
            unbreakedLineSize = 0;
            spacePos = -1;
          break;
        case '\r' :
            unbreakedLineSize = 0;
            spacePos = -1;
          break;
        case '\t' :
            spacePos = i;
            ++unbreakedLineSize;
          break;
        case ' ' :
            spacePos = i;
            ++unbreakedLineSize;
          break;

        default:
          if(unbreakedLineSize >= MAX_LINE_SIZE) {
            // insert break
            if(spacePos == -1) {
              // break without space
              spacePos = i;
            } // if

            breakPositions.add(new Integer(spacePos+1));
            unbreakedLineSize = i - spacePos;
            spacePos = -1;
          }
          else {
            ++unbreakedLineSize;
          } // if
      } // switch
    } // for

    return breakPositions;
  } // getBreakPositions(String content)

  /** Max unbreaked line size */
  private final int MAX_LINE_SIZE = 2048;

  /**
   * Cut very long lines to pieces not grater than MAX_LINE_SIZE
   * This is a correction of SWING problem with very long lines of text
   * <BR>
   * Return positions of new line insertion.
   */
  private Vector correctLongLines(StringBuffer buff) {
    // analyze for long unbreaked line of text
    Vector breaks = getBreakPositions(buff.toString());
//if(breaks.size() > 0) System.out.println("Breaks: "+breaks);

    Integer currentBreak;
    int intValue;
    // put new line in break positions
    for(int i = breaks.size()-1; i>=0; --i) {
      currentBreak = (Integer) breaks.get(i);
      intValue = currentBreak.intValue();
      buff.insert(intValue, '\n');
    } // for

    if(breaks.size() > 0) {
      return breaks;
    }
    else {
      return null;
    }
  } // correctLongLines(StringBuffer buff)

  /** Compute correction for additional new line in very long lines of text */
  private int longLinesCorrection(int position) {
    int result = 0;

    if(longLinesCorrectionPositions != null) {
      boolean underPosition = true;
      Integer current;
      Iterator it = longLinesCorrectionPositions.iterator();

      while(underPosition && it.hasNext()) {
        current = (Integer) it.next();
        if(position > (current.intValue()+result)) {
          // cross this new line point
          ++result;
        }
        else {
          // all new lines computed
          underPosition = false;
        } // if
      } // while
    } // if

    return result;
  } // int longLinesCorrection(int position)

  /** Keep cut places in very long lines inside document */
  private Vector longLinesCorrectionPositions;

  /**
   * Updates this component when the underlying document is changed. This method
   * is only triggered when the document is changed to a new one and not when
   * the internal data from the document changes.
   */
  protected void this_documentChanged(){
//    initLocalData();
//
//    Enumeration enumeration = stylesTreeRoot.children();
//    while(enumeration.hasMoreElements()){
//      stylesTreeModel.removeNodeFromParent((DefaultMutableTreeNode)
//                                           enumeration.nextElement());
//    }
//    if(document == null) return;
//
//    // check for very long lines of text in order to correct SWING bug
//    String documentContent = document.getContent().toString();
//    StringBuffer buffContent = new StringBuffer(documentContent);
//    // cut very long lines to pieces not grater than MAX_LINE_SIZE
//    longLinesCorrectionPositions = correctLongLines(buffContent);
//    if(longLinesCorrectionPositions != null) {
//      documentContent = buffContent.toString();
//    } // if
//
//    textPane.setText(documentContent);
//    //listen for events from the document content editor
//    textPane.getDocument().addDocumentListener(new SwingDocumentListener());
//
//    //add the default annotation set
//    eventHandler.annotationSetAdded(new gate.event.DocumentEvent(
//                  document,
//                  gate.event.DocumentEvent.ANNOTATION_SET_ADDED, null));
//
//    //register the for this new document's events
//    document.addDocumentListener(eventHandler);
//
//    annotationsTableModel.fireTableDataChanged();
//    document.getFeatures().addFeatureMapListener(new FeatureMapListener(){
//      public void featureMapUpdated(){
//        updateCorefTree();
//      }
//    });
//    updateCorefTree();
//
//
//    //add all the other annotation sets
//    Map namedASs = document.getNamedAnnotationSets();
//    if(namedASs != null){
//      Iterator setsIter = namedASs.values().iterator();
//      while(setsIter.hasNext()){
//        AnnotationSet currentAS = (AnnotationSet)setsIter.next();
//        if(currentAS != null){
//          eventHandler.annotationSetAdded(new gate.event.DocumentEvent(
//                        document,
//                        gate.event.DocumentEvent.ANNOTATION_SET_ADDED,
//                        currentAS.getName()));
//        }
//      }
//    }
  }//protected void this_documentChanged()

  /**
   * Gets the data related to a given annotation type.
   * An annotation type is uniquely identified by the name of its AnnotationSet
   * and the name of the type.
   * For the default annotation set of a document (which has no name) the
   * &quot;&lt;Default&gt;&quot; value is used.
   *
   * Once a {@link TypeData} value has been obtained it can be used to change
   * the way the respective type of annotations are displayed.
   * @param setName a {@link java.lang.String}, the name of the annotation set
   * @param type a {@link java.lang.String}, the name of the type.
   * @return a {@link TypeData} value
   */
  protected TypeData getTypeData(String setName, String type){
    Map setMap = (Map)typeDataMap.get(setName);
    if(setMap != null) return (TypeData)setMap.get(type);
    else return null;
  }// protected TypeData getTypeData(String setName, String type)


  /**
   * Repaints the highlighting for annotation types in the text display.
   */
  protected void showHighlights(Set annotations, AttributeSet style) {
    //store the state of the text display
    int selStart = textPane.getSelectionStart();
    int selEnd = textPane.getSelectionEnd();
    final int position = textPane.viewToModel(
                            textScroll.getViewport().getViewPosition());
    //hide the text
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        progressBar.setValue(0);
        //progressBar.setMaximumSize(new Dimension(textScroll.getWidth(),20));
        textScroll.getViewport().setView(progressBox);
        textScroll.paintImmediately(textScroll.getBounds());
      }
    });

    paintHighlights(annotations, style);

    //restore the state
    textPane.select(selStart, selEnd);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        //show the text
        textScroll.getViewport().setView(textPane);
        try {
          textScroll.getViewport().setViewPosition(
              textPane.modelToView(position).getLocation());
          textScroll.paintImmediately(textScroll.getBounds());
        }
        catch (BadLocationException ble) {
        }
      }
    });
  }//protected void showHighlights()

  protected void paintHighlights(Set annotations, AttributeSet style){
    //highlight the annotations
    int size = annotations.size();
    int i = 0;
    int lastValue = 0;
    int value;

    int start, end;
    Iterator annIter = annotations.iterator();
    while(annIter.hasNext()){
      Annotation ann = (Annotation)annIter.next();
      start = ann.getStartNode().getOffset().intValue();
      end = ann.getEndNode().getOffset().intValue();
      // compute correction for new line breaks in long lines
      start += longLinesCorrection(start);
      end += longLinesCorrection(end);

      textPane.select(start, end);
      textPane.setCharacterAttributes(style, true);
      if(progressBar.isVisible()){
        value = i * 100 / size;
        if (value - lastValue >= 5) {
          progressBar.setValue(value);
          progressBar.paintImmediately(progressBar.getBounds());
          lastValue = value;
        }
        i++;
      }
    }
  }

  /**
   * Called whenever a part of the textual display needs to be repainted
   * because, for instance, of an edit operation.
   * @param start the start offset for the area to be repainted
   * @param end the end offset for the area to be repainted.
   */
  protected void repairHighlights(int start, int end) {
    //we need to fix the character ranges for all types visible or not
    //clear everything
    int selStart = textPane.getSelectionStart();
    int selEnd = textPane.getSelectionEnd();
    //clear the styles in the affected area
    textPane.select(start, end);
    textPane.setCharacterAttributes(textPane.getStyle("default"), true);

    //repaint the highlights for the annotations going through the affected area
    Iterator setsIter = typeDataMap.keySet().iterator();
    while(setsIter.hasNext()){
      Map typesMap = (Map)typeDataMap.get(setsIter.next());
      Iterator typesIter = typesMap.keySet().iterator();
      while(typesIter.hasNext()){
        TypeData tData = (TypeData) typesMap.get(typesIter.next());
        if (tData.getVisible()) {
          String setName = tData.getSet();
          AnnotationSet annSet = setName.equals("Default") ?
                                 document.getAnnotations() :
                                 document.getAnnotations(setName);
          annSet = annSet.get(tData.getType());
          if(annSet != null){
            AnnotationSet annotationsToRepaint = annSet.get(new Long(start),
                new Long(end));
//          Set annotationsToRepaint = new HashSet();
//          Iterator annIter = tData.getAnnotations().iterator();
//          while (annIter.hasNext()) {
//            Annotation anAnnotation = (Annotation) annIter.next();
//            long annStart = anAnnotation.getStartNode().getOffset().longValue();
//            long annEnd = anAnnotation.getEndNode().getOffset().longValue();
//            if ( (annStart < start && annEnd >= start) ||
//                (start <= annStart && annStart <= end)
//                )
//              annotationsToRepaint.add(anAnnotation);
//          }
            paintHighlights(annotationsToRepaint, tData.getActualStyle());
          }
        }
      }
    }
    //restore selection
    textPane.select(selStart, selEnd);
//    textPane.requestFocus();
  }

  /**
   * Updates the GUI when the user has selected an annotation e.g. by using the
   * right click popup. That basically means make the appropiate type of
   * annotations visible in case it isn't already.
   */
  protected void selectAnnotation(String set, Annotation ann) {
    TypeData tData = getTypeData(set, ann.getType());
    if(!tData.getVisible()){
      tData.setVisible(true);
      //sleep a while so the gui updater thread has time to start
      try{
        Thread.sleep(100);
      }catch(InterruptedException ie){}
      //refresh the display for the type
      //(the checkbox has to be shown selected)
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                    ((DefaultMutableTreeNode)stylesTreeRoot).
                                    getFirstChild();
      while(node != null &&
            !((TypeData)node.getUserObject()).getSet().equals(set))
        node = node.getNextSibling();
      if(node != null){
        node = (DefaultMutableTreeNode)node.getFirstChild();
        String type = ann.getType();
        while(node != null &&
              !((TypeData)node.getUserObject()).getType().equals(type))
          node = node.getNextSibling();
        if(node != null) stylesTreeModel.nodeChanged(node);
      }
    }
    int position = -1;
    position = data.indexOf(ann);
    if(position != -1){
      position = annotationsTable.getTableRow(position);
      if(position != -1){
        annotationsTable.clearSelection();
        annotationsTable.addRowSelectionInterval(position, position);
        annotationsTable.scrollRectToVisible(
              annotationsTable.getCellRect(position, 0, true));
      }
    }
  }//protected void selectAnnotation(String set, Annotation ann)


  /**
   * Creates the layout of this component acording to the set of subcomponents
   * (text display, annotations table, etc.) that need to be visible.
   */
  protected void layoutComponents(){
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        Component leftComp = null, rightComp = null;
        if(isTextVisible() && isAnnotationsTableVisible()){
          leftSplit.setTopComponent(textScroll);
          leftSplit.setBottomComponent(tableScroll);
          leftComp = leftSplit;
        }else{
          if(isTextVisible()) leftComp = textScroll;
          else if(isAnnotationsTableVisible()) leftComp = tableScroll;
        }

        boolean corefDisplayed = isCoreferenceVisible() &&
                                 isCorefOptionAvailable();
        if(corefDisplayed) updateCorefTree();
        if(isTypesTreeVisible() && corefDisplayed){
          rightSplit.setTopComponent(stylesTreeScroll);
          rightSplit.setBottomComponent(corefScroll);
          rightComp = rightSplit;
        }else{
          if(isTypesTreeVisible()) rightComp = stylesTreeScroll;
          else if(corefDisplayed) rightComp = corefScroll;
        }

        if(DocumentEditor.this.getComponentCount() > 1)
          DocumentEditor.this.remove(1);
        if(leftComp != null && rightComp != null){
          //we need the main split
          mainSplit.setLeftComponent(leftComp);
          mainSplit.setRightComponent(rightComp);
          DocumentEditor.this.add(mainSplit, BorderLayout.CENTER);
        }else{
          if(leftComp != null) DocumentEditor.this.add(leftComp,
                                                       BorderLayout.CENTER);
          else if(rightComp != null)DocumentEditor.this.add(rightComp,
                                                            BorderLayout.CENTER);
        }

        DocumentEditor.this.validate();
        DocumentEditor.this.repaint();
      }
    });
  }


  /**
   * Updates the coref tree from the coref data on the document's features
   */
  protected void updateCorefTree(){
    if(document == null || document.getFeatures() == null){
      //no coref data; clear the tree
      corefTreeRoot.removeAllChildren();
      corefTreeModel.nodeStructureChanged(corefTreeRoot);
      setCorefOptionAvailable(false);
      return;
    }

    Map matchesMap = null;
    try{
      matchesMap = (Map)document.getFeatures().get(DOCUMENT_COREF_FEATURE_NAME);
    }catch(Exception e){
    }
    if(matchesMap == null){
      //no coref data; clear the tree
      Enumeration nodes = corefTreeRoot.breadthFirstEnumeration();
      while(nodes.hasMoreElements()){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                      nodes.nextElement();
        if(node.getUserObject() instanceof CorefData){
          ((CorefData)node.getUserObject()).setVisible(false);
        }
      }
      corefTreeRoot.removeAllChildren();
      corefTreeModel.nodeStructureChanged(corefTreeRoot);
      setCorefOptionAvailable(false);
      return;
    }

    //matches map is not null; check whether it's valid
    Iterator setsIter = matchesMap.keySet().iterator();
    setsLoop: while(setsIter.hasNext()){
      String setName = (String)setsIter.next();
      AnnotationSet annSet = setName == null ? document.getAnnotations() :
                                               document.getAnnotations(setName);
      Iterator entitiesIter = ((java.util.List)matchesMap.get(setName)).
                              iterator();
      //each entity is a list of annotation IDs
      while(entitiesIter.hasNext()){
        Iterator idsIter = ((java.util.List)entitiesIter.next()).iterator();
        while(idsIter.hasNext()){
          if(annSet.get((Integer)idsIter.next()) == null){
            //remove the data for this set
            setsIter.remove();
            Err.prln("Coreference data for the \"" +
                     (setName == null ? "Default" : setName) +
                      "\" annotation set of document \"" + document.getName() +
                     "\" was invalid and has been removed");
            continue setsLoop;
          }
        }
      }
    }

    if(matchesMap.isEmpty()){
      //no more coref data
      corefTreeRoot.removeAllChildren();
      corefTreeModel.nodeStructureChanged(corefTreeRoot);
      setCorefOptionAvailable(false);
      return;
    }

    String[] newSetNames = (String[])
                           matchesMap.keySet().toArray(new String[]{});
    Arrays.sort(newSetNames);

    ArrayList oldSetNames = new ArrayList(corefTreeRoot.getChildCount());
    Enumeration setNodes = corefTreeRoot.children();
    while(setNodes.hasMoreElements()){
      String oldSetName = (String)
                           ((DefaultMutableTreeNode)setNodes.nextElement()).
                           getUserObject();
      oldSetNames.add(oldSetName.equals("Default") ? null : oldSetName);
    }


    // stores the new set nodes; they will be added to root after the
    // processing is done
    ArrayList newSetNodes = new ArrayList();
    //for each new set update the children
    for(int i =0; i < newSetNames.length; i++){
      String setName = newSetNames[i];
      int oldNodeIndex = oldSetNames.indexOf(setName);
      DefaultMutableTreeNode setNode =
          (oldNodeIndex != -1) ?
          (DefaultMutableTreeNode)
          corefTreeRoot.getChildAt(oldNodeIndex) :
          new DefaultMutableTreeNode((setName == null ? "Default" : setName),
                                     true);
      //if found it will be reused so delete it from the list
      if(oldNodeIndex != -1) oldSetNames.remove(oldNodeIndex);

      // temporarily stores the new nodes
      ArrayList newEntityNodes = new ArrayList();
      //for each set the coref data is a list of lists
      Iterator corefDataIter = ((java.util.List)matchesMap.get(setName)).
                               iterator();
      while(corefDataIter.hasNext()){
        java.util.List newAnnotIDs = (java.util.List)corefDataIter.next();
        CorefData cData = null;
        DefaultMutableTreeNode entityNode = null;
        //try to find the old coref data
        Enumeration entityNodes = setNode.children();
        while(cData == null && entityNodes.hasMoreElements()){
          entityNode = (DefaultMutableTreeNode)entityNodes.nextElement();
          java.util.List oldAnnotIDs = ((CorefData)entityNode.getUserObject()).
                                     getAnnoationIDs();
          java.util.List intersection = new ArrayList(oldAnnotIDs);
          intersection.retainAll(newAnnotIDs);
          if(!intersection.isEmpty()){
            //we have some common values; assume we found it
            cData = (CorefData)entityNode.getUserObject();
            if(intersection.size() == newAnnotIDs.size()){
              //identical values, we just got lucky: noting to do
            }else{
              cData.setAnnotationIDs(newAnnotIDs);
            }
          }
        }
        if(cData == null){
          //we couldn't find a suitable node, create a new one
          cData = new CorefData(newAnnotIDs, false, setName == null ?
                                                    "Default" : setName);
          entityNode = new DefaultMutableTreeNode(cData, false);
        }
        newEntityNodes.add(entityNode);
      }//while(corefDataIter.hasNext())
      //we're done with this set: add all the nodes to the set node
      //set visible to false for all nodes that will not be kept
//      for(Enumeration entityNodes = setNode.children();
//          entityNodes.hasMoreElements();){
//        Object anOldNode = entityNodes.nextElement();
//        if(!newEntityNodes.contains(anOldNode)){
//          ((CorefData)((DefaultMutableTreeNode)anOldNode).
//          getUserObject()).setVisible(false);
//        }
//      }

      setNode.removeAllChildren();
      for(Iterator nodesIter = newEntityNodes.iterator();
          nodesIter.hasNext();
          setNode.add((DefaultMutableTreeNode)nodesIter.next())){
      }
      newSetNodes.add(setNode);
    }//for(int i =0; i < newSetNames.length; i++)
    //we're done with all the sets: add the nodes to the tree root
    corefTreeRoot.removeAllChildren();
    for(Iterator nodesIter = newSetNodes.iterator();
        nodesIter.hasNext();){
      DefaultMutableTreeNode setNode = (DefaultMutableTreeNode)nodesIter.next();
      corefTreeRoot.add(setNode);
    }
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        highlighter.removeAllHighlights();
        corefTreeModel.nodeStructureChanged(corefTreeRoot);
        //expand the root
        corefTree.expandPath(new TreePath(new Object[]{corefTreeRoot}));
        //expand all of root's children
        Enumeration children = corefTreeRoot.children();
        while(children.hasMoreElements()){
          DefaultMutableTreeNode aNode =
            (DefaultMutableTreeNode)children.nextElement();
          corefTree.expandPath(
            new TreePath(corefTreeModel.getPathToRoot(aNode)));
          if(aNode.getUserObject() instanceof CorefData){
            CorefData cData = (CorefData)aNode.getUserObject();
            //trigger highlights repaint
            cData.setVisible(cData.getVisible());
          }
        }
      }
    });
    setCorefOptionAvailable(true);
  }//protected void updateCorefTree()


  /**Should the editor functionality of this component be enabled*/
  public void setEditable(boolean newEditable) {
    editable = newEditable;
  }

  /**Is the editor functionality enabled*/
  public boolean isEditable() {
    return editable;
  }
  public void setAnnotationsTableVisible(boolean annotationsTableVisible) {
    boolean  oldAnnotationsTableVisible = this.annotationsTableVisible;
    this.annotationsTableVisible = annotationsTableVisible;
    propertyChangeListeners.firePropertyChange(
        "annotationsTableVisible",
        new Boolean(oldAnnotationsTableVisible),
        new Boolean(annotationsTableVisible));
  }
  public boolean isAnnotationsTableVisible() {
    return annotationsTableVisible;
  }
  public void setCoreferenceVisible(boolean coreferenceVisible) {
    boolean  oldCoreferenceVisible = this.coreferenceVisible;
    this.coreferenceVisible = coreferenceVisible;
    propertyChangeListeners.firePropertyChange(
      "coreferenceVisible",
      new Boolean(oldCoreferenceVisible),
      new Boolean(coreferenceVisible));
  }

  public boolean isCoreferenceVisible() {
    return coreferenceVisible;
  }
  public void setTextVisible(boolean textVisible) {
    boolean  oldTextVisible = this.textVisible;
    this.textVisible = textVisible;
    propertyChangeListeners.firePropertyChange("textVisible",
                                               new Boolean(oldTextVisible),
                                               new Boolean(textVisible));
  }
  public boolean isTextVisible() {
    return textVisible;
  }
  public void setTypesTreeVisible(boolean typesTreeVisible) {
    boolean  oldTypesTreeVisible = this.typesTreeVisible;
    this.typesTreeVisible = typesTreeVisible;
    propertyChangeListeners.firePropertyChange("typesTreeVisible",
                                               new Boolean(oldTypesTreeVisible),
                                               new Boolean(typesTreeVisible));
  }
  public boolean isTypesTreeVisible() {
    return typesTreeVisible;
  }
  public void setCorefOptionAvailable(boolean corefOptionAvailable) {
    boolean  oldCorefOptionAvailable = this.corefOptionAvailable;
    this.corefOptionAvailable = corefOptionAvailable;
    propertyChangeListeners.firePropertyChange(
      "corefOptionAvailable", new Boolean(oldCorefOptionAvailable),
      new Boolean(corefOptionAvailable));
  }

  public boolean isCorefOptionAvailable() {
    return corefOptionAvailable;
  }

  //inner classes
  /**
   * A custom table model used to render a table containing the annotations
   * from a set of annotation sets.
   * The columns will be: Type, Set, Start, End, Features
   */
  protected class AnnotationsTableModel extends AbstractTableModel{
    public AnnotationsTableModel(){
    }

    public int getRowCount(){
      return data.size();
    }

    public int getColumnCount(){
      return 5;
    }

    public String getColumnName(int column){
      switch(column){
        case 0: return "Type";
        case 1: return "Set";
        case 2: return "Start";
        case 3: return "End";
        case 4: return "Features";
        default:return "?";
      }
    }

    public Class getColumnClass(int column){
      switch(column){
        case 0: return String.class;
        case 1: return String.class;
        case 2: return Long.class;
        case 3: return Long.class;
        case 4: return String.class;
        default:return Object.class;
      }
    }

    public Object getValueAt(int row, int column){
      Annotation ann;
      ann = (Annotation)data.get(row);
      switch(column){
        case -1:{//The actual annotation
          return ann;
        }
        case 0:{//Type
          return ann.getType();
        }
        case 1:{//Set
          Iterator rangesIter = ranges.iterator();
          while(rangesIter.hasNext()){
            Range range = (Range)rangesIter.next();
            if(range.start <= row && row < range.end) return range.setName;
          }
          return "?";
        }
        case 2:{//Start
          return ann.getStartNode().getOffset();
        }
        case 3:{//End
          return ann.getEndNode().getOffset();
        }
        case 4:{//Features
          if(ann.getFeatures() == null) return null;
          else return ann.getFeatures().toString();
        }
        default:{
        }
      }
      return null;
    }
  }//class AnnotationsTableModel extends AbstractTableModel


  protected class CorefData{
    CorefData(java.util.List annotationIDs, boolean visible, String setName){
      this.visible = visible;
      this.setName = setName;
      this.colour = colGenerator.getNextColor();
      highlights = new ArrayList();
      this.annotationIDs = annotationIDs;
      this.title = getNameForCorefList(annotationIDs);
    }

    /**
     * Finds the name for a set of co refering entities (uses the string of the
     * first one).
     * @param list a list of annotation IDs
     */
    String getNameForCorefList(java.util.List list){
      if(list == null || list.isEmpty()) return null;
      Integer id = (Integer)list.get(0);
      AnnotationSet set = setName.equals("Default") ?
                          document.getAnnotations() :
                          document.getAnnotations(setName);
      Annotation ann = set.get(id);

      String name = null;
      try{
        name = document.getContent().
                        getContent(ann.getStartNode().getOffset(),
                                   ann.getEndNode().getOffset()).toString();
      }catch(InvalidOffsetException ioe){
      }
      return name;
    }

    public boolean getVisible(){
      return visible;
    }

    public void removeAnnotations(){
      AnnotationSet set = setName.equals("Default") ?
                          document.getAnnotations() :
                          document.getAnnotations(setName);

      Iterator idIter = annotationIDs.iterator();
      while(idIter.hasNext()){
        set.remove(set.get((Integer)idIter.next()));
      }
      ((java.util.List)((Map)document.getFeatures().
        get(ANNIEConstants.DOCUMENT_COREF_FEATURE_NAME)).
        get(setName.equals("Default") ? null : setName)).remove(annotationIDs);
      annotationIDs.clear();
      updateCorefTree();
    }

    public void setVisible(boolean isVisible){
      if(this.visible == isVisible) return;
      this.visible = isVisible;
      if(visible){
        //add new highlights and store them
        AnnotationSet set = setName.equals("Default") ?
                            document.getAnnotations() :
                            document.getAnnotations(setName);
        Iterator idIter = annotationIDs.iterator();
        ArrayList invalidIDs = new ArrayList();
        while(idIter.hasNext()){
          Integer id = (Integer)idIter.next();
          Annotation ann = set.get(id);
          if(ann == null){
            invalidIDs.add(id);
          }else try{
            highlights.add(highlighter.addHighlight(
              ann.getStartNode().getOffset().intValue(),
              ann.getEndNode().getOffset().intValue(),
              new DefaultHighlighter.DefaultHighlightPainter(colour)));
          }catch(BadLocationException ble){
            ble.printStackTrace();
          }
        }
        if(!invalidIDs.isEmpty()){
          annotationIDs.removeAll(invalidIDs);
        }
      }else{
        //remove the highlights
        if(!highlights.isEmpty()){
          Iterator hlIter = highlights.iterator();
          while(hlIter.hasNext()){
            Object tag = hlIter.next();
            highlighter.removeHighlight(tag);
            hlIter.remove();
          }
        }
      }
    }

    public String getTitle(){
      return title;
    }

    public Color getColour(){
      return colour;
    }

    public void setColour(Color newColour){
      this.colour = newColour;
      if(visible){
        //update the highlights
        setVisible(false);
        setVisible(true);
      }
    }

    public java.util.List getAnnoationIDs(){
      return annotationIDs;
    }

    public String getSetName(){
      return setName;
    }
    public String toString(){
      return title;
    }

    public void setAnnotationIDs(java.util.List newAnnIDs){
      this.annotationIDs =newAnnIDs;
      this.title = getNameForCorefList(annotationIDs);
      if(visible){
        //restore the highlights
        setVisible(false);
        setVisible(true);
      }
    }

    private boolean visible;
    private String title;
    private String setName;
    private Color colour;
    private java.util.List highlights;
    private java.util.List annotationIDs;
  }

/*
  protected class CorefComboModel extends AbstractListModel
                                  implements ComboBoxModel{

    CorefComboModel(){
      lastReturnedSize = 0;
    }

    public int getSize(){
      if(document == null || document.getFeatures() == null) return 0;
      Map matchesMap = null;
      try{
        matchesMap = (Map)document.getFeatures().get(DOCUMENT_COREF_FEATURE_NAME);
      }catch(Exception e){
        e.printStackTrace();
      }
      int size = (matchesMap == null) ? 0 : matchesMap.size();
      if(lastReturnedSize != size){
        lastReturnedSize = size;
        fireDataChanged();
      }
      return lastReturnedSize;
    }


    public Object getElementAt(int index){
      if(document == null || document.getFeatures() == null) return null;
      Map matchesMap = null;
      try{
        matchesMap = (Map)document.getFeatures().get(DOCUMENT_COREF_FEATURE_NAME);
      }catch(Exception e){
        e.printStackTrace();
      }
      if(matchesMap == null) return null;
      java.util.List setsList = new ArrayList(matchesMap.keySet());
      boolean nullPresent = setsList.remove(null);
      Collections.sort(setsList);
      if(nullPresent) setsList.add(0, null);
      String res = (String)setsList.get(index);
      return (res == null) ? "Default" : res;
    }

    public void setSelectedItem(Object anItem){
      if(anItem == null) selectedItem = null;
      else selectedItem = ((String)anItem).equals("Default") ? null : anItem;
    }

    public Object getSelectedItem(){
      return selectedItem == null ? "Default" : selectedItem;
    }

    void fireDataChanged(){
      fireContentsChanged(this, 0, getSize());
    }

    Object selectedItem = null;
    int lastReturnedSize;
  }
*/

  /**
   * Panels used in cell/node renderers
   */
  class LazyJPanel extends JPanel{
    /**
     * Overridden for performance reasons.
     */
    public void revalidate() {}

    /**
     * Overridden for performance reasons.
     */
    public void repaint(long tm, int x, int y, int width, int height) {}

    /**
     * Overridden for performance reasons.
     */
    public void repaint(Rectangle r) {}

    /**
     * Overridden for performance reasons.
     */
    protected void firePropertyChange(String propertyName, Object oldValue,
                                                            Object newValue) {}

    /**
     * Overridden for performance reasons.
     */
    public void firePropertyChange(String propertyName, byte oldValue,
                                                              byte newValue) {}

    /**
     * Overridden for performance reasons.
     */
    public void firePropertyChange(String propertyName, char oldValue,
                                                              char newValue) {}

    /**
     * Overridden for performance reasons.
     */
    public void firePropertyChange(String propertyName, short oldValue,
                                                            short newValue) {}

    /**
     * Overridden for performance reasons.
     */
    public void firePropertyChange(String propertyName, int oldValue,
                                                              int newValue) {}

    /**
     * Overridden for performance reasons.
     */
    public void firePropertyChange(String propertyName, long oldValue,
                                                              long newValue) {}

    /**
     * Overridden for performance reasons.
     */
    public void firePropertyChange(String propertyName, float oldValue,
                                                              float newValue) {}

    /**
     * Overridden for performance reasons.
     */
    public void firePropertyChange(String propertyName, double oldValue,
                                                            double newValue) {}

    /**
     * Overridden for performance reasons.
     */
    public void firePropertyChange(String propertyName, boolean oldValue,
                                                            boolean newValue) {}
  }

  /**
   * A tree node renderer used by the coref tree
   */
  class CorefNodeRenderer implements TreeCellRenderer{

    CorefNodeRenderer(){
      label = new JLabel();
      label.setOpaque(true);

      checkBox = new JCheckBox();
      checkBox.setBorderPaintedFlat(true);

      panel = new LazyJPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setOpaque(false);

      hBox = new LazyJPanel();
      hBox.setLayout(new BoxLayout(hBox, BoxLayout.X_AXIS));
      hBox.setOpaque(false);

      panel.add(Box.createVerticalStrut(2));
      panel.add(hBox);
      panel.add(Box.createVerticalStrut(2));

      leftSpacer = Box.createHorizontalStrut(3);
      rightSpacer = Box.createHorizontalStrut(3);

      selectedBorder = BorderFactory.createLineBorder(Color.blue, 1);
      normalBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                              Object value,
                                              boolean selected,
                                              boolean expanded,
                                              boolean leaf,
                                              int row,
                                              boolean hasFocus){

      hBox.removeAll();
      hBox.add(leftSpacer);

      if(value instanceof DefaultMutableTreeNode){
        value = ((DefaultMutableTreeNode)value).getUserObject();
      }
      if(value instanceof CorefData){
        CorefData cData = (CorefData)value;
        checkBox.setSelected(cData.getVisible());
        checkBox.setBackground(tree.getBackground());

        label.setBackground(cData.getColour());
        label.setForeground(tree.getForeground());
        label.setText(cData.getTitle());
        label.setFont(tree.getFont());
        hBox.add(checkBox);
        hBox.add(label);
        hBox.add(rightSpacer);
      }else{
        label.setText(value == null ? "" : value.toString());
        label.setForeground(tree.getForeground());
        label.setBackground(tree.getBackground());
        label.setFont(tree.getFont());
        hBox.add(label);
      }
      if(selected) panel.setBorder(selectedBorder);
      else panel.setBorder(normalBorder);
      return panel;
    }

    JLabel label;
    JCheckBox checkBox;
    JPanel panel;
    JPanel hBox;
    Border selectedBorder;
    Border normalBorder;
    Component leftSpacer, rightSpacer;
  }

  /**
   * A tree node renderer used byt the coref tree
   */
  class CorefNodeRenderer1 implements TreeCellRenderer{

    CorefNodeRenderer1(){
      label = new JLabel();
      label.setOpaque(true);

      toggleButton = new JToggleButton();
      toggleButton.setMargin(new Insets(0,3,0,3));

      panel = new LazyJPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setOpaque(false);
      topSpacer = Box.createVerticalStrut(2);
      bottomSpacer = Box.createVerticalStrut(2);

      selectedBorder = BorderFactory.createLineBorder(Color.blue, 1);
      normalBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);

    }

    public Component getTreeCellRendererComponent(JTree tree,
                                              Object value,
                                              boolean selected,
                                              boolean expanded,
                                              boolean leaf,
                                              int row,
                                              boolean hasFocus){

      panel.removeAll();
      panel.add(topSpacer);

      if(value instanceof DefaultMutableTreeNode){
        value = ((DefaultMutableTreeNode)value).getUserObject();
      }
      if(value instanceof CorefData){
        CorefData cData = (CorefData)value;
        toggleButton.setSelected(cData.getVisible());
        toggleButton.setBackground(cData.getColour());
        toggleButton.setForeground(tree.getForeground());
        toggleButton.setText(cData.getTitle());
        toggleButton.setFont(tree.getFont());
        panel.add(toggleButton);
      }else{
        label.setText(value.toString());
        label.setForeground(tree.getForeground());
        label.setBackground(tree.getBackground());
        label.setFont(tree.getFont());
        panel.add(label);
      }
      panel.add(bottomSpacer);
      if(selected) panel.setBorder(selectedBorder);
      else panel.setBorder(normalBorder);
      return panel;
    }

    JLabel label;
    JToggleButton toggleButton;
    JPanel panel;
    Border selectedBorder;
    Border normalBorder;
    Component topSpacer, bottomSpacer;
  }


  /**
   * Displays an entry in the right hand side tree.
   * <strong>Implementation Note:</strong>
   * This class overrides
   * <code>revalidate</code>,
   * <code>repaint</code>,
   * and
   * <code>firePropertyChange</code>
   * solely to improve performance.
   * If not overridden, these frequently called methods would execute code paths
   * that are unnecessary for a tree cell renderer.
   */
  class NodeRenderer extends LazyJPanel implements TreeCellRenderer{

    public NodeRenderer(){
      visibleChk = new JCheckBox("",false);
      visibleChk.setOpaque(false);
      visibleChk.setBorderPaintedFlat(true);

      label = new JLabel();
      label.setOpaque(true);
      fontAttrs = new HashMap();
      selectedBorder = BorderFactory.createLineBorder(Color.blue, 1);
      normalBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      setOpaque(false);
      spacer = Box.createHorizontalStrut(3);
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                              Object value,
                                              boolean selected,
                                              boolean expanded,
                                              boolean leaf,
                                              int row,
                                              boolean hasFocus){
      removeAll();
      add(spacer);

      int width = spacer.getWidth();


      TypeData nData = (TypeData)
                            ((DefaultMutableTreeNode)value).getUserObject();

      if(nData != null){
        label.setText(nData.getTitle());
        setLabelAttributes(nData.getAttributes());

        if(nData.getType() != null) {
          visibleChk.setSelected(nData.getVisible());
          add(visibleChk);
          width += visibleChk.getMinimumSize().width;
        }
      }else{
        label.setText(((value == null || value.toString() == null) ?
                              "" : value.toString()));
      }
      add(label);

      if(selected) setBorder(selectedBorder);
      else setBorder(normalBorder);
      return this;
    }//public Component getTreeCellRendererComponent

    protected void setLabelAttributes(AttributeSet style){
      label.setForeground(StyleConstants.getForeground(style));
      label.setBackground(StyleConstants.getBackground(style));
      fontAttrs.clear();
      fontAttrs.put(TextAttribute.FAMILY, StyleConstants.getFontFamily(style));
      fontAttrs.put(TextAttribute.SIZE, new Float(StyleConstants.getFontSize(style)));
      if(StyleConstants.isBold(style))
        fontAttrs.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
      else fontAttrs.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
      if(StyleConstants.isItalic(style))
        fontAttrs.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
      else fontAttrs.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
      if(StyleConstants.isUnderline(style))
        fontAttrs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
      else fontAttrs.remove(TextAttribute.UNDERLINE);
      if(StyleConstants.isStrikeThrough(style))
        fontAttrs.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
      else fontAttrs.remove(TextAttribute.STRIKETHROUGH);
      if(StyleConstants.isSuperscript(style))
        fontAttrs.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER);
      else if(StyleConstants.isSubscript(style))
        fontAttrs.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);
      else fontAttrs.remove(TextAttribute.SUPERSCRIPT);

      label.setFont(new Font(fontAttrs));
    }

    Border selectedBorder;
    Border normalBorder;
    JCheckBox visibleChk;
    JLabel label;
    Map fontAttrs;
    Component spacer;
  }//class NodeRenderer extends JPanel implements TreeCellRenderer
  /**
   * Displays an entry in the right hand side tree.
   * <strong>Implementation Note:</strong>
   * This class overrides
   * <code>revalidate</code>,
   * <code>repaint</code>,
   * and
   * <code>firePropertyChange</code>
   * solely to improve performance.
   * If not overridden, these frequently called methods would execute code paths
   * that are unnecessary for a tree cell renderer.
   */
  class NodeRenderer1 extends LazyJPanel implements TreeCellRenderer{

    public NodeRenderer1(){
      visibleChk = new JCheckBox("",false);
      visibleChk.setOpaque(false);
      visibleChk.setBorderPaintedFlat(true);
      textComponent = new JTextPane();
      selectedBorder = BorderFactory.createLineBorder(Color.blue, 1);
      normalBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      setOpaque(false);
      spacer = Box.createHorizontalStrut(3);
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                              Object value,
                                              boolean selected,
                                              boolean expanded,
                                              boolean leaf,
                                              int row,
                                              boolean hasFocus){
      removeAll();
      add(spacer);

      int width = spacer.getWidth();

      //the text pane needs to be sized for modelToView() to work
      textComponent.setSize(1000, 1000);

      TypeData nData = (TypeData)
                            ((DefaultMutableTreeNode)value).getUserObject();
//      javax.swing.text.Document doc = textComponent.getDocument();

      if(nData != null){
        textComponent.setText(nData.getTitle());
        textComponent.selectAll();
        textComponent.setCharacterAttributes(nData.getAttributes(), false);
        textComponent.select(0, 0);
//        try{
//          doc.remove(0, doc.getLength());
//          doc.insertString(0, nData.getTitle(),
//                           nData.getAttributes());
//        }catch(BadLocationException ble){
//          ble.printStackTrace();
//        }

        if(nData.getType() != null) {
          visibleChk.setSelected(nData.getVisible());
          add(visibleChk);
          width += visibleChk.getMinimumSize().width;
        }
      }else{
        textComponent.setText(((value == null || value.toString() == null) ?
                              "" : value.toString()));
//        try{
//          doc.remove(0, doc.getLength());
//          doc.insertString(0, value.toString(),
//                           textComponent.getStyle("default"));
//        }catch(BadLocationException ble){
//          ble.printStackTrace();
//        }
      }
      setTextComponentSize(textComponent);
      add(textComponent);
      width += textComponent.getPreferredSize().width;
      if(selected) setBorder(selectedBorder);
      else setBorder(normalBorder);
      width += getInsets().left + getInsets().right;
      setPreferredSize(null);
      setPreferredSize(new Dimension(width, super.getPreferredSize().height));
      return this;
    }//public Component getTreeCellRendererComponent

   protected void setTextComponentSize(JTextComponent comp){
      try{
        if(comp.getDocument() == null || comp.getDocument().getLength() <= 0){
          return;
        }
        int width = 0;
        Rectangle rect = comp.modelToView(0);
        int height = rect.height;
        int length = comp.getDocument().getLength();
        if(length > 0){
          Rectangle rect2 = comp.modelToView(length );
          if(rect2 != null){
            if(rect.x < rect2.x){
              //left to right
              width = rect2.x + rect2.width - rect.x;
            }else{
              //RtL
              width = rect.x +rect.width - rect2.x;
            }
            height = Math.max(height, rect2.height);
          }
        }
        Insets insets = comp.getInsets();
        Dimension dim = new Dimension(width + insets.left + insets.right + 5,
                                      height + insets.top + insets.bottom);
        comp.setPreferredSize(dim);
      }catch(BadLocationException ble){
        //this will work the next time around so it's safe to ignore it now
      }
    }
    Border selectedBorder;
    Border normalBorder;
    JCheckBox visibleChk;
    JTextPane textComponent;
    Component spacer;
  }//class NodeRenderer extends JPanel implements TreeCellRenderer

  /**
   * Displays an entry in the right hand side tree.
   * <strong><a name="override">Implementation Note:</a></strong>
   * This class overrides
   * <code>revalidate</code>,
   * <code>repaint</code>,
   * and
   * <code>firePropertyChange</code>
   * solely to improve performance.
   * If not overridden, these frequently called methods would execute code paths
   * that are unnecessary for a tree cell renderer.
   */
/*
  class NodeRenderer1 extends JPanel implements TreeCellRenderer{

    public NodeRenderer1(){
      visibleChk = new JCheckBox("",false);
      visibleChk.setOpaque(false);
      typeComponent = new JTextPane();
      setComponent = new JTextPane();
      selectedBorder = BorderFactory.createLineBorder(Color.blue);
      normalBorder = BorderFactory.createEmptyBorder(1,1,1,1);

      setPanel = new LazyJPanel();
      setPanel.setOpaque(false);
      setPanel.setLayout(new BoxLayout(setPanel, BoxLayout.X_AXIS));
      setPanel.add(setComponent);
      typePanel = new LazyJPanel();
      typePanel.setOpaque(false);
      typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
      typePanel.add(visibleChk);
      typePanel.add(typeComponent);
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                              Object value,
                                              boolean selected,
                                              boolean expanded,
                                              boolean leaf,
                                              int row,
                                              boolean hasFocus){
      JComponent renderer = null;
      TypeData nData = (TypeData)
                            ((DefaultMutableTreeNode)value).getUserObject();
      if(nData != null){
        if(nData.getType() != null) {
          visibleChk.setSelected(nData.getVisible());
          typeComponent.setSize(1000, 1000);
          javax.swing.text.Document doc = typeComponent.getDocument();
          try{
            doc.remove(0, doc.getLength());
            doc.insertString(0, nData.getTitle(), nData.getAttributes());
          }catch(BadLocationException ble){
            ble.printStackTrace();
          }
          setTextComponentSize(typeComponent);
//          typePanel.removeAll();
//          typePanel.add(visibleChk);
//          typePanel.add(typeComponent);
          renderer = typePanel;
        }else{
          setComponent.setSize(1000, 1000);
          javax.swing.text.Document doc = setComponent.getDocument();
          try{
            doc.remove(0, doc.getLength());
            doc.insertString(0, nData.getTitle(), nData.getAttributes());
          }catch(BadLocationException ble){
            ble.printStackTrace();
          }
          setTextComponentSize(setComponent);
//          setPanel.removeAll();
//          setPanel.add(setComponent);
          renderer = setPanel;
        }
      }else{
        setComponent.setSize(1000, 1000);
        javax.swing.text.Document doc = setComponent.getDocument();
        try{
          doc.remove(0, doc.getLength());
          doc.insertString(0, value.toString(), setComponent.getStyle("default"));
        }catch(BadLocationException ble){
          ble.printStackTrace();
        }
        setTextComponentSize(setComponent);
//        setPanel.removeAll();
//        setPanel.add(setComponent);
        renderer = setPanel;
      }
      if(selected) renderer.setBorder(selectedBorder);
      else renderer.setBorder(normalBorder);
      return renderer;
    }//public Component getTreeCellRendererComponent

    protected void setTextComponentSize(JTextComponent comp){
      try{
        Rectangle rect = comp.modelToView(0);
        int length = comp.getDocument().getLength();
        if(length > 0){
          Rectangle rect2 = comp.modelToView(length - 1);
          if(rect2 != null){
Out.pr("Rect2.x " + rect2.x);
            //this mutates rect
            rect = SwingUtilities.computeUnion(rect2.x, rect2.y, rect2.width,
                                        rect2.height, rect);
Out.prln("Rect.width " + rect.width);
          }else{
Out.prln("NULL size");
          }
        }
        Insets insets = comp.getInsets();
        Dimension dim = new Dimension(rect.width + insets.left + insets.right,
                                      rect.height + insets.top + insets.bottom);
        comp.setPreferredSize(dim);
      }catch(BadLocationException ble){
        ble.printStackTrace();
      }
    }

    Border selectedBorder;
    Border normalBorder;
    JCheckBox visibleChk;
    JTextPane setComponent;
    JTextPane typeComponent;
    JPanel setPanel;
    JPanel typePanel;
  }//class NodeRenderer extends JPanel implements TreeCellRenderer
*/
  /**
   * Holds the GUI metadata for a given annotation type. An annotation type is
   * uniquely identified by the name of its AnnotationSet and the name of the
   * type.
   * For the default annotation set of a document (which has no name) the
   * &quot;&lt;Default&gt;&quot; value is used.
   * The GUI metadata contains, amongst other things, the style used for
   * highlighting the annotations of this type.
   * These styles are cascading styles (there is a relation of inheritance
   * between them) so the annotation type style inherits the characteristics
   * from the style associated with the annotation set it belongs to.
   *
   * For eficiency reasons there are some intermediary styles between a parent
   * and a child style that used for changing the display in one operation.
   */
  public class TypeData {

    public TypeData(String set, String type, boolean visible){
      this.set = set;
      this.type = type;
      this.visible = visible;
      Map setMap = (Map)typeDataMap.get(set);
      if(setMap == null){
        setMap = new HashMap();
        typeDataMap.put(set, setMap);
      }
      if(type == null) {
        //this node represents a Set
        style = textPane.addStyle(set, textPane.getStyle("default"));
      } else {
        style = textPane.addStyle(set + "." + type, textPane.getStyle(set));
        StyleConstants.setBackground(style,
                                     colGenerator.getNextColor().brighter());
        //add an intermediary style that will be used attribute inheritance tricks
        middleStyle = visible ?
                      textPane.addStyle("_" + set + "." + type, style) :
                      textPane.addStyle("_" + set + "." + type,
                                        textPane.getStyle("default"));
        //add the style that will be used for the actual display
        actualStyle = textPane.addStyle("_" + set + "." + type + "_",
                          textPane.getStyle("_" + set + "." + type));
        setMap.put(type, this);
      }
    }

    public String getSet() { return set;}

    public void setSet(String set) {this.set = set;}

    public String getType() {return type;}

    public String getTitle() {return (type == null) ? set + " annotations" :
                                                      type;}
    public boolean getVisible() {return visible;}

    public void setVisible(boolean isVisible) {
      if(this.visible == isVisible) return;
      this.visible = isVisible;
      //this is most likely called from the SWING thread so we want to get
      //out of here as quickly as possible. We'll start a new thread that will
      //do all that needs doing
      Runnable runnable = new Runnable() {
        public void run() {
          if(visible) {
            //make the corresponding range visible
            //update the annotations table
            synchronized(data) {
              range = new Range(set, type, data.size(),
                                data.size() + annotations.size());
              ranges.add(range);
              data.addAll(annotations);
              SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                  annotationsTableModel.fireTableDataChanged();
                }
              });
            }

            //update the text display
//            Style tempStyle = textPane.getStyle("_" + set + "." + type);
            middleStyle.setResolveParent(style);
            showHighlights(annotations, actualStyle);
          } else {
            //hide the corresponding range
            //update the annotations table
            Collections.sort(ranges);
            Iterator rangesIter = ranges.iterator();
            while(rangesIter.hasNext()) {
              //find my range
              Range aRange = (Range)rangesIter.next();
              if(aRange == range){
                rangesIter.remove();
                int size = range.end - range.start;
                //remove the elements from Data
                data.subList(range.start, range.end).clear();
                //shift back all the remaining ranges
                while(rangesIter.hasNext()) {
                  aRange = (Range)rangesIter.next();
                  aRange.start -= size;
                  aRange.end -= size;
                }
              }
            }
            range = null;
            SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                annotationsTableModel.fireTableDataChanged();
              }
            });
            //update the text display
//            Style middleStyle = textPane.getStyle("_" + set + "." + type);
            middleStyle.setResolveParent(textPane.getStyle("default"));
          }//if(visible)
        }//public void run()
      };//Runnable runnable = new Runnable()
      Thread thread = new Thread(Thread.currentThread().getThreadGroup(),
                                   runnable,
                                   "AnnotationEditor4");
      thread.setPriority(Thread.MIN_PRIORITY);
      thread.start();
    }//public void setVisible(boolean isVisible)

    public AttributeSet getAttributes() { return style;}

    private AttributeSet getActualStyle() { return actualStyle;}

    public void setAttributes(AttributeSet newAttributes) {
      style.removeAttributes(style.copyAttributes());
      style.addAttributes(newAttributes);
    }


    public void setAnnotations(Set as) {
      this.annotations = as;
    }

    public Set getAnnotations() {
      return annotations;
    }

    public void setNode(DefaultMutableTreeNode node){
      this.node = node;
    }

    public DefaultMutableTreeNode getNode(){
      return node;
    }

    public String toString() {return getTitle();}

    private String set;
    private String type;
    private boolean visible;
    /**
     * The style used for annotations of this type
     */
    private Style style;

    /**
     * Used internally for attribute inheritance tricks.
     */
    private Style middleStyle;

    /**
     * The style actually used to affect the text.
     */
    private Style actualStyle;
    private Set annotations = null;
    private Range range = null;

    /** The node that represents this set/type in the types tree*/
    private DefaultMutableTreeNode node = null;
  }//class TypeData


  /**
   * Describes a range in the {@link #data} structure. A range is a bunch of
   * annotations of the same type belonging to the same annotation set that
   * are contiguous in the {@link #data} structure.
   */
  class Range implements Comparable {
    public Range(String setName, String type, int start, int end) {
      this.setName = setName;
      this.type = type;
      this.start = start;
      this.end = end;
    }

    public String toString() {
      return setName +  ", " + type + " (" + start + ", " + end + ")";
    }

    public int compareTo(Object other) {
      if(other instanceof Range) return start - ((Range)other).start;
      else throw new ClassCastException("Can't compare a " +
                                         other.getClass() + " to a " +
                                         getClass() + "!");
    }

    String setName;
    String type;
    int start;
    int end;
  }//class Range


  /**
   * All the events from the document or its annotation sets are handled by
   * this inner class.
   */
  class EventsHandler implements gate.event.DocumentListener,
                                 AnnotationSetListener{

    public void annotationSetAdded(gate.event.DocumentEvent e) {
      String setName = e.getAnnotationSetName();
      AnnotationSet as = (setName == null ? document.getAnnotations() :
                             document.getAnnotations(setName));

      as.addAnnotationSetListener(this);
      if(setName == null) setName = "Default";
      TypeData setData = new TypeData(setName, null, false);
      setData.setAnnotations(as);

      SwingUtilities.invokeLater(new NodeAdder(setData));

      ArrayList typesLst = new ArrayList(as.getAllTypes());
      Collections.sort(typesLst);

      Iterator typesIter = typesLst.iterator();
      while(typesIter.hasNext()){
        String type = (String)typesIter.next();
        TypeData typeData = new TypeData(setName, type, false);
        AnnotationSet sameType = as.get(type);
        typeData.setAnnotations(sameType);

        SwingUtilities.invokeLater(new NodeAdder(typeData));
      }
    }

    public void annotationSetRemoved(gate.event.DocumentEvent e) {
      //we access the GUI a lot here so we'll do everything from the
      //Swing thread
      SwingUtilities.invokeLater(
                     new SetRemovedOperation(e.getAnnotationSetName()));
    }//public void annotationSetRemoved(gate.event.DocumentEvent e)

    /**Called when the content of the document has changed through an edit
     * operation.
     */
    public void contentEdited(DocumentEvent e){
      //ignore
    }

    public void annotationAdded(AnnotationSetEvent e) {
      AnnotationSet set = (AnnotationSet)e.getSource();
      String setName = set.getName();
      if(setName == null) setName = "Default";
      Annotation ann = e.getAnnotation();
      String type = ann.getType();
      TypeData tData = getTypeData(setName, type);

      boolean tableChanged = false;
      if(tData != null){
//                tData.annotations.add(ann);
        if(tData.getVisible()){
          //1) update the table
          data.add(tData.range.end, ann);
          tData.range.end++;
          Iterator rangesIter = ranges.
                                subList(
                                    ranges.indexOf(tData.range) + 1,
                                        ranges.size()).
                                iterator();
          while(rangesIter.hasNext()){
            Range aRange = (Range) rangesIter.next();
            aRange.start++;
            aRange.end++;
          }//while(rangesIter.hasNext())
          tableChanged = true;

          //2) update the text
          SwingUtilities.invokeLater(
                         new HihglightsShower(ann,
                                              textPane.getStyle(
                                                "_" + setName + "." +
                                                type + "_")));
        }//if(tData.getVisible())
      } else {
        //new type
        Map setMap = (Map)typeDataMap.get(setName);
        if(setMap == null){
          setMap = new HashMap();
          typeDataMap.put(setName, setMap);
        }
        tData = new TypeData(setName, type, false);
        tData.setAnnotations(set.get(type));
        setMap.put(type, tData);
        SwingUtilities.invokeLater(new NodeAdder(tData));

      }//new type

      if(tableChanged){
        SwingUtilities.invokeLater(new Runnable() {
          public void run(){
            if(annotationsTableModel != null){
              annotationsTableModel.fireTableDataChanged();
            }
          }
        });
      }//if(tableChanged)
    }//public void annotationAdded(AnnotationSetEvent e)

    public void annotationRemoved(AnnotationSetEvent e){
      AnnotationSet set = (AnnotationSet)e.getSource();
      String setName = set.getName();
      if(setName == null) setName = "Default";
      Annotation ann = e.getAnnotation();
      String type = ann.getType();
      TypeData tData = getTypeData(setName, type);
      boolean tableChanged = false;

      if(tData != null){
//                tData.annotations.remove(ann);
        if(tData.getVisible()){
          //1) update the annotations table
          data.remove(ann);
          //shorten the range conatining the annotation
          tData.range.end--;
          //shift all the remaining ranges
          Iterator rangesIter = ranges.
                              subList(ranges.indexOf(tData.range) + 1,
                              ranges.size()).
                                iterator();
          while(rangesIter.hasNext()){
            Range aRange = (Range) rangesIter.next();
            aRange.start--;
            aRange.end--;
          }//while(rangesIter.hasNext())
          tableChanged = true;
        }//if(tData.getVisible())
        //update the text -> hide the highlight
        //required even if not visible
        SwingUtilities.invokeLater(new HighlightsRemover(ann));

        //if this was the last annotation of this type remove the type node
        if((tData.annotations.size() == 1 &&
           tData.annotations.iterator().next() == ann) ||
           tData.annotations.size() == 0){
          //no more annotations of this type -> delete the node
          SwingUtilities.invokeLater(new NodeRemover(tData));
          //remove the data for this type
          Map setMap = (Map)typeDataMap.get(setName);
          setMap.remove(tData.getType());
        }//if(tData.getAnnotations().isEmpty())
      }//if(tData != null)

      if(tableChanged){
        SwingUtilities.invokeLater(new Runnable() {
          public void run(){
            if(annotationsTableModel != null){
              annotationsTableModel.fireTableDataChanged();
            }
          }
        });
      }//if(tableChanged)
    }//public void annotationRemoved(AnnotationSetEvent e)

    /**
     * Helper class that removes one highlight corresponding to an annotation.
     */
    class HighlightsRemover implements Runnable{
      HighlightsRemover(Annotation ann){
        this.ann = ann;
      }
      public void run(){
        int selStart = textPane.getSelectionStart();
        int selEnd = textPane.getSelectionEnd();
        textPane.select(ann.getStartNode().getOffset().intValue(),
                        ann.getEndNode().getOffset().intValue());
        textPane.setCharacterAttributes(
                  textPane.getStyle("default"), true);
        textPane.select(selStart, selEnd);
      }
      Annotation ann;
    }//class HihglightsRemover implements Runnable

    /**
     * Helper class that highlights a given annotation with the specified style.
     */
    class HihglightsShower implements Runnable{
      HihglightsShower(Annotation ann, Style style){
        this.ann = ann;
        this.style = style;
      }
      public void run(){
        textPane.select(ann.getStartNode().getOffset().intValue(),
                        ann.getEndNode().getOffset().intValue());
        textPane.setCharacterAttributes(style, true);
      }
      Annotation ann;
      Style style;
    }//class HihglightsRemover implements Runnable

    /**
     * Helper class that removes one node from the types tree.
     */
    class NodeRemover implements Runnable{
      NodeRemover(TypeData tData){
        this.tData = tData;
      }
      public void run(){
        DefaultMutableTreeNode node = tData.getNode();
        if(node != null){
          stylesTreeModel.removeNodeFromParent(tData.getNode());
        }else{
          Err.prln("Could not find node for " + tData.set + "->" + tData.type);
        }
      }
      TypeData tData;
    }//class NodeRemover implements Runnable

    /**
     * Helper class that adds a specified tree node
     */
    class NodeAdder implements Runnable{
      NodeAdder(TypeData tData){
        this.tData = tData;
      }
      public void run(){
        //create the new node
        DefaultMutableTreeNode newNode =
                  new DefaultMutableTreeNode(tData, tData.getType() == null);
        tData.setNode(newNode);
        //find its parent
        DefaultMutableTreeNode node = null;
        if(tData.getType() == null){
          //set node
          node = (DefaultMutableTreeNode)stylesTreeRoot;
//System.out.println("Set node " + tData.getSet());
        }else{
//System.out.println("Type node " + tData.getSet() + ":" + tData.getType());

          //the document should at least have the default annotation set
          //if it doesn't, then something's fishy -> return;
          if(((DefaultMutableTreeNode)stylesTreeRoot).getChildCount() == 0)
            return;
          node = (DefaultMutableTreeNode)
            ((DefaultMutableTreeNode)stylesTreeRoot).getFirstChild();
          while(node != null &&
            !((TypeData)node.getUserObject()).getSet().equals(tData.getSet()))
            node = node.getNextSibling();
        }

        //we have to add typeNode to node
        //find the right place
        int i = 0;
        if(tData.getType() == null){
          while (i < node.getChildCount() &&
                ((TypeData)
                  ((DefaultMutableTreeNode)node.getChildAt(i)).
                  getUserObject()
                ).getSet().compareTo(tData.getSet())<0) i++;
        }else{
          while (i < node.getChildCount() &&
                ((TypeData)
                  ((DefaultMutableTreeNode)node.getChildAt(i)).
                  getUserObject()
                ).getType().compareTo(tData.getType())<0) i++;
        }

        //insert it!
        stylesTreeModel.insertNodeInto(newNode, node, i);

        if(tData.getType() == null){
          //set node, expand it!
          stylesTree.expandPath(new TreePath(new Object[]{stylesTreeRoot,
                                                          newNode}));
        }
      }

      TypeData tData;
    }//class NodeAdder implements Runnable

    /**
     * Helper class that handles the removal of a named annotation set.
     * This runnable should only be called from the Swing thread
     */
    class SetRemovedOperation implements Runnable{
      SetRemovedOperation(String setName){
        this.setName = setName;
      }

      public void run(){
        //find the set node
        Enumeration setNodesEnum = stylesTreeRoot.children();
        DefaultMutableTreeNode setNode = null;
        boolean done = false;
        while(!done && setNodesEnum.hasMoreElements()){
          setNode = (DefaultMutableTreeNode)setNodesEnum.nextElement();
          done = ((TypeData)setNode.getUserObject()).getSet().equals(setName);
        }

        if(!((TypeData)setNode.getUserObject()).getSet().equals(setName)){
          throw new GateRuntimeException(
                "Could not find the tree node for the " + setName +
                " annotation set!");
        }

        boolean tableChanged = false;
        Enumeration typeNodesEnum = setNode.children();
        while(typeNodesEnum.hasMoreElements()){
          DefaultMutableTreeNode typeNode =
            (DefaultMutableTreeNode)typeNodesEnum.nextElement();
          TypeData tData = (TypeData)typeNode.getUserObject();
          if(tData.getVisible()){
            //1) update the annotations table
            data.subList(tData.range.start, tData.range.end).clear();
            //remove the range
            int delta = tData.range.end - tData.range.start;
            //1a)first shift all following ranges
            Iterator rangesIter = ranges.
                                subList(ranges.indexOf(tData.range) + 1,
                                ranges.size()).
                                  iterator();
            while(rangesIter.hasNext()){
              Range aRange = (Range) rangesIter.next();
              aRange.start -= delta;
              aRange.end -= delta;
            }//while(rangesIter.hasNext())
            //1b)now remove the range
            ranges.remove(tData.range);
            tableChanged = true;

            //2)update the text
            //hide the highlights

            Iterator annIter = tData.getAnnotations().iterator();
            while(annIter.hasNext()){
              Annotation ann = (Annotation)annIter.next();
              new HighlightsRemover(ann).run();
            }//while(annIter.hasNext())
          }//if(tData.getVisible())
        }//while(typeNodesEnum.hasMoreElements())

        if(tableChanged){
          if(annotationsTableModel != null){
            annotationsTableModel.fireTableDataChanged();
          }
        }//if(tableChanged)

        //remove the node for the set
        typeDataMap.remove(setName);
        stylesTreeModel.removeNodeFromParent(setNode);
      }//public void run()

      String setName;
    }

  }//class EventsHandler

  /**
   *  Listens for updates from the text editor and updates the GATE document
   *  accordingly
   */
  class SwingDocumentListener implements javax.swing.event.DocumentListener{
    public void insertUpdate(final javax.swing.event.DocumentEvent e) {
      try{
        document.edit(new Long(e.getOffset()), new Long(e.getOffset()),
                      new DocumentContentImpl(
                        e.getDocument().getText(e.getOffset(), e.getLength())));
        SwingUtilities.invokeLater(new Runnable(){
          public void run(){
            annotationsTable.repaint();
            repairHighlights(e.getOffset(), e.getOffset() + e.getLength());
          }
        });
        updateBlinks();
      }catch(BadLocationException ble){
        ble.printStackTrace(Err.getPrintWriter());
      }catch(InvalidOffsetException ioe){
        ioe.printStackTrace(Err.getPrintWriter());
      }
    }

    public void removeUpdate(javax.swing.event.DocumentEvent e) {
      try{
        document.edit(new Long(e.getOffset()),
                      new Long(e.getOffset() + e.getLength()),
                      new DocumentContentImpl(""));
        SwingUtilities.invokeLater(new Runnable(){
          public void run(){
            annotationsTable.repaint();
          }
        });
        updateBlinks();
      }catch(InvalidOffsetException ioe){
        ioe.printStackTrace(Err.getPrintWriter());
      }
    }

    public void changedUpdate(javax.swing.event.DocumentEvent e) {
      //some attributes changed: we don't care about that
    }
    /**
     * Restores the blinking selection if any
     */
    protected void updateBlinks(){
      int[] rows = annotationsTable.getSelectedRows();
      if(rows != null && rows.length > 0){
        selectionHighlighter.removeAllHighlights();
        for(int i = 0; i < rows.length; i++){
          int start = ((Long)annotationsTable.getModel().
                       getValueAt(rows[i], 2)
                      ).intValue();
          int end = ((Long)annotationsTable.getModel().
                     getValueAt(rows[i], 3)
                    ).intValue();

          // compute correction for new line breaks in long lines
          start += longLinesCorrection(start);
          end += longLinesCorrection(end);

          //start blinking the annotation
          try{
            synchronized (selectionHighlighter){
              selectionHighlighter.addHighlight(start, end,
                          DefaultHighlighter.DefaultPainter);
            }
          }catch(BadLocationException ble){
            throw new GateRuntimeException(ble.toString());
          }
        }//for(int i = 0; i < rows.length; i++)

//        annotationsTable.clearSelection();
//        for (int i = 0; i < rows.length; i++) {
//          annotationsTable.addRowSelectionInterval(rows[i], rows[i]);
//        }
      }

    }
  }//class SwingDocumentListener implements javax.swing.event.DocumentListener

  /**
   * This class handles the blinking for the selected annotations in the
   * text display.
   */
  class SelectionBlinker implements Runnable{
    public void run(){
      synchronized(selectionHighlighter){
        highlights = selectionHighlighter.getHighlights();
      }


      while(highlights != null && highlights.length > 0){
        SwingUtilities.invokeLater(new Runnable(){
          public void run(){
            showHighlights();
          }
        });
        try{
          Thread.sleep(400);
        }catch(InterruptedException ie){
          ie.printStackTrace(Err.getPrintWriter());
        }
        SwingUtilities.invokeLater(new Runnable(){
          public void run(){
            hideHighlights();
          }
        });

        try{
          Thread.sleep(600);
        }catch(InterruptedException ie){
          ie.printStackTrace(Err.getPrintWriter());
        }
        synchronized(selectionHighlighter){
          highlights = selectionHighlighter.getHighlights();
        }
      }//while we have highlights
      //no more highlights; stop the thread by exiting run();
      synchronized(selectionHighlighter){
        thread = null;
      }
    }//run()

    /**
     * If there is no running thread then starts one and stores it in
     * the <tt>thread</tt> member.
     */
    public synchronized void testAndStart(){
      synchronized(selectionHighlighter){
        if(thread == null){
          thread  = new Thread(Thread.currentThread().getThreadGroup(),
                               this, "AnnotationEditor2");
          thread.setPriority(Thread.MIN_PRIORITY);
          thread.start();
        }
      }
    }

    protected void showHighlights(){
      actualHighlights.clear();
      try{
        for(int i = 0; i < highlights.length; i++){
          actualHighlights.add(highlighter.addHighlight(
                                   highlights[i].getStartOffset(),
                                   highlights[i].getEndOffset(),
                                   highlights[i].getPainter()));
        }
      }catch(BadLocationException ble){
        ble.printStackTrace(Err.getPrintWriter());
      }
    }

    protected void hideHighlights(){
      Iterator hIter = actualHighlights.iterator();
      while(hIter.hasNext()) highlighter.removeHighlight(hIter.next());
    }

    ArrayList actualHighlights = new ArrayList();
    Thread thread;
    Highlighter.Highlight[] highlights;
  }//class SelectionBlinker implements Runnable

  /**
   * Fixes the <a
   * href="http://developer.java.sun.com/developer/bugParade/bugs/4406598.html">
   * 4406598 bug</a> in swing text components.
   * The bug consists in the fact that the Background attribute is ignored by
   * the text component whent it is defined in a style from which the current
   * style inherits.
   */
  public class CustomLabelView extends javax.swing.text.LabelView {
    public CustomLabelView(Element elem) {
      super(elem);
    }

    public Color getBackground() {
      AttributeSet attr = getAttributes();
      if (attr != null) {
        javax.swing.text.Document d = super.getDocument();
        if (d instanceof StyledDocument){
          StyledDocument doc = (StyledDocument) d;
          return doc.getBackground(attr);
        }else{
          return null;
        }
      }
      return null;
    }
  }

  /**
   * The popup menu items used to select annotations at right click.
   * Apart from the normal {@link javax.swing.JMenuItem} behaviour, this menu
   * item also highlits the annotation which it would select if pressed.
   */
  protected class HighlightAnnotationMenu extends JMenu {
    public HighlightAnnotationMenu(Annotation ann, AnnotationSet aSet) {
      super(ann.getType());
      setToolTipText("<html><b>Features:</b><br>" +
                     (ann.getFeatures() == null ? "" :
                     ann.getFeatures().toString()) + "</html>");
      this.annotation = ann;
      this.set = aSet;
      this.setName = (set.getName() == null) ? "Default" : set.getName();
      start = ann.getStartNode().getOffset().intValue();
      end = ann.getEndNode().getOffset().intValue();
      this.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
          try {
            highlight = highlighter.addHighlight(start, end,
                                            DefaultHighlighter.DefaultPainter);
          }catch(BadLocationException ble){
            throw new GateRuntimeException(ble.toString());
          }
        }

        public void mouseExited(MouseEvent e) {
          if(highlight != null){
            highlighter.removeHighlight(highlight);
            highlight = null;
          }
        }
      });

      this.add(new AbstractAction(){
        {
          putValue(NAME, "Select");
        }
        public void actionPerformed(ActionEvent e) {
          Runnable runnable = new Runnable(){
            public void run(){
              if(highlight != null){
                highlighter.removeHighlight(highlight);
                highlight = null;
              }
              selectAnnotation(setName, annotation);
            }
          };
          Thread thread = new Thread(Thread.currentThread().getThreadGroup(),
                                     runnable,
                                     "AnnotationEditor5");
          thread.start();
        }
      });

      this.add(new AbstractAction(){
        {
          putValue(NAME, "Delete");
        }
        public void actionPerformed(ActionEvent e) {
          Runnable runnable = new Runnable(){
            public void run(){
              if(highlight != null){
                highlighter.removeHighlight(highlight);
                highlight = null;
              }
              set.remove(annotation);
            }
          };
          Thread thread = new Thread(Thread.currentThread().getThreadGroup(),
                                     runnable,
                                     "AnnotationEditor5");
          thread.start();
        }
      });

    }

    int start;
    int end;
    AnnotationSet set;
    String setName;
    Annotation annotation;
    Object highlight;
  }


  protected class DeleteSelectedAnnotationsAction extends AbstractAction {
    public DeleteSelectedAnnotationsAction(JComponent source){
      super("Delete selected annotations");
      this.source = source;
    }

    public void actionPerformed(ActionEvent evt){
      if(source == annotationsTable){
        //collect the list of annotations to be removed
        //maps from set name to list of annotations to be removed
        Map annotationsBySet = new HashMap();
        int[] rows = annotationsTable.getSelectedRows();
        String setName;
        for(int i = 0; i < rows.length; i++){
          int row = rows[i];
          //find the annotation
          Annotation ann = (Annotation)annotationsTable.
                              getModel().getValueAt(row, -1);
          //find the annotation set
          setName = (String)annotationsTable.getModel().
                                                      getValueAt(row, 1);
          java.util.List existingList = (java.util.List)
                                        annotationsBySet.get(setName);
          if(existingList == null){
            existingList = new ArrayList();
            annotationsBySet.put(setName, existingList);
          }
          existingList.add(ann);
        }//for(int i = 0; i < rows.length; i++)
        //remove the collected annotations
        Iterator setsIter = annotationsBySet.keySet().iterator();
        while(setsIter.hasNext()){
          setName = (String)setsIter.next();
          AnnotationSet set = setName.equals("Default")?
                              document.getAnnotations() :
                              document.getAnnotations(setName);
          set.removeAll((java.util.List)annotationsBySet.get(setName));
        }//while(setsIter.hasNext())
      }else if(source == stylesTree){
        TreePath[] paths = stylesTree.getSelectionPaths();
        for(int i = 0; i < paths.length; i++){
          TypeData tData = (TypeData)((DefaultMutableTreeNode)
                            paths[i].getLastPathComponent()).getUserObject();
          String setName = tData.getSet();
          if(tData.getType() == null){
            //set node
            if(setName.equals("Default")){
              JOptionPane.showMessageDialog(
                DocumentEditor.this,
                "The default annotation set cannot be deleted!\n" +
                "It will only be cleared...",
                "GATE", JOptionPane.ERROR_MESSAGE);
              document.getAnnotations().clear();
            }else{
              document.removeAnnotationSet(setName);
            }
          }else{
            //type node
            if(!setName.equals("Default") &&
               !document.getNamedAnnotationSets().containsKey(setName)){
              //the set for this type has already been removed completely
              //nothing more do (that's nice :) )
              return;
            }
            AnnotationSet set = setName.equals("Default") ?
                                document.getAnnotations() :
                                document.getAnnotations(setName);
            if(set != null){
              AnnotationSet subset = set.get(tData.getType());
              if(subset != null) set.removeAll(new ArrayList(subset));
            }//if(set != null)
          }//type node
        }//for(int i = 0; i < paths.length; i++)
      }else if(source == corefTree){
        TreePath[] paths = corefTree.getSelectionPaths();
        for(int i = 0; i < paths.length; i++){
          CorefData cData = (CorefData)((DefaultMutableTreeNode)
                            paths[i].getLastPathComponent()).getUserObject();
          class CorefClearer implements Runnable{
            CorefClearer(CorefData cData){
              this.cData = cData;
            }
            public void run(){
              cData.removeAnnotations();
            }
            CorefData cData;
          }
          Thread thread = new Thread(new CorefClearer(cData));
          thread.setPriority(Thread.MIN_PRIORITY);
          thread.start();
        }
      }
    }//public void actionPerformed(ActionEvent evt)
    JComponent source;
  }//protected class DeleteSelectedAnnotationsAction

  protected class SearchAction extends AbstractAction {
    public SearchAction(){
      super("Search");
      putValue(SHORT_DESCRIPTION, "Search within the text");
      putValue(SMALL_ICON, MainFrame.getIcon("search"));
    }

    public void actionPerformed(ActionEvent evt){
      if(searchDialog == null){
        Window parent = SwingUtilities.getWindowAncestor(DocumentEditor.this);
        searchDialog = (parent instanceof Dialog) ?
                       new SearchDialog((Dialog)parent) :
                       new SearchDialog((Frame)parent);
        searchDialog.pack();
        searchDialog.setLocationRelativeTo(DocumentEditor.this);
        searchDialog.setResizable(false);
        MainFrame.getGuiRoots().add(searchDialog);
      }

      if(searchDialog.isVisible()){
        searchDialog.toFront();
      }else{
        searchDialog.setVisible(true);
      }
    }
  }

  protected class SearchDialog extends JDialog{
    SearchDialog(Frame owner){
      super(owner, false);
      setTitle( "Find in \"" + document.getName() + "\"");
      initLocalData();
      initGuiComponents();
      initListeners();
    }

    SearchDialog(Dialog owner){
      super(owner, false);
      setTitle("Find in \"" + document.getName() + "\"");
      initLocalData();
      initGuiComponents();
      initListeners();
    }
    protected void initLocalData(){
      pattern = null;
      nextMatchStartsFrom = 0;
      content = document.getContent().toString();

      findFirstAction = new AbstractAction("Find first"){
        {
          putValue(SHORT_DESCRIPTION, "Finds first match");
        }

        public void actionPerformed(ActionEvent evt){
          //needed to create the right RE
          refresh();
          //remove selection
          textPane.setCaretPosition(textPane.getCaretPosition());
          boolean found = false;
          int start = nextMatchStartsFrom - 1;
          int end = -1;

          Matcher matcher = pattern.matcher(content);
          while (matcher.find() && (found == false))
              {
                  start = matcher.start();
                  end = matcher.end();
                  if (wholeWordsChk.isSelected())
                      {
                          //validate the result
                          found = (start == 0 || !Character.isLetterOrDigit(content.charAt(start - 1)))
                                  && (end == content.length() || !Character.isLetterOrDigit(content.charAt(end)));
                      }
                  else
                      found = true;
              }

          if (found)
              {
                  nextMatchStartsFrom = start + 1;
                  //display the result
                  SwingUtilities.getWindowAncestor(textPane).requestFocus();
                  textPane.requestFocus();
                  textPane.setCaretPosition(start);
                  textPane.moveCaretPosition(end);
              }
          else
              {
                  JOptionPane.showMessageDialog(searchDialog, "String not found!", "GATE", JOptionPane.INFORMATION_MESSAGE);
              }
         }
      };


      findNextAction = new AbstractAction("Find next"){
        {
          putValue(SHORT_DESCRIPTION, "Finds next match");
        }
        public void actionPerformed(ActionEvent evt){
          //needed to create the right RE
          refresh();
          //remove selection
          textPane.setCaretPosition(textPane.getCaretPosition());
          boolean found = false;
          int start = nextMatchStartsFrom - 1;
          int end = -1;

          Matcher matcher = pattern.matcher(content);
          while (matcher.find() && (found == false))
              {
                  start = matcher.start();
                  end = matcher.end();
                  if (wholeWordsChk.isSelected())
                      {
                          //validate the result
                          found = (start == 0 || !Character.isLetterOrDigit(content.charAt(start - 1)))
                                  && (end == content.length() || !Character.isLetterOrDigit(content.charAt(end)));
                      }
                  else
                      found = true;
              }

          if (found)
              {
                  nextMatchStartsFrom = start + 1;
                  //display the result
                  SwingUtilities.getWindowAncestor(textPane).requestFocus();
                  textPane.requestFocus();
                  textPane.setCaretPosition(start);
                  textPane.moveCaretPosition(end);
              }
          else
              {
                  JOptionPane.showMessageDialog(searchDialog, "String not found!", "GATE", JOptionPane.INFORMATION_MESSAGE);
              }
            }
           };

      cancelAction = new AbstractAction("Cancel"){
        {
          putValue(SHORT_DESCRIPTION, "Cancel");
        }
        public void actionPerformed(ActionEvent evt){
          searchDialog.setVisible(false);
        }
      };

    }


    protected void initGuiComponents(){
      getContentPane().setLayout(new BoxLayout(getContentPane(),
                                               BoxLayout.Y_AXIS));

      getContentPane().add(Box.createVerticalStrut(5));
      Box hBox = Box.createHorizontalBox();
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(new JLabel("Find what:"));
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(patternTextField = new JTextField(20));
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(Box.createHorizontalGlue());
      getContentPane().add(hBox);

      getContentPane().add(Box.createVerticalStrut(5));
      hBox = Box.createHorizontalBox();
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(ignoreCaseChk = new JCheckBox("Ignore case", false));
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(wholeWordsChk = new JCheckBox("Whole words only", false));
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(Box.createHorizontalGlue());
      getContentPane().add(hBox);

      getContentPane().add(Box.createVerticalStrut(5));
      hBox = Box.createHorizontalBox();
      hBox.add(Box.createHorizontalGlue());
      hBox.add(new JButton(findFirstAction));
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(new JButton(findNextAction));
      hBox.add(Box.createHorizontalStrut(5));
      hBox.add(new JButton(cancelAction));
      hBox.add(Box.createHorizontalGlue());
      getContentPane().add(hBox);
      getContentPane().add(Box.createVerticalStrut(5));
    }

    protected void initListeners(){
      addComponentListener(new ComponentAdapter() {
        public void componentHidden(ComponentEvent e) {
        }

        public void componentMoved(ComponentEvent e) {
        }

        public void componentResized(ComponentEvent e) {
        }

        public void componentShown(ComponentEvent e) {
          refresh();
        }
      });

      patternTextField.getDocument().addDocumentListener(
        new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
          refresh();
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
          refresh();
        }

        public void changedUpdate(javax.swing.event.DocumentEvent e) {
          refresh();
        }
      });

    }

    protected void refresh(){
      String patternText = patternTextField.getText();
      if(patternText != null && patternText.length() > 0){
        //update actions state
        findFirstAction.setEnabled(true);
        findNextAction.setEnabled(true);

        //update patternRE
        try
		    {
		        pattern = ignoreCaseChk.isSelected() ? Pattern.compile(patternText, Pattern.CASE_INSENSITIVE) : Pattern
		                .compile(patternText);
		    }
		catch (Exception ree)
		    {
		        JOptionPane.showMessageDialog(searchDialog, "Invalid pattern!\n" + ree.toString(), "GATE", JOptionPane.ERROR_MESSAGE);
		    }
      }else{
        findFirstAction.setEnabled(false);
        findNextAction.setEnabled(false);
      }

      if(pattern == null){
      }
    }
    JTextField patternTextField;
    JCheckBox ignoreCaseChk;
    JCheckBox wholeWordsChk;
    Pattern pattern;
    int nextMatchStartsFrom;
    String content;

    Action findFirstAction;
    Action findNextAction;
    Action cancelAction;
  }

  protected class PrintAction extends AbstractAction{
    public PrintAction(){
      super("Print");
    }

    public void actionPerformed(ActionEvent e){
      Runnable runnable = new Runnable(){
        public void run(){
          PrinterJob printerJob = PrinterJob.getPrinterJob();

          if (printerJob.printDialog()) {
            try{

//              PageFormat pageFormat = printerJob.pageDialog(printerJob.defaultPage());
              PageFormat pageFormat = printerJob.defaultPage();
              Pageable pageable = new JComponentPrinter(textPane, pageFormat);
              printerJob.setPageable(pageable);

              printerJob.print();
              //fire the events
              StatusListener sListener = (StatusListener)MainFrame.
                                         getListeners().
                                         get("gate.event.StatusListener");
              if(sListener != null){
                sListener.statusChanged("Document printed!");
              }

            }catch(Exception ex) {
              ex.printStackTrace();
            }
          }
        }
      };

      Thread thread = new Thread(Thread.currentThread().getThreadGroup(),
                                 runnable, "Print thread");
      thread.setPriority(Thread.MIN_PRIORITY);
      thread.start();
    }
  }


  /**
   * The action that is fired when the user wants to edit an annotation.
   * It will build a dialog containing all the valid annotation editors.
   */
  protected class EditAnnotationAction extends AbstractAction {
    public EditAnnotationAction(AnnotationSet set, Annotation annotation){
      super("Edit");
      this.set = set;
      this.annotation = annotation;
      putValue(SHORT_DESCRIPTION, "Edits the annotation");
    }

    public void actionPerformed(ActionEvent e){
      //get the list of editors
      java.util.List specificEditors = Gate.getCreoleRegister().
                                       getAnnotationVRs(annotation.getType());
      java.util.List genericEditors = Gate.getCreoleRegister().
                                      getAnnotationVRs();
      //create the GUI
      JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
      //add all the specific editors
      Iterator editorIter = specificEditors.iterator();
      while(editorIter.hasNext()){
        String editorType = (String)editorIter.next();
        //create the editor
        AnnotationVisualResource editor;
        try{
          editor = (AnnotationVisualResource)
                   Factory.createResource(editorType);
          if(editor instanceof ResizableVisualResource){
            tabbedPane.add((Component)editor,
                           ((ResourceData)Gate.getCreoleRegister().
                           get(editorType)).getName());
          }else{
            JScrollPane scroller = new JScrollPane((Component)editor);
//            scroller.setPreferredSize(((Component) editor).getPreferredSize());
            tabbedPane.add(scroller,
                           ((ResourceData)Gate.getCreoleRegister().
                            get(editorType)).getName());
          }

//          editor.setTarget(set);
//          editor.setAnnotation(annotation);
          editor.editAnnotation(annotation, set);
        }catch(ResourceInstantiationException rie){
          rie.printStackTrace(Err.getPrintWriter());
        }
      }

      //add all the generic editors
      editorIter = genericEditors.iterator();
      while(editorIter.hasNext()){
        String editorType = (String)editorIter.next();
        //create the editor
        AnnotationVisualResource editor;
        try{
          editor  = (AnnotationVisualResource)
                                          Factory.createResource(editorType);
          if(editor.canDisplayAnnotationType(annotation.getType())){
//            editor.setTarget(set);
//            editor.setAnnotation(annotation);
            editor.editAnnotation(annotation, set);
            if(editor instanceof ResizableVisualResource){
              tabbedPane.add((Component)editor,
                             ((ResourceData)Gate.getCreoleRegister().
                                                get(editorType)).getName());
            }else{
              tabbedPane.add(new JScrollPane((Component)editor),
                             ((ResourceData)Gate.getCreoleRegister().
                                                get(editorType)).getName());
            }
          }
        }catch(ResourceInstantiationException rie){
          rie.printStackTrace(Err.getPrintWriter());
        }

      }

      //show the modal dialog until the data is OK or the user cancels
      boolean allOK = false;
      while(!allOK){
        if(OkCancelDialog.showDialog(DocumentEditor.this,
                                     tabbedPane,
                                     "Edit Annotation")){
          try{
            Component comp = tabbedPane.getSelectedComponent();
            if(comp instanceof AnnotationVisualResource){
              ((AnnotationVisualResource)comp).okAction();
            }else if(comp instanceof JScrollPane){
              ((AnnotationVisualResource)((JScrollPane)comp).
                                          getViewport().getView()).okAction();
            }else{
              throw new LuckyException("DocumentEditor.EditAnnotationAction1");
            }

            allOK = true;
          }catch(GateException ge){
            JOptionPane.showMessageDialog(
              DocumentEditor.this,
              "There was an error:\n" +
              ge.toString(),
              "GATE", JOptionPane.ERROR_MESSAGE);
            ge.printStackTrace(Err.getPrintWriter());
            allOK = false;
          }
        }else{
          if (false)
            try{
              Component comp = tabbedPane.getSelectedComponent();
              if(comp instanceof AnnotationVisualResource){
                ((AnnotationVisualResource)comp).cancelAction();
              }else if(comp instanceof JScrollPane){
                ((AnnotationVisualResource)
                    ((JScrollPane)comp).getViewport().getView()).cancelAction();
              }else{
                throw new LuckyException("DocumentEditor.EditAnnotationAction");
              }
              allOK = true;
            } catch(GateException ge){
              JOptionPane.showMessageDialog(
                DocumentEditor.this,
                "There was an error:\n" +
                ge.toString(),
                "GATE", JOptionPane.ERROR_MESSAGE);
              allOK = false;
            }
          allOK = true;
        }
      }//while(!allOK)
    }//public void actionPerformed(ActionEvent e)

    protected AnnotationSet set;
    protected Annotation annotation;
  }//class EditAnnotationAction

  /**
   * The action that is fired when the user wants to create a new annotation.
   * It will build a dialog containing all the valid annotation editors.
   */
  class NewAnnotationAction extends AbstractAction{
    public NewAnnotationAction(AnnotationSet set,
                               Long startOffset,
                               Long endOffset){
      super("New annotation");
      putValue(SHORT_DESCRIPTION, "Creates a new annotation");
      this.set = set;
      this.startOffset = startOffset;
      this.endOffset = endOffset;
      this.type = null;
    }

    public NewAnnotationAction(AnnotationSet set, String type,
                               Long startOffset, Long endOffset){
      super("New \"" + type + "\" annotation");
      putValue(SHORT_DESCRIPTION, "Creates a new annotation of type \"" +
                                  type + "\"");
      this.set = set;
      this.startOffset = startOffset;
      this.endOffset = endOffset;
      this.type = type;
    }

    public void actionPerformed(ActionEvent e){
      if(set == null){
        //get the name from the user
        String setName = JOptionPane.showInputDialog(
              DocumentEditor.this,
              "Please provide a name for the new annotation set",
              "GATE", JOptionPane.QUESTION_MESSAGE);
        if(setName == null) return;
        this.set = document.getAnnotations(setName);
      }
      try{
        //create the new annotation
        Integer annId = set.add(startOffset, endOffset,
                (type == null ? "_New_" : type),
                Factory.newFeatureMap());
        Annotation ann = set.get(annId);
        new EditAnnotationAction(set, ann).actionPerformed(e);
      }catch (GateException ge) {
        JOptionPane.showMessageDialog(
                DocumentEditor.this,
                "There was an error:\n" +
                ge.toString(),
                "GATE", JOptionPane.ERROR_MESSAGE);
      }
//
//      //get the lists of editors
//      java.util.List specificEditors;
//      if(type != null) specificEditors = Gate.getCreoleRegister().
//                                         getAnnotationVRs(type);
//      else specificEditors = new ArrayList();
//
//      java.util.List genericEditors = Gate.getCreoleRegister().
//                                      getAnnotationVRs();
//      //create the GUI
//      JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
//      //add all the specific editors
//      Iterator editorIter = specificEditors.iterator();
//      while(editorIter.hasNext()){
//        String editorType = (String)editorIter.next();
//        //create the editor
//        AnnotationVisualResource editor;
//        try{
//          editor = (AnnotationVisualResource)
//                                          Factory.createResource(editorType);
//          tabbedPane.add(new JScrollPane((Component)editor),
//                        ((ResourceData)Gate.getCreoleRegister().get(editorType)).
//                                                                getName());
//          editor.setTarget(set);
//          editor.setSpan(startOffset, endOffset, type);
//
//        }catch(ResourceInstantiationException rie){
//          rie.printStackTrace(Err.getPrintWriter());
//        }
//      }
//
//      //add all the generic editors
//      editorIter = genericEditors.iterator();
//      while(editorIter.hasNext()){
//        String editorType = (String)editorIter.next();
//        //create the editor
//        AnnotationVisualResource editor;
//        try{
//          editor  = (AnnotationVisualResource)
//                                          Factory.createResource(editorType);
//
//          if(type == null ||
//             (type != null && editor.canDisplayAnnotationType(type))){
//            editor.setTarget(set);
//            editor.setSpan(startOffset, endOffset, type);
//            tabbedPane.add(new JScrollPane((Component)editor),
//                           ((ResourceData)Gate.getCreoleRegister().
//                                              get(editorType)).getName());
//          }
//        }catch(ResourceInstantiationException rie){
//          rie.printStackTrace(Err.getPrintWriter());
//        }
//
//      }
//
//      //show the modal dialog until the data is OK or the user cancels
//      boolean allOK = false;
//      while(!allOK){
//        if(OkCancelDialog.showDialog(DocumentEditor.this,
//                                     tabbedPane, "Edit Annotation")){
//          try{
//            ((AnnotationVisualResource)((JScrollPane)tabbedPane.
//                                        getSelectedComponent()).getViewport().
//                                                                getView()
//             ).okAction();
//             allOK = true;
//          }catch(GateException ge){
//            JOptionPane.showMessageDialog(
//              DocumentEditor.this,
//              "There was an error:\n" +
//              ge.toString(),
//              "GATE", JOptionPane.ERROR_MESSAGE);
////            ge.printStackTrace(Err.getPrintWriter());
//            allOK = false;
//          }
//        }else{
//          allOK = true;
//        }
//      }//while(!allOK)
//
//
    }//public void actionPerformed(ActionEvent e)

    AnnotationSet set;
    Long startOffset;
    Long endOffset;
    String type;
  }//class NewAnnotationAction extends AbstractAction

  /**
   * Fixes the <a
   * href="http://developer.java.sun.com/developer/bugParade/bugs/4406598.html">
   * 4406598 bug</a> in swing text components.
   * The bug consists in the fact that the Background attribute is ignored by
   * the text component whent it is defined in a style from which the current
   * style inherits.
   */
  public class CustomStyledEditorKit extends StyledEditorKit{
    private final ViewFactory defaultFactory = new CustomStyledViewFactory();
    public ViewFactory getViewFactory() {
      return defaultFactory;
    }

    /**
      * Inserts content from the given stream, which will be
      * treated as plain text.
      * This insertion is done without checking \r or \r \n sequence.
      * It takes the text from the Reader and place it into Document at position
      * pos
      */
    public void read(Reader in, javax.swing.text.Document doc, int pos)
                throws IOException, BadLocationException {

      char[] buff = new char[65536];
      int charsRead = 0;
      while ((charsRead = in.read(buff, 0, buff.length)) != -1) {
            doc.insertString(pos, new String(buff, 0, charsRead), null);
            pos += charsRead;
      }// while
    }// read
  }

  /**
   * Fixes the <a
   * href="http://developer.java.sun.com/developer/bugParade/bugs/4406598.html">
   * 4406598 bug</a> in swing text components.
   * The bug consists in the fact that the Background attribute is ignored by
   * the text component whent it is defined in a style from which the current
   * style inherits.
   */
  public class CustomStyledViewFactory implements ViewFactory{
    public View create(Element elem) {
      String kind = elem.getName();
      if (kind != null) {
        if (kind.equals(AbstractDocument.ContentElementName)) {
          return new CustomLabelView(elem);
        }else if (kind.equals(AbstractDocument.ParagraphElementName)) {
          return new ParagraphView(elem);
        }else if (kind.equals(AbstractDocument.SectionElementName)) {
          return new BoxView(elem, View.Y_AXIS);
        }else if (kind.equals(StyleConstants.ComponentElementName)) {
          return new ComponentView(elem);
        }else if (kind.equals(StyleConstants.IconElementName)) {
          return new IconView(elem);
        }
      }
      // default to text display
      return new CustomLabelView(elem);
    }
  }
  }//class AnnotationEditor