<?php
   require_once '../includes/DbOperation.php'; 
   $response=array();
   if($_SERVER['REQUEST_METHOD']=='POST') {
       if(isset($_POST['pid'])) {
         $db = new DbOperation();
         $response=$db->pDetails($_POST['pid']);
         $response['error']='false';
       }
       else {
           $response['error']=true;
           $respone['message']="Insufficient Details";
       }
   }
   else {
       $respone['error']=true;
       $respone['message']="Invalid request method";
   }

   echo json_encode($response);


?>