package com.example.dao;

import com.example.beans.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseManager {
  private static final String URL =
          "jdbc:postgresql://localhost:5432/postgres";
  private static final String USER = "postgres";
  private static final String PASSWORD = "7654321a";
  private boolean mIsConnectionValid = true;
  private Connection mConnection;
  private Statement mStatement;
  private final ReentrantLock mLock = new ReentrantLock();

  private void closeStatementAndUnlock() {
    try {
      if (mStatement != null) {
        mStatement.close();
      }
    } catch (SQLException ignored) {
    }
    mLock.unlock();
  }

  public void init() {
    try {
      if (mConnection == null) {
        mConnection = DriverManager.getConnection(URL, USER, PASSWORD);
        mConnection.setAutoCommit(false);
        mStatement = mConnection.createStatement();
        mStatement.execute(SQLQueries.CREATE_FUNCS_QUERY);
        mConnection.commit();
      }
    } catch (SQLException e) {
      mIsConnectionValid = false;
    } finally {
      try {
        if (mStatement != null) {
          mStatement.close();
        }
      } catch (SQLException ignored) {
      }
    }
  }

  public boolean isConnectionValid() {
    return mIsConnectionValid;
  }

  public boolean rollback() {
    try {
      mLock.lock();
      mConnection.rollback();
    } catch (SQLException ex) {
      return false;
    } finally {
      mLock.unlock();
    }
    return true;
  }

  public ArrayList<Student> getListOfStudents() throws SQLException {
    ResultSet resultSet;
    ArrayList<Student> res = new ArrayList<>();
    try {
      mLock.lock();
      mStatement = mConnection.createStatement();
      resultSet = mStatement.executeQuery(
              SQLQueries.GET_LIST_OF_STUDENTS_QUERY);
      mConnection.commit();
      while (resultSet.next()) {
        res.add(new Student(resultSet.getString("name"),
                resultSet.getString("surname"),
                Short.parseShort(resultSet.getString("group"))));
      }
      return res;
    } finally {
      closeStatementAndUnlock();
    }
  }

  public ArrayList<String> getCurrSubjects()
          throws SQLException {
    ResultSet resultSet;
    ArrayList<String> res = new ArrayList<>();
    try {
      mLock.lock();
      mStatement = mConnection.createStatement();
      resultSet = mStatement.executeQuery(
              SQLQueries.GET_LIST_OF_SUBJECTS_QUERY);
      mConnection.commit();
      while (resultSet.next()) {
        res.add(resultSet.getString("subject_name"));
      }
    } finally {
      closeStatementAndUnlock();
    }
    return res;
  }

  public void addNewStudent(Student newStudent) throws SQLException {
    try {
      ResultSet resultSet;
      mLock.lock();
      mStatement = mConnection.prepareStatement(
              SQLQueries.INSERT_NEW_STUDENT_QUERY);
      ((PreparedStatement) mStatement).setString(1, newStudent.getSurname());
      ((PreparedStatement) mStatement).setString(2, newStudent.getName());
      ((PreparedStatement) mStatement).setShort(3, newStudent.getGroup());
      ((PreparedStatement) mStatement).execute();
      resultSet = mStatement.getResultSet();
      resultSet.next();
      long studentId = resultSet.getLong("student_id");
      StringBuilder gradesInArrFormat = new StringBuilder("[");
      var grades = newStudent.getGrades();
      for (int i = 0; i < grades.size() - 1; ++i) {
        gradesInArrFormat.append(grades.get(i)).append(',');
      }
      gradesInArrFormat.append(grades.get(grades.size() - 1)).append(']');
      mStatement = mConnection.createStatement();
      mStatement.execute(SQLQueries.insertStudentsGradesQuery(
              gradesInArrFormat.toString(), studentId));
      mConnection.commit();
    } finally {
      closeStatementAndUnlock();
    }
  }

  public boolean removeStudent(Student student) throws SQLException {
    ResultSet resultSet;
    try {
      mLock.lock();
      mStatement = mConnection.createStatement();
      resultSet = mStatement.executeQuery(SQLQueries.removeStudentQuery(
              student.getSurname(), student.getName(),
              String.valueOf(student.getGroup())));
      mConnection.commit();
      resultSet.next();
      return resultSet.getBoolean(1);
    } finally {
      closeStatementAndUnlock();
    }
  }

  public String[] getSessionResults() throws SQLException {
    String[] sessionResults;
    ResultSet resultSet;
    String resultsRecord;
    try {
      mLock.lock();
      mStatement = mConnection.createStatement();
      mStatement.execute(SQLQueries.CALL_FUNC_QUERY);
      mConnection.commit();
      resultSet = mStatement.getResultSet();
      resultSet.next();
      resultsRecord = resultSet.getString("get_session_results");
    } finally {
      closeStatementAndUnlock();
    }
    sessionResults = resultsRecord.replaceAll("[()]", "").
            split(",");
    return sessionResults;
  }
}
