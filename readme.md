

```sql

CREATE TABLE `lp_bank_config` (
`id`  integer NOT NULL AUTO_INCREMENT ,
`bank_name`  varchar(64) NOT NULL ,
`create_time`  bigint NOT NULL ,
`update_time`  bigint NOT NULL COMMENT '银行名称' ,
PRIMARY KEY (`id`)
)
;

```


```sql
CREATE TABLE `lp_alipay_config` (
`id`  integer NOT NULL AUTO_INCREMENT ,
`private_key`  varchar(255) NOT NULL COMMENT '私钥' ,
`public_key`  varchar(255) NOT NULL COMMENT '公钥' ,
`type`  int NOT NULL COMMENT'类型' ,
`status`  int NOT NULL ,
`bank_id`  integer NOT NULL COMMENT '银行Id' ,
PRIMARY KEY (`id`)
)
;

```


```sql

CREATE TABLE `lp_wx_config` (
`id`  integer NOT NULL AUTO_INCREMENT ,
`private_key`  varchar(255) NOT NULL COMMENT '私钥' ,
`public_key`  varchar(255) NOT NULL COMMENT '公钥' ,
`app_id`  varchar(255) NOT NULL COMMENT 'AppID' ,
`type`  int NOT NULL COMMENT '类型' ,
`status`  int NOT NULL ,
`bank_id`  integer NOT NULL COMMENT '银行Id' ,
`mch_id`   varchar(255) NOT NULL COMMENT '商户号' ,
`pid`   varchar(255) NOT NULL COMMENT 'pid', 
PRIMARY KEY (`id`)
)
;

```

```sql

CREATE TABLE `lp_jd_config` (
`id`  integer NOT NULL AUTO_INCREMENT ,
`private_key`  varchar(255) NOT NULL COMMENT '私钥' ,
`public_key`  varchar(255) NOT NULL COMMENT '公钥' ,
`account_no`  varchar(255) NOT NULL COMMENT '', 
`risk_des_key`  varchar(255) NOT NULL COMMENT '' ,
`type`  int NOT NULL COMMENT '类型' ,
`status`  int NOT NULL ,
`bank_id`  integer NOT NULL COMMENT '银行Id' ,
PRIMARY KEY (`id`)
)
;

```

```sql
CREATE TABLE `lp_bestpay_config` (
`id`  integer NOT NULL AUTO_INCREMENT ,
`private_key`  varchar(255) NOT NULL COMMENT '私钥' ,
`public_key`  varchar(255) NOT NULL COMMENT '公钥' ,
`date_key`  varchar(255) NOT NULL COMMENT '' ,
`type`  int NOT NULL COMMENT '类型' ,
`status`  int NOT NULL ,
`bank_id`  integer NOT NULL COMMENT '银行Id' ,
`mch_id`   varchar(255) NOT NULL COMMENT '商户号' ,
`mch_name`   varchar(255) NOT NULL COMMENT '商户名称' ,
`mch_alias`   varchar(255) NOT NULL COMMENT '商户名称' ,
`user_name`   varchar(255) NOT NULL COMMENT '商户名称' ,
`password`   varchar(255) NOT NULL COMMENT '商户名称' ,
PRIMARY KEY (`id`)
)
;
```

```sql

create table lp_liquidator
(
    `id`  integer NOT NULL AUTO_INCREMENT ,
	liquidator_id varchar(28) not null comment '清算方Id',
	liquidator_name varchar(32) not null comment '清算方名称',
	public_key text null comment '公钥',
	is_open_wx int(1) default '0' null comment '是否开启微信支付',
	is_open_alipay int(1) default '0' null comment '是否开启支付宝支付',
	notify_url varchar(255) null comment '回调地址url',
	status int(1) default '1' null comment '1、开启 0关闭',
	alipay_pid varchar(32) null comment 'isv pid',
	sub_appid varchar(32) null comment '微信分配的子商户公众账号ID',
	create_time bigint not null comment '创建时间',
	update_time bigint not null comment '修改时间',
	is_license int(1) default '0' not null comment '是否是持牌方，1、是 0、否',
	limit_momey decimal(11,2) default '0.00' null comment '交易限额',
	credit_card int(1) default '1' null comment '微信是否限制信用卡支付  1不限制   0为限制',
	agent_no varchar(128) null comment '代付文件agentNo',
	is_whitelist int(1) default '2' null comment '1是白名单，2不是白名单。在白名单中的清算方不会被风控。',
	is_open_auto_verify int(1) default '1' null comment '是否开启自动审核商户开关 0、关闭 1、开启',
	alipay_credit_card int(1) default '1' null comment '支付宝是否限制信用卡支付  1不限制   0为限制',
	risk_status int(1) default '1' null comment '风控状态:0.不需要进行风控查询  1.需要进行风控查询',
	channel_id varchar(32) null comment '渠道号',
	alipay_risk_white tinyint(1) default '0' null comment '是否为支付宝安全服务白名单: 0:不是 , 1: 是',
	`bank_id`  integer NOT NULL COMMENT '银行Id',
	PRIMARY KEY (`id`)
)
;

```


```sql

create table lp_liquidator_store
(
 `id`  integer NOT NULL AUTO_INCREMENT ,
	store_id varchar(20) not null comment '店铺id,这个是我们系统唯一，对应支付宝externa_id'
		,
	liquidator_store_id varchar(20) not null comment '清算方店铺id',
	liquidator_id varchar(28) not null comment '清算方id',
	platform_store_id varchar(20) null comment '支付平台storeId',
	merchant_name varchar(128) default '' not null comment '商户名称',
	alias_name varchar(64) default '' not null comment '别名',
	service_phone varchar(64) default '' not null comment '客服电话',
	contact_name varchar(64) default '' null comment '联系人名称',
	contact_phone varchar(64) default '' null comment '联系人电话',
	contact_mobile varchar(64) default '' null comment '联系人手机号',
	contact_email varchar(128) default '' null comment '联系人邮箱',
	category_id varchar(128) not null comment '经营类目',
	memo varchar(512) null comment '商户备注',
	balance decimal(16,2) default '0.00' not null comment '商户余额',
	status varchar(1) default '1' null comment '商户状态值，1、开启 0、关闭 2、未通过审核 3、审核中 4、不生成打款文件',
	create_time bigint default '0' null comment '创建时间',
	update_time bigint default '0' null comment '更新时间',
	liquidator_commission_rate decimal(10,6) null comment '自定义的清算方费率',
	alipid varchar(20) null,
	`bank_id`  integer NOT NULL COMMENT '银行Id',
	
	PRIMARY KEY (`id`)
)
;



```

```sql
create table lp_liquidator_order
(
    `id`  bigint NOT NULL AUTO_INCREMENT ,
	order_sn varchar(64) not null comment '清算平台订单id',
	pay_platform_order_sn varchar(64) null comment '支付平台订单号，支付宝或微信返回的订单号',
	liquidator_order_sn varchar(64) not null comment '清算方订单号',
	store_id varchar(28) not null comment '子商户号',
	bank_id  integer NOT NULL COMMENT '银行Id',
	liquidator_merchant_id varchar(28) null comment '清算方商户id',
	liquidator_id varchar(28) not null comment '清算方id',
	notify_url varchar(255) null comment '支付成功回调地址',
	order_type int(1) not null comment '订单类型 1、刷卡 2、预支付订单（扫码）',
	pay_platform_type int(1) default '1' not null comment '支付平台类型 1、支付宝 2、微信',
	real_money decimal(16,2) not null comment '实际收款金额：清算方上传的收款金额',
	net_money decimal(16,2) null comment '净收入 ， 实际收款金额－总手续费， 总手续费＝佣金手续费＋返佣手续费＋支付宝手续费',
	pay_platform_rate decimal(10,5) not null comment '支付平台费率',
	pay_platform_fee decimal(10,5) not null comment '支付平台手续费',
	bank_commission_rate decimal(10,5) not null comment '佣金费率,每笔交易，清算平台收取的费率',
	bank_commission_fee decimal(10,5) not null comment '佣金费率,每笔交易，清算平台收取的费用',
	liquidator_commission_rate decimal(10,5) not null comment '返佣费率,每笔交易，返给清算方的佣金费率',
	liquidator_commission_fee decimal(10,5) not null comment '返佣费率,每笔交易，返给清算方的佣金费',
	pay_status int(1) default '0' not null comment '0、未支付 1、支付成功 2、支付失败 3、退款中 4、退款成功 5、退款失败',
	pay_time bigint null comment '支付时间',
	create_time bigint not null comment '创建时间',
	update_time bigint not null comment '修改时间',
	create_day int(8) not null comment '支付时间日期的时间(yyyyMMdd)，精度到天，因为分库不支付date类型，加这个字段绕过限制',
	PRIMARY KEY (`id`)
)
;


```


```sql
create table lp_fiance
(
    `id`  bigint NOT NULL AUTO_INCREMENT ,
	order_sn varchar(64) not null comment '清算平台订单id'
		,
	pay_platform_order_sn varchar(64) null comment '支付平台订单号，支付宝或微信返回的订单号',
	liquidator_order_sn varchar(64) not null comment '清算方订单号',
	store_id varchar(28) not null comment '子商户号',
		bank_id  integer NOT NULL COMMENT '银行Id',
	liquidator_merchant_id varchar(28) null comment '清算方商户id',
	liquidator_id varchar(28) not null comment '清算方id',
	
	notify_url varchar(255) null comment '支付成功回调地址',
	order_type int(1) not null comment '订单类型 1、刷卡 2、预支付订单（扫码）',
	pay_platform_type int(1) default '1' not null comment '支付平台类型 1、支付宝 2、微信',
	real_money decimal(16,2) not null comment '实际收款金额：清算方上传的收款金额',
	net_money decimal(16,2) null comment '净收入 ， 实际收款金额－总手续费， 总手续费＝佣金手续费＋返佣手续费＋支付宝手续费',
	pay_platform_rate decimal(10,5) not null comment '支付平台费率',
	pay_platform_fee decimal(10,5) not null comment '支付平台手续费',
	bank_commission_rate decimal(10,5) not null comment '佣金费率,每笔交易，清算平台收取的费率',
	bank_commission_fee decimal(10,5) not null comment '佣金费率,每笔交易，清算平台收取的费用',
	liquidator_commission_rate decimal(10,5) not null comment '返佣费率,每笔交易，返给清算方的佣金费率',
	liquidator_commission_fee decimal(10,5) not null comment '返佣费率,每笔交易，返给清算方的佣金费',
	pay_status int(1) default '0' not null comment '0、未支付 1、支付成功 2、支付失败 3、退款中 4、退款成功 5、退款失败',
	pay_time bigint null comment '支付时间',
	create_time bigint not null comment '创建时间',
	update_time bigint not null comment '修改时间',
		pay_day int(8) not null comment '支付时间日期的时间(yyyyMMdd)，精度到天，因为分库不支付date类型，加这个字段绕过限制',
create table lp_fiance
(
    `id`  bigint NOT NULL AUTO_INCREMENT ,
	order_sn varchar(64) not null comment '清算平台订单id'
		,
	pay_platform_order_sn varchar(64) null comment '支付平台订单号，支付宝或微信返回的订单号',
	liquidator_order_sn varchar(64) not null comment '清算方订单号',
	store_id varchar(28) not null comment '子商户号',
		bank_id  integer NOT NULL COMMENT '银行Id',
	liquidator_merchant_id varchar(28) null comment '清算方商户id',
	liquidator_id varchar(28) not null comment '清算方id',
	
	notify_url varchar(255) null comment '支付成功回调地址',
	order_type int(1) not null comment '订单类型 1、刷卡 2、预支付订单（扫码）',
	pay_platform_type int(1) default '1' not null comment '支付平台类型 1、支付宝 2、微信',
	real_money decimal(16,2) not null comment '实际收款金额：清算方上传的收款金额',
	net_money decimal(16,2) null comment '净收入 ， 实际收款金额－总手续费， 总手续费＝佣金手续费＋返佣手续费＋支付宝手续费',
	pay_platform_rate decimal(10,5) not null comment '支付平台费率',
	pay_platform_fee decimal(10,5) not null comment '支付平台手续费',
	bank_commission_rate decimal(10,5) not null comment '佣金费率,每笔交易，清算平台收取的费率',
	bank_commission_fee decimal(10,5) not null comment '佣金费率,每笔交易，清算平台收取的费用',
	liquidator_commission_rate decimal(10,5) not null comment '返佣费率,每笔交易，返给清算方的佣金费率',
	liquidator_commission_fee decimal(10,5) not null comment '返佣费率,每笔交易，返给清算方的佣金费',
	pay_status int(1) default '0' not null comment '0、未支付 1、支付成功 2、支付失败 3、退款中 4、退款成功 5、退款失败',
	pay_time bigint null comment '支付时间',
	create_time bigint not null comment '创建时间',
	update_time bigint not null comment '修改时间',
		pay_day int(8) not null comment '支付时间日期的时间(yyyyMMdd)，精度到天，因为分库不支付date类型，加这个字段绕过限制',
	PRIMARY KEY (`id`)
)
;

)
;



```


```sql
create table lp_refund_order
(
  `id`  bigint NOT NULL AUTO_INCREMENT ,
	liquidator_id varchar(28) not null comment '清算方id',
	store_id varchar(28) default '' not null comment '店铺id',
	bank_id integer NOT NULL COMMENT '银行Id',
	order_sn varchar(64) not null comment '平台订单号',
	liquidator_order_sn varchar(64) not null comment '清算方订单号',
	platform_order_sn varchar(64) default '' not null comment '支付平台订单号',
	refund_no varchar(32) not null comment '退款号',
	out_refund_no varchar(32) not null comment '外部退款号',
	platform_refund_no varchar(32) default '' null comment '平台退款号',
	platform_type int not null comment '支付平台类型',
	order_money decimal(16,2) not null comment '订单金额',
	refund_money decimal(16,2) not null comment '退款金额',
	refund_status int default '0' not null comment '0、未支付 1、支付成功 2、支付失败 3、退款中 4、退款成功 5、退款失败',
	remit_status int(1) default '0' not null comment '打款状态，在退款失败的情况下，要进行手动打款',
	create_time bigint not null comment '创建时间',
	update_time bigint not null comment '修改时间',
	pay_time bigint default '0' not null comment '支付时间',
	bank_commission_rate decimal(10,5) default '0.00000' not null,
	bank_commission_fee decimal(10,5) default '0.00000' not null,
	pay_platform_rate decimal(10,5) default '0.00000' not null,
	pay_platform_fee decimal(10,5) default '0.00000' not null,
	is_more_day int default '0' not null comment '0. 不是多日退款 1. 是多日退款',
	liquidator_commission_rate decimal(10,5) default '0.00000' not null,
	liquidator_commission_fee decimal(10,5) default '0.00000' not null,
	PRIMARY KEY (`id`)
)
;



```

```sql
create table lp_user
(
	id INTEGER auto_increment comment '唯一id'
		primary key,
	username varchar(32) default '' not null comment '登录名',
	password varchar(255) default '' not null comment '密码',
	salt varchar(64) not null comment '盐',
		`bank_id`  integer NOT NULL COMMENT '银行Id',
	real_name varchar(32) default '' not null comment '真实姓名',
	phone varchar(32) null,
	department varchar(255) null,
	role_id varchar(32) not null comment '角色id',
	create_time bigint not null comment '创建时间',
	locked_time bigint null comment '锁定时间',
	update_time bigint null comment '更新时间',
	failed_count int(1) default '0' null comment '失败次数',
	status int(1) default '1' null comment '状态 1:正常 0:删除 2:锁定 3:禁止登陆'
)
;


```

```sql


create table lp_role
(
	role_id varchar(32) default '' not null comment '角色id'
		primary key,
	role_name varchar(32) default '' not null comment '角色名',
		`bank_id`  integer NOT NULL COMMENT '银行Id',
	create_time bigint not null comment '创建时间',
	update_time bigint not null comment '默认时间',
	status int(1) not null comment '角色状态: 0.关闭  1.开启'
)
;

```
```sql
create table lp_api_list
(
	id int auto_increment comment 'api id'
		primary key,
		
	api_name varchar(60) not null comment 'api接口名称',
	api_url varchar(200) not null comment 'api url 接口url， beanName,methodName',
	`bank_id`  integer NOT NULL COMMENT '银行Id',
	version varchar(5) default '1.0' not null comment '接口版本',
	is_open int(1) default '1' not null comment '接口是否开启 1开启 0关闭',
	description varchar(100) default '' null comment '接口描述',
	create_time bigint not null comment '创建时间',
	update_time bigint not null comment '修改时间'
)

```

```sql
create table lp_liquidation_api
(
	liquidator_id varchar(28) not null comment '清算方id',
	api_id int not null comment 'api接口id',
	is_open int(1) default '1' not null comment '是否开启 1开启 0关闭',
	create_time bigint not null,
	update_time bigint not null,
	primary key (liquidator_id, api_id)
)

```