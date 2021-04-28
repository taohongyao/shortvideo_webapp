package shortvideo.declantea.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {


    @Autowired
    Environment environment;

    @RequestMapping(method = RequestMethod.GET)
    public Object healthStatus() throws UnknownHostException {
        Map<String,String> map=new HashMap<>();
        map.put("server_port",environment.getProperty("local.server.port"));
        map.put("private_ip", InetAddress.getLocalHost().getHostAddress());
        return map;
    }
}
