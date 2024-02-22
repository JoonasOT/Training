package com.github.JoonasOT.Training;

import com.garmin.fit.Intensity;
import com.garmin.fit.WktStepDuration;
import com.garmin.fit.WktStepTarget;
import com.garmin.fit.WorkoutStepMesg;

public class WorkoutStep {
    private int messageIndex;
    private String name;
    private String notes;
    private Intensity intensity;
    private WktStepDuration durationType;
    private Integer durationValue;
    private WktStepTarget targetType;
    private int targetValue;
    private Integer customTargetValueLow;
    private Integer customTargetValueHigh;

    public WorkoutStep(int messageIndex, String name, String notes, Intensity intensity, WktStepDuration durationType,
                         Integer durationValue, WktStepTarget targetType, int targetValue) {
        this.messageIndex = messageIndex;
        this.name = name;
        this.notes = notes;
        this.intensity = intensity;
        this.durationType = durationType;
        this.durationValue = durationValue;
        this.targetType = targetType;
        this.targetValue = targetValue;
        this.customTargetValueLow = null;
        this.customTargetValueHigh = null;
    }

    public WorkoutStep(int messageIndex, String name, String notes, Intensity intensity, WktStepDuration durationType,
                Integer durationValue, WktStepTarget targetType, int customTargetValueLow, int customTargetValueHigh) {

        this.messageIndex = messageIndex;
        this.name = name;
        this.notes = notes;
        this.intensity = intensity;
        this.durationType = durationType;
        this.durationValue = durationValue;
        this.targetType = targetType;
        this.targetValue = 0;
        this.customTargetValueLow = customTargetValueLow;
        this.customTargetValueHigh = customTargetValueHigh;
    }

    public WorkoutStepMesg GetWorkoutStep() {

        WorkoutStepMesg workoutStepMesg = new WorkoutStepMesg();
        workoutStepMesg.setMessageIndex(messageIndex);

        if (name != null) {
            workoutStepMesg.setWktStepName(name);
        }

        if (notes != null) {
            workoutStepMesg.setNotes(notes);
        }

        if (durationType == WktStepDuration.INVALID) {
            return null;
        }

        workoutStepMesg.setIntensity(intensity);
        workoutStepMesg.setDurationType(durationType);

        if (durationValue != null) {
            workoutStepMesg.setDurationValue((long) durationValue);
        }

        if (targetType != WktStepTarget.INVALID && customTargetValueLow != null && customTargetValueHigh != null) {
            workoutStepMesg.setTargetType(targetType);
            workoutStepMesg.setTargetValue((long) 0);
            workoutStepMesg.setCustomTargetValueLow((long) customTargetValueLow);
            workoutStepMesg.setCustomTargetValueHigh((long) customTargetValueHigh);
        } else if (targetType != WktStepTarget.INVALID) {
            workoutStepMesg.setTargetValue((long) targetValue);
            workoutStepMesg.setTargetType(targetType);
            workoutStepMesg.setCustomTargetValueLow((long) 0);
            workoutStepMesg.setCustomTargetValueHigh((long) 0);
        }

        return workoutStepMesg;
    }
}
