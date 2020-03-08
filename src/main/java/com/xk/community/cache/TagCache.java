package com.xk.community.cache;

import com.xk.community.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDto> get(){
        ArrayList<TagDto> tagDtos = new ArrayList<>();
        TagDto lifeAndEntertain = new TagDto();
        lifeAndEntertain.setCategoryName("生活娱乐");
        lifeAndEntertain.setTags(Arrays.asList("游戏", "美剧", "美食", "摄影"));
        tagDtos.add(lifeAndEntertain);


        TagDto coder = new TagDto();
        coder.setCategoryName("IT");
        coder.setTags(Arrays.asList("编程", "程序员", "DIY", "手机"));


        tagDtos.add(coder);
        return tagDtos;
    }

    public static String filterInValid(String tabs){
        String[] split = StringUtils.split(tabs, ",");
        List<TagDto> tagDtos = get();

        List<String> tagList = tagDtos.stream().flatMap(tagDto -> tagDto.getTags().stream()).collect(Collectors.toList());

        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }

}
