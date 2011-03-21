package util;

import java.sql.*;

/**
 * This class provides access to databases (now SQLite)
 * @author danon
 */
public class DataBaseAccess {

    Connection connection = null;

    /**
     * Constructs new instance and connects to the db
     * @param dbfile SQLite database file to open
     */
    public DataBaseAccess(String dbfile) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile);
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
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile, user, password);
        } catch (SQLException e){
            System.err.println("DataBaseAccess: "+e.getLocalizedMessage());
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public ResultSet doQouery(String sql) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet r = s.executeQuery(sql);
        return r;
    }

    public void close(){
        try{
            connection.close();
        } catch(SQLException ex){
            
        }
    }
}
