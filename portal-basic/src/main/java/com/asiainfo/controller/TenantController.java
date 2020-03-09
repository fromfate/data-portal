package com.asiainfo.controller;

import com.asiainfo.dto.PageDto;
import com.asiainfo.dto.RestResponse;
import com.asiainfo.dto.TenantAuthorizationDto;
import com.asiainfo.entity.Tenant;
import com.asiainfo.exception.BadRequestException;
import com.asiainfo.exception.ResourceNotFoundException;
import com.asiainfo.operlog.BusinessType;
import com.asiainfo.operlog.Log;
import com.asiainfo.service.TenantAuthorizationService;
import com.asiainfo.service.TenantService;
import com.asiainfo.vo.AuthorizationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tenant")
@Api(tags = "租户管理")
public class TenantController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    private TenantService tenantService;
    @Autowired
    TenantAuthorizationService tenantAuthorizationService;

    @Log(title = "新增租户", action = BusinessType.INSERT)
    @RequestMapping(method = { RequestMethod.POST } ,value="insert")
    @ResponseBody
    @ApiOperation(value = "新增租户",notes = "新增租户")
    @PostMapping("/save")
    public RestResponse save(@Valid @RequestBody Tenant tenant) {
        try{
            Tenant result = tenantService.addTenant(tenant);
            return new RestResponse(result,"添加成功");
        }catch (Exception e){
            if(e.getMessage().contains("un_name")){
                return new RestResponse("租户名称已存在");
            }else if(e.getMessage().contains("un_code")){
                return new RestResponse("租户编码已存在");
            }else{
                return new RestResponse(e.getMessage());
            }
        }
    }

    @Log(title = "删除租户", action = BusinessType.DELETE)
    @DeleteMapping("/del/{tenantId}")
    @ApiOperation(value = "删除租户",notes = "删除租户")
    public RestResponse delete(@PathVariable int tenantId) throws ResourceNotFoundException {
        Tenant Tenant = tenantService.queryById(tenantId);
        if(Tenant == null){
            throw new ResourceNotFoundException("没有找到要删除的租户！");
        }
        com.asiainfo.entity.Tenant tenant = new Tenant();
        tenant.setTenantId(tenantId);
        tenant.setDelYn("1");
        tenantService.updateTenant(tenant);
        return new RestResponse("删除成功");
    }

    @Log(title = "更新租户", action = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "更新租户",notes = "更新租户")
    public RestResponse update(@Valid @RequestBody Tenant tenant) throws BadRequestException, ResourceNotFoundException {
        if(tenant.getTenantId() == null) {
            throw new BadRequestException("请填写要删除的租户ID!");
        }
        Tenant result = tenantService.queryById(tenant.getTenantId());
        if(result == null){
            throw new ResourceNotFoundException("没有找到要修改的租户！");
        }
        try{
            tenantService.updateTenant(tenant);
            return new RestResponse(result,"修改成功");
        }catch (Exception e){
            if(e.getMessage().contains("un_name")){
                return new RestResponse("租户名称已存在");
            }else if(e.getMessage().contains("un_code")){
                return new RestResponse("租户编码已存在");
            }else{
                return new RestResponse(e.getMessage());
            }
        }
    }

    @Log(title = "查询租户列表-分页", action = BusinessType.SELECT)
    @GetMapping("/queryTenantPageList")
    @ApiOperation(value = "查询租户列表-分页",notes = "查询租户列表-分页")
    public RestResponse queryTenantPageList(@RequestParam(required=false) Integer pageNum,
                                  @RequestParam(required=false) Integer pageSize,
                                  @RequestParam(required=false) String tenantname){
        PageDto pageDto = tenantService.queryTenantPageList(pageNum, pageSize, tenantname);
        return new RestResponse(pageDto,"查询成功");
    }

    @Log(title = "查询租户列表-不分页", action = BusinessType.SELECT)
    @GetMapping("/queryTenantList")
    @ApiOperation(value = "查询租户列表-不分页",notes = "查询租户列表不分页")
    public RestResponse queryTenantList(@RequestParam(required=false) String tenantName){
        List<Tenant> tenantList = tenantService.queryTenantList(tenantName);
        return new RestResponse(tenantList,"查询成功");
    }

    @Log(title = "查询租户已授权列表", action = BusinessType.SELECT)
    @GetMapping("/queryTenantAuthorizationList/{tenantId}")
    @ApiOperation(value = "查询租户已授权列表",notes = "查询租户已授权列表")
    public RestResponse queryTenantAuthorizationList(@PathVariable int tenantId) throws ResourceNotFoundException {
        Tenant Tenant = tenantService.queryById(tenantId);
        if(Tenant == null){
            throw new ResourceNotFoundException("没有找到租户！");
        }
        List<AuthorizationVo> tenantList = tenantService.queryTenantAuthorizationList(tenantId);
        return new RestResponse(tenantList,"查询成功");
    }

    @Log(title = "授权租户信息", action = BusinessType.INSERT)
    @PutMapping("/tenantAuthorization")
    @ApiOperation(value = "授权租户信息",notes = "授权租户信息")
    public RestResponse tenantAuthorization(@Valid @RequestBody TenantAuthorizationDto tenantAuthorizationDto) throws BadRequestException, ResourceNotFoundException {
        if(tenantAuthorizationDto.getResourceIds() == null) {
            throw new BadRequestException("请选择资源!");
        }
        try{
            tenantAuthorizationService.addTenantAuthorization(tenantAuthorizationDto);
            return new RestResponse("授权资源成功");
        }catch (Exception e){
            return new RestResponse("修改失败");
        }
    }
}
