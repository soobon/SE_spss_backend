package com.example.SE_project.utils;

import java.util.*;

public class TransactionManager {
    private final List<TransactionStep> steps = new ArrayList<>();
    private final Stack<TransactionStep> executedSteps = new Stack<>();

    public void addStep(TransactionStep step) {
        steps.add(step);
    }

    public void execStep() {
        try {
            for (TransactionStep step : steps) {
                step.doActions();
                executedSteps.push(step);
            }
        } catch (Exception e){
            rollback();
            throw new RuntimeException(e);
        }
    }

    private void rollback() {
        while (!executedSteps.isEmpty()) {
            TransactionStep step = executedSteps.pop();
            try {
                step.undoActions();
            } catch (Exception e) {
                // Log the exception and continue with the next step
                System.err.println("Failed to undo step: " + e.getMessage());
            }
        }
    }
}
