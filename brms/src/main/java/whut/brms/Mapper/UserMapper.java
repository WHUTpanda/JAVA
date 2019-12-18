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
            @Result(column = "User_Balance",property = "User_Balance"),
            @Result(column = "PhoneNumber",property = "phoneNum"),
            @Result(column = "User_Name",property = "userName")
    })
 @Select( "select * from Users where User_ID = #{User_ID}" )
    Users queryUserById(String User_ID);
 @Insert("insert into Users (User_ID, User_Password, User_Status,User_Balance,PhoneNumber,User_Name) " +
         "values (#{User_ID},#{User_Password},#{User_Status},#{User_Balance},#{PhoneNumber},#{User_Name})")
    void insertUser(String User_ID, String User_Password,int User_Status,float User_Balance,String PhoneNumber,String User_Name);
 @Update("update Users set User_Status=2 where User_ID=#{User_ID}")
    void updateUser_Status(String User_ID);
 @Update("update Users set User_Balance=User_Balance+#{amount} where User_ID=#{userId}")
    void recharge(float amount,String userId);
    @Update("update Users set User_Balance=User_Balance-#{price} where User_ID=#{userId}")
    void pay(String userId,float price);
}