package com.example.beans;

import java.io.Serial;
import java.io.Serializable;

public class Statistics implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private int mFailed;
  private int mPassed;
  private int mExcellent;

  public Statistics() {
  }

  public Statistics(String[] statisticsInArr) {
    if (statisticsInArr.length != 3) {
      throw new IllegalArgumentException("Length of array with stats " +
              "should be 3");
    }
    mFailed = Integer.parseInt(statisticsInArr[0]);
    mPassed = Integer.parseInt(statisticsInArr[1]);
    mExcellent = Integer.parseInt(statisticsInArr[2]);
  }

  public int getFailed() {
    return mFailed;
  }

  public void setFailed(int failed) {
    mFailed = failed;
  }

  public int getPassed() {
    return mPassed;
  }

  public void setPassed(int passed) {
    mPassed = passed;
  }

  public int getExcellent() {
    return mExcellent;
  }

  public void setExcellent(int excellent) {
    mExcellent = excellent;
  }
}
