
---- GSC table ----
CREATE TABLE global_service_center
(
  id serial PRIMARY KEY NOT NULL,
  name character varying(255) UNIQUE NOT NULL,
  display_name character varying(255) UNIQUE NOT NULL,
  created_by character varying(255) NOT NULL DEFAULT 'AUTO',
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  last_modified_by character varying(255),
  last_modified_at TIMESTAMP WITHOUT TIME ZONE
);

---- REGION table ----
CREATE TABLE region
(
  id serial PRIMARY KEY NOT NULL,
  name character varying(255) UNIQUE NOT NULL,
  created_by character varying(255) NOT NULL DEFAULT 'AUTO',
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  last_modified_by character varying(255),
  last_modified_at TIMESTAMP WITHOUT TIME ZONE
);

---- COUNTRY table ----
CREATE TABLE country
(
  id serial PRIMARY KEY NOT NULL,
  region_id integer NOT NULL REFERENCES region (id),
  name character varying(255) UNIQUE NOT NULL,
  timezone character varying(255),
  standard_cost_model_ready boolean,
  created_by character varying(255) NOT NULL DEFAULT 'AUTO',
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  last_modified_by character varying(255),
  last_modified_at TIMESTAMP WITHOUT TIME ZONE
);

---- COST MODEL table ----
CREATE TABLE cost_model
(
  id serial PRIMARY KEY NOT NULL,
  name character varying(255) UNIQUE NOT NULL,
  created_by character varying(255) NOT NULL DEFAULT 'AUTO',
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  last_modified_by character varying(255),
  last_modified_at TIMESTAMP WITHOUT TIME ZONE
);

---- PRODUCT AREA table ----
CREATE TABLE product_area
(
  id serial PRIMARY KEY NOT NULL,
  cost_model_id integer NOT NULL REFERENCES cost_model (id),
  name character varying(255) NOT NULL,
  created_by character varying(255) NOT NULL DEFAULT 'AUTO',
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  last_modified_by character varying(255),
  last_modified_at TIMESTAMP WITHOUT TIME ZONE
);

---- BUNDLING table ----
CREATE TABLE bundling
(
  id serial PRIMARY KEY NOT NULL,
  code integer NOT NULL,
  description character varying(255) NOT NULL,
  CONSTRAINT UK_code_description UNIQUE (code, description),
  created_by character varying(255) NOT NULL DEFAULT 'AUTO',
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  last_modified_by character varying(255),
  last_modified_at TIMESTAMP WITHOUT TIME ZONE
);

---- Status table ----
CREATE TABLE status
(
  name character varying(255) PRIMARY KEY NOT NULL
);

---- PROJECT table ----
CREATE TABLE project
(
  id serial PRIMARY KEY NOT NULL,
  external_reference_id  character varying(16),
  global_service_center_id integer REFERENCES global_service_center (id),
  cost_model_id integer NOT NULL REFERENCES cost_model (id),
  region_id integer NOT NULL REFERENCES region (id),
  country_id integer NOT NULL REFERENCES country (id),
  product_area_id integer NOT NULL REFERENCES product_area (id),
  bundling_id integer REFERENCES bundling (id),
  network integer,
  sap_project_id character varying(255),
  name character varying(255) NOT NULL,
  timezone character varying(255) NOT NULL,
  status character varying(255) NOT NULL REFERENCES status (name),
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_at TIMESTAMP WITHOUT TIME ZONE,
  created_by character varying(255) NOT NULL,
  last_modified_by character varying(255),
  start_date TIMESTAMP WITHOUT TIME ZONE,
  due_date TIMESTAMP WITHOUT TIME ZONE,
  customer character varying(255) NOT NULL,
  work_package_completion_rate integer DEFAULT 0,
  work_breakdown_structure_completion_rate integer DEFAULT 0
);

---- WBS table ----
CREATE TABLE work_breakdown_structure
(
  id serial PRIMARY KEY NOT NULL,
  project_id integer NOT NULL REFERENCES project (id),
  name character varying(255) NOT NULL,
  label character varying(255),
  site character varying(255),
  status character varying(255) NOT NULL REFERENCES status (name),
  standard_network integer,
  start_date TIMESTAMP WITHOUT TIME ZONE,
  due_date TIMESTAMP WITHOUT TIME ZONE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_at TIMESTAMP WITHOUT TIME ZONE,
  created_by character varying(255) NOT NULL,
  last_modified_by character varying(255),
  comments character varying(255),
  sequence_id character varying(255)
);

---- WP table ----
CREATE TABLE work_package
(
  id serial PRIMARY KEY NOT NULL,
  industrialized_work_package_code character varying(255),
  work_breakdown_structure_id integer NOT NULL REFERENCES work_breakdown_structure (id),
  name character varying(255) NOT NULL,
  industrialized_work_package_version character varying(255) NOT NULL,
  status character varying(255) NOT NULL REFERENCES status (name),
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  last_modified_at TIMESTAMP WITHOUT TIME ZONE,
  created_by character varying(255) NOT NULL,
  last_modified_by character varying(255),
  start_date TIMESTAMP WITHOUT TIME ZONE,
  due_date TIMESTAMP WITHOUT TIME ZONE,
  comments character varying,
  sequence_id character varying(255)
);

---- PROJECT STATUS EVENT table ----
CREATE TABLE project_status_event
(
  id serial PRIMARY KEY NOT NULL,
  project_id integer NOT NULL REFERENCES project (id),
  old_status character varying(255) NOT NULL,
  new_status character varying(255) NOT NULL,
  triggered_by character varying(255) NOT NULL,
  triggered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  details character varying(255) NOT NULL
);

---- WORK BREAKDOWN STRUCTURE STATUS EVENT table ----
CREATE TABLE work_breakdown_structure_status_event
(
  id serial PRIMARY KEY NOT NULL,
  work_breakdown_structure_id integer NOT NULL REFERENCES work_breakdown_structure (id),
  old_status character varying(255) NOT NULL,
  new_status character varying(255) NOT NULL,
  triggered_by character varying(255) NOT NULL,
  triggered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  details character varying(255) NOT NULL
);

---- WORK PACKAGE STATUS EVENT table ----
CREATE TABLE work_package_status_event
(
  id serial PRIMARY KEY NOT NULL,
  work_package_id integer NOT NULL REFERENCES work_package (id),
  old_status character varying(255) NOT NULL,
  new_status character varying(255) NOT NULL,
  triggered_by character varying(255) NOT NULL,
  triggered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  details character varying(255) NOT NULL
);

---- CAPACITY table ----
CREATE TABLE capacity
(
  id serial PRIMARY KEY NOT NULL,
  project_id integer NOT NULL REFERENCES project (id),
  work_breakdown_structure_id integer NOT NULL REFERENCES work_breakdown_structure (id),
  work_package_id integer NOT NULL REFERENCES work_package (id),
  version character varying(255) NOT NULL,
  job_role character varying(255) NOT NULL,
  job_stage character varying(255) NOT NULL,
  competence character varying(255) NOT NULL,
  duration decimal(10,2) NOT NULL
);

---- ASSISTING RESOURCE ----
CREATE TABLE assisting_resource
(
  id serial PRIMARY KEY NOT NULL,
  signum character varying(255) NOT NULL,
  fullname character varying(255) NOT NULL,
  role character varying(255) NOT NULL,
  CONSTRAINT UK_assisting_resource_signum_fullname_role UNIQUE (signum, fullname, role)
);

---- ASSISTING RESOURCE/PROJECT ----
CREATE TABLE assisting_resource_project
(
  assisting_resource_id integer NOT NULL REFERENCES assisting_resource (id),
  project_id integer NOT NULL REFERENCES project (id),
  project_role character varying(255) NOT NULL,
  CONSTRAINT UK_assisting_resource_project UNIQUE (assisting_resource_id, project_id)
);

---- STATIC DATA ----

-- BUNDLE --
INSERT INTO bundling(code, description) VALUES (0, 'No Bundle at all');
INSERT INTO bundling(code, description) VALUES (1, 'Bundle all activities on a single WP');
INSERT INTO bundling(code, description) VALUES (2, 'Bundle by Job Role and Job Stage');

-- COST MODEL --
INSERT INTO cost_model(name) VALUES ('Actual Cost Model');
INSERT INTO cost_model(name) VALUES ('Managed Services Project');
INSERT INTO cost_model(name) VALUES ('Standard Cost Model');

-- PRODUCT AREA --
INSERT INTO product_area(cost_model_id, name) VALUES (1, 'CSI');
INSERT INTO product_area(cost_model_id, name) VALUES (1, 'NRO');
INSERT INTO product_area(cost_model_id, name) VALUES (1, 'NDO');

INSERT INTO product_area(cost_model_id, name) VALUES (2, 'MS Operate');
INSERT INTO product_area(cost_model_id, name) VALUES (2, 'MS Optimize');

INSERT INTO product_area(cost_model_id, name) VALUES (3, 'CSI');
INSERT INTO product_area(cost_model_id, name) VALUES (3, 'NRO');
INSERT INTO product_area(cost_model_id, name) VALUES (3, 'NDO');

-- GLOBAL SERVICE CENTER --
INSERT INTO global_service_center(name, display_name) VALUES ('MEXICO','GSC Mexico');
INSERT INTO global_service_center(name, display_name) VALUES ('CHINA','GSC China');
INSERT INTO global_service_center(name, display_name) VALUES ('INDIA','GSC India');
INSERT INTO global_service_center(name, display_name) VALUES ('ROMANIA','GSC Romania');

-- REGION --
INSERT INTO region(name) VALUES ('RASO');
INSERT INTO region(name) VALUES ('RWCE');
INSERT INTO region(name) VALUES ('RECA');
INSERT INTO region(name) VALUES ('RINA');
INSERT INTO region(name) VALUES ('RLAM');
INSERT INTO region(name) VALUES ('RMEA');
INSERT INTO region(name) VALUES ('RMED');
INSERT INTO region(name) VALUES ('RNAM');
INSERT INTO region(name) VALUES ('RSSA');
INSERT INTO region(name) VALUES ('RNEA');

-- COUNTRY --
-- THE BELOW STATEMENTS ARE GENERATED WITH db/helper/generate_country_region.py --
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Australia', false, 'UTC+10:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Bangladesh', true, 'UTC+06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Brunei Darussalam', false, 'UTC+08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Cocos (Keeling) Islands', false, 'UTC+06:30');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Cook Islands', false, 'UTC-10:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Christmas Island', false, 'UTC+07:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Fiji', false, 'UTC+12:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Indonesia', false, 'UTC+07:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Cambodia', true, 'UTC+07:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Kiribati', false, 'UTC+12:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Sri Lanka', false, 'UTC+05:30');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Marshall Islands', false, 'UTC+12:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Myanmar', true, 'UTC+06:30');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Northern Mariana Islands', false, 'UTC+10:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Maldives', false, 'UTC+05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Malaysia', false, 'UTC+08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Norfolk Island', false, 'UTC+10:30');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Nauru', false, 'UTC+12:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Niue', false, 'UTC-11:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'French Polynesia', false, 'UTC-10:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Papua New Guinea', true, 'UTC+10:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Philippines', false, 'UTC+08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Pitcairn', false, 'UTC-08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Palau', false, 'UTC+09:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Solomon Islands', false, 'UTC+11:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Singapore', false, 'UTC+08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'French Southern Territories', false, 'UTC+05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Thailand', false, 'UTC+07:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Tokelau', false, 'UTC+13:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Timor-Leste', false, 'UTC+09:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Tonga', false, 'UTC+13:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Tuvalu', false, 'UTC+12:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Wallis and Futuna', false, 'UTC+12:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Viet Nam', false, 'UTC+07:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Samoa', false, 'UTC+13:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Vanuatu', false, 'UTC+11:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'British Indian Ocean Territory', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Côte D’ivoire', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Guernsey', false, 'UTC-01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Isle of Man', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Jersey', false, 'UTC-01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNEA'), 'China', false, 'UTC+08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Armenia', false, 'UTC+04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Aland Islands', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Azerbaijan', false, 'UTC+04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Belarus', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Denmark', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Estonia', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Finland', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Faroe Islands', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Georgia', false, 'UTC+04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Greenland', false, 'UTC-03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Iceland', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Kyrgyzstan', false, 'UTC+06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Kazakhstan', false, 'UTC+06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Lithuania', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Latvia', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Mongolia', false, 'UTC+08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Norway', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Russian Federation', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Sweden', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Svalbard and Jan Mayen', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Tajikistan', false, 'UTC+05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Turkmenistan', false, 'UTC+05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Ukraine', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RECA'), 'Uzbekistan', false, 'UTC+05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RINA'), 'Bhutan', false, 'UTC+06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RINA'), 'India', true, 'UTC+05:30');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RINA'), 'Nepal', false, 'UTC+06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Antigua and Barbuda', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Anguilla', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Antarctica', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Argentina', true, 'UTC-03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Aruba', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Barbados', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Saint Barthelemy', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Bermuda', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Brazil', false, 'UTC-03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Bahamas', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Bouvet Island', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Belize', false, 'UTC-06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Chile', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Colombia', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Costa Rica', false, 'UTC-06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Cuba', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Curacao', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Dominica', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Dominican Republic', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Ecuador', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Falkland Islands (Malvinas)', false, 'UTC-03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Grenada', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'French Guiana', false, 'UTC-03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Guadeloupe', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Guatemala', false, 'UTC-06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Guyana', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Honduras', false, 'UTC-06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Haiti', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Jamaica', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Saint Kitts and Nevis', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Cayman Islands', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Saint Lucia', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Saint Martin', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Martinique', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Montserrat', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Mexico', false, 'UTC-06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Nicaragua', false, 'UTC-06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Panama', false, 'UTC-09:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Peru', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Saint Pierre and Miquelon', false, 'UTC-03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Puerto Rico', true, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Paraguay', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Suriname', true, 'UTC-03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'El Salvador', false, 'UTC-06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Sint Maarten (Dutch part)', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Turks and Caicos Islands', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Trinidad and Tobago', true, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Virgin Islands (British)', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Virgin Islands, (U.S.)', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'United Arab Emirates', false, 'UTC+04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Afghanistan', true, 'UTC+04:30');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Bahrain', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Djibouti', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Egypt', true, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Eritrea', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Ethiopia', true, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Iraq', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Iran (Islamic Republic of)', false, 'UTC+03:30');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Jordan', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Kuwait', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Lebanon', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Oman', false, 'UTC+04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Pakistan', false, 'UTC+05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Qatar', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Saudi Arabia', true, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Sudan', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Somalia', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'South Sudan', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Syrian Arab Republic', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Turkey', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Yemen', true, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Andorra', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Albania', true, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Bulgaria', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Cyprus', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Algeria', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Western Sahara', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Spain', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'France', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Gibraltar', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Greece', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Israel', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Italy', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Libyan Arab Jamahiriya', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Morocco', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Monaco', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Moldova, Republic of', true, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Montenegro', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Macedonia', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Mauritania', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Malta', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'New Caledonia', false, 'UTC+11:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Portugal', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Romania', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Serbia', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'San Marino', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Tunisia', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Holy See (Vatican City State)', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Kosovo', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNAM'), 'American Samoa', false, 'UTC-11:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNAM'), 'Canada', false, 'UTC-05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNAM'), 'Guam', false, 'UTC+10:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Angola', true, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Burkina Faso', true, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Burundi', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Benin', true, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Botswana', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Central African Republic', true, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Ivory Coast', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Cameroon', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Cape Verde', false, 'UTC-01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Gabon', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Ghana', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Gambia', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Equatorial Guinea', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Guinea-Bissau', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Kenya', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Comoros', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Liberia', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Lesotho', false, 'UTC-02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Madagascar', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Mali', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Mauritius', false, 'UTC+04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Malawi', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Mozambique', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Namibia', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Niger', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Nigeria', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Reunion', false, 'UTC+04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Rwanda', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Sierra Leone', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Senegal', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Sao Tome and Principe', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Swaziland', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Chad', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Togo', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Tanzania, United Republic of', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Uganda', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Mayotte', false, 'UTC+03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'South Africa', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Zambia', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Zimbabwe', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Austria', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Bosnia and Herzegovina', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Belgium', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Switzerland', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Czech Republic', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'United Kingdom', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Croatia', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Hungary', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Ireland', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Liechtenstein', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Luxembourg', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Netherlands', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Poland', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNEA'), 'Japan', false, 'UTC+09:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Uruguay', false, 'UTC-03:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Seychelles', false, 'UTC+04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Slovenia', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Slovakia', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RWCE'), 'Germany', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNAM'), 'United States', false, 'UTC-06:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Lao Peoples Democratic Republic', false, 'UTC+07:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'New Zealand', false, 'UTC+12:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Bolivia', true, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Venezuela', true, 'UTC-04:30');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Guinea-Conakry', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Micronesia (Federated States of)', false, 'UTC+11:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RASO'), 'Heard Island and McDonald Islands', false, 'UTC+05:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Bonaire, Saint Eustatius and Saba', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RLAM'), 'Saint Vincent and the Grenadines', false, 'UTC-04:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMEA'), 'Palestinian Territory, Occupied', false, 'UTC+02:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RMED'), 'Ceuta, Melilla', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNAM'), 'United States Minor Outlying Islands', false, 'UTC-12:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Congo, The Democratic Republic of the', false, 'UTC+01:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNEA'), 'Korea, Republic of', false, 'UTC+09:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNEA'), 'Taiwan', true, 'UTC+08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNEA'), 'Macau', false, 'UTC+08:00');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RSSA'), 'Saint Helena, Ascension and Tristan', false, 'UTC');
INSERT INTO country(region_id, name, standard_cost_model_ready, timezone) VALUES ((SELECT id FROM region WHERE name = 'RNEA'), 'Hong Kong', false, 'UTC+08:00');
