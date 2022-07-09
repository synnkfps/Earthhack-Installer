package me.earthhack.installer.util.collections;

import java.util.*;

public class ConvenientStack<E> extends Stack<E>
{
    public ConvenientStack() {
    }

    public ConvenientStack(final Collection<E> collection) {
        this.addAll((Collection<? extends E>)collection);
    }

    @Override
    public synchronized E pop() {
        if (this.empty()) {
            return null;
        }
        return super.pop();
    }

    @Override
    public synchronized E peek() {
        if (this.empty()) {
            return null;
        }
        return super.peek();
    }
}
