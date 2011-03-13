package client.util;

/**
 * This class is used for communication with Server
 * @author danon
 */
public class TitanicServer {
    public String status = "none";

    /**
     * Authentication method.
     * @return TRUE if authentication performed, and FALSE otherwise.
     */
    public boolean authorize(){
        status = "Authentication";
        try{
            Thread.sleep(1000);
        } catch (Exception ex){
            
        }
        return true;
    }

}
