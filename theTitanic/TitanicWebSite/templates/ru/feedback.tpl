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
                    {include file="$lang/_parts/nav/feedback_nav.tpl"}
                    <div>&nbsp;</div>
                    {include file="{$lang}/_parts/left_side_static.tpl"}
                </div>
                <!-- LEFT SIDE NAVIGATION end -->
                <!-- MAIN CONTENT begin -->
                <div id="content">
                    <h2>
                        Обратная связь
                    </h2>
                    <p>Уважаемые пользователи.</p> 
                    <p>
                        В этом разделе вы сможете остваить нам отзыв, предложение или 
                        замечание по поводу проекта Titanic. В будущих версиях прогаммы мы по возможности учтем ваши пожелания.
                    </p>
                    <p>
                        Связаться с нами можно по e-mail, ICQ, Jabber и Skype. <a href="feedback/contacts">Наши координаты</a>. Не спамить! ;)
                    </p>
                    <p>
                        Обязательно оставьте отзыв о нашем проекте, воспользовавшись <a href="feedback/leave">этой формой</a>.
                    </p>
                    <p>
                        Благодарим за оказанное внимание.
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