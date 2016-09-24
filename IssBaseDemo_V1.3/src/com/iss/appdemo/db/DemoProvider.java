package com.iss.appdemo.db;

import com.iss.appdemo.bean.User;
import com.iss.db.DBConfig;
import com.iss.db.IssContentProvider;
import com.iss.db.IssDBFactory;

public class DemoProvider extends IssContentProvider {

    @Override
    public void init() {
        DBConfig config = new DBConfig.Builder()
                                        .setName("appdemo.db")
                                        .setVersion(1)
                                        .setAuthority("com.iss.appdemo")
                                        .addTatble(User.class)
                                        .build();
        IssDBFactory.init(getContext(), config);
    }

}
