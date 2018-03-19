package com.promise.pdfreader.uitils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: liulei
 * @date:2018/3/16
 */
public class SDCardUtil {

    private SDCardUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断 SD 卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable(Context context) {
        return !getSDCardPaths(context).isEmpty();
    }

    /**
     * 判断sd卡是否可用
     * @author:liulei
     *
     * created at 2018/3/16 13:19
     */
    public static boolean isSDCardEnable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取sdcard根路径
     * @author:liulei
     *
     * created at 2018/3/16 13:24
     */
    public static String getSdCardRootPath(){
        if (isSDCardEnable()) {
            return Environment.getExternalStorageDirectory().getPath();//获取根目录

        }else {
            return null;
        }
    }

    /**
     * 获取sdcard根路径
     * @author:liulei
     *
     * created at 2018/3/16 13:24
     */
    public static File getSdCardRootFile(){
        if (isSDCardEnable()) {
            return Environment.getExternalStorageDirectory();//获取根文件

        }else {
            return null;
        }
    }

    /**
     * 获取sdcard download 路径
     * @author:liulei
     *
     * created at 2018/3/16 13:24
     */
    public static File getDownloadFile(){
        if (isSDCardEnable()) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        }else {
            return null;
        }
    }





    /**
     * 获取 SD 卡路径
     *
     * @param removable true : 外置 SD 卡<br>false : 内置 SD 卡
     * @return SD 卡路径
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static List<String> getSDCardPaths(Context context,boolean removable) {
        List<String> paths = new ArrayList<>();
        StorageManager mStorageManager = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean res = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable == res) {
                    paths.add(path);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 获取 SD 卡路径
     *
     * @return SD 卡路径
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static List<String> getSDCardPaths(Context context) {
        StorageManager storageManager = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        List<String> paths = new ArrayList<>();
        try {
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
            getVolumePathsMethod.setAccessible(true);
            Object invoke = getVolumePathsMethod.invoke(storageManager);
            paths = Arrays.asList((String[]) invoke);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return paths;
    }
}
