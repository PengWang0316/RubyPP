<?php
    $host='localhost';
    $uname='1279000';
    $pwd='jiajia';
    $db="1279000";

    $con=mysql_connect($host,$uname,$pwd) or die("connection failed");
    mysql_select_db($db,$con) or die("db selection failed");
?>