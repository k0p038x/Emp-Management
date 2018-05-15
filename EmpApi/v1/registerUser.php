<?php 
require_once '../includes/DbOperation.php'; 
$response = array();

  if($_SERVER['REQUEST_METHOD']=='POST') {
    
      if(isset($_POST['uname']) and isset($_POST['pword'])) {
          $db=new DbOperation();
          $response['uu']=$_POST['uname'];
          $response['pword']=$_POST['pword'];
         if($db->addUserInLogin($_POST['uname'],$_POST['pword'])) {
           $response['error']=false;
           $response['message']="Added Successfully";
         }
         else {
             $response['error']=true;
             $response['message']="Can't add the entry to DataBase";
         }
      }
      else {
          $response['error']=true;
          $response['message']="Fields are missing";
      }
  }
  else {
    $response['error']=true;
    $response['message']="Invalid Request";
}
 echo json_encode($response);

?>