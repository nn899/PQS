package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects. It maintains
 * references to all created IStopwatch objects and provides a convenient method for getting a list
 * of those objects.
 *
 */
public class StopwatchFactory {

  private static List<IStopwatch> watcheList = new ArrayList<IStopwatch>();
  private static final Object     lock       = new Object();

  /**
   * Creates and returns a new IStopwatch object
   *
   * @param id
   *          The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException
   *           if <code>id</code> is empty, null, or already taken.
   */
  public static IStopwatch getStopwatch(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Stopwatch id cannot be null or empty");
    }
    synchronized (watcheList) {
      if (ifExists(id)) {
        throw new IllegalArgumentException("A Stopwatch with this id already exists");
      }
      IStopwatch stopwatchObject = new Stopwatch(id);
      watcheList.add(stopwatchObject);
      return stopwatchObject;
    }
  }

  /**
   * Returns a list of all created stopwatches
   *
   * @return a List of all creates IStopwatch objects. Returns an empty list if no IStopwatches
   *         have been created.
   */
  public static List<IStopwatch> getStopwatches() {
    synchronized (lock) {
      return new ArrayList<IStopwatch>(watcheList);
    }
  }

  /**
   * This method checks if a Stopwatch objects with a given ID already exists.
   *
   * @param id
   *          The ID we need to check against
   * @return boolean true if a Stopwatch exists with same ID, else return false
   */
  private static boolean ifExists(String id) {
    for (IStopwatch watch : watcheList) {
      if (watch.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }

}
