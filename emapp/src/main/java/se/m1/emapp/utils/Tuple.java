package se.m1.emapp.utils;

/**
 * represents a tuple
 * @param <A>
 * @param <B>
 */
public class Tuple<A, B>
{
    public final A a;
    public final B b;

    public Tuple(A a, B b)
    {
        this.a = a;
        this.b = b;
    }
}
