package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    private final static String PAGE_UPLOED_SHOW = "admin/upload";
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

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(ModelMap model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return PAGE_UPLOED_SHOW;
    }

    @PostMapping("/upload/{id}")
    public String upload(@PathVariable Long id, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        String newFileName =  UUID.randomUUID().toString() ;
        // 上传图片
        QiniuUtils.upload2Qiniu(file.getBytes(),newFileName);
        String url= "你的七牛云空间的域名/"+ newFileName;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(url);
        adminService.update(admin);
        return this.successPage(this.MESSAGE_SUCCESS, request);
    }


}
