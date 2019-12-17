package whut.brms.entity;

import java.sql.Date;

public class Rent {
    private String User_ID;
    private String Book_ID;
    private String book_name;
    private Date Rent_Date;
    private Date Return_Date;
    private String Rent_ID;
    private int handle;//0无请求，1租请求，2还请求

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "User_ID='" + User_ID + '\'' +
                ", Book_ID='" + Book_ID + '\'' +
                ", book_name='" + book_name + '\'' +
                ", Rent_Date=" + Rent_Date +
                ", Return_Date=" + Return_Date +
                ", Rent_ID='" + Rent_ID + '\'' +
                '}';
    }

    public void setRent_ID(String rent_ID) {
        Rent_ID = rent_ID;
    }

    public String getRent_ID() {
        return Rent_ID;
    }

    public void setBook_ID(String book_ID) {
        Book_ID = book_ID;
    }

    public void setRent_Date(Date rent_Date) {
        Rent_Date = rent_Date;
    }

    public void setReturn_Date(Date return_Date) {
        Return_Date = return_Date;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_ID() {
        return Book_ID;
    }

    public Date getRent_Date() {
        return Rent_Date;
    }

    public Date getReturn_Date() {
        return Return_Date;
    }

    public String getUser_ID() {
        return User_ID;
    }

}
