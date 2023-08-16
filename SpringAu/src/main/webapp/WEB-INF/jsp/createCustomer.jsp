<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Customer</title>
</head>
<body>
    <h2>Create Customer</h2>
    <form action="/customers/create" method="post">
        <label>First Name:</label>
        <input type="text" name="first_name" required><br>

        <label>Last Name:</label>
        <input type="text" name="last_name"><br>

        <!-- Add other input fields here -->

        <button type="submit">Create</button>
    </form>

    <!-- Display success or error messages -->
    <div>
        <p>${message}</p>
    </div>
</body>
</html>
