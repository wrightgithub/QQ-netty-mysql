########################
# Create user table
########################
CREATE TABLE user_info
(
  user_id         int       NOT NULL ,
  user_password   char(50)  NOT NULL ,
  user_nickname   char(50)  NULL ,

  PRIMARY KEY (user_id)
) ENGINE=InnoDB;

########################
# Create friends table
########################
CREATE TABLE friends_info
(
  fnum              int       NOT NULL AUTO_INCREMENT,
  friend_id         int       NOT NULL ,
  user_id           int       NOT NULL ,
  friend_status     int       NULL ,
  
  PRIMARY KEY (fnum)
) ENGINE=InnoDB;

########################
# Create Login_info table
########################
CREATE TABLE login_info
(
  lnum              int       NOT NULL AUTO_INCREMENT,
  user_ip           char(50)  NULL ,
  user_id           int       NOT NULL ,
  user_status       int       NULL ,
  
  PRIMARY KEY (lnum)
) ENGINE=InnoDB;

ALTER TABLE friends_info ADD CONSTRAINT fk_user_info FOREIGN KEY (user_id) REFERENCES user_info (user_id);
ALTER TABLE friends_info ADD CONSTRAINT fk_friends_info FOREIGN KEY (friend_id) REFERENCES user_info (user_id);
ALTER TABLE login_info ADD CONSTRAINT fk_login_info FOREIGN KEY (user_id) REFERENCES user_info (user_id);

