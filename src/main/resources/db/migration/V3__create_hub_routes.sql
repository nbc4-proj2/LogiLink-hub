CREATE TABLE p_hub_routes (
                              route_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              origin_hub_id UUID NOT NULL REFERENCES p_hubs(hub_id),
                              destination_hub_id UUID NOT NULL REFERENCES p_hubs(hub_id),
                              total_distance DOUBLE PRECISION NOT NULL,
                              total_duration INTEGER NOT NULL,
                              created_at TIMESTAMP DEFAULT NOW(),
                              updated_at TIMESTAMP DEFAULT NOW(),
                              created_by BIGINT,
                              updated_by BIGINT,
                              deleted_by BIGINT,
                              deleted_at TIMESTAMP,
                              CONSTRAINT uc_origin_dest_active UNIQUE (origin_hub_id, destination_hub_id, deleted_at)
);
