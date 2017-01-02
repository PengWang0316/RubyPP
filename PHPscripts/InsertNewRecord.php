<?php
    include 'MySqlHostInfo.php';
    include 'QueryBasicParameters.php';

	mysql_query("INSERT INTO schedule (id,time,date,isPeed,isPooped,isAte,name,isPeedInside,isPoopedInside) VALUES (null,'$time','$date','$isPeed','$isPooped','$isAte','$name','$isPeedInside','$isPoopedInside')",$con);
    print mysql_insert_id();
	mysql_close($con);
?>