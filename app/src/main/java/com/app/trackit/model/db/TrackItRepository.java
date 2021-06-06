package com.app.trackit.model.db;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.app.trackit.model.Exercise;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Photo;
import com.app.trackit.model.Set;
import com.app.trackit.model.Workout;
import com.app.trackit.model.db.dao.ExerciseDao;
import com.app.trackit.model.db.dao.PhotoDao;
import com.app.trackit.model.db.dao.WorkoutDao;
import com.app.trackit.ui.MainActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TrackItRepository {

    private static final String TAG = "TrackItRepository";

    private final ExerciseDao exerciseDao;
    private final WorkoutDao workoutDao;
    private final PhotoDao photoDao;
    private final LiveData<List<Exercise>> exercises;
    private final LiveData<List<Workout>> workouts;
//    private final LiveData<List<Photo>> photos;

    public TrackItRepository(Application application) {
        TrackItDatabase db = TrackItDatabase.getDatabase(application);
        exerciseDao = db.exerciseDao();
        workoutDao = db.workoutDao();
        photoDao = db.photoDao();
        exercises = exerciseDao.getAllExercises();
        workouts = workoutDao.getAll();
//        photos = photoDao.getAllByDate();
    }

    public void addPhoto(Photo photo) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            photoDao.insertPhoto(photo);
        });
    }

    public void deletePhoto(Photo photo) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            photoDao.deletePhoto(photo);
        });
    }

    public Photo getLastPhoto() {
        try {
            return photoDao.getLastPhoto().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Photo>> getAllPhotosByDate() {
        return photoDao.getAllByDate();
    }

    public void updateExercisesDate(int workoutId, Date date) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.updateExercisesDate(workoutId, date);
        });
    }

    public void setConfirmWorkout(int id, boolean confirm) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.confirmWorkout(id, confirm);
        });
    }

    public void setExerciseAsFavorite(int exerciseId, boolean favorite) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.setExerciseAsFavorite(exerciseId, favorite);
        });
    }

    public void updateWorkout(Workout workout) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.updateWorkout(workout);
        });
    }

    public LiveData<List<Exercise>> getFavoriteExercises() {
        return exerciseDao.getFavoritesExercises();
    }

    public boolean isExerciseFavorite(int exerciseId) {
        try {
            return exerciseDao.isFavorite(exerciseId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Exercise getExerciseFromId(int exerciseId) {
        try {
            return exerciseDao.getExerciseFromId(exerciseId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateExercise(Exercise exercise) {
       TrackItDatabase.databaseWriteExecutor.execute(() -> {
           exerciseDao.updateExercise(exercise);
       });
    }

    public List<PerformedExercise> getPerformancesForExercise(int exerciseId) {
        try {
            return exerciseDao.getPerformancesForExercise(exerciseId).get();
        } catch (ExecutionException e) {
            Log.d(TAG, e.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getBestReps(int exerciseId) {
        try {
            return exerciseDao.getBestReps(exerciseId).get();
        } catch (ExecutionException | InterruptedException e) {
            Log.d(TAG, e.toString());
        }
        return 0;
    }

    public Set getBestSetForReps(int performedExerciseId) {
        try {
            return exerciseDao.getBestSetForReps(performedExerciseId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set getBestSetForWeight(int performedExerciseId) {
        try {
            return exerciseDao.getBestSetForWeight(performedExerciseId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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
            return exerciseDao.getExerciseFromName(name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PerformedExercise getPerformedExercise(int performedExerciseId) {
        try {
            return exerciseDao.getPerformedExercise(performedExerciseId).get();
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
            for (PerformedExercise ex : exercises) {
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
        TrackItDatabase.databaseWriteExecutor.execute(() -> workoutDao.insert(workout));
    }

    public void convalidateWorkout(Workout workout) {
        // The "best" way to insert a workout is to create
        // it and add all the exercises. Then after the workout
        // is built, proceed to insert it in the DB.
        // ([Builder] pattern in future maybe)
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            Workout currentWorkout = MainActivity.repo.getCurrentWorkout();
            if (currentWorkout != null) {
                workoutDao.confirmWorkout(currentWorkout.getWorkoutId(), true);
            }
            List<PerformedExercise> exercises = getWorkoutExercises(workout.getWorkoutId());
            for (PerformedExercise ex : exercises) {
                List<Set> sets = getSetsFromExercise(ex.getPerformedExerciseId());
                if (!sets.isEmpty()) {
                    for (Set set : sets) {
                        exerciseDao.updateSet(set);
                    }
                } else {
                    exerciseDao.deletePerformedExercise(ex);
                }
            }
        });
    }

    public void editWorkout(int workoutId) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> workoutDao.editWorkout(workoutId));
    }

    public void insertExercise(Exercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> exerciseDao.insertExercise(exercise));
    }

    public void insertPerformedExercise(PerformedExercise performedExercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> exerciseDao.insertPerformedExercise(performedExercise));
    }

    public void deleteWorkout(Workout workout) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> workoutDao.delete(workout));
    }

    public void deleteExercise(Exercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> exerciseDao.deleteExercise(exercise));
    }

    public void deletePerformedExercise(PerformedExercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.deleteSetsFromExercise(exercise.getPerformedExerciseId());
            exerciseDao.deletePerformedExercise(exercise);
        });
    }

    public void insertSet(Set set) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> exerciseDao.insertSet(set));
    }

    public void deleteSet(Set set) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> exerciseDao.deleteSet(set));
    }

    public void updateSet(Set set) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> exerciseDao.updateSet(set));
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
