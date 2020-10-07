package me.rina.racc.client;

/**
 *
 * @author Rina.
 * @since 25/09/2020.
 *
 **/
public class RevenantSetting {
    private String name;
    private String tag;
    private String description;
    private String sliderType;

    private Type type;

    /** Variables setting. **/
    private String  valueString;
    private boolean valueBoolean;
    private double  valueDouble;
    private Enum    valueEnum;

    /** Minimum and maximum **/
    private double minimum;
    private double maximum;

    /**
     * @param details - The name, tag & description.
     * @param value   - Value string.
     **/
    public RevenantSetting(String[] details, String value) {
        this.name        = details[0];
        this.tag         = details[1];
        this.description = details[2];

        this.valueString = value;
    }

    /**
     * @param details - The name, tag & description.
     * @param value   - Value boolean.
     **/
    public RevenantSetting(String[] details, boolean value) {
        this.name        = details[0];
        this.tag         = details[1];
        this.description = details[2];

        this.valueBoolean = value;
    }

    /**
     * @param details  - The name, tag & description.
     * @param value    - Value number can be cast to int, double & float.
     * @param minimum  - Minimum number can be cast to int, double & float.
     * @param maximum  - Maximum number can be cast to int, double & float.
     **/
    public RevenantSetting(String[] details, Number value, Number minimum, Number maximum) {
        this.name        = details[0];
        this.tag         = details[1];
        this.description = details[2];

        if (value instanceof Integer) {
            this.valueDouble = (double) ((int) value);

            this.minimum = (double) ((int) minimum);
            this.maximum = (double) ((int) maximum);

            this.sliderType = "int";
        } else if (value instanceof Double) {
            this.valueDouble = (double) value;

            this.minimum = (double) minimum;
            this.maximum = (double) maximum;

            this.sliderType = "double";
        } else if (value instanceof Float) {
            this.valueDouble = (double) value;

            // Cute.
            this.minimum = (double) ((float) minimum);
            this.maximum = (double) ((float) maximum);

            this.sliderType = "double";
        }
    }

    /**
     * @param details - The name, tag & description.
     * @param value   - Value enum.
     **/
    public RevenantSetting(String[] details, Enum value) {
        this.name        = details[0];
        this.tag         = details[1];
        this.description = details[2];

        this.valueEnum = value;
    }

    /**
     * @param details      - The name, tag & description.
     * @param valueInteger - Value int to work as a bind.
     * @param valueBoolean - Value boolean to work as statement.
     **/
    public RevenantSetting(String[] details, int valueInteger, boolean valueBoolean) {
        this.name        = details[0];
        this.tag         = details[1];
        this.description = details[2];

        // :0 //
        this.valueDouble  = (double) valueInteger;
        this.valueBoolean = valueBoolean;
    }

    public void setString(String string) {
        this.valueString = string;
    }

    public void setBoolean(boolean vBoolean) {
        this.valueBoolean = vBoolean;
    }

    public void setInteger(int integer) {
        this.valueDouble = (int) integer;
    }

    public void setFloat(float vFloat) {
        this.valueDouble = (double) vFloat;
    }

    public void setDouble(double vDouble) {
        this.valueDouble = vDouble;
    }

    public void setBruteEnum(Enum vEnums) {
        this.valueEnum = vEnums;
    }

    public void setEnum(String vEnumName) {
        for (Enum vEnums : this.valueEnum.getClass().getEnumConstants()) {
            if (vEnums.name().equalsIgnoreCase(vEnumName)) {
                this.valueEnum = vEnums;

                break;
            }
        }
    }

    private void setMinium(double minimum) {
        this.minimum = minimum;
    }

    private void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setTag(String tag) {
        this.tag = tag;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getString() {
        return valueString;
    }

    public boolean getBoolean() {
        return valueBoolean;
    }

    public int getInteger() {
        return (int) valueDouble;
    }

    public double getDouble() {
        return valueDouble;
    }

    public float getFloat() {
        return (float) valueDouble;
    }

    public Enum getEnum() {
        return (Enum) valueEnum;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSlider(String typeString) {
        return (boolean) this.sliderType.equals(typeString);
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        SETTING_ENTRY,
        SETTING_BUTTON,
        SETTING_SLIDER,
        SETTING_SELECT_BOX,
        SETTING_BUTTON_MACRO_LISTENER,
        SETTING_BACKEND; // Backend tools.
    }
}