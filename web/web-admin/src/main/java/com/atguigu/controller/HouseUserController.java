package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/houseUser")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseUserController extends BaseController {

    @Reference
    private HouseUserService houseUserService;

    private final static String LIST_ACTION = "redirect:/house/";

    private final static String PAGE_CREATE = "houseUser/create";
    private final static String PAGE_EDIT = "houseUser/edit";
    private final static String PAGE_SUCCESS = "common/successPage";


    /**
     * 进入新增
     */
    @GetMapping("/create")
    public String create(ModelMap model,@RequestParam("houseId") Long houseId) {
        model.addAttribute("houseId",houseId);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     */
    @PostMapping("/save")
    public String save(HouseUser houseUser) {

        houseUserService.insert(houseUser);

        return PAGE_SUCCESS;
    }

    /**
     * 编辑
     */
    @GetMapping("/edit/{id}")
    public String edit(ModelMap model,@PathVariable Long id) {
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     */
    @PostMapping(value="/update")
    public String update(HouseUser houseUser) {

        houseUserService.update(houseUser);

        return PAGE_SUCCESS;
    }

    /**
     * 删除
     */
    @GetMapping("/delete/{houseId}/{id}")
    public String delete(ModelMap model,@PathVariable Long houseId, @PathVariable Long id) {
        houseUserService.delete(id);
        return LIST_ACTION + houseId;
    }

}