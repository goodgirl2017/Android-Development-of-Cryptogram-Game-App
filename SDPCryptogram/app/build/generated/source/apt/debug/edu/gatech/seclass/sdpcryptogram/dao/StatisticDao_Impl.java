package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StatisticDao_Impl implements StatisticDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfStatistic;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfStatistic;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public StatisticDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStatistic = new EntityInsertionAdapter<Statistic>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `statistic_table`(`_id`,`s_username`,`s_puzzlename`,`attempts`,`solved`,`completedate`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Statistic value) {
        stmt.bindLong(1, value._id);
        if (value.getUsername() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUsername());
        }
        if (value.getPuzzlename() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPuzzlename());
        }
        if (value.getAttempts() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getAttempts());
        }
        final Integer _tmp;
        _tmp = value.getSolved() == null ? null : (value.getSolved() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.getCompletedate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getCompletedate());
        }
      }
    };
    this.__updateAdapterOfStatistic = new EntityDeletionOrUpdateAdapter<Statistic>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `statistic_table` SET `_id` = ?,`s_username` = ?,`s_puzzlename` = ?,`attempts` = ?,`solved` = ?,`completedate` = ? WHERE `_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Statistic value) {
        stmt.bindLong(1, value._id);
        if (value.getUsername() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUsername());
        }
        if (value.getPuzzlename() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPuzzlename());
        }
        if (value.getAttempts() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getAttempts());
        }
        final Integer _tmp;
        _tmp = value.getSolved() == null ? null : (value.getSolved() ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        if (value.getCompletedate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, value.getCompletedate());
        }
        stmt.bindLong(7, value._id);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM statistic_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(Statistic statistic) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfStatistic.insert(statistic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Statistic statistic) {
    __db.beginTransaction();
    try {
      __updateAdapterOfStatistic.handle(statistic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Statistic>> getAllStatistics() {
    final String _sql = "SELECT * from statistic_table ORDER BY s_username ASC, s_puzzlename ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Statistic>>() {
      private Observer _observer;

      @Override
      protected List<Statistic> compute() {
        if (_observer == null) {
          _observer = new Observer("statistic_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("s_username");
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("s_puzzlename");
          final int _cursorIndexOfAttempts = _cursor.getColumnIndexOrThrow("attempts");
          final int _cursorIndexOfSolved = _cursor.getColumnIndexOrThrow("solved");
          final int _cursorIndexOfCompletedate = _cursor.getColumnIndexOrThrow("completedate");
          final List<Statistic> _result = new ArrayList<Statistic>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Statistic _item;
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPuzzlename;
            _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
            final Integer _tmpAttempts;
            if (_cursor.isNull(_cursorIndexOfAttempts)) {
              _tmpAttempts = null;
            } else {
              _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            }
            final Boolean _tmpSolved;
            final Integer _tmp;
            if (_cursor.isNull(_cursorIndexOfSolved)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(_cursorIndexOfSolved);
            }
            _tmpSolved = _tmp == null ? null : _tmp != 0;
            final Long _tmpCompletedate;
            if (_cursor.isNull(_cursorIndexOfCompletedate)) {
              _tmpCompletedate = null;
            } else {
              _tmpCompletedate = _cursor.getLong(_cursorIndexOfCompletedate);
            }
            _item = new Statistic(_tmpUsername,_tmpPuzzlename,_tmpAttempts,_tmpSolved,_tmpCompletedate);
            _item._id = _cursor.getInt(_cursorIndexOfId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<Statistic> getStatisticByUserPuzzle(String username, String puzzlename) {
    final String _sql = "SELECT * from statistic_table WHERE s_username=? AND s_puzzlename=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    _argIndex = 2;
    if (puzzlename == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, puzzlename);
    }
    return new ComputableLiveData<Statistic>() {
      private Observer _observer;

      @Override
      protected Statistic compute() {
        if (_observer == null) {
          _observer = new Observer("statistic_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("s_username");
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("s_puzzlename");
          final int _cursorIndexOfAttempts = _cursor.getColumnIndexOrThrow("attempts");
          final int _cursorIndexOfSolved = _cursor.getColumnIndexOrThrow("solved");
          final int _cursorIndexOfCompletedate = _cursor.getColumnIndexOrThrow("completedate");
          final Statistic _result;
          if(_cursor.moveToFirst()) {
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPuzzlename;
            _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
            final Integer _tmpAttempts;
            if (_cursor.isNull(_cursorIndexOfAttempts)) {
              _tmpAttempts = null;
            } else {
              _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            }
            final Boolean _tmpSolved;
            final Integer _tmp;
            if (_cursor.isNull(_cursorIndexOfSolved)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(_cursorIndexOfSolved);
            }
            _tmpSolved = _tmp == null ? null : _tmp != 0;
            final Long _tmpCompletedate;
            if (_cursor.isNull(_cursorIndexOfCompletedate)) {
              _tmpCompletedate = null;
            } else {
              _tmpCompletedate = _cursor.getLong(_cursorIndexOfCompletedate);
            }
            _result = new Statistic(_tmpUsername,_tmpPuzzlename,_tmpAttempts,_tmpSolved,_tmpCompletedate);
            _result._id = _cursor.getInt(_cursorIndexOfId);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<Statistic>> getUserPuzzles(String name, Boolean solved) {
    final String _sql = "SELECT * FROM statistic_table WHERE s_username LIKE ? AND solved = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    _argIndex = 2;
    final Integer _tmp;
    _tmp = solved == null ? null : (solved ? 1 : 0);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return new ComputableLiveData<List<Statistic>>() {
      private Observer _observer;

      @Override
      protected List<Statistic> compute() {
        if (_observer == null) {
          _observer = new Observer("statistic_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("s_username");
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("s_puzzlename");
          final int _cursorIndexOfAttempts = _cursor.getColumnIndexOrThrow("attempts");
          final int _cursorIndexOfSolved = _cursor.getColumnIndexOrThrow("solved");
          final int _cursorIndexOfCompletedate = _cursor.getColumnIndexOrThrow("completedate");
          final List<Statistic> _result = new ArrayList<Statistic>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Statistic _item;
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPuzzlename;
            _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
            final Integer _tmpAttempts;
            if (_cursor.isNull(_cursorIndexOfAttempts)) {
              _tmpAttempts = null;
            } else {
              _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            }
            final Boolean _tmpSolved;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSolved)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfSolved);
            }
            _tmpSolved = _tmp_1 == null ? null : _tmp_1 != 0;
            final Long _tmpCompletedate;
            if (_cursor.isNull(_cursorIndexOfCompletedate)) {
              _tmpCompletedate = null;
            } else {
              _tmpCompletedate = _cursor.getLong(_cursorIndexOfCompletedate);
            }
            _item = new Statistic(_tmpUsername,_tmpPuzzlename,_tmpAttempts,_tmpSolved,_tmpCompletedate);
            _item._id = _cursor.getInt(_cursorIndexOfId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<Statistic>> getUserPuzzlesArray(String name, Boolean solved) {
    final String _sql = "SELECT * FROM statistic_table WHERE s_username LIKE ? AND solved = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    _argIndex = 2;
    final Integer _tmp;
    _tmp = solved == null ? null : (solved ? 1 : 0);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return new ComputableLiveData<List<Statistic>>() {
      private Observer _observer;

      @Override
      protected List<Statistic> compute() {
        if (_observer == null) {
          _observer = new Observer("statistic_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("s_username");
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("s_puzzlename");
          final int _cursorIndexOfAttempts = _cursor.getColumnIndexOrThrow("attempts");
          final int _cursorIndexOfSolved = _cursor.getColumnIndexOrThrow("solved");
          final int _cursorIndexOfCompletedate = _cursor.getColumnIndexOrThrow("completedate");
          final List<Statistic> _result = new ArrayList<Statistic>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Statistic _item;
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPuzzlename;
            _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
            final Integer _tmpAttempts;
            if (_cursor.isNull(_cursorIndexOfAttempts)) {
              _tmpAttempts = null;
            } else {
              _tmpAttempts = _cursor.getInt(_cursorIndexOfAttempts);
            }
            final Boolean _tmpSolved;
            final Integer _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSolved)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(_cursorIndexOfSolved);
            }
            _tmpSolved = _tmp_1 == null ? null : _tmp_1 != 0;
            final Long _tmpCompletedate;
            if (_cursor.isNull(_cursorIndexOfCompletedate)) {
              _tmpCompletedate = null;
            } else {
              _tmpCompletedate = _cursor.getLong(_cursorIndexOfCompletedate);
            }
            _item = new Statistic(_tmpUsername,_tmpPuzzlename,_tmpAttempts,_tmpSolved,_tmpCompletedate);
            _item._id = _cursor.getInt(_cursorIndexOfId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
