package whut.brms.entity;

public class Book {
    private String Book_ID;
    private String Book_Name;
    private String Book_Writer;
    private String Book_description;
    private float Book_Price;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getBook_ID() {
        return Book_ID;
    }

    public void setBook_ID(String book_ID) {
        Book_ID = book_ID;
    }

    public String getBook_Name() {
        return Book_Name;
    }

    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }

    public String getBook_Writer() {
        return Book_Writer;
    }

    public void setBook_Writer(String book_Writer) {
        Book_Writer = book_Writer;
    }

    public String getBook_description() {
        return Book_description;
    }

    public void setBook_description(String book_description) {
        Book_description = book_description;
    }

    public float getBook_Price() {
        return Book_Price;
    }

    public void setBook_Price(float book_Price) {
        Book_Price = book_Price;
    }
}
