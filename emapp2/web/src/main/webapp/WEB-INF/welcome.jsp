<jsp:useBean id="empList" scope="session" type="java.util.ArrayList"/>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of Employees</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link type="text/css" rel="stylesheet" href="css/cssWelcome.css">
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
                 <h1>List of Employees</h1>
            </nav>
        <c:if test="${!empty errCheck}">
        <div class="alert alert-danger" role="alert">
            <c:out value="${errCheck}" />
        </div>
        </c:if>
        <div class="container">

            <!--Logout button-->
            <form name="welcome" action="${pageContext.request.contextPath}/se.m1.emapp.controller">
            <!--Pop up for the logout-->
                <div class="modal bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <div class="modal-header"><h4>Logout <i class="fa fa-lock"></i></h4></div>
                            <div class="modal-body"><i class="fa fa-question-circle"></i> Are you sure you want to log-off?</div>
                            <div class="modal-footer">
                                <input type='submit' class="btn btn-primary btn-block" name="action" value="LogOut"/>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <form method="get" action="${pageContext.request.contextPath}/se.m1.emapp.controller">
                <div class="table-responsive-sm">
                    <table class="table">
                        <tr>
                            <td><b>SEL</b></td>
                            <td><b>FIRSTNAME</b></td>
                            <td><b>LASTNAME</b></td>
                        </tr>

                        <c:forEach items="${empList}" var="employee">
                        
                        <tr>
                            <c:if test="${user.login.equals('admin')&& user.pwd.equals('admin')}">
                                <td>
                                    <div class="form-group form-check">
                                        <input type="radio" class="form-check-input" name="check" value= ${employee.id}>
                                    </div>
                            </td>
                            </c:if>
                            <td> ${employee.firstName} </td>
                            <td> ${employee.lastName} </td>
                            
                        </tr>
                      
                        </c:forEach>
                        
                        
                    </table>
                    <c:if test="${empList.size()==0}">
                      <div class="alert alert-danger" role="alert">
                           The company has no employee!
                      </div>
                    </c:if>
                    
                </div>
                    

                <c:if test="${user.login.equals('admin')&& user.pwd.equals('admin')}">
                <div>
                    <input type='submit' class="btn btn-primary" name="action" value="Delete"/>
                    <input type='submit' class="btn btn-primary" name="action" value="Details"/>
                    <input type='submit' class="btn btn-primary" name="action" value="Add"/>
                </div>
                </c:if>
            </form>
        </div>
                    
    </body>
</html>
