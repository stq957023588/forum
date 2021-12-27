DROP TABLE IF EXISTS User;
CREATE TABLE User
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '自增ID',
    NAME        VARCHAR(32) COMMENT '用户名称',
    EMAIL       VARCHAR(32) UNIQUE COMMENT '邮箱',
    PASSWORD    VARCHAR(128) COMMENT '密码',
    HEAD_PIC    VARCHAR(128) COMMENT '头像,网络地址',
    CREATE_TIME DATETIME COMMENT '创建时间'       DEFAULT NOW(),
    DELETED     INT COMMENT '是否删除;0为未删除,1为删除' DEFAULT 0,
    DELETE_TIME DATETIME COMMENT '逻辑删除时间'
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS Role;
CREATE TABLE Role
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '自增ID',
    NAME        VARCHAR(32) COMMENT '',
    ENABLE      INT COMMENT '是否启用,0否,1是' DEFAULT 1,
    DESCRIPTION VARCHAR(64) COMMENT '角色描述',
    CREATE_TIME DATETIME COMMENT '创建时间'  DEFAULT NOW(),
    DELETED     INT COMMENT '是否删除'       DEFAULT 0,
    DELETE_TIME DATETIME COMMENT '逻辑删除时间'
) ENGINE = INNODB
  CHARSET = UTF8;

DROP TABLE IF EXISTS Authority;
CREATE TABLE Authority
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '自增ID',
    NAME        VARCHAR(32) UNIQUE COMMENT '',
    DESCRIPTION VARCHAR(128) COMMENT '描述',
    URL         VARCHAR(256) COMMENT '接口地址',
    METHOD      VARCHAR(8) COMMENT 'HTTP请求方法,GET,POST',
    CREATOR     INT COMMENT '创建人',
    CREATE_TIME TIMESTAMP COMMENT '创建时间' DEFAULT NOW(),
    CONSTRAINT UN_URL_METHOD UNIQUE (URL, METHOD)
) ENGINE = INNODB
  CHARSET = UTF8;

DROP TABLE IF EXISTS Data_Rule;
CREATE TABLE Data_Rule
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '自增ID',
    NAME        VARCHAR(32) COMMENT '规则名',
    FIELD       VARCHAR(32) COMMENT '规则字段',
    `CONDITION` VARCHAR(32) COMMENT '规则条件.大于,等于,小于',
    VALUE       VARCHAR(32) COMMENT '规则值',
    CREATE_TIME DATETIME COMMENT '创建时间' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS data_rule_group;
CREATE TABLE Data_Rule_Group
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '自增ID',
    NAME        VARCHAR(32) COMMENT '规则组组名',
    ENABLE      INT COMMENT '是否启用'      DEFAULT 1,
    CREATE_TIME DATETIME COMMENT '创建时间' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS User_Role_Relation;
CREATE TABLE User_Role_Relation
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '自增ID',
    USER_ID     INT COMMENT '用户ID',
    ROLE_ID     INT COMMENT '角色ID',
    CREATE_TIME DATETIME COMMENT '创建时间 ' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS Role_Authority_Relation;
CREATE TABLE Role_Authority_Relation
(
    ID           INT PRIMARY KEY AUTO_INCREMENT COMMENT '自增ID',
    ROLE_ID      INT COMMENT '角色表ID',
    AUTHORITY_ID INT COMMENT '权限表ID',
    CREATE_TIME  DATETIME COMMENT '创建时间' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS Data_Rule_Group_Member;
CREATE TABLE Data_Rule_Group_Member
(
    ID                 INT PRIMARY KEY AUTO_INCREMENT COMMENT '',
    DATA_RULE_GROUP_ID INT COMMENT '数据规则组ID',
    DATA_GROUP_ID      INT COMMENT '数据规则ID',
    CREATE_TIME        DATETIME COMMENT '创建时间' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS Authority_Data_Rule;
CREATE TABLE Authority_Data_Rule
(
    ID                 INT PRIMARY KEY AUTO_INCREMENT COMMENT '',
    AUTHORITY_ID       INT COMMENT '权限ID',
    DATA_RULE_GROUP_ID INT COMMENT '数据规则组ID',
    ENABLE             INT COMMENT '是否启用'      DEFAULT 1,
    CREATE_TIME        DATETIME COMMENT '创建时间' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS Role_Authority_Rule_Data_Relation;
CREATE TABLE Role_Authority_Rule_Data_Relation
(
    ID                         INT PRIMARY KEY AUTO_INCREMENT COMMENT '',
    ROLE_AUTHORITY_RELATION_ID INT COMMENT '角色权限关联ID',
    DATA_RULE_GROUP_ID         INT COMMENT '数据规则组ID',
    CREATE_TIME                DATETIME COMMENT '创建时间' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS user_operation_log;
CREATE TABLE User_Operation_Log
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '',
    OPERATION   INT COMMENT '用户操作,字典',
    IP          INT COMMENT '操作时IP',
    ADDRESS     VARCHAR(16) COMMENT '操作时所在地',
    CREATE_TIME DATETIME COMMENT '创建时间/操作时间' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS Post;
CREATE TABLE Post
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '',
    TITLE       VARCHAR(64) COMMENT '标题',
    CONTENT     VARCHAR(512) COMMENT '内容,图片采用URL存储',
    CREATOR     INT COMMENT '创建人,USER的ID字段',
    CREATE_TIME DATETIME COMMENT '创建时间' DEFAULT NOW(),
    DELETED     INT COMMENT '是否删除'      DEFAULT 0,
    DELETE_TIME DATETIME COMMENT '删除时间'
) ENGINE = INNODB
  CHARSET = UTF8;
DROP TABLE IF EXISTS Post_Reply;
CREATE TABLE Post_Reply
(
    ID                 INT PRIMARY KEY AUTO_INCREMENT COMMENT '',
    SORT_ID            INT COMMENT '用于排序的ID,从1开始',
    POST_ID            INT COMMENT '贴子ID',
    REFERENCE_REPLY_ID INT COMMENT '引用回复ID',
    CONTENT            VARCHAR(512) COMMENT '内容,图片采用URL存储',
    CREATOR            INT COMMENT '创建人,USER的ID字段',
    CREATE_TIME        DATETIME COMMENT '创建时间' DEFAULT NOW(),
    DELETED            INT COMMENT ''          DEFAULT 0,
    DELETE_TIME        DATETIME COMMENT ''
) ENGINE = INNODB
  CHARSET = UTF8;

CREATE TABLE Role_Menu_Relation
(
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '',
    MENU_ID     INT COMMENT '菜单ID',
    ROLE_ID     INT COMMENT '角色ID',
    CREATOR     INT COMMENT '创建人,USER的ID字段',
    CREATE_TIME TIMESTAMP COMMENT '创建时间' DEFAULT NOW()
) ENGINE = INNODB
  CHARSET = UTF8;

DROP TABLE IF EXISTS MENU;
CREATE TABLE MENU
(
    ID             INT PRIMARY KEY AUTO_INCREMENT COMMENT '',
    NAME           VARCHAR(32) COMMENT '菜单名',
    DESCRIPTION    VARCHAR(128) COMMENT '描述',
    URL            VARCHAR(128) COMMENT '菜单对应地址',
    COMPONENT      VARCHAR(128) COMMENT '菜单对应组件',
    ICON           VARCHAR(128) COMMENT '菜单图标',
    PARENT_MENU_ID INT COMMENT '上级菜单ID',
    ENABLE         INT COMMENT '是否启用',
    CREATOR        INT COMMENT '创建人ID',
    CREATE_TIME    DATETIME COMMENT '创建时间',
    DELETED        INT COMMENT '逻辑删除' DEFAULT 0,
    DELETER        INT COMMENT '删除人',
    DELETE_TIME    DATETIME COMMENT '删除时间'
) ENGINE = INNODB
  CHARSET = UTF8;



insert into user(name, email, password)
values ('Admin', 'admin@admin.com', '$2a$10$ouTwwzgy/7J6ZvMxEaZWkeaUHtmWfXjGXzwEhZJeIoxdN4owwUfcu');

insert into role(name, DESCRIPTION)
VALUES ('Admin', 'have all authority');

INSERT INTO User_Role_Relation(USER_ID, ROLE_ID)
VALUES (1, 1);
