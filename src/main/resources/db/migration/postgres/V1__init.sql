CREATE TABLE IF NOT EXISTS style
(
	id bigserial not null primary key,
	name varchar(50) not null,
	image_url varchar(255) not null,
	metadata  json null,
	description varchar(255) null
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
