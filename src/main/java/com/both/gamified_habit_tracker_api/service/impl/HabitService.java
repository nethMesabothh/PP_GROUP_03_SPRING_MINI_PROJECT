package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.repository.HabitRepository;
import com.both.gamified_habit_tracker_api.service.IHabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService implements IHabitService {

    private final HabitRepository habitRepository;

    @Override
    public List<Habit> getAllHabits() {
        return habitRepository.getAllHabits();
    }
}
