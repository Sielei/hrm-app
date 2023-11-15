INSERT INTO password_policies (id, name, number_of_characters, number_of_lowercase_characters, number_of_numeric_characters, number_of_special_characters, number_of_uppercase_characters, password_reset_days) VALUES
('2bfddc8d-4a25-4fc8-a2b7-5ea9caadad23', 'Default Policy', 8, 1, 1, 1, 1, 90);

INSERT INTO users(id,username, change_password_next_login, created_by, email_address, password, status,
                  password_policy) VALUES
    ('cc5ea78d-61a8-4f34-8ac4-a50e92ef8ad3', 'JUN001', 'FALSE', 'cc5ea78d-61a8-4f34-8ac4-a50e92ef8ad3', 'systemadmin@hrm-app.com',
     '$2a$12$/TLPiW4HijPHXOzE4xTIu.I3i6ZqNRKY28QM4SoJV8mqj4mS4ocIi', 'ACTIVE', '2bfddc8d-4a25-4fc8-a2b7-5ea9caadad23'),
    ('47c9842a-d9bf-49be-b887-b07eddd095ab', 'DeactivatedUser', 'FALSE', 'cc5ea78d-61a8-4f34-8ac4-a50e92ef8ad3', 'systemadmin@hrm-app.com',
     '$2a$12$XzyJ0r7YgiaPoFi6ftKAIORm99hwj/Cwr//IU6hJ5Wl.es/La1WlG', 'DEACTIVATED', '2bfddc8d-4a25-4fc8-a2b7-5ea9caadad23'),
('f3fa9972-ed51-444e-98ce-984630c2b693', 'CorrectUser', 'FALSE', 'cc5ea78d-61a8-4f34-8ac4-a50e92ef8ad3', 'cuser@hrm-app.com',
     '$2a$12$y176V4tc5Smrsy9G6QldvuyMnxFdtVCFAD0BkDowWbk22.I.39Ryq', 'ACTIVE', '2bfddc8d-4a25-4fc8-a2b7-5ea9caadad23');

INSERT INTO roles VALUES
('fc3ae74e-927e-42cb-84f8-6d791c1ad870', 'cc5ea78d-61a8-4f34-8ac4-a50e92ef8ad3', 'Performs System administration functions.', 'System Administrator');

INSERT INTO user_roles VALUES
('cc5ea78d-61a8-4f34-8ac4-a50e92ef8ad3', 'fc3ae74e-927e-42cb-84f8-6d791c1ad870');

INSERT INTO permissions(id, name, subject, permission, authority, action) VALUES
('4f0921d1-f9c3-4c20-9cfa-742d2457f633', 'User Management', 'roles', 'Create roles', 'CREATE_ROLES', 'Create'),
('1f30a0d8-d0d7-4e54-ab62-6669151b523a', 'User Management', 'roles', 'View any roles', 'VIEW_ANY_ROLE', 'Read'),
('0506d7e0-3c34-4fbc-96ab-8a42822122d3', 'User Management', 'roles', 'Edit any role', 'EDIT_ANY_ROLE', 'Update'),
('271c3b7e-29d6-4c5b-9096-cb7fedc9b27b', 'User Management', 'roles', 'Delete any role', 'DELETE_ANY_ROLE', 'Delete'),
('cc522ec5-7449-4d6f-a16a-c30aa33879cc', 'User Management', 'roles', 'Full Access to roles', 'ROLE_FULL_ACCESS', 'Full Access'),
('fef918e8-4277-4f5e-bc6b-a146fe96d0fd', 'User Management', 'permissions', 'Create Permissions', 'CREATE_PERMISSION', 'Create'),
('e9bc9447-fde7-4758-9785-fc7ed18cf88c', 'User Management', 'permissions', 'View any permissions', 'VIEW_ANY_PERMISSION', 'Read'),
('5f1881c3-7c17-498a-adf1-1d353d72e5b3', 'User Management', 'permissions', 'Edit any permission', 'EDIT_ANY_PERMISSION', 'Update'),
('304077d2-65bb-428b-8a94-7157988b8d90', 'User Management', 'permissions', 'Delete any permission', 'DELETE_ANY_PERMISSION', 'delete'),
('9b034ee3-12ee-495f-837d-ffce6f70e226', 'User Management', 'permissions', 'Full Access to permissions', 'PERMISSION_FULL_ACCESS', 'Full Access'),
('f924e300-da57-46f1-897b-bb05d278dbac', 'User Management', 'users','Create users', 'CREATE_USERS', 'Create'),
('e0e7e39b-73bb-480d-b360-fa5df1e9ab63', 'User Management', 'users','View any users', 'VIEW_ANY_USERS', 'Read'),
('2743def8-1f75-4ce1-a9ed-1b5b31bb3524', 'User Management', 'users', 'Edit any users', 'EDIT_ANY_USER', 'Update'),
('bd44f5da-120c-47d7-afcc-9d1c381ba8b5', 'User Management', 'users', 'Delete any user', 'DELETE_ANY_USER', 'delete'),
('5882605b-92c2-4db9-bcfb-d7c1daeb9b6a', 'User Management', 'users', 'Full Access to users', 'USER_FULL_ACCESS', 'Full Access');

INSERT INTO role_permissions VALUES
('fc3ae74e-927e-42cb-84f8-6d791c1ad870', '9b034ee3-12ee-495f-837d-ffce6f70e226'),
('fc3ae74e-927e-42cb-84f8-6d791c1ad870', 'cc522ec5-7449-4d6f-a16a-c30aa33879cc'),
('fc3ae74e-927e-42cb-84f8-6d791c1ad870', '5882605b-92c2-4db9-bcfb-d7c1daeb9b6a');