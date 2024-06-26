INSERT INTO PLANT (PLANT_ID, PLANT_NAME) VALUES (1, '첫번째');
INSERT INTO PLANT (PLANT_ID, PLANT_NAME) VALUES (2, '두번째');

INSERT INTO PLANT_INFO (PLANT_ID, IMAGE, DATE, LIGHT_STATUS, SUNLIGHT_DURATION, GROW_LIGHT_DURATION) VALUES (1, '1.png', '2024-04-01', false, 0, 0);
INSERT INTO PLANT_INFO (PLANT_ID, IMAGE, DATE, LIGHT_STATUS, SUNLIGHT_DURATION, GROW_LIGHT_DURATION) VALUES (2, '2.png', '2024-04-05', false, 0, 0);

INSERT INTO PLANT_THRESHOLD (PLANT_ID, LIGHT_THRESHOLD, SOIL_THRESHOLD, WATER_THRESHOLD, SUN_LIGHT_MAX, WATER_FLAG, SUN_LIGHT_FLAG) VALUES (1, 0.0, 0.0, 0.0, 40, false, false);
INSERT INTO PLANT_THRESHOLD (PLANT_ID, LIGHT_THRESHOLD, SOIL_THRESHOLD, WATER_THRESHOLD, SUN_LIGHT_MAX, WATER_FLAG, SUN_LIGHT_FLAG) VALUES (2, 1.0, 1.0, 1.0, 40, false, false);

INSERT INTO PLANT_WATER (PLANT_ID, WATER_TOGGLE) VALUES (1, false);
INSERT INTO PLANT_WATER (PLANT_ID, WATER_TOGGLE) VALUES (2, false);