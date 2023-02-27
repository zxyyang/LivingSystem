package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
    @Reference
    private AdminService adminService;
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request){
        Map<String,Object> filters=getFilters(request);
        map.put("filters",filters);
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        map.put("page",pageInfo);
        return "admin/index";
    }
    @RequestMapping("/create")
    public String goAddPage(){
        return "admin/create";
    }
    @RequestMapping("/save")
    public String save(Admin admin){
        adminService.insert(admin);
        return "common/successPage";
    }
    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable("adminId") Long adminId){
        adminService.delete(adminId);
        return "redirect:/admin";
    }
    @RequestMapping("/edit/{adminId}")
    public String goEditPage(@PathVariable("adminId") Long adminId,Map map){
        Admin admin = adminService.getById(adminId);
        map.put("admin",admin);
        return "admin/edit";
    }
    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        return "common/successPage";
    }
}
