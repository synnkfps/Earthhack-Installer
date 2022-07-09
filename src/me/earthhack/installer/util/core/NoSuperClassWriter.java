package me.earthhack.installer.util.core;

import org.objectweb.asm.*;

public class NoSuperClassWriter extends ClassWriter
{
    public NoSuperClassWriter(final int flags) {
        super(flags);
    }

    @Override
    protected String getCommonSuperClass(final String type1, final String type2) {
        if (type1.equals("blr")) {
            return "blk";
        }
        return "java/lang/Object";
    }
}
