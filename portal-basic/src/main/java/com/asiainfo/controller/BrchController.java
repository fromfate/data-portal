package com.asiainfo.controller;

import com.asiainfo.dto.PageDto;
import com.asiainfo.dto.RestResponse;
import com.asiainfo.entity.BrchInfo;
import com.asiainfo.exception.BadRequestException;
import com.asiainfo.exception.ResourceNotFoundException;
import com.asiainfo.operlog.BusinessType;
import com.asiainfo.operlog.Log;
import com.asiainfo.service.BrchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/brch")
@Api(tags = "机构管理")
public class BrchController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BrchController.class);

    @Autowired
    private BrchService brchService;

    @Log(title = "机构新增", action = BusinessType.INSERT)
    @RequestMapping(method = { RequestMethod.POST } ,value="insert")
    @ResponseBody
    @ApiOperation(value = "新增机构",notes = "新增机构")
    @PostMapping("/save")
    public RestResponse save(@Valid @RequestBody BrchInfo brchInfo) {
        try{
            BrchInfo result = brchService.addBrchInfo(brchInfo);
            return new RestResponse(result,"添加成功");
        }catch (Exception e){
            if(e.getMessage().contains("un_brchname")){
                return new RestResponse("机构名称已存在");
            }else if(e.getMessage().contains("un_code")){
                return new RestResponse("机构编码已存在");
            }else{
                return new RestResponse(e.getMessage());
            }
        }
    }

    @Log(title = "删除机构", action = BusinessType.DELETE)
    @DeleteMapping("/del/{brchId}")
    @ApiOperation(value = "删除机构",notes = "删除机构")
    public RestResponse delete(@PathVariable int brchId) throws ResourceNotFoundException {
        BrchInfo brchInfo = brchService.queryById(brchId);
        if(brchInfo == null){
            throw new ResourceNotFoundException("没有找到要删除的机构！");
        }
        brchService.delBrchInfo(brchId);
        return new RestResponse("删除成功");
    }

    @Log(title = "更新机构", action = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "更新机构",notes = "更新机构")
    public RestResponse update(@Valid @RequestBody BrchInfo brchInfo) throws BadRequestException, ResourceNotFoundException {
        if(brchInfo.getBrchId() == null) {
            throw new BadRequestException("请填写要删除的机构ID!");
        }
        BrchInfo result = brchService.queryById(brchInfo.getBrchId());
        if(result == null){
            throw new ResourceNotFoundException("没有找到要修改的机构！");
        }
        try{
            brchService.updateBrchInfo(brchInfo);
            return new RestResponse("修改成功");
        }catch (Exception e){
            if(e.getMessage().contains("un_brchname")){
                return new RestResponse("机构名称已存在");
            }else if(e.getMessage().contains("un_code")){
                return new RestResponse("机构编码已存在");
            }else{
                return new RestResponse(e.getMessage());
            }
        }
    }

    @Log(title = "禁用启用机构", action = BusinessType.ENABLE_FORBIDDEN)
    @PutMapping("/updateForUse")
    @ApiOperation(value = "禁用启用机构",notes = "禁用启用机构")
    public RestResponse updateForUse(@Valid @RequestBody BrchInfo brchInfo) throws BadRequestException, ResourceNotFoundException {
        if(brchInfo.getBrchId() == null) {
            throw new BadRequestException("请填写要删除的机构ID!");
        }
        BrchInfo result = brchService.queryById(brchInfo.getBrchId());
        if(result == null){
            throw new ResourceNotFoundException("没有找到要修改的机构！");
        }
        brchService.updateForUse(brchInfo);
        return new RestResponse("操作成功");
    }

    @Log(title = "查询机构列表-分页", action = BusinessType.SELECT)
    @GetMapping("/queryBrchPageList")
    @ApiOperation(value = "查询机构列表-分页",notes = "查询机构列表-分页")
    public RestResponse queryBrchPageList(@RequestParam(required=false) Integer pageNum,
                                  @RequestParam(required=false) Integer pageSize,
                                  @RequestParam(required=false) String brchName){
        PageDto pageDto = brchService.queryBrchPageList(pageNum, pageSize, brchName);
        return new RestResponse(pageDto,"查询成功");
    }

    @Log(title = "查询机构列表-分页", action = BusinessType.SELECT)
    @GetMapping("/queryBrchList")
    @ApiOperation(value = "查询机构列表-不分页",notes = "查询机构列表-不分页")
    public RestResponse queryBrchList(@RequestParam(required=false) String brchName){
        List<BrchInfo> brchInfoList = brchService.queryBrchList(brchName);
        return new RestResponse(brchInfoList,"查询成功");
    }

}
