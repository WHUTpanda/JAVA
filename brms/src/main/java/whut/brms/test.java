package whut.brms;


import org.mybatis.spring.annotation.MapperScan;
import whut.brms.entity.Users ;
import whut.brms.Mapper.UserMapper ;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.junit.Test;
import java.io.Reader;
//测试是否成功和数据库连通
public class test {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader;
    public static void main(String []arg) {
        SqlSession session = null;
            try {
                reader = Resources.getResourceAsReader("Configuration.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                session = sqlSessionFactory.openSession();
                UserMapper userMapper=session.getMapper(UserMapper.class);
                Users users=userMapper.queryUserById("zzx");
                System.out.println(users.toString());
              //  session.commit();
            } catch (Exception e) {
               // session.rollback();
                e.printStackTrace();
            }finally {
                if(session!=null)
                    session.close();
            }
    }

    }