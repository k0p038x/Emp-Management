<?php
   require_once '../includes/DbOperation.php'; 
   $response=array();

   if($_SERVER['REQUEST_METHOD']=='POST') {
      if(isset($_POST['eid']) and isset($_POST['pid']) and isset($_POST['hrs'])) {
          $db = new DbOperation();
          $response=$db->updateHrs($_POST['eid'],$_POST['pid'],$_POST['hrs']);
      }
      else {
          $response['error']=true;
          $resposne['message']="Invalid input";
      }
    }
    else {
        $response['error']=true;
        $response['message']="Invalid request";
    }
   
    echo json_encode($response);

?>