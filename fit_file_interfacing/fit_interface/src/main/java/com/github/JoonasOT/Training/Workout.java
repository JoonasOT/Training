package com.github.JoonasOT.Training;

import com.garmin.fit.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Workout {
    public static class WorkoutBuilder {

        // The workout requires a header and description
        private WorkoutHeader workoutHeader;
        private WorkoutMesg workoutDescription;

        // The workout may contain actual data as well
        private ArrayList<WorkoutStep> workoutSteps;
        private ArrayList<WorkoutRepeats> workoutRepeats;
        private int size;
        public WorkoutBuilder(WorkoutHeader header, String workoutName) {
            workoutHeader = header;
            workoutSteps = new ArrayList<>();
            workoutRepeats = new ArrayList<>();

            workoutDescription = new WorkoutMesg();
            workoutDescription.setWktName(workoutName);
            workoutDescription.setSport(Sport.CYCLING);
            workoutDescription.setSubSport(SubSport.INVALID);
            workoutDescription.setNumValidSteps(calcSize());
        }
        private int calcSize() {
            return (size = workoutSteps.size() + workoutRepeats.size());
        }
        public WorkoutBuilder addStep(WorkoutStep step) {
            workoutSteps.add(step);
            workoutDescription.setNumValidSteps(calcSize());
            return this;
        }
        public WorkoutBuilder addRepeat(WorkoutRepeats repeat) {
            workoutRepeats.add(repeat);
            workoutDescription.setNumValidSteps(calcSize());
            return this;
        }
        public Workout build() {
            return new Workout(this);
        }
    }

    // Each workout requires a header and a description
    private WorkoutHeader header;
    private WorkoutMesg description;

    // All the different steps and repeats are stored in here
    private ArrayList<WorkoutStepMesg> steps;


    // Private constructor to force use of the builder
    private Workout(WorkoutBuilder builder) {
        header = builder.workoutHeader;
        description = builder.workoutDescription;
        steps = WorkoutStepMesgParse(builder.workoutSteps, builder.workoutRepeats);
    }


    private ArrayList<WorkoutStepMesg> WorkoutStepMesgParse(ArrayList<WorkoutStep> steps,
                                                              ArrayList<WorkoutRepeats> reps) {
        Map<Integer, WorkoutRepeats> repeatLocations = reps.stream()
                                                            .collect(Collectors.toMap(WorkoutRepeats::getEnd, i -> i));
        ArrayList<WorkoutStepMesg> result = new ArrayList<>();
        for(int i : IntStream.range(0, steps.size()).toArray()) {
            result.add(steps.get(i).GetWorkoutStep());
            if (repeatLocations.containsKey(i)) {
                result.add(repeatLocations.get(i).CreateWorkoutStepRepeat());
            }
        }
        return result;
    }

    public boolean createFile(String filename) {
        FileEncoder encode;

        try {
            encode = new FileEncoder(new java.io.File(filename + ".fit"), Fit.ProtocolVersion.V1_0);
        } catch (FitRuntimeException e) {
            System.err.println("Error opening file " + filename + ".fit");
            e.printStackTrace();
            return false;
        }

        // Write the messages to the file, in the proper sequence
        encode.write(header.getHeader());
        encode.write(description);

        for (WorkoutStepMesg step : steps) {
            encode.write(step);
        }

        // Close the output stream
        try {
            encode.close();
        } catch (FitRuntimeException e) {
            System.err.println("Error closing encode.");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public ArrayList<WorkoutStepMesg> getSteps() {
        return steps;
    }

    public WorkoutHeader getHeader() {
        return header;
    }

    public WorkoutMesg getDescription() {
        return description;
    }
}
