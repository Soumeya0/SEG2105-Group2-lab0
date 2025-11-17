package com.example.group2calculator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Evaluator Class
 * ----------------
 * This class is responsible for evaluating mathematical expressions
 * such as "10 + 5", "20 / 4", "3 * 7", etc.
 *
 * Instead of performing arithmetic manually, we use the Java ScriptEngine.
 * ScriptEngine allows us to evaluate strings as if they were JavaScript
 * expressions, which is a quick and flexible way to compute results.
 *
 * Example Usage:
 *     Evaluator e = new Evaluator();
 *     e.evaluate("10 + 20");     // returns "30"
 *     e.evaluate("15 / 3");      // returns "5"
 *
 * This helps us separate the UI logic from the calculation logic,
 * following good software design principles.
 */
public class Evaluator {

    // This is the ScriptEngine object that will interpret and calculate expressions
    ScriptEngine engine;

    /**
     * Constructor
     * -----------
     * When this class is created, we initialize the ScriptEngine.
     * "rhino" is a JavaScript engine bundled with Java for evaluating expressions.
     */
    public Evaluator() {
        // ScriptEngineManager gives us access to different scripting engines
        engine = new ScriptEngineManager().getEngineByName("rhino");
    }

    /**
     * evaluate()
     * ----------
     * Evaluates a mathematical expression passed as a string.
     *
     * @param expression A string expression such as "10 + 5" or "8 * 3"
     * @return The result as a String
     * @throws ScriptException When the expression is invalid
     *
     * Steps that we assume:
     * 1. Passing the expression to ScriptEngine.
     * 2. ScriptEngine computing the result.
     * 3. Converting result to string and return it.
     *
     * We throw ScriptException to let MainActivity handle errors gracefully.
     */
    public String evaluate(String expression) throws ScriptException {
        // Evaluate the expression using the JavaScript engine
        Object result = engine.eval(expression);

        // Convert the result back to a string before returning
        return result.toString();
    }
}
