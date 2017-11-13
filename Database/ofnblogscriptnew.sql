DROP DATABASE IF EXISTS ofnblognew;
CREATE DATABASE ofnblognew;
USE ofnblognew;

CREATE TABLE users (
	UserID INT AUTO_INCREMENT NOT NULL,
    UserName VARCHAR(255) NOT NULL UNIQUE,
    UserPass VARCHAR(500) NOT NULL,
    Avatar VARCHAR(500) NULL,
    UserProfile TEXT NULL,
    Enabled TINYINT(1) DEFAULT 1,
    KEY (UserName),
	PRIMARY KEY(UserID)
);

CREATE TABLE authorities (
	UserName VARCHAR(255) NOT NULL,
    Authority VARCHAR(255) NOT NULL,
    KEY (UserName),    
    FOREIGN KEY (UserName) REFERENCES users(UserName)
);
    
CREATE TABLE tags (
    TagText VARCHAR(255) NOT NULL,
    PRIMARY KEY (TagText)    
);

CREATE TABLE categories (
	CategoryID INT AUTO_INCREMENT NOT NULL,
    CategoryName VARCHAR(255) NOT NULL,
    Description TEXT NULL,
    PRIMARY KEY (CategoryID)
);

CREATE TABLE blogposts (
	BlogPostID INT AUTO_INCREMENT NOT NULL,
    UserID INT NOT NULL,
    PostTime DATETIME NOT NULL,
    Title VARCHAR(255) NOT NULL,
    CategoryID INT NOT NULL,
    Body TEXT NOT NULL,
    StartDate DATETIME,
    EndDate DATETIME,
    Published BOOLEAN,
    PRIMARY KEY(BlogPostID),
    FOREIGN KEY (UserID) REFERENCES users(UserID),
    FOREIGN KEY (CategoryID) REFERENCES categories(CategoryID)
);

CREATE TABLE comments (
	CommentID INT AUTO_INCREMENT NOT NULL,
    BlogPostID INT NOT NULL,
    UserID INT NOT NULL,
    Body TEXT NOT NULL,
    CommentTime DATETIME NOT NULL,
    Published BOOLEAN,
    PRIMARY KEY (CommentID),
    FOREIGN KEY (BlogPostID) REFERENCES blogposts(BlogPostID),
    FOREIGN KEY (UserID) REFERENCES users(UserID)    
);

CREATE TABLE staticpages (
	PageID INT AUTO_INCREMENT NOT NULL,
    UserID INT NOT NULL,
    UpdatedTime DATETIME NOT NULL,
    PageTitle VARCHAR(255) NOT NULL,
    Body TEXT NOT NULL,
	Published BOOLEAN,
    PRIMARY KEY (PageID),
    FOREIGN KEY (UserID) REFERENCES users(UserID)
);

CREATE TABLE blogpoststags (
	BlogPostID INT NOT NULL,    
    TagText VARCHAR(255) NOT NULL,
    PRIMARY KEY (BlogPostID, TagText),
    FOREIGN KEY (BlogPostID) REFERENCES blogposts(BlogPostID),
    FOREIGN KEY (TagText) REFERENCES tags(TagText)
);

 
 
INSERT INTO users (UserName, UserPass, Avatar, UserProfile, Enabled) VALUES
('admin', '$2a$10$7kXfoB1tpsaWPiuQsPONxuIIwRhZqmiusxkaqGpMEHik.7GW5wFKW', null, null,1),
('user', '$2a$10$7kXfoB1tpsaWPiuQsPONxuIIwRhZqmiusxkaqGpMEHik.7GW5wFKW', null, null,1),
('owner', '$2a$10$7kXfoB1tpsaWPiuQsPONxuIIwRhZqmiusxkaqGpMEHik.7GW5wFKW', null, null,1),
('janssenda','$2a$10$7kXfoB1tpsaWPiuQsPONxuIIwRhZqmiusxkaqGpMEHik.7GW5wFKW',null,"ofn.org/users/janssenda",1),
('hlemke91','$2a$10$7kXfoB1tpsaWPiuQsPONxuIIwRhZqmiusxkaqGpMEHik.7GW5wFKW',null,"ofn.org/users/hlemke91",1),
('sethroTull', '$2a$10$7kXfoB1tpsaWPiuQsPONxuIIwRhZqmiusxkaqGpMEHik.7GW5wFKW', 'sethroTullWerewolf.jpg', 'ofn.org/users/sethroTull', 1);

INSERT INTO authorities (UserName, Authority) VALUES
('admin', 'ROLE_ADMIN'),
('admin', 'ROLE_USER'),
('user', 'ROLE_USER'),
('owner', 'ROLE_OWNER'),
('owner', 'ROLE_ADMIN'),
('owner', 'ROLE_USER'),
('sethroTull', 'ROLE_OWNER'),
('sethroTull', 'ROLE_ADMIN'),
('sethroTull', 'ROLE_USER'),
('janssenda', 'ROLE_OWNER'),
('janssenda', 'ROLE_ADMIN'),
('janssenda', 'ROLE_USER'),
('hlemke91', 'ROLE_OWNER'),
('hlemke91', 'ROLE_ADMIN'),
('hlemke91', 'ROLE_USER');

insert into categories (CategoryName, Description) values ("hippie","news on jam bands, prog rock, and acid rock"),
('news', 'news involving musicians and anything music-related'),
('metal', 'heavy metal bands and happenings'),
('blues/jazz', 'blues, R&B, jazz, and all those going-downs'),
('live', 'concerts, festivals, street musicians, buskers, and performances'),
('pop', "MTV, Billboard Hot 100, and what's hot"),
('TV/film', 'upcoming TV and film appearances'),
('oldies but goodies', 'news on oldies, classic rock, etc.'),
('classical', 'news on opera, classical, symphonic, and all music where suit-and-tie is the attire');


insert into tags (TagText) values ("hippie");
insert into tags (TagText) values ("metal");
insert into tags (TagText) values ("Meshuggah");
insert into tags (TagText) values ("todaystomsawyerhegetshighonshrooms");

insert into blogposts (UserID, PostTime, Title, CategoryID, Body, StartDate, EndDate, Published) values (6, now(), "Rush On Shrooms Rules!", 1, "<html>Listen to Rush after taking shrooms! You won't be disappointed! #hippie #todaystomsawyerhegetshighonshrooms</html><br><iframe width='560' height='315' src='https://www.youtube.com/embed/auLBLk4ibAk' frameborder='0' allowfullscreen></iframe>", now(), addtime(now(), '14 0:00:00.00'), true);
insert into blogposts (UserID, PostTime, Title, CategoryID, Body, StartDate, EndDate, Published) values (3, now(), "test blog", 1, "page about metal draft", now(), now(), false);
insert into blogpoststags(BlogPostID, TagText) values (1, "hippie");
insert into blogpoststags(BlogPostID, TagText) values (1, "todaystomsawyerhegetshighonshrooms"); 

insert into staticpages (UserID, UpdatedTime, PageTitle, Body, Published) values (6, now(), "Welcome to One Final Note", "<html>You've parked it at the one-stop source for all things music. News of band formations, breakups, tour schedules, album releases, awards, chartings, shindigs, hoedowns, ragers, ravers, you name it. Every beautiful day and hard day's night, we're blogging about music.<p><h3 style='font:metal;color:red'>Enjoy!</h3><img src='hlemke91andjanssendaandsethrotullcandid.png'/> -Hayden, Danimae, and Seth</html>", true);
insert into staticpages (UserID, UpdatedTime, PageTitle, Body, Published) values (3, now(), "test page", "test page code draft", false);

insert into comments(BlogPostID, UserID, Body, CommentTime, Published) values (1, 6, "Seriously, though, it is quite the... rush. See what I did there?!", now(), true);
insert into comments(BlogPostID, UserID, Body, CommentTime, Published) values (1, 2, "If you don't do it, you're missing out big time.", now(), false);



DROP DATABASE IF EXISTS ofnblogtest;
CREATE DATABASE ofnblogtest;
USE ofnblogtest;

DELIMITER //
CREATE PROCEDURE refreshdata()
BEGIN

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS blogposts;
DROP TABLE IF EXISTS blogpoststags;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS staticpages;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
	UserID INT AUTO_INCREMENT NOT NULL,
    UserName VARCHAR(255) NOT NULL UNIQUE,
    UserPass VARCHAR(500) NOT NULL,
    Avatar VARCHAR(500) NULL,
    UserProfile TEXT NULL,
	Enabled TINYINT(1) DEFAULT 1,
    KEY (UserName),
	PRIMARY KEY(UserID)
);

CREATE TABLE authorities (
	UserName VARCHAR(255) NOT NULL,
    Authority VARCHAR(255) NOT NULL,
    KEY (UserName)
--     FOREIGN KEY (UserName) REFERENCES users(UserName)
);
    
CREATE TABLE tags (
    TagText VARCHAR(255) NOT NULL,
    PRIMARY KEY (TagText)    
);

CREATE TABLE categories (
	CategoryID INT AUTO_INCREMENT NOT NULL,
    CategoryName VARCHAR(255) NOT NULL,
    Description TEXT NULL,
    PRIMARY KEY (CategoryID)
);

CREATE TABLE blogposts (
	BlogPostID INT AUTO_INCREMENT NOT NULL,
    UserID INT NOT NULL,
    PostTime DATETIME NOT NULL,
    Title VARCHAR(255) NOT NULL,
    CategoryID INT NOT NULL,
    Body TEXT NOT NULL,
    StartDate DATETIME,
    EndDate DATETIME,
    Published BOOLEAN,
    PRIMARY KEY(BlogPostID),
    FOREIGN KEY (UserID) REFERENCES users(UserID),
    FOREIGN KEY (CategoryID) REFERENCES categories(CategoryID)
);

CREATE TABLE comments (
	CommentID INT AUTO_INCREMENT NOT NULL,
    BlogPostID INT NOT NULL,
    UserID INT NOT NULL,
    Body TEXT NOT NULL,
    CommentTime DATETIME NOT NULL,
    Published BOOLEAN,
    PRIMARY KEY (CommentID),
    FOREIGN KEY (BlogPostID) REFERENCES blogposts(BlogPostID),
    FOREIGN KEY (UserID) REFERENCES users(UserID)    
);

CREATE TABLE staticpages (
	PageID INT AUTO_INCREMENT NOT NULL,
    UserID INT NOT NULL,
    UpdatedTime DATETIME NOT NULL,
    PageTitle VARCHAR(255) NOT NULL,
    Body TEXT NOT NULL,
	Published BOOLEAN,
    PRIMARY KEY (PageID),
    FOREIGN KEY (UserID) REFERENCES users(UserID)
);

CREATE TABLE blogpoststags (
	BlogPostID INT NOT NULL,    
    TagText VARCHAR(255) NOT NULL,
    PRIMARY KEY (BlogPostID, TagText),
    FOREIGN KEY (BlogPostID) REFERENCES blogposts(BlogPostID),
    FOREIGN KEY (TagText) REFERENCES tags(TagText)
);

insert into users (UserName, UserPass, Avatar, UserProfile, Enabled) values ("sethroTull", "cornwolf", "sethroTullWerewolf.jpg", "ofn.org/users/sethroTull", 1);
insert into categories (CategoryName, Description) values ("hippie","news on jam bands, prog rock, and acid rock");
insert into tags (TagText) values ("hippie");
insert into tags (TagText) values ("metal");
insert into tags (TagText) values ("todaystomsawyerhegetshighonshrooms");
insert into tags (TagText) values ("Meshuggah");
insert into blogposts (UserID, PostTime, Title, CategoryID, Body, StartDate, EndDate, Published) values (1, now(), "Rush On Shrooms Rules!", 1, "<html>Listen to Rush after taking shrooms! You won't be disappointed! #hippie #todaystomsawyerhegetshighonshrooms</html>", now(), addtime(now(), '14 0:00:00.00'), true);
insert into staticpages (UserID, UpdatedTime, PageTitle, Body, Published) values (1, now(), "Welcome to One Final Note", "<html>You've parked it at the one-stop source for all things music. News of band formations, breakups, tour schedules, album releases, awards, chartings, shindigs, hoedowns, ragers, ravers, you name it. Every beautiful day and hard day's night, we're blogging about music.<p><h3 style='font:metal;color:red'>Enjoy!</h3><img src='hlemke91andjanssendaandsethrotullcandid.png'/> -Hayden, Danimae, and Seth</html>", true);
insert into blogposts (UserID, PostTime, Title, CategoryID, Body, StartDate, EndDate, Published) values (1, now(), "test blog", 1, "page about metal draft", now(), now(), false);
insert into staticpages (UserID, UpdatedTime, PageTitle, Body, Published) values (1, now(), "test page", "test page code draft", false);
insert into comments(BlogPostID, UserID, Body, CommentTime, Published) values (1, 1, "Seriously, though, it is quite the... rush. See what I did there?!", now(), true);
insert into comments(BlogPostID, UserID, Body, CommentTime, Published) values (1, 1, "If you don't do it, you're missing out big time.", now(), false);
insert into blogpoststags(BlogPostID, TagText) values (1, "hippie");
insert into blogpoststags(BlogPostID, TagText) values (1, "todaystomsawyerhegetshighonshrooms");

INSERT INTO users (UserName, UserPass, Avatar, UserProfile, Enabled) VALUES
('admin', 'password', null, null,1),
('user', 'password', null, null,1),
('owner', 'password', null, null,1),
('janssenda','password',null,null,1),
('hlemke91','password',null,null,1);

INSERT INTO authorities (UserName, Authority) VALUES
('admin', 'ROLE_ADMIN'),
('admin', 'ROLE_USER'),
('user', 'ROLE_USER'),
('owner', 'ROLE_OWNER'),
('owner', 'ROLE_ADMIN'),
('owner', 'ROLE_USER'),
('sethroTull', 'ROLE_OWNER'),
('sethroTull', 'ROLE_ADMIN'),
('sethroTull', 'ROLE_USER'),
('janssenda', 'ROLE_OWNER'),
('janssenda', 'ROLE_ADMIN'),
('janssenda', 'ROLE_USER'),
('hlemke91', 'ROLE_OWNER'),
('hlemke91', 'ROLE_ADMIN'),
('hlemke91', 'ROLE_USER');

ALTER TABLE `authorities`
 ADD CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`UserName`) REFERENCES `users` (`UserName`);


END //
DELIMITER ;

CALL refreshdata();