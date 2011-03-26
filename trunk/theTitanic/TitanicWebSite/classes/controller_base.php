<?php


Abstract Class Controller_Base {
        protected $registry;

        function __construct($registry) {
                $this->registry = $registry;
        }

        abstract function index($args);

	function redirect($redir, $t, $reason){
		$registry['controllers'] = "off";
		if(!isset($t)) $t = 0;
		if(!isset($reason)) $reason = "Reason is not given.";
		$this->message($reason, "Redirecting...", $redir, $t);
	}
        
        function message($text, $title, $redirect, $timeout){
        global $lang;
        
        $this->registry['smarty']->assign("msg_text", $text);
        $this->registry['smarty']->assign("msg_title", $title);
        $this->registry['smarty']->assign("redirect", $redirect);
        $this->registry['smarty']->assign("redirect_timeout", $timeout);
        $this->registry['smarty']->display("$lang/message.tpl");
    }

}

?>

