create table if not exists member
(
	id     bigserial    not null primary key,
	name   varchar(255) not null,
	email  varchar(255) null,
	gender varchar(10)  not null
);

create unique index if not exists uq_user_email on member (email);
create index if not exists idx_user_gender on member (gender);

drop table if exists member_style cascade ;
create table if not exists member_style
(
	id        bigserial not null primary key,
	member_id bigint    not null references member (id) on update cascade on delete cascade,
	style_id  bigint    not null references style (id) on update cascade on delete cascade
);

create index if not exists idx_member_style_member_id on member_style (member_id);
create index if not exists idx_member_style_style_id on member_style (style_id);
create unique index if not exists uq_member_style_member_id_style_id on member_style (member_id, style_id);