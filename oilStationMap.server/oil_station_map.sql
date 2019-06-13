create database oil_station_map;

create table o_ad_extension_history
(
	id int auto_increment comment 'id'
		primary key,
	ad_appId varchar(100) null comment '广告主的appId',
	remark varchar(100) null comment '广告备注',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '用户_创建时间',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '用户_更新时间',
	ad_extension_random_num varchar(100) null comment '广告被推广的唯一随机数',
	media_appId varchar(100) null comment '媒体的appId',
	status int default '0' null comment 'cpc的状态，0是计费，1是不计费'
)
comment '广告推广历史表'
;

create table o_comments
(
	id int auto_increment comment '意见_id'
		primary key,
	uid int not null,
	comments text not null comment '意见_意见内容',
	remark varchar(200) null comment '意见_备注',
	STATUS int default '0' null comment '意见_状态，0是未处理，1是已处理',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '意见_更新时间',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '意见_创建时间'
)
comment '意见'
;

create table o_custom_message_history
(
	id int auto_increment
		primary key,
	open_id varchar(100) not null comment '用户的唯一标识openId',
	mini_program_id varchar(100) null comment '小程序的唯一标示id',
	mini_program_name varchar(100) null comment '小程序名称',
	custom_message_type varchar(100) not null comment '客服消息内容种类',
	custom_message_code varchar(100) not null comment '客服消息编码',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间'
)
comment '客服消息访问历史记录'
;

create table o_dic
(
	id int auto_increment comment '字典表_id'
		primary key,
	dic_type varchar(50) not null comment '字典表_类型',
	dic_code varchar(100) not null comment '字典表_code',
	dic_name varchar(200) not null comment '字典表_名称',
	dic_remark varchar(1000) null comment '字典表_备注,遵循json字符串格式',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '字典表_创建时间',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '字典表_更新时间',
	dic_status int default '0' not null
)
comment '字典表'
;

create index index_o_dic_of_dic_code
	on o_dic (dic_code)
;

create index index_o_dic_of_dic_name
	on o_dic (dic_name)
;

create index index_o_dic_of_dic_type
	on o_dic (dic_type)
;

create index index_o_dic_of_id
	on o_dic (id)
;

create table o_league
(
	id int auto_increment comment 'id'
		primary key,
	uid varchar(50) null comment '用户uid',
	phone varchar(50) null comment '联系号码',
	name varchar(50) null comment '联系姓名',
	league_type_code varchar(50) null comment '加盟类型，0：我要开店，1：成为服务商，2：成为代理',
	remark varchar(50) null comment '加盟备注',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '用户_创建时间',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '用户_更新时间'
)
comment '加盟表'
;

create table o_oil_station
(
	id int auto_increment comment '加油站ID'
		primary key,
	oil_station_code varchar(100) not null comment '加油站编码',
	oil_station_name varchar(100) not null comment '加油站名称',
	oil_station_area_spell varchar(100) null comment '加油站地区拼音',
	oil_station_area_name varchar(45) null comment '加油站地区名称',
	oil_station_adress varchar(500) not null comment '加油站地址',
	oil_station_brand_name varchar(100) null comment '加油站品牌名称',
	oil_station_type varchar(100) not null comment '加油站类型：加盟店，民营店',
	oil_station_discount varchar(100) null comment '加油站折扣店',
	oil_station_exhaust varchar(100) null comment '加油站油品排放',
	oil_station_position varchar(100) not null comment '加油站经纬度位置（经度,纬度）',
	oil_station_lon varchar(100) not null comment '加油站经度',
	oil_station_lat varchar(100) not null comment '加油站纬度',
	oil_station_pay_type varchar(100) null comment '加油站支付方式',
	oil_station_price text not null comment '加油站油价，使用json格式进行存储',
	oil_station_distance varchar(100) null comment '加油站距离',
	is_manual_modify int default '0' null comment '是否手动更新，0为false,不手动更新即自动更新数据；1为true,人为手动更新数据',
	is_hire int default '0' null,
	oil_station_wx_payment_code_img_url varchar(1000) null,
	update_time timestamp default CURRENT_TIMESTAMP not null comment '更新时间n',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	share_title varchar(200) default '油价又要涨了!赶紧快来看看您附近的加油站的油价吧，秒慢无...' null,
	oil_station_owner_uid varchar(100) null comment '加油站业主uid',
	oil_station_hire_url varchar(1000) null,
	oil_station_hire_title varchar(1000) null
)
comment '加油站基本信息'
;

create index index_o_oil_station_of_id
	on o_oil_station (id)
;

create index index_o_oil_station_of_oil_station_code
	on o_oil_station (oil_station_code)
;

create index index_o_oil_station_of_oil_station_lat
	on o_oil_station (oil_station_lat)
;

create index index_o_oil_station_of_oil_station_lon
	on o_oil_station (oil_station_lon)
;

create index index_o_oil_station_of_oil_station_name
	on o_oil_station (oil_station_name)
;

create table o_oil_station_operator
(
	id int auto_increment comment '操作编码'
		primary key,
	uid int null comment '操作用户',
	oil_station_code varchar(100) null comment '加油站编码',
	red_packet_total varchar(100) default '0' null comment '红包金额',
	operator varchar(100) null comment '操作，目前分为 添加油站 和 纠正油价',
	status int default '0' null comment '状态，0,待审核;1,待发放;-1,已拒绝;2,已发放;',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '更新时间'
)
comment '加油站操作'
;

create table o_red_packet_draw_cash_history
(
	id int auto_increment
		primary key,
	uid int not null,
	draw_cash_money varchar(45) not null comment '提现金额',
	remark varchar(500) null,
	status int default '1' null comment '提现记录_状态，0是无效提现，1是有效提现',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间'
)
comment '红包提现记录'
;

create table o_red_packet_history
(
	id int auto_increment
		primary key,
	uid int not null,
	operator_id int null comment '操作ID',
	red_packet_money varchar(45) not null comment '红包金额',
	remark varchar(500) null,
	status int default '0' null comment '红包领取记录_状态，0是未正常发送红包(未领取)，1是正常发送红包(已领取)',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '更新时间'
)
comment '红包领取记录'
;

create table o_user
(
	id int auto_increment comment '用户_id'
		primary key,
	open_id varchar(50) null comment '微信的open_id',
	nick_name varchar(100) null comment '用户昵称',
	gender int null comment '性别',
	city varchar(100) null comment '城市',
	province varchar(100) null comment '省份',
	country varchar(100) null comment '国家',
	LANGUAGE varchar(100) null comment '用户的语言',
	avatar_url varchar(500) null comment '用户头像',
	recommend_uid int null,
	user_remark varchar(100) null comment '用户_备注',
	source varchar(200) default 'gh_417c90af3488' null,
	gray_status int default '0' null comment '灰度用户状态，0是正常登陆用户，1是不需要登陆的用户',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '用户_创建时间',
	update_time timestamp default CURRENT_TIMESTAMP not null comment '用户_更新时间'
)
comment '用户表'
;

create index index_o_user_of_id
	on o_user (id)
;

create index index_o_user_of_nick_name
	on o_user (nick_name)
;

create index index_o_user_of_open_id
	on o_user (open_id)
;

create table o_user_form_mapping
(
	id int auto_increment
		primary key,
	uid int not null comment '用户ID',
	form_id varchar(200) not null comment '给当前用户发送模板消息唯一标识的formId',
	form_id_status int default '0' null comment '是否使用当前formId,0是未使用，1是已使用',
	create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
	update_time timestamp default CURRENT_TIMESTAMP null comment '更新时间'
)
comment '用户与微信的模板消息的映射表'
;

