package whut.brms.Controller;

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
    public int Login(@RequestParam(value = "User_ID",required = true) String User_ID,
                     @RequestParam(value = "User_Password",required = true) String User_Password)
    {
         return userService.Login(User_ID,User_Password);
    }
    @RequestMapping(value = "/Register",method = RequestMethod.POST)
    @ResponseBody
    public boolean  Register(@RequestParam(value = "User_ID",required = true) String User_ID,
                          @RequestParam(value = "User_Password",required = true) String User_Password) {
        try {
            boolean rt=userService.RegisterUser(User_ID, User_Password);
            return rt;
        }catch (Exception e)
        {
            return false;
        }
    }
    @RequestMapping(value = "/Upgrade",method =RequestMethod.GET)
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

}