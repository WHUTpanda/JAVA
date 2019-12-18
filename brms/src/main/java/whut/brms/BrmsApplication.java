package whut.brms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@MapperScan("whut.brms.Mapper") //扫描的mapper
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class BrmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrmsApplication.class, args);
    }
}