-- 创建数据库
CREATE DATABASE IF NOT EXISTS hotel_management;
USE hotel_management;

-- 房间类型表
CREATE TABLE RoomType (
    type_id INT PRIMARY KEY AUTO_INCREMENT,
    type_name VARCHAR(50) NOT NULL,
    amenities TEXT,
    max_guests INT NOT NULL DEFAULT 2,
    base_price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 客房信息表
CREATE TABLE Room (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10) NOT NULL UNIQUE,
    room_type_id INT NOT NULL,
    floor INT NOT NULL,
    status TINYINT NOT NULL DEFAULT 0 COMMENT '0:空闲 1:已预订 2:已入住 3:维护中',
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (room_type_id) REFERENCES RoomType(type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 客户信息表
CREATE TABLE Guest (
    guest_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    id_type TINYINT NOT NULL COMMENT '1:身份证 2:护照 3:军官证',
    id_number VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    vip_level INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_id_number (id_type, id_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 预订表
CREATE TABLE Reservation (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    guest_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    status TINYINT NOT NULL DEFAULT 0 COMMENT '0:待确认 1:已确认 2:已取消 3:已完成',
    total_amount DECIMAL(10,2) NOT NULL,
    payment_status TINYINT DEFAULT 0 COMMENT '0:未支付 1:部分支付 2:已完成',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (guest_id) REFERENCES Guest(guest_id),
    FOREIGN KEY (room_id) REFERENCES Room(room_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 入住记录表
CREATE TABLE CheckIn (
    check_in_id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_id INT,
    guest_id INT NOT NULL,
    room_id INT NOT NULL,
    actual_check_in DATETIME NOT NULL,
    planned_check_out DATETIME NOT NULL,
    actual_check_out DATETIME,
    deposit DECIMAL(10,2) NOT NULL DEFAULT 0,
    status TINYINT NOT NULL DEFAULT 1 COMMENT '1:在住 2:已退房',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id),
    FOREIGN KEY (guest_id) REFERENCES Guest(guest_id),
    FOREIGN KEY (room_id) REFERENCES Room(room_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 部门表
CREATE TABLE Department (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(50) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 员工表
CREATE TABLE Employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    department_id INT NOT NULL,
    position VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    status TINYINT NOT NULL DEFAULT 1 COMMENT '0:离职 1:在职',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES Department(department_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 支付记录表
CREATE TABLE Payment (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_type TINYINT NOT NULL COMMENT '1:现金 2:信用卡 3:支付宝 4:微信',
    payment_time DATETIME NOT NULL,
    status TINYINT NOT NULL DEFAULT 0 COMMENT '0:处理中 1:成功 2:失败',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 维护记录表
CREATE TABLE Maintenance (
    maintenance_id INT PRIMARY KEY AUTO_INCREMENT,
    room_id INT NOT NULL,
    employee_id INT NOT NULL,
    issue_description TEXT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    status TINYINT NOT NULL DEFAULT 0 COMMENT '0:待处理 1:处理中 2:已完成',
    cost DECIMAL(10,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES Room(room_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 系统用户表
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 在插入数据前先清空表（注意顺序，要先清空有外键关联的表）
DELETE FROM Maintenance;
DELETE FROM Payment;
DELETE FROM CheckIn;
DELETE FROM Reservation;
DELETE FROM Room;
DELETE FROM RoomType;
DELETE FROM Employee;
DELETE FROM Department;
DELETE FROM sys_user;

-- 重置自增ID
ALTER TABLE Maintenance AUTO_INCREMENT = 1;
ALTER TABLE Payment AUTO_INCREMENT = 1;
ALTER TABLE CheckIn AUTO_INCREMENT = 1;
ALTER TABLE Reservation AUTO_INCREMENT = 1;
ALTER TABLE Room AUTO_INCREMENT = 1;
ALTER TABLE RoomType AUTO_INCREMENT = 1;
ALTER TABLE Employee AUTO_INCREMENT = 1;
ALTER TABLE Department AUTO_INCREMENT = 1;
ALTER TABLE sys_user AUTO_INCREMENT = 1;

-- 然后再插入数据
INSERT INTO Department (department_name, description) VALUES
('前台部', '负责接待、入住、退房等服务'),
('客房部', '负责房间清洁、维护等工作'),
('管理部', '负责酒店整体运营管理'),
('工程部', '负责设备维护和维修');

INSERT INTO RoomType (type_name, amenities, max_guests, base_price) VALUES
('标准间', '电视,空调,WiFi,24小时热水', 2, 288),
('豪华间', '电视,空调,WiFi,24小时热水,minibar', 2, 388),
('商务套房', '电视,空调,WiFi,24小时热水,minibar,工作区', 2, 588),
('总统套房', '电视,空调,WiFi,24小时热水,minibar,工作区,会客厅', 4, 1888);

INSERT INTO Room (room_number, room_type_id, floor, price, description) VALUES
('101', 1, 1, 288, '标准间-大床'),
('102', 1, 1, 288, '标准间-双床'),
('201', 2, 2, 388, '豪华间-大床'),
('202', 2, 2, 388, '豪华间-双床'),
('301', 3, 3, 588, '商务套房'),
('401', 4, 4, 1888, '总统套房');

-- 插入测试用户（密码为123456的BCrypt加密形式）
INSERT INTO sys_user (username, password, name, phone, email) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt2s8/u', '管理员', '13800138000', 'admin@example.com');

-- 创建索引
-- CREATE INDEX IF NOT EXISTS idx_room_status ON Room(status);
-- CREATE INDEX IF NOT EXISTS idx_reservation_dates ON Reservation(check_in_date, check_out_date);
-- CREATE INDEX IF NOT EXISTS idx_checkin_status ON CheckIn(status);
-- CREATE INDEX IF NOT EXISTS idx_payment_status ON Payment(status);
-- CREATE INDEX IF NOT EXISTS idx_maintenance_status ON Maintenance(status); 