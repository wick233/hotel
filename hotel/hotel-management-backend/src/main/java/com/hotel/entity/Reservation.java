package com.hotel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("Reservation")
public class Reservation {
    @TableId(type = IdType.AUTO)
    private Integer reservationId; // 预订ID

    private Integer guestId; // 客户ID
    private Integer roomId; // 房间ID
    private LocalDateTime checkInDate; // 入住日期
    private LocalDateTime checkOutDate; // 退房日期
    private Integer status; // 状态 (0:待确认, 1:已确认, 2:已取消, 3:已完成)
    private BigDecimal totalAmount; // 总金额
    private Integer paymentStatus; // 支付状态 (0:未支付, 1:部分支付, 2:已完成)

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt; // 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt; // 更新时间
} 