package com.asiainfo.controller;

import com.asiainfo.dto.PageDto;
import com.asiainfo.dto.RestResponse;
import com.asiainfo.entity.User;
import com.asiainfo.exception.BadRequestException;
import com.asiainfo.exception.ResourceNotFoundException;
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
        return new RestResponse(result,"添加成功");
    }

    @DeleteMapping("/del/{userId}")
    public RestResponse delete(@PathVariable int userId) throws ResourceNotFoundException {
        User user = userService.queryById(userId);
        if(user == null){
            throw new ResourceNotFoundException("没有找到要删除的用户！");
        }
        userService.delUser(userId);
        return new RestResponse("删除成功");
    }

    @PutMapping("/update")
    public RestResponse update(@Valid @RequestBody User user) throws BadRequestException, ResourceNotFoundException {
        if(user.getUserId() == null) {
            throw new BadRequestException("请填写要删除的用户ID!");
        }
        User result = userService.queryById(user.getUserId());
        if(result == null){
            throw new ResourceNotFoundException("没有找到要修改的用户！");
        }
        userService.updateUser(user);
        return new RestResponse("修改成功");
    }

    @GetMapping("/queryUserList")
    public RestResponse getPublic(@RequestParam(required=false) Integer pageNum,
                                  @RequestParam(required=false) Integer pageSize,
                                  @RequestParam(required=false) String username){

        PageDto pageDto = userService.queryUserList(pageNum, pageSize, username);
        return new RestResponse(pageDto,"查询成功");
    }


}
