<div id="aside">
    <div>
        <h3>
            Site language:
        </h3>
        <div id="language_selector" class="align_c">
            <p><b>English&nbsp;|&nbsp;<a href="util/lang/change/ru">Russian</a></b></p>
        </div>
    </div>
    <div>
        <h3>
            Authentication:
        </h3>
        <div class="align_r">
            [<a href="user/register">Sign up</a>]
        </div>
        {php} include("pages/{$lang}/user/login_form.php"); {/php}
    </div>
    {include file="{$lang}/_parts/right_side_static.tpl"}
    <p></p>
</div>