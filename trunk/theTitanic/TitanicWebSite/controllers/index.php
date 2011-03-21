<?php

class Controller_Index extends Controller_Base {
    function index($args) {
//        if(isset($_SESSION['login']) && $_SESSION['login']==true){
//          $redir = "user/name/".$_SESSION['user_name'];
//          $this->registry['router']->deligate($redir);
//          return;
//        }
//        $this->registry['smarty']->assign("args", $args);
//        $this->registry['smarty']->assign("controller", "Default");
//        $this->registry['smarty']->assign("action", "index");
//        $this->registry['smarty']->display("ru/index.tpl");
        phpinfo();
    }



}

?>
