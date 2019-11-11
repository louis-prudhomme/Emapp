<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>
<jsp:useBean id="firstDigit" scope="request" type="java.lang.Character"/>
<jsp:useBean id="secondDigit" scope="request" type="java.lang.Character"/>
<jsp:useBean id="thirdDigit" scope="request" type="java.lang.Character"/>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <link type="text/css" rel="stylesheet" href="css/darkTheme.css">
        <script type="text/javascript" src="js/darkTheme.js"></script>
    </head>
    <link href="css/cssErrorPage.css" rel='stylesheet' type='text/css'>
    <body onload="loadPage2()">
        <!-- Error Page -->
            <div class="error">
                <div class="container-fluid">
                    <div class="col-xs-12 ground-color text-center">
                        <div class="container-error-404">
                            <div class="clip"><div class="shadow"><span class="digit thirdDigit"></span></div></div>
                            <div class="clip"><div class="shadow"><span class="digit secondDigit"></span></div></div>
                            <div class="clip"><div class="shadow"><span class="digit firstDigit"></span></div></div>
                            <div class="msg">OH!<span class="triangle"></span></div>
                        </div>
                    </div>
                    <h2 class="h1">${errorMessage}</h2>
                </div>
            </div>
        </div>
        <form action="${pageContext.request.contextPath}/se.m1.emapp.controller">
            <div class="error-actions">
                <input type="submit" class="btn btn-primary btn-lg" name="action" value="Home">      
            </div>
        </form>
        <!-- Error Page JS-->
        <script type="text/javascript">
             function randomNum()
            {
                "use strict";
                return Math.floor(Math.random() * 9)+1;
            }

            var loop1,loop2,loop3,time=30, i=0, number, selector3 = document.querySelector('.thirdDigit'), selector2 = document.querySelector('.secondDigit'),
                selector1 = document.querySelector('.firstDigit');
            loop3 = setInterval(function()
            {
              "use strict";
                if(i > 40)
                {
                    clearInterval(loop3);
                    selector3.textContent = ${firstDigit};
                }else
                {
                    selector3.textContent = randomNum();
                    i++;
                }
            }, time);
            loop2 = setInterval(function()
            {
              "use strict";
                if(i > 80)
                {
                    clearInterval(loop2);
                    selector2.textContent = ${secondDigit};
                }else
                {
                    selector2.textContent = randomNum();
                    i++;
                }
            }, time);
            loop1 = setInterval(function()
            {
              "use strict";
                if(i > 100)
                {
                    clearInterval(loop1);
                    selector1.textContent = ${thirdDigit};
                }else
                {
                    selector1.textContent = randomNum();
                    i++;
                }
            }, time);
        </script>
    </body>
</html>
