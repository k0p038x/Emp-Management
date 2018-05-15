<?php
  require_once '../includes/DbOperation.php';
  $response = array();
  if($_SERVER['REQUEST_METHOD']=='POST') {
      if(isset($_POST['eid']) and isset($_POST['pid'])) {
           $db = new DbOperation();
           $response = $db->findEmp($_POST['eid'],$_POST['pid']);
      }
      else {
          $response['error']=true;
          $response['message']='Invalid Inputs';
      }
  }
  else {
   $resposne['error']=true;
   $response['message']='Invalid Request';
  }
  echo json_encode($response);

?>