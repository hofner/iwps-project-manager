-- Add reason to status change events

ALTER TABLE project_status_event ADD COLUMN reason character varying(255);
ALTER TABLE work_breakdown_structure_status_event ADD COLUMN reason character varying(255);
ALTER TABLE work_package_status_event ADD COLUMN reason character varying(255);