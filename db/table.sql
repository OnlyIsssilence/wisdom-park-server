
CREATE TABLE `wp_server_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `config_key` varchar(64) DEFAULT '' COMMENT '配置数据的键',
  `config_value` varchar(512) default '' COMMENT '配置数据的值',
  `introduction` VARCHAR(256) default '' comment '计划简介',
  PRIMARY KEY(`id`),
  unique key configKey(config_key)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment='全局配置表';

CREATE TABLE `wp_dept_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(64) NOT NULL COMMENT '组织名称',
  `type` tinyint(3) not null default 1 COMMENT '组织类型 0根 1组织 2项目 3部门',
  `parent_id` bigint(20) not null COMMENT '上一级组织主键id',
  `level` int default 1 COMMENT '组织层级 根组织层级为0 依次向下为1 2 3 最高层级为3 类似目录级别',
  `out_dept_status` tinyint(3) default 0 COMMENT '是否是外协单位 0 不是 1 是',
  `select_out_dept_status` tinyint(3) default 0 COMMENT '是否可以选择外协单位 0 可以 1 不可以',
  PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='企业架构信息表';

CREATE TABLE `wp_project_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dept_id` bigint(20) NOT NULL COMMENT '组织(项目)主键id',
  `longitude` decimal(10, 7) default 0.0 COMMENT '项目经度',
  `latitude` decimal(10, 7) default 0.0 COMMENT '项目纬度',
  `address` varchar(256) default '' COMMENT '项目地址',
  `area` varchar(32) default '' COMMENT '项目面积',
  `type` int default 0 COMMENT '项目类型',
  `owner_number` bigint(16) default 0 COMMENT '业主数量',
  `select_out_dept_status` tinyint(3) default 0 COMMENT '是否可以选择外协单位 0 可以 1 不可以',
  PRIMARY KEY(`id`),
  unique key deptId(dept_id)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='项目信息表';

CREATE TABLE `wp_menu_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '页面编号',
  `name` varchar(64) default '' COMMENT '页面名称',
  `path` varchar(256) not null COMMENT '页面路径',
  `introduction` VARCHAR(256) default '' comment '计划简介',
  `parent_menu_id` bigint(20) default 0 COMMENT '父页面主键id',
  PRIMARY KEY(`id`),
  unique key idCode(id_code)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='后台页面信息表';

CREATE TABLE `wp_button_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '按钮编号',
  `name` varchar(32) default '' COMMENT '按钮名称',
  `style` varchar(64) default '' COMMENT '按钮样式',
  `function` VARCHAR(128) default '' comment '按钮功能',
  `parent_menu_id` bigint(20) default 0 COMMENT '所在页面主键id',
  PRIMARY KEY(`id`),
  unique key idCode(id_code)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='后台按钮信息表';

CREATE TABLE `wp_position_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(3) NOT NULL default 1 COMMENT '岗位状态 1启用 0停用',
  `name` varchar(32) default '' COMMENT '岗位名称',
  `last_start_time` bigint(20) default 0 COMMENT '最近一次启用时间',
  `last_stop_time` bigint(20) default 0 COMMENT '最近一次停用时间',
  `postion_type_id` int not null comment '岗位类别主键id',
  `introduction` VARCHAR(256) default '' comment '计划简介',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='岗位信息表';

CREATE TABLE `wp_position_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(32) default '' COMMENT '岗位名称',
  `introduction` VARCHAR(256) default '' comment '计划简介',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='岗位类别表';

CREATE TABLE `wp_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(32) default '' COMMENT '角色操作权限名称',
  `operate_menu_ids` varchar(128) default '' COMMENT '可操作的页面主键id集合 多个以逗号隔开',
  `operate_button_ids` varchar(512) default '' COMMENT '可操作的按钮主键id集合 多个以逗号隔开',
  `app_grant_type` tinyint(3) default 0 COMMENT 'app端登录的角色 1内控端app管理员登录 0内控端app维修员登录 默认0',
  `max_order_num` bigint(16) default 0 comment '工程端最多接单数量',
  PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='角色操作权限表';

CREATE TABLE `wp_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `account_name` varchar(64) default '' COMMENT '账号名称',
  `phone` varchar(24) not null COMMENT '用户手机号',
  `idCardNo` varchar(24) default '' COMMENT '用户身份证号',
  `user_name` varchar(24) default '' COMMENT '用户真实姓名',
  `password` varchar(24) not null COMMENT '密码',
  `sex` tinyint(3) default 0 COMMENT '用户性别 0男 1女 默认0',
  `dept_id` bigint(20) not null COMMENT '用户所属组织主键id',
  `position_info_id` bigint(20) default 0 comment '岗位名称主键id',
  `role_permission_id` bigint(20) default 0 comment '角色主键id',
  `look_over_dept_ids` varchar(512) default '' comment '可查看组织的主键id 多个用逗号隔开',
  PRIMARY KEY(`id`),
  unique key ukPhone(phone)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='账户信息表';


CREATE TABLE `wp_equip_system_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '设备系统编号',
  `name` varchar(64) not null COMMENT '设备系统名称',
  `introduction` varchar(128) default '' COMMENT '设备系统说明',
  `status` tinyint(3) not null default 1 COMMENT '启用状态 1 启用 0 禁用 默认1',
  `belong_type` tinyint(3) not null default 0 COMMENT '设备系统类型的使用类型 0全局性质数据 1具体组织的数据 默认0',
  `dept_id` bigint(20) not null COMMENT '设备系统类别所属组织主键id',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  `audit_status` int not null default 0 COMMENT '只针对type=1时有用 审核状态 待定 ',
  PRIMARY KEY(`id`),
  unique key code_type_dept(id_code, belong_type, dept_id)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='全局设备系统类别表';

CREATE TABLE `wp_global_machine_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '机房类别编号',
  `name` varchar(64) not null COMMENT '设备系统名称',
  `introduction` varchar(128) default '' COMMENT '设备系统说明',
  `status` tinyint(3) not null default 1 COMMENT '启用状态 1 启用 0 禁用 默认1',
  `dept_id` bigint(20) not null COMMENT '所属组织主键id',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  `equip_system_class_id` bigint(20) not null comment '所属全局系统设备类别主键id',
  PRIMARY KEY(`id`),
  unique key ukIdCode(id_code)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='全局机房类别表';

CREATE TABLE `wp_global_equip_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '机房类别编号',
  `name` varchar(64) not null COMMENT '设备系统名称',
  `introduction` varchar(128) default '' COMMENT '设备系统说明',
  `status` tinyint(3) not null default 1 COMMENT '启用状态 1 启用 0 禁用 默认1',
  `dept_id` bigint(20) not null COMMENT '所属组织主键id',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  `equip_system_class_id` bigint(20) not null comment '所属全局系统设备类别主键id',
  `global_machine_class_id` bigint(20) not null comment '所属全局机房类别主键id',
  PRIMARY KEY(`id`),
  unique key ukIdCode(id_code)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='全局设备类别表';

CREATE TABLE `wp_org_machine_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '企业机房编号',
  `name` varchar(64) not null COMMENT '企业机房名称',
  `address` varchar(128) default '' COMMENT '企业机房地址',
  `audit_status` int not null default 0 COMMENT '审核状态 待定',
  `dept_id` bigint(20) not null COMMENT '所属组织主键id',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  `equip_system_class_id` bigint(20) not null comment '所属企业系统设备类别主键id',
  `global_machine_class_id` bigint(20) not null comment '所属全局机房类别主键id',
  PRIMARY KEY(`id`),
  unique key ukIdCode(id_code)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='企业机房信息表';

CREATE TABLE `wp_org_device_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '设备编号',
  `name` varchar(64) not null COMMENT '设备名称',
  `audit_status` int not null default 0 COMMENT '审核状态 待定',
  `dept_id` bigint(20) not null COMMENT '所属组织主键id',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  `equip_system_class_id` bigint(20) not null comment '所属企业系统设备类别主键id',
  `org_machine_id` bigint(20) not null comment '所属企机房主键id',
  `standard_type` varchar(32) default '' comment '规格型号',
  `device_level` varchar(10) default '' comment '设备级别',
  `produce_company` varchar(64) default '' comment '制造单位',
  `factory_time` bigint(20) default 0 comment '出厂日期',
  `factory_no` varchar(20) default '' comment '出厂编号',
  `use_time` timestamp DEFAULT CURRENT_TIMESTAMP comment '使用日期',
  `check_time` timestamp DEFAULT CURRENT_TIMESTAMP comment '验收日期',
  `protect_maintain_time` int default 0 comment '保修期限 单位为月',
  `extra_params` varchar(512) default '' comment '附加技术参数 json格式字符串',
  `extraFileIds` varchar(128) default '' comment '附加文件主键id 多个以逗号隔开',
  `extraDevices` varchar(512) default '' comment '附加配件的参数 json格式字符串',
  PRIMARY KEY(`id`),
  unique key ukIdCode(id_code)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='企业设备信息表';

CREATE TABLE `wp_global_maintain_standard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '操作编号',
  `name` varchar(64) not null COMMENT '操作名称',
  `dept_id` bigint(20) not null COMMENT '所属组织主键id',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  `operator_type` tinyint(3) not null default 0 COMMENT '设备的操作类型 0 维保 1 巡检 默认0',
  `equip_system_class_id` bigint(20) default 0 COMMENT '所属全局设备系统类别主键id',
  `global_machine_class_id` bigint(20) default 0 comment '所属全局机房类别主键id',
  `global_equip_class_id` bigint(20) default 0 comment '所属全局设备类别主键id',
  PRIMARY KEY(`id`),
  unique key ukIdCodeType(id_code, operator_type)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='全局维修标准表';

CREATE TABLE `wp_global_maintain_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '操作编号',
  `name` varchar(64) not null COMMENT '操作名称',
  `dept_id` bigint(20) not null COMMENT '所属组织主键id',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  `operator_type` tinyint(3) not null default 0 COMMENT '设备的操作类型 0 维保 1 巡检 默认0',
  `equip_system_class_id` bigint(20) default 0 COMMENT '所属全局设备系统类别主键id',
  `global_machine_class_id` bigint(20) default 0 comment '所属全局机房类别主键id',
  `global_equip_class_id` bigint(20) default 0 comment '所属全局设备类别主键id',
  PRIMARY KEY(`id`),
  unique key ukIdCodeType(id_code, operator_type)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='全局维保具体条目信息表';

CREATE TABLE `wp_global_maintain_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id key',
  `properties` varchar(2048) comment 'properties',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time of record, for db',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_code` varchar(36) NOT NULL COMMENT '操作编号',
  `name` varchar(64) not null COMMENT '操作名称',
  `dept_id` bigint(20) not null COMMENT '所属组织主键id',
  `creator_account_id` bigint(20) default 0 comment '创建者主键id',
  `equip_system_class_id` bigint(20) default 0 COMMENT '所属全局设备系统类别主键id',
  `global_machine_class_id` bigint(20) default 0 comment '所属全局机房类别主键id',
  `taskTimes` varchar(256) default '' comment '计划执行时间 分段存储开始/结束/是否可修改，可以有几段以list json格式存储',
  `status` tinyint(3) default 1 comment '使用状态 0 禁用 1使用 默认1',
  `introduction` varchar(128) default '' comment '描述',
  PRIMARY KEY(`id`),
  unique key ukIdCode(id_code)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment ='全局维保计划表';














