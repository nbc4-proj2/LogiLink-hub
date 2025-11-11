INSERT INTO p_hubs (hub_id, hub_name, hub_address, latitude, longitude, created_at, updated_at, created_by, updated_by, deleted_by, deleted_at)
VALUES
    (gen_random_uuid(),'서울', '서울특별시 강남구', 37.5665, 126.9780, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'경기북부', '경기도 의정부시', 37.7380, 127.0330, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'경기남부', '경기도 이천시', 37.2729, 127.4350, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'인천', '인천광역시', 37.4563, 126.7052, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'강원도', '강원특별자치도 춘천시', 37.8813, 127.7298, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'충청북도', '청주시', 36.6424, 127.4890, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'충청남도', '천안시', 36.8151, 127.1139, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'세종', '세종특별자치시', 36.4800, 127.2890, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'대전', '대전광역시', 36.3504, 127.3845, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'전라북도', '전주시', 35.8242, 127.1480, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'전라남도', '순천시', 34.9507, 127.4875, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'광주', '광주광역시', 35.1595, 126.8526, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'경상북도', '안동시', 36.5684, 128.7294, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'경상남도', '창원시', 35.2283, 128.6811, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'대구', '대구광역시', 35.8714, 128.6014, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'울산', '울산광역시', 35.5384, 129.3114, NOW(), NOW(), 0, 0, NULL, NULL),
    (gen_random_uuid(),'부산', '부산광역시', 35.1796, 129.0756, NOW(), NOW(), 0, 0, NULL, NULL);
