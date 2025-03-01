package cpoo5.layout_evaluator;

import cpoo5.layout_evaluator.model.evaluator.Multipliers;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MultipliersTest {

    @Test
    void testSetValueAndKeyNumber() {
        Multipliers m = Multipliers.HIGH_DISTANCE;
        m.setValue(0.9);
        m.setKeyNumber(8);
        assertEquals(0.9, m.getValue(), 0.0001);
        assertEquals(8, m.getKeyNumber());
    }

    @Test
    void testLoadValuesFromJsonThrowsIfNotFound() {
        assertThrows(Exception.class, () -> Multipliers.loadValuesFromJson("invalid/path.json"));
    }
}