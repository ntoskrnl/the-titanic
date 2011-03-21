<?php
    require_once("Smarty.class.php");
    include('configs/config.php');

    $registry = new Registry();

    $router = new Router($registry);
    $registry['router'] = $router;
    $router->setPath (site_path . 'controllers');

    $smarty = new Smarty();

    $smarty->debugging = false;
    $smarty->allow_php_tag = true;
    $smarty->caching = false;
    $smarty->cache_lifetime = 120;
    $smarty->template_dir = site_path."templates".DIRSEP;
    $smarty->config_dir = site_path."configs".DIRSEP;

    $registry['smarty'] = $smarty;

    $router->deligate();

?>
