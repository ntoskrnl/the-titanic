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
public class CommandInterpreter {
    
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
                Main.logs.info("Client sent terminal command.");
                try{
                    socket.close();
                    return;
                } catch(Exception ex){
                    Main.logs.warning(ex.getMessage());
                }
                return;
            }

            // Authentication
            if(cmd.equals("authorize")){
                String login = br.readLine().trim();
                String pwd = br.readLine().trim();
                if(authorize(u, login, pwd)){
                    pw.println("SUCCESS");
                    pw.println(u.getSecret());

                    u.setAuthorized(true);
                    result = true;
                }
            }

            // User List Request (secret required)
            if(cmd.equals("list users")){
                String which = br.readLine();
                String secret = br.readLine();
                if(u.getSecret()!=null && u.getSecret().equals(secret.trim()))
                    if(which.trim().toLowerCase().equals("online")){
                        pw.println("SUCCESS");
                        ResultSet r = Main.usersDB.doQouery("SELECT * FROM online_users;");
                        while(r.next()){
                            pw.println(r.getString("user_id"));
                        }
                        result = true;
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
                if(result){
                    pw.println("SUCCESS");
                }
            }

            if(cmd.equals("profile by id")){
               String id = br.readLine().trim();
               String secret = br.readLine().trim();
               if(secret.equals(u.getSecret())){
                   String sql = "SELECT * FROM profiles WHERE id = " + id;
                   ResultSet r = Main.usersDB.doQouery(sql);
                   if(r.next()){
                       pw.println("SUCCESS");
                       pw.println(10);
                       for(int i=1;i<=10;i++){
                           String s = r.getString(i);
                           if(s==null) s = "";
                           pw.println("\\"+s);
                       }
                       result = true;
                   }
               }
            }

            if(cmd.equals("registered")){
                String login = br.readLine().trim();
                ResultSet r = Main.usersDB.doQouery("SELECT id FROM profiles WHERE login like '"+login+"'");
                if(r.next()){
                    if(!r.next()){
                        pw.println("SUCCESS");
                        result = true;
                    }
                }
            }
            
            if(cmd.equals("check password")){
                String login = br.readLine().trim();
                String password = br.readLine().trim();
                String sql = "SELECT * FROM profiles WHERE login LIKE '" + login + "' AND password LIKE '" + password + "'";

                ResultSet r = Main.usersDB.doQouery(sql);
                if(r.next()){
                    if(!r.next()){
                        pw.println("SUCCESS");
                        result = true;
                    }
                }
            }
            
            if(cmd.equals("server online")){
                if(Main.usersDB.isConnected()){
                    result = true;
                    pw.println("SUCCESS");
                }
            }
            
            if(cmd.equals("top players")){
                int N = Integer.parseInt(br.readLine());
                ResultSet res = Main.usersDB.doQouery("SELECT * FROM rating ORDER BY score");
                if(res != null){ 
                    int i = 0;
                    pw.println("SUCCESS");
                    while(res.next() && i<N){
                        pw.println(res.getInt("user_id"));
                        i++;
                    }
                    result = true;
                }
            }
            
            if(cmd.equals("nickname by id")){
                int id = Integer.parseInt(br.readLine().trim());
                ResultSet res = Main.usersDB.doPreparedQuery("SELECT pub_nickname FROM profiles WHERE (id = ?)", id+"");
                if(res != null && res.next()){ 
                    String nick = res.getString("pub_nickname");
                    if(nick==null || nick.trim().equals("")) 
                        pw.println("<incognito>");
                    else pw.println(nick);
                    result = true;
                }
            }

            if(!result) pw.println("FAIL");
            else pw.println();

            // send buffered data to the client
            pw.flush();
        } catch(Exception ex) {
            Main.logs.warning("COMMAND: "+ex.getLocalizedMessage());
            pw.println("FAIL");
        }
    }


    private boolean authorize(User u, String login, String password){
        boolean result = false;
        u.setAuthorized(false);
        u.setId(-1);
        try {
            String sql = "SELECT * FROM profiles WHERE login LIKE '" + login 
                    + "' AND password LIKE '" + password + "'";

            ResultSet r = Main.usersDB.doQouery(sql);
            if(!r.next()){
                return false;
            }
            
            u.setId(r.getInt("id"));
            u.setPubNickname(r.getString("pub_nickname"));

            sql = "INSERT INTO online_users " +
                            " (user_id, status)" +
                            " VALUES ("+u.getId()+", 'WAIT')";
            
            if(r.next())
                throw new Exception("Trying to hack?");
            
            if(Main.usersDB.doUpdate(sql)==1) result = true;
            else result = false;
        } catch (Exception ex) {
            Main.logs.warning("authorize: "+ex.getLocalizedMessage());
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
                "pub_nickname, pub_email, sex, age, location) VALUES(?,?,?,?,?,?,?,?,?);";
        int numRows = Main.usersDB.doPreparedUpdate(sql, login, password, firstName, surName,
                    pubNickName, pubEmail, sex, age, location);
        if(numRows!=1) return false;
        
        int id = -1;
        try{
            ResultSet r = Main.usersDB.doQouery("SELECT * FROM profiles WHERE login LIKE '"+login+"'");
            if(!r.next()) return false;
            id = r.getInt("id");
        } catch (SQLException ex){
            Main.logs.warning(ex.getMessage());
            return false;
        }
        
        sql = "INSERT INTO rating (user_id) VALUES (" + id + ")";
        Main.logs.info(sql);
        boolean r = (Main.usersDB.doUpdate(sql)>=0);
        if(!r){
            Main.usersDB.doUpdate("DELETE FROM profiles WHERE id = '"+id+"'");
        }
        return r;
    }
}
