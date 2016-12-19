<?php
    $host='sql113.byethost4.com';
	$uname='b4_19329911';
	$pwd='xyz19822';
	$db="b4_19329911_rubypp";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");
>