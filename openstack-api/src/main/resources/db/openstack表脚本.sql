/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : openstack

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 14/04/2022 14:21:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) DEFAULT NULL COMMENT '用户账号',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `phone` varchar(20) DEFAULT NULL COMMENT '用户手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户信息表';


-- -----------------------------------
-- Table structure for openstack_auth
-- -----------------------------------
CREATE TABLE `openstack_auth` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack权限用户id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack权限用户名称',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack权限用户密码',
  `domain_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack权限域id',
  `domain_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack权限域名称',
  `project_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack项目id',
  `project_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack项目名称',
  `customer_id` int DEFAULT NULL COMMENT '用户id',
  `type` int DEFAULT NULL COMMENT '用户类型（1.openstack用户，2.用户）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='openstack keystone服务验证信息表';

-- --------------------------------------
-- Table structure for openstack_auth_net
-- --------------------------------------
CREATE TABLE `openstack_auth_net` (
  `id` int NOT NULL AUTO_INCREMENT,
  `network_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack网络id',
  `router_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack路由id',
  `sg_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'openstack安全组id',
  `subnet_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'openstack子网id',
  `region_id` int DEFAULT NULL COMMENT 'openstack区域id',
  `customer_id` int DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='用户在openstack不同region关联的网络信息表';

-- --------------------------
-- Table structure for region
-- --------------------------
CREATE TABLE `region` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '区域名称',
  `city` varchar(50) DEFAULT NULL COMMENT '区域所在地',
  `real_name` varchar(255) DEFAULT NULL COMMENT '区域真实名称',
  `ext_net_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'openstack外部网络id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='openstack区域信息表';

-- -----------------------------
-- Table structure for router_os
-- -----------------------------
CREATE TABLE `router_os` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) COLLATE utf8mb4_croatian_ci DEFAULT NULL COMMENT '网关ip',
  `port` int DEFAULT NULL COMMENT '连接端口号',
  `user_name` varchar(100) COLLATE utf8mb4_croatian_ci DEFAULT NULL COMMENT '登录账号',
  `pasword` varchar(100) COLLATE utf8mb4_croatian_ci DEFAULT NULL COMMENT '登录密码',
  `region_id` int DEFAULT NULL COMMENT '区域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci COMMENT='routerOS网关信息表';

-- ---------------------------
-- Table structure for rs_port
-- ---------------------------
CREATE TABLE `rs_port` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rs_id` int DEFAULT NULL COMMENT 'routerOS的id',
  `port` int DEFAULT NULL COMMENT '分配给机器的端口号',
  `virtual_id` int DEFAULT NULL COMMENT '机器id',
  `bind` int DEFAULT NULL COMMENT '是否绑定（0否，1是）',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci COMMENT='openstack机器绑定网关表';

-- ------------------------
-- Table structure for task
-- ------------------------
CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `virtual_ids` varchar(1000) DEFAULT NULL COMMENT '机器id集合',
  `success_num` int DEFAULT NULL COMMENT '成功台数',
  `fail_num` int DEFAULT NULL COMMENT '失败台数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='openstack机器操作任务状态表';

-- ---------------------------------
-- Table structure for task_fail_log
-- ---------------------------------
CREATE TABLE `task_fail_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int DEFAULT NULL COMMENT '创建任务id',
  `details` varchar(255) DEFAULT NULL COMMENT '失败原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='openstack机器创建失败日志表';

-- ---------------------------
-- Table structure for virtual
-- ---------------------------
CREATE TABLE `virtual` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '机器名称',
  `password` varchar(10) DEFAULT NULL COMMENT '机器密码',
  `status` int DEFAULT NULL COMMENT '机器状态（1已开机，2开机中，3关机中，4已关机，5创建中，6创建失败，7取消创建）',
  `customer_id` int DEFAULT NULL COMMENT '用户id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `instance_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'openstack实例id',
  `progress` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'openstack实例创建进度',
  `floating_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'openstack浮动ip',
  `floating_ip_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'openstack浮动ip id',
  `policy_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'openstack策略id',
  `volume_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'openstack数据盘id',
  `region_id` int DEFAULT NULL COMMENT 'openstack区域id',
  `is_delete` int DEFAULT '0' COMMENT '是否删除（0否，1是）',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='openstack机器表';