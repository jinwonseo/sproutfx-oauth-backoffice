INSERT INTO `clients` (`id`, `type`, `code`, `secret`, `name`, `access_token_secret`, `access_token_validity_in_seconds`, `status`, `description`, `deleted`, `created_by`, `created_on`, `last_modified_by`, `last_modified_on`, `project_id`) VALUES ('000b1e76-caab-4854-ac1f-ba0f40df64c1', 'AUTHORIZATION_SERVER', 'b605d2a8058542cd888dc547f56768bc', 'SnhtODRwQTRWZmszQ2c4bWxtMDI0SThRcEFmVEdibmE=', 'sproutfx-oauth-authorization', 'EqN3bj4EwLkOLmEb2wNWD73MeRCN0eCKsFxGQDWCyrB70OOngLbP3widMJWZtseHHSsUXqdCAJowTFg1V5yUOVykFwLiU9xP', 7200, 'ACTIVE', NULL, 0, NULL, '2022-03-29 11:19:58', NULL, '2022-03-29 11:09:31', '000ad2e4-a785-4e1a-b082-60e71381c64a');
INSERT INTO `clients` (`id`, `type`, `code`, `secret`, `name`, `access_token_secret`, `access_token_validity_in_seconds`, `status`, `description`, `deleted`, `created_by`, `created_on`, `last_modified_by`, `last_modified_on`, `project_id`) VALUES ('000cd991-74fc-49a9-981e-77d89f915633', 'RESOURCE_SERVER', '5e0f947f90e74061afb4f521d96b5170', 'U3F5VjlJUWpQaDN6TE40MEcwaXUxWlRuQWFwY29BMkk=', 'sproutfx-oauth-backoffice', 'sOLpFJKe58kMKDtx4gutvj4444fqoMWmngouZQLFEy4qaKSMQRToRBUcKpG7mkRupVWOQJtopjW29QRGDtnKopvSIewlAVOW', 7200, 'ACTIVE', NULL, 0, NULL, '2022-03-29 11:19:58', NULL, '2022-03-29 11:09:32', '000ad2e4-a785-4e1a-b082-60e71381c64a');

-- Template
INSERT INTO `clients` (`id`, `type`, `created_by`, `created_on`, `deleted`, `last_modified_by`, `last_modified_on`, `access_token_secret`, `access_token_validity_in_seconds`, `code`, `description`, `name`, `secret`, `status`, `project_id`) VALUES ('ff69f1ac-4582-4cff-a45b-b3191518dafc', 'RESOURCE_SERVER', '0000f14c-e1d9-4908-8c0e-8abba741dd97', '2022-05-27 17:40:05', 0, '0000f14c-e1d9-4908-8c0e-8abba741dd97', '2022-05-27 08:34:49', 'yqwnP48gv7Xc7nm1oB5lYafgFpHGEDsfLRnptyPQa3pD1eMYPwmSBh5W3ZCL9I5WPDQFQktJaNj1tgxLZ0KOkDUcUBJGovki', 7200, '007d996aefca44e3b47062301c0bbd7e', NULL, 'sproutfx-template-backend-mybatis', 'cDJEMFh6MFhscWQzNU1vVVFqOVgxZ0phOHJ1NktyQng=', 'ACTIVE', 'ffe8f4ff-a00d-4863-8a7b-52c7b1c4d1c4');
INSERT INTO `clients` (`id`, `type`, `created_by`, `created_on`, `deleted`, `last_modified_by`, `last_modified_on`, `access_token_secret`, `access_token_validity_in_seconds`, `code`, `description`, `name`, `secret`, `status`, `project_id`) VALUES ('ffc69d97-2b29-40aa-87ee-ad6526732555', 'RESOURCE_SERVER', '0000f14c-e1d9-4908-8c0e-8abba741dd97', '2022-05-27 17:40:02', 0, '0000f14c-e1d9-4908-8c0e-8abba741dd97', '2022-05-27 08:38:31', 'oJdujoLtMqTim3sIAdqxpvNbqIZNBbdcApKxnXrg1Tx1iHOJJ2sU3k5dQUhwEyRQaLEh5b8iqbHt6hgkBdllARbp62394aTv', 7200, 'c7f67ff78f1a4e4983d6f3243771f840', NULL, 'sproutfx-template-backend-jpa', 'MU9HWmVPa0hVODVNdjBpZk9uN1BiYkk1bWw3Y1lLYWI=', 'ACTIVE', 'ffe8f4ff-a00d-4863-8a7b-52c7b1c4d1c4');