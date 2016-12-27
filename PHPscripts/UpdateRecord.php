<?php
    include 'MySqlHostInfo.php';
    include 'QueryBasicParameters.php';


	mysql_query("UPDATE schedule SET name = '$name', time='$time', isPeed='$isPeed', isPooped='$isPooped', isAte='$isAte' WHERE date ='$date' and spouseTime='$spouseTime'",$con);

	mysql_close($con);
?>