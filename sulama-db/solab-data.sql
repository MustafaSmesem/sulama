-- Roles --
INSERT INTO app_role (id, role_name, description) VALUES
  (1, 'STANDARD_USER', 'Standard User - Has no admin rights'),
  (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks');

-- Predefined users --
INSERT INTO app_user (id, name, surname, password, username, enabled, admin)
VALUES (1, 'sulama', 'root', '$2a$10$Anofm1F4h4datfau.C9nye6tWBUOtR6HLl7xItHjDrrQS6waen6Oy', 'admin', 't', 't');

-- User Roles --
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);

-- System Settings --
INSERT INTO systemSettings (TAG, INFO, VALUE) VALUES
  ('sulama_service_url', 'Sulama service application address.', 'http://localhost:6622/sulama');
