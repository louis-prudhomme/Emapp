<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link type="text/css" rel="stylesheet" href="css/CssCode.css">
        <link type="text/css" rel="stylesheet" href="css/darkTheme.css">
        <script type="text/javascript" src="js/darkTheme.js"></script>
    </head>
    <body onload="loadPage2()">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
         
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <c:if test="${!empty errKey}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${errKey}" />
            </div>
        </c:if>
        
        <nav class="navbar navbar-fixed-left navbar-minimal animate" role="navigation">
		<div class="navbar-toggler animate">
			<span class="menu-icon"></span>
		</div>
		<ul class="navbar-menu animate">
			<li>
                             <a href="#blog" class="animate" onclick="setLightMode()">
                                 <span class="desc animate"> Light </span>
				<span class="glyphicon glyphicon-flash"></span>
                             </a>
			</li>
			<li>
                            <a href="#blog" class="animate" onclick="setDarkMode()">
				<span class="desc animate"> Dark </span>
				<span class="glyphicon glyphicon-certificate"></span></span>
                            </a>
			</li>
		</ul>
	</nav>
        
        <div class="wrapper fadeInDown">
            <div id="formContent">

                <div class="fadeIn first">
                    <img class="resize" src="https://pngimage.net/wp-content/uploads/2018/06/logo-user-png-6.png" id="icon" alt="User Icon"  />
                </div>

                <form name="registering" action="${pageContext.request.contextPath}/se.m1.emapp.controller">
                    <input type='text' name="loginField" class="fadeIn second" placeholder="Login">
                    <input type='text' name="pwdField" class="fadeIn third" placeholder="Password">
                    <input type='submit' class="fadeIn fourth" name='action' value='Login'>
                </form>
            </div>
        </div>
                    
                    
    <script type="text/javascript">
                    $(function () {
    
    $('.navbar-toggler').on('click', function(event) {
		event.preventDefault();
		$(this).closest('.navbar-minimal').toggleClass('open');
	});
});</script>
    </body>
</html>
