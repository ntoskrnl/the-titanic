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

    synchronized public void processCommand(String command, User u) {
        if (command == null) {
            return;
        }

        Socket socket = u.getClientHandler().getSocket();
        PrintWriter pw = u.getClientHandler().getSocketWriter();
        BufferedReader br = u.getClientHandler().getSocketReader();

        Boolean result = null;
        String cmd = command.toLowerCase().trim();

        synchronized (Main.usersDB) {
            try {
                // Connection stop
                if (cmd.equals("exit")) {
                    Main.logs.info("Client sent terminal command.");
                    try {
                        socket.close();
                        return;
                    } catch (Exception ex) {
                        Main.logs.warning(ex.getMessage());
                    }
                    return;
                }

                // Authentication
                if (cmd.equals("authorize")) {
                    String login = br.readLine().trim();
                    String pwd = br.readLine().trim();
                    if (authorize(u, login, pwd)) {
                        pw.println("SUCCESS");
                        pw.println(u.getSecret());

                        u.setAuthorized(true);
                        result = true;
                    } else result = false;
                }

                // User List Request (secret required)
                if (cmd.equals("list users")) {
                    String which = br.readLine();
                    String secret = br.readLine();
                    if (u.getSecret() != null && u.getSecret().equals(secret.trim())) {
                        if (which.trim().toLowerCase().equals("online")) {
                            pw.println("SUCCESS");
                            ResultSet r = Main.usersDB.doQouery("SELECT * FROM online_users;");
                            while (r.next()) {
                                pw.println(r.getString("user_id"));
                            }
                            result = true;
                        }
                    } else result = false;
                }
                
                // Returns secret key on demand
                if(cmd.equals("secret")){
                    pw.println("SUCCESS");
                    pw.println(u.getSecret());
                    result = true;
                }

                if (cmd.equals("register")) {
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
                    if (result) {
                        pw.println("SUCCESS");
                    }
                }

                if (cmd.equals("profile by id")) {
                    String id = br.readLine().trim();
                    String secret = br.readLine().trim();
                    result = false;
                    if (secret.equals(u.getSecret())) {
                        if(id.equals("0")) id=u.getId()+"";
                        String sql = "SELECT * FROM profiles WHERE id = " + id;
                        ResultSet r = Main.usersDB.doQouery(sql);
                        if (r.next()) {
                            pw.println("SUCCESS");
                            String s = r.getString("id");
                            if(s==null) s = "";
                            pw.println("id:"+s);
                            
                            s = r.getString("login");
                            if(s==null) s = "";
                            pw.println("login:"+s);
                            
                            s = r.getString("first_name");
                            if(s==null) s = "";
                            pw.println("first_name:"+s);
                            
                            s = r.getString("surname");
                            if(s==null) s = "";
                            pw.println("surname:"+s);
                            
                            s = r.getString("pub_nickname");
                            if(s==null) s = "";
                            pw.println("pub_nickname:"+s);
                            
                            s = r.getString("pub_email");
                            if(s==null) s = "";
                            pw.println("pub_email:"+s);
                            
                            s = r.getString("sex");
                            if(s==null) s = "";
                            pw.println("sex:"+s);
                            
                            s = r.getString("age");
                            if(s==null) s = "";
                            pw.println("age:"+s);
                            
                            s = r.getString("location");
                            if(s==null) s = "";
                            pw.println("location:"+s);
                            
                            result = true;
                        }
                    }
                }

                if (cmd.equals("registered")) {
                    String login = br.readLine().trim();
                    ResultSet r = Main.usersDB.doQouery("SELECT id FROM profiles WHERE login like '" + login + "'");
                    if (r.next()) {
                        if (!r.next()) {
                            pw.println("SUCCESS");
                            result = true;
                        }
                    }
                }

                if (cmd.equals("check password")) {
                    String login = br.readLine().trim();
                    String password = br.readLine().trim();
                    String sql = "SELECT * FROM profiles WHERE login LIKE '" + login + "' AND password LIKE '" + password + "'";

                    ResultSet r = Main.usersDB.doQouery(sql);
                    if (r.next()) {
                        if (!r.next()) {
                            pw.println("SUCCESS");
                            result = true;
                        }
                    }
                }

                if (cmd.equals("server online")) {
                    if (Main.usersDB.isConnected()) {
                        result = true;
                        pw.println("SUCCESS");
                    }
                }

                if (cmd.equals("top players")) {
                    int N = Integer.parseInt(br.readLine());
                    ResultSet res = Main.usersDB.doQouery("SELECT * FROM rating ORDER BY score");
                    if (res != null) {
                        int i = 0;
                        pw.println("SUCCESS");
                        while (res.next() && i < N) {
                            pw.println(res.getInt("user_id"));
                            i++;
                        }
                        result = true;
                    }
                }

                if (cmd.equals("nickname by id")) {
                    int id = Integer.parseInt(br.readLine().trim());
                    ResultSet res = Main.usersDB.doPreparedQuery("SELECT pub_nickname FROM profiles WHERE (id = ?)", id + "");
                    if (res != null && res.next()) {
                        pw.println("SUCCESS");
                        String nick = res.getString("pub_nickname");
                        if (nick == null || nick.trim().equals("")) {
                            pw.println("<incognito>");
                        } else {
                            pw.println(nick);
                        }
                        result = true;
                    }
                }
                
                if(cmd.equals("request game")){
                    System.out.println("REQUEST!");
                    int to_id = Integer.parseInt(br.readLine().trim());
                    String secret = br.readLine().trim();
                    result = false;
                    if(secret.equals(u.getSecret())){
                        if(result = MainServerThread.requests.add(new GameRequest(u.getId(), to_id))){
                            pw.println("SUCCESS");
                            result = true;
                        }
                    }
                }
                
                if(cmd.equals("accept game")){
                    System.out.println("ACCEPT!");
                    int from = Integer.parseInt(br.readLine().trim());
                    int my_id = u.getId();
                    String secret = br.readLine().trim();
                    result = false;
                    if(secret.equals(u.getSecret())){
                        GameRequest req = MainServerThread.requests.get(from, my_id);
                        if(req!=null) {
                            req.setAccepted(true);
                            pw.println("SUCCESS");
                            result = true;
                        }
                    }
                }
                
                if(cmd.equals("is game accepted")){ 
                    int to_id = Integer.parseInt(br.readLine().trim());
                    int my_id = u.getId();
                    String secret = br.readLine().trim();
                    result = false;
                    if(secret.equals(u.getSecret())){
                        GameRequest req = MainServerThread.requests.get(my_id, to_id);
                        if(req!=null){
                            pw.println("SUCCESS");
                            Boolean ac = req.isAccepted();
                            if(ac==null) pw.println("NULL");
                            else {
                                MainServerThread.requests.remove(req);
                                if(ac==true) pw.println("YES");
                                    else pw.println("NO");
                            }                  
                            result = true;
                        }
                    }
                }
                
                if(cmd.equals("get game request")){
                    int my_id = u.getId();
                    String secret = br.readLine().trim();
                    result=false;
                    if(secret.equals(u.getSecret())){
                        GameRequest req = MainServerThread.requests.getActiveFor(my_id);
                        if(req!=null){
                            req.deactivate();
                            pw.println("SUCCESS");
                            pw.println(req.getFrom());
                            result = true;
                        }
                    }
                }
                
                if(cmd.equals("reject game")){
                    System.out.println("REJECT!");
                    int my_id = u.getId();
                    int from = Integer.parseInt(br.readLine().trim());
                    String secret = br.readLine().trim();
                    result=false;
                    if(secret.equals(u.getSecret())){
                        GameRequest req = MainServerThread.requests.get(from, my_id);
                        if(req!=null){
                            MainServerThread.requests.remove(req);
                            pw.println("SUCCESS");
                            result = true;
                        }
                    }
                }
                
                if(cmd.equals("cancel request")){
                    System.out.println("CANCEL!");
                    int my_id = u.getId();
                    int to = Integer.parseInt(br.readLine().trim());
                    String secret = br.readLine().trim();
                    result=false;
                    if(secret.equals(u.getSecret())){
                        GameRequest req = MainServerThread.requests.get(my_id, to);
                        if(req!=null){
                            MainServerThread.requests.remove(req);
                            pw.println("SUCCESS");
                            result = true;
                        }
                    }
                }
                if(cmd.equals("is request valid")){
                    System.out.println("VALID?");
                    int from = Integer.parseInt(br.readLine().trim());
                    int to = Integer.parseInt(br.readLine().trim());
                    String secret = br.readLine().trim();
                    result=false;
                    if(secret.equals(u.getSecret())){
                        GameRequest req = MainServerThread.requests.get(from, to);
                        if(req!=null){
                            pw.println("SUCCESS");
                            result = true;
                        }
                    }
                }
                
                if(result!=null){
                    if (!result) {
                        pw.println("FAIL");
                    } else {
                        pw.println();
                    }
                } else Main.logs.warning("COMMAND: unknown command "+command);

                // send buffered data to the client
                pw.flush();
            } catch (Exception ex) {
                Main.logs.warning("COMMAND: " + ex.getLocalizedMessage()+" on "+command);
                pw.println("FAIL");
            }
        }
    }

    

    private boolean authorize(User u, String login, String password) {
        boolean result = false;
        u.setAuthorized(false);
        u.setId(-1);
        try {
            String sql = "SELECT * FROM profiles WHERE login LIKE '" + login
                    + "' AND password LIKE '" + password + "'";

            ResultSet r = Main.usersDB.doQouery(sql);
            if (!r.next()) {
                return false;
            }

            u.setId(r.getInt("id"));
            u.setPubNickname(r.getString("pub_nickname"));

            sql = "INSERT INTO online_users "
                    + " (user_id, status)"
                    + " VALUES (" + u.getId() + ", 'WAIT')";

            if (r.next()) {
                throw new Exception("Trying to hack?");
            }

            if (Main.usersDB.doUpdate(sql) == 1) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Main.logs.warning("authorize: " + ex.getLocalizedMessage());
            result = false;
        }

        if (!result) {
            u.setId(-1);
            u.setPubNickname("");
        }
        u.setAuthorized(result);
        return result;
    }

    private boolean registerUser(User u, String login, String password, String firstName,
            String surName, String pubNickName, String pubEmail, String sex,
            String age, String location) {

        String sql = "INSERT INTO profiles (login, password, first_name, surname, "
                + "pub_nickname, pub_email, sex, age, location) VALUES(?,?,?,?,?,?,?,?,?);";
        int numRows = Main.usersDB.doPreparedUpdate(sql, login, password, firstName, surName,
                pubNickName, pubEmail, sex, age, location);
        if (numRows != 1) {
            return false;
        }

        int id = -1;
        try {
            ResultSet r = Main.usersDB.doQouery("SELECT * FROM profiles WHERE login LIKE '" + login + "'");
            if (!r.next()) {
                return false;
            }
            id = r.getInt("id");
        } catch (SQLException ex) {
            Main.logs.warning(ex.getMessage());
            return false;
        }

        sql = "INSERT INTO rating (user_id) VALUES (" + id + ")";
        Main.logs.info(sql);
        boolean r = (Main.usersDB.doUpdate(sql) >= 0);
        if (!r) {
            Main.usersDB.doUpdate("DELETE FROM profiles WHERE id = '" + id + "'");
        }
        return r;
    }
}
