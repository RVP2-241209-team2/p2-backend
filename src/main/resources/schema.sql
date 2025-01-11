CREATE SCHEMA IF NOT EXISTS shoply_db;

-- shoply_db.products definition

-- Drop table

-- DROP TABLE shoply_db.products;

CREATE TABLE shoply_db.products (
	id uuid NOT NULL,
	description varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	price float8 NOT NULL,
	quantity int4 NOT NULL,
	CONSTRAINT products_pkey PRIMARY KEY (id)
);


-- shoply_db.tags definition

-- Drop table

-- DROP TABLE shoply_db.tags;

CREATE TABLE shoply_db.tags (
	id uuid NOT NULL,
	tag_name varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT tags_pkey PRIMARY KEY (id),
	CONSTRAINT uk2c6s9hekidseaj5vbgb3pgy3k UNIQUE (tag_name),
	CONSTRAINT ukt48xdq560gs3gap9g7jg36kgc UNIQUE (name)
);


-- shoply_db.users definition

-- Drop table

-- DROP TABLE shoply_db.users;

CREATE TABLE shoply_db.users (
	id uuid NOT NULL,
	email varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	phone_number varchar(255) NULL,
	"role" int2 NULL,
	username varchar(255) NOT NULL,
	CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_role_check CHECK (((role >= 0) AND (role <= 2))),
	CONSTRAINT users_unique UNIQUE (username)
);


-- shoply_db.addresses definition

-- Drop table

-- DROP TABLE shoply_db.addresses;

CREATE TABLE shoply_db.addresses (
	id uuid NOT NULL,
	address_line1 varchar(255) NOT NULL,
	address_line2 varchar(255) NULL,
	city varchar(255) NOT NULL,
	country varchar(255) NOT NULL,
	state varchar(255) NOT NULL,
	"type" int2 NOT NULL,
	zip_code varchar(255) NOT NULL,
	user_id uuid NULL,
	CONSTRAINT addresses_pkey PRIMARY KEY (id),
	CONSTRAINT addresses_type_check CHECK (((type >= 0) AND (type <= 1))),
	CONSTRAINT fk1fa36y2oqhao3wgg2rw1pi459 FOREIGN KEY (user_id) REFERENCES shoply_db.users(id)
);


-- shoply_db.cart definition

-- Drop table

-- DROP TABLE shoply_db.cart;

CREATE TABLE shoply_db.cart (
	id uuid NOT NULL,
	total float8 NOT NULL,
	user_id uuid NULL,
	CONSTRAINT cart_pkey PRIMARY KEY (id),
	CONSTRAINT uk9emlp6m95v5er2bcqkjsw48he UNIQUE (user_id),
	CONSTRAINT fkg5uhi8vpsuy0lgloxk2h4w5o6 FOREIGN KEY (user_id) REFERENCES shoply_db.users(id)
);


-- shoply_db.cart_items definition

-- Drop table

-- DROP TABLE shoply_db.cart_items;

CREATE TABLE shoply_db.cart_items (
	id uuid NOT NULL,
	quantity int4 NOT NULL,
	total float8 NOT NULL,
	cart_id uuid NULL,
	product_id uuid NULL,
	CONSTRAINT cart_items_pkey PRIMARY KEY (id),
	CONSTRAINT fk1re40cjegsfvw58xrkdp6bac6 FOREIGN KEY (product_id) REFERENCES shoply_db.products(id),
	CONSTRAINT fk99e0am9jpriwxcm6is7xfedy3 FOREIGN KEY (cart_id) REFERENCES shoply_db.cart(id)
);


-- shoply_db.payment_details definition

-- Drop table

-- DROP TABLE shoply_db.payment_details;

CREATE TABLE shoply_db.payment_details (
	id uuid NOT NULL,
	card_holder_name varchar(255) NOT NULL,
	card_number varchar(255) NOT NULL,
	expire_date varchar(255) NOT NULL,
	is_default bool NOT NULL,
	address_id uuid NULL,
	user_id uuid NULL,
	CONSTRAINT payment_details_pkey PRIMARY KEY (id),
	CONSTRAINT fkm5hvii42apg8xjrt70gi9sxd5 FOREIGN KEY (address_id) REFERENCES shoply_db.addresses(id),
	CONSTRAINT fko0204s82lvsh2q5f1bj273a23 FOREIGN KEY (user_id) REFERENCES shoply_db.users(id)
);


-- shoply_db.product_tags definition

-- Drop table

-- DROP TABLE shoply_db.product_tags;

CREATE TABLE shoply_db.product_tags (
	id uuid NOT NULL,
	product_id uuid NULL,
	tag_id uuid NULL,
	CONSTRAINT product_tags_pkey PRIMARY KEY (id),
	CONSTRAINT fk5rk6s19k3risy7q7wqdr41uss FOREIGN KEY (product_id) REFERENCES shoply_db.products(id),
	CONSTRAINT fkpur2885qb9ae6fiquu77tcv1o FOREIGN KEY (tag_id) REFERENCES shoply_db.tags(id)
);


-- shoply_db.reviews definition

-- Drop table

-- DROP TABLE shoply_db.reviews;

CREATE TABLE shoply_db.reviews (
	id uuid NOT NULL,
	description varchar(255) NOT NULL,
	rating int4 NOT NULL,
	title varchar(255) NOT NULL,
	product_id uuid NULL,
	user_id uuid NULL,
	CONSTRAINT reviews_pkey PRIMARY KEY (id),
	CONSTRAINT fkcgy7qjc1r99dp117y9en6lxye FOREIGN KEY (user_id) REFERENCES shoply_db.users(id),
	CONSTRAINT fkpl51cejpw4gy5swfar8br9ngi FOREIGN KEY (product_id) REFERENCES shoply_db.products(id)
);


-- shoply_db.orders definition

-- Drop table

-- DROP TABLE shoply_db.orders;

CREATE TABLE shoply_db.orders (
	id uuid NOT NULL,
	"date" varchar(255) NOT NULL,
	status int2 NOT NULL,
	total float8 NOT NULL,
	payment_details_id uuid NULL,
	address_id uuid NULL,
	user_id uuid NULL,
	CONSTRAINT orders_pkey PRIMARY KEY (id),
	CONSTRAINT orders_status_check CHECK (((status >= 0) AND (status <= 3))),
	CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES shoply_db.users(id),
	CONSTRAINT fkhlglkvf5i60dv6dn397ethgpt FOREIGN KEY (address_id) REFERENCES shoply_db.addresses(id),
	CONSTRAINT fkrd13pl3vdcgb1es375k7a83qq FOREIGN KEY (payment_details_id) REFERENCES shoply_db.payment_details(id)
);


-- shoply_db.order_items definition

-- Drop table

-- DROP TABLE shoply_db.order_items;

CREATE TABLE shoply_db.order_items (
	id uuid NOT NULL,
	quantity int4 NOT NULL,
	total float8 NOT NULL,
	order_id uuid NULL,
	product_id uuid NULL,
	CONSTRAINT order_items_pkey PRIMARY KEY (id),
	CONSTRAINT fkbioxgbv59vetrxe0ejfubep1w FOREIGN KEY (order_id) REFERENCES shoply_db.orders(id),
	CONSTRAINT fkocimc7dtr037rh4ls4l95nlfi FOREIGN KEY (product_id) REFERENCES shoply_db.products(id)
);