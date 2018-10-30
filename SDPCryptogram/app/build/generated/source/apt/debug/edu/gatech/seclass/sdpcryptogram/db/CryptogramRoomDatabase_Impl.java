package edu.gatech.seclass.sdpcryptogram.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import edu.gatech.seclass.sdpcryptogram.dao.CryptogramDao;
import edu.gatech.seclass.sdpcryptogram.dao.CryptogramDao_Impl;
import edu.gatech.seclass.sdpcryptogram.dao.PlayerDao;
import edu.gatech.seclass.sdpcryptogram.dao.PlayerDao_Impl;
import edu.gatech.seclass.sdpcryptogram.dao.StatisticDao;
import edu.gatech.seclass.sdpcryptogram.dao.StatisticDao_Impl;
import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStatsDao;
import edu.gatech.seclass.sdpcryptogram.dao.UserCryptogramStatsDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class CryptogramRoomDatabase_Impl extends CryptogramRoomDatabase {
  private volatile PlayerDao _playerDao;

  private volatile CryptogramDao _cryptogramDao;

  private volatile StatisticDao _statisticDao;

  private volatile UserCryptogramStatsDao _userCryptogramStatsDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `player_table` (`p_username` TEXT NOT NULL, `firstname` TEXT NOT NULL, `lastname` TEXT NOT NULL, `email` TEXT NOT NULL, PRIMARY KEY(`p_username`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cryptogram_table` (`c_puzzlename` TEXT NOT NULL, `phrase` TEXT NOT NULL, `encrypted` TEXT NOT NULL, `allowed` INTEGER NOT NULL, `alphabets` TEXT NOT NULL, `cipher` TEXT NOT NULL, `createdate` INTEGER, PRIMARY KEY(`c_puzzlename`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `statistic_table` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `s_username` TEXT NOT NULL, `s_puzzlename` TEXT NOT NULL, `attempts` INTEGER NOT NULL, `solved` INTEGER NOT NULL, `completedate` INTEGER)");
        _db.execSQL("CREATE UNIQUE INDEX `index_statistic_table_s_username_s_puzzlename` ON `statistic_table` (`s_username`, `s_puzzlename`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"25d8feffe1a86f1e97cee18974bba45d\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `player_table`");
        _db.execSQL("DROP TABLE IF EXISTS `cryptogram_table`");
        _db.execSQL("DROP TABLE IF EXISTS `statistic_table`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPlayerTable = new HashMap<String, TableInfo.Column>(4);
        _columnsPlayerTable.put("p_username", new TableInfo.Column("p_username", "TEXT", true, 1));
        _columnsPlayerTable.put("firstname", new TableInfo.Column("firstname", "TEXT", true, 0));
        _columnsPlayerTable.put("lastname", new TableInfo.Column("lastname", "TEXT", true, 0));
        _columnsPlayerTable.put("email", new TableInfo.Column("email", "TEXT", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlayerTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlayerTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlayerTable = new TableInfo("player_table", _columnsPlayerTable, _foreignKeysPlayerTable, _indicesPlayerTable);
        final TableInfo _existingPlayerTable = TableInfo.read(_db, "player_table");
        if (! _infoPlayerTable.equals(_existingPlayerTable)) {
          throw new IllegalStateException("Migration didn't properly handle player_table(edu.gatech.seclass.sdpcryptogram.dao.Player).\n"
                  + " Expected:\n" + _infoPlayerTable + "\n"
                  + " Found:\n" + _existingPlayerTable);
        }
        final HashMap<String, TableInfo.Column> _columnsCryptogramTable = new HashMap<String, TableInfo.Column>(7);
        _columnsCryptogramTable.put("c_puzzlename", new TableInfo.Column("c_puzzlename", "TEXT", true, 1));
        _columnsCryptogramTable.put("phrase", new TableInfo.Column("phrase", "TEXT", true, 0));
        _columnsCryptogramTable.put("encrypted", new TableInfo.Column("encrypted", "TEXT", true, 0));
        _columnsCryptogramTable.put("allowed", new TableInfo.Column("allowed", "INTEGER", true, 0));
        _columnsCryptogramTable.put("alphabets", new TableInfo.Column("alphabets", "TEXT", true, 0));
        _columnsCryptogramTable.put("cipher", new TableInfo.Column("cipher", "TEXT", true, 0));
        _columnsCryptogramTable.put("createdate", new TableInfo.Column("createdate", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCryptogramTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCryptogramTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCryptogramTable = new TableInfo("cryptogram_table", _columnsCryptogramTable, _foreignKeysCryptogramTable, _indicesCryptogramTable);
        final TableInfo _existingCryptogramTable = TableInfo.read(_db, "cryptogram_table");
        if (! _infoCryptogramTable.equals(_existingCryptogramTable)) {
          throw new IllegalStateException("Migration didn't properly handle cryptogram_table(edu.gatech.seclass.sdpcryptogram.dao.Cryptogram).\n"
                  + " Expected:\n" + _infoCryptogramTable + "\n"
                  + " Found:\n" + _existingCryptogramTable);
        }
        final HashMap<String, TableInfo.Column> _columnsStatisticTable = new HashMap<String, TableInfo.Column>(6);
        _columnsStatisticTable.put("_id", new TableInfo.Column("_id", "INTEGER", true, 1));
        _columnsStatisticTable.put("s_username", new TableInfo.Column("s_username", "TEXT", true, 0));
        _columnsStatisticTable.put("s_puzzlename", new TableInfo.Column("s_puzzlename", "TEXT", true, 0));
        _columnsStatisticTable.put("attempts", new TableInfo.Column("attempts", "INTEGER", true, 0));
        _columnsStatisticTable.put("solved", new TableInfo.Column("solved", "INTEGER", true, 0));
        _columnsStatisticTable.put("completedate", new TableInfo.Column("completedate", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStatisticTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStatisticTable = new HashSet<TableInfo.Index>(1);
        _indicesStatisticTable.add(new TableInfo.Index("index_statistic_table_s_username_s_puzzlename", true, Arrays.asList("s_username","s_puzzlename")));
        final TableInfo _infoStatisticTable = new TableInfo("statistic_table", _columnsStatisticTable, _foreignKeysStatisticTable, _indicesStatisticTable);
        final TableInfo _existingStatisticTable = TableInfo.read(_db, "statistic_table");
        if (! _infoStatisticTable.equals(_existingStatisticTable)) {
          throw new IllegalStateException("Migration didn't properly handle statistic_table(edu.gatech.seclass.sdpcryptogram.dao.Statistic).\n"
                  + " Expected:\n" + _infoStatisticTable + "\n"
                  + " Found:\n" + _existingStatisticTable);
        }
      }
    }, "25d8feffe1a86f1e97cee18974bba45d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "player_table","cryptogram_table","statistic_table");
  }

  @Override
  public PlayerDao playerDao() {
    if (_playerDao != null) {
      return _playerDao;
    } else {
      synchronized(this) {
        if(_playerDao == null) {
          _playerDao = new PlayerDao_Impl(this);
        }
        return _playerDao;
      }
    }
  }

  @Override
  public CryptogramDao cryptogramDao() {
    if (_cryptogramDao != null) {
      return _cryptogramDao;
    } else {
      synchronized(this) {
        if(_cryptogramDao == null) {
          _cryptogramDao = new CryptogramDao_Impl(this);
        }
        return _cryptogramDao;
      }
    }
  }

  @Override
  public StatisticDao statisticDao() {
    if (_statisticDao != null) {
      return _statisticDao;
    } else {
      synchronized(this) {
        if(_statisticDao == null) {
          _statisticDao = new StatisticDao_Impl(this);
        }
        return _statisticDao;
      }
    }
  }

  @Override
  public UserCryptogramStatsDao userCryptogramStatsDao() {
    if (_userCryptogramStatsDao != null) {
      return _userCryptogramStatsDao;
    } else {
      synchronized(this) {
        if(_userCryptogramStatsDao == null) {
          _userCryptogramStatsDao = new UserCryptogramStatsDao_Impl(this);
        }
        return _userCryptogramStatsDao;
      }
    }
  }
}
