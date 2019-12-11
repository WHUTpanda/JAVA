package whut.brms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */
public class Users {
    private String User_ID;
    @JsonIgnore//在Json中隐藏
    private String User_Password;
    private Integer User_Status;
    private Integer Login_Status;

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Password() {
        return User_Password;
    }

    public void setUser_Password(String user_Password) {
        User_Password = user_Password;
    }

    public Integer getUser_Status() {
        return User_Status;
    }

    public void setUser_Status(Integer user_Status) {
        User_Status = user_Status;
    }

    public Integer getLogin_Status() {
        return Login_Status;
    }

    public void setLogin_Status(Integer login_Status) {
        Login_Status = login_Status;
    }

    @Override
    public String toString() {
        return "Users{" +
                "User_ID='" + User_ID + '\'' +
                ", User_Password='" + User_Password + '\'' +
                ", User_Status=" + User_Status +
                ", Login_Status=" + Login_Status +
                '}';
    }
}
