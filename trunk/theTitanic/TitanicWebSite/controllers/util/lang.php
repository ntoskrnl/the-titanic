<?php

class Controller_Lang extends Controller_Base {
    function index($args) {
        global $lang;
        $this->message("Current language is <b>$lang</b>.", "Language");
    }
    function change($args) {
        global $lang, $_SESSION;
        if(!isset($args[0])||strcmp(trim($args[0]),"")==0) return;
        $_SESSION['lang'] = $args[0];
        $lang = $args[0];
        $this->registry['smarty']->assign("lang", $lang);
        $this->message("Current language is <b>$lang</b>.", "Language", "index", 0);
    }
    
}

?>
