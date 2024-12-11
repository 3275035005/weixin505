package com.feedback.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feedback.entity.User;
import com.feedback.entity.query.UserAdminQuery;
import com.feedback.service.UserService;
import com.feedback.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对管理平台提供 学生管理接口
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页条件查询
     *
     * @param page           当前页码
     * @param limit          每页的大小
     * @param userAdminQuery
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public ResultVo pageQuery(@PathVariable long page,
                              @PathVariable long limit,
                              @RequestBody UserAdminQuery userAdminQuery) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        LambdaQueryWrapper<User> lambda = qw.lambda();

        // 按照用户状态查询
        if (!StringUtils.isEmpty(userAdminQuery.getStatus())) {
            lambda.eq(User::getStatus, userAdminQuery.getStatus());
        }

        // 按照用户姓名查询
        if (!StringUtils.isEmpty(userAdminQuery.getName())) {
            lambda.like(User::getName, userAdminQuery.getName());
        }

        // 按照用户账号查询
        if (!StringUtils.isEmpty(userAdminQuery.getAccount())) {
            lambda.like(User::getAccount, userAdminQuery.getAccount());
        }
        lambda.eq(User::getType, "0");
        // 按照注册时间排序
        lambda.orderByDesc(User::getCreated);
        Page<User> pageParam = new Page<>(page, limit);

        IPage<User> data = userService.page(pageParam, qw);
        return ResultVo.success(data);
    }


    /**
     * 新增接口
     *
     * @param UserModel
     * @return
     */
    @PostMapping("insert")
    public ResultVo insert(@RequestBody User UserModel) {
        UserModel.setType("0");
        userService.save(UserModel);
        return ResultVo.success();
    }

    /**
     * 修改接口
     *
     * @param UserModel
     * @return
     */
    @PutMapping("update")
    public ResultVo update(@RequestBody User UserModel) {

        userService.updateById(UserModel);
        return ResultVo.success();
    }

    /**
     * 删除接口
     *
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public ResultVo deleteById(@PathVariable String id) {
        userService.removeById(id);
        return ResultVo.success();
    }


      /**
     * 查询所有用户
     *
     * @return
     */
    @GetMapping("getUserAll")
    public ResultVo getUserAll() {
        List<User> list = userService.list(null);
        return ResultVo.success(list);
    }


    /**
     * 根据登录获取头像信息
     */
    @GetMapping("getInfo/{token}")
    public ResultVo getInfo(@PathVariable String token) {
        User user = userService.getById(token);
        user.setRoles(new String[]{"admin"});
        return ResultVo.success(user);
    }

    /**
     * 模拟退出登录
     *
     * @return
     */
    @GetMapping("logout")
    public ResultVo logout() {
        return ResultVo.success();
    }


    /**
     * 登录接口
     *
     * @return
     */
    @PostMapping("login")
    public ResultVo login(@RequestBody Map<String, String> map) {

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("account", map.get("account"));
        qw.eq("password", map.get("password"));
        qw.eq("type", map.get("type"));
        User one = userService.getOne(qw);
        if (one == null) {
            return ResultVo.fail("账号或密码不正确");
        }
        if ("1".equals(one.getStatus())) {
            return ResultVo.fail("账号已被禁用");
        }


        Map<String, String> map2 = new HashMap<>();
        map2.put("token", one.getId());
        return ResultVo.success(map2);
    }

    /**
     * 注册接口
     * @return
     */
    @PostMapping("sign")
    public ResultVo sign(@RequestBody User user) {
        user.setStatus("0");
        userService.save(user);
        return ResultVo.success();
    }


    /**
     * 通过账号获取用户信息
     */
    @GetMapping("getUserInfo/{account}")
    public ResultVo getUserInfo(@PathVariable String account){
        User user = userService.getById(account);
        return ResultVo.success(user);
    }

}
