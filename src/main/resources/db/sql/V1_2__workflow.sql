---- workflow_action_rule table ----
CREATE TABLE workflow_action_rule
(
  id serial PRIMARY KEY NOT NULL,
  current_status character varying(255) NOT NULL REFERENCES status (name),
  next_status character varying(255) NOT NULL REFERENCES status (name),
  action character varying(255) NOT NULL,
  level character varying(255) NOT NULL,
  permission character varying(255) NOT NULL,
  normalized_permission character varying(255) NOT NULL,
  CONSTRAINT UK_current_status_next_status_level UNIQUE (current_status, next_status, level, permission, normalized_permission)
);

---- Status static data ----

INSERT INTO status(name) VALUES ('Preparation');
INSERT INTO status(name) VALUES ('Demand Submitted');
INSERT INTO status(name) VALUES ('Planned');
INSERT INTO status(name) VALUES ('Plan Submitted');
INSERT INTO status(name) VALUES ('Plan Accepted');
INSERT INTO status(name) VALUES ('Fulfill WP Order');
INSERT INTO status(name) VALUES ('Delivery Accepted');
INSERT INTO status(name) VALUES ('Initialized');
INSERT INTO status(name) VALUES ('Ordered');
INSERT INTO status(name) VALUES ('Completed');
INSERT INTO status(name) VALUES ('Delivered');
INSERT INTO status(name) VALUES ('Fulfill WP');
INSERT INTO status(name) VALUES ('Scheduled');
INSERT INTO status(name) VALUES ('Dispatched');
INSERT INTO status(name) VALUES ('In Progress');
INSERT INTO status(name) VALUES ('Cancelled');
INSERT INTO status(name) VALUES ('Closed');


-- workflow_action_rule Actions Static data
-- Note: The action AUTOMATIC is a label for automatic actions, they will be filtered out for front-end usage
---- Project Level ----

INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Preparation', 'Demand Submitted', 'Allocate', 'PROJECT', '/dtra/project/allocate', 'PROJECT_ALLOCATE');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Preparation', 'Cancelled', 'Cancel Project', 'PROJECT', '/dtra/project/cancel', 'PROJECT_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Demand Submitted', 'Planned', 'Assign SPM', 'PROJECT', '/dtra/project/assign', 'PROJECT_ASSIGN');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Demand Submitted', 'Preparation', 'Reject Project', 'PROJECT', '/dtra/project/reject_to_region', 'PROJECT_REJECT_TO_REGION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Planned', 'Plan Submitted', 'Submit Plan', 'PROJECT', '/dtra/project/submit_plan', 'PROJECT_SUBMIT_PLAN');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Planned', 'Demand Submitted', 'Reject Project', 'PROJECT', '/dtra/project/reject', 'PROJECT_REJECT');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Plan Submitted', 'Plan Accepted', 'Accept Plan', 'PROJECT', '/dtra/project/accept_plan', 'PROJECT_ACCEPT_PLAN');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Plan Submitted', 'Cancelled', 'Cancel Project', 'PROJECT', '/dtra/project/cancel', 'PROJECT_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Plan Accepted', 'Fulfill WP Order', 'AUTOMATIC', 'PROJECT', '/dtra/project/wp/fulfill', 'WP_FULFILL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Plan Accepted', 'Cancelled', 'Cancel Project', 'PROJECT', '/dtra/project/cancel', 'PROJECT_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Fulfill WP Order', 'Delivery Accepted', 'AUTOMATIC', 'PROJECT', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivery Accepted', 'Fulfill WP Order', 'AUTOMATIC', 'PROJECT', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivery Accepted', 'Closed', 'Close Project', 'PROJECT', '/dtra/project/close', 'PROJECT_CLOSE');

---- WBS Level ----

INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Initialized', 'Cancelled', 'Cancel WBS', 'WBS', '/dtra/project/wbs/cancel', 'WBS_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Initialized', 'Ordered', 'AUTOMATIC', 'WBS', '/dtra/project/wp/order', 'WP_ORDER');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Ordered', 'Completed', 'AUTOMATIC', 'WBS', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Ordered', 'Cancelled', 'Cancel WBS', 'WBS', '/dtra/project/wbs/cancel', 'WBS_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Completed', 'Ordered', 'AUTOMATIC', 'WBS', '/dtra/project/wp/rework', 'WP_REWORK');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Completed', 'Initialized', 'AUTOMATIC', 'WBS', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Completed', 'Delivered', 'AUTOMATIC', 'WBS', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivered', 'Delivery Accepted', 'AUTOMATIC', 'WBS', '/dtra/project/wp/accept_delivery', 'WP_ACCEPT_DELIVERY');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivered', 'Initialized', 'AUTOMATIC', 'WBS', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivered', 'Ordered', 'AUTOMATIC', 'WBS', '/dtra/project/wp/reject_delivery', 'WP_REJECT_DELIVERY');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivery Accepted', 'Initialized', 'AUTOMATIC', 'WBS', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivery Accepted', 'Closed', 'Close WBS', 'WBS', '/dtra/project/wbs/close', 'WBS_CLOSE');

---- WP Level ----

INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Initialized', 'Cancelled', 'Cancel WP', 'WP', '/dtra/project/wp/cancel', 'WP_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Initialized', 'Ordered', 'Order WP', 'WP', '/dtra/project/wp/order', 'WP_ORDER');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Ordered', 'Fulfill WP', 'Fulfill WP', 'WP', '/dtra/project/wp/fulfill', 'WP_FULFILL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Fulfill WP', 'Scheduled', 'AUTOMATIC', 'WP', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Fulfill WP', 'Cancelled', 'Cancel WP', 'WP', '/dtra/project/wp/cancel', 'WP_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Scheduled', 'Dispatched', 'AUTOMATIC', 'WP', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Scheduled', 'Cancelled', 'Cancel WP', 'WP', '/dtra/project/wp/cancel', 'WP_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Dispatched', 'In Progress', 'AUTOMATIC', 'WP', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Dispatched', 'Cancelled', 'Cancel WP', 'WP', '/dtra/project/wp/cancel', 'WP_CANCEL');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('In Progress', 'Completed', 'AUTOMATIC', 'WP', '/dtra/project/automatic_action', 'PROJECT_AUTOMATIC_ACTION');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Completed', 'Ordered', 'Rework WP', 'WP', '/dtra/project/wp/rework', 'WP_REWORK');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Completed', 'Delivered', 'Deliver WP', 'WP', '/dtra/project/wp/deliver', 'WP_DELIVER');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivered', 'Delivery Accepted', 'Accept Delivery', 'WP', '/dtra/project/wp/accept_delivery', 'WP_ACCEPT_DELIVERY');
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES ('Delivered', 'Ordered', 'Reject Delivery', 'WP', '/dtra/project/wp/reject_delivery', 'WP_REJECT_DELIVERY');