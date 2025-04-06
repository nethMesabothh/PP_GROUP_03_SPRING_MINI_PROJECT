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

SELECT *
FROM app_users
WHERE app_user_id = 'c00edcae-4bb2-466f-8adf-f36b22a6a60b';

SELECT *
FROM habit_logs
WHERE habit_id = '7c3e6849-0433-40f2-b3d0-8f6c96289145'

SELECT a.achievement_id, title, description, badge, xp_required
FROM achievements a
         INNER JOIN app_user_achievements aua ON a.achievement_id = aua.achievement_id


INSERT INTO achievements (achievement_id, title, description, badge, xp_required)
VALUES ('8a5eb583-35d2-4cee-9520-d0ee1694ac9d', 'First Win', 'Awarded for your first win!', 'gold_star.png', 100),
       ('f7c2b4e8-1a3d-4f9e-b4c5-2e9a7c8d1b2c', 'Master Planner', 'Completed 10 tasks in a single day.',
        'planner_badge.png', 500),
       ('d3e9b1a2-5c8f-4a1b-b9c7-3d4e5f6a7b8c', 'Consistency King', 'Logged in for 7 consecutive days.',
        'calendar_badge.png', 300),
       ('a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d', 'Habit Hero', 'Completed 30 habits in a row.', 'habit_badge.png', 1000),
       ('b4e8c2f7-3d1a-4e9f-b5c4-6a7b8c9d0e1f', 'Early Bird', 'Completed a task before 6 AM.', 'morning_badge.png',
        200),
       ('c9d0e1f2-a3b4-4c5d-8e9f-7a6b5c4d3e2f', 'Night Owl', 'Completed a task after midnight.', 'night_badge.png',
        200),
       ('e5f6a7b8-c9d0-4e1f-a2b3-4c5d6e7f8a9b', 'XP Collector', 'Earned 5000 XP in total.', 'xp_collector_badge.png',
        5000),
       ('f6a7b8c9-d0e1-4f2a-b3c4-5d6e7f8a9b0c', 'Task Master', 'Completed 50 tasks in total.', 'task_master_badge.png',
        1500),
       ('a7b8c9d0-e1f2-4a3b-8c9d-0e1f2a3b4c5d', 'Challenge Champion', 'Completed all daily challenges for a week.',
        'challenge_badge.png', 700),
       ('b8c9d0e1-f2a3-4b4c-8d9e-0f1a2b3c4d5e', 'Level Up Legend', 'Reached level 10.', 'level_up_badge.png', 2000);
