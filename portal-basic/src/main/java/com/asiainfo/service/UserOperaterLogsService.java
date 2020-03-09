package com.asiainfo.service;

import com.asiainfo.dto.OperLogDto;
import com.asiainfo.dto.PageDto;
import com.asiainfo.entity.UserOperaterLogs;

public interface UserOperaterLogsService {

    UserOperaterLogs addUserOperaterLogs(UserOperaterLogs userOperaterLogs);

    PageDto queryLogsPageList(Integer pageNum, Integer pageSize, OperLogDto operLogDto);

}
