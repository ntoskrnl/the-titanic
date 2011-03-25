<?php
    $start_time = microtime(true);
    $smarty_path = "./lib/smarty/libs/";

    require_once($smarty_path."Smarty.class.php");
    include('configs/config.php');

    $_SESSION['start_time'] = $start_time;

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

    $smarty->assign("lang", $lang);

    $registry['smarty'] = $smarty;

    $router->deligate();

?>
