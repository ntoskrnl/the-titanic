# Introduction #

Our server interacts with client applications via Java Sockets. All the information transmitted as a plain text. We use simple commands to control the server and to get or change the status.

# Details #

## Command Syntax ##
Commands have two parts: **command name** and **command arguments**.
Each command name should take a separate line and may consist of one or several words. The register of the characters is not important.
After the command name there can be one or more arguments - just strings.

**Example 1**:

```

1    authorize
2    login
3    password
```

This command is used for authentication. As we can see we send login and password to the server as strings in separate lines. Line 1 contains a command name, lines 2, 3 - arguments. So, the command **authorize** requires 2 arguments (login and password).

**Example 2**:

```

1    list users
2    online
3    secret code
```

Request for online users.

## Server response ##

If the command is sent to the server (do not forget FLUSH()), we have to get the result.

The result may contain one or several lines. The first line always contain the server status: FAIL - the command is refused, SUCCESS - it is all OK.

If it is all OK (SUCCESS), there may be several lines containing required information (e.g. list of users). The list is always ends with one empty line.

# Supported Commands #

Current version of the server supports the following commands:
  * [SERVER ONLINE](commandServerOnline.md)
  * [AUTHOSIZE](commandAuthorize.md)
  * [LIST USERS](commandListUsers.md)
  * [PROFILE BY ID](commandProfileById.md)
  * [NICKNAME BY ID](commanNicknameById.md)
  * [REGISTER](commandRegister.md)
  * [REGISTERED](commandRegistered.md)
  * [CHECK PASSWORD](commandCheckPassword.md)
  * [SECRET](commandSecret.md)
  * [EXIT](commandExit.md)
This list will be extended soon.

# How to connect and send commands #

Server listens port 10000 (this will be changed in future releases!) on the host machine, so first of all you need to create a socket connection between client ans server machines. This, of cause, depends on programming language and platform.

## PHP ##

```

<?php

$address = gethostbyname("localhost");
$port = 10000;
$online = false;

try {
set_time_limit(5);

// create socket
$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
if ($socket < 0) {
throw new Exception('socket_create() failed: ' . socket_strerror(socket_last_error()) . "\n");
}

//connect socket
$result = socket_connect($socket, $address, $port);
if ($result === false) {
throw new Exception('socket_connect() failed: ' . socket_strerror(socket_last_error()) . "\n");
}

$msg = "SERVER ONLINE\n";
socket_write($socket, $msg);

$ans = socket_read($socket, 1024);
if ($ans === FALSE) {
throw new Exception("socket_read failed.");
}

$ans = trim($ans);
if (strcasecmp($ans, "SUCCESS") != 0) {
throw new Exception("Сервер не активен. <br />Попробуйте позже.");
}

$online = true;

$msg = "EXIT\n";
socket_write($socket, $msg);
} catch (Exception $e) {
$online = false;
}

if (isset($socket)) {
socket_close($socket);
}

?>
```

## Java ##

In Java you should use standard java.net.Socket class to connect to the server. Do not forget to flush after sending a command.

```

import java.net.*;
import java.io.*;

//          ...
// ... Some code here ...
//          ...

Socket socket;
BufferedReader br;
PrintWriter pw;
boolean online = false;

try{
socket = new Socket(host, port);
socket.setSoTimeout(3000);
br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
pw = new PrintWriter(socket.getOutputStream());

String command = "SERVER ONLINE"; // your command
String cmd = command.trim().toUpperCase();
pw.println(cmd);

//Send arguments:
//for(String arg : args){
//    if(arg!=null) pw.println(arg.trim());
//    else pw.println();
//}

pw.flush();

//Get response
String res = br.readLine();

if(res!=null && res.trim().toLowerCase().equals("success")){
online = true;
do {
res = br.readline();
}while(res!=null || !res.trim().equals(""));
}

pw.println("EXIT");
pw.flush();

} catch (Exception ex){
System.err.println("Server: "+ex.getLocalizedMessage());
online = false;
}

if(online) System.out.println("Server: OK");
else System.out.println("Server: FAIL");

try {
if(socket!=null) socket.close();
} catch (Exception ex) {}

```



&lt;hr&gt;


Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages