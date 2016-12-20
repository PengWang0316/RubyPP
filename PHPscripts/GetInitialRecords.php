<?php
include ('MySqlHostInfo.php');

$r=mysql_query("SELECT * FROM schedule oRDER BY DATE DESC, TIME DESC LIMIT 15");
while($row=mysql_fetch_array($r))
   {
        $flag[$row[id]][time]=$row[time];
    	$flag[$row[id]][date]=$row[date];
    	$flag[$row[id]][spouseTime]=$row[spouseTime];
    	$flag[$row[id]][isPeed]=$row[isPeed];
    	$flag[$row[id]][isPooped]=$row[isPooped];
    	$flag[$row[id]][isAte]=$row[isAte];
   }

   print(json_encode($flag));
   mysql_close(con);
?>