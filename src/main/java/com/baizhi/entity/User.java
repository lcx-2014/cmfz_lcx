package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "名称")
    private String username;
    @Excel(name = "密码")
    private String password;
    private String salt;
    @Excel(name = "昵称")
    private String nickname;
    @Excel(name = "地址")
    private String address;
    @Excel(name = "签名")
    private String sign;
    @Excel(name = "头像")
    private String photo;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "状态")
    private String  status;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间")
    private Date createDate;
    private String starId;

}
