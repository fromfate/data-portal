package com.asiainfo.controller;

import com.asiainfo.dto.RestResponse;
import com.asiainfo.entity.User;
import com.asiainfo.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public RestResponse save(@Valid @RequestBody User user) {
        User result = userService.addUser(user);
        return new RestResponse(result,"添加成功！");
    }

    @DeleteMapping("/del/{userId}")
    public RestResponse delete(@PathVariable int userId){
        userService.delUser(userId);
        return new RestResponse("删除成功！");
    }


}
