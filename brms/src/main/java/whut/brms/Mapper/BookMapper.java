package whut.brms.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import whut.brms.entity.Book;

import java.util.List;

@Repository
public interface BookMapper {

    @Results({
            @Result(id=true ,property = "Book_ID", column = "Book_ID"),
            @Result(property = "Book_Name", column = "Book_Name"),
            @Result(property = "Book_Writer", column = "Book_Writer"),
            @Result(column = "Book_description",property = "Book_description"),
            @Result(column = "Book_Price",property = "Book_Price"),
            @Result(column = "Num",property = "num")
    })
    @Select("select * from Book where Book_ID=#{Book_ID}")
    Book queryBookById(String Id);

    @Select("select * from Book where Book_Name like #{input}")
    List<Book> searchByInput(String input);

    @Select("select * from Book where Num>0")
    List<Book> showAll();

    @Update("update Book set Num=Num-#{num} where Book_ID = #{bookId}")
    void subBook(int num,String bookId);

    @Update("update Book set Num=Num+#{num} where Book_ID = #{bookId}")
    void addBook(int num,String bookId);
}
