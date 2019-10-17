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
    </head>
    <body>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <h1 class="display-4">Employee details </h1>
        <form>
            <div class="form-group row">
                <label for="InputNom" class="col-sm-2 col-form-label">Nom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputNom" placeholder="Nom">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPrenom" class="col-sm-2 col-form-label">Prénom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputPrenom" placeholder="Prénom">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputTelDom" class="col-sm-2 col-form-label">Tél dom</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputTelDom" placeholder="Tél dom">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputTelMob" class="col-sm-2 col-form-label">Tél mob</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputTelMob" placeholder="Tél Mob">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputTelPro" class="col-sm-2 col-form-label">Tél pro</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputTelPro" placeholder="Tél pro">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputAdresse" class="col-sm-2 col-form-label">Adresse</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputAdresse" placeholder="Adresse">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputCodePostal" class="col-sm-2 col-form-label">Code Postal</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputCodePostal" placeholder="Code Postal">
                </div>
            </div>
            <div class="form-row">                
                <div class="form-group col-sm-2">
                   <label for="inputVille" class="">Ville</label> 
                </div>
                <div class="form-group col-md-3"> 
                   <input type="text" class="form-control" id="inputVille" placeholder="Ville"> 
                </div>
                <div class="form-group col-sm-3">
                   <label for="inputAdresseMail" class="center-block">Adresse e-mail</label>
                </div>
                <div class="form-group col-md-3">
                   <input type="text" class="form-control" id="inputAdresseMail" placeholder="adresse e-mail">
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
