-- name: create-wedding!
-- creates a new user record
INSERT INTO wedding
(id, bridegroom_name, bride_name, hold_date, content)
VALUES (:id, :bridegroom_name, :bride_name, :hold_date, :content, :type)

-- name: update-wedding!
-- update an existing user record
UPDATE wedding
SET bridegroom_name = :bridegroom_name, bride_name = :bride_name, hold_date = :hold_date, content = :content, type = :type
WHERE id = :id

-- name: get-wedding
-- retrieve a user given the id.
SELECT * FROM wedding
WHERE id = :id

-- name: delete-wedding!
-- delete a user given the id
DELETE FROM wedding
WHERE id = :id
