package com.example.manageapi.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author Keith
 */
@Configuration
public class TeachingModuleConfig {
    public static final int APPLYING_TO_BE_ASSISTANT = 0;
    public static final int ACCEPTED_TO_BE_ASSISTANT = 1;
    public static final int DENIED_TO_BE_ASSISTANT = -1;
    public static final int JOINING_COURSE = 0;
    public static final int ACCEPTED_TO_JOIN_COURSE = 1;
    public static final int DENIED_TO_JOIN_COURSE = -1;

    public static final boolean HAVE_SUBMITTED = false;
    public static final boolean HAVE_CORRECTED = true;
}
