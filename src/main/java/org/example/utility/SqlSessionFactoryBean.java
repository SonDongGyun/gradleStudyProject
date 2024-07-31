package org.example.utility;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

public class SqlSessionFactoryBean {

    private static SqlSessionFactory sessionFactory = null;

    static {
        try {
            if(sessionFactory == null) {
                Reader reader = Resources.getResourceAsReader("sql-mapper-config");
                sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSessionInstance() {
        return sessionFactory.openSession();
    }
}
