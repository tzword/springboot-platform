package com.platform.common.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.domain.AjaxResult;
import com.platform.common.sysenum.ResultTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jianghy
 * @Description: 增删改查基础类
 * @date 2020/4/26 10:47
 */
public class CurdController<T> {

    @Autowired
    private BaseMapper<T> mapper;

    /**
     * 在搜索的时候，去除这几个 map 参数
     */
    private String[] pageParams = {"size", "current", "orders"};


    /**
     * @Description: 分页查询
     * @param map 1 
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/27 14:58 
     */
    @PostMapping("/listByPage")
    @ResponseBody
    public AjaxResult listByPage(@RequestBody Map<String, Object> map) {
        IPage<T> object = mapper.selectPage(extractPageFromRequestMap(map),
                extractWrapperFromRequestMap(map));
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),object);
    }


    /**
     * @Description: 保存信息
     * @param map 1 
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/27 14:58 
     */
    @PostMapping("/save")
    @ResponseBody
    public AjaxResult save(@RequestBody T map) {
        int result = mapper.insert(map);
        if (result > 0){
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }
    }


   /**
    * @Description: 根据id获取对象
    * @param id 1 
    * @return com.platform.common.domain.AjaxResult
    * @throws
    * @author jianghy
    * @date 2020/4/27 15:01 
    */
    @PostMapping("/getById")
    @ResponseBody
    public AjaxResult getById(@RequestBody Long id) {
        T t = mapper.selectById(id);
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),t);
    }

    
    /**
     * @Description: 修改信息
     * @param map 1 
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/27 15:02 
     */
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult updateById(@RequestBody T map) {
        int result = mapper.updateById(map);
        if (result > 0){
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }
    }

    
    /**
     * @Description: 删除信息
     * @param id 1 
     * @return com.platform.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/4/27 15:02 
     */
    @PostMapping("/deleteById")
    @ResponseBody
    public AjaxResult deleteById(@RequestBody Long id) {
        int result = mapper.deleteById(id);
        if (result > 0){
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }
    }


    /**
     * 从请求体中提取查询参数
     * @param map
     * @return
     */
    private QueryWrapper<T> extractWrapperFromRequestMap(Map<String, Object> map) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        for (String pageParam : pageParams) {
            map.remove(pageParam);
        }
        queryWrapper.allEq(map, false);
        return queryWrapper;
    }

    /**
     * 从请求体中提取分页参数
     * @param map
     * @return
     */
    private Page<T> extractPageFromRequestMap(Map<String, Object> map) {

        Page<T> page = new Page<>();

        String key = pageParams[0];
        if (map.containsKey(key) && map.get(key) instanceof Integer) {
            page.setSize((Integer)map.get(key));
        }

        key = pageParams[1];
        if (map.containsKey(key) && map.get(key) instanceof Integer) {
            page.setCurrent((Integer)map.get(key));
        }

        // 排序
        key = pageParams[2];
        if (map.containsKey(key) && map.get(key) instanceof List) {
            List<OrderItem> orderItemList = new ArrayList<>();
            for (String orderArrStr : (List<String>)map.get(key)) {
                if (StringUtils.isBlank(orderArrStr) || !orderArrStr.contains(" ")) {
                    continue;
                }
                String[] orderArr = orderArrStr.split(" ");
                if ("desc".equals(orderArr[1])) {
                    orderItemList.add(OrderItem.desc(orderArr[0]));
                } else {
                    orderItemList.add(OrderItem.asc(orderArr[0]));
                }
            }
            page.setOrders(orderItemList);
        }

        return page;
    }
}
