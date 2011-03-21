<?php

class Router {
	private $registry;
	private $path;
	private $args = array();
        private $controller;
        private $action;

	function __construct($registry) {
		$this->registry = $registry;
	}

        /**
         * Where to search for controllers (recursively)?
         * @param string $path directory with controllers
         */
	function setPath($path) {
		$path = trim($path, '/\\');
		$path .= DIRSEP;
		$path = DIRSEP.$path;

		if (is_dir($path) == false) {
			throw new Exception ('Invalid controller path: `' . $path . '`');
		}
		$this->path = $path;
	}

	function deligate($path) {
	if(strcmp($this->registry['controllers'],"off")==0) return;	
	if(!isset($path))
	    $this->getController($file, $controller, $action, $args);
	else $this->getControllerByPath($path, $file, $controller, $action, $args);
		 // Файл доступен?
        if (is_readable($file) == false) {
                die ($file.':404 Not Found');
        }
        // Подключаем файл
        include ($file);

        // Создаём экземпляр контроллера
        $class = 'Controller_' . $controller;
        $controller = new $class($this->registry);
        // Действие доступно?
        if (is_callable(array($controller, $action)) == false) {
                die ('404 Not Found');
        }
        // Выполняем действие

        $this->args = $args;
        $this->controller = $controller;
        $this->action = $action;
        
        $controller->$action($args);
	}
	

	private function getController(&$file, &$controller, &$action, &$args) {
		$route = (empty($_GET['route'])) ? '' : $_GET['route'];
 		if (empty($route)) { $route = 'index'; }
        // Получаем раздельные части
        $route = trim($route, '/\\');
        $parts = explode('/', $route);
        // Находим правильный контроллер
        $cmd_path = $this->path;
        foreach ($parts as $part) {
                $fullpath = $cmd_path . $part;
                // Есть ли папка с таким путём?
                if (is_dir($fullpath)) {
                        $cmd_path .= $part . DIRSEP;
                        array_shift($parts);
                        continue;
                }
                // Находим файл
                if (is_file($fullpath . '.php')) {
                        $controller = $part;
                        array_shift($parts);
                        break;
                }
        }
        if (empty($controller)) { $controller = 'index'; };
        // Получаем действие
        $action = array_shift($parts);
        if (empty($action)) { $action = 'index'; }
        $file = $cmd_path . $controller . '.php';
        $args = $parts;
	}
	
    private function getControllerByPath($path, &$file, &$controller, &$action, &$args) {
	$route = (empty($path)) ? '' : $path;
 	if (empty($route)) { $route = 'index'; }	
        // Получаем раздельные части
        $route = trim($route, '/\\');
        $parts = explode('/', $route);
        // Находим правильный контроллер
        $cmd_path = $this->path;
        foreach ($parts as $part) {
                $fullpath = $cmd_path . $part;
                // Есть ли папка с таким путём?
                if (is_dir($fullpath)) {
                        $cmd_path .= $part . DIRSEP;
                        array_shift($parts);
                        continue;
                }
                // Находим файл
                if (is_file($fullpath . '.php')) {
                        $controller = $part;
                        array_shift($parts);
                        break;
                }
        }
        if (empty($controller)) { $controller = 'index'; };
        // Получаем действие
        $action = array_shift($parts);
        if (empty($action)) { $action = 'index'; }
        $file = $cmd_path . $controller . '.php';
        $args = $parts;
	}
	
}

?>

