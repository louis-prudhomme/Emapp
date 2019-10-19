<%-- 
    Document   : Add
    Created on : 17 oct. 2019, 17:48:05
    Author     : melaniemarques
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link type="text/css" rel="stylesheet" href="cssWelcome.css">
    </head>
    <body>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        
        
        <nav class="navbar navbar-expand-sm bg-primary navbar-dark">
            <button id = "logOutButton" type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target=".bs-example-modal-sm">
                <span class="glyphicon glyphicon-log-out"></span> Log out
            </button>
            <h2> Details of employee: ${employeeChecked.getName()} ${employeeChecked.getFirstname()}</h2>
        </nav>
        </br>
        <form>
            <div class="modal bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-sm">
                <div class="modal-content">
      <div class="modal-header"><h4>Logout <i class="fa fa-lock"></i></h4></div>
      <div class="modal-body"><i class="fa fa-question-circle"></i> Are you sure you want to log-off?</div>
      
      <div class="modal-footer" action="controller">
          <input type='submit' class="btn btn-primary btn-block" name="action" value="LogOut"/>
      </div>
    </div>
  </div>
</div>
            <div class="form-group row">
                <label for="InputNom" class="col-sm-2 col-form-label">Nom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="inputNom" placeholder="Nom" value="${employeeChecked.getName()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPrenom" class="col-sm-2 col-form-label">Prénom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="inputPrenom" placeholder="Prénom" value="${employeeChecked.getFirstname()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputTelDom" class="col-sm-2 col-form-label">Tél dom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="inputTelDom" placeholder="Tél dom" value="${employeeChecked.getHomePhone()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputTelMob" class="col-sm-2 col-form-label">Tél mob</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="inputTelMob" placeholder="Tél Mob" value="${employeeChecked.getMobilePhone()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputTelPro" class="col-sm-2 col-form-label">Tél pro</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="inputTelPro" placeholder="Tél pro" value="${employeeChecked.getWorkPhone()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputAdresse" class="col-sm-2 col-form-label">Adresse</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="inputAdresse" placeholder="Adresse" value="${employeeChecked.getAdress()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputCodePostal" class="col-sm-2 col-form-label">Code Postal</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="inputCodePostal" placeholder="Code Postal" value="${employeeChecked.getPostalCode()}">
                </div>
            </div>
            <div class="form-row">                
                <div class="form-group col-sm-2">
                   <label for="inputVille" class="">Ville</label> 
                </div>
                <div class="form-group col-md-3"> 
                   <input type="text" class="form-control" name="inputVille" placeholder="Ville" value="${employeeChecked.getCity()}"> 
                </div>
                <div class="form-group col-sm-3">
                   <label for="inputAdresseMail" class="center-block">Adresse e-mail</label>
                </div>
                <div class="form-group col-md-3">
                   <input type="text" class="form-control" name="inputAdresseMail" placeholder="adresse e-mail" value="${employeeChecked.getEmail()}">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-sm-10">
                </div>
                <div class="form-group col-sm-2">
                   <input type='submit' class="btn btn-primary" name='action' value='Save'> 
                <input type='submit' class="btn btn-light" name='action' value='Cancel'> 
                </div>
                
            </div>
        </form>
    </body>
</html>
