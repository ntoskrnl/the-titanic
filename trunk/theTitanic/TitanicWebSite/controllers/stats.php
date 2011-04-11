<?php

class Controller_Stats extends Controller_Base {
    function index($args) {
        global $lang;
        $this->registry['smarty']->display("$lang/stats.tpl");
    }

    function rating($args){
        global $lang;
        $file = "$lang/stats/rating.tpl";
        $this->registry['smarty']->assign("navigation_file", "$lang/_parts/nav/stats_nav.tpl");
        $this->registry['smarty']->assign("content_file", $file);
        $this->registry['smarty']->display("$lang/tpl_article.tpl");
    }
    
    function players_online($args){
        global $lang;
        $file = "$lang/stats/players_online.tpl";
        $this->registry['smarty']->assign("navigation_file", "$lang/_parts/nav/stats_nav.tpl");
        $this->registry['smarty']->assign("content_file", $file);
        $this->registry['smarty']->display("$lang/tpl_article.tpl");
    }

    function games_online($args){
        global $lang;
        $file = "$lang/stats/games_online.tpl";
        $this->registry['smarty']->assign("navigation_file", "$lang/_parts/nav/stats_nav.tpl");
        $this->registry['smarty']->assign("content_file", $file);
        $this->registry['smarty']->display("$lang/tpl_article.tpl");
    }

    function history($args){
        global $lang;
        $file = "$lang/stats/history.tpl";
        $this->registry['smarty']->assign("navigation_file", "$lang/_parts/nav/stats_nav.tpl");
        $this->registry['smarty']->assign("content_file", $file);
        $this->registry['smarty']->display("$lang/tpl_article.tpl");
    }
}

?>
