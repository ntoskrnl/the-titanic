package util;

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

    /**
     * Constructs new instance and connects to the db
     * @param dbfile SQLite database file to open
     */
    public DataBaseAccess(String dbfile) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        anonymous = true;
        this.dbfile = dbfile;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
            connection.setAutoCommit(true);
        } catch (SQLException e){
            System.err.println("DataBaseAccess: "+e.getLocalizedMessage());
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
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile, user, password);
            connection.setAutoCommit(true);
        } catch (SQLException e){
            System.err.println("DataBaseAccess: "+e.getLocalizedMessage());
        }
    }

    public boolean isConnected(){
        try{
            if(connection==null) return false;
            return !connection.isClosed();
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean reconnect(){
        try{
            if(!anonymous) 
                connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile, user, password);
            else connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
            connection.setAutoCommit(true);
        } catch (SQLException e){
            System.err.println("DataBaseAccess: "+e.getLocalizedMessage());
        }
        return isConnected();
    }
    
    public Connection getConnection(){
        return connection;
    }

    public ResultSet doQouery(String sql) throws SQLException{
        Statement s = connection.createStatement();
        s.setEscapeProcessing(true);
        ResultSet r = s.executeQuery(sql);
        return r;
    }


    public int doUpdate(String sql){
        try{
            if(!isConnected()) reconnect();
            Statement s = connection.createStatement();
            s.setEscapeProcessing(true);
            int r = s.executeUpdate(sql);
            return r;
        } catch (Exception ex) {
            System.err.println("DB.doUpdate"+ex.getMessage());
            return -1;
        }
    }
    
    public int doPreparedUpdate(String query, String... values){
        try{
            if(!isConnected()) reconnect();
            PreparedStatement s = connection.prepareStatement(query);
            for(int i=0;i<values.length;i++)
                s.setString(i+1, values[i]);
            return s.executeUpdate();
        } catch (Exception ex) {
            System.err.println("DB.doPreparedUpdate"+ex.getMessage());
            return -1;
        } 
    }
    
    public ResultSet doPreparedQuery(String query, String... values) throws SQLException{
        if(!isConnected()) reconnect();
        PreparedStatement s = connection.prepareStatement(query);
        for(int i=0;i<values.length;i++)
            s.setString(i+1, values[i]);
        return s.executeQuery();
    }

    public void close(){
        try{
            connection.close();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
}
