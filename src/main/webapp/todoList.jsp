<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.TylerPac.ToDoList" %>
<%
    @SuppressWarnings("unchecked")
    List<ToDoList> todos = (List<ToDoList>) request.getAttribute("todos");
%>

<html>
<head>
    <title>To-Do List</title>
</head>
<body>
<h2>My To-Do List</h2>

<!-- Show List -->
<ul>
    <% for (ToDoList todo : todos) { %>
    <li>
        <%= todo.getTDL_NAME() %>
        [<%= todo.isTDL_DONE() ? "Done" : "Pending" %>]
        <form action="todos" method="post" style="display:inline;">
            <input type="hidden" name="todoId" value="<%= todo.getTDL_ID() %>">
            <button type="submit" name="action" value="update">Mark Done</button>
            <button type="submit" name="action" value="delete">Delete</button>
        </form>
    </li>
    <% } %>
</ul>

<!-- Add Item Form -->
<form action="todos" method="post">
    <label>
        <input type="text" name="todoName" required placeholder="Enter new to do list item">
    </label>
    <button type="submit" name="action" value="add">Add</button>
</form>
</body>
</html>
