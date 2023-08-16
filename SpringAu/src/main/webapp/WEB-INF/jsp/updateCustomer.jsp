<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Customer</title>
</head>
<body>
    <h2>Update Customer</h2>
    <form action="/customers/update/${customer.uuid}" method="post">
        <label>First Name:</label>
        <input type="text" name="first_name" value="${customer.first_name}" required><br>

        <label>Last Name:</label>
        <input type="text" name="last_name" value="${customer.last_name}"><br>

        <!-- Add other input fields here -->

        <button type="submit">Update</button>
    </form>
</body>
</html>
