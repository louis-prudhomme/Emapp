<%-- 
    Document   : welcome
    Created on : 15 oct. 2019, 18:16:25
    Author     : melaniemarques
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List of Employees</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <h1>List of Employees</h1>
        <form name="welcome" action="controller">
            <div class="table-responsive-sm">
                <table class="table">
            
                <tr>
                    <td><b>SEL</b></td>
                    <td><b>NAME</b></td>
                    <td><b>FIRSTNAME</b></td>
                    <td><b>HOME PHONE</b></td>
                    <td><b>MOBILE PHONE</b></td>
                    <td><b>WORK PHONE</b></td>
                    <td><b>ADDRESS</b></td>
                    <td><b>POSTAL CODE</b></td>
                    <td><b>CITY</b></td>
                    <td><b>EMAIL</b></td>
                </tr>

                <c:forEach items="${empList}" var="employee">
                    <c:if test="${!employee.name.equals('admin')}">
        
                        <tr> 
                            <td> 
                            <div class="form-group form-check">
                                <input type="checkbox" class="form-check-input" id="check">   
                            </div>                           
                            </td>
                            <td>  ${employee.name}</td>
                            <td>  ${employee.firstname}    </td>
                            <td>  ${employee.homePhone}    </td>
                            <td>  ${employee.mobilePhone}  </td>
                            <td>  ${employee.workPhone}  </td>
                            <td>  ${employee.adress}  </td>
                            <td>  ${employee.postalCode}  </td>
                            <td>  ${employee.city}  </td>
                            <td>  ${employee.email}  </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
            </div>
            <div class="container">
                <input type='submit'class="btn btn-primary" name="action" value="Delete"/>
                <input type='submit' class="btn btn-primary" name="action" value="Details"/>
                <input type='submit' class="btn btn-primary" name="action" value="Add"/>
            </div>
            </form>
            </br>
            </br>
            
            
        
    </body>
</html>
