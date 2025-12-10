DROP TABLE IF EXISTS style CASCADE;
CREATE TABLE IF NOT EXISTS style
(
	id             bigserial     not null primary key,
	name           varchar(100)  not null,
	image_url      varchar(255)  not null,
	key_style      varchar(50)[] null,
	movement       varchar(50)   null,
	strap_material varchar(50)   null,
	colors         varchar(50)[] null,
	price          varchar(50)   null,
	note           text          not null
);

CREATE TABLE IF NOT EXISTS tag
(
	id   bigserial   not null primary key,
	name varchar(50) not null unique
);

CREATE TABLE IF NOT EXISTS style_tag
(
	id       bigserial not null primary key,
	style_id bigint    not null references style (id) on delete cascade on update cascade,
	tag_id   bigint    not null references tag (id) on delete cascade on update cascade
);
