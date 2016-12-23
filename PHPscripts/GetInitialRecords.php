<?php
include ('MySqlHostInfo.php');

$r=mysql_query("SELECT * FROM schedule oRDER BY DATE DESC, TIME DESC LIMIT 15");
$index=0;
while($row=mysql_fetch_array($r))
   {
        $flag[++$index][id]=$row[id];
        $flag[$index][time]=$row[time];
    	$flag[$index][date]=$row[date];
    	$flag[$index][spouseTime]=$row[spouseTime];
    	$flag[$index][isPeed]=$row[isPeed];
    	$flag[$index][isPooped]=$row[isPooped];
    	$flag[$index][isAte]=$row[isAte];
    	$flag[$index][name]=$row[name];
   }

   print(json_encode($flag));
   mysql_close(con);
?>