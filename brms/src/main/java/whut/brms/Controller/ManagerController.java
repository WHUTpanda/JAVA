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
                          @RequestParam(value = "type",required = true) int type)
    {
        try{
            managerService.define(Id,type);
        return true;
        }catch (Exception e)
        {
            return false;
        }

    }

}
