package fr.vulture.hostocars.model.request;

/**
 * Wrapper for the SQL query arguments.
 */
public class QueryArgument {

    private String name;
    private Object value;
    private int type;

    /**
     * Valued constructor.
     *
     * @param name
     *     The argument name
     * @param value
     *     The argument value
     * @param type
     *     The argument type
     */
    public QueryArgument(String name, Object value, int type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    /**
     * Returns the argument name.
     *
     * @return the argument name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the argument name.
     *
     * @param name
     *     The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the argument value.
     *
     * @return the argument value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the argument value.
     *
     * @param value
     *     The value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Returns the argument type.
     *
     * @return the argument type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the argument type.
     *
     * @param type
     *     The type to set
     */
    public void setType(int type) {
        this.type = type;
    }

}
