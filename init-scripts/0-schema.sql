use demo;

drop table if exists message;

create table message
(
    id      int primary key auto_increment,
    content varchar(255) not null,
    created timestamp    not null default CURRENT_TIMESTAMP,
    updated timestamp    not null default current_timestamp
        on update CURRENT_TIMESTAMP
);