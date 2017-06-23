package SecondStage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyFileHandler {
   /** @param fileName */
   public static List<String> readFile(String fileName) {
       return readFile ( fileName , "CP1256" );
   }
   
   /** @param file */
   public static List<String> readFile(File file) {
       return readFile ( file , "CP1256" );
   }
   
   /** @param fileName 
    * @param code */
   public static List<String> readFile(String fileName, String code) {
       return readFile ( new File ( fileName ) , code );
   }
   
   /** @param file 
    * @param code */
   public static List<String> readFile(File file, String code) {
       FileInputStream in = null;
       List<String> tokenizedLine = new ArrayList<String> ();
       try
       {
           in = new FileInputStream ( file );
           InputStreamReader str = new InputStreamReader ( in , code );
           BufferedReader br = new BufferedReader ( str );
           // read in the text a line at a time
           String line = "";
           StringBuffer word = new StringBuffer ();
           Character character;
           while ( ( line = br.readLine () ) != null )
           {
               line = line + " ";
               // tokenize each line
               for ( int i = 0 ; i < line.length () ; i ++ )
               {
                   // if the character is not a space, append it to a word
                   if (  ! ( character = new Character ( line.charAt ( i ) ) ).isWhitespace ( line.charAt ( i ) ) )
                   {
                       word.append ( line.charAt ( i ) );
                   }
                   else
                   {
                       if ( word.length () != 0 )
                       {
                           tokenizedLine.add ( word.toString () );
                           word.setLength ( 0 );
                       }
                   }
               }
           }
           // close the FileReader
           br.close ();
           in.close ();
       }
       catch ( Exception ex )
       {
           Logger.getLogger ( MyFileHandler.class.getName () ).log ( Level.SEVERE , null , ex );
           System.out.println ( "---------------File not found-----------------" );
       }
       return tokenizedLine;
   }
   
   /** @param file */
   public static String getText(File file) {
       return getText ( file , "CP1256" );
   }
   
   /** @param fileName */
   public static String getText(String fileName) {
       return getText ( new File ( fileName ) , "CP1256" );
   }
   
   /** @param fileName 
    * @param code */
   public static String getText(String fileName, String code) {
       return getText ( new File ( fileName ) , code );
   }
   
   /** @param file 
    * @param code */
   public static String getText(File file, String code) {
       FileInputStream in = null;
       String txt = "";
       try
       {
           in = new FileInputStream ( file );
           InputStreamReader str = new InputStreamReader ( in , code );
           BufferedReader br = new BufferedReader ( str );
           // read in the text a line at a time
           String line = "";
           while ( ( line = br.readLine () ) != null )
           {
               txt += line + "\n";
           }
           // close the FileReader
           br.close ();
           in.close ();
       }
       catch ( Exception ex )
       {
           Logger.getLogger ( MyFileHandler.class.getName () ).log ( Level.SEVERE , null , ex );
           System.out.println ( "---------------File not found-----------------" );
       }
       return txt;
   }
   
   /** @param str 
    * @param fileName 
    * @param code */
   public static void printToFile(String str, String fileName, String code) {
       {
           try
           {
               FileOutputStream outputStream = null;
               OutputStreamWriter streamWriter = null;
               outputStream = new FileOutputStream ( fileName );
               streamWriter = new OutputStreamWriter ( outputStream , code );
               BufferedWriter out = new BufferedWriter ( streamWriter );
               out.write ( str );
               out.close ();
               streamWriter.close ();
               outputStream.close ();
           }
           catch ( IOException ex )
           {
               Logger.getLogger ( MyFileHandler.class.getName () ).log ( Level.SEVERE , null , ex );
           }
       }
   }
   
   /** @param str 
    * @param fileName */
   public static void printToFile(String str, String fileName) {
       printToFile ( str , fileName , "CP1256" );
   }

}