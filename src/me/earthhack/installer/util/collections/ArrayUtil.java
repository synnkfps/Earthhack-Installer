package me.earthhack.installer.util.collections;

public class ArrayUtil
{
    public static boolean contains(final char ch, final char[] array) {
        for (final char c : array) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }
}
