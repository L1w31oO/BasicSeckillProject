import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 启动类
 */

@SpringBootApplication(scanBasePackages = {"com.lw"})
@MapperScan("com.lw.dao")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
