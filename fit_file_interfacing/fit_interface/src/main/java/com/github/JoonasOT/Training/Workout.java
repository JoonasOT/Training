package com.github.JoonasOT.Training;

import com.garmin.fit.FileEncoder;
import com.garmin.fit.Fit;
import com.garmin.fit.FitRuntimeException;

import java.util.ArrayList;

public class Workout {
    private WorkoutHeader header;
    private ArrayList<WorkoutStep> steps;
    private Workout(WorkoutBuilder builder) {
        header = builder.workoutHeader;
        steps = builder.workoutSteps;
    }
    public static class WorkoutBuilder {

        // The workout requires a header
        private WorkoutHeader workoutHeader;

        // The workout may contain actual data as well
        private ArrayList<WorkoutStep> workoutSteps;

        public WorkoutBuilder(WorkoutHeader header) {
            workoutHeader = header;
            workoutSteps = new ArrayList<>();
        }
        public WorkoutBuilder addStep(WorkoutStep step) {
            workoutSteps.add(step);
            workoutHeader.addStep();
            return this;
        }
        public WorkoutBuilder addRepeat(WorkoutStep repeat) {
            workoutSteps.add(repeat);
            workoutHeader.addStep();
            return this;
        }
        public Workout build() {
            return new Workout(this);
        }
    }

    public void createFile(String filename) {
        FileEncoder encode;

        try {
            encode = new FileEncoder(new java.io.File(filename + ".fit"), Fit.ProtocolVersion.V1_0);
        } catch (FitRuntimeException e) {
            System.err.println("Error opening file " + filename);
            e.printStackTrace();
            return;
        }

        // Write the messages to the file, in the proper sequence
        encode.write(header.getHeader());
        encode.write(header.getWorkoutDescription());
    }
}
