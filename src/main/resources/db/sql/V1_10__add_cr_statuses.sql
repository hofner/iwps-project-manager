INSERT INTO status (name) VALUES ('CR Preparation'), ('CR Submitted');

-- Add the workflow rules for Change Requests
INSERT INTO workflow_action_rule(current_status, next_status, action, level, permission, normalized_permission) VALUES
('Fulfill WP Order', 'CR Preparation', 'Create CR', 'PROJECT', '/dtra/project/create_cr', 'PROJECT_CREATE_CR'),
('CR Preparation', 'CR Submitted', 'Submit CR', 'PROJECT', '/dtra/project/submit_cr', 'PROJECT_SUBMIT_CR'),
('CR Submitted', 'CR Preparation', 'Reject CR', 'PROJECT', '/dtra/project/reject_cr', 'PROJECT_REJECT_CR'),
('CR Submitted', 'Fulfill WP Order', 'Approve CR', 'PROJECT', '/dtra/project/approve_cr', 'PROJECT_APPROVE_CR'),
('Plan Accepted', 'CR Preparation', 'Create CR', 'PROJECT', '/dtra/project/create_cr', 'PROJECT_CREATE_CR'),
('Delivery Accepted', 'CR Preparation', 'Create CR', 'PROJECT', '/dtra/project/create_cr', 'PROJECT_CREATE_CR');
