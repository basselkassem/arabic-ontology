
package FirstStage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {
    
    
    /// common arguments for db connection are defined here 
    private String DatabaseURL; // connection string identify host and port
    private String DatabaseName; // identify the database
    private String DatabaseDriver; /// provided by server
    private String UserName;
    private String Password;
    private static Connection conn;
    private ResultSet queryDB;
    /// another arguments may can become in handy 
    private String Message;
    
    
    public DBConnector(String _DatabaseURL ,String _DatabaseName,String _DatabaseDriver,String _UserName,String _Password ) {
        this.DatabaseURL = _DatabaseURL;
        this.DatabaseName=_DatabaseName;
        this.DatabaseDriver = _DatabaseDriver;
        this.UserName = _UserName;
        this.Password = _Password;
        this.conn = null;
    }
    public boolean TestConnection(){
        this.conn = null;
        try
        {
            try
            {
               Class.forName(this.DatabaseDriver);
            }
            catch(ClassNotFoundException e)
            {
                 this.Message = "driver for mysql not found !!! \r\n" + e.getMessage();
                 return false;
            }
            this.conn = DriverManager.getConnection(this.DatabaseURL+this.DatabaseName,this.UserName,this.Password);
           // this.conn.close();
            this.Message = "connection succeed ...";
            return true;    
        }
        catch (Exception  e )
        {
            this.Message = "connection failed  \r\n " + e.getMessage();
            return false;
        }
    }
    public int GetDataBaseSize(String table){
        ResultSet resSet=null;
        String resS=null;
        try {
            
            Statement st = this.conn.createStatement();// Membuat Statement
            resSet = st.executeQuery("SELECT count(`ardic_utf8`.`"+table+"`.`meaning`) AS expr"+
                                   " FROM `ardic_utf8`.`"+table+"`;");
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(resSet.next()==true) {
                resS=resSet.getString("expr").toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        int res=Integer.parseInt(resS);
        return res;
        
    }
    public ResultSet GetData(String Table){
        this.conn = null;
        ResultSet res = null;
       try
        {
            try
            {
               Class.forName(this.DatabaseDriver);
            }
            catch(ClassNotFoundException e)
            {
                 this.Message = "driver for mysql not found !!! \r\n" + e.getMessage();
                 return null;
            }
            this.conn = DriverManager.getConnection(this.DatabaseURL+this.DatabaseName,this.UserName,this.Password);
            this.Message = "data retrieved successfully  ...";
            Statement st = this.conn.createStatement();// Membuat Statement
            res = st.executeQuery("select * from "+Table+";");
           // st.close();
           // this.conn.close();
            queryDB=res;
            return res;   
        }
        catch (Exception  e )
        {
            this.Message = "connection failed  \r\n " + e.getMessage();
            return null;
        }
        
    }
    public String GetWordType(String word){
         ResultSet res=null;
         String resS=null;
        try {
            
            Statement st = this.conn.createStatement();// Membuat Statement
            res = st.executeQuery("SELECT rawword.rawWord, type.type  FROM  rawword INNER JOIN derivednoun"
                    + " ON rawword.rawWordId = derivednoun.rawNounId"
                    + " INNER JOIN type "
                    + "ON derivednoun.typeId = type.typeId WHERE  rawWord ='"+word+"';");
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        try {
            if(res.next()==true)
                resS=res.getString("type").toString();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(resS==null)
            resS="##";
        return resS;
        
    }
    public static String GetWordTypeS(String word){
         ResultSet res=null;
         String resS=null;
        try {
            
           Statement st = DBConnector.conn.createStatement();// Membuat Statement
           res = st.executeQuery("SELECT rawword.rawWord, type.type  FROM  rawword INNER JOIN derivednoun"
                    + " ON rawword.rawWordId = derivednoun.rawNounId"
                    + " INNER JOIN type "
                    + "ON derivednoun.typeId = type.typeId WHERE  rawWord ='"+word+"';");
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        try {
            if(res.next()==true) {
                resS=res.getString("type").toString();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(resS==null) {
            resS="##";
        }
        return resS;
        
    }
    public void CloseConnection(){
        try {
            queryDB.close();
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String []arg){
        
        if("تكون خَلفًا وتكون قُدَّامًا. (ضدّ).".compareTo("تكون خَلفًا وتكون قُدَّامًا. (ضدّ).")==0 ) {
            System.out.println("Hello word");
        }
    }
    
    
    public String getDatabaseURL() {
        return DatabaseURL;
    }
    public String getDatabaseName() {
        return DatabaseName;
    }
    public String getDatabaseDriver() {
        return DatabaseDriver;
    }
    public String getUserName() {
        return UserName;
    }
    public String getPassword() {
        return Password;
    }
    public Connection getConn() {
        return conn;
    }
    public ResultSet getQueryDB() {
        return queryDB;
    }
    public String getMessage() {
        return Message;
    }

    public void setDatabaseURL(String DatabaseURL) {
        this.DatabaseURL = DatabaseURL;
    }
    public void setDatabaseName(String DatabaseName) {
        this.DatabaseName = DatabaseName;
    }
    public void setDatabaseDriver(String DatabaseDriver) {
        this.DatabaseDriver = DatabaseDriver;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    public void setQueryDB(ResultSet queryDB) {
        this.queryDB = queryDB;
    }
    public void setMessage(String Message) {
        this.Message = Message;
    }
    
    
   
}

