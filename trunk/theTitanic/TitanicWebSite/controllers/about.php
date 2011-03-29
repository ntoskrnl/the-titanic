<?php

class Controller_About extends Controller_Base {
    function index($args) {
        global $lang;
        $this->registry['smarty']->display("$lang/about.tpl");
    }

    function history($args){
        global $lang;
        $this->registry['smarty']->assign("navigation_file", "$lang/_parts/nav/about_nav.tpl");
        $this->registry['smarty']->assign("content_file", "pages/$lang/about/history.html");
        $this->registry['smarty']->assign("content_file_date", date('Y-m-d H:i:s.', 
                filemtime("pages/$lang/about/history.html")));
        $this->registry['smarty']->display("$lang/article.tpl");
    }
    
    function faq($args){
        global $lang;
        $this->registry['smarty']->assign("navigation_file", "$lang/_parts/nav/about_nav.tpl");
        $this->registry['smarty']->assign("content_file", "pages/$lang/about/faq.html");
        $this->registry['smarty']->assign("content_file_date", date('Y-m-d H:i:s.', 
                filemtime("pages/$lang/about/faq.html")));
        $this->registry['smarty']->display("$lang/article.tpl");
    }
}

?>
