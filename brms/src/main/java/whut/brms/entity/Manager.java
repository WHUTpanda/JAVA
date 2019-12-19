package whut.brms.entity;

public class Manager {
    private String id;
    private String bookName;
    private String bookId;
    private int handle;//1为租借中，2为购买，3为归还
    private int num;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
