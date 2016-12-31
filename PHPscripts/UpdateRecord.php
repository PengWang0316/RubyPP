<?php
    include 'MySqlHostInfo.php';
    include 'QueryBasicParameters.php';


	mysql_query("UPDATE schedule SET name = '$name', time='$time', date='$date', isPeed='$isPeed', isPooped='$isPooped', isAte='$isAte', isPeedInside='$isPeedInside', isPoopedInside='$isPoopedInside' WHERE id ='$id'",$con);

	mysql_close($con);
?>