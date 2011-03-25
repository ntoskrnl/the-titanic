<?php

class Controller_Register extends Controller_Base {
    

    function index($args) {
        global $lang;
        $this->registry['smarty']->assign("content_file", "pages/ru/user/registration_form.html");
        $this->registry['smarty']->display("$lang/form.tpl");
    }

    function post($args){
        global $lang;
        $reg = $this->register_user();
        if($reg===true){
            $this->message("Поздравляем! Новый пользователь '".$_POST['login']."' зарегистрирован в системе.", "Успешная регистрация", "index", 10);
        }
    }

    private function register_user(){
        $login = $_POST['login'];
        $password = $_POST['password'];
        $retype = $_POST['password_retype'];
        $first_name = $_POST['first_name'];
        $surname = $_POST['surname'];
        $pub_nickname = $_POST['pub_nickname'];
        
        if(strcmp($password, $retype)!=0){
            $this->message("Пароли не совпадают. Проверьте правильность заполнения полей.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        if(empty ($password)){
            $this->message("Пароль не должен быть пустым.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        if(empty($first_name)||empty ($surname) || empty ($pub_nickname)){
            $this->message("Необходимо заполнить все поля формы регистрации.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        $address = gethostbyname("localhost");
        $port    = 10000;
        $registered = false;
        
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

            $msg = "REGISTERED\n";
            socket_write($socket, $msg);
            $msg = $login."\n";
            socket_write($socket, $msg);
            
            $ans = socket_read($socket, 1024);
            if($ans===FALSE){
                throw new Exception("socket_read failed.");
            }
            
            $ans = trim($ans);
            if(strcasecmp($ans, "SUCCESS")==0) {
                throw new Exception("Пользователь с таким именем уже зарегистрирован. <br />Попробуйте другое имя.");
            }
            
            $msg = "REGISTER\n";
            socket_write($socket, $msg);
            $msg = $login."\n";
            socket_write($socket, $msg);
            $msg = md5($password)."\n";
            socket_write($socket, $msg);
            $msg = $first_name."\n";
            socket_write($socket, $msg);
            $msg = $surname."\n";
            socket_write($socket, $msg);
            $msg = $pub_nickname."\n";
            socket_write($socket, $msg);
            $msg = "\n";
            socket_write($socket, $msg);
            $msg = "\n";
            socket_write($socket, $msg);
            $msg = "\n";
            socket_write($socket, $msg);
            $msg = "\n";
            socket_write($socket, $msg);
            
            $ans = socket_read($socket, 1024);
            if($ans==FALSE){
                throw new Exception('Регистрационные данные били отправлены на сервер, 
                    но не был получен ответ. <br />Приносим извинения за неудобства.');
            }
            
            $ans = trim($ans);
            if(strcasecmp($ans, "SUCCESS")!=0){
                throw new Exception('Не удалось зарегистрировать пользователя. 
                    <br />Проверьте правильность ввода данных и повторите попытку регистрации.');
            }
            
            $registered = true;
            
            $msg = "EXIT\n";
            socket_write($socket, $msg);
            
        } catch (Exception $e) {
            $msg = "<b>Ошибка</b>: " . $e->getMessage();
            $registered = false;
            $this->message($msg, "Ошибка регистрации", "user/register", 5);
        }

        if (isset($socket)) {
            socket_close($socket);
        }
        
        return $registered;
    }
    
    private function message($text, $title, $redirect, $timeout){
        global $lang;
        
        $this->registry['smarty']->assign("msg_text", $text);
        $this->registry['smarty']->assign("msg_title", $title);
        $this->registry['smarty']->assign("redirect", $redirect);
        $this->registry['smarty']->assign("redirect_timeout", $timeout);
        $this->registry['smarty']->display("$lang/message.tpl");
    }
}

?>
