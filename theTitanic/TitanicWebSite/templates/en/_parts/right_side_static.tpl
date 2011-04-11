
        <div>
            <h3>
                Server status:
            </h3>
            <p>
            <b>Status:</b>
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
                Now playing:
            </h3>
            <div id="online_users">
                <noscript>
                    {php} include("pages/util/online_users.php"); {/php}
                </noscript>
            </div>
            <p class="align_r"><a href="stats/players_online">More info...</a></p>
        </div>
        <hr />
        <div>
            <h3>Top players:</h3>
            <div id="best_players">
                <noscript>
                    {php} include("pages/util/best_players.php"); {/php}
                    </noscript>
            </div>
            <p class="align_r"><a href="#">Full rating...</a></p>
        </div>
        