package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import com.baizhi.service.UserService;
import com.baizhi.util.SecurityCode;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("selectUsersByStarId")
    @ResponseBody
    public Map<String,Object> selectUsersByStarId(String starId,Integer page,Integer rows){
        Map<String, Object> map = userService.selectUsersByStarId(starId, page, rows);

        return map;
    }
    @RequestMapping("selectAll")
    @ResponseBody
    public Map<String,Object> selectAll(Integer page,Integer rows){
        Map<String, Object> map = userService.selectAll(page, rows);
        return map;
    }

    @RequestMapping("export")
    public void export(HttpServletResponse resp){
//        准备数据
//        userService...
        List<User> list = userService.export();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"), com.baizhi.entity.User.class, list);

        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文下载名乱码
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(new FileOutputStream(new File("D:/cfmz_lcx/user.xls")));
            workbook.close();
            System.out.println("导出成功");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(User user){
        HashMap<String, Object> map = new HashMap<>();
        try{
            userService.update(user);
            map.put("status",true);
        }catch(Exception e){
            map.put("status",false);
            map.put("message",e.getMessage());
        }

        return map;
    }
    @RequestMapping("del")
    @ResponseBody
    public Map<String,Object> del(String id){
        HashMap<String, Object> map = new HashMap<>();
        try {
            userService.del(id);
            map.put("status",true);
        }catch (Exception e){
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("echarts")
    @ResponseBody
    public Map<String,List<Integer>> echarts(){
        HashMap<String, List<Integer>> map = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();

        List<Users> allGroupBy = userService.findAllGroupBy();
        List<Users> users = userService.selectAllDroupBy();
        for (int i = 1; i < 7; i++) {
            list.add(i);
        }
        map.put("name",list);
        for (Users user : allGroupBy) {
          list1.add(user.getCounts());
        }
        map.put("nvs",list1);
        for (Users user : users) {
            list2.add(user.getCounts());
        }
        map.put("nans",list2);
        return map;
    }
    @RequestMapping("findCode")
    @ResponseBody
    public Map<String,Object> findCode(HttpServletRequest request1) throws Exception{
        HashMap<String, Object> map = new HashMap<>();
        String code = String.valueOf((int)((Math.random()*9+1)*1000));

        System.out.println(code);
        System.out.println(code);
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIaEI24AEjv8Xr";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "P9CEJSnvgfNXDSbKpdu97tW61KgbSN";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
//        13949121813,15732572971,13331025260,13527628084,
        request.setPhoneNumbers("15139101825");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("天地方圆");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_170835879");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{'code':"+code+"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println(sendSmsResponse.getCode());
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            System.out.println("请求成功");
            request1.getSession().setAttribute("code",code);
             map.put("status",true);

        }

        return map;
    }
    @RequestMapping("add")
    @ResponseBody
    public Map<String,Object> add(User user,HttpServletRequest request,String code){

        String code1 = (String)request.getSession().getAttribute("code");
        HashMap<String, Object> map = new HashMap<>();
        if(code1.equals(code)){
            try{
                String id = userService.add(user);
                map.put("status",true);
                map.put("message",id);

            }catch (Exception e){
                map.put("status",false);
                map.put("message",e.getMessage());
            }
        }else {
            map.put("status",false);
            map.put("message","验证码输入错误");
        }

        return  map;
    }
}
