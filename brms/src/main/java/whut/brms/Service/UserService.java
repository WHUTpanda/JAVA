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
    public int Login(String id,String password){

        Users user=userMapper.queryUserById(id);
        if(user==null)//没有该用户
            return 1;
        else if(user.getUser_Password().equals(password))
            return 2;//登陆成功
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
    public boolean RegisterUser(String Username, String Password)  {
        if(userMapper.queryUserById(Username)== null)
        {
            try{
                userMapper.insertUser(Username,Password,1,1);
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
        return userMapper.queryUserById(User_ID);
    }
    /**方法名：UpgradeMember
    功能：将普通用户升级为会员
    参数：用户名
    返回值：无*/
    @Transactional
    public void UpgradeMember(String Username)  {
        userMapper.updateUser_Status(Username);
    }

}