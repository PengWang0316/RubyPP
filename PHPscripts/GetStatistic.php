<?php
include ('MySqlHostInfo.php');

$r=mysql_query("SELECT count(id) as totalNumber,name FROM `schedule` group by name order by totalNumber");
$index=0;
while($row=mysql_fetch_array($r))
   {
        $flag[++$index][name]=$row[name];
        $flag[$index][totalNumber]=$row[totalNumber];
   }

   print(json_encode($flag));
   mysql_close(con);
?>