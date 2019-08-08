package core;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class Junit4Rerunner extends BlockJUnit4ClassRunner {

    private static final int MAX_TRY_COUNT = 3;

    public Junit4Rerunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        Description description = this.describeChild(method);
        if (method.getAnnotation(Ignore.class) != null) {
            notifier.fireTestIgnored(description);
        } else {
            runTestWithRetries(methodBlock(method), describeChild(method), notifier);
        }

    }

    private void runTestWithRetries(Statement statement, Description description, RunNotifier runNotifier) {
        EachTestNotifier eachTestNotifier = new EachTestNotifier(runNotifier, description);
        Throwable finalError = null;
        eachTestNotifier.fireTestStarted();

        for (int i = 0; i < MAX_TRY_COUNT; i++) {
            try {
                statement.evaluate();
                finalError = null;
                break;
            } catch (AssumptionViolatedException assumptionException) {
                eachTestNotifier.addFailedAssumption(assumptionException);
                break;
            } catch (Throwable throwable) {
                finalError = throwable;
            }
        }

        if (finalError != null) {
            eachTestNotifier.addFailure(finalError);
        }
        eachTestNotifier.fireTestFinished();
    }
}