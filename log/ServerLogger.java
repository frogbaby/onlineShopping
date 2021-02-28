package onlineShop.log;

import org.springframework.stereotype.Component;

// @component 定义这是一个bean， name is serverLogger
@Component(value = "serverLogger")
public class ServerLogger implements Logger {
    public void log(String info) {
        System.out.println("server log = " + info);
    }
}
