CREATE USER IF NOT EXISTS 'repl_user'@'%' IDENTIFIED WITH mysql_native_password BY 'repl_pass';
GRANT REPLICATION SLAVE ON *.* TO 'repl_user'@'%';
FLUSH PRIVILEGES;
SET GLOBAL transaction_isolation = 'REPEATABLE-READ';

CREATE DATABASE IF NOT EXISTS nacos_config
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 创建 root 远程访问权限 (生产环境应使用专用用户)
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '12345';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;

USE testdb;


create table artist
(
    id           bigint auto_increment comment '画师ID'
        primary key,
    name         varchar(255)                          not null comment '画师名称/昵称',
    bio          text                                  null comment '画师简介',
    avatar_url   varchar(512)                          null comment '画师头像URL',
    art_style    text                                  null comment '画风描述',
    social_links json                                  null comment '社交媒体链接 (JSON格式，例如: {"pixiv": "...", "weibo": "..."})',
    website_url  varchar(512)                          null comment '个人网站URL',
    status       varchar(50) default 'ACTIVE'          not null comment '状态: ACTIVE, INACTIVE, SUSPENDED',
    created_at   datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at   datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间'
)
    comment '画师信息表';

create index idx_name
    on artist (name);

create table artwork
(
    id          bigint auto_increment comment '艺术作品ID'
        primary key,
    title       varchar(255)                       not null comment '作品标题',
    artist_id   bigint                             not null comment '作者ID，关联artist表',
    description text                               null comment '作品描述',
    image_urls  json                               null comment '作品图片URL列表 (JSON格式)',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间'
)
    comment '艺术作品表';

create index idx_artist_id
    on artwork (artist_id);

create table comment
(
    id          bigint auto_increment comment '评论ID'
        primary key,
    entity_id   bigint                                not null comment '评论的实体ID',
    entity_type varchar(50)                           not null comment '评论的实体类型 (WIKI_ENTRY, GAME, MEDIA_WORK等)',
    user_id     bigint                                not null comment '评论者用户ID，关联user表',
    parent_id   bigint                                null comment '父评论ID，用于实现楼中楼评论',
    content     text                                  not null comment '评论内容',
    status      varchar(50) default 'APPROVED'        not null comment '评论状态: APPROVED, PENDING, SPAM',
    created_at  datetime    default CURRENT_TIMESTAMP not null comment '评论时间',
    updated_at  datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间'
)
    comment '评论表';

create index idx_entity
    on comment (entity_id, entity_type);

create index idx_user_id
    on comment (user_id);

create table creator
(
    id           bigint auto_increment comment '创作者ID'
        primary key,
    name         varchar(255)                          not null comment '创作者名称/昵称',
    bio          text                                  null comment '创作者简介',
    avatar_url   varchar(512)                          null comment '创作者头像URL',
    social_links json                                  null comment '社交媒体链接 (JSON格式，例如: {"twitter": "...", "pixiv": "..."})',
    website_url  varchar(512)                          null comment '个人网站URL',
    status       varchar(50) default 'ACTIVE'          not null comment '状态: ACTIVE, INACTIVE, SUSPENDED',
    created_at   datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at   datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间'
)
    comment '同人创作者表';

create index idx_name
    on creator (name);

create table entity_tag
(
    entity_id   bigint      not null comment '实体ID (wiki_entry_id, creator_id, work_id等)',
    entity_type varchar(50) not null comment '实体类型 (WIKI_ENTRY, CREATOR, WORK, ARTIST等)',
    tag_id      bigint      not null comment '标签ID，关联tag表',
    primary key (entity_id, entity_type, tag_id)
)
    comment '实体-标签关联表';

create index idx_entity_id_type
    on entity_tag (entity_id, entity_type);

create index idx_tag_id
    on entity_tag (tag_id);

create table favorite
(
    user_id     bigint                             not null comment '用户ID，关联user表',
    entity_id   bigint                             not null comment '收藏的实体ID',
    entity_type varchar(50)                        not null comment '收藏的实体类型 (WIKI_ENTRY, GAME, MEDIA_WORK等)',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '收藏时间',
    primary key (user_id, entity_id, entity_type)
)
    comment '收藏表';

create index idx_entity
    on favorite (entity_id, entity_type);

create index idx_user_id
    on favorite (user_id);

create table game
(
    id                   bigint auto_increment comment '游戏ID'
        primary key,
    title                varchar(255)                       not null comment '游戏标题',
    platform             varchar(255)                       null comment '游戏平台 (PC, Mobile, Console等)',
    genre                varchar(255)                       null comment '游戏类型/题材',
    release_date         date                               null comment '发行日期',
    developer_id         bigint                             null comment '开发者ID，关联creator或新的developer表',
    publisher_id         bigint                             null comment '发行商ID，关联creator或新的publisher表',
    description          longtext                           null comment '游戏简介',
    cover_image_url      varchar(512)                       null comment '游戏封面图URL',
    official_website_url varchar(512)                       null comment '官方网站URL',
    created_at           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间'
)
    comment '游戏表';

create index idx_title
    on game (title);

create table literature_work
(
    id                   bigint auto_increment comment '作品ID'
        primary key,
    title                varchar(255)                       not null comment '作品标题',
    type                 varchar(50)                        not null comment '作品类型 (COMIC, NOVEL, WEBTOON等)',
    author_id            bigint                             null comment '作者ID，关联creator表',
    illustrator_id       bigint                             null comment '插画师ID，关联artist表',
    publisher_id         bigint                             null comment '出版社ID，关联creator表',
    release_date         date                               null comment '发行日期',
    description          longtext                           null comment '作品简介',
    cover_image_url      varchar(512)                       null comment '作品封面图URL',
    official_website_url varchar(512)                       null comment '官方网站URL',
    created_at           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间'
)
    comment '漫画小说作品表';

create index idx_title
    on literature_work (title);

create index idx_type
    on literature_work (type);

create table media_work
(
    id                   bigint auto_increment comment '作品ID'
        primary key,
    title                varchar(255)                       not null comment '作品标题',
    type                 varchar(50)                        not null comment '作品类型 (ANIME, MOVIE, TV_SERIES, CARTOON等)',
    release_date         date                               null comment '发行日期/首播日期',
    director_id          bigint                             null comment '导演ID，关联creator表',
    studio_id            bigint                             null comment '制作公司ID，关联creator表',
    description          longtext                           null comment '作品简介',
    cover_image_url      varchar(512)                       null comment '作品封面图URL',
    official_website_url varchar(512)                       null comment '官方网站URL',
    created_at           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间'
)
    comment '影视动漫作品表';

create index idx_title
    on media_work (title);

create index idx_type
    on media_work (type);

create table permission
(
    id          bigint auto_increment comment '权限ID'
        primary key,
    code        varchar(100)                       not null comment '权限代码 (例如: wiki_entry:create, wiki_entry:edit)',
    description text                               null comment '权限描述',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint code
        unique (code)
)
    comment '权限表';

create table report
(
    id          bigint auto_increment comment '举报ID'
        primary key,
    user_id     bigint                                not null comment '举报者用户ID',
    entity_id   bigint                                not null comment '被举报的实体ID',
    entity_type varchar(50)                           not null comment '被举报的实体类型',
    reason      varchar(255)                          not null comment '举报理由',
    description text                                  null comment '详细描述',
    status      varchar(50) default 'PENDING'         not null comment '举报处理状态: PENDING, REVIEWED, RESOLVED',
    handled_by  bigint                                null comment '处理者用户ID',
    handled_at  datetime                              null comment '处理时间',
    created_at  datetime    default CURRENT_TIMESTAMP not null comment '举报时间'
)
    comment '举报表';

create index idx_entity
    on report (entity_id, entity_type);

create table role
(
    id          bigint auto_increment comment '角色ID'
        primary key,
    name        varchar(50)                        not null comment '角色名称 (例如: ADMIN, EDITOR, USER)',
    description text                               null comment '角色描述',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint name
        unique (name)
)
    comment '角色表';

create table role_permission
(
    role_id       bigint not null comment '角色ID，关联role表',
    permission_id bigint not null comment '权限ID，关联permission表',
    primary key (role_id, permission_id)
)
    comment '角色-权限关联表';

create index idx_permission_id
    on role_permission (permission_id);

create index idx_role_id
    on role_permission (role_id);

create table tag
(
    id          bigint auto_increment comment '标签ID'
        primary key,
    name        varchar(100)                       not null comment '标签名称',
    description text                               null comment '标签描述',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint name
        unique (name)
)
    comment '标签表';

create table user
(
    id              bigint auto_increment comment '用户ID'
        primary key,
    username        varchar(50)                           not null comment '用户名',
    password_hash   varchar(255)                          not null comment '密码哈希值',
    nickname        varchar(50)                           null comment '用户昵称',
    avatar_url      varchar(512)                          null comment '用户头像URL',
    email           varchar(255)                          null comment '用户邮箱',
    phone_number    varchar(20)                           null comment '手机号码',
    status          varchar(50) default 'ACTIVE'          not null comment '用户状态: ACTIVE, INACTIVE, LOCKED',
    registration_ip varchar(45)                           null comment '注册IP地址',
    last_login_at   datetime                              null comment '最后登录时间',
    created_at      datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at      datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint email
        unique (email),
    constraint phone_number
        unique (phone_number),
    constraint username
        unique (username)
)
    comment '用户表';

create index idx_email
    on user (email);

create index idx_username
    on user (username);

create table user_role
(
    user_id bigint not null comment '用户ID，关联user表',
    role_id bigint not null comment '角色ID，关联role表',
    primary key (user_id, role_id)
)
    comment '用户-角色关联表';

create index idx_role_id
    on user_role (role_id);

create index idx_user_id
    on user_role (user_id);

create table wiki_category
(
    id          bigint auto_increment comment '分类ID'
        primary key,
    name        varchar(100)                       not null comment '分类名称',
    parent_id   bigint                             null comment '父分类ID，用于实现层级结构',
    description text                               null comment '分类描述',
    sort_order  int      default 0                 not null comment '排序值',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    constraint name
        unique (name)
)
    comment '维基分类表';

create index idx_parent_id
    on wiki_category (parent_id);

create table wiki_entry
(
    id              bigint auto_increment comment '维基条目ID'
        primary key,
    title           varchar(255)                          not null comment '条目标题',
    slug            varchar(255)                          not null comment 'URL友好别名，用于美化URL和去重',
    category_id     bigint                                not null comment '所属分类ID，关联wiki_category表',
    content         longtext                              not null comment '条目内容，支持Markdown或其他富文本格式',
    cover_image_url varchar(512)                          null comment '条目封面图片URL',
    status          varchar(50) default 'DRAFT'           not null comment '条目状态: DRAFT(草稿), PENDING_REVIEW(待审核), PUBLISHED(已发布), ARCHIVED(已归档)',
    view_count      bigint      default 0                 not null comment '浏览次数',
    created_by      bigint                                not null comment '创建者用户ID，关联user表',
    created_at      datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_by      bigint                                null comment '最后更新者用户ID，关联user表',
    updated_at      datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    is_deleted      tinyint(1)  default 0                 not null comment '是否已删除（软删除）',
    constraint slug
        unique (slug)
)
    comment '维基条目表';

create index idx_category_id
    on wiki_entry (category_id);

create index idx_title
    on wiki_entry (title);

create table wiki_entry_version
(
    wiki_entry_id  bigint                             not null comment '关联的wiki_entry ID',
    version_number int                                not null comment '版本号',
    content        longtext                           not null comment '该版本的条目内容',
    created_by     bigint                             not null comment '创建该版本用户ID',
    created_at     datetime default CURRENT_TIMESTAMP not null comment '版本创建时间',
    comment        varchar(512)                       null comment '版本修改说明',
    primary key (wiki_entry_id, version_number)
)
    comment '维基条目版本表';

