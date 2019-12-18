package whut.brms.Controller;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import whut.brms.entity.Users;
import whut.brms.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author:zzx
 * @Date: 2019/11/26
 * @Time: 22:11
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    @ResponseBody
    public Object Login(@RequestParam(value = "User_ID",required = true) String User_ID,
                      @RequestParam(value = "User_Password",required = true) String User_Password)
    {
         return userService.Login(User_ID,User_Password);
    }
    @RequestMapping(value = "/Register",method = RequestMethod.POST)
    @ResponseBody
    public boolean  Register(@RequestParam(value = "User_ID",required = true) String User_ID,
                            @RequestParam(value = "User_Password",required = true) String User_Password,
                             @RequestParam(value = "pn",required = true) String pn,
                             @RequestParam(value = "name",required = true) String name) {
        try {
            boolean rt=userService.RegisterUser(User_ID, User_Password,pn,name);
            return rt;
        }catch (Exception e)
        {
            return false;
        }
    }
    @RequestMapping(value = "/Upgrade",method =RequestMethod.POST)
    public boolean Upgrade(@RequestParam(value = "User_ID",required = true) String User_ID)
    {
        try {
            userService.UpgradeMember(User_ID);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }
    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    @ResponseBody
    public Users getUser(@RequestParam(value = "User_ID",required = true) String User_ID) {return userService.getUserById(User_ID);
    }

    /**
     * 支付
     * @param User_ID
     * @param price
     * @return
     */
    @PostMapping(value = "/pay")
    @ResponseBody
    public int pay(@RequestParam(value = "User_ID",required = true) String User_ID,
                   @RequestParam(value = "price",required = true) float price)
    {
        return userService.pay(User_ID,price);
    }

    /**
     * 充值
     * @param User_ID
     * @param amount
     * @return
     */
    @PostMapping("/recharge")
    @ResponseBody
    public boolean recharge(@RequestParam(value = "User_ID",required = true) String User_ID,
                            @RequestParam(value = "amount",required = true) float amount)
    {
        return userService.recharge(User_ID,amount);
    }

}