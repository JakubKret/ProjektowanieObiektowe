package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntityFactoryTest {

    @Test
    void testCreateHuman() {
        Human human = EntityFactory.createHuman(5, 10);
        assertNotNull(human);
        assertTrue(human instanceof Entity);
        assertEquals(5, human.getPosX());
    }

    @Test
    void testCreateAnimals() {
        Animal rat = EntityFactory.createRat(1, 1);
        Animal bat = EntityFactory.createBat(2, 2);
        assertTrue(rat instanceof Rat);
        assertTrue(bat instanceof Bat);
    }
}