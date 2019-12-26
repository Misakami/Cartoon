package com.example.cartoon.model;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.cartoon.model.Bean.NetCartoonCacheDao;
import com.example.cartoon.model.Bean.NetCartoonCacheDao_Impl;
import com.example.cartoon.model.Bean.NetCartoonDao;
import com.example.cartoon.model.Bean.NetCartoonDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDateBase_Impl extends AppDateBase {
  private volatile NetCartoonDao _netCartoonDao;

  private volatile NetCartoonCacheDao _netCartoonCacheDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `netCartoon` (`ncId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cartoonName` TEXT, `url` TEXT, `siteName` TEXT, `lastUpdates` TEXT, `introduction` TEXT, `coverSrc` TEXT, `lastRead` INTEGER NOT NULL, `lastReadLast` INTEGER NOT NULL, `catalogsSize` INTEGER NOT NULL, `time` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `netCache` (`ncCacheId` INTEGER NOT NULL, `catalogsUrl` TEXT, `catalogsTitle` TEXT, PRIMARY KEY(`ncCacheId`), FOREIGN KEY(`ncCacheId`) REFERENCES `netCartoon`(`ncId`) ON UPDATE CASCADE ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '45d41b75f47b6c2382b5eaea83b778e7')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `netCartoon`");
        _db.execSQL("DROP TABLE IF EXISTS `netCache`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsNetCartoon = new HashMap<String, TableInfo.Column>(11);
        _columnsNetCartoon.put("ncId", new TableInfo.Column("ncId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("cartoonName", new TableInfo.Column("cartoonName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("url", new TableInfo.Column("url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("siteName", new TableInfo.Column("siteName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("lastUpdates", new TableInfo.Column("lastUpdates", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("introduction", new TableInfo.Column("introduction", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("coverSrc", new TableInfo.Column("coverSrc", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("lastRead", new TableInfo.Column("lastRead", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("lastReadLast", new TableInfo.Column("lastReadLast", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("catalogsSize", new TableInfo.Column("catalogsSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("time", new TableInfo.Column("time", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNetCartoon = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNetCartoon = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNetCartoon = new TableInfo("netCartoon", _columnsNetCartoon, _foreignKeysNetCartoon, _indicesNetCartoon);
        final TableInfo _existingNetCartoon = TableInfo.read(_db, "netCartoon");
        if (! _infoNetCartoon.equals(_existingNetCartoon)) {
          return new RoomOpenHelper.ValidationResult(false, "netCartoon(com.example.cartoon.model.Bean.NetCartoon).\n"
                  + " Expected:\n" + _infoNetCartoon + "\n"
                  + " Found:\n" + _existingNetCartoon);
        }
        final HashMap<String, TableInfo.Column> _columnsNetCache = new HashMap<String, TableInfo.Column>(3);
        _columnsNetCache.put("ncCacheId", new TableInfo.Column("ncCacheId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCache.put("catalogsUrl", new TableInfo.Column("catalogsUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCache.put("catalogsTitle", new TableInfo.Column("catalogsTitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNetCache = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysNetCache.add(new TableInfo.ForeignKey("netCartoon", "CASCADE", "CASCADE",Arrays.asList("ncCacheId"), Arrays.asList("ncId")));
        final HashSet<TableInfo.Index> _indicesNetCache = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNetCache = new TableInfo("netCache", _columnsNetCache, _foreignKeysNetCache, _indicesNetCache);
        final TableInfo _existingNetCache = TableInfo.read(_db, "netCache");
        if (! _infoNetCache.equals(_existingNetCache)) {
          return new RoomOpenHelper.ValidationResult(false, "netCache(com.example.cartoon.model.Bean.NetCartoonCache).\n"
                  + " Expected:\n" + _infoNetCache + "\n"
                  + " Found:\n" + _existingNetCache);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "45d41b75f47b6c2382b5eaea83b778e7", "0ae3cd38043b5c38bc7afdf50e67eb3f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "netCartoon","netCache");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `netCartoon`");
      _db.execSQL("DELETE FROM `netCache`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public NetCartoonDao cartoonDao() {
    if (_netCartoonDao != null) {
      return _netCartoonDao;
    } else {
      synchronized(this) {
        if(_netCartoonDao == null) {
          _netCartoonDao = new NetCartoonDao_Impl(this);
        }
        return _netCartoonDao;
      }
    }
  }

  @Override
  public NetCartoonCacheDao cacheDao() {
    if (_netCartoonCacheDao != null) {
      return _netCartoonCacheDao;
    } else {
      synchronized(this) {
        if(_netCartoonCacheDao == null) {
          _netCartoonCacheDao = new NetCartoonCacheDao_Impl(this);
        }
        return _netCartoonCacheDao;
      }
    }
  }
}
