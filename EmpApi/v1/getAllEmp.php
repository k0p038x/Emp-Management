<?php
  require_once '../includes/DbOperation.php';
  $response = array();
  if($_SERVER['REQUEST_METHOD']=='POST') {
     $db = new DbOperation();
     $response = $db->getAllEmp();
  }
  else {
      $response['error']=true;
      $response['message']='Invalid Request';
  }
  echo json_encode($response); 

?>