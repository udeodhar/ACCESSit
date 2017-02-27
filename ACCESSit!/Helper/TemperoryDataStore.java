/*
 * Decompiled with CFR 0_118.
 */
package Helper;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TemperoryDataStore
implements Serializable {
    private Map<String, Object> map = new HashMap<String, Object>();

    public TemperoryDataStore() {
        System.out.println("Map initialized");
    }

    public Map<String, Object> getMap() {
        return this.map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}

