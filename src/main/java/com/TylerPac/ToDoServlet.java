package com.TylerPac;

import javax.servlet.*;
import javax.servlet.http.*;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;

public class ToDoServlet extends HttpServlet {
    private static final SessionFactory factory = new Configuration()
            .configure()
            .addAnnotatedClass(ToDoList.class)
            .buildSessionFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = factory.openSession();
        List<ToDoList> todos = session.createQuery("FROM ToDoList", ToDoList.class).list();
        session.close();

        request.setAttribute("todos", todos);
        request.getRequestDispatcher("todoList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        if ("add".equals(action)) {
            String name = request.getParameter("todoName");
            ToDoList newItem = new ToDoList();
            newItem.setTDL_NAME(name);
            newItem.setTDL_DONE(false);
            session.persist(newItem);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("todoId"));
            ToDoList item = session.get(ToDoList.class, id);
            if (item != null) {
                session.remove(item);
            }
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("todoId"));
            ToDoList item = session.get(ToDoList.class, id);
            if (item != null) {
                item.setTDL_DONE(true);
                session.merge(item);
            }
        }

        transaction.commit();
        session.close();

        response.sendRedirect("todos");
    }
}
