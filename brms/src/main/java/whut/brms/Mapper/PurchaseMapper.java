package whut.brms.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import whut.brms.entity.Purchase;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseMapper {
    @Results({
            @Result(property = "Book_ID", column = "Book_ID"),
            @Result(column = "User_ID", property = "User_ID"),
            @Result(property = "Purchase_Date", column = "Purchase_Date"),
    })
    @Select("select * from Purchase where User_ID=#{User_ID}")
    List<Purchase> queryPurchaseByUserId(String User_ID);
    @Insert("insert into Purchase(Book_ID,User_ID,Purchase_Date) values(#{Book_ID},#{User_ID},#{Purchase_Date})")
    void insertPurchase(String Book_ID, String User_ID, Date Purchase_Date);
}
