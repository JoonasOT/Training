package com.github.JoonasOT.Training;

import com.garmin.fit.*;

import java.util.ArrayList;

public class Workout {
    private WorkoutHeader header;
    private WorkoutMesg workoutDescription;
    private ArrayList<WorkoutStep> steps;
    private Workout(WorkoutBuilder builder) {
        header = builder.workoutHeader;
        workoutDescription = builder.workoutDescription;
        steps = builder.workoutSteps;
    }
    public static class WorkoutBuilder {

        // The workout requires a header and description
        private WorkoutHeader workoutHeader;
        private WorkoutMesg workoutDescription;

        // The workout may contain actual data as well
        private ArrayList<WorkoutStep> workoutSteps;

        public WorkoutBuilder(WorkoutHeader header, String workoutName) {
            workoutHeader = header;
            workoutSteps = new ArrayList<>();

            workoutDescription = new WorkoutMesg();
            workoutDescription.setWktName(workoutName);
            workoutDescription.setSport(Sport.CYCLING);
            workoutDescription.setSubSport(SubSport.INVALID);
            workoutDescription.setNumValidSteps(workoutSteps.size());
        }
        public WorkoutBuilder addStep(WorkoutStep step) {
            workoutSteps.add(step);
            workoutDescription.setNumValidSteps(workoutSteps.size());
            return this;
        }
        public WorkoutBuilder addRepeat(WorkoutStep repeat) {
            workoutSteps.add(repeat);
            workoutDescription.setNumValidSteps(workoutSteps.size());
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
