package com.fire.firepalaceorderingsys.controller;

import com.fire.firepalaceorderingsys.common.Result;
import com.fire.firepalaceorderingsys.dto.AssignInfoDTO;
import com.fire.firepalaceorderingsys.pojo.AssignInfo;
import com.fire.firepalaceorderingsys.service.AssignInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 分配信息控制器
 */
@RestController
@RequestMapping("/assign-info")
public class AssignInfoController {

    @Autowired
    private AssignInfoService assignInfoService;

    /**
     * 创建分配信息
     */
    @PostMapping("/create")
    public Result createAssignInfo(@Valid @RequestBody AssignInfoDTO assignInfoDTO) {
        AssignInfo assignInfo = assignInfoService.createAssignInfo(assignInfoDTO);
        return Result.success(assignInfo);
    }

    /**
     * 删除分配信息
     */
    @DeleteMapping("delete/{id}")
    public Result deleteAssignInfo(@PathVariable Long id) {
        assignInfoService.deleteAssignInfo(id);
        return Result.success("分配信息删除成功");
    }

    /**
     * 根据ID获取分配信息
     */
    @GetMapping("select/{id}")
    public Result getAssignInfoById(@PathVariable Long id) {
        AssignInfo assignInfo = assignInfoService.getAssignInfoById(id);
        return Result.success(assignInfo);
    }

    /**
     * 获取所有分配信息
     */
    @GetMapping("/selectAll")
    public Result getAllAssignInfos() {
        List<AssignInfo> assignInfos = assignInfoService.getAllAssignInfos();
        return Result.success(assignInfos);
    }

    /**
     * 根据服务员ID获取分配信息
     */
    @GetMapping("/waiter/{waiterId}")
    public Result getAssignInfosByWaiterId(@PathVariable Long waiterId) {
        List<AssignInfo> assignInfos = assignInfoService.getAssignInfosByWaiterId(waiterId);
        return Result.success(assignInfos);
    }

    /**
     * 根据包厢ID获取分配信息
     */
    @GetMapping("/room/{roomId}")
    public Result getAssignInfosByRoomId(@PathVariable Long roomId) {
        List<AssignInfo> assignInfos = assignInfoService.getAssignInfosByRoomId(roomId);
        return Result.success(assignInfos);
    }

    /**
     * 根据管理员ID获取分配信息
     */
    @GetMapping("/admin/{adminId}")
    public Result getAssignInfosByAdminId(@PathVariable Long adminId) {
        List<AssignInfo> assignInfos = assignInfoService.getAssignInfosByAdminId(adminId);
        return Result.success(assignInfos);
    }

    /**
     * 根据日期查询当天的所有分配信息
     */
    @GetMapping("/date")
    public Result getAssignInfosByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AssignInfo> assignInfos = assignInfoService.getAssignInfosByDate(date);
        return Result.success(assignInfos);
    }
}
