package edu.gatech.seclass.sdpcryptogram.dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
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

public class UserCryptogramStatsDao_Impl implements UserCryptogramStatsDao {
  private final RoomDatabase __db;

  public UserCryptogramStatsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
  }

  @Override
  public LiveData<List<UserCryptogramStats>> getUnsolvedCryptograms(String username) {
    final String _sql = "select * from (\n"
            + "select * from player_table a inner join cryptogram_table b where a.p_username = ? ) c \n"
            + "left join statistic_table d on c.c_puzzlename = d.s_puzzlename and c.p_username = d.s_username \n"
            + "where d.completedate is null";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    return new ComputableLiveData<List<UserCryptogramStats>>() {
      private Observer _observer;

      @Override
      protected List<UserCryptogramStats> compute() {
        if (_observer == null) {
          _observer = new Observer("player_table","cryptogram_table","statistic_table") {
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
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("c_puzzlename");
          final int _cursorIndexOfPhrase = _cursor.getColumnIndexOrThrow("phrase");
          final int _cursorIndexOfEncrypted = _cursor.getColumnIndexOrThrow("encrypted");
          final int _cursorIndexOfAllowed = _cursor.getColumnIndexOrThrow("allowed");
          final int _cursorIndexOfAlphabets = _cursor.getColumnIndexOrThrow("alphabets");
          final int _cursorIndexOfCipher = _cursor.getColumnIndexOrThrow("cipher");
          final int _cursorIndexOfCreatedate = _cursor.getColumnIndexOrThrow("createdate");
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfUsername_1 = _cursor.getColumnIndexOrThrow("s_username");
          final int _cursorIndexOfPuzzlename_1 = _cursor.getColumnIndexOrThrow("s_puzzlename");
          final int _cursorIndexOfAttempts = _cursor.getColumnIndexOrThrow("attempts");
          final int _cursorIndexOfSolved = _cursor.getColumnIndexOrThrow("solved");
          final int _cursorIndexOfCompletedate = _cursor.getColumnIndexOrThrow("completedate");
          final List<UserCryptogramStats> _result = new ArrayList<UserCryptogramStats>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final UserCryptogramStats _item;
            final Player _tmpPlayer;
            if (! (_cursor.isNull(_cursorIndexOfUsername) && _cursor.isNull(_cursorIndexOfFirstname) && _cursor.isNull(_cursorIndexOfLastname) && _cursor.isNull(_cursorIndexOfEmail))) {
              final String _tmpUsername;
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
              final String _tmpFirstname;
              _tmpFirstname = _cursor.getString(_cursorIndexOfFirstname);
              final String _tmpLastname;
              _tmpLastname = _cursor.getString(_cursorIndexOfLastname);
              final String _tmpEmail;
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
              _tmpPlayer = new Player(_tmpUsername,_tmpFirstname,_tmpLastname,_tmpEmail);
            }  else  {
              _tmpPlayer = null;
            }
            final Cryptogram _tmpCryptogram;
            if (! (_cursor.isNull(_cursorIndexOfPuzzlename) && _cursor.isNull(_cursorIndexOfPhrase) && _cursor.isNull(_cursorIndexOfEncrypted) && _cursor.isNull(_cursorIndexOfAllowed) && _cursor.isNull(_cursorIndexOfAlphabets) && _cursor.isNull(_cursorIndexOfCipher) && _cursor.isNull(_cursorIndexOfCreatedate))) {
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
              _tmpCryptogram = new Cryptogram(_tmpPuzzlename,_tmpPhrase,_tmpEncrypted,_tmpAllowed,_tmpAlphabets,_tmpCipher,_tmpCreatedate);
            }  else  {
              _tmpCryptogram = null;
            }
            final Statistic _tmpStatistic;
            if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfUsername_1) && _cursor.isNull(_cursorIndexOfPuzzlename_1) && _cursor.isNull(_cursorIndexOfAttempts) && _cursor.isNull(_cursorIndexOfSolved) && _cursor.isNull(_cursorIndexOfCompletedate))) {
              final String _tmpUsername_1;
              _tmpUsername_1 = _cursor.getString(_cursorIndexOfUsername_1);
              final String _tmpPuzzlename_1;
              _tmpPuzzlename_1 = _cursor.getString(_cursorIndexOfPuzzlename_1);
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
              _tmpStatistic = new Statistic(_tmpUsername_1,_tmpPuzzlename_1,_tmpAttempts,_tmpSolved,_tmpCompletedate);
              _tmpStatistic._id = _cursor.getInt(_cursorIndexOfId);
            }  else  {
              _tmpStatistic = null;
            }
            _item = new UserCryptogramStats(_tmpPlayer,_tmpCryptogram,_tmpStatistic);
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
  public LiveData<List<UserCryptogramStats>> getCompletedCryptograms(String username) {
    final String _sql = "select * from (\n"
            + "select * from player_table a inner join cryptogram_table b where a.p_username = ? ) c \n"
            + "inner join statistic_table d on c.c_puzzlename = d.s_puzzlename and c.p_username = d.s_username \n"
            + "where d.completedate is not null \n"
            + "order by d.completedate asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    return new ComputableLiveData<List<UserCryptogramStats>>() {
      private Observer _observer;

      @Override
      protected List<UserCryptogramStats> compute() {
        if (_observer == null) {
          _observer = new Observer("player_table","cryptogram_table","statistic_table") {
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
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("c_puzzlename");
          final int _cursorIndexOfPhrase = _cursor.getColumnIndexOrThrow("phrase");
          final int _cursorIndexOfEncrypted = _cursor.getColumnIndexOrThrow("encrypted");
          final int _cursorIndexOfAllowed = _cursor.getColumnIndexOrThrow("allowed");
          final int _cursorIndexOfAlphabets = _cursor.getColumnIndexOrThrow("alphabets");
          final int _cursorIndexOfCipher = _cursor.getColumnIndexOrThrow("cipher");
          final int _cursorIndexOfCreatedate = _cursor.getColumnIndexOrThrow("createdate");
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfUsername_1 = _cursor.getColumnIndexOrThrow("s_username");
          final int _cursorIndexOfPuzzlename_1 = _cursor.getColumnIndexOrThrow("s_puzzlename");
          final int _cursorIndexOfAttempts = _cursor.getColumnIndexOrThrow("attempts");
          final int _cursorIndexOfSolved = _cursor.getColumnIndexOrThrow("solved");
          final int _cursorIndexOfCompletedate = _cursor.getColumnIndexOrThrow("completedate");
          final List<UserCryptogramStats> _result = new ArrayList<UserCryptogramStats>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final UserCryptogramStats _item;
            final Player _tmpPlayer;
            if (! (_cursor.isNull(_cursorIndexOfUsername) && _cursor.isNull(_cursorIndexOfFirstname) && _cursor.isNull(_cursorIndexOfLastname) && _cursor.isNull(_cursorIndexOfEmail))) {
              final String _tmpUsername;
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
              final String _tmpFirstname;
              _tmpFirstname = _cursor.getString(_cursorIndexOfFirstname);
              final String _tmpLastname;
              _tmpLastname = _cursor.getString(_cursorIndexOfLastname);
              final String _tmpEmail;
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
              _tmpPlayer = new Player(_tmpUsername,_tmpFirstname,_tmpLastname,_tmpEmail);
            }  else  {
              _tmpPlayer = null;
            }
            final Cryptogram _tmpCryptogram;
            if (! (_cursor.isNull(_cursorIndexOfPuzzlename) && _cursor.isNull(_cursorIndexOfPhrase) && _cursor.isNull(_cursorIndexOfEncrypted) && _cursor.isNull(_cursorIndexOfAllowed) && _cursor.isNull(_cursorIndexOfAlphabets) && _cursor.isNull(_cursorIndexOfCipher) && _cursor.isNull(_cursorIndexOfCreatedate))) {
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
              _tmpCryptogram = new Cryptogram(_tmpPuzzlename,_tmpPhrase,_tmpEncrypted,_tmpAllowed,_tmpAlphabets,_tmpCipher,_tmpCreatedate);
            }  else  {
              _tmpCryptogram = null;
            }
            final Statistic _tmpStatistic;
            if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfUsername_1) && _cursor.isNull(_cursorIndexOfPuzzlename_1) && _cursor.isNull(_cursorIndexOfAttempts) && _cursor.isNull(_cursorIndexOfSolved) && _cursor.isNull(_cursorIndexOfCompletedate))) {
              final String _tmpUsername_1;
              _tmpUsername_1 = _cursor.getString(_cursorIndexOfUsername_1);
              final String _tmpPuzzlename_1;
              _tmpPuzzlename_1 = _cursor.getString(_cursorIndexOfPuzzlename_1);
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
              _tmpStatistic = new Statistic(_tmpUsername_1,_tmpPuzzlename_1,_tmpAttempts,_tmpSolved,_tmpCompletedate);
              _tmpStatistic._id = _cursor.getInt(_cursorIndexOfId);
            }  else  {
              _tmpStatistic = null;
            }
            _item = new UserCryptogramStats(_tmpPlayer,_tmpCryptogram,_tmpStatistic);
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
  public LiveData<List<UserCryptogramStats>> getCryptogramStatistics(String puzzlename,
      Boolean solved) {
    final String _sql = "select s.s_puzzlename, s.s_username, s.solved, s.completedate, p.createdate from statistic_table s left join cryptogram_table p on s.s_puzzlename = p.c_puzzlename where s.s_puzzlename=? and s.solved=? order by s.completedate asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (puzzlename == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, puzzlename);
    }
    _argIndex = 2;
    final Integer _tmp;
    _tmp = solved == null ? null : (solved ? 1 : 0);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return new ComputableLiveData<List<UserCryptogramStats>>() {
      private Observer _observer;

      @Override
      protected List<UserCryptogramStats> compute() {
        if (_observer == null) {
          _observer = new Observer("statistic_table","cryptogram_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPuzzlename = _cursor.getColumnIndexOrThrow("s_puzzlename");
          final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("s_username");
          final int _cursorIndexOfSolved = _cursor.getColumnIndexOrThrow("solved");
          final int _cursorIndexOfCompletedate = _cursor.getColumnIndexOrThrow("completedate");
          final int _cursorIndexOfCreatedate = _cursor.getColumnIndexOrThrow("createdate");
          final List<UserCryptogramStats> _result = new ArrayList<UserCryptogramStats>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final UserCryptogramStats _item;
            final Statistic _tmpStatistic;
            if (! (_cursor.isNull(_cursorIndexOfPuzzlename) && _cursor.isNull(_cursorIndexOfUsername) && _cursor.isNull(_cursorIndexOfSolved) && _cursor.isNull(_cursorIndexOfCompletedate))) {
              final String _tmpPuzzlename;
              _tmpPuzzlename = _cursor.getString(_cursorIndexOfPuzzlename);
              final String _tmpUsername;
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
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
              _tmpStatistic = new Statistic(_tmpUsername,_tmpPuzzlename,null,_tmpSolved,_tmpCompletedate);
            }  else  {
              _tmpStatistic = null;
            }
            final Cryptogram _tmpCryptogram;
            if (! (_cursor.isNull(_cursorIndexOfCreatedate))) {
              final Long _tmpCreatedate;
              if (_cursor.isNull(_cursorIndexOfCreatedate)) {
                _tmpCreatedate = null;
              } else {
                _tmpCreatedate = _cursor.getLong(_cursorIndexOfCreatedate);
              }
              _tmpCryptogram = new Cryptogram(null,null,null,null,null,null,_tmpCreatedate);
            }  else  {
              _tmpCryptogram = null;
            }
            _item = new UserCryptogramStats(null,_tmpCryptogram,_tmpStatistic);
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
