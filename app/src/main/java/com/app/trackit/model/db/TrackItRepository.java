package com.app.trackit.model.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.app.trackit.model.Exercise;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Set;
import com.app.trackit.model.Workout;
import com.app.trackit.model.db.dao.ExerciseDao;
import com.app.trackit.model.db.dao.WorkoutDao;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TrackItRepository {

    private ExerciseDao exerciseDao;
    private WorkoutDao workoutDao;
    private LiveData<List<Exercise>> exercises;
    private LiveData<List<Workout>> workouts;

    public TrackItRepository(Application application) {
        TrackItDatabase db = TrackItDatabase.getDatabase(application);
        exerciseDao = db.exerciseDao();
        workoutDao = db.workoutDao();
        exercises = exerciseDao.getAllExercises();
        workouts = workoutDao.getAll();
    }

    public LiveData<List<Exercise>> loadAllExercises() {
        return exercises;
    }

    public LiveData<List<Workout>> loadAllWorkouts() {
        return workouts;
    }

    public List<PerformedExercise> getWorkoutExercises(int workoutId) {
        try {
            return exerciseDao.getExercisesFromWorkout(workoutId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Workout getCurrentWorkout() {
        try {
            return workoutDao.getCurrentWorkout().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<PerformedExercise>> getObservableWorkoutExercises(int workoutId) {
        return exerciseDao.getObservableExercisesFromWorkout(workoutId);
    }

    public LiveData<List<Set>> getObservableSetsFromExercise(int exerciseId) {
        return exerciseDao.getObservableSetsFromExercise(exerciseId);
    }

    public List<Set> getSetsFromExercise(int exerciseId) {
        try {
            return exerciseDao.getSetsFromExercise(exerciseId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Exercise getExercise(String name) {
        try {
            return exerciseDao.getExercise(name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PerformedExercise getPerformedExercise(int exerciseId){
        try {
            return exerciseDao.getPerformedExercise(exerciseId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deleteIncompleteWorkout(Workout workout) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            List<PerformedExercise> exercises = null;
            try {
                exercises = exerciseDao.getExercisesFromWorkout(workout.getWorkoutId()).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            for (PerformedExercise ex: exercises) {
                exerciseDao.deleteSetsFromExercise(ex.getPerformedExerciseId());
            }
            exerciseDao.deleteAllExercisesFromWorkout(workout.getWorkoutId());
            workoutDao.delete(workout);
        });
    }

    public Workout getWorkoutFromId(int workoutId) {
        try {
            return workoutDao.getWorkout(workoutId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertWorkout(Workout workout) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.insert(workout);
        });
    }

    public void confirmWorkout(Workout workout) {
        // The "best" way to insert a workout is to create
        // it and add all the exercises. Then after the workout
        // is built, proceed to insert it in the DB.
        // ([Builder] pattern in future maybe)
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            List<PerformedExercise> exercises = getWorkoutExercises(workout.getWorkoutId());
            for (PerformedExercise ex: exercises) {
                List<Set> sets = getSetsFromExercise(ex.getPerformedExerciseId());
                for (Set set: sets) {
                    exerciseDao.updateSet(set);
                }
            }
            workoutDao.confirmWorkout(workout.getWorkoutId());
        });
    }

    public void editWorkout(int workoutId) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.editWorkout(workoutId);
        });
    }

    public void insertExercise(Exercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.insertExercise(exercise);
        });
    }

    public void insertPerformedExercise(PerformedExercise performedExercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.insertPerformedExercise(performedExercise);
        });
    }

    public void deleteWorkout(Workout workout) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.delete(workout);
        });
    }

    public void deleteExercise(Exercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.deleteExercise(exercise);
        });
    }

    public void deletePerformedExercise(PerformedExercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.deleteSetsFromExercise(exercise.getPerformedExerciseId());
            exerciseDao.deletePerformedExercise(exercise);
        });
    }

    public void insertSet(Set set) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.insertSet(set);
        });
    }

    public void deleteSet(Set set) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.deleteSet(set);
        });
    }

    public void updateSet(Set set) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.updateSet(set);
        });
    }

    public Set getSetFromId(int setId) {
        try {
            return exerciseDao.getSetFromId(setId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
