<?php


Class Registry implements ArrayAccess{
	private $vars = array();
	
	function set($key, $val){
		if(isset($this->vars[$key])==true) throw new Exception("Trying to modify value $key which is already set! Remove it first.");
		$this->vars[$key] = $val;	
	}
	
	function get($key){
		if(isset($this->vars[$key])==false){
			return null;
		}
		return $this->vars[$key];
	}
	
	function remove($key){
		unset($this->vars[$key]);
	}

	function offsetExists($offset) {
		return isset($this->vars[$offset]);
	}

	function offsetGet($offset) {
		return $this->get($offset);
	}

	function offsetSet($offset, $value) {
 		$this->set($offset, $value);
	}

	function offsetUnset($offset) {
 		unset($this->vars[$offset]);
	}
}


?>

