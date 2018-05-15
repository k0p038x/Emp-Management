<?php
   require_once '../includes/DbOperation.php'; 
   $response=array();

   if($_SERVER['REQUEST_METHOD']=='POST') {
      if(isset($_POST['eid']) and isset($_POST['name']) and isset($_POST['class'])) {
        
        $db = new DbOperation();
        $response = $db->newEmp($_POST['eid'],$_POST['name'],$_POST['class']); 
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