package client.util;

import client.Main;
import java.util.HashMap;

/**
 * This class provides all information about the user
 * @author danon
 */
public class UserProfile implements Comparable<UserProfile> {

    private HashMap<String, String> data;
    private String comp = "id";

    public UserProfile(int id) {
        comp = "id";
        data = new HashMap<String, String>();
        data.put("id", "" + id);
    }

    public UserProfile(int id, String comp) {
        this.comp = comp;
        data = new HashMap<String, String>();
        data.put("id", "" + id);

    }

    public int getId() {
        return Integer.parseInt(data.get("id"));

    }

    public String setProperty(String prop, String val){
        return data.put(prop, val);
    }
    public String getProperty(String prop) {
        return data.get(prop);
    }

    public int compareTo(UserProfile o) {
        if(comp.equals("id")){
            Integer myId = new Integer(getId());
            Integer oId = new Integer(o.getId());
            return myId.compareTo(oId);
        } else {
            String mine = getProperty(comp);
            String others = getProperty(comp);
            if(mine==null) return -1;
            return mine.compareToIgnoreCase(others);
        }
    }
    
    @Override
    public String toString(){
        return data.get("pub_nickname");
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof UserProfile)
            return this.compareTo((UserProfile)o)==0;
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.data != null ? this.data.hashCode() : 0);
        hash = 41 * hash + (this.comp != null ? this.comp.hashCode() : 0);
        return hash;
    }
    
    public void update(){
        synchronized(Main.server){
            Main.server.command("profile by id", getId()+"", Main.server.secret);
            String r[] = Main.server.getResponse();
            if(r[0]==null || !r[0].equals("SUCCESS")) return;
            data.clear();
            for(int j=1;j<r.length;j++){
                setProperty(r[j].substring(0, r[j].indexOf(':')).trim(), 
                r[j].substring(r[j].indexOf(':')+1, r[j].length()).trim());
            }
        }
    }
}
