package whut.brms.Mapper;

import org.apache.ibatis.annotations.*;
import whut.brms.entity.Users;
import org.springframework.stereotype.Repository;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:20
 */
@Repository
public interface UserMapper {
    //Users queryUserById(String User_ID);
    @Results({
            @Result(id=true ,property = "User_ID", column = "User_ID"),
            @Result(property = "User_Password", column = "User_Password"),
            @Result(property = "User_Status", column = "User_Status"),
            @Result(column = "Login_Status",property = "Login_Status")
    })
 @Select( "select * from Users where User_ID = #{User_ID}" )
    Users queryUserById(String User_ID);
 @Insert("insert into Users (User_ID, User_Password, User_Status,Login_Status) values (#{User_ID},#{User_Password},#{User_Status},#{Login_Status})")
    void insertUser(String User_ID, String User_Password,int User_Status, int Login_Status );
 @Update("update Users set User_Status=2 where User_ID=#{User_ID}")
    void updateUser_Status(String User_ID);

}