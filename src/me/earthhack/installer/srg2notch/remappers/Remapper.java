package me.earthhack.installer.srg2notch.remappers;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.*;
import me.earthhack.installer.srg2notch.*;

public interface Remapper
{
    void remap(final ClassNode p0, final Mapping p1);
}
