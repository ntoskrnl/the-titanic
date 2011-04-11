<h2>User profile for <b>id{$user_id}</b></h2>
{if isset($smarty.session.logged_in) && $smarty.session.logged_in}
    <p>Empty yet.</p>
{else}
    <p>You must log in to view profiles of users.</p>
{/if}