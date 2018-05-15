<?php
   require_once '../includes/DbOperation.php'; 
   $response=array();

   if($_SERVER['REQUEST_METHOD']=='POST') {
      if(isset($_POST['class']) and isset($_POST['rate'])) {
        
        $db = new DbOperation();
        $response = $db->newJClass($_POST['class'],$_POST['rate']); 
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