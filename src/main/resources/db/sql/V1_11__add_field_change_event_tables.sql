
---- PROJECT FIELD CHANGE EVENT table ----
CREATE TABLE project_field_change_event
(
  id serial PRIMARY KEY NOT NULL,
  project_id integer NOT NULL REFERENCES project (id),
  old_value character varying(255) NOT NULL,
  new_value character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  triggered_by character varying(255) NOT NULL,
  triggered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  details character varying(255) NOT NULL
);

---- WORK BREAKDOWN STRUCTURE FIELD CHANGE EVENT table ----
CREATE TABLE work_breakdown_structure_field_change_event
(
  id serial PRIMARY KEY NOT NULL,
  work_breakdown_structure_id integer NOT NULL REFERENCES work_breakdown_structure (id),
  old_value character varying(255) NOT NULL,
  new_value character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  triggered_by character varying(255) NOT NULL,
  triggered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  details character varying(255) NOT NULL
);

---- WORK PACKAGE FIELD CHANGE EVENT table ----
CREATE TABLE work_package_field_change_event
(
  id serial PRIMARY KEY NOT NULL,
  work_package_id integer NOT NULL REFERENCES work_package (id),
  old_value character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  new_value character varying(255) NOT NULL,
  triggered_by character varying(255) NOT NULL,
  triggered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  details character varying(255) NOT NULL
);
