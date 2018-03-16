package com.promise.pdfreader.uitils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * @author: liulei
 * @date:2018/3/16
 */
public class RxUtil {

    /**
     * rxjava递归查询内存中的视频文件
     * @param f
     * @return
     */
    public static Observable<File> listFiles(final File f){


        if (f.isDirectory()) {
            return Observable.fromArray(f.listFiles()).flatMap(new Function<File, ObservableSource<File>>() {
                @Override
                public ObservableSource<File> apply(File file) throws Exception {
                    return listFiles(file);
                }
            });

        }else {
            return Observable.just(f).filter(new Predicate<File>() {
                @Override
                public boolean test(File file) throws Exception {

                    return file.exists() && file.canRead() && FileUtil.fileIsPdf(file);
                }
            });
        }
    }
}
