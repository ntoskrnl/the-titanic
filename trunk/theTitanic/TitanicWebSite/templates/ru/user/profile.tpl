<h2>Профиль пользователя id{$user_id}</h2>
{if isset($smarty.session.logged_in) && $smarty.session.logged_in}
    <p>Пока пусто.</p>
{else}
    <p>Чтобы просматривать профили пользователей, необходимо войти на сайт.</p>
{/if}