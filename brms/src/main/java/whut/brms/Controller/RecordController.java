package whut.brms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import whut.brms.Service.BookService;
import whut.brms.Service.RecordService;
import whut.brms.entity.Purchase;
import whut.brms.entity.Rent;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService recordService;
    //查询租借书籍记录
    @PostMapping("/rent")
    @ResponseBody
    List<Rent> queryRentRecord(@RequestParam(value = "User_ID")String User_ID)
    {
        return recordService.QueryRentRecord(User_ID);
    }
    //查询购买书籍记录
    @PostMapping("/purchase")
    @ResponseBody
    List<Purchase> queryPurchaseRecord(@RequestParam(value = "User_ID")String User_ID)
    {
        return recordService.QueryPurchaseRecord(User_ID);
    }
}
