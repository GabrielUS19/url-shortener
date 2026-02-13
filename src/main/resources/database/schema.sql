CREATE TABLE IF NOT EXISTS urls (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    original_url TEXT NOT NULL,
    short_code VARCHAR(10) NOT NULL UNIQUE COLLATE "C",
    created_at TIMESTAMPZ DEFAULT now()
)