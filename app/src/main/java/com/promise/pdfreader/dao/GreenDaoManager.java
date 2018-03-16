package com.promise.pdfreader.dao;

import android.database.sqlite.SQLiteDatabase;

import com.promise.pdfreader.App;
import com.promise.pdfreader.greendao.gen.DaoMaster;
import com.promise.pdfreader.greendao.gen.DaoSession;
import com.promise.pdfreader.greendao.gen.PdfInfoEntityDao;

/**
 * @author: liulei
 * @date:2018/3/15
 */
public class GreenDaoManager {

    DaoMaster.OpenHelper helper;
    private static final String DB_NAME = "pdf_db";
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private static final GreenDaoManager ourInstance = new GreenDaoManager();

    public static GreenDaoManager getInstance() {
        return ourInstance;
    }

    private GreenDaoManager() {
        helper = new DaoMaster.DevOpenHelper(App.getInstance(), DB_NAME);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {

        return daoSession;
    }
}
