
package com.iss.appdemo.db;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.iss.appdemo.bean.User;

public class DBUtil {
    public static Uri URI_USER = DemoProvider.buildUri(User.class);

    public static boolean addUser(Context context, User user) throws Exception {
        int result = updateUser(context, user);
        if(result==0){
            ContentResolver mResolver = context.getContentResolver();
            Uri uri = mResolver.insert(URI_USER, user.beanToValues());
            //通过返回的uri可以获取到当条数据插入的行号，插入失败为-1
            String path = uri.getLastPathSegment();
            if(path.equals("-1")){
                throw new Exception("insert error");
            }else{
                return true;
            }
        }else{
            return false;
        }
        
    }

    public static int deleteUser(Context context, User user) {
        ContentResolver mResolver = context.getContentResolver();
        int result = mResolver.delete(URI_USER, "name=?", new String[] {
            user.name
        });
        return result;
    }
    
    public static int updateUser(Context context,User user){
        ContentResolver mResolver = context.getContentResolver();
        int result = mResolver.update(URI_USER, user.beanToValues(), "name=?", new String[] {
            user.name
        });
        return result;
    }

    public static ArrayList<User> getUsers(Context context) {
        ContentResolver mResolver = context.getContentResolver();
        Cursor cursor = mResolver.query(URI_USER, null, null, null, null);
        ArrayList<User> beanList = new ArrayList<User>();
        while (cursor.moveToNext()) {
            User bean = new User();
            bean.cursorToBean(cursor);
            beanList.add(bean);
        }
        cursor.close();
        return beanList;
    }
}
