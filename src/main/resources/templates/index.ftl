<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>博瑞星云 | 校园服务器</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${rc.contextPath}/plugins/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${rc.contextPath}/plugins/Font-Awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${rc.contextPath}/plugins/ionicons/css/ionicons.min.css">

    <!-- Theme style -->
    <link rel="stylesheet" href="${rc.contextPath}/plugins/adminlte/css/AdminLTE.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${rc.contextPath}/plugins/adminlte/css/skins/_all-skins.min.css">

    <!-- jQuery-->
    <script src="${rc.contextPath}/plugins/jQuery/jquery.min.js"></script>
	
    <script>
        $(document).ready(function() {
            $("#header").load("${rc.contextPath}/layout/header.ftl",function(response,status,xhr){
                //$("#welcome").addClass("active");
            });
            $("#footer").load("${rc.contextPath}/layout/footer.ftl");
            
        });
    </script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<input type="hidden" id="baseUrl" value="${rc.contextPath}" />
<div class="wrapper">
<div id="header">
		
</div>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        Welcome
        <small>BRXY School Server</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"> </i> 首页</a></li>
        <!--li class="active">Welcome</li-->
    </ol>
</section>

<!-- Main content -->
<section class="content">

<!-- Small boxes (Stat box) -->
<div class="row">
    <div class="col-lg-4 col-xs-6">
        <!-- small box -->
        <div class="small-box bg-aqua">
            <div class="inner">
                <h3>150</h3>

                <p>激活设备</p>
            </div>
            <div class="icon">
                <i class="fa fa-tablet"> </i>
            </div>
            <a href="#" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"> </i></a>
        </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-4 col-xs-6">
        <!-- small box -->
        <div class="small-box bg-green">
            <div class="inner">
                <h3>53<sup style="font-size: 20px">%</sup></h3>

                <p>设备监控</p>
            </div>
            <div class="icon">
                <i class="fa fa-laptop"> </i>
            </div>
            <a href="#" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"> </i></a>
        </div>
    </div>
    <!-- ./col -->
    <div class="col-lg-4 col-xs-6">
        <!-- small box -->
        <div class="small-box bg-yellow">
            <div class="inner">
                <h3>44</h3>

                <p>计划任务</p>
            </div>
            <div class="icon">
                <i class="fa fa-calendar"> </i>
            </div>
            <a href="#" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"> </i></a>
        </div>
    </div>
    <!-- ./col -->

    <div class="col-lg-4 col-xs-6">
        <!-- small box -->
        <div class="small-box bg-red">
            <div class="inner">
                <h3>65</h3>

                <p>文件上传</p>
            </div>
            <div class="icon">
                <i class="fa fa-cloud-upload"> </i>
            </div>
            <a href="#" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"> </i></a>
        </div>
    </div>
    <!-- ./col -->

    <div class="col-lg-4 col-xs-6">
        <!-- small box -->
        <div class="small-box bg-purple">
            <div class="inner">
                <h3>65</h3>

                <p>RFID管理</p>
            </div>
            <div class="icon">
                <i class="fa fa-credit-card"> </i>
            </div>
            <a href="#" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"> </i></a>
        </div>
    </div>
    <!-- ./col -->

    <div class="col-lg-4 col-xs-6">
        <!-- small box -->
        <div class="small-box bg-maroon">
            <div class="inner">
                <h3>65</h3>

                <p>日志查看</p>
            </div>
            <div class="icon">
                <i class="fa fa-files-o"> </i>
            </div>
            <a href="#" class="small-box-footer">更多信息 <i class="fa fa-arrow-circle-right"> </i></a>
        </div>
    </div>
    <!-- ./col -->
</div>
<!-- /.row -->




</section>
<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<footer class="main-footer" id="footer">

</footer>



</div>
<!-- ./wrapper -->


<!-- Bootstrap 3.3.6 -->
<script src="${rc.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- FastClick -->
<!--<script src="plugins/fastclick/fastclick.js"></script>-->
<!-- AdminLTE App -->
<script src="${rc.contextPath}/plugins/adminlte/js/app.min.js"></script>
<!-- Sparkline -->
<!--<script src="plugins/sparkline/jquery.sparkline.min.js"></script>-->

<!-- SlimScroll 1.3.0 -->
<!--<script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>-->


<!-- AdminLTE for demo purposes -->
<!--<script src="js/demo.js"></script>-->


</body>
</html>
