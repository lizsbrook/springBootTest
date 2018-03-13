package com.shangde.gao.web;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.shangde.gao.domain.BookBean;
import com.shangde.gao.service.BookServiceImpl;
import com.shangde.gao.util.HttpClientUtils;
import com.shangde.gao.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class HelloController {

    @Value("${wxLogin.url}")
    private String wxLoginUrl;
    @Value("${appid}")
    private String appid;
    @Value("${secret}")
    private String secret;

    @Value("${assessment.url}")
    private String assessmentUrl;

    @Value("${submission.url}")
    private String submissionUrl;

    @Autowired
    private BookBean bookBean;

    @RequestMapping("/")
    public String greeting() {
        return "Hello World!";
    }

    @RequestMapping("/now")
    String hehe() {
        return "现在时间：" + (new Date()).toLocaleString();
    }

    @RequestMapping("/users/{username}")
    public String userProfile(@PathVariable("username") String username) {
        return String.format("user %s", username);
    }

    @RequestMapping("/posts/{id}")
    public String post(@PathVariable("id") int id) {
        return String.format("post %d", id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return "Login Page";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost() {
        return "Login Post Request";
    }


    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {

        return "hello: "+name+"</br>assessmentUrl: "+submissionUrl+"</br>bookBean: "+bookBean.getAuthor();

    }

    //测试插入数据库
    @RequestMapping("/insertBook")
    public String hello() {
//        BookBean bookBean = new BookBean();
//        bookBean.setAuthor("gaoming");
//        bookBean.setName("xiyouji");
//        bookBean.setPrice("100");
//        bookServiceImpl.insert(bookBean);
//        return bookBean.toString();
        return "";
    }

    //测试url中参数和Head中参数的传递
    @RequestMapping("/testRequestParam/{city}")
    public String testJson(@PathVariable("city") String cityName,String teacherName,String teacherOneRemark) {

        return "teacherName="+teacherName+"</br>teacherOneRemark="+teacherOneRemark+"</br>cityName="+cityName;
    }

    @RequestMapping("/mapJsonTest")
    public String testMapJson() {
        Map param = new HashMap();
        param.put("userId","11111");
        param.put("role","teacher");
        return JsonUtils.toJson(param);
    }

    @RequestMapping("/doTest")
    public String doTest(HttpServletRequest request, HttpServletResponse response)
    {
        // TODO Auto-generated method stub

        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");

        //获取微信小程序get的参数值并打印
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map param = new HashMap();
        System.out.println("username="+username+" ,password="+password);
        param.put("userId",username);
        param.put("password",password);
        return JsonUtils.toJson(param);
    }
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    public String userLogin(String code,HttpServletResponse response)
    {
        String url = wxLoginUrl + "?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        System.out.println("登录请求参数" + url);
        String result = HttpClientUtils.getInstance().doPost(url);
        System.out.println("登录请求返回结果"+result);

        Map map = JsonUtils.toBean(result, Map.class);
        String openid = (String) map.get("openid");
        if (StringUtils.isEmpty(openid)) {
            Map returnjSON = new HashMap();
            returnjSON.put("Status","Fairule!");
            return "no valid user info!!!";
        }
        //注册用户
        //userService.insert(new User(openid, (String) map.get("unionid")));
        //设置session key
        //redisService.setSessionKey((String) map.get("openid"), (String) map.get("session_key"));
        //map.remove("session_key");
        map.put("appid", appid);
        map.put("secret",secret);
        String clientData = JsonUtils.toJson(map);
        Map returnjSON = new HashMap();
        returnjSON.put("Status","SUCCESS");
        returnjSON.put("dataResult",clientData);
        return JsonUtils.toJson(returnjSON);
    }

}
