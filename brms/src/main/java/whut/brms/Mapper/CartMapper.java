package whut.brms.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import whut.brms.entity.ShoppingCart;

import java.util.Date;
import java.util.List;

@Repository
public interface CartMapper {
    @Results({
            @Result(id=true ,property = "cartId", column = "Cart_ID"),
            @Result(property = "bookId", column = "Book_ID"),
            @Result(property = "userId", column = "User_ID"),
            @Result(column = "Add_Date",property = "addDate"),
            @Result(column = "Type",property = "type"),
            @Result(column = "Price",property = "price"),
            @Result(column = "IsFinish",property = "isFinish"),
            @Result(column = "Num",property = "num")
    })
    @Insert("insert into ShoppingCart (Cart_ID,Book_ID,User_ID,Add_Date,Type,Price,IsFinish)" +
            "values(#{Cart_ID},#{Book_ID},#{User_ID},#{Add_Date},#{Type},#{Price},#{IsFinish})")
    void insertCart(String Cart_ID, String Book_ID, String User_ID, Date Add_Date,boolean Type,float Price,boolean IsFinish);
    @Select("select * from ShoppingCart where Cart_ID=#{Cart_ID}")
    ShoppingCart queryById(String str);

    @Update("update ShoppingCart set IsFinish=#{IsFinish}")
    void isDone(boolean IsFinish);

    @Delete("delete from ShoppingCart where Cart_ID=#{str}")
    void delCart(String str);

    @Select("select * from ShoppingCart where userId=#{userId} and IsFinish=1")
    List<ShoppingCart> queryUserCart(String userId);
}
