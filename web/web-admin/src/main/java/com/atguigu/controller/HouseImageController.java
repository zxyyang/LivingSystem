package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping(value="/houseImage")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseImageController extends BaseController {

    @Reference
    private HouseImageService houseImageService;

    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_UPLOED_SHOW = "house/upload";

    @GetMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(ModelMap model,@PathVariable Long houseId, @PathVariable Long type) {
        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return PAGE_UPLOED_SHOW;
    }

    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId, @PathVariable Integer type, @RequestParam(value = "file") MultipartFile[] files) throws Exception {
        if(files != null && files.length > 0) {
            for(MultipartFile file : files) {
                String newFileName =  UUID.randomUUID().toString() ;
                // 上传图片
                QiniuUtils.upload2Qiniu(file.getBytes(),newFileName);
                String url= "http://rqqfc2plo.hn-bkt.clouddn.com/"+ newFileName;
                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setType(type);
                houseImage.setImageName(file.getOriginalFilename());
                houseImage.setImageUrl(url);
                houseImageService.insert(houseImage);
            }
        }
        return Result.ok();
    }

    /**
     * 删除
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/delete/{houseId}/{id}")
    public String delete(ModelMap model,@PathVariable Long houseId, @PathVariable Long id,RedirectAttributes redirectAttributes) {
        HouseImage houseImage = houseImageService.getById(id);
        houseImageService.delete(id);
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageUrl());
        return LIST_ACTION + houseId;
    }
}