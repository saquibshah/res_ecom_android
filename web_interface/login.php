<?php
session_start();
$login_error=$_SESSION['login_error'];
?>
						
					
					
<html>
<head>
<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Res-ecom Login</title>

    <!-- Core CSS - Include with every page -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link rel="shortcut icon" href="assets/img/favicon.png">
    
    <script type="text/javascript">
			function chkfrm()
			{
				var email=document.login.email.value;
				var pass=document.login.pass.value;
				
				if(email=="")
				{
					alert("User Name Required ! ! !");
					document.login.email.focus();
					return false;
				}
				else if(pass=="")
				{
					alert("Password Required ! ! !");
					document.login.pass.focus();
					return false;
				}
				else
				{
					return true;
				}
			}
		</script>
    
</head>

<body>
<div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">

                       <form role="form" action="login_submit.php" method="POST" name="login" >
                        <fieldset>
                        <div class="form-group">
							<input class="form-control" placeholder="E-mail" name="email" type="text" autofocus>
   						</div>                     
                        <div class="form-group">
                        	<input class="form-control" placeholder="Password" name="pass" type="password">
                        </div>
                        <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                        <input type="submit" class="btn btn-lg btn-success btn-block" name="login" value="Login" onclick="return chkfrm();">
                        
                        </fieldset>
                       </form>
                       
                        <?php 
							if($login_error!="")
							{
							?>
                        
                        <div style="width: inherit; height: 20px; margin-top: 15px; text-align: center; color:#950000;">
							<?php echo "&nbsp;".$login_error; $_SESSION['login_error']="";?>
						</div>
							<?php 
							} 
							else 
							{
							echo "&nbsp;";	
							}
							?>
                    </div>
                </div>
            </div>
        </div>
    </div>
<!-- Core Scripts - Include with every page -->
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>

    <!-- SB Admin Scripts - Include with every page -->
    <script src="js/sb-admin.js"></script>
</body>

</html>
