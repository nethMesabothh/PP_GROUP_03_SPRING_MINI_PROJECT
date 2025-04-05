CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE achievements
(
    achievement_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title          VARCHAR(100) NOT NULL,
    description    VARCHAR(100) NOT NULL,
    badge          VARCHAR(100) NOT NULL,
    xp_required    INT          NOT NULL
);

-- CREATE TABLE app_users
-- (
--     app_user_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
--     username      VARCHAR(100) NOT NULL,
--     email         VARCHAR(100) NOT NULL,
--     password      VARCHAR(100) NOT NULL,
--     level         INT          NOT NULL,
--     xp            INT          NOT NULL,
--     profile_image VARCHAR(100) NOT NULL,
--     is_verified   BOOLEAN,
--     created_at    TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP
-- );

CREATE TABLE habits
(
    habit_id    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    frequency   VARCHAR(100) NOT NULL,
    is_active   BOOLEAN      NOT NULL,
    created_at  TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP,
    app_user_id UUID         NOT NULL,
    FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id)
);

CREATE TABLE habit_logs
(
    habit_log_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    log_date     TIMESTAMPTZ      DEFAULT CURRENT_TIMESTAMP,
    status       VARCHAR(100) NOT NULL,
    xp_earned    INT          NOT NULL,
    habit_id     UUID         NOT NULL,
    FOREIGN KEY (habit_id) REFERENCES habits (habit_id)
);

CREATE TABLE app_user_achievements
(
    app_user_achievement_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    achievement_id          UUID NOT NULL,
    app_user_id             UUID NOT NULL,
    FOREIGN KEY (achievement_id) REFERENCES achievements (achievement_id),
    FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id)
);
