package com.clockworkjava.batchrent;

import org.springframework.batch.item.ItemProcessor;

public class JeepFilter implements ItemProcessor<Car,Car> {

    @Override
    public Car process(Car item) throws Exception {
        if(item.getMake().equalsIgnoreCase("jeep")) {
            return null;
        } else {
            return item;
        }
    }
}
