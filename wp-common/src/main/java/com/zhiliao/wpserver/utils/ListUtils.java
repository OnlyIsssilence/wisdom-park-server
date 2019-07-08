package com.zhiliao.wpserver.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2019 OnlyIssilence, Inc. All rights reserved.
 *
 * @Author: MuYa
 * @Date: 2019/7/9
 * @Time: 0:06
 * @Desc:
 */
public class ListUtils {

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static List<Long> listStrToLong(List<String> strings) {
        List<Long> longs = new ArrayList<>();

        if (isEmpty(strings)) {
            return null;
        }
        strings.stream().forEach(s -> {
            longs.add(Long.parseLong(s));
        });

        return longs;
    }

    public static List<Long> strToListLong(String str, String spilt) {
        List<Long> listIds = new ArrayList<>();

        if (StringUtils.isNotEmpty(str)) {
            String[] arrIds = str.split(spilt);
            for (String temp : arrIds) {
                listIds.add(Long.parseLong(temp));
            }
        }
        return listIds;
    }

    public static List<String> strToListStr(String str, String spilt) {
        if (StringUtils.isNotEmpty(str)) {
            String[] arrIds = str.split(spilt);
            List<String> listIds = new ArrayList<>();
            for (String temp : arrIds) {
                listIds.add(temp);
            }

            return listIds;
        }
        return null;
    }

    public static String listStrsToString(List<String> srcLists, String spilt) {
        String retStr = "";
        if (!isEmpty(srcLists)) {
            for (int i = 0; i < srcLists.size(); i++) {
                retStr += srcLists.get(i);

                if (i != srcLists.size() - 1) {
                    retStr += spilt;
                }
            }
        }

        return retStr;
    }

    public static String listStrsToStr(List<String> strings, String split) {
        String retStr = "";
        if (!ListUtils.isEmpty(strings)) {
            for (int i = 0; i < strings.size(); i++) {
                retStr += strings.get(i);
                if (i != strings.size() - 1) {
                    retStr += split;
                }
            }
        }

        return retStr;
    }

}
