<?php
    include 'MySqlHostInfo.php';
    include 'QueryBasicParameters.php';

	mysql_query("INSERT INTO schedule (id,time,date,isPeed,isPooped,isAte,spouseTime,name) VALUES (null,'$time','$date','$isPeed','$isPooped','$isAte','$spouseTime','$name')",$con);

	mysql_close($con);
?>