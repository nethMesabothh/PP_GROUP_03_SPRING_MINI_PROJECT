-- Check extension installation
SELECT *
FROM pg_extension
WHERE extname = 'uuid-ossp';

-- Check function existence
SELECT proname
FROM pg_proc
WHERE proname = 'uuid_generate_v4';

-- Check current database
SELECT current_database();

SELECT * FROM app_users WHERE app_user_id = 'c00edcae-4bb2-466f-8adf-f36b22a6a60b';

SELECT * FROM habit_logs WHERE habit_id = '7c3e6849-0433-40f2-b3d0-8f6c96289145'