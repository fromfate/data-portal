package com.asiainfo.controller;

import com.asiainfo.dto.RestResponse;
import com.asiainfo.entity.Platform;
import com.asiainfo.exception.BadRequestException;
import com.asiainfo.exception.ResourceNotFoundException;
import com.asiainfo.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @PostMapping
    public RestResponse add(@Valid @RequestBody Platform platform){
        platform.setOpTime(new Date());
        platformService.save(platform);
        return new RestResponse("添加成功");
    }

    @DeleteMapping("/{id}")
    public RestResponse delete(@PathVariable Long id) throws ResourceNotFoundException {
        Platform platform = platformService.getPlatformById(id);
        if(platform == null){
            throw new ResourceNotFoundException("找不到要删除的平台！");
        }
        platformService.delete(id);
        return new RestResponse("删除成功");
    }

    @PutMapping
    public RestResponse update(@Valid @RequestBody Platform platform) throws BadRequestException, ResourceNotFoundException {
        if(platform.getId() == null){
            throw new BadRequestException("请求参数缺少平台ID");
        }
        Platform source = platformService.getPlatformById(platform.getId());
        if(source == null){
            throw new ResourceNotFoundException("没有找到要修改的平台");
        }
        int update = platformService.update(platform);
        if(update == 1){
            return new RestResponse("修改成功");
        }
        return new RestResponse("修改失败");
    }

    @GetMapping("/getAll")
    public RestResponse getAll(){
        List<Platform> all = platformService.findAll();
        return new RestResponse(all,"查询成功");
    }

    @PostMapping("/isAvailable")
    public RestResponse isAvailable(@RequestBody Platform platform) throws BadRequestException, ResourceNotFoundException {
        if(platform.getId() == null){
            throw new BadRequestException("请求参数缺少平台ID");
        }
        Platform source = platformService.getPlatformById(platform.getId());
        if(source == null){
            throw new ResourceNotFoundException("没有找到要修改的平台");
        }
        Platform operate = new Platform();
        operate.setId(platform.getId());
        operate.setValid(platform.getValid());
        int update = platformService.update(operate);
        if(update == 1){
            return new RestResponse("操作成功");
        }
        return new RestResponse("操作成功");
    }

}
