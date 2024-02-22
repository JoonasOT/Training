package com.github.JoonasOT.Training;

import com.garmin.fit.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Workout {
    private WorkoutHeader header;
    private WorkoutMesg workoutDescription;
    private ArrayList<WorkoutStepMesg> steps;
    private Workout(WorkoutBuilder builder) {
        header = builder.workoutHeader;
        workoutDescription = builder.workoutDescription;
        steps = WorkoutStepMesgFactory(builder.workoutSteps, builder.workoutRepeats);
    }

    private ArrayList<WorkoutStepMesg> WorkoutStepMesgFactory(ArrayList<WorkoutStep> steps,
                                                              ArrayList<WorkoutRepeats> reps) {
        Map<Integer, WorkoutRepeats> repeatLocations = reps.stream()
                                                            .collect(Collectors.toMap(WorkoutRepeats::getEnd, i -> i));
        ArrayList<WorkoutStepMesg> result = new ArrayList<>();
        for(int i = 0; i < steps.size(); i++) {
            result.add(steps.get(i).GetWorkoutStep());
            if (repeatLocations.containsKey(i)) {
                result.add(repeatLocations.get(i).CreateWorkoutStepRepeat());
            }
        }
        return result;
    }

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
        encode.write(workoutDescription);


    }
}
