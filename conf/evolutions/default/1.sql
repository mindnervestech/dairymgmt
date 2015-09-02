# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cattle_feed_master (
  feed_id                   integer auto_increment not null,
  last_update_date_time     datetime,
  feed_type                 varchar(255),
  feedprotine               varchar(255),
  feed_water_content        varchar(255),
  feed_fiber                varchar(255),
  feed_vitamins             varchar(255),
  attrib1                   varchar(255),
  attrib2                   varchar(255),
  attrib3                   varchar(255),
  attrib4                   varchar(255),
  attrib5                   varchar(255),
  oraganization_org_id      integer,
  users_user_id             varchar(255),
  constraint pk_cattle_feed_master primary key (feed_id))
;

create table cattle_health (
  cattle_id                 integer auto_increment not null,
  last_update_date_time     datetime,
  medication_type           varchar(255),
  medication_name           varchar(255),
  medication_start_date     datetime,
  medication_enddate        datetime,
  pregnant                  tinyint(1) default 0,
  pregnancy_date            datetime,
  last_delivaerydate        datetime,
  duedate                   datetime,
  attrib1                   varchar(255),
  attrib2                   varchar(255),
  attrib3                   varchar(255),
  attrib4                   varchar(255),
  attrib5                   varchar(255),
  oraganization_org_id      integer,
  users_user_id             varchar(255),
  constraint pk_cattle_health primary key (cattle_id))
;

create table cattle_intake (
  cattle_id                 integer auto_increment not null,
  last_update_date_time     datetime,
  date                      datetime,
  quantity                  integer,
  device_id                 varchar(255),
  oraganization_org_id      integer,
  users_user_id             varchar(255),
  cattle_feed_master_feed_id integer,
  constraint pk_cattle_intake primary key (cattle_id))
;

create table cattle_master (
  cattle_id                 integer auto_increment not null,
  rfid                      integer,
  last_update_date_time     datetime,
  name                      varchar(255),
  breed                     varchar(255),
  dateof_birth              datetime,
  gender                    varchar(255),
  cattle_identification_mark varchar(255),
  attrib1                   varchar(255),
  attrib2                   varchar(255),
  attrib3                   varchar(255),
  attrib4                   varchar(255),
  attrib5                   varchar(255),
  oraganization_org_id      integer,
  users_user_id             varchar(255),
  constraint pk_cattle_master primary key (cattle_id))
;

create table cattle_output (
  cattle_id                 integer auto_increment not null,
  last_update_date_time     datetime,
  date                      datetime,
  quantity                  integer,
  fat_content               integer,
  snfcontent                integer,
  device_id                 varchar(255),
  attrib1                   varchar(255),
  attrib2                   varchar(255),
  attrib3                   varchar(255),
  attrib4                   varchar(255),
  attrib5                   varchar(255),
  oraganization_org_id      integer,
  users_user_id             varchar(255),
  constraint pk_cattle_output primary key (cattle_id))
;

create table entities (
  id                        integer auto_increment not null,
  entity_name               varchar(255),
  constraint pk_entities primary key (id))
;

create table oraganization (
  org_id                    integer auto_increment not null,
  name                      varchar(255),
  address_line1             varchar(255),
  address_line2             varchar(255),
  last_updatedtime          datetime,
  city                      varchar(255),
  state                     varchar(255),
  pincode                   integer,
  constraint pk_oraganization primary key (org_id))
;

create table permissions (
  id                        integer auto_increment not null,
  permission_name           varchar(255),
  access                    varchar(255),
  users_user_id             varchar(255),
  constraint pk_permissions primary key (id))
;

create table users (
  user_id                   varchar(255) not null,
  password                  varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  access_add                tinyint(1) default 0,
  access_write              tinyint(1) default 0,
  lastlogin                 datetime,
  last_logout               datetime,
  last_updatedatetime       datetime,
  oraganization_org_id      integer,
  constraint pk_users primary key (user_id))
;

alter table cattle_feed_master add constraint fk_cattle_feed_master_oraganization_1 foreign key (oraganization_org_id) references oraganization (org_id) on delete restrict on update restrict;
create index ix_cattle_feed_master_oraganization_1 on cattle_feed_master (oraganization_org_id);
alter table cattle_feed_master add constraint fk_cattle_feed_master_users_2 foreign key (users_user_id) references users (user_id) on delete restrict on update restrict;
create index ix_cattle_feed_master_users_2 on cattle_feed_master (users_user_id);
alter table cattle_health add constraint fk_cattle_health_oraganization_3 foreign key (oraganization_org_id) references oraganization (org_id) on delete restrict on update restrict;
create index ix_cattle_health_oraganization_3 on cattle_health (oraganization_org_id);
alter table cattle_health add constraint fk_cattle_health_users_4 foreign key (users_user_id) references users (user_id) on delete restrict on update restrict;
create index ix_cattle_health_users_4 on cattle_health (users_user_id);
alter table cattle_intake add constraint fk_cattle_intake_oraganization_5 foreign key (oraganization_org_id) references oraganization (org_id) on delete restrict on update restrict;
create index ix_cattle_intake_oraganization_5 on cattle_intake (oraganization_org_id);
alter table cattle_intake add constraint fk_cattle_intake_users_6 foreign key (users_user_id) references users (user_id) on delete restrict on update restrict;
create index ix_cattle_intake_users_6 on cattle_intake (users_user_id);
alter table cattle_intake add constraint fk_cattle_intake_CattleFeedMaster_7 foreign key (cattle_feed_master_feed_id) references cattle_feed_master (feed_id) on delete restrict on update restrict;
create index ix_cattle_intake_CattleFeedMaster_7 on cattle_intake (cattle_feed_master_feed_id);
alter table cattle_master add constraint fk_cattle_master_oraganization_8 foreign key (oraganization_org_id) references oraganization (org_id) on delete restrict on update restrict;
create index ix_cattle_master_oraganization_8 on cattle_master (oraganization_org_id);
alter table cattle_master add constraint fk_cattle_master_users_9 foreign key (users_user_id) references users (user_id) on delete restrict on update restrict;
create index ix_cattle_master_users_9 on cattle_master (users_user_id);
alter table cattle_output add constraint fk_cattle_output_oraganization_10 foreign key (oraganization_org_id) references oraganization (org_id) on delete restrict on update restrict;
create index ix_cattle_output_oraganization_10 on cattle_output (oraganization_org_id);
alter table cattle_output add constraint fk_cattle_output_users_11 foreign key (users_user_id) references users (user_id) on delete restrict on update restrict;
create index ix_cattle_output_users_11 on cattle_output (users_user_id);
alter table permissions add constraint fk_permissions_users_12 foreign key (users_user_id) references users (user_id) on delete restrict on update restrict;
create index ix_permissions_users_12 on permissions (users_user_id);
alter table users add constraint fk_users_oraganization_13 foreign key (oraganization_org_id) references oraganization (org_id) on delete restrict on update restrict;
create index ix_users_oraganization_13 on users (oraganization_org_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table cattle_feed_master;

drop table cattle_health;

drop table cattle_intake;

drop table cattle_master;

drop table cattle_output;

drop table entities;

drop table oraganization;

drop table permissions;

drop table users;

SET FOREIGN_KEY_CHECKS=1;

