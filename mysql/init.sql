create table admin
(
    id       bigint auto_increment
        primary key,
    username varchar(255) not null,
    password varchar(255) not null
)
    collate = utf8mb4_unicode_ci
    row_format = DYNAMIC;

create table admin_permission
(
    id            bigint auto_increment
        primary key,
    admin_id      bigint not null,
    permission_id bigint not null
)
    collate = utf8mb4_unicode_ci
    row_format = DYNAMIC;

create table article
(
    id             bigint auto_increment
        primary key,
    comment_counts int                  null comment '评论数量',
    create_date    datetime             null comment '创建时间',
    summary        varchar(255)         null comment '简介',
    title          varchar(64)          null comment '标题',
    view_counts    int                  null comment '浏览数量',
    weight         int                  not null comment '是否置顶',
    author_id      bigint               null comment '作者id',
    body_id        bigint               null comment '内容id',
    category_id    int                  null comment '类别id',
    like_counts    int                  null,
    is_like        tinyint(1) default 0 null
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table article_body
(
    id           bigint auto_increment
        primary key,
    content      longtext null,
    content_html longtext null,
    article_id   bigint   not null
)
    charset = utf8mb3
    row_format = DYNAMIC;

create index article_id
    on article_body (article_id);

create table article_material
(
    id         bigint auto_increment
        primary key,
    time       datetime     null comment '上传时间',
    article_id bigint       null,
    url        varchar(255) null,
    name       varchar(32)  null comment '名称',
    size       varchar(32)  null comment '大小'
);

create table article_tag
(
    id         bigint auto_increment
        primary key,
    article_id bigint not null,
    tag_id     bigint not null
)
    charset = utf8mb3
    row_format = DYNAMIC;

create index article_id
    on article_tag (article_id);

create index tag_id
    on article_tag (tag_id);

create table category
(
    id            bigint auto_increment
        primary key,
    avatar        varchar(255) collate utf8mb4_unicode_ci null,
    category_name varchar(255) collate utf8mb4_unicode_ci null,
    description   varchar(255) collate utf8mb4_unicode_ci null
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table comment
(
    id          bigint auto_increment
        primary key,
    content     varchar(255) collate utf8mb4_unicode_ci not null,
    create_date datetime                                not null,
    article_id  bigint                                  not null,
    author_id   bigint                                  not null,
    parent_id   bigint                                  not null,
    to_uid      bigint                                  not null,
    level       varchar(1)                              not null
)
    charset = utf8mb3
    row_format = DYNAMIC;

create index article_id
    on comment (article_id);

create table curriculum
(
    id           bigint auto_increment
        primary key,
    name         varchar(32)  null comment '课程名称',
    credit       double       null comment '学分',
    introduction varchar(255) null comment '简介'
);

create table favorite
(
    id          bigint auto_increment
        primary key,
    name        varchar(100) null,
    count       bigint       null,
    create_time datetime     null,
    avatar      varchar(255) null
);

create table favorite_article
(
    id          bigint auto_increment
        primary key,
    favorite_id bigint null,
    article_id  bigint null
);

create table message
(
    id            bigint auto_increment
        primary key,
    send_nickname varchar(100) null,
    send_avatar   varchar(255) null,
    send_content  varchar(255) null,
    article_title varchar(100) null,
    article_id    bigint       null,
    to_uid        bigint       null,
    create_date   varchar(100) null
);

create table permission
(
    id          bigint auto_increment
        primary key,
    name        varchar(255) not null,
    path        varchar(255) not null,
    description varchar(255) not null
)
    collate = utf8mb4_unicode_ci
    row_format = DYNAMIC;

create table sys_log
(
    id          bigint auto_increment
        primary key,
    create_date bigint                                 null,
    ip          varchar(15) collate utf8mb3_bin        null,
    method      varchar(100) collate utf8mb3_bin       null,
    module      varchar(10) collate utf8mb3_bin        null,
    nickname    varchar(10) collate utf8mb4_unicode_ci null,
    operation   varchar(25) collate utf8mb3_bin        null,
    params      varchar(255) collate utf8mb3_bin       null,
    time        bigint                                 null,
    userid      bigint                                 null
)
    collate = utf8mb3_unicode_ci
    row_format = DYNAMIC;

create table sys_user
(
    id                  bigint auto_increment
        primary key,
    account             varchar(64)   null comment '账号',
    admin               bit           null comment '是否管理员',
    avatar              varchar(255)  null comment '头像',
    create_date         datetime      null comment '注册时间',
    deleted             bit           null comment '是否删除',
    email               varchar(128)  null comment '邮箱',
    last_login          datetime      null comment '最后登录时间',
    mobile_phone_number varchar(20)   null comment '手机号',
    nickname            varchar(255)  null comment '昵称',
    password            varchar(64)   null comment '密码',
    salt                varchar(255)  null comment '加密盐',
    status              varchar(255)  null comment '状态',
    permission          varchar(20)   null comment '用户权限',
    signature           varchar(2255) null,
    dept_name           varchar(255)  null
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table course
(
    id            bigint auto_increment
        primary key,
    curriculum_id bigint       not null,
    enrollment    int          null comment '上课人数',
    capacity      int          null comment '容量',
    teacher_id    bigint       not null,
    dept_name     varchar(100) null comment '系名称',
    week_begin    int          null comment '开始周次',
    week_end      int          null comment '结束周次',
    day           int          null comment '周几',
    section_begin int          null comment '开始节次',
    section_end   int          null comment '结束节次',
    room          varchar(32)  null comment '教室',
    category_id   bigint       null,
    constraint course_curriculum_fk
        foreign key (curriculum_id) references curriculum (id)
            on update cascade on delete cascade,
    constraint course_sys_user_fk
        foreign key (teacher_id) references sys_user (id)
            on update cascade on delete cascade
);

create table assignment
(
    id          bigint auto_increment
        primary key,
    name        varchar(32)  null comment '作业名称',
    description varchar(255) null comment '作业描述',
    begin_time  datetime     null comment '开始时间',
    end_time    datetime     null comment '结束时间',
    url         varchar(255) null comment '作业文件',
    course_id   bigint       null,
    file_name   varchar(100) null,
    constraint assignment_course_id
        foreign key (course_id) references course (id)
            on update cascade on delete cascade
);

create index assignment_course_fk
    on assignment (course_id);

create table assistant
(
    id         bigint auto_increment
        primary key,
    student_id bigint null,
    course_id  bigint null,
    status     int    null,
    constraint assistant_course_id
        foreign key (course_id) references course (id)
            on update cascade on delete cascade,
    constraint assistant_student_fk
        foreign key (student_id) references sys_user (id)
            on update cascade on delete cascade
);

create index assistant_course_FK
    on assistant (course_id);

create index clbum_curriculum_fk
    on course (curriculum_id);

create index course_sys_user_FK
    on course (teacher_id);

create table material
(
    id        bigint auto_increment
        primary key,
    time      datetime     null comment '上传时间',
    course_id bigint       null,
    url       varchar(255) null,
    name      varchar(32)  null comment '名称',
    size      varchar(32)  null comment '大小',
    constraint material_course_fk
        foreign key (course_id) references course (id)
            on update cascade on delete cascade
);

create index material_course_FK
    on material (course_id);

create table sc
(
    id         bigint auto_increment
        primary key,
    student_id bigint        null,
    course_id  bigint        null,
    score      decimal(3, 1) null,
    status     int           null,
    constraint sc_course_fk
        foreign key (course_id) references course (id)
            on update cascade on delete cascade,
    constraint sc_sys_user_fk
        foreign key (student_id) references sys_user (id)
            on update cascade on delete cascade
);

create trigger sc_delete
    after delete
    on sc
    for each row
begin
    if old.status = 1 then
        update course
        set enrollment = enrollment - 1
        where course.id = old.course_id;
    end if;
end;

create trigger sc_insert
    after insert
    on sc
    for each row
begin
    if new.status = 1 then
        update course
        set enrollment = enrollment + 1
        where course.id = new.course_id;
    end if;
end;

create trigger sc_update
    after update
    on sc
    for each row
begin
    if old.status = 1 then
        update course
        set enrollment = enrollment - 1
        where course.id = old.course_id;
    end if;
    if new.status = 1 then
        update course
        set enrollment = enrollment + 1
        where course.id = new.course_id;
    end if;
end;

create table submission
(
    id             bigint auto_increment
        primary key,
    submission_url varchar(255) null comment '作业文件',
    time           datetime     null comment '提交时间',
    status         bit          null comment '作业状态',
    score          int          null comment '分数',
    feedback_url   varchar(255) null comment '批改文件',
    comment        varchar(255) null comment '评语',
    assignment_id  bigint       null,
    student_id     bigint       null,
    file_name      varchar(100) null,
    constraint submit_assignment_fk
        foreign key (assignment_id) references assignment (id)
            on update cascade on delete cascade,
    constraint submit_student_fk
        foreign key (student_id) references sys_user (id)
            on update cascade on delete cascade
);

create table tag
(
    id       bigint auto_increment
        primary key,
    avatar   varchar(255) collate utf8mb4_unicode_ci null,
    tag_name varchar(255) collate utf8mb4_unicode_ci null
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table user_article_like
(
    id         bigint auto_increment
        primary key,
    article_id bigint null,
    user_id    bigint null
);

create table user_favorite
(
    id          bigint auto_increment
        primary key,
    user_id     bigint null,
    favorite_id bigint null
);

create table user_message
(
    id         bigint auto_increment
        primary key,
    user_id    bigint null,
    message_id bigint null
);

