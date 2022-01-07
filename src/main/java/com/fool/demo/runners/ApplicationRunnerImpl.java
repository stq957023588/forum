package com.fool.demo.runners;

import com.fool.demo.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final AuthorityService authorityService;

    public ApplicationRunnerImpl(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authorityService.initializeDefaultAuthority(false);

        log.debug("Info Test");
        log.info("Info Test");
        log.warn("Warn Test");
        log.error("error Test");
    }
}
