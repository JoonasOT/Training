package com.github.JoonasOT.Training;

import com.garmin.fit.*;

import java.util.Date;
import java.util.Random;

public class WorkoutHeader {
    private FileIdMesg header;
    private int steps = 0;
    public WorkoutHeader(String filename) {
        File filetype = File.WORKOUT;
        short manufacturerId = Manufacturer.DEVELOPMENT;
        short productId = 0;
        Random random = new Random();
        int serialNumber = random.nextInt();

        // Every FIT file MUST contain a File ID message
        header = new FileIdMesg();
        header.setType(filetype);
        header.setManufacturer((int) manufacturerId);
        header.setProduct((int) productId);
        header.setTimeCreated(new DateTime(new Date()));
        header.setSerialNumber((long) serialNumber);
    }
    public FileIdMesg getHeader() {
        return header;
    }
}
