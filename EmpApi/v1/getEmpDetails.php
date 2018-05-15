<?php 
 require_once '../includes/DbOperation.php'; 

 $response=array();
 if($_SERVER['REQUEST_METHOD']=='POST') {
   if(isset($_POST['eid'])) {
          $db = new DbOperation();
          $response=$db->getEmpDetails($_POST['eid']);
   }
   else {
       $response['error']=true;
       $response['message']='Inavlid inputs';
   }
 }
 else {
     $response['error']=true;
     $response['message']='Invalid request method';
 }
  echo json_encode($response);

?>