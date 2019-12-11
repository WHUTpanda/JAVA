package whut.brms.entity;

public class ModelBook {
    private String ModelBook_ID;
    private String Book_Name;
    private String Book_Writer;
    private String Book_description;
    private float Book_Price;
    @Override
    public String toString() {
        return "ModelBook{" +
                "ModelBook_ID='" + ModelBook_ID + '\'' +
                ", Book_Name='" + Book_Name + '\'' +
                ", Book_Writer='" + Book_Writer + '\'' +
                ", Book_description='" + Book_description + '\'' +
                ", Book_Price=" + Book_Price +
                '}';
    }



    public String getModelBook_ID() {
        return ModelBook_ID;
    }

    public void setModelBook_ID(String modelBook_ID) {
        ModelBook_ID = modelBook_ID;
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
