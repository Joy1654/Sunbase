<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer List</title>
</head>
<body>
    <h2>Customer List</h2>

    <table>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <!-- Add other table headers here -->
        </tr>

        <c:forEach items="${customers}" var="customer">
            <tr>
                <td>${customer.first_name}</td>
                <td>${customer.last_name}</td>
                <!-- Display other customer attributes -->
            </tr>
        </c:forEach>
    </table>
</body>
</html>
