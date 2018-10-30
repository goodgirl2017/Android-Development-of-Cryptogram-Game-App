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
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CryptogramDao_Impl implements CryptogramDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCryptogram;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCryptogram;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CryptogramDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCryptogram = new EntityInsertionAdapter<Cryptogram>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `cryptogram_table`(`c_puzzlename`,`phrase`,`encrypted`,`allowed`,`alphabets`,`cipher`,`createdate`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cryptogram value) {
        if (value.getPuzzlename() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPuzzlename());
        }
        if (value.getPhrase() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPhrase());
        }
        if (value.getEncrypted() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEncrypted());
        }
        if (value.getAllowed() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getAllowed());
        }
        if (value.getAlphabets() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAlphabets());
        }
        if (value.getCipher() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCipher());
        }
        if (value.getCreatedate() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getCreatedate());
        }
      }
    };
    this.__updateAdapterOfCryptogram = new EntityDeletionOrUpdateAdapter<Cryptogram>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `cryptogram_table` SET `c_puzzlename` = ?,`phrase` = ?,`encrypted` = ?,`allowed` = ?,`alphabets` = ?,`cipher` = ?,`createdate` = ? WHERE `c_puzzlename` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cryptogram value) {
        if (value.getPuzzlename() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPuzzlename());
        }
        if (value.getPhrase() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPhrase());
        }
        if (value.getEncrypted() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEncrypted());
        }
        if (value.getAllowed() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getAllowed());
        }
        if (value.getAlphabets() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAlphabets());
        }
        if (value.getCipher() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCipher());
        }
        if (value.getCreatedate() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getCreatedate());
        }
        if (value.getPuzzlename() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getPuzzlename());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM cryptogram_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(Cryptogram cryptogram) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCryptogram.insert(cryptogram);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Cryptogram cryptogram) {
    __db.beginTransaction();
    try {
      __updateAdapterOfCryptogram.handle(cryptogram);
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
  public LiveData<List<Cryptogram>> getAllCryptograms() {
    final String _sql = "SELECT * from cryptogram_table ORDER BY c_puzzlename ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Cryptogram>>() {
      private Observer _observer;

      @Override
      protected List<Cryptogram> compute() {
        if (_observer == null) {
          _observer = new Observer("cryptogram_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("c_puzzlename");
          final int _cursorIndexOfPhrase = _cursor.getColumnIndexOrThrow("phrase");
          final int _cursorIndexOfEncrypted = _cursor.getColumnIndexOrThrow("encrypted");
          final int _cursorIndexOfAllowed = _cursor.getColumnIndexOrThrow("allowed");
          final int _cursorIndexOfAlphabets = _cursor.getColumnIndexOrThrow("alphabets");
          final int _cursorIndexOfCipher = _cursor.getColumnIndexOrThrow("cipher");
          final int _cursorIndexOfCreatedate = _cursor.getColumnIndexOrThrow("createdate");
          final List<Cryptogram> _result = new ArrayList<Cryptogram>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Cryptogram _item;
            final String _tmpPuzzlename;
            _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
            final String _tmpPhrase;
            _tmpPhrase = _cursor.getString(_cursorIndexOfPhrase);
            final String _tmpEncrypted;
            _tmpEncrypted = _cursor.getString(_cursorIndexOfEncrypted);
            final Integer _tmpAllowed;
            if (_cursor.isNull(_cursorIndexOfAllowed)) {
              _tmpAllowed = null;
            } else {
              _tmpAllowed = _cursor.getInt(_cursorIndexOfAllowed);
            }
            final String _tmpAlphabets;
            _tmpAlphabets = _cursor.getString(_cursorIndexOfAlphabets);
            final String _tmpCipher;
            _tmpCipher = _cursor.getString(_cursorIndexOfCipher);
            final Long _tmpCreatedate;
            if (_cursor.isNull(_cursorIndexOfCreatedate)) {
              _tmpCreatedate = null;
            } else {
              _tmpCreatedate = _cursor.getLong(_cursorIndexOfCreatedate);
            }
            _item = new Cryptogram(_tmpPuzzlename,_tmpPhrase,_tmpEncrypted,_tmpAllowed,_tmpAlphabets,_tmpCipher,_tmpCreatedate);
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
  public LiveData<Cryptogram> getCryptogramDetails(String puzzlename) {
    final String _sql = "SELECT * from cryptogram_table WHERE c_puzzlename=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (puzzlename == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, puzzlename);
    }
    return new ComputableLiveData<Cryptogram>() {
      private Observer _observer;

      @Override
      protected Cryptogram compute() {
        if (_observer == null) {
          _observer = new Observer("cryptogram_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("c_puzzlename");
          final int _cursorIndexOfPhrase = _cursor.getColumnIndexOrThrow("phrase");
          final int _cursorIndexOfEncrypted = _cursor.getColumnIndexOrThrow("encrypted");
          final int _cursorIndexOfAllowed = _cursor.getColumnIndexOrThrow("allowed");
          final int _cursorIndexOfAlphabets = _cursor.getColumnIndexOrThrow("alphabets");
          final int _cursorIndexOfCipher = _cursor.getColumnIndexOrThrow("cipher");
          final int _cursorIndexOfCreatedate = _cursor.getColumnIndexOrThrow("createdate");
          final Cryptogram _result;
          if(_cursor.moveToFirst()) {
            final String _tmpPuzzlename;
            _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
            final String _tmpPhrase;
            _tmpPhrase = _cursor.getString(_cursorIndexOfPhrase);
            final String _tmpEncrypted;
            _tmpEncrypted = _cursor.getString(_cursorIndexOfEncrypted);
            final Integer _tmpAllowed;
            if (_cursor.isNull(_cursorIndexOfAllowed)) {
              _tmpAllowed = null;
            } else {
              _tmpAllowed = _cursor.getInt(_cursorIndexOfAllowed);
            }
            final String _tmpAlphabets;
            _tmpAlphabets = _cursor.getString(_cursorIndexOfAlphabets);
            final String _tmpCipher;
            _tmpCipher = _cursor.getString(_cursorIndexOfCipher);
            final Long _tmpCreatedate;
            if (_cursor.isNull(_cursorIndexOfCreatedate)) {
              _tmpCreatedate = null;
            } else {
              _tmpCreatedate = _cursor.getLong(_cursorIndexOfCreatedate);
            }
            _result = new Cryptogram(_tmpPuzzlename,_tmpPhrase,_tmpEncrypted,_tmpAllowed,_tmpAlphabets,_tmpCipher,_tmpCreatedate);
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
  public LiveData<List<Cryptogram>> getCryptogramSolved(String puzzlename) {
    final String _sql = "select\n"
            + "c.*\n"
            + "from\n"
            + "\tcryptogram_table c\n"
            + "inner join statistic_table s on\n"
            + "\tc.c_puzzlename = s.s_puzzlename\n"
            + "\tand s.solved = 1\n"
            + "\tWHERE 1=1 \n"
            + "\tAND c.c_puzzlename=? \n"
            + "\torder by s.completedate asc;";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (puzzlename == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, puzzlename);
    }
    return new ComputableLiveData<List<Cryptogram>>() {
      private Observer _observer;

      @Override
      protected List<Cryptogram> compute() {
        if (_observer == null) {
          _observer = new Observer("cryptogram_table","statistic_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("c_puzzlename");
          final int _cursorIndexOfPhrase = _cursor.getColumnIndexOrThrow("phrase");
          final int _cursorIndexOfEncrypted = _cursor.getColumnIndexOrThrow("encrypted");
          final int _cursorIndexOfAllowed = _cursor.getColumnIndexOrThrow("allowed");
          final int _cursorIndexOfAlphabets = _cursor.getColumnIndexOrThrow("alphabets");
          final int _cursorIndexOfCipher = _cursor.getColumnIndexOrThrow("cipher");
          final int _cursorIndexOfCreatedate = _cursor.getColumnIndexOrThrow("createdate");
          final List<Cryptogram> _result = new ArrayList<Cryptogram>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Cryptogram _item;
            final String _tmpPuzzlename;
            _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
            final String _tmpPhrase;
            _tmpPhrase = _cursor.getString(_cursorIndexOfPhrase);
            final String _tmpEncrypted;
            _tmpEncrypted = _cursor.getString(_cursorIndexOfEncrypted);
            final Integer _tmpAllowed;
            if (_cursor.isNull(_cursorIndexOfAllowed)) {
              _tmpAllowed = null;
            } else {
              _tmpAllowed = _cursor.getInt(_cursorIndexOfAllowed);
            }
            final String _tmpAlphabets;
            _tmpAlphabets = _cursor.getString(_cursorIndexOfAlphabets);
            final String _tmpCipher;
            _tmpCipher = _cursor.getString(_cursorIndexOfCipher);
            final Long _tmpCreatedate;
            if (_cursor.isNull(_cursorIndexOfCreatedate)) {
              _tmpCreatedate = null;
            } else {
              _tmpCreatedate = _cursor.getLong(_cursorIndexOfCreatedate);
            }
            _item = new Cryptogram(_tmpPuzzlename,_tmpPhrase,_tmpEncrypted,_tmpAllowed,_tmpAlphabets,_tmpCipher,_tmpCreatedate);
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
  public LiveData<List<String>> getCryptogramPlayersSolved(String puzzlename, Integer numPlayers) {
    final String _sql = "select\n"
            + "s.s_puzzlename\n"
            + "from\n"
            + "\tcryptogram_table c\n"
            + "inner join statistic_table s on\n"
            + "\tc.c_puzzlename = s.s_puzzlename\n"
            + "\tand s.solved = 1\n"
            + "\tWHERE 1=1 \n"
            + "\tAND c.c_puzzlename=? \n"
            + "\torder by s.completedate asc\n"
            + " limit ?;";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (puzzlename == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, puzzlename);
    }
    _argIndex = 2;
    if (numPlayers == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, numPlayers);
    }
    return new ComputableLiveData<List<String>>() {
      private Observer _observer;

      @Override
      protected List<String> compute() {
        if (_observer == null) {
          _observer = new Observer("cryptogram_table","statistic_table") {
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

  @Override
  public LiveData<List<Cryptogram>> getAllCryptogramsByCreated() {
    final String _sql = "SELECT * from cryptogram_table ORDER BY createdate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Cryptogram>>() {
      private Observer _observer;

      @Override
      protected List<Cryptogram> compute() {
        if (_observer == null) {
          _observer = new Observer("cryptogram_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("c_puzzlename");
          final int _cursorIndexOfPhrase = _cursor.getColumnIndexOrThrow("phrase");
          final int _cursorIndexOfEncrypted = _cursor.getColumnIndexOrThrow("encrypted");
          final int _cursorIndexOfAllowed = _cursor.getColumnIndexOrThrow("allowed");
          final int _cursorIndexOfAlphabets = _cursor.getColumnIndexOrThrow("alphabets");
          final int _cursorIndexOfCipher = _cursor.getColumnIndexOrThrow("cipher");
          final int _cursorIndexOfCreatedate = _cursor.getColumnIndexOrThrow("createdate");
          final List<Cryptogram> _result = new ArrayList<Cryptogram>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Cryptogram _item;
            final String _tmpPuzzlename;
            _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
            final String _tmpPhrase;
            _tmpPhrase = _cursor.getString(_cursorIndexOfPhrase);
            final String _tmpEncrypted;
            _tmpEncrypted = _cursor.getString(_cursorIndexOfEncrypted);
            final Integer _tmpAllowed;
            if (_cursor.isNull(_cursorIndexOfAllowed)) {
              _tmpAllowed = null;
            } else {
              _tmpAllowed = _cursor.getInt(_cursorIndexOfAllowed);
            }
            final String _tmpAlphabets;
            _tmpAlphabets = _cursor.getString(_cursorIndexOfAlphabets);
            final String _tmpCipher;
            _tmpCipher = _cursor.getString(_cursorIndexOfCipher);
            final Long _tmpCreatedate;
            if (_cursor.isNull(_cursorIndexOfCreatedate)) {
              _tmpCreatedate = null;
            } else {
              _tmpCreatedate = _cursor.getLong(_cursorIndexOfCreatedate);
            }
            _item = new Cryptogram(_tmpPuzzlename,_tmpPhrase,_tmpEncrypted,_tmpAllowed,_tmpAlphabets,_tmpCipher,_tmpCreatedate);
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
  public LiveData<List<String>> getAllCryptogramNames() {
    final String _sql = "SELECT c_puzzlename from cryptogram_table ORDER BY c_puzzlename ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<String>>() {
      private Observer _observer;

      @Override
      protected List<String> compute() {
        if (_observer == null) {
          _observer = new Observer("cryptogram_table") {
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
