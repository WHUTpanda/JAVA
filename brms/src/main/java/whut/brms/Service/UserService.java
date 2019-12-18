package whut.brms.Service;

import org.springframework.transaction.annotation.Transactional;
import whut.brms.entity.Users;
import whut.brms.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:zzx
 * @Date: 2019/11/26
 * @Time: 19:45
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    /**
     方法名：Login
     功能：输入用户名和密码，进行登录服务
     参数：用户名,密码
     返回值：1没有该用户；2登陆成功；3密码错误
     */
    public Object Login(String id,String password){

        Users user=userMapper.queryUserById(id);
        if(user==null)//没有该用户
            return 1;
        else if(user.getUser_Password().equals(password))
        {
            user.setUser_Password(null);
            return user;//登陆成功
        }
        else
            return 3;//密码错误
    }
    /**
     方法名：RegisterUser
    功能：输入用户名密码新建一个用户
    参数：用户名,密码和确认密码
    返回值：无
     */
    @Transactional
    public boolean RegisterUser(String UserId, String Password,String pn,String name)  {
        if(userMapper.queryUserById(UserId)== null)
        {
            try{
                userMapper.insertUser(UserId,Password,1,0,pn,name);
                return true;
            }catch (Exception e)
            {
                return false;
            }
        }
        return false;
    }
    /** 方法名：getUser
     功能：获取当前登录用户
     参数：用户名
     返回值：Users*/
    public Users getUserById(String User_ID){
        Users users=userMapper.queryUserById(User_ID);
        users.setUser_Password(null);
        return users;
    }
    /**方法名：UpgradeMember
    功能：将普通用户升级为会员
    参数：用户名
    返回值：无*/
    @Transactional
    public void UpgradeMember(String Username)  {
        userMapper.updateUser_Status(Username);
    }

    /**
     * 充值
     */
    @Transactional
    public boolean recharge(String userId,float amount)
    {
        try{
            userMapper.recharge(amount, userId);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    /**
     * 支付
     */
    @Transactional
    public int pay(String userId,float price)
    {
        try{
            Users users=userMapper.queryUserById(userId);
            if(users.getUser_Balance()>=price) {
                userMapper.pay(userId, price);
                return 1;
            }
            else {
                return 2;
            }
        }catch (Exception e)
        {
            return 0;
        }
    }

}