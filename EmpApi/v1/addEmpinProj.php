<?php
   require_once '../includes/DbOperation.php'; 
   $response=array();

   if($_SERVER['REQUEST_METHOD']=='POST') {
      if(isset($_POST['eid']) and isset($_POST['pid'])) {
        
        $db = new DbOperation();
        $response = $db->addEmpProj($_POST['eid'],$_POST['pid']);  
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