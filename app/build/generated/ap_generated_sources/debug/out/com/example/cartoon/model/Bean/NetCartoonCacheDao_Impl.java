package com.example.cartoon.model.Bean;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NetCartoonCacheDao_Impl implements NetCartoonCacheDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NetCartoonCache> __insertionAdapterOfNetCartoonCache;

  private final EntityDeletionOrUpdateAdapter<NetCartoonCache> __updateAdapterOfNetCartoonCache;

  public NetCartoonCacheDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNetCartoonCache = new EntityInsertionAdapter<NetCartoonCache>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `netCache` (`cartoonName`,`siteType`,`catalogsUrl`,`catalogsTitle`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NetCartoonCache value) {
        if (value.getCartoonName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getCartoonName());
        }
        stmt.bindLong(2, value.getSiteType());
        final String _tmp;
        _tmp = ContentsConverter.converter(value.getCatalogsUrl());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = ContentsConverter.converter(value.getCatalogsTitle());
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp_1);
        }
      }
    };
    this.__updateAdapterOfNetCartoonCache = new EntityDeletionOrUpdateAdapter<NetCartoonCache>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `netCache` SET `cartoonName` = ?,`siteType` = ?,`catalogsUrl` = ?,`catalogsTitle` = ? WHERE `cartoonName` = ? AND `siteType` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NetCartoonCache value) {
        if (value.getCartoonName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getCartoonName());
        }
        stmt.bindLong(2, value.getSiteType());
        final String _tmp;
        _tmp = ContentsConverter.converter(value.getCatalogsUrl());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = ContentsConverter.converter(value.getCatalogsTitle());
        if (_tmp_1 == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp_1);
        }
        if (value.getCartoonName() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCartoonName());
        }
        stmt.bindLong(6, value.getSiteType());
      }
    };
  }

  @Override
  public void insert(final NetCartoonCache cache) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNetCartoonCache.insert(cache);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void Update(final NetCartoonCache cache) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfNetCartoonCache.handle(cache);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public NetCartoonCache getcatLog(final String cartoonName, final int siteType) {
    final String _sql = "select * from netCache where cartoonName =? and siteType=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (cartoonName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, cartoonName);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, siteType);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCartoonName = CursorUtil.getColumnIndexOrThrow(_cursor, "cartoonName");
      final int _cursorIndexOfSiteType = CursorUtil.getColumnIndexOrThrow(_cursor, "siteType");
      final int _cursorIndexOfCatalogsUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogsUrl");
      final int _cursorIndexOfCatalogsTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogsTitle");
      final NetCartoonCache _result;
      if(_cursor.moveToFirst()) {
        _result = new NetCartoonCache();
        final String _tmpCartoonName;
        _tmpCartoonName = _cursor.getString(_cursorIndexOfCartoonName);
        _result.setCartoonName(_tmpCartoonName);
        final int _tmpSiteType;
        _tmpSiteType = _cursor.getInt(_cursorIndexOfSiteType);
        _result.setSiteType(_tmpSiteType);
        final List<String> _tmpCatalogsUrl;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfCatalogsUrl);
        _tmpCatalogsUrl = ContentsConverter.revert(_tmp);
        _result.setCatalogsUrl(_tmpCatalogsUrl);
        final List<String> _tmpCatalogsTitle;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfCatalogsTitle);
        _tmpCatalogsTitle = ContentsConverter.revert(_tmp_1);
        _result.setCatalogsTitle(_tmpCatalogsTitle);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
