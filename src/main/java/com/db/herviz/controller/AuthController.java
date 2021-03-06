package com.db.herviz.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.db.herviz.domain.BusinessException;
import com.db.herviz.domain.ResponseX;
import com.db.herviz.entity.Customer;
import com.db.herviz.entity.Staff;
import com.db.herviz.entity.User;
import com.db.herviz.service.CustomerService;
import com.db.herviz.service.StaffService;
import com.db.herviz.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Description:
 * @Author Rootian
 * @Date 2022-04-17
 */
@RestController
@Api
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomerService customerService;


    /**
     * @Description User Register Request
     * @Author Rootian
     * @Date 2022-04-20
     * @param: register json parameter
     * @return java.lang.String
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody String body) {
        // todo 用户名重复
        User user = JSON.parseObject(body, User.class);
        userService.register(user);
        Customer customer = new Customer();
        customer.setUId(user.getId());
        customer.setType("i");
        customerService.save(customer);
        return ResponseX.success(customer);
    }


    /**
     * @Description User Login Request
     * @Author Rootian
     * @Date 2022-04-20
     * @param: login json parameter
     * @return java.lang.String
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody String body) {
        JSONObject obj = JSONObject.parseObject(body);
        String username = obj.getString("username");
        String password = obj.getString("password");

        try {
            return ResponseX.success(userService.login(username, password));
        } catch (BusinessException e) {
            return ResponseX.fail(e.getMessage());
        }
    }

    /**
     * @Description User Logout
     * @Author Rootian
     * @Date 2022-04-20
     * @param:
     * @return java.lang.String
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        StpUtil.logout();
        return ResponseX.success(null);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String passwordReset(@RequestBody String body) {

        if (!StpUtil.isLogin()) {
            return ResponseX.fail("please log in first");
        }

        JSONObject obj = JSONObject.parseObject(body);
        String oldPassword = obj.getString("oldPw");
        String newPassword = obj.getString("newPw");

        try {
            userService.passwordReset(oldPassword, newPassword);
        } catch (BusinessException e) {
            return ResponseX.fail(e.getMessage());
        }

        return ResponseX.success(null);
    }

    @RequestMapping(value = "/registerStaff", method = RequestMethod.POST)
    public String registerStaff(@RequestBody String body) {
        // todo 用户名重复
        Staff staff = JSON.parseObject(body, Staff.class);
        staffService.register(staff);
        return ResponseX.success(null);
    }

    @RequestMapping(value = "/loginStaff", method = RequestMethod.POST)
    public String loginStaff(@RequestBody String body) {
        JSONObject obj = JSONObject.parseObject(body);
        String username = obj.getString("username");
        String password = obj.getString("password");

        try {
            return ResponseX.success(staffService.login(username, password));
        } catch (BusinessException e) {
            return ResponseX.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "/logoutStaff", method = RequestMethod.GET)
    public String logoutStaff() {
        StpUtil.logout();
        return ResponseX.success(null);
    }

}
