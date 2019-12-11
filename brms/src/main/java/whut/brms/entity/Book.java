package whut.brms.entity;

import java.util.Date;

public class Book {
    private String Book_ID;
    private int Book_Status;
    private Date Entrance_Date;
    private String ModelBook_ID;

    @Override
    public String toString() {
        return "Book{" +
                "Book_ID='" + Book_ID + '\'' +
                ", Book_Status=" + Book_Status +
                ", Entrance_Date=" + Entrance_Date +
                ", ModelBook_ID='" + ModelBook_ID + '\'' +
                '}';
    }



    public String getBook_ID() {
        return Book_ID;
    }

    public void setBook_ID(String book_ID) {
        Book_ID = book_ID;
    }

    public int getBook_Status() {
        return Book_Status;
    }

    public void setBook_Status(int book_Status) {
        Book_Status = book_Status;
    }

    public Date getEntrance_Date() {
        return Entrance_Date;
    }

    public void setEntrance_Date(Date entrance_Date) {
        Entrance_Date = entrance_Date;
    }

    public String getModelBook_ID() {
        return ModelBook_ID;
    }

    public void setModelBook_ID(String modelBook_ID) {
        ModelBook_ID = modelBook_ID;
    }
}
