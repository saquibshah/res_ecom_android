<?php
session_start();
$login_error=$_SESSION['login_error'];
?>
	
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/img/favicon.png">

    <title>Res-ecom Admin</title>

    <!-- Bootstrap -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
	<link href="assets/css/bootstrap-theme.css" rel="stylesheet">

    <!-- siimple style -->
    <link href="assets/css/style.css" rel="stylesheet">
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
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

    <!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.html">Restaurant Ecommerce</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="signup.php"><b>Sign up</b></a></li>			
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>

	<div id="header">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<h1 style="text-shadow: 5px 0px 10px rgba(0, 1, 0, .9);">Restaurant Ecom Admin Portal...</h1>
					<h2 class="subtitle" style="text-shadow: 5px 0px 10px rgba(0, 1, 0, .9);"><b>Here you can manage your restaurant and orders</b></h2>
					<form class="form-inline signup" role="form" id="form" action="login_submit.php" method="POST" name="login" target="_self">
					  <div class="form-group">
					    <input type="email" class="form-control" name="email" placeholder="Enter your email address" style="border-radius:5px; padding-left:5px; font-size:16px; width:350px;"><br /><br />
					    <input type="password" class="form-control" name="pass" placeholder="Enter your Password" style="border-radius:5px; padding-left:5px; font-size:16px; width:350px;">
					  </div><br /><br />		
					  <?php 
							if($login_error!="")
							{
							?>	 					  
					  <div style="color:#000;text-shadow: 5px 0px 10px rgba(255, 255, 25, .9); hight: 15px; background-color: #f5ddfd; border-radius: 5px; width: auto;">	
					  			<?php echo "&nbsp;".$login_error; $_SESSION['login_error']="";?>	
						</div><br />			
						<?php 
							} 
							else 
							{
							echo "&nbsp;";	
							}
							?>
					<input type="submit" class="btn btn-theme" name="submit" value="Login" style="float: none; width: 100px;" onclick="return chkfrm();">				  		</form>	
					
				</div>
				
				<div class="col-lg-4 col-lg-offset-2">
					<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					  <ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					  </ol>					
					  <!-- slides -->
					  <div class="carousel-inner">
						<div class="item active">
						  <img src="assets/img/slide1.png" alt="">
						</div>
						<div class="item">
						  <img src="assets/img/slide2.png" alt="">
						</div>
						<div class="item">
						  <img src="assets/img/slide3.png" alt="">
						</div>
					  </div>
					</div>		
				</div>
				
			</div>
		</div>
	</div>
	<div id="footer">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-lg-offset-3">
					<p class="copyright">Copyright &copy; 2014 - Restaurant Ecom</p>
			</div>
		</div>		
	</div>	
	</div>

    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </body>
</html>
