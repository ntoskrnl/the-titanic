
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <base href="{#website_root#}" />
    <title>The Titanic :: Playing Russian Billiard via the Internet</title>
    
    {if isset($redirect_timeout)} 
    <meta http-equiv="Refresh" content="{$redirect_timeout}; url={$redirect}" />
    {/if}   
    <meta name="description" content="Сайт проекта Titanic - кросс-платформенной игры Русский бильярд 3D. Играйте в бильярд через Интернет!">
    
    <link rel="stylesheet" type="text/css" href="./styles/global.css" />
    <link rel="favicon" href="{#website_root#}favicon.ico" />
    <link rel="shortcut icon" href="favicon.ico">
    
    <script type="text/javascript" src="{#website_root#}js/jquery-1.5.2.min.js"></script>
    <script type="text/javascript">                                         
        function ajax_login(){
            var url = "user/login/ajax";
            var l = document.getElementById("login_input").value;
            var p = document.getElementById("password_input").value;
            var r = document.getElementById("redirect_input").value;
            $.post(url, { login : l, password : p, redirect_input : r } , function(data) {
               $('#login_form').load("pages/ru/user/login_form.php");
            });
        }
        
        $(document).ready(function() {
            $('#server_status').load('pages/util/server_status.php');
            $('#best_players').load('pages/util/best_players.php');
            $('#online_users').load('pages/util/online_users.php');
        });                                    
    </script>  
</head>
