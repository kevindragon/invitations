
CREATE TABLE user_stash (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  email TEXT UNIQUE,
  password TEXT,
  code TEXT,
  create_at TEXT
);