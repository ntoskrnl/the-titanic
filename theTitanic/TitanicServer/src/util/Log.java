package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Class for logging.
 * @author danon
 */
public class Log {
    private Handler h;
    
    public Log() {
        h = new ConsoleHandler();
    }
    
    public Log(Handler h){
        this.h = h;
        if(h==null) h = new ConsoleHandler();
    }
    
    public Log(URL logfile, boolean append) throws IOException, SecurityException{
        h = new FileHandler(logfile.getFile(), append);
    }
    
    public void info(String s){
        try{
            h.publish(new LogRecord(Level.INFO, s));
        } catch (Exception ex){
            System.err.println("Logger: " + ex.getMessage());
        }
    }
    
    
    public void config(String s){
        try{
            h.publish(new LogRecord(Level.CONFIG, s));
        } catch (Exception ex){
            System.err.println("Logger: " + ex.getMessage());
        }
    }
    
    public void severe(String s){
        try{
            h.publish(new LogRecord(Level.SEVERE, s));
        } catch (Exception ex){
            System.err.println("Logger: " + ex.getMessage());
        }
    }
    
    public void warning(String s){
        try{
            h.publish(new LogRecord(Level.WARNING, s));
        } catch (Exception ex){
            System.err.println("Logger: " + ex.getMessage());
        }
    }
    
    public void log(String s){
        try{
            h.publish(new LogRecord(Level.OFF, s));
        } catch (Exception ex){
            System.err.println("Logger: " + ex.getMessage());
        }
    }
    
    public Writer getDefaultWriter(){
        return new PrintWriter(System.err);
    }
}
