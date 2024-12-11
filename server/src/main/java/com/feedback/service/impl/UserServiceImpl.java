package com.feedback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feedback.entity.User;
import com.feedback.mapper.UserMapper;
import com.feedback.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
