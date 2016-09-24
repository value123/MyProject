
package com.iss.appdemo.bean;

import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

import com.iss.bean.BaseBean;
import com.iss.db.TableColumn;

public class User extends BaseBean<User> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @TableColumn(type = TableColumn.Types.TEXT,  isIndex = true, isNotNull = true)
    public String name;

    @TableColumn(type = TableColumn.Types.TEXT)
    public String password;

    public InfoState infoState = InfoState.normal;

    
    
    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    @Override
    public User parseJSON(JSONObject jsonObj) {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public User cursorToBean(Cursor cursor) {
        name = cursor.getString(cursor.getColumnIndex("name"));
        password = cursor.getString(cursor.getColumnIndex("password"));
        return this;
    }

    @Override
    public ContentValues beanToValues() {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("password", password);
        return values;
    }
    
    public enum InfoState {
        normal,edit,delete
    }
}
