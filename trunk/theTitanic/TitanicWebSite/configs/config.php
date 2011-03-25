<?php
    /**
     * Get variable from session data, or set it by default and modify session.
     */
    function get_from_session($param, $current){
            if(isset($_SESSION[$param]))
                    $res = $_SESSION[$param];
            else $res = $_SESSION[$param] = $current;
            if(isset($_REQUEST[$param]))
                    $res = $_SESSION[$param] = $_REQUEST[$param];
            return $res;
    }

    session_start();
    error_reporting(E_ALL & ~E_WARNING & ~E_NOTICE);
    if (version_compare(phpversion(), '5.1.0', '<') == true) {
            die ('PHP5.1 or higher!');
    }

    ob_implicit_flush();
    ob_end_flush();

    // langugage
    $lang = get_from_session("lang", "ru");

    // Константы:
    define ('DIRSEP', DIRECTORY_SEPARATOR);
    // Узнаём путь до файлов сайта
    $site_path = realpath(dirname(dirname(__FILE__)).DIRSEP).DIRSEP;
    define ('site_path', $site_path);

    // Регистрируем загрузчик классов
    function __autoload($class_name) {
      $filename = strtolower($class_name) . '.php';
      $file = site_path . 'classes' . DIRSEP . $filename;
      if (file_exists($file) == false) {
        return false;
      }
      include ($file);
    }
    spl_autoload_register("__autoload");


?>