drop table if exists member_style_item;
drop table if exists member_style;

create table member_style
(
	id         bigserial    not null primary key,
	member_id  bigint       not null references member (id) on update cascade on delete cascade,
	name       varchar(100) not null,
	summary    jsonb        not null,
	created_at timestamptz  not null default current_timestamp,
	updated_at timestamptz  not null default current_timestamp
);

create unique index uq_member_style_member_id_name on member_style (member_id, name);


create table member_style_item
(
	id              bigserial   not null primary key,
	member_style_id bigint      not null references member_style (id) on update cascade on delete cascade,
	style_id        bigint      not null references style (id) on update cascade on delete cascade,
	created_at      timestamptz not null default current_timestamp,
	updated_at      timestamptz not null default current_timestamp
);

create unique index uq_member_style_item_member_style_id_style_id on member_style_item (member_style_id, style_id);