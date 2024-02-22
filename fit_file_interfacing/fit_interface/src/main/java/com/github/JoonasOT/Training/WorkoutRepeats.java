package com.github.JoonasOT.Training;

import com.garmin.fit.WktStepDuration;
import com.garmin.fit.WktStepTarget;
import com.garmin.fit.WorkoutStepMesg;

public class WorkoutRepeats {
    private int msgIndex;
    private int repeatFrom;
    private int repeatEnd;
    private int repetitions;
    public WorkoutRepeats(int messageIndex, int repeatFrom, int repeatEnd, int repetitions) {
        msgIndex = messageIndex;
        this.repeatFrom = repeatFrom;
        this.repeatEnd = repeatEnd;
        this.repetitions = repetitions;
    }

    public int getEnd() {
        return repeatEnd;
    }

    public WorkoutStepMesg CreateWorkoutStepRepeat() {
        WorkoutStepMesg workoutStepMesg = new WorkoutStepMesg();
        workoutStepMesg.setMessageIndex((msgIndex));

        workoutStepMesg.setDurationType(WktStepDuration.REPEAT_UNTIL_STEPS_CMPLT);
        workoutStepMesg.setDurationValue((long) repeatFrom);

        workoutStepMesg.setTargetType(WktStepTarget.OPEN);
        workoutStepMesg.setTargetValue((long) repetitions);

        return workoutStepMesg;
    }
}
