package com.github.JoonasOT.Training;

import org.junit.jupiter.api.Test;

import com.garmin.fit.*;
import com.garmin.fit.csv.CSVTool;


public class WorkoutTest {
    @Test
    void WorkoutHeaderCreationAndConversion() {
        System.out.println("Creating workout header.");
        WorkoutHeader wh = new WorkoutHeader("filename");

        System.out.println("Got header: " + wh.getHeader().toString());
    }
    @Test
    void WorkoutStepCreationAndConversion() {
        System.out.println("Creating workout steps with target value and custom values.");
        WorkoutStep tv = new WorkoutStep(0, "Step 0", "notes 0",
                                        Intensity.ACTIVE, WktStepDuration.TIME,
                                        100*60*60, WktStepTarget.POWER, 3);

        WorkoutStep cv = new WorkoutStep(1, "Step 1", "notes 1",
                Intensity.ACTIVE, WktStepDuration.TIME,
                100*60*60, WktStepTarget.POWER, 1125, 1250);
        System.out.println("Formed the target value step " + tv.GetWorkoutStep().toString() + " and " +
                "completely custom valued step " + cv.GetWorkoutStep().toString());
    }

    @Test
    void BasicWorkoutCreationAndSave() {
        String file = "BasicWorkoutTestResult.fit";
        WorkoutStep tv = new WorkoutStep(0, "Step 0", "notes 0",
                Intensity.ACTIVE, WktStepDuration.TIME,
                100*60*60, WktStepTarget.POWER, 3);

        WorkoutStep cv = new WorkoutStep(1, "Step 1", "notes 1",
                Intensity.ACTIVE, WktStepDuration.TIME,
                100*60*60, WktStepTarget.POWER, 1125, 1250);

        Workout workout = new Workout.WorkoutBuilder(new WorkoutHeader("filename"), "test")
                                     .addStep(tv).addStep(cv).build();
        workout.createFile(file);

        CSVTool.main(new String[]{"-b", file, file.substring(0, file.length() - 4) + ".csv"});
    }

    @Test
    void WorkoutCreationAndSave() {
        String file = "WorkoutTestResult.fit";
        WorkoutStep warmup = new WorkoutStep(0, "Warm-up", null,
                Intensity.WARMUP, WktStepDuration.TIME,
                100*60*10, WktStepTarget.POWER, 1);

        WorkoutStep heavy = new WorkoutStep(1, "Stomp", "As hard as possible!!",
                Intensity.INTERVAL, WktStepDuration.TIME,
                100*20, WktStepTarget.POWER, 2000, 2250);
        WorkoutStep recovery = new WorkoutStep(2, "Recover", null,
                Intensity.RECOVERY, WktStepDuration.TIME,
                100*60*3, WktStepTarget.POWER, 1);
        WorkoutRepeats repeat = new WorkoutRepeats(3, 1, 2, 6);

        WorkoutStep cooldown = new WorkoutStep(4, "Cooldown", null,
                Intensity.RECOVERY, WktStepDuration.TIME,
                100*60*3, WktStepTarget.POWER, 1);

        Workout workout = new Workout.WorkoutBuilder(new WorkoutHeader("filename"), "test")
                .addStep(warmup).addStep(heavy).addStep(recovery).addRepeat(repeat).addStep(cooldown).build();
        workout.createFile(file);

        CSVTool.main(new String[]{"-b", file, file.substring(0, file.length() - 4) + ".csv"});
    }
}
