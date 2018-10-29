package com.clockworkjava.batchrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class LineProcessor implements ItemProcessor<String,String> {

    private static final Logger logger = LoggerFactory.getLogger(LineProcessor.class);

    @Override
    public String process(String line) throws Exception {
        String uppercased = line.toUpperCase();
        logger.info("Input " + line + " changed into " + uppercased);
        return uppercased;
    }
}
