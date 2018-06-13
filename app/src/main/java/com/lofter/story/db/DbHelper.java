package com.lofter.story.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lofter.story.entity.StoryVO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * PACKAGE_NAME：com.lofter.story.db
 * DATE：2018/1/5 17:11
 * USER: xiantao.jiang
 * DESCRIBE:
 */

public class DbHelper {
    // 数据库存储路径
    final static String filePath = "data/data/com.lofter.story/databases/story.db";
    // 数据库存放的文件夹
    final static String pathStr = "data/data/com.lofter.story/databases/";

    public static SQLiteDatabase openDatabase(Context context) {
        File jhPath = new File(filePath);
        // 查看数据库文件是否存在
        if (jhPath.exists()) {
            // 存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        } else {
            // 不存在先创建文件夹
            File path = new File(pathStr);
            if (!path.exists()) {
                if (path.mkdir()) {
                    Log.i("story", "创建成功");
                } else {
                    Log.i("story", "创建失败");
                }
            }
            try {
                // 得到资源
                AssetManager am = context.getAssets();
                // 得到数据库的输入流
                InputStream is = am.open("story.db");
                Log.i("story", is + "");
                // 用输出流写到SDcard上面
                FileOutputStream fos = new FileOutputStream(jhPath);
                // 创建byte数组 用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                // 最后关闭就可以了
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            // 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabase(context);
        }
    }

    public static List<StoryVO> getStoryList(Context context) {
        SQLiteDatabase db = openDatabase(context);
        Cursor cursor = db.rawQuery("select * from story", new String[]{});
        List<StoryVO> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            StoryVO countyVO = new StoryVO();
            countyVO.setId(cursor.getInt(cursor.getColumnIndex("id")));
            countyVO.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            countyVO.setContent(cursor.getString(cursor.getColumnIndex("content")));
            countyVO.setCount(cursor.getString(cursor.getColumnIndex("count")));
            list.add(countyVO);
        }

        cursor.close();
        db.close();
        return list;
    }

    public static void insertStory(Context context, String title, String content) {
        SQLiteDatabase db = openDatabase(context);
        Cursor cursor = db.rawQuery("insert into story (title,content,count) values(?,?,?)", new
                String[]{title, content, "0"});
        cursor.close();
        db.close();
    }
}
