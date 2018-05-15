<?php 
  require_once '../includes/DbOperation.php'; 
  $resp=array();
  if($_SERVER['REQUEST_METHOD']=='POST') {
    if(isset($_POST['uname']) and isset($_POST['pword'])) {
      
        $db=new DbOperation();
        $resp=$db->empLogin($_POST['uname'],$_POST['pword']);
    }
    else {
      
        $resp['error']=true;
        $resp['message']="Fields are missing";
    }
  }
  else {
     
      $resp['error']=true;
      $resp['message']="Invalid Request Method";
  }
  
    echo json_encode($resp);
  
  
?>