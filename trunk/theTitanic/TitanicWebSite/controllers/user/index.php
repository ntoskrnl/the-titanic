<?php
class Controller_Index extends Controller_Base {
    public function index($args){
        echo "INDEX";
    }
    
    public function profile($args){
        global $lang;
        $file = "$lang/user/profile.tpl";
        $this->registry['smarty']->assign("content_file", $file);
        $this->registry['smarty']->assign("user_id", $_GET['id']);
        $this->registry['smarty']->display("$lang/tpl_article.tpl");
    }
}
?>
