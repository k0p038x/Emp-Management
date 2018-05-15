
<?php 
 
 class DbOperation {
     private $con;

     function __construct() {

        require_once dirname(__FILE__).'/DbConnect.php';
        $db=new DbConnect();
        $this->con=$db->connect();
     }

     function addUserInLogin($uname,$pass) { 
        
         $pword = $pass;
         $stmt = $this->con->prepare("SELECT * FROM employee WHERE e_id=?");
         $stmt->bind_param("s",$uname);
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows>0) {
            $stmt1 = $this->con->prepare("INSERT INTO login VALUES (?,?);");
            $stmt1->bind_param("ss",$uname,$pword);
            
            if($stmt1->execute()) { 
               return true; 
            }  
           else 
              return false;
         }
         else
         return false;
        

         
     } 

     function empLogin($uname,$pword) {
         $response=array();
         $stmt = $this->con->prepare("SELECT employee.e_id FROM login,employee WHERE login.uname=employee.e_id and uname=? and pword=?");
         $stmt->bind_param("ss",$uname,$pword);
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows>0) {
             $stmt->bind_result($eid);
             $stmt->fetch();
             $db1 = new DbOperation();
             $response=$db1->getEmpDetails($eid);
         }
         else{ 
             $response['error']=true;
             $response['message']='No entry found';
         }        

        return $response;
     }

     function pDetails($pid) {
         $response=array();
         $stmt= $this->con->prepare("SELECT empProject.e_id,empProject.hrs_spent,jobClass.hrly_rate FROM projectDetails,empProject,jobClass,employee WHERE empProject.p_id=projectDetails.p_id AND projectDetails.p_id=? AND empProject.e_id=employee.e_id AND employee.e_ctype=jobClass.ctype");
         $stmt->bind_param("s",$pid);
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows>0) {
             $stmt->bind_result($eid,$hours,$rate);
             $employee = array();
             while($stmt->fetch()) {
                 $employee[]=array('eid'=>$eid,'hours'=>$hours,'rate'=>$rate);
             }
             $response['employee']=$employee;
         }
         return $response;
     }

     function updateHrs($eid,$pid,$khrs) {
         $hrs=(int)$khrs;
         $response = array();
         $stmt=$this->con->prepare("SELECT hrs_spent FROM empProject WHERE e_id=? AND p_id=?");
         $stmt->bind_param("ss",$eid,$pid);
         $stmt->execute();
         $stmt->store_result();
         $stmt->bind_result($past_hrs);
         $stmt->fetch();
         $stmt=$this->con->prepare("UPDATE empProject SET hrs_spent=? WHERE e_id =? AND p_id=?");
         $stmt->bind_param("sss",$hrs,$eid,$pid);
         $stmt->execute();

         $diff = $hrs-$past_hrs;
         $stmt=$this->con->prepare("UPDATE employee SET hrs_spent=hrs_spent+? WHERE e_id=?");
         $stmt->bind_param("ss",$diff,$eid);
         $stmt->execute();
         $response['error']=false;
         $response['message']='Updated Successfully';
         return $response;
     }

     function getEmpDetails($eid)  {
        $response=array();
        $stmt = $this->con->prepare("SELECT e_name,e_ctype,hrs_spent FROM employee WHERE e_id=?");
        $stmt->bind_param("s",$eid);
        $stmt->execute();
        $stmt->store_result();
        if($stmt->num_rows > 0) {
            $stmt->bind_result($ename,$etype,$hrs);
            $stmt->fetch();
            $response['id']=$eid;
            $response['name']=$ename;
            $response['type']=$etype;
            $response['thours']=$hrs;
            $response['error']=false;
            $response['message']="Success";
           
            $stmt3 = $this->con->prepare("SELECT hrly_rate FROM jobClass WHERE ctype=?");
            $stmt3->bind_param("s",$etype);
            $stmt3->execute();
            $stmt3->store_result();
            if($stmt3->num_rows > 0) {
               $stmt3->bind_result($rate);
               $stmt3->fetch();
               $response['rate']=$rate;
            }
            $stmt2 = $this->con->prepare("SELECT empProject.p_id,p_name,p_lead,hrs_spent FROM empProject,projectDetails WHERE empProject.p_id=projectDetails.p_id and empProject.e_id=?");
            $stmt2->bind_param("s",$eid); 
            $stmt2->execute();
            $stmt2->store_result();
            $response['count']=$stmt2->num_rows;
            if($stmt2->num_rows > 0) {
                $stmt2->bind_result($pid,$pname,$plead,$hrs);
                
                $projects=array();
                while($stmt2->fetch()) {
                    $projects[]=array('pid'=>$pid,'pname'=>$pname,'hours'=>$hrs,'plead'=>$plead);
                } 
                $response['projects']=$projects;
            }   
       }
       else {
           $response['error']=true;
           $response['message']="No entry found";
       }
       return $response;
     }

     function changePword($eid,$old,$new) {
         $response = array();
         $stmt1 = $this->con->prepare("SELECT * FROM login WHERE uname=? and pword=?");
         $stmt1->bind_param("ss",$eid,$old);
         $stmt1->execute();
         $stmt1->store_result();
         if($stmt1->num_rows > 0) {
            $stmt = $this->con->prepare("UPDATE login SET pword=? WHERE uname=?");
            $stmt->bind_param("ss",$new,$eid);
            $stmt->execute();
            $response['error']=true;
            $response['message']='Password Updated';
         }
         else {
             $response['error']=true;
             $response['message']='Wrong Password';
         }
         return $response;
     }

     function findEmp($eid,$pid) {
         $response = array();
         $stmt = $this->con->prepare("SELECT * FROM employee WHERE e_id=?");
         $stmt->bind_param("s",$eid);
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows > 0) {
            $stmt1 = $this->con->prepare("SELECT * FROM empProject WHERE e_id=? and p_id=?");
            $stmt1->bind_param("ss",$eid,$pid);
            $stmt1->execute();
            if($stmt->num_rows>0) {
                 $stmt2 = $this->con->prepare("INSERT INTO empProject VALUES(?,?,0)");
                 $stmt2->bind_param("ss",$eid,$pid);
                 $stmt2->execute();
                 $response['error']=false;
                 $response['message']='Added Successfully';
            }
            else {
                $response['error']=true;
                $response['message']='Already in the project';
            }
         }
         else {
             $response['error']=true;
             $response['message']='Invalid Employee ID';
         }
     }

     function getAllEmp() {

         $response=array();
         
         $stmt = $this->con->prepare("SELECT pword from login WHERE uname='admin'");
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows>0) {
             $stmt->bind_result($pword);
             $stmt->fetch();
             $response['pword']=$pword;
         }
         $stmt = $this->con->prepare("SELECT e_id,e_name,e_ctype FROM employee");
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows>0) {
             $stmt->bind_result($id,$name,$class);
             $employee=array();
             while($stmt->fetch()) {
                 $employee[]=array('id'=>$id,'name'=>$name,'class'=>$class);
             }
         }
         $response['employee']=$employee;

         $stmt = $this->con->prepare("SELECT p_id,p_name,p_lead FROM projectDetails");
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows>0) {
             $stmt->bind_result($pid,$pname,$plead);
             $project=array();
             while($stmt->fetch()) {
                 $project[]=array('id'=>$pid,'name'=>$pname,'lead'=>$plead);
             }
         }
         $response['project']=$project;

         $stmt = $this->con->prepare("SELECT ctype,hrly_rate FROM jobClass");
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows>0) {
             $stmt->bind_result($class,$hrly_rate);
             $jobClass=array();
             while($stmt->fetch()) {
                 $jobClass[]=array('class'=>$class,'rate'=>$hrly_rate);
             }
         }
         $response['jobClass']=$jobClass; 

         return $response;
     }

     function newEmp($id,$name,$class) {
         $response= array();
         $stmt = $this->con->prepare("SELECT * FROM employee WHERE e_id=?");
         $stmt->bind_param("s",$id);
         $stmt->execute();
         $stmt->store_result();
         if($stmt->num_rows>0) {
             $response['error']=true;
             $response['message']='Already registered';

         }
         else {
            $stmt1 = $this->con->prepare("SELECT * FROM jobClass WHERE ctype=?");
            $stmt1->bind_param("s",$class);
            $stmt1->execute();
            $stmt1->store_result();
            if($stmt1->num_rows>0) {
                $stmt2 = $this->con->prepare("INSERT INTO employee VALUES(?,?,?,0)");
                $stmt2->bind_param("sss",$id,$name,$class);
                if($stmt2->execute()) {
                    $response['error']=false;
                    $response['message']='Entry Registered';
                    $db = new DbOperation();
                    $response['data']=$db->getAllEmp();
                }
                else {
                    $response['error']=true;
                    $response['message']='Something Went Wrong';
                }
                
   
            }
            else {
                $response['error']=true;
                $response['message']='Illegal JobClass';
            }
            
            
         }
         return $response;
     }

    function newProj($pid,$name,$eid) {
        $response= array();
        $stmt = $this->con->prepare("SELECT * FROM projectDetails WHERE p_id=?");
        $stmt->bind_param("s",$pid);
        $stmt->execute();
        $stmt->store_result();
        if($stmt->num_rows > 0) {
           $response['error']=true;
           $response['message']='Project ID already exists';
        }
        else {
            $stmt1 = $this->con->prepare("SELECT * FROM employee WHERE e_id=?");
            $stmt1->bind_param("s",$eid);
            $stmt1->execute();
            $stmt1->store_result();
            if($stmt1->num_rows > 0) {
                   $stmt2 = $this->con->prepare("INSERT INTO projectDetails VALUES(?,?,?)");
                   $stmt2->bind_param("sss",$pid,$name,$eid);
                   $stmt2->execute();
                   $stmt3 = $this->con->prepare("INSERT INTO empProject VALUES(?,?,0)");
                   $stmt3->bind_param("ss",$eid,$pid);
                   $stmt3->execute();
                   $response['error']=false;
                   $response['message']='Successfully added';
                   $db = new DbOperation();
                   $response['data']=$db->getAllEmp();
            }
            else {
                $response['error']=true;
                $response['message']='Enter correct lead ID'; 
            }
        }
        return $response;
    } 

    function newJClass($class,$rate) {
        $response = array();
        $stmt = $this->con->prepare("SELECT * FROM jobClass WHERE ctype=?");
        $stmt->bind_param("s",$class);
        $stmt->execute();
        $stmt->store_result();
        if($stmt->num_rows > 0) {
            $response['error']=true;
            $response['message']='Already registered';
        }
        else {
            $stmt1 = $this->con->prepare("INSERT INTO jobClass VALUES (?,?)");
            $stmt1->bind_param("ss",$class,$rate);
            $stmt1->execute();
            $response['error']=false;
            $response['message']='Successfully added';
            $db = new DbOperation();
            $response['data']=$db->getAllEmp();
        }
        return $response;
    }

    function updateEmp($eid,$name,$class) {
        $response = array();
        $stmt = $this->con->prepare("SELECT * FROM jobClass WHERE ctype=?");
        $stmt->bind_param("s",$class);
        $stmt->execute();
        $stmt->store_result();
        if($stmt->num_rows > 0) {
            $stmt1 = $this->con->prepare("UPDATE employee SET e_name=?,e_ctype=? WHERE e_id=?");
            $stmt1->bind_param("sss",$name,$class,$eid);
            $stmt1->execute();
            $response['error']=false;
            $response['message']='Successfully Updated';
            $db = new DbOperation();
            $response['data']=$db->getAllEmp();
        }
        else {
            $response['error']=true;
            $response['message']='No Job Class';
        }
        return $response;
    }

    function addEmpProj($eid,$pid) {
        $response = array();
        $stmt = $this->con->prepare("SELECT * FROM employee WHERE e_id=?");
        $stmt->bind_param("s",$eid);
        $stmt->execute();
        $stmt->store_result();
        if($stmt->num_rows>0) {
             $stmt1 = $this->con->prepare("SELECT * FROM empProject WHERE e_id=? and p_id=? ");
             $stmt1->bind_param("ss",$eid,$pid);
             $stmt1->execute();
             $stmt1->store_result();
             if($stmt1->num_rows > 0) {
                $response['error']=true;
                $response['message']='Already Working';
             }
             else {
                $stmt2 = $this->con->prepare("INSERT INTO empProject VALUES(?,?,0)");
                $stmt2->bind_param("ss",$eid,$pid);
                $stmt2->execute();
                $response['error']=false;
                $response['message']='Successfully Added';
                $db = new DbOperation();
                $response['data']=$db->getAllEmp(); 
                
             }
          }
        else {
            $response['error']=true;
            $response['message']='Invalid Employee';
        }
       return $response;
    }
     
 }
?>


