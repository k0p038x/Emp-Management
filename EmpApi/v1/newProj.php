<?php
   require_once '../includes/DbOperation.php'; 
   $response=array();

   if($_SERVER['REQUEST_METHOD']=='POST') {
      if(isset($_POST['pid']) and isset($_POST['name']) and isset($_POST['lead'])) {
        
        $db = new DbOperation();
        $response = $db->newProj($_POST['pid'],$_POST['name'],$_POST['lead']); 
      } 
      else {
          $response['error']=true;
          $response['message']='Invalid inputs';
      }
    }
    else {
        $response['error']=true;
        $response['message']='Invalid request method';
    }

    echo json_encode($response);


?>