CREATE TABLE `users` (
  `email` VARCHAR(50) PRIMARY KEY,
  `password` VARCHAR(20) NOT NULL
);

CREATE TABLE `posts` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `title` TEXT NOT NULL,
  `content` LONGTEXT NOT NULL,
  `author` VARCHAR(50) NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `comments` (
  `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `author` VARCHAR(50) NOT NULL,
  `post_id` INTEGER NOT NULL,
  `created_at` timestamp
);

CREATE TABLE `heart` (
	`id` BIGINT(20),
    `user_id` VARCHAR(50)
);

ALTER TABLE `posts` ADD FOREIGN KEY (`author`) REFERENCES `users` (`email`);

ALTER TABLE `comments` ADD FOREIGN KEY (`author`) REFERENCES `users` (`email`);

ALTER TABLE `comments` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);

ALTER TABLE `heart` ADD FOREIGN KEY(`user_id`) REFERENCES `users`(`email`);