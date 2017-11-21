
<?php
/*extract($_POST);*/
$email=$_POST['email'];
$pass=$_POST['pass'];

include('connect.php');

$sql=mysql_query("select * from restaurant where email='$email' and pass='$pass'");
	session_start();
	if($data=mysql_fetch_array($sql))
	{
		$_SESSION['id']=$data['id'];		
		echo '<script>top.location=("admin.php");</script>';
	}
	else
	{
		$_SESSION['login_error']="Invalid User Name or Password ! !";
		echo"<script>top.location=('login.php');</script>";
	}
?>