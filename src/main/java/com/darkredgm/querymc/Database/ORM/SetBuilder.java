package com.darkredgm.querymc.Database.ORM;

import java.util.HashMap;
import java.util.Map;

public class SetBuilder {

    protected Map<String, Object> values = new HashMap<>();

    public SetBuilder set(String col, Object value ){
        values.put( col, value );
        return this;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public boolean isEmpty()
    {
        return this.values.isEmpty();
    }
}
