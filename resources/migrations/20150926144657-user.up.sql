CREATE TABLE user (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  email TEXT UNIQUE,
  password TEXT
);

INSERT INTO user (
  email, password
) VALUES (
  "wenlin1988@126.com",
  "f55a3364cef0494342191745b31af6d56f36da8760670309eca5f028abe53c1f"
);