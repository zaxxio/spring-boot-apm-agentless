package org.wsd.app.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    @Retryable(retryFor = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000))
    void retryService(int[][] matrix);

    @Recover
    String recoverProblem(Exception e);
}
