package org.example;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Główny Zestaw Testów Symulacji Epidemii")
@SelectClasses({
        CreationalPatternsSuite.class,
        BehavioralAndArchitecturalSuite.class
})
public class MasterTestSuite {
}