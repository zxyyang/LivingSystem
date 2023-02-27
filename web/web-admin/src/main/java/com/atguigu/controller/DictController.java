package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/dict")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictController extends BaseController {

    @Reference
    private DictService dictService;

    private final static String PAGE_INDEX = "dict/index";

    /**
     * 根据上级id获取子节点数据列表
     * @param id
     * @return
     */
    @GetMapping(value = "findZnodes")
    @ResponseBody
    public Result findByParentId(@RequestParam(value = "id", defaultValue = "0") Long id) {
        List<Map<String,Object>> zNodes = dictService.findZnodes(id);
        return Result.ok(zNodes);
    }

    @GetMapping
    public String index(ModelMap model) {
        return PAGE_INDEX;
    }
    //小区管理
    //根据上级id获取子节点数据列表
    @GetMapping(value = "/findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

    //根据编码获取子节点数据列表
    @GetMapping(value = "/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }
}