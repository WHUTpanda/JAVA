package whut.brms.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import whut.brms.entity.Book;

import java.util.List;

@Repository
public interface BookMapper {

    @Results({
            @Result(id=true ,property = "Book_ID", column = "Book_ID"),
            @Result(property = "Entrance_Date", column = "Entrance_Date"),
            @Result(property = "Book_Status", column = "Book_Status"),
            @Result(column = "ModelBook_ID",property = "ModelBook_ID")
    })
    @Select("select * from Book where Book_ID=#{Book_ID}")
    Book queryBookById(String Id);

    @Select("select * from Book where Book_Name=#{input}")
    List<Book> searchByInput(String input);

    @Select("select * from Book where Num>0")
    List<Book> showAll();

    @Update("update Book set Num=Num-#{num} where Book_ID = #{bookId}")
    void subBook(int num,String bookId);
}
