<!DOCTYPE html>
<html>
    {config_load file="global.ini" section="main"}
    <!-- HTML HEADER begin -->
    {include file="{$lang}/_parts/html_header.tpl"}
    <!-- HTML HEADER end -->
    <body>
        <div id="container">
            <!-- PAGE HEADER begin -->
            {include file="{$lang}/_parts/page_header.tpl"}
            <!-- PAGE HEADER end -->
            <div id="content-container">
                <!-- LEFT SIDE NAVIGATION begin -->
                <div id="section-navigation">
                    {include file="$lang/_parts/nav/stats_nav.tpl"}
                    <div>&nbsp;</div>
                    {include file="{$lang}/_parts/left_side_static.tpl"}
                </div>
                <!-- LEFT SIDE NAVIGATION end -->
                <!-- MAIN CONTENT begin -->
                <div id="content">
                    <h2>
                        Статистика сервера
                    </h2>
                    <p><b>Версия:</b>  TitanicServer-1.0b<br />
                        <b>Хост:</b> danon-laptop.campus.mipt.ru<br />
                        <b>Порт:</b> 10000/TCP<br />
                        <b>Пользователей зарегистрировано:</b> 4
                    <h3>Топ-10 пользователей</h3>
                    <div id="top10">
                        <p>Статистика не готова.</p>
                    </div>
                </div>
                <!-- MAIN CONTENT end -->
                <!-- RIGHT SIDE begin -->
                {include file="{$lang}/_parts/aside.tpl"}
                <!-- RIGTH SIDE end -->
            </div>
            <!-- FOOTER begin -->
            {include file="footer.tpl"}
            <!-- FOOTER END -->
        </div>
    </body>
</html>