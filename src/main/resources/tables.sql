CREATE EXTENSION pgcrypto;

CREATE TABLE users
(
    id         UUID         DEFAULT gen_random_uuid() PRIMARY KEY,
    name       VARCHAR(255)        NOT NULL,
    surname    VARCHAR(255) UNIQUE NOT NULL,
    birth_date TIMESTAMP(0) DEFAULT now()
);

CREATE TABLE posts
(
    id               UUID         DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id          UUID NOT NULL,
    text             TEXT NOT NULL,
    date_of_creation TIMESTAMP(0) DEFAULT now(),

    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE friendships
(
    user_id1 UUID NOT NULL,
    user_id2 UUID NOT NULL,
    date     TIMESTAMP(0) DEFAULT now(),

    PRIMARY KEY (user_id1, user_id2),
    FOREIGN KEY (user_id1) REFERENCES users (id),
    FOREIGN KEY (user_id2) REFERENCES users (id)
);

CREATE TABLE likes
(
    post_id UUID NOT NULL,
    user_id UUID NOT NULL,
    date    TIMESTAMP(0) DEFAULT now(),

    PRIMARY KEY (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Program should print out all names (only distinct) of users who has more when 100 friends and 100 likes in March 2025
select u.name
from users as u
         join friendships as f
              on (u.id = f.user_id1 or u.id = f.user_id2)
where u.id IN
      (select p.user_id
       from posts as p
                join likes as l
                     on (p.id = l.post_id)
       where l.date >= '2025-03-01'
         and l.date < date '2025-03-01' + integer '31'
       group by p.user_id
       having count(*) > 100)
group by u.name
having count(*) > 100