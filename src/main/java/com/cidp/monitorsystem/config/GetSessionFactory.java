package com.cidp.monitorsystem.config;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.InputStream;
/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/312:25
 **/

public class GetSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;
    private GetSessionFactory(){

    }

    synchronized public static SqlSessionFactory getSqlSessionFactory(){
        if(sqlSessionFactory==null){
            String resources="config/Mybatis-confg.xml";
            InputStream inputStream=null;
            try {
                inputStream= Resources.getResourceAsStream(resources);
            }catch (Exception e){
                e.printStackTrace();
            }
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        }
        return sqlSessionFactory;

    }
}
