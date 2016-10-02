package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * Stopwatch is a thread-safe class which implements on IStopwatch interface. It implements
 * following functionalities of a Stopwatch: start, stop, lap, reset
 *
 * @author Nikita Amartya
 *
 */

public class Stopwatch implements IStopwatch {

  private final String watchID;
  private long         startTime;
  private boolean      isRunning;
  private List<Long>   lapTimes;
  private long         previousLapTime;
  private Object       lock;

  /**
   * Constructs a Stopwatch object with watchID initialized to the string passed as parameter
   *
   * @param id
   *          as string
   */
  public Stopwatch(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Stopwatch id cannot be null or empty");
    }
    else {
      watchID = id;
      startTime = 0;
      isRunning = false;
      lapTimes = new ArrayList<Long>();
      previousLapTime = 0;
      lock = new Object();
    }
  }

  @Override
  public String getId() {
    return watchID;
  }

  @Override
  public void start() {
    synchronized (lock) {
      if (isRunning) {
        throw new IllegalStateException("Stopwatch is already running.");
      }
      startTime = System.nanoTime();
      isRunning = true;
    }
  }

  @Override
  public void lap() {
    synchronized (lock) {
      Long currentTime = System.nanoTime();
      if (!isRunning) {
        throw new IllegalStateException("Stopwatch is not running.");
      }
      if (previousLapTime == 0) {
        lapTimes.add(currentTime - startTime);
      }
      else {
        lapTimes.add(currentTime - previousLapTime);
      }
      previousLapTime = currentTime;
    }
  }

  @Override
  public void stop() {
    lap();
    synchronized (lock) {
      isRunning = false;
    }
  }

  @Override
  public void reset() {
    synchronized (lock) {
      startTime = 0;
      isRunning = false;
      lapTimes.clear();
      previousLapTime = 0;
    }
  }

  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      List<Long> lapTimesInMilliSec = new ArrayList<Long>();
      for (Long lap : lapTimes) {
        lapTimesInMilliSec.add(convertToMillis(lap));
      }
      return lapTimesInMilliSec;
    }
  }

  /**
   * Takes time in nanoseconds as input and returns time in milliseconds
   *
   * @param timeInNanoSec
   * @return time in milliseconds
   */
  private Long convertToMillis(Long timeInNanoSec) {
    return TimeUnit.MILLISECONDS.convert(timeInNanoSec, TimeUnit.NANOSECONDS);
  }

  @Override
  public String toString() {
    synchronized (lock) {
      return "Stopwatch id=" + watchID + ", startTime=" + startTime + ", lapTimes=" + lapTimes;
    }
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Stopwatch)) {
      return false;
    }
    Stopwatch anotherWatch = (Stopwatch) obj;
    if (watchID.equals(anotherWatch.getId())) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = 19;
    result = 31 * result + watchID.hashCode();
    return result;
  }

}
