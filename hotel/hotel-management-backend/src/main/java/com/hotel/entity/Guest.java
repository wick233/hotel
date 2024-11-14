package com.hotel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("Guest")
public class Guest {
    @TableId(type = IdType.AUTO)
    private Integer guestId; // 客户ID

    private String name; // 客户姓名
    private Integer idType; // 证件类型 (1:身份证, 2:护照, 3:军官证)
    private String idNumber; // 证件号码
    private String phone; // 电话
    private String email; // 邮箱
    private String address; // 地址
    private Integer vipLevel; // VIP等级

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt; // 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt; // 更新时间

    @TableLogic
    private Integer deleted; // 逻辑删除标志
} 