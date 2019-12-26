package com.example.cartoon.model.Bean;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
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

  private final EntityDeletionOrUpdateAdapter<NetCartoonCache> __updateAdapterOfNetCartoonCache;

  public NetCartoonCacheDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__updateAdapterOfNetCartoonCache = new EntityDeletionOrUpdateAdapter<NetCartoonCache>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `netCache` SET `ncCacheId` = ?,`catalogsUrl` = ?,`catalogsTitle` = ? WHERE `ncCacheId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NetCartoonCache value) {
        stmt.bindLong(1, value.getNcCacheId());
        final String _tmp;
        _tmp = ContentsConverter.converter(value.getCatalogsUrl());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = ContentsConverter.converter(value.getCatalogsTitle());
        if (_tmp_1 == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp_1);
        }
        stmt.bindLong(4, value.getNcCacheId());
      }
    };
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
  public NetCartoonCache getcatLog(final int ncCacheId) {
    final String _sql = "select * from netCache where ncCacheId =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, ncCacheId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfNcCacheId = CursorUtil.getColumnIndexOrThrow(_cursor, "ncCacheId");
      final int _cursorIndexOfCatalogsUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogsUrl");
      final int _cursorIndexOfCatalogsTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogsTitle");
      final NetCartoonCache _result;
      if(_cursor.moveToFirst()) {
        _result = new NetCartoonCache();
        final int _tmpNcCacheId;
        _tmpNcCacheId = _cursor.getInt(_cursorIndexOfNcCacheId);
        _result.setNcCacheId(_tmpNcCacheId);
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
