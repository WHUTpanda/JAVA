package whut.brms.entity;

import java.util.Date;

public class ShoppingCart {
    private String cartId;
    private String userId;
    private String bookId;
    private Date addDate;
    private boolean type;
    private float price;
    private boolean isFinish;

    public String getCartId() {
        return cartId;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cartId='" + cartId + '\'' +
                ", userId='" + userId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", addDate=" + addDate +
                ", type=" + type +
                ", price=" + price +
                ", isFinsh=" + isFinish +
                '}';
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isFinsh() {
        return isFinish;
    }

    public void setFinsh(boolean finsh) {
        isFinish = finsh;
    }
}
