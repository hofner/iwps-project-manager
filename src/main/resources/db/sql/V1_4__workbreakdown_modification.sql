
---- work breakdown structure modification ----
ALTER TABLE work_breakdown_structure ALTER COLUMN standard_network TYPE bigint;
ALTER TABLE work_breakdown_structure RENAME standard_network TO sub_network;

ALTER TABLE work_breakdown_structure ADD COLUMN technical_input character varying(255);
ALTER TABLE work_breakdown_structure ADD COLUMN technical_output character varying(255);
ALTER TABLE work_breakdown_structure ADD COLUMN purchase_order character varying(80);
ALTER TABLE work_breakdown_structure ADD COLUMN service_order character varying(80);
ALTER TABLE work_breakdown_structure ADD COLUMN operational_activity character varying(80);


---- work package modification ----
ALTER TABLE work_package ADD COLUMN technical_input character varying(255);
ALTER TABLE work_package ADD COLUMN technical_output character varying(255);
ALTER TABLE work_package ADD COLUMN purchase_order character varying(80);
ALTER TABLE work_package ADD COLUMN service_order character varying(80);
ALTER TABLE work_package ADD COLUMN operational_activity character varying(80);
ALTER TABLE work_package ADD COLUMN network_activity character varying(80);
