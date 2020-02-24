INSERT INTO status (name) VALUES ('Plan Rework Preparation'), ('Plan Reworked');

-- Add the workflow rules for Change Requests
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES
('Plan Submitted', 'Plan Rework Preparation', 'Rework Plan', 'PROJECT', '/dtra/project/rework', 'PROJECT_REWORK'),
('Plan Rework Preparation', 'Plan Reworked', 'Submit Rework', 'PROJECT', '/dtra/project/submit_rework', 'PROJECT_SUBMIT_REWORK'),
('Plan Reworked', 'Plan Rework Preparation', 'Reject Plan Rework', 'PROJECT', '/dtra/project/reject_rework', 'PROJECT_REJECT_REWORK'),
('Plan Reworked', 'Plan Submitted', 'Submit Plan', 'PROJECT', '/dtra/project/submit_plan', 'PROJECT_SUBMIT_PLAN');