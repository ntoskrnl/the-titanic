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
                    {include file="{$lang}/_parts/left_side_static.tpl"}
                </div>
                <!-- LEFT SIDE NAVIGATION end -->
                <!-- MAIN CONTENT begin -->
                <div id="content">
                    <h2>{$msg_title}</h2>
                    <div>
                        <p>{$msg_text}</p>
                    </div>
                    {if isset($redirect)}
                    <div>
                        <p>
                            Вы можете перейти на страницу <a href="{$redirect}">{$redirect}</a>
                            {if isset($redirect_timeout)} Либо вы будете автоматически на неё перенаправлены через {$redirect_timeout}с.{/if}
                        </p>
                    </div> 
                    {/if}
                    <p>
                        Вы также можете <a href="javascript:history.back(1)">вернуться</a> на предыдущую страницу.
                    </p>
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