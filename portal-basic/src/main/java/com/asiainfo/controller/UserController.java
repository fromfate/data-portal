package com.asiainfo.controller;

import com.asiainfo.dto.*;
import com.asiainfo.entity.User;
import com.asiainfo.exception.BadRequestException;
import com.asiainfo.exception.ResourceNotFoundException;
import com.asiainfo.operlog.BusinessType;
import com.asiainfo.operlog.Log;
import com.asiainfo.service.UserService;
import com.asiainfo.vo.UserRoleAndTenantVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "用户管理")
public class UserController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @Log(title = "新增用户", action = BusinessType.INSERT)
    @RequestMapping(method = { RequestMethod.POST } ,value="insert")
    @ResponseBody
    @ApiOperation(value = "新增用户",notes = "新增用户")
    @PostMapping("/save")
    public RestResponse save(@Valid @RequestBody UserDto userDto) {
        try{
            User result = userService.addUser(userDto);
            return new RestResponse(result,"添加成功");
        }catch (Exception e){
            if(e.getMessage().contains("un_fk_username")){
                return new RestResponse("用户名已存在");
            }else if(e.getMessage().contains("un_fk_phone")){
                return new RestResponse("手机号码已存在");
            }else{
                return new RestResponse(e.getMessage());
            }
        }
    }

    @Log(title = "删除用户", action = BusinessType.DELETE)
    @DeleteMapping("/del/{userId}")
    @ApiOperation(value = "删除用户",notes = "删除用户")
    public RestResponse delete(@PathVariable int userId) throws ResourceNotFoundException {
        User user1 = userService.queryById(userId);
        if(user1 == null){
            throw new ResourceNotFoundException("没有找到要删除的用户！");
        }
        User user = new User();
        user.setUserId(userId);
        user.setDelYn("1");
        userService.updateUser(user);
        return new RestResponse("删除成功");
    }

    @Log(title = "更新用户", action = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation(value = "更新用户",notes = "更新用户")
    public RestResponse update(@Valid @RequestBody User user) throws BadRequestException, ResourceNotFoundException {
        if(user.getUserId() == null) {
            throw new BadRequestException("请填写要删除的用户ID!");
        }
        User result = userService.queryById(user.getUserId());
        if(result == null){
            throw new ResourceNotFoundException("没有找到要修改的用户！");
        }
        try{
            userService.updateUser(user);
            return new RestResponse(result,"修改成功");
        }catch (Exception e){
            if(e.getMessage().contains("un_fk_username")){
                return new RestResponse("用户名已存在");
            }else if(e.getMessage().contains("un_fk_phone")){
                return new RestResponse("手机号码已存在");
            }else{
                return new RestResponse(e.getMessage());
            }
        }
    }

    @Log(title = "禁用启用用户", action = BusinessType.ENABLE_FORBIDDEN)
    @ApiOperation(value = "禁用启用用户",notes = "禁用启用用户")
    public RestResponse updateForUse(@Valid @RequestBody User user) throws BadRequestException, ResourceNotFoundException {
        if(user.getUserId() == null) {
            throw new BadRequestException("请填写要删除的用户ID!");
        }
        User result = userService.queryById(user.getUserId());
        if(result == null){
            throw new ResourceNotFoundException("没有找到要修改的用户！");
        }
        userService.updateForUse(user);
        return new RestResponse("修改成功");
    }

    @Log(title = "用户列表查询", action = BusinessType.SELECT)
    @GetMapping("/queryUserPageList")
    @ApiOperation(value = "用户列表查询-分页",notes = "用户列表查询-分页")
    public RestResponse queryUserPageList(@RequestParam(required=false) Integer pageNum,
                                          @RequestParam(required=false) Integer pageSize,
                                          @RequestParam(required=false) String userName){

        PageDto pageDto = userService.queryUserPageList(pageNum, pageSize, userName);
        return new RestResponse(pageDto,"查询成功");
    }

    @Log(title = "用户列表查询", action = BusinessType.SELECT)
    @GetMapping("/queryUserList")
    @ApiOperation(value = "用户列表查询-不分页",notes = "用户列表查询-不分页")
    public RestResponse queryUserList(@RequestParam(required=false) String userName){
        List<User> userList = userService.queryUserList(userName);
        return new RestResponse(userList,"查询成功");
    }

    /**
     * 导出用户信息
     * @return 文件
     */
    @Log(title = "导出用户信息", action = BusinessType.EXPORT)
    @ApiOperation(value = "导出用户信息",notes = "导出用户信息")
    @RequestMapping(value = "/downloadUserListInfo",method = RequestMethod.GET)
    public void downloadUserListInfo(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String userIds = request.getParameter("userIds");
        HSSFWorkbook workbook;
        workbook = new HSSFWorkbook();
        try {
           //表头数据
            String[] header = {"用户名", "用户姓名", "手机号", "身份证号码", "邮箱", "所属机构", "创建时间"};
            //声明一个工作簿
            //生成一个表格，设置表格名称为"用户数据表"
            HSSFSheet sheet = workbook.createSheet("用户列表");
            sheet.setColumnWidth(17, 20 * 256);  //设置列宽，20个字符宽
            //设置表格列宽度为10个字节
            sheet.setDefaultColumnWidth(15);
            Row rowTitle = sheet.createRow(0);
            rowTitle.setHeightInPoints(40);//目的是想把行高设置成20px
            HSSFCellStyle cellStyle=workbook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setWrapText(true);
            //创建第一行表头
            HSSFRow headrow = sheet.createRow(0);
            //遍历添加表头
            for (int i = 0; i < header.length; i++) {
                //创建一个单元格
                HSSFCell cell = headrow.createCell(i);
                //创建一个内容对象
                HSSFRichTextString text = new HSSFRichTextString(header[i]);
                //将内容对象的文字内容写入到单元格中
                cell.setCellValue(text);
                //设置为居中加粗
                HSSFCellStyle style = workbook.createCellStyle();
                HSSFFont font = workbook.createFont();
                font.setBold(true);
                style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                font.setColor(Font.COLOR_NORMAL);
                style.setFont(font);
                cell.setCellStyle(style);
            }
            //遍历结果集，把内容加入表格
            List<User> userList = userService.queryUserListForImport(userIds);
            for (int i = 0; i < userList.size(); i++) {
                HSSFRow row1 = sheet.createRow(i + 1);
                HSSFCell cell0 = row1.createCell(0);
                cell0.setCellValue(userList.get(i).getUserName());
                HSSFCell cell1 = row1.createCell(1);
                cell1.setCellValue(userList.get(i).getRealName());
                HSSFCell cell2 = row1.createCell(2);
                cell2.setCellValue(userList.get(i).getPhone());
                HSSFCell cell3 = row1.createCell(3);
                cell3.setCellValue(userList.get(i).getIdCards());
                HSSFCell cell4 = row1.createCell(4);
                cell4.setCellValue(userList.get(i).getEmail());
                HSSFCell cell5 = row1.createCell(5);
                cell5.setCellValue(userList.get(i).getBrchId());
                HSSFCell cell6 = row1.createCell(6);
                cell6.setCellType(Cell.CELL_TYPE_STRING);
                cell6.setCellValue(userList.get(i).getRegDts());
            }
            //准备将Excel的输出流通过response输出到页面下载
            String fileName = "用户信息导出";
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream sout = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(sout);
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (Exception e) {
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    @Log(title = "查询用户已授权的角色和租户集合", action = BusinessType.SELECT)
    @PutMapping("/queryUserRoleAuthorizationed")
    @ApiOperation(value = "查询用户已授权的角色和租户集合",notes = "查询用户已授权的角色和租户集合")
    public RestResponse queryUserRoleAuthorizationed(@Valid @RequestBody IDDto idDto) throws BadRequestException, ResourceNotFoundException {
        if(idDto.getId()==null) {
            throw new BadRequestException("请选择用户信息!");
        }
        try{
            UserRoleAndTenantVo userRoleAndTenantVo = userService.queryUserAuthorizationed(idDto.getId());
            return new RestResponse(userRoleAndTenantVo,"查询成功");
        }catch (Exception e){
            e.printStackTrace();
            return new RestResponse("查询失败");
        }
    }

    @Log(title = "给指定用户授予系统角色权限和租户权限", action = BusinessType.INSERT)
    @PutMapping("/userAuthorization")
    @ApiOperation(value = "给指定用户授予系统角色权限和租户权限",notes = "给指定用户授予系统角色权限和租户权限")
    public RestResponse userAuthorization(@Valid @RequestBody UserAuthorizationDto userAuthorizationDto) throws BadRequestException, ResourceNotFoundException {
        if(userAuthorizationDto.getRuleIds() == null && userAuthorizationDto.getTenantIds()==null) {
            throw new BadRequestException("请选择授权信息!");
        }
        try{
            userService.addUserAuthorization(userAuthorizationDto);
            return new RestResponse("用户授权成功");
        }catch (Exception e){
            return new RestResponse("修改失败");
        }
    }

    @Log(title = "通过选择组织节点，将整个组织下的用户授权", action = BusinessType.INSERT)
    @PutMapping("/batchUserAuthorization")
    @ApiOperation(value = "通过选择组织节点，将整个组织下的用户授权",notes = "通过选择组织节点，将整个组织下的用户授权")
    public RestResponse batchUserAuthorization(@Valid @RequestBody BrchUserAuthorizationDto brchUserAuthorizationDto) throws BadRequestException, ResourceNotFoundException {
        if(brchUserAuthorizationDto.getRuleIds() == null && brchUserAuthorizationDto.getTenantIds()==null) {
            throw new BadRequestException("请选择授权信息!");
        }
        try{
            userService.batchUserAuthorization(brchUserAuthorizationDto);
            return new RestResponse("用户授权成功");
        }catch (Exception e){
            return new RestResponse("修改失败");
        }
    }
}
