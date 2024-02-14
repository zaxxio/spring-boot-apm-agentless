package org.wsd.app.service.impl;

import org.springframework.stereotype.Service;
import org.wsd.app.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    private int count = 0;

    @Override
    public void retryService(int[][] matrix) {
        System.out.println("Start");
        System.out.println("Called " + count++);
        if (Math.random() > 0.5) {
            throw new RuntimeException("Exception");
        }
        System.out.println("End");
        count = 0;
    }

    @Override
    public String recoverProblem(Exception e) {
        return "De";
    }
}
