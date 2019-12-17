package whut.brms.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import whut.brms.entity.ModelBook;

import java.util.List;

@Repository
public interface ModelBookMapper {
    @Results({
            @Result(id=true ,property = "ModelBook_ID", column = "ModelBook_ID"),
            @Result(property = "Book_Name", column = "Book_Name"),
            @Result(property = "Book_Writer", column = "Book_Writer"),
            @Result(column = "Book_description",property = "Book_description"),
            @Result(column = "Book_Price",property = "Book_Price"),
            @Result(column = "CanRentNum",property = "canRentNum"),
            @Result(column = "CanBuyNum",property = "canBuyNum")
    })
    @Select("select * from ModelBook where ModelBook_ID=#{ModelBook_ID}")//查
    ModelBook  queryModelBookById(String ModelBook_ID);

    @Insert("insert into ModelBook (ModelBook_ID,Book_Writer,Book_Name,Book_description,Book_Price) values(#{ModelBook_ID},#{Book_Writer},#{Book_Name},#{Book_description},#{Book_Price})")
    void insertModelBook(String ModelBook_ID,String Book_Writer,String Book_Name,String Book_description,float Book_Price);

    @Select("select * from ModelBook where Book_Writer =#{Book_Writer} and Book_Name =#{Book_Name}")
    ModelBook queryModelBookByWriterName(String Book_Writer,String Book_Name);

    @Select("select * from ModelBook where Book_Name like #{Book_Name} " )
    List<ModelBook> queryModelBookByInput(String Str);
    @Select("select * from ModelBook where Book_Name like #{Book_Name} " +
            "and ModelBook_ID in" +
            "(select ModelBook_ID from Book where Book_Status=1 or Book_Status=3)")
    List<ModelBook> queryRentModelBookByInput(String Str);

    @Select("select * from ModelBook where Book_Name like #{Book_Name} "+
            "and ModelBook_ID in" +
            "(select ModelBook_ID from Book where Book_Status=2 or Book_Status=3)" )//CONCAT('%',#{Book_Name},'%')sqlserver中无效
    List<ModelBook> queryPurchaseModelBookByInput(String Book_Name);

    @Select("select * from ModelBook where ModelBook_ID in " +
            "(select ModelBook_ID from Book where Book_Status=1 or Book_Status=3)")
    List<ModelBook> queryAllRent();

    @Select("select * from ModelBook where ModelBook_ID in " +
            "(select ModelBook_ID from Book where Book_Status=2 or Book_Status=3)")
    List<ModelBook> queryAllPurchase();

    @Select("select* from ModelBook where ModelBook_ID in" +
            "(select ModelBook_ID from Book where Book_Status=1 or Book_Status=2 or Book_Status=3)")
    List<ModelBook> queryAll();
}
