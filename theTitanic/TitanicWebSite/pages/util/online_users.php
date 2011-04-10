<?php
error_reporting(E_ALL & (~E_WARNING) & (~E_NOTICE) & (~E_DEPRECATED));

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

    $msg = "SECRET\n";
    socket_write($socket, $msg);

    $ans = socket_read($socket, 1024);
    if ($ans === FALSE) {
        throw new Exception("socket_read failed.");
    }

    $res = explode("\n", $ans);
    if (strcasecmp(trim($res[0]), "success") != 0)
        throw new Exception("FAIL");

    $secret = trim($res[1]);

    $msg = "LIST USERS\n";
    socket_write($socket, $msg);

    $msg = "online\n";
    socket_write($socket, $msg);

    $msg = "$secret\n";
    socket_write($socket, $msg);

    $ans = socket_read($socket, 1024);
    if ($ans === FALSE) {
        throw new Exception("socket_read failed.");
    }

    $res = explode("\n", $ans);

    $max = 5;
    $cur = 0;
    echo "<ol id=\"online_users_list\">";
    for ($i = 1;$i<count($res); $i++) {
        if (strcmp(trim($res[$i]), "") == 0)
            break;
        $msg = "nickname by id\n";
        socket_write($socket, $msg);
        $msg = trim($res[$i]) . "\n";
        socket_write($socket, $msg);

        $ans = socket_read($socket, 1024);
        if ($ans === FALSE) {
            throw new Exception("socket_read failed.");
        }

        $res1 = explode("\n", $ans);
        if (strcasecmp(trim($res1[0]), "success") != 0) {
            continue;
        }

        $id = trim($res[$i]);
        echo("<li><a href=\"user/profile?id=$id\">" . trim($res1[1]) . "</a></li>");

        $cur++;
        if ($cur >= $max)
            break;
    }
    echo "</ol>";
    if ($cur == 0)
        echo "<p>No players online.</p>";
    $msg = "EXIT\n";
    socket_write($socket, $msg);
} catch (Exception $e) {
    $online = false;
    echo "<p>Connection problem.</p>";
}

if (isset($socket)) {
    socket_close($socket);
}
?>
