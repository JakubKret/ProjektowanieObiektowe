package org.example;

import org.example.model.EntityFactoryTest;
import org.example.model.SimulationConfigTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Zestaw: Wzorce Kreacyjne (Singleton, Factory)")
@SelectClasses({
        SimulationConfigTest.class,
        EntityFactoryTest.class
})
public class CreationalPatternsSuite {
}