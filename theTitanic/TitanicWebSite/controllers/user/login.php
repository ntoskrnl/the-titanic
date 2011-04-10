<?php

class Controller_Login extends Controller_Base {
    
    function index($args) {
        global $lang;
        if($_SESSION['logged_in']){
            $this->redirect("index", 0, "Вход уже выполнен.");
            return;
        }
        if($this->check_login()){
            $this->do_login();
            $redir = $_POST['redirect'];
            $this->message("Добро пожаловать на сайт, ".$_POST['login'].
                    ".\nЖелаем приятного времяпрепровождения. :)", "Вход выполнен",
                    $_POST['redirect']);
        }
    }
    
    function out($args) {
        global $lang; 
        unset($_SESSION['login']);
        unset($_SESSION['logged_in']);
        $this->message("Выход произведен успешно. Спасибо, что посетили наш сайт.", 
                "Выход из системы", "?", 3);
    }

    private function check_login($silent=false){
        $login = $_POST['login'];
        $password = $_POST['password'];
        $m = preg_match("/[A-Za-z0-9@._-]{5,30}$/", $login, $mathces);
        if($m===FALSE || $m == 0){
            if(!$silent) $this->message("Обнаружены недопустимые символы в поле Логин. Допустимые символы:
                'A' ... 'Z', 'a' ... 'z', '0' ... '9', '@', '.', '-', '_'. <br />
                Логин должен содержать от 5 до 30 символов.", 
                    "Ошибка ввода данных", $_POST['redirect']);
            return false;
        }
        
        $m = preg_match("/.{0,30}$/", $password, $mathces);
        if($m===FALSE || $m == 0){
            if(!$silent) $this->message("Обнаружены недопустимые символы в поле Пароль. <br />
                Пароль должен содержать от 4 до 30 символов.", 
                    "Ошибка ввода данных", $_POST['redirect']);
            return false;
        }
        
        $password = md5($password);
        
        
        $address = gethostbyname("localhost");
        $port    = 10000;
        $logged_in = false;
        
        try {

            ob_flush();
            ob_implicit_flush();
            set_time_limit(10);
            
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

            $msg = "CHECK PASSWORD\n";
            socket_write($socket, $msg);
            $msg = $login."\n";
            socket_write($socket, $msg);
            $msg = $password."\n";
            socket_write($socket, $msg);
            
            $ans = socket_read($socket, 1024);
            if($ans===FALSE){
                throw new Exception("socket_read failed.");
            }
            
            $ans = trim($ans);
            if(strcasecmp($ans, "SUCCESS")!=0) {
                throw new Exception("Пользователь не зарегистрирован либо введен неверный пароль.");
            }
            
            $logged_in = true;
            
            $msg = "EXIT\n";
            socket_write($socket, $msg);
            
        } catch (Exception $e) {
            $msg = "<b>Ошибка</b>: " . $e->getMessage();
            $logged_in = false;
            if(!$silent) $this->message($msg, "Ошибка входа", $_POST['redirect']);
        }

        if (isset($socket)) {
            socket_close($socket);
        }
        
        return $logged_in;
    }
    
    private function do_login(){
        $_SESSION['login'] = $_POST['login'];
        $_SESSION['logged_in'] = true;
    }
    
    public function ajax($args){
        global $lang;
        if($this->check_login(true)){
            $this->do_login();
            echo "<p>Вход выполнен.</p>";
        } else{
            echo "<p>Ошибка входа в систему.</p>";
        }
    }

}

?>
