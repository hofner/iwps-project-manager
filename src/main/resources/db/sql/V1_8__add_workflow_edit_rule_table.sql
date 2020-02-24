CREATE TABLE workflow_edit_rule
(
  id serial PRIMARY KEY NOT NULL,
  field character varying(255) NOT NULL,
  level character varying(255) NOT NULL,
  project_status character varying(255) NOT NULL,
  permission character varying(255) NOT NULL,
  normalized_permission character varying(255) NOT NULL,
  CONSTRAINT UK_editable_field_entity_level_project_status UNIQUE (field, level, project_status)
);
