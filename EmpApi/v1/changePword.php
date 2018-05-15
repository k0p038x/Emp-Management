<?php
  require_once '../includes/DbOperation.php';
  $response = array();
  if($_SERVER['REQUEST_METHOD']=='POST') {
      if(isset($_POST['eid']) and isset($_POST['old']) and (isset($_POST['new']))) {
           $db = new DbOperation();
           $response = $db->changePword($_POST['eid'],$_POST['old'],$_POST['new']);
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