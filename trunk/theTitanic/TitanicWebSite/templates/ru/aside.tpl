<div id="aside">
    <div>
        <h3>
            Вход в систему:
        </h3>
        <div style="text-align: right;">
            [<a href="user/register">Регистрация</a>]
        </div>
        {if isset($smarty.session.logged_in)&&($smarty.session.logged_in)}
        <div>
            <p>Вход выполнен <b>{$smarty.session.login}</b><br /><a href="user/login/out">Выйти</a></p>
        </div>
        {else}
        <form method="post" action="user/login" class="align_r">
            <fieldset>
                <div>
                    <input type="text" placeholder="Логин" name="login" style="width: 110px;" />
                </div>
                <div>
                    <input type="password" placeholder="Пароль" name="password" style="width: 110px;" />
                </div>
                <div>
                    <input type="submit" value="Вход" />
                </div>
                <input type="hidden" name="redirect" value="{$smarty.server.REQUEST_URI}" />
            </fieldset>
        </form>
        {/if}
    </div>
    {include file="{$lang}/right_side_static.tpl"}
    <p></p>
</div>