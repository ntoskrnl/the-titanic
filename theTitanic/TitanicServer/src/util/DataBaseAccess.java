package util;

import java.io.PrintWriter;
import java.sql.*;

/**
 * This class provides access to databases (now SQLite)
 * @author danon
 */
public class DataBaseAccess {

    private Connection connection = null;
    private String password = "";
    private String user = "";
    private boolean anonymous = true;
    private String dbfile;
    private boolean modified = false;

    /**
     * Constructs new instance and connects to the db
     * @param dbfile SQLite database file to open
     */
    public DataBaseAccess(String dbfile) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        anonymous = true;
        this.dbfile = dbfile;
        try{
            DriverManager.setLogWriter(new PrintWriter(Main.logs.getDefaultWriter()));
            
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
            connection.setAutoCommit(true);
        } catch (SQLException e){
            Main.logs.warning("DataBaseAccess: "+e.getLocalizedMessage());
        }
    }

    /**
     * Constructs new instance and connects to the db
     * @param dbfile SQLite database file to open
     * @param user User name
     * @param password Password to the database
     */
    public DataBaseAccess(String dbfile, String user, String password) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        anonymous = false;
        this.password = password;
        this.user = user;
        this.dbfile = dbfile;    
        try{
            DriverManager.setLogWriter(new PrintWriter(Main.logs.getDefaultWriter()));
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile, user, password);
            connection.setAutoCommit(true);
        } catch (SQLException e){
            Main.logs.warning("DataBaseAccess: "+e.getLocalizedMessage());
        }
    }

    synchronized public boolean isConnected(){
        try{
            if(connection==null) return false;
            return !connection.isClosed();
        } catch (Exception ex) {
            return false;
        }
    }
    
    synchronized public boolean reconnect(){
        try{
            try{
                connection.close();
            } catch(Exception ex) {}
            if(!anonymous) 
                connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile, user, password);
            else connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
            connection.setAutoCommit(true);
        } catch (SQLException e){
            Main.logs.warning("DataBaseAccess: "+e.getLocalizedMessage());
        }
        modified = false;
        return isConnected();
    }
    
    synchronized public Connection getConnection(){
        return connection;
    }

    synchronized public ResultSet doQouery(String sql) throws SQLException{
        Statement s = connection.createStatement();
        s.setEscapeProcessing(true);
        ResultSet r = s.executeQuery(sql);
        return r;
    }


    synchronized public int doUpdate(String sql){
        try{
            if(!isConnected()) reconnect();
            Statement s = connection.createStatement();
            s.setEscapeProcessing(true);
            int r = s.executeUpdate(sql);
            if(r>0) modified = true;
            return r;
        } catch (Exception ex) {
            Main.logs.warning("DB.doUpdate: "+ex.getMessage());
            return -1;
        }
    }
    
    synchronized public int doPreparedUpdate(String query, String... values){
        try{
            if(!isConnected()) reconnect();
            PreparedStatement s = connection.prepareStatement(query);
            for(int i=0;i<values.length;i++)
                s.setString(i+1, values[i]);
            int r = s.executeUpdate();
            if(r>0) modified = true;
            return r;
        } catch (Exception ex) {
            Main.logs.warning("DB.doPreparedUpdate: "+ex.getMessage());
            return -1;
        } 
    }
    
    synchronized public ResultSet doPreparedQuery(String query, String... values) throws SQLException{
        if(!isConnected()) reconnect();
        PreparedStatement s = connection.prepareStatement(query);
        for(int i=0;i<values.length;i++)
            s.setString(i+1, values[i]);
        return s.executeQuery();
    }

    synchronized public void close(){
        try{
            connection.close();
        } catch(SQLException ex){
            Main.logs.warning(ex.getMessage());
        }
    }

    public synchronized boolean modified() {
        return modified;
    }
}
