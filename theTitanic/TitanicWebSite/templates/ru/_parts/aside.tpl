<div id="aside">
    <div>
        <h3>
            Язык сайта:
        </h3>
        <div id="language_selector" class="align_c">
            <p><b><a href="util/lang/change/en">English</a>&nbsp;|&nbsp;Русский</b></p>
        </div>
    </div>
    <div>
        <h3>
            Вход в систему:
        </h3>
        <div class="align_r">
            [<a href="user/register">Регистрация</a>]
        </div>
        {php} include("pages/{$lang}/user/login_form.php"); {/php}
    </div>
    {include file="{$lang}/_parts/right_side_static.tpl"}
    <p></p>
</div>