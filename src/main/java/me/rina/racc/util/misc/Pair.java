package me.rina.racc.util.misc;

public class Pair <T, S>
{
    T key;
    S value;

    public Pair(T key, S value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public S getValue() {
        return value;
    }

    public void setValue(S value) {
        this.value = value;
    }
}
