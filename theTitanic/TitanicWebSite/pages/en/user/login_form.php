<?php session_start(); ?>
<?php if(isset($_SESSION['logged_in']) && $_SESSION['logged_in']) : ?>
        <div>
            <p>Вход выполнен <b><?php echo(strip_tags($_SESSION['login'])); ?></b><br /><a href="user/login/out">Выйти</a></p>
        </div>
<?php else: ?>
        <form method="post" action="user/login" class="align_r" id="login_form" onsubmit="ajax_login()">
            <fieldset style="border-style: none;">
                <div>
                    <input type="text" id="login_input" placeholder="Логин" name="login" style="width: 100px;" />
                </div>
                <div>
                    <input type="password" id="password_input" placeholder="Пароль" name="password" style="width: 100px;" />
                </div>
                <div>
                    <input type="button" value="Вход" onclick="ajax_login()" />
                    <noscript><input type="submit" value="Вход" /></noscript>
                </div>
                <input type="hidden" id="redirect_input" name="redirect" value="index" />
            </fieldset>
        </form>
<?php endif; ?>
