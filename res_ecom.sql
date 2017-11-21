CREATE SCHEMA res_ecom;

CREATE TABLE res_ecom.address_book ( 
	id                   int    
 );

CREATE TABLE res_ecom.area ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	name                 varchar(100)    ,
	CONSTRAINT pk_area PRIMARY KEY ( id ),
	CONSTRAINT pk_area_0 UNIQUE ( name ) 
 ) engine=InnoDB;

CREATE TABLE res_ecom.city ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	name                 varchar(100)    ,
	CONSTRAINT pk_city PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE TABLE res_ecom.delivery_address ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	city                 varchar(20)    ,
	area                 varchar(20)    ,
	street               varchar(20)    ,
	house                varchar(20)    ,
	floor                varchar(20)    ,
	CONSTRAINT pk_address_user_1 PRIMARY KEY ( id )
 );

CREATE TABLE res_ecom.food_catagory ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	name                 varchar(20)    ,
	CONSTRAINT pk_food_catagory PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE TABLE res_ecom.restaurant ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	name                 varchar(100)    ,
	email                varchar(30)    ,
	pass                 varchar(30)    ,
	phn_num              varchar(100)    ,
	city                 int    ,
	area                 int    ,
	street               varchar(50)    ,
	house_no             varchar(30)    ,
	floor                varchar(10)    ,
	about                varchar(500)    ,
	restaurant_pic_url   varchar(50)    ,
	delivery_charge      numeric(15,2)    ,
	CONSTRAINT pk_restaurant PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_restaurant ON res_ecom.restaurant ( city );

CREATE INDEX idx_restaurant_0 ON res_ecom.restaurant ( area );

ALTER TABLE res_ecom.restaurant MODIFY phn_num varchar(100)     COMMENT 'if more than one phn number, put them using comma
';

CREATE TABLE res_ecom.status ( 
	id                   int    ,
	status_name          varchar(100)    
 ) engine=InnoDB;

CREATE INDEX idx_status ON res_ecom.status ( id );

CREATE TABLE res_ecom.user_address ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	city                 varchar(20)    ,
	area                 varchar(20)    ,
	street               varchar(20)    ,
	house                varchar(20)    ,
	floor                varchar(20)    ,
	user_id              int    ,
	CONSTRAINT pk_address_user PRIMARY KEY ( id ),
	CONSTRAINT pk_address_user_0 UNIQUE ( user_id ) 
 ) engine=InnoDB;

CREATE TABLE res_ecom.menus ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	restaurant_id        int    ,
	name                 varchar(100)    ,
	ingredient           varchar(500)    ,
	pic_url              varchar(100)    ,
	price                numeric(15,2)    ,
	show_menu            bool    ,
	catagory_id          varchar(20)    ,
	CONSTRAINT pk_menus PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE INDEX idx_menus ON res_ecom.menus ( restaurant_id );

CREATE INDEX idx_menus_0 ON res_ecom.menus ( catagory_id );

ALTER TABLE res_ecom.menus MODIFY catagory_id varchar(20)     COMMENT 'breakfast, lunch, dinner';

CREATE TABLE res_ecom.offer ( 
	id                   int    ,
	user_id              int    ,
	code                 int    ,
	restaurant_id        int    ,
	percentage           int    ,
	CONSTRAINT pk_offer UNIQUE ( id ) 
 ) engine=InnoDB;

CREATE INDEX idx_offer ON res_ecom.offer ( restaurant_id );

CREATE TABLE res_ecom.user ( 
	id                   int  NOT NULL  AUTO_INCREMENT,
	name                 varchar(100)    ,
	email                varchar(30)    ,
	phn_num              int  NOT NULL  ,
	pass                 varchar(16)    ,
	CONSTRAINT pk_user PRIMARY KEY ( id )
 ) engine=InnoDB;

CREATE TABLE res_ecom.cart ( 
	menu_id              int    ,
	quantity             int    ,
	price                numeric(15,2)    
 );

CREATE INDEX idx_cart ON res_ecom.cart ( menu_id );

CREATE TABLE res_ecom.order ( 
	id                   int  NOT NULL  ,
	is_registered_user   varchar(1)    ,
	user_id              int    ,
	delivery_address_id  int    ,
	name                 varchar(100)    ,
	phn_num              varchar(11)    ,
	is_home_delive       varchar(1)    ,
	desired_delivery_date date    ,
	desired_delivery_time time    ,
	delivered_date       date    ,
	created_date         date    ,
	created_time         time    ,
	delivered_time       time    ,
	status_id            int    ,
	total_price_befor_offer numeric(15,2)    ,
	offer_id             int    ,
	final_price          numeric(15,2)    ,
	customer_comment     varchar(300)    ,
	CONSTRAINT pk_orde PRIMARY KEY ( id ),
	CONSTRAINT pk_order UNIQUE ( status_id ) 
 ) engine=InnoDB;

CREATE INDEX idx_order ON res_ecom.order ( offer_id );

CREATE INDEX idx_order_0 ON res_ecom.order ( user_id );

ALTER TABLE res_ecom.order MODIFY is_home_delive varchar(1)     COMMENT 'Will customer pick up from the restaurant, or he needs home delivery?';

ALTER TABLE res_ecom.order MODIFY final_price numeric(15,2)     COMMENT 'price after calculating offer if any
';

CREATE TABLE res_ecom.ordered_menus ( 
	id                   int    ,
	order_id             int    ,
	menu_id              int    ,
	quantity             int    ,
	total_price          numeric(15,2)    ,
	restaurant_id        int    
 ) engine=InnoDB;

CREATE INDEX idx_ordered_menus ON res_ecom.ordered_menus ( order_id );

CREATE INDEX idx_ordered_menus_0 ON res_ecom.ordered_menus ( menu_id );

ALTER TABLE res_ecom.cart ADD CONSTRAINT fk_cart FOREIGN KEY ( menu_id ) REFERENCES res_ecom.menus( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.menus ADD CONSTRAINT fk_menus FOREIGN KEY ( restaurant_id ) REFERENCES res_ecom.restaurant( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.menus ADD CONSTRAINT fk_menus_0 FOREIGN KEY ( catagory_id ) REFERENCES res_ecom.food_catagory( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.offer ADD CONSTRAINT fk_offer FOREIGN KEY ( restaurant_id ) REFERENCES res_ecom.restaurant( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.order ADD CONSTRAINT fk_order FOREIGN KEY ( status_id ) REFERENCES res_ecom.status( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.order ADD CONSTRAINT fk_order_0 FOREIGN KEY ( offer_id ) REFERENCES res_ecom.offer( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.order ADD CONSTRAINT fk_order_1 FOREIGN KEY ( user_id ) REFERENCES res_ecom.user( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.ordered_menus ADD CONSTRAINT fk_ordered_menus FOREIGN KEY ( order_id ) REFERENCES res_ecom.order( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.ordered_menus ADD CONSTRAINT fk_ordered_menus_0 FOREIGN KEY ( menu_id ) REFERENCES res_ecom.menus( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.restaurant ADD CONSTRAINT fk_restaurant FOREIGN KEY ( city ) REFERENCES res_ecom.city( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.restaurant ADD CONSTRAINT fk_restaurant_0 FOREIGN KEY ( area ) REFERENCES res_ecom.area( id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE res_ecom.user ADD CONSTRAINT fk_user FOREIGN KEY ( id ) REFERENCES res_ecom.user_address( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;

