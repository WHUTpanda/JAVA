package whut.brms.Mapper;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import whut.brms.entity.Rent;

import java.util.Date;
import java.util.List;

@Repository
public interface RentMapper {
    @Results({
            @Result(id = true, property = "Rent_ID", column = "Rent_ID"),
            @Result(property = "Book_ID", column = "Book_ID"),
            @Result(property = "Rent_Date", column = "Rent_Date"),
            @Result(column = "Return_Date", property = "Return_Date"),
            @Result(column = "User_ID", property = "User_ID"),
            @Result(column = "Handle", property = "Handle"),
            @Result(column = "Num", property = "num")


    })
    @Select("select * from Rent where Rent_ID=#{Rent_ID}")
    Rent queryRentById(String Rent_ID);
    @Select("select * from Rent where User_ID=#{User_ID}")
    List<Rent> queryRentByUserId(String User_ID);
    @Insert("insert into Rent(Rent_ID,User_ID,Book_ID,Rent_Date,Handle,Num)" +
            " values(#{Rent_ID},#{User_ID},#{Book_ID},#{Rent_Date},#{Handle},#{Num})")//#{}中的内容必须与参数中相同
    void insertRent(String Rent_ID, String User_ID, String Book_ID, Date Rent_Date,int Handle,int Num);
    @Update("update Rent set Return_Date =#{Return_Date} where Rent_ID=#{Rent_ID}")
    void updateRentDate(Date Return_Date,String Rent_ID);
    @Select("select * from Rent where Handle= 1 or Handle=2")
    List<Rent> queryRequesting();

    /**
     * 设置完成请求
     * @param rentId
     */
    @Update("update Rent set Handle =0 where Rent_ID=#{rentId}")
    void done(String rentId);
}
