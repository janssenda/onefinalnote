DROP DATABASE IF EXISTS ofnblogtestnew;
CREATE DATABASE ofnblogtestnew;
USE ofnblogtestnew;

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

DROP DATABASE IF EXISTS ofnblogtest;
CREATE DATABASE ofnblogtest;
USE ofnblogtest;

CREATE TABLE users (
	UserID INT AUTO_INCREMENT NOT NULL,
    UserName VARCHAR(255) NOT NULL UNIQUE,
    UserPass VARCHAR(500) NOT NULL,
    Avatar VARCHAR(500) NULL,
    UserProfile TEXT NULL,
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

insert into users (UserName, UserPass, Avatar, UserProfile) values ("sethroTull", "cornwolf", "sethroTullWerewolf.jpg", "ofn.org/users/sethroTull");


INSERT INTO users (UserName, UserPass, Avatar, UserProfile, Enabled) VALUES
('admin', 'password', null, null,1),
('user', 'password', null, null,1),
('owner', 'password', null, null,1);

INSERT INTO authorities (UserName, Authority) VALUES
('admin', 'ROLE_ADMIN'),
('admin', 'ROLE_USER'),
('user', 'ROLE_USER'),
('owner', 'ROLE_OWNER'),
('owner', 'ROLE_ADMIN'),
('owner', 'ROLE_USER');