drop table if exists member_style_product;
create table if not exists member_style_product
(
	id bigserial not null primary key ,
	member_style_id bigint not null references member_style(id)
		on update cascade
		on delete cascade ,
	model_number varchar(100) not null,
	display_number int not null
);

create index if not exists idx_member_style_product_member_style_id_display_number on member_style_product(member_style_id, display_number);
create index if not exists idx_member_style_product_model_number on member_style_product(model_number);