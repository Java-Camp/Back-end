create table "user"
(
    id int generated as identity
        constraint USER_PK
            primary key,
    first_name VARCHAR(100) not null,
    last_name VARCHAR(150) not null,
    email VARCHAR(100) not null,
    password VARCHAR(100)
)
    /

create unique index USER_EMAIL_UINDEX
	on "user" (email)
/