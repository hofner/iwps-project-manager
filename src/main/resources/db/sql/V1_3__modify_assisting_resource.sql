ALTER TABLE project ADD COLUMN customer_project_manager_fullname character varying(255) NOT NULL;
ALTER TABLE project ADD COLUMN customer_project_manager_signum character varying(255) NOT NULL;
ALTER TABLE project ADD COLUMN comment character varying(255);

ALTER TABLE project ADD COLUMN service_project_manager_fullname character varying(255);
ALTER TABLE project ADD COLUMN service_project_manager_signum character varying(255);

DROP TABLE assisting_resource_project;
DROP TABLE assisting_resource;

---- ASSISTING CUSTOMER PROJECT MANAGER ----
CREATE TABLE assisting_customer_project_manager
(
  id serial PRIMARY KEY NOT NULL,
  signum character varying(255) NOT NULL,
  fullname character varying(255) NOT NULL,
  CONSTRAINT UK_assisting_customer_project_manager_signum_fullname UNIQUE (signum, fullname),
  created_by character varying(255) NOT NULL DEFAULT 'AUTO',
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  last_modified_by character varying(255),
  last_modified_at TIMESTAMP WITHOUT TIME ZONE
);

---- ASSISTING SERVICE PROJECT MANAGER ----
CREATE TABLE assisting_service_project_manager
(
  id serial PRIMARY KEY NOT NULL,
  signum character varying(255) NOT NULL,
  fullname character varying(255) NOT NULL,
  CONSTRAINT UK_assisting_service_project_manager_signum_fullname UNIQUE (signum, fullname),
  created_by character varying(255) NOT NULL DEFAULT 'AUTO',
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  last_modified_by character varying(255),
  last_modified_at TIMESTAMP WITHOUT TIME ZONE
);

---- ASSISTING CUSTOMER PROJECT MANAGER / PROJECT----
CREATE TABLE assisting_customer_project_manager_project
(
  project_id integer NOT NULL REFERENCES project (id),
  resource_id integer NOT NULL REFERENCES assisting_customer_project_manager (id),
  CONSTRAINT UK_assisting_customer_project_manager_project UNIQUE (project_id, resource_id)
);

---- ASSISTING SERVICE PROJECT MANAGER / PROJECT----
CREATE TABLE assisting_service_project_manager_project
(
  project_id integer NOT NULL REFERENCES project (id),
  resource_id integer NOT NULL REFERENCES assisting_service_project_manager (id),
  CONSTRAINT UK_assisting_service_project_manager_project UNIQUE (project_id, resource_id)
);

---- Project schema change -----
ALTER TABLE project ALTER COLUMN network TYPE bigint;
ALTER TABLE project RENAME network TO standard_network;