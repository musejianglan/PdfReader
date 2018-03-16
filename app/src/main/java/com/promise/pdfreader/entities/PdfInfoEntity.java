package com.promise.pdfreader.entities;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author: liulei
 * @date:2018/3/15
 */
@Entity
public class PdfInfoEntity {

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String fileName;
    @NotNull
    @Unique
    private String path;
    @NotNull
    private int currentPage;
    private int pageCount;
    private Date updateTime;
    private String cover;

    @Transient
    private boolean isAdded = false;

    @Generated(hash = 1420354955)
    public PdfInfoEntity(Long id, @NotNull String fileName, @NotNull String path,
            int currentPage, int pageCount, Date updateTime, String cover) {
        this.id = id;
        this.fileName = fileName;
        this.path = path;
        this.currentPage = currentPage;
        this.pageCount = pageCount;
        this.updateTime = updateTime;
        this.cover = cover;
    }

    @Generated(hash = 1037870450)
    public PdfInfoEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
