package whut.brms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
}
