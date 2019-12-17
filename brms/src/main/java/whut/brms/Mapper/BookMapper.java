package whut.brms.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import whut.brms.entity.Book;

import java.util.Date;
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


    @Update("Update Book set Book_Status=#{Book_Status} where Book_ID=#{Book_ID}")
    void updateBookStatus(int Book_Status,String Book_ID);

    @Select("select * from Book  where ModelBook_ID=#{ModelBook_ID}")
    List<Book> queryBooksByModelbookId(String Id);

    @Select("select Book_ID from Book where Book_Status=#{Book_Status}")
    String queryBookIdByStatus(int status);
}
