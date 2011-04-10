<?php

class Controller_Feedback extends Controller_Base {
    function index($args) {
        global $lang;
        $this->registry['smarty']->display("$lang/feedback.tpl");
    }

    function contacts($args){
        global $lang;
        $file = "pages/$lang/feedback/contacts.html";
        $this->registry['smarty']->assign("navigation_file", "$lang/_parts/nav/feedback_nav.tpl");
        $this->registry['smarty']->assign("content_file", $file);
        $this->registry['smarty']->assign("content_file_date", date('Y-m-d H:i:s.', 
                filemtime($file)));
        $this->registry['smarty']->display("$lang/article.tpl");
    }
    
    function leave($args){
        global $lang;
        $file = "pages/$lang/feedback/leave.html";
        $this->registry['smarty']->assign("navigation_file", "$lang/_parts/nav/feedback_nav.tpl");
        $this->registry['smarty']->assign("content_file", $file);
        $this->registry['smarty']->assign("content_file_date", date('Y-m-d H:i:s.', 
                filemtime($file)));
        $this->registry['smarty']->display("$lang/article.tpl");
    }
}

?>
