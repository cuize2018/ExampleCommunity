package com.xk.community.service;

import com.xk.community.dto.NotificationDto;
import com.xk.community.dto.PageDto;
import com.xk.community.enums.NotificationStatusEnum;
import com.xk.community.enums.NotificationTypeEnum;
import com.xk.community.exception.CustomizeErrorCode;
import com.xk.community.exception.CustomizeException;
import com.xk.community.mapper.NotificationMapper;
import com.xk.community.model.Notification;
import com.xk.community.model.NotificationExample;
import com.xk.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PageDto<NotificationDto> list(Long userId, Integer page, Integer size) {

        PageDto<NotificationDto> pageDto = new PageDto<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);

        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        //计算总页数
        int totalPages = totalCount / size;
        if (totalCount % size != 0) {
            totalPages += 1;
        }
        //控制页码范围，1 <= page <= totalPages
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }

        pageDto.setPagination(page, totalPages);

        //offset = size*(page -1)
        int offset = page < 1 ? 0 : size * (page - 1);

        NotificationExample notificationExample1 = new NotificationExample();
        notificationExample1.createCriteria().andReceiverEqualTo(userId);
        notificationExample1.setOrderByClause("gmt_create desc");

        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample1, new RowBounds(offset, size));
        if (notifications.isEmpty())return pageDto;


        List<NotificationDto> notificationDtos = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDtos.add(notificationDto);
        }

        pageDto.setData(notificationDtos);
        return pageDto;
    }

    public Long unReadCount(Long userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(example);
    }

    public NotificationDto read(Long id, User user) {

        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        if (!notification.getReceiver().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification, notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDto;
    }
}
