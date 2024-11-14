package com.hotel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("Room")
public class Room {
    @TableId(type = IdType.AUTO)
    private Integer roomId; // 房间ID

    private String roomNumber; // 房间号
    private Integer roomTypeId; // 房间类型ID
    private Integer floor; // 楼层
    private Integer status; // 状态 (0:空闲, 1:已预订, 2:已入住, 3:维护中)
    private BigDecimal price; // 价格
    private String description; // 描述

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt; // 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt; // 更新时间
} 