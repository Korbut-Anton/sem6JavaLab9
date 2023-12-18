package com.example.servlets;

import com.example.beans.Statistics;
import com.example.beans.Student;
import com.example.dao.DatabaseManager;
import com.example.helpers.ButtonNames;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student-manager")
public class StudentManager extends HttpServlet {
  private static final String MAIN_PAGE_NAME = "/main_page.jsp";
  private static final String STATS_PAGE_NAME = "/statistics.jsp";
  private static final String ADD_PAGE_NAME = "/add_student_page.jsp";
  private static final String ADD_DONE_PAGE_NAME =
          "/successful_adding_page.jsp";
  private static final String REMOVE_PAGE_NAME = "/remove_student_page.jsp";
  private static final String REMOVE_DONE_PAGE_NAME =
          "/successful_removal_page.jsp";
  private final DatabaseManager mDatabaseManager = new DatabaseManager();

  @Override
  public void init() {
    mDatabaseManager.init();
  }

  @Override
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException,
          IOException {
    ServletContext servletContext = getServletContext();

    try {
      switch (request.getParameter("button")) {
        case ButtonNames.MAIN_PAGE_STR -> servletContext.getRequestDispatcher(
                MAIN_PAGE_NAME).forward(request, response);
        case ButtonNames.UPDATE_STATS_STR -> {
          request.setAttribute("statistics", new Statistics(
                  mDatabaseManager.getSessionResults()));
          servletContext.getRequestDispatcher(STATS_PAGE_NAME).forward(request,
                  response);
        }
        case ButtonNames.ADD_STUDENT_STR -> {
          request.setAttribute("subjects", mDatabaseManager.getCurrSubjects());
          servletContext.getRequestDispatcher(ADD_PAGE_NAME).forward(request,
                  response);
        }
        case ButtonNames.ADD_STR -> {
          try {
            mDatabaseManager.addNewStudent(new Student(
                    request.getParameter("name"),
                    request.getParameter("surname"),
                    Short.parseShort(request.getParameter("group")),
                    request.getParameter("grades")));
          } catch (IllegalArgumentException e) {
            request.setAttribute("invalidInput", true);
            request.setAttribute("subjects",
                    mDatabaseManager.getCurrSubjects());
            servletContext.getRequestDispatcher(ADD_PAGE_NAME).forward(request,
                    response);
            return;
          }
          servletContext.getRequestDispatcher(
                  ADD_DONE_PAGE_NAME).forward(request, response);
        }
        case ButtonNames.REMOVE_STUDENT_STR -> {
          request.setAttribute("students",
                  mDatabaseManager.getListOfStudents());
          servletContext.getRequestDispatcher(
                  REMOVE_PAGE_NAME).forward(request, response);
        }
        case ButtonNames.REMOVE_STR -> {
          boolean removed;
          try {
            removed = mDatabaseManager.removeStudent(new Student(
                    request.getParameter("name"),
                    request.getParameter("surname"),
                    Short.parseShort(request.getParameter("group"))));
          } catch (IllegalArgumentException e) {
            request.setAttribute("invalidInput", true);
            request.setAttribute("students",
                    mDatabaseManager.getListOfStudents());
            servletContext.getRequestDispatcher(
                    REMOVE_PAGE_NAME).forward(request, response);
            return;
          }
          if (removed) {
            servletContext.getRequestDispatcher(
                    REMOVE_DONE_PAGE_NAME).forward(request, response);
          } else {
            request.setAttribute("students",
                    mDatabaseManager.getListOfStudents());
            request.setAttribute("removed", false);
            servletContext.getRequestDispatcher(
                    REMOVE_PAGE_NAME).forward(request, response);
          }
        }
      }
    } catch (SQLException ignored) {
    }
  }
}
