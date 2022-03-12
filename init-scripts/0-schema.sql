create table message
(
    id      uuid primary key,
    content text        not null,
    created timestamptz not null default now(),
    updated timestamptz not null default now()
);

CREATE OR REPLACE FUNCTION update_updated_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated = now();
    RETURN NEW;
END;

$$ language 'plpgsql';

CREATE TRIGGER update_message_updated
    BEFORE UPDATE
    ON message
    FOR EACH ROW
EXECUTE PROCEDURE update_updated_column();