package util;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author danon
 */
public class ServerCommandProcessor {
    
    synchronized public void processCommand(String command, User u){
        if(command==null) return;

        Socket socket = u.getClientHandler().getSocket();
        PrintWriter pw = u.getClientHandler().getSocketWriter();
        BufferedReader br = u.getClientHandler().getSocketReader();
        
        boolean result = false;
        String cmd = command.toLowerCase().trim();

        try{
            // Connection stop
            if(cmd.equals("exit")){
                System.out.println("Client sent terminal command.");
                try{
                    socket.close();
                    return;
                } catch(Exception ex){}
            }

            // Authentication
            if(cmd.equals("authorize")){
                String login = br.readLine().trim();
                String pwd = br.readLine().trim();
                if(authorize(u, login, pwd)){
                    pw.println("SUCCESS");
                    pw.println(u.getSecret());
                    pw.println();

                    u.setAuthorized(true);
                    result = true;
                }
            }

            // User List Request (secret required)
            if(cmd.equals("list users")){
                String which = br.readLine();
                String secret = br.readLine();
                System.out.println("COMMAND: list users");
                if(u.getSecret()!=null && u.getSecret().equals(secret.trim()))
                    if(which.trim().toLowerCase().equals("online")){
                        pw.println("SUCCESS");
                        ResultSet r = Main.usersDB.doQouery("SELECT * FROM online_users;");
                        while(r.next()){
                            pw.println("#"+r.getString("user_id") + ": " + r.getString("status"));
                        }
                        pw.println();
                        result = true;
                        //r.close();
                        //Main.serviceDB.closeQuery();
                    }
            }

            if(cmd.equals("register")){
                String login = br.readLine().trim();
                String password = br.readLine().trim();
                String firstName = br.readLine().trim();
                String surName = br.readLine().trim();
                String pubNickName = br.readLine().trim();
                String pubEmail = br.readLine().trim();
                String sex = br.readLine().trim();
                String age = br.readLine().trim();
                String location = br.readLine().trim();

                result = registerUser(u, login, password, firstName, surName, pubNickName,
                        pubEmail, sex, age, location);
            }

            if(!result) pw.println("FAIL");

            // send buffered data to the client
            pw.flush();
        } catch(Exception ex) {
            System.err.println("COMMAND: "+ex.getLocalizedMessage());
        }
    }


    private boolean authorize(User u, String login, String password){
        boolean result = false;
        u.setAuthorized(false);
        u.setId(-1);
        try {
            String sql = "SELECT * FROM profiles WHERE login LIKE '" + login + "' AND password LIKE '" + password + "'";

            ResultSet r = Main.usersDB.doQouery(sql);
            if(!r.next()){
                return false;
            }
            u.setId(r.getInt("id"));
            u.setPubNickname(r.getString("pub_nickname"));

            sql = "INSERT INTO online_users " +
                            " (user_id, status)" +
                            " VALUES ("+u.getId()+", 'WAIT')";

            if(Main.usersDB.doUpdate(sql)==1) result = true;
            else result = false;
        } catch (Exception ex) {
            System.err.println("authorize: "+ex.getLocalizedMessage());
            result = false;
        }

        if(!result){
            u.setId(-1);
            u.setPubNickname("");
        }
        u.setAuthorized(result);
        return result;
    }


    private boolean registerUser(User u, String login, String password, String firstName,
                                    String surName, String pubNickName, String pubEmail, String sex,
                                    String age, String location){

        String sql = "INSERT INTO profiles (login, password, first_name, surname, " +
                "pub_nickname, pub_email, sex, age, location) VALUES('" +
                login + "','" + password + "','" + firstName + "','" + surName +
                "','" + pubNickName + "','" + pubEmail + "','" + sex + "','" + age + "', '" + location + "')";
        System.out.println(sql);
        if(Main.usersDB.doUpdate(sql)<1) return false;

        int id = -1;
        try{
            ResultSet r = Main.usersDB.doQouery("SELECT * FROM profiles WHERE lofin LIKE '"+login+"'");
            if(!r.next()) return false;
            id = r.getInt("id");
        } catch (SQLException ex){
            return false;
        }
        
        sql = "INSERT INTO rating (user_id) VALUES (" + id + ")";
        return(Main.usersDB.doUpdate(sql)>=0);
    }
}
