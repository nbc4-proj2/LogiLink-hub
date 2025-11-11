CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE p_hubs (
                        hub_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        hub_name VARCHAR(50) NOT NULL,
                        hub_address VARCHAR(255) NOT NULL,
                        latitude DOUBLE PRECISION NOT NULL,
                        longitude DOUBLE PRECISION NOT NULL,
                        created_at TIMESTAMP DEFAULT NOW(),
                        updated_at TIMESTAMP DEFAULT NOW(),
                        created_by BIGINT,
                        updated_by BIGINT,
                        deleted_by BIGINT,
                        deleted_at TIMESTAMP
);
