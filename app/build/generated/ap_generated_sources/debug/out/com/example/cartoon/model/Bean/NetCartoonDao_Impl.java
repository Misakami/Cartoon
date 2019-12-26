package com.example.cartoon.model.Bean;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NetCartoonDao_Impl implements NetCartoonDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NetCartoon> __insertionAdapterOfNetCartoon;

  private final EntityDeletionOrUpdateAdapter<NetCartoon> __updateAdapterOfNetCartoon;

  private final SharedSQLiteStatement __preparedStmtOfDelect;

  public NetCartoonDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNetCartoon = new EntityInsertionAdapter<NetCartoon>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `netCartoon` (`ncId`,`cartoonName`,`url`,`siteName`,`lastUpdates`,`introduction`,`coverSrc`,`lastRead`,`lastReadLast`,`catalogsSize`,`time`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NetCartoon value) {
        stmt.bindLong(1, value.getNcId());
        if (value.getCartoonName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCartoonName());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUrl());
        }
        if (value.getSiteName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSiteName());
        }
        if (value.getLastUpdates() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLastUpdates());
        }
        if (value.getIntroduction() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getIntroduction());
        }
        if (value.getCoverSrc() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCoverSrc());
        }
        stmt.bindLong(8, value.getLastRead());
        stmt.bindLong(9, value.getLastReadLast());
        stmt.bindLong(10, value.getCatalogsSize());
        stmt.bindLong(11, value.getTime());
      }
    };
    this.__updateAdapterOfNetCartoon = new EntityDeletionOrUpdateAdapter<NetCartoon>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `netCartoon` SET `ncId` = ?,`cartoonName` = ?,`url` = ?,`siteName` = ?,`lastUpdates` = ?,`introduction` = ?,`coverSrc` = ?,`lastRead` = ?,`lastReadLast` = ?,`catalogsSize` = ?,`time` = ? WHERE `ncId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NetCartoon value) {
        stmt.bindLong(1, value.getNcId());
        if (value.getCartoonName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCartoonName());
        }
        if (value.getUrl() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getUrl());
        }
        if (value.getSiteName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSiteName());
        }
        if (value.getLastUpdates() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLastUpdates());
        }
        if (value.getIntroduction() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getIntroduction());
        }
        if (value.getCoverSrc() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCoverSrc());
        }
        stmt.bindLong(8, value.getLastRead());
        stmt.bindLong(9, value.getLastReadLast());
        stmt.bindLong(10, value.getCatalogsSize());
        stmt.bindLong(11, value.getTime());
        stmt.bindLong(12, value.getNcId());
      }
    };
    this.__preparedStmtOfDelect = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from netCartoon where siteName = ? and cartoonName = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final NetCartoon cartoon) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfNetCartoon.insert(cartoon);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void upDate(final NetCartoon cartoon) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfNetCartoon.handle(cartoon);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delect(final String siteName, final String cartoonName) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelect.acquire();
    int _argIndex = 1;
    if (siteName == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, siteName);
    }
    _argIndex = 2;
    if (cartoonName == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, cartoonName);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelect.release(_stmt);
    }
  }

  @Override
  public List<NetCartoon> getNetCartoons() {
    final String _sql = "select * from netCartoon order by time desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfNcId = CursorUtil.getColumnIndexOrThrow(_cursor, "ncId");
      final int _cursorIndexOfCartoonName = CursorUtil.getColumnIndexOrThrow(_cursor, "cartoonName");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfSiteName = CursorUtil.getColumnIndexOrThrow(_cursor, "siteName");
      final int _cursorIndexOfLastUpdates = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdates");
      final int _cursorIndexOfIntroduction = CursorUtil.getColumnIndexOrThrow(_cursor, "introduction");
      final int _cursorIndexOfCoverSrc = CursorUtil.getColumnIndexOrThrow(_cursor, "coverSrc");
      final int _cursorIndexOfLastRead = CursorUtil.getColumnIndexOrThrow(_cursor, "lastRead");
      final int _cursorIndexOfLastReadLast = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReadLast");
      final int _cursorIndexOfCatalogsSize = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogsSize");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final List<NetCartoon> _result = new ArrayList<NetCartoon>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final NetCartoon _item;
        _item = new NetCartoon();
        final int _tmpNcId;
        _tmpNcId = _cursor.getInt(_cursorIndexOfNcId);
        _item.setNcId(_tmpNcId);
        final String _tmpCartoonName;
        _tmpCartoonName = _cursor.getString(_cursorIndexOfCartoonName);
        _item.setCartoonName(_tmpCartoonName);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _item.setUrl(_tmpUrl);
        final String _tmpSiteName;
        _tmpSiteName = _cursor.getString(_cursorIndexOfSiteName);
        _item.setSiteName(_tmpSiteName);
        final String _tmpLastUpdates;
        _tmpLastUpdates = _cursor.getString(_cursorIndexOfLastUpdates);
        _item.setLastUpdates(_tmpLastUpdates);
        final String _tmpIntroduction;
        _tmpIntroduction = _cursor.getString(_cursorIndexOfIntroduction);
        _item.setIntroduction(_tmpIntroduction);
        final String _tmpCoverSrc;
        _tmpCoverSrc = _cursor.getString(_cursorIndexOfCoverSrc);
        _item.setCoverSrc(_tmpCoverSrc);
        final int _tmpLastRead;
        _tmpLastRead = _cursor.getInt(_cursorIndexOfLastRead);
        _item.setLastRead(_tmpLastRead);
        final int _tmpLastReadLast;
        _tmpLastReadLast = _cursor.getInt(_cursorIndexOfLastReadLast);
        _item.setLastReadLast(_tmpLastReadLast);
        final int _tmpCatalogsSize;
        _tmpCatalogsSize = _cursor.getInt(_cursorIndexOfCatalogsSize);
        _item.setCatalogsSize(_tmpCatalogsSize);
        final long _tmpTime;
        _tmpTime = _cursor.getLong(_cursorIndexOfTime);
        _item.setTime(_tmpTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public NetCartoon getNetCartoon(final String siteName, final String cartoonName) {
    final String _sql = "select * from netCartoon where siteName = ? and cartoonName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (siteName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, siteName);
    }
    _argIndex = 2;
    if (cartoonName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, cartoonName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfNcId = CursorUtil.getColumnIndexOrThrow(_cursor, "ncId");
      final int _cursorIndexOfCartoonName = CursorUtil.getColumnIndexOrThrow(_cursor, "cartoonName");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfSiteName = CursorUtil.getColumnIndexOrThrow(_cursor, "siteName");
      final int _cursorIndexOfLastUpdates = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdates");
      final int _cursorIndexOfIntroduction = CursorUtil.getColumnIndexOrThrow(_cursor, "introduction");
      final int _cursorIndexOfCoverSrc = CursorUtil.getColumnIndexOrThrow(_cursor, "coverSrc");
      final int _cursorIndexOfLastRead = CursorUtil.getColumnIndexOrThrow(_cursor, "lastRead");
      final int _cursorIndexOfLastReadLast = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReadLast");
      final int _cursorIndexOfCatalogsSize = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogsSize");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final NetCartoon _result;
      if(_cursor.moveToFirst()) {
        _result = new NetCartoon();
        final int _tmpNcId;
        _tmpNcId = _cursor.getInt(_cursorIndexOfNcId);
        _result.setNcId(_tmpNcId);
        final String _tmpCartoonName;
        _tmpCartoonName = _cursor.getString(_cursorIndexOfCartoonName);
        _result.setCartoonName(_tmpCartoonName);
        final String _tmpUrl;
        _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        _result.setUrl(_tmpUrl);
        final String _tmpSiteName;
        _tmpSiteName = _cursor.getString(_cursorIndexOfSiteName);
        _result.setSiteName(_tmpSiteName);
        final String _tmpLastUpdates;
        _tmpLastUpdates = _cursor.getString(_cursorIndexOfLastUpdates);
        _result.setLastUpdates(_tmpLastUpdates);
        final String _tmpIntroduction;
        _tmpIntroduction = _cursor.getString(_cursorIndexOfIntroduction);
        _result.setIntroduction(_tmpIntroduction);
        final String _tmpCoverSrc;
        _tmpCoverSrc = _cursor.getString(_cursorIndexOfCoverSrc);
        _result.setCoverSrc(_tmpCoverSrc);
        final int _tmpLastRead;
        _tmpLastRead = _cursor.getInt(_cursorIndexOfLastRead);
        _result.setLastRead(_tmpLastRead);
        final int _tmpLastReadLast;
        _tmpLastReadLast = _cursor.getInt(_cursorIndexOfLastReadLast);
        _result.setLastReadLast(_tmpLastReadLast);
        final int _tmpCatalogsSize;
        _tmpCatalogsSize = _cursor.getInt(_cursorIndexOfCatalogsSize);
        _result.setCatalogsSize(_tmpCatalogsSize);
        final long _tmpTime;
        _tmpTime = _cursor.getLong(_cursorIndexOfTime);
        _result.setTime(_tmpTime);
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
