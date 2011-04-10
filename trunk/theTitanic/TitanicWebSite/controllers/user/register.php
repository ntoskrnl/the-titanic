<?php

class Controller_Register extends Controller_Base {
    

    function index($args) {
        global $lang;
        $this->registry['smarty']->assign("content_file", "pages/$lang/user/registration_form.html");
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
        
        if(!isset($_POST['age'])||empty($_POST['age'])) $age = -1;
        else $age = (int)$_POST['age'];
        
        if(isset($_POST['sex']) && !empty($_POST['sex'])){
            if(strcasecmp($_POST['sex'], 'male')) $sex='male';
            else if(strcasecmp($_POST['sex'], 'female')) $sex="female";
            else $sex="";
        } else $sex = "";
        
        $pub_email = $_POST['pub_email'];
        $location = $_POST['location'];
        
        $m = preg_match("/^[A-Za-z0-9@._-]{5,30}$/", $login, $mathces);
        if($m===FALSE || $m == 0){
            $this->message("Обнаружены недопустимые символы в поле Логин. Допустимые символы:
                'A' ... 'Z', 'a' ... 'z', '0' ... '9', '@', '.', '-', '_'. <br />
                Логин должен содержать от 5 до 30 символов.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
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
        
        $m = preg_match("/^.{4,30}$/", $password, $mathces);
        if($m===FALSE || $m == 0){
            $this->message("Обнаружены недопустимые символы в поле Пароль. <br />
                Пароль должен содержать от 4 до 30 символов.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        if(empty($first_name)||empty ($surname) || empty ($pub_nickname) || empty($login)){
            $this->message("Необходимо заполнить все поля формы регистрации.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        $m = preg_match("/^.{3,30}$/", $pub_nickname, $mathces);
        if($m===FALSE || $m == 0){
            $this->message("Обнаружены недопустимые символы в поле Псевдоним. <br />
                Псевдоним должен содержать от 3 до 30 символов.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        $m = preg_match("/^\w+$/", $first_name, $mathces);
        if($m===FALSE || $m == 0){
            $this->message("Обнаружены недопустимые символы в поле Имя. <br />
                Разрешены только буквы, цифры и знак подчеркивания.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        $m = preg_match("/^\w+$/", $surname, $mathces);
        if($m===FALSE || $m == 0){
            $this->message("Обнаружены недопустимые символы в поле Фамилия. <br />
                Разрешены только буквы, цифры и знак подчеркивания.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        $m = preg_match("/^[A-Za-z0-9@._-]{0,128}$/", $pub_email, $mathces);
        if($m===FALSE || $m == 0){
            $this->message("Обнаружены недопустимые символы в поле Публичный e-mail. Допустимые символы:
                'A' ... 'Z', 'a' ... 'z', '0' ... '9', '@', '.', '-', '_'. <br />
                Поле должно содержать от 0 до 128 символов.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        if($age!=-1 && ($age>100 || $age < 5)){
            $this->message("Ваш возраст выглядит подозрительно.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        $m = preg_match("/^.{0,128}$/", $location, $mathces);
        if($m===FALSE || $m == 0){
            $this->message("Недопустимый символ в поле Местоположение.", 
                    "Ошибка ввода данных", "user/register", 5);
            return false;
        }
        
        $address = gethostbyname("localhost");
        $port    = 10000;
        $registered = false;
        
        try {

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
            $msg = $pub_email."\n";
            socket_write($socket, $msg);
            $msg = $sex."\n";
            socket_write($socket, $msg);
            $msg = $age."\n";
            socket_write($socket, $msg);
            $msg = $location."\n";
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
    
}

?>
