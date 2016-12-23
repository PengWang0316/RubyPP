<?php
    include 'MySqlHostInfo.php';
    include 'QueryBasicParameters.php';


	mysql_query("UPDATE schedule SET name = '$name', time='$time', isPeed='$isPeed', isPooped='$isPooped', isAte='$isAte' WHERE id ='$id'",$con);

	mysql_close($con);
?>