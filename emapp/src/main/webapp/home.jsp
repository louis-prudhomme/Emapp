<%-- 
    Document   : home
    Created on : 15 oct. 2019, 08:55:32
    Author     : melaniemarques
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        
    </head>
    <body>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <c:if test="${!empty errKey}">
        <div class="alert alert-danger" role="alert">       
            <c:out value="${errKey}" />       
        </div>
    </c:if>
    <div class="container"> 
  <form name="registering" action="controller">
   
  <div class="form-group row">
    <label for="exampleInputEmail1" class="col-sm-2 col-form-label">Login : </label>
    <div class="col-sm-10">
    <input type='text' name='loginField' placeholder="Login">
    </div>
  </div>
  <div class="form-group row">
    <label for="exampleInputPassword1" class="col-sm-2 col-form-label">Password : </label>
    <div class="col-sm-10">
    <input type='text' name='pwdField' placeholder="Password">
    </div>
  </div>     
      <div class="form-group">
        <input type='submit' class="btn btn-primary center-block" name='action' value='Login'>         
      </div>
</form>
</body>
     
</div> 
</html>
