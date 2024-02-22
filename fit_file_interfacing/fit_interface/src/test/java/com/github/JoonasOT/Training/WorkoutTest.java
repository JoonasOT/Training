package com.github.JoonasOT.Training;

import com.garmin.fit.*;
import com.garmin.fit.csv.CSVTool;
import com.garmin.fit.plugins.HrToRecordMesgBroadcastPlugin;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

public class WorkoutTest {
    @Test
    void WorkoutHeaderCreationAndConversion() {
        WorkoutHeader wh = new WorkoutHeader("filename");
        wh.getHeader();
    }
    @Test
    void WorkoutStepCreationAndConversion() {
        WorkoutStep tv = new WorkoutStep(0, "Step 0", "notes 0",
                                        Intensity.ACTIVE, WktStepDuration.TIME,
                                        100*60*60, WktStepTarget.POWER, 3);

        WorkoutStep cv = new WorkoutStep(1, "Step 1", "notes 1",
                Intensity.ACTIVE, WktStepDuration.TIME,
                100*60*60, WktStepTarget.POWER, 1125, 1250);

        tv.GetWorkoutStep();
        cv.GetWorkoutStep();
    }
    @Test
    void WorkoutCreationAndSave() {
        String file = "WorkoutTestResult.fit";
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
}
