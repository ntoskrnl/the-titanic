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
                    {include file="$lang/_parts/nav/index_nav.tpl"}
                    <div>&nbsp;</div>
                    {include file="{$lang}/_parts/left_side_static.tpl"}
                </div>
                <!-- LEFT SIDE NAVIGATION end -->
                <!-- MAIN CONTENT begin -->
                <div id="content">
                    <h2>
                        Titanic &ndash; Internet-Billiard 3D
                    </h2>
                    <img src="./images/girl.jpg" width="200" class="img_r" title="Girl" />
                    <p>
                        Lorem ipsum dolor sit amet consect etuer adipi scing elit sed diam nonummy nibh euismod tinunt ut laoreet dolore magna aliquam erat volut. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.
                    </p>
                    <p>
                        Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                    </p>
                    <p>
                        Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.
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