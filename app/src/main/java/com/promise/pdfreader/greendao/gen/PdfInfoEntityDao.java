package com.promise.pdfreader.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.promise.pdfreader.entities.PdfInfoEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PDF_INFO_ENTITY".
*/
public class PdfInfoEntityDao extends AbstractDao<PdfInfoEntity, Long> {

    public static final String TABLENAME = "PDF_INFO_ENTITY";

    /**
     * Properties of entity PdfInfoEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property FileName = new Property(1, String.class, "fileName", false, "FILE_NAME");
        public final static Property Path = new Property(2, String.class, "path", false, "PATH");
        public final static Property CurrentPage = new Property(3, int.class, "currentPage", false, "CURRENT_PAGE");
        public final static Property PageCount = new Property(4, int.class, "pageCount", false, "PAGE_COUNT");
        public final static Property UpdateTime = new Property(5, java.util.Date.class, "updateTime", false, "UPDATE_TIME");
        public final static Property Cover = new Property(6, String.class, "cover", false, "COVER");
    }


    public PdfInfoEntityDao(DaoConfig config) {
        super(config);
    }
    
    public PdfInfoEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PDF_INFO_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"FILE_NAME\" TEXT NOT NULL ," + // 1: fileName
                "\"PATH\" TEXT NOT NULL UNIQUE ," + // 2: path
                "\"CURRENT_PAGE\" INTEGER NOT NULL ," + // 3: currentPage
                "\"PAGE_COUNT\" INTEGER NOT NULL ," + // 4: pageCount
                "\"UPDATE_TIME\" INTEGER," + // 5: updateTime
                "\"COVER\" TEXT);"); // 6: cover
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PDF_INFO_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PdfInfoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getFileName());
        stmt.bindString(3, entity.getPath());
        stmt.bindLong(4, entity.getCurrentPage());
        stmt.bindLong(5, entity.getPageCount());
 
        java.util.Date updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindLong(6, updateTime.getTime());
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(7, cover);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PdfInfoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getFileName());
        stmt.bindString(3, entity.getPath());
        stmt.bindLong(4, entity.getCurrentPage());
        stmt.bindLong(5, entity.getPageCount());
 
        java.util.Date updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindLong(6, updateTime.getTime());
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(7, cover);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PdfInfoEntity readEntity(Cursor cursor, int offset) {
        PdfInfoEntity entity = new PdfInfoEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // fileName
            cursor.getString(offset + 2), // path
            cursor.getInt(offset + 3), // currentPage
            cursor.getInt(offset + 4), // pageCount
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)), // updateTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // cover
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PdfInfoEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFileName(cursor.getString(offset + 1));
        entity.setPath(cursor.getString(offset + 2));
        entity.setCurrentPage(cursor.getInt(offset + 3));
        entity.setPageCount(cursor.getInt(offset + 4));
        entity.setUpdateTime(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
        entity.setCover(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PdfInfoEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PdfInfoEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PdfInfoEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
