package pt.jkaiui.tools.log;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MyFilter implements Filter {

  private Level _level;

  /**
   * Contructor
   * @param level log handler's level
   */
  public MyFilter(Level level) {
    super();
    this._level = level;
  }

    @Override
  public synchronized boolean isLoggable(LogRecord record) {
    // return true if the record should be logged;
    // false otherwise.
    if (this._level == Level.ALL) {
      return true;
    } else if (this._level == Level.OFF) {
      return false;
    } else if (record.getLevel().intValue() >= this._level.intValue()) {
      return true;
    } else {
      return false;
    }
  }

}
