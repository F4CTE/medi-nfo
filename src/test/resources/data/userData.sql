INSERT INTO user (
    id,
    username,
    password,
    `role`,
    status,
    firstname,
    lastname,
    specialization,
    date_created,
    last_updated
) VALUES (
    1000,
    'admin',
    '{bcrypt}$2a$10$FMzmOkkfbApEWxS.4XzCKOR7EbbiwzkPEyGgYh6uQiPxurkpzRMa6',
    'ADMIN',
    'ACTIVE',
    'Sed diam voluptua.',
    'Nulla facilisis.',
    'Ut wisi enim.',
    '2023-09-02 16:30:00',
    '2023-09-02 16:30:00'
);

INSERT INTO user (
    id,
    username,
    password,
    `role`,
    status,
    firstname,
    lastname,
    specialization,
    date_created,
    last_updated
) VALUES (
    1001,
    'user',
    '{bcrypt}$2a$10$FMzmOkkfbApEWxS.4XzCKOR7EbbiwzkPEyGgYh6uQiPxurkpzRMa6',
    'ADMIN',
    'ACTIVE',
    'At vero eos.',
    'Et ea rebum.',
    'Nam liber tempor.',
    '2023-09-03 16:30:00',
    '2023-09-03 16:30:00'
);
