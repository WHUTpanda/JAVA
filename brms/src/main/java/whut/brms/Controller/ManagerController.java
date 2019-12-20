package whut.brms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import whut.brms.Service.ManagerService;
import whut.brms.entity.Manager;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @RequestMapping("/show")
    @ResponseBody
    public List<Manager> show()
    {
        return managerService.showAll();
    }

    @PostMapping("/define")
    @ResponseBody
    public boolean define(@RequestParam(value = "id",required = true)String Id,
                          @RequestParam(value = "handle",required = true) int handle,
                          @RequestParam(value = "num",required = true) int num)
    {
        try{
            managerService.define(Id,handle,num);
        return true;
        }catch (Exception e)
        {
            return false;
        }

    }
    @PostMapping("/search")
    @ResponseBody
    public List<Manager> search(@RequestParam(value = "input",required = true) String input)
    {
        try {

            return managerService.searchManager(input);
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 从管理员处充值
     * @param User_ID
     * @param money
     * @return返回0为出错，1为成功，2为没有该用户
     */
    @PostMapping("/recharge")
    @ResponseBody
    public int recharge(@RequestParam(value = "User_ID",required = true) String User_ID,
                        @RequestParam(value = "money",required = true) float money)
    {
        return managerService.recharge(User_ID, money);
    }

}
