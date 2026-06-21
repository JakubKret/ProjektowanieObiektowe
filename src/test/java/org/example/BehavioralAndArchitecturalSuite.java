package org.example;

import org.example.model.BehavioralPatternsTest;
import org.example.model.BuilderTest;
import org.example.observer.ObserverTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Zestaw: Wzorce Czynnościowe i Architektoniczne")
@SelectClasses({
        BehavioralPatternsTest.class,
        BuilderTest.class,
        ObserverTest.class
})
public class BehavioralAndArchitecturalSuite {
}