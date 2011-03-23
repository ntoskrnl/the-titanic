package util;

import java.util.UUID;

/**
 *
 * @author danon
 */
public class User {
    private int user_id;
    private boolean authorized;
    private String secret;
    private String pub_nickname;
    private ClientHandler h;

    public User(ClientHandler handler) {
        user_id = -1;
        this.h = handler;
        secret = UUID.randomUUID().toString();
        authorized = false;
    }

    public ClientHandler getClientHandler(){
        return h;
    }
 
    public int getId() {
        return user_id;
    }

    public void setId(int id){
        user_id = id;
    }


    public boolean isAuthorized() {
        return authorized;
    }


    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }


    public String getSecret() {
        return secret;
    }


    public void setSecret(String secret) {
        this.secret = secret;
    }


    public String getPubNickname() {
        return pub_nickname;
    }

    public void setPubNickname(String name){
        pub_nickname = name;
    }
    
}
