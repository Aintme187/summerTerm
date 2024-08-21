package com.example.usercenterapi.vo.params;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.yulichang.query.MPJQueryWrapper;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Data
public class FilterData {
    private String filter;
    private String name;
    private String content;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将 filterDataList 中的筛选信息注入 queryWrapper
     *
     * @param queryWrapper   查询包裹
     * @param filterDataList 筛选信息
     * @return 是否成功注入
     */
    public static boolean injectFilter(QueryWrapper<?> queryWrapper, List<FilterData> filterDataList) {
        try {
            for (FilterData filterData : filterDataList) {
                switch (filterData.getFilter()) {
                    case "equal":
                        queryWrapper.eq(StringUtils.camelToUnderline(filterData.getName()), Long.parseLong(filterData.content));
                        break;
                    case "lessThan":
                        queryWrapper.lt(StringUtils.camelToUnderline(filterData.getName()), Long.parseLong(filterData.content));
                        break;
                    case "greaterThan":
                        queryWrapper.gt(StringUtils.camelToUnderline(filterData.getName()), Long.parseLong(filterData.content));
                        break;
                    case "equalDouble":
                        queryWrapper.eq(StringUtils.camelToUnderline(filterData.getName()), Double.parseDouble(filterData.content));
                        break;
                    case "lessThanDouble":
                        queryWrapper.lt(StringUtils.camelToUnderline(filterData.getName()), Double.parseDouble(filterData.content));
                        break;
                    case "greaterThanDouble":
                        queryWrapper.gt(StringUtils.camelToUnderline(filterData.getName()), Double.parseDouble(filterData.content));
                        break;
                    case "contain":
                        queryWrapper.like(StringUtils.camelToUnderline(filterData.getName()), "%" + filterData.content + "%");
                        break;
                    case "startWith":
                        queryWrapper.like(StringUtils.camelToUnderline(filterData.getName()), filterData.content + "%");
                        break;
                    case "endWith":
                        queryWrapper.like(StringUtils.camelToUnderline(filterData.getName()), "%" + filterData.content);
                        break;
                    case "at":
                        queryWrapper.eq(StringUtils.camelToUnderline(filterData.getName()), simpleDateFormat.parse(filterData.content));
                        break;
                    case "before":
                        queryWrapper.lt(StringUtils.camelToUnderline(filterData.getName()), simpleDateFormat.parse(filterData.content));
                        break;
                    case "after":
                        queryWrapper.gt(StringUtils.camelToUnderline(filterData.getName()), simpleDateFormat.parse(filterData.content));
                        break;
                    case "on":
                        String date = filterData.content.substring(0, 11);
                        queryWrapper.between(StringUtils.camelToUnderline(filterData.getName()), simpleDateFormat.parse(date + "00:00:00"), simpleDateFormat.parse(date + "23:59:59"));
                        break;
                    case "in":
                        queryWrapper.in(StringUtils.camelToUnderline(filterData.getName()), Arrays.stream(filterData.content.split(",")).toArray());
                        break;
                    case "notIn":
                        queryWrapper.notIn(StringUtils.camelToUnderline(filterData.getName()), Arrays.stream(filterData.content.split(",")).toArray());
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * MPJ 版本注入筛选条件
     *
     * @param queryWrapper   需要注入的筛选包装
     * @param filterDataList 筛选信息
     * @param mapFunction    列名映射处理函数
     * @return 是否成功注入
     */
    public static boolean injectFilter(MPJQueryWrapper<?> queryWrapper, List<FilterData> filterDataList, Function<List<FilterData>, List<FilterData>> mapFunction) {
        try {
            filterDataList = mapFunction.apply(filterDataList);
            for (FilterData filterData : filterDataList) {
                switch (filterData.getFilter()) {
                    case "equal":
                        queryWrapper.eq(StringUtils.camelToUnderline(filterData.getName()), Long.parseLong(filterData.content));
                        break;
                    case "lessThan":
                        queryWrapper.lt(StringUtils.camelToUnderline(filterData.getName()), Long.parseLong(filterData.content));
                        break;
                    case "greaterThan":
                        queryWrapper.gt(StringUtils.camelToUnderline(filterData.getName()), Long.parseLong(filterData.content));
                        break;
                    case "equalDouble":
                        queryWrapper.eq(StringUtils.camelToUnderline(filterData.getName()), Double.parseDouble(filterData.content));
                        break;
                    case "lessThanDouble":
                        queryWrapper.lt(StringUtils.camelToUnderline(filterData.getName()), Double.parseDouble(filterData.content));
                        break;
                    case "greaterThanDouble":
                        queryWrapper.gt(StringUtils.camelToUnderline(filterData.getName()), Double.parseDouble(filterData.content));
                        break;
                    case "contain":
                        queryWrapper.like(StringUtils.camelToUnderline(filterData.getName()), "%" + filterData.content + "%");
                        break;
                    case "startWith":
                        queryWrapper.like(StringUtils.camelToUnderline(filterData.getName()), filterData.content + "%");
                        break;
                    case "endWith":
                        queryWrapper.like(StringUtils.camelToUnderline(filterData.getName()), "%" + filterData.content);
                        break;
                    case "at":
                        queryWrapper.eq(StringUtils.camelToUnderline(filterData.getName()), simpleDateFormat.parse(filterData.content));
                        break;
                    case "before":
                        queryWrapper.lt(StringUtils.camelToUnderline(filterData.getName()), simpleDateFormat.parse(filterData.content));
                        break;
                    case "after":
                        queryWrapper.gt(StringUtils.camelToUnderline(filterData.getName()), simpleDateFormat.parse(filterData.content));
                        break;
                    case "on":
                        String date = filterData.content.substring(0, 11);
                        queryWrapper.between(StringUtils.camelToUnderline(filterData.getName()), simpleDateFormat.parse(date + "00:00:00"), simpleDateFormat.parse(date + "23:59:59"));
                        break;
                    case "in":
                        queryWrapper.in(StringUtils.camelToUnderline(filterData.getName()), Arrays.stream(filterData.content.split(",")).toArray());
                        break;
                    case "notIn":
                        queryWrapper.notIn(StringUtils.camelToUnderline(filterData.getName()), Arrays.stream(filterData.content.split(",")).toArray());
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}