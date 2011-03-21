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
		if(!isset($reason)) $reason = "N/A";
		$this->registry['smarty']->assign("redir", $redir);
		$this->registry['smarty']->assign("time", $t);
		$this->registry['smarty']->assign("reason", $reason);
		$this->registry['smarty']->display("etc/redirect.tpl");
	}

}

?>

