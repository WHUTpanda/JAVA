package whut.brms.entity;

import java.sql.Date;

public class Purchase {
    private String User_ID;
    private String Book_ID;
    private String book_name;
    private float book_price;
    private Date Purchase_Date;
    @Override
    public String toString() {
        return "Purchase{" +
                "User_ID='" + User_ID + '\'' +
                ", Book_ID='" + Book_ID + '\'' +
                ", book_name='" + book_name + '\'' +
                ", book_price=" + book_price +
                ", Purchase_Date=" + Purchase_Date +
                '}';
    }


    public void setBook_ID(String book_ID) {
        Book_ID = book_ID;
    }

    public void setPurchase_Date(Date purchase_Date) {
        Purchase_Date = purchase_Date;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setBook_price(float book_price) {
        this.book_price = book_price;
    }

    public float getBook_price() {
        return book_price;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getBook_ID() {
        return Book_ID;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public Date getPurchase_Date() {
        return Purchase_Date;
    }
}

