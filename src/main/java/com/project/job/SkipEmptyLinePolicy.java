package com.project.job;

import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;

public class SkipEmptyLinePolicy extends DefaultRecordSeparatorPolicy {
    @Override
    public boolean isEndOfRecord(String line) {
        return super.isEndOfRecord(line) && line.trim().length() > 0; // Skip empty and whitespace rows
    }

    @Override
    public String postProcess(String record) {
        return record.trim(); // Remove leading/trailing whitespace
    }
}
