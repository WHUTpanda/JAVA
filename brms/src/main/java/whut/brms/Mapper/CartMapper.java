package whut.brms.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import whut.brms.entity.ShoppingCart;

import java.util.Date;
import java.util.List;

@Repository
public interface CartMapper {
    @Results( id = "cartMap",value = {//对一个方法进行注解
            @Result(id=true ,property = "cartId", column = "Cart_ID"),
            @Result(property = "bookId", column = "Book_ID"),
            @Result(property = "userId", column = "User_ID"),
            @Result(column = "Add_Date",property = "addDate"),
            @Result(column = "Num",property = "num")
    })
    @Select("select * from ShoppingCart where Cart_ID=#{Cart_ID}")//只有@Results和@select相邻@Results才能生效
    ShoppingCart queryById(String Cart_ID);
    @Insert("insert into ShoppingCart (Cart_ID,Book_ID,User_ID,Add_Date,Num)" +
            "values(#{Cart_ID},#{Book_ID},#{User_ID},#{Add_Date},#{Num})")
    void insertCart(String Cart_ID, String Book_ID, String User_ID, Date Add_Date,int Num);


    @Delete("delete from ShoppingCart where Cart_ID=#{str}")
    void delCart(String str);

    @Select("select * from ShoppingCart where User_ID=#{User_ID}")
    @ResultMap("cartMap")
    List<ShoppingCart> queryUserCart(String User_ID);
}
