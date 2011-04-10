<?
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

    $msg = "EXIT\n";
    socket_write($socket, $msg);
} catch (Exception $e) {
    $online = false;
}

if (isset($socket)) {
    socket_close($socket);
}

if(!$online) echo ('<span style="color:red;">FAIL</span>');
else echo ('<span style="color:green;">OK</span>');
?>