package com.example.beans;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
  @Serial
  private static final long serialVersionUID = 2L;
  private String mName;
  private String mSurname;
  private short mGroup;
  List<Short> mGrades = null;

  public Student() {
    mName = "";
    mSurname = "";
    mGroup = 1;
  }

  public Student(String name, String surname, short group) {
    mName = name;
    mSurname = surname;
    mGroup = group;
    if (group <= 0) {
      throw new IllegalArgumentException("Group should be positive");
    }
  }

  public Student(String name, String surname, short group,
                 List<Short> grades) {
    this(name, surname, group);
    for (short grade : grades) {
      if (grade <= 0 || grade > 10) {
        throw new IllegalArgumentException("Grade should de be a value " +
                "from 1 to 10");
      }
    }
    mGrades = grades;
  }

  public Student(String name, String surname, short group,
                 String gradesThroughSpace) {
    this(name, surname, group);
    String[] gradesAsStrings = gradesThroughSpace.split(" ");
    mGrades = new ArrayList<>(gradesAsStrings.length);
    for (String gradeStr : gradesAsStrings) {
      short grade = Short.parseShort(gradeStr);
      if (grade <= 0 || grade > 10) {
        throw new IllegalArgumentException("Grade should de be a value " +
                "from 1 to 10");
      }
      mGrades.add(Short.parseShort(gradeStr));
    }
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getSurname() {
    return mSurname;
  }

  public void setSurname(String surname) {
    mSurname = surname;
  }

  public short getGroup() {
    return mGroup;
  }

  public void setGroup(short group) {
    mGroup = group;
  }

  public List<Short> getGrades() {
    return mGrades;
  }

  public void setGrades(List<Short> grades) {
    mGrades = grades;
  }
}
