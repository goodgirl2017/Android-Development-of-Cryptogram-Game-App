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
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerDao_Impl implements PlayerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPlayer;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPlayer;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PlayerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlayer = new EntityInsertionAdapter<Player>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `player_table`(`p_username`,`firstname`,`lastname`,`email`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Player value) {
        if (value.getUsername() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUsername());
        }
        if (value.getFirstname() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstname());
        }
        if (value.getLastname() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastname());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
        }
      }
    };
    this.__updateAdapterOfPlayer = new EntityDeletionOrUpdateAdapter<Player>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `player_table` SET `p_username` = ?,`firstname` = ?,`lastname` = ?,`email` = ? WHERE `p_username` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Player value) {
        if (value.getUsername() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUsername());
        }
        if (value.getFirstname() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstname());
        }
        if (value.getLastname() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastname());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
        }
        if (value.getUsername() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getUsername());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM player_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(Player player) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPlayer.insert(player);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Player player) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPlayer.handle(player);
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
  public LiveData<List<Player>> getAllPlayers() {
    final String _sql = "SELECT * from player_table ORDER BY p_username ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Player>>() {
      private Observer _observer;

      @Override
      protected List<Player> compute() {
        if (_observer == null) {
          _observer = new Observer("player_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("p_username");
          final int _cursorIndexOfFirstname = _cursor.getColumnIndexOrThrow("firstname");
          final int _cursorIndexOfLastname = _cursor.getColumnIndexOrThrow("lastname");
          final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email");
          final List<Player> _result = new ArrayList<Player>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Player _item;
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpFirstname;
            _tmpFirstname = _cursor.getString(_cursorIndexOfFirstname);
            final String _tmpLastname;
            _tmpLastname = _cursor.getString(_cursorIndexOfLastname);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _item = new Player(_tmpUsername,_tmpFirstname,_tmpLastname,_tmpEmail);
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
  public LiveData<Integer> getPlayerCount(String username) {
    final String _sql = "SELECT COUNT(*) from player_table WHERE p_username = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    return new ComputableLiveData<Integer>() {
      private Observer _observer;

      @Override
      protected Integer compute() {
        if (_observer == null) {
          _observer = new Observer("player_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public LiveData<Player> getPlayer(String username) {
    final String _sql = "SELECT * from player_table WHERE p_username = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    return new ComputableLiveData<Player>() {
      private Observer _observer;

      @Override
      protected Player compute() {
        if (_observer == null) {
          _observer = new Observer("player_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("p_username");
          final int _cursorIndexOfFirstname = _cursor.getColumnIndexOrThrow("firstname");
          final int _cursorIndexOfLastname = _cursor.getColumnIndexOrThrow("lastname");
          final int _cursorIndexOfEmail = _cursor.getColumnIndexOrThrow("email");
          final Player _result;
          if(_cursor.moveToFirst()) {
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpFirstname;
            _tmpFirstname = _cursor.getString(_cursorIndexOfFirstname);
            final String _tmpLastname;
            _tmpLastname = _cursor.getString(_cursorIndexOfLastname);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            _result = new Player(_tmpUsername,_tmpFirstname,_tmpLastname,_tmpEmail);
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
  public LiveData<List<String>> getAllPlayerNames() {
    final String _sql = "SELECT p_username from player_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<String>>() {
      private Observer _observer;

      @Override
      protected List<String> compute() {
        if (_observer == null) {
          _observer = new Observer("player_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
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
