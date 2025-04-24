CREATE TABLE IF NOT EXISTS song (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    duration INT CHECK (duration > 0),
    release_year INT CHECK (release_year >= 1980),
    details TEXT
    );