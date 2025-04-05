-- Check extension installation
SELECT * FROM pg_extension WHERE extname = 'uuid-ossp';

-- Check function existence
SELECT proname FROM pg_proc WHERE proname = 'uuid_generate_v4';

-- Check current database
SELECT current_database();
