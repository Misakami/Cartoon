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
import com.example.cartoon.model.Bean.CartoonMarkDao;
import com.example.cartoon.model.Bean.CartoonMarkDao_Impl;
import com.example.cartoon.model.Bean.NetCartoonCacheDao;
import com.example.cartoon.model.Bean.NetCartoonCacheDao_Impl;
import com.example.cartoon.model.Bean.NetCartoonDao;
import com.example.cartoon.model.Bean.NetCartoonDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDateBase_Impl extends AppDateBase {
  private volatile NetCartoonDao _netCartoonDao;

  private volatile NetCartoonCacheDao _netCartoonCacheDao;

  private volatile CartoonMarkDao _cartoonMarkDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `netCartoon` (`ncId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cartoonName` TEXT, `url` TEXT, `siteType` INTEGER NOT NULL, `lastUpdates` TEXT, `introduction` TEXT, `coverSrc` TEXT, `lastReadLast` INTEGER NOT NULL, `catalogsSize` INTEGER NOT NULL, `time` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `netCache` (`cartoonName` TEXT NOT NULL, `siteType` INTEGER NOT NULL, `catalogsUrl` TEXT, `catalogsTitle` TEXT, PRIMARY KEY(`cartoonName`, `siteType`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cartoonMark` (`markId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cartoonName` TEXT, `siteType` INTEGER NOT NULL, `lastRead` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '248d82766145665a52329f1d98a5d9e5')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `netCartoon`");
        _db.execSQL("DROP TABLE IF EXISTS `netCache`");
        _db.execSQL("DROP TABLE IF EXISTS `cartoonMark`");
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
        final HashMap<String, TableInfo.Column> _columnsNetCartoon = new HashMap<String, TableInfo.Column>(10);
        _columnsNetCartoon.put("ncId", new TableInfo.Column("ncId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("cartoonName", new TableInfo.Column("cartoonName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("url", new TableInfo.Column("url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("siteType", new TableInfo.Column("siteType", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("lastUpdates", new TableInfo.Column("lastUpdates", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("introduction", new TableInfo.Column("introduction", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCartoon.put("coverSrc", new TableInfo.Column("coverSrc", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
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
        final HashMap<String, TableInfo.Column> _columnsNetCache = new HashMap<String, TableInfo.Column>(4);
        _columnsNetCache.put("cartoonName", new TableInfo.Column("cartoonName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCache.put("siteType", new TableInfo.Column("siteType", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCache.put("catalogsUrl", new TableInfo.Column("catalogsUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNetCache.put("catalogsTitle", new TableInfo.Column("catalogsTitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNetCache = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNetCache = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNetCache = new TableInfo("netCache", _columnsNetCache, _foreignKeysNetCache, _indicesNetCache);
        final TableInfo _existingNetCache = TableInfo.read(_db, "netCache");
        if (! _infoNetCache.equals(_existingNetCache)) {
          return new RoomOpenHelper.ValidationResult(false, "netCache(com.example.cartoon.model.Bean.NetCartoonCache).\n"
                  + " Expected:\n" + _infoNetCache + "\n"
                  + " Found:\n" + _existingNetCache);
        }
        final HashMap<String, TableInfo.Column> _columnsCartoonMark = new HashMap<String, TableInfo.Column>(4);
        _columnsCartoonMark.put("markId", new TableInfo.Column("markId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartoonMark.put("cartoonName", new TableInfo.Column("cartoonName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartoonMark.put("siteType", new TableInfo.Column("siteType", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCartoonMark.put("lastRead", new TableInfo.Column("lastRead", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCartoonMark = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCartoonMark = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCartoonMark = new TableInfo("cartoonMark", _columnsCartoonMark, _foreignKeysCartoonMark, _indicesCartoonMark);
        final TableInfo _existingCartoonMark = TableInfo.read(_db, "cartoonMark");
        if (! _infoCartoonMark.equals(_existingCartoonMark)) {
          return new RoomOpenHelper.ValidationResult(false, "cartoonMark(com.example.cartoon.model.Bean.CartoonMark).\n"
                  + " Expected:\n" + _infoCartoonMark + "\n"
                  + " Found:\n" + _existingCartoonMark);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "248d82766145665a52329f1d98a5d9e5", "dc7a80226f52a7a5fab75f1b38521229");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "netCartoon","netCache","cartoonMark");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `netCartoon`");
      _db.execSQL("DELETE FROM `netCache`");
      _db.execSQL("DELETE FROM `cartoonMark`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
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

  @Override
  public CartoonMarkDao markDao() {
    if (_cartoonMarkDao != null) {
      return _cartoonMarkDao;
    } else {
      synchronized(this) {
        if(_cartoonMarkDao == null) {
          _cartoonMarkDao = new CartoonMarkDao_Impl(this);
        }
        return _cartoonMarkDao;
      }
    }
  }
}
