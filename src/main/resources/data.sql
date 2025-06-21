INSERT INTO `tb_permission` (`description`) VALUES
                                                     ('ADMIN'),
                                                     ('MANAGER'),
                                                     ('COMMON_USER');


INSERT INTO `tb_users` (`user_name`, `full_name`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`,`active`) VALUES
                                                                                                                                                                                   ('leandro', 'Leandro Costa', '1e3cdeeaaaeeda173ff6d002e7cb5e3f91ebc354dcff52156c9eaba1793a3a5e5bee306c11099e22', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('flavio', 'Flavio Costa', '362ad02420268beeb22d3a1f0d92749df461d7f4b74c9433d7415bdeef1b2902f4eb1edaecb37cb3', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('alice', 'Alice Martins', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1'), -- senha: password
                                                                                                                                                                                   ('bruno', 'Bruno Silva', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('carla', 'Carla Souza', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('daniel', 'Daniel Lima', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('elisa', 'Elisa Rocha', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('felipe', 'Felipe Torres', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('giovana', 'Giovana Alves', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('heitor', 'Heitor Mendes', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1'),
                                                                                                                                                                                   ('isabela', 'Isabela Pires', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', b'1', b'1', b'1', b'1',b'1');


INSERT INTO `tb_user_permission` (`id_user`, `id_permission`) VALUES
                                                                                       (1, 1),
                                                                                       (2, 1),
                                                                                       (1, 2),
                                                                                       (3, 1), -- alice: ADMIN
                                                                                       (4, 2), -- bruno: MANAGER
                                                                                       (5, 3), -- carla: COMMON_USER
                                                                                       (6, 2), -- daniel: MANAGER
                                                                                       (7, 3), -- elisa: COMMON_USER
                                                                                       (8, 3), -- felipe: COMMON_USER
                                                                                       (9, 2), -- giovana: MANAGER
                                                                                       (10, 3), -- heitor: COMMON_USER
                                                                                       (11, 3); -- isabela: COMMON_USER
