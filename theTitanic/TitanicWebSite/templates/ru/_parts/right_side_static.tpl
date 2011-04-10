
        <div>
            <h3>
                Состояние сервера:
            </h3>
            <p>
            <b>Статус:</b> 
                <span id="server_status">
                    <noscript>
                    {php} include("pages/util/server_status.php"); {/php}
                    </noscript>
                </span>
            </p>
        </div>
        <hr />
        <div>
            <h3>
                Сейчас играют:
            </h3>
            <div id="online_users">
                <noscript>
                    {php} include("pages/util/online_users.php"); {/php}
                </noscript>
            </div>
            <p class="align_r"><a href="stats">Подробнее...</a></p>
        </div>
        <hr />
        <div>
            <h3>Лучшие игроки:</h3>
            <div id="best_players">
                <noscript>
                    {php} include("pages/util/best_players.php"); {/php}
                    </noscript>
            </div>
            <p class="align_r"><a href="#">Полный рейтинг...</a></p>
        </div>
        