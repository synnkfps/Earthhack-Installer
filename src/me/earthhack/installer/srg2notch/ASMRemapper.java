package me.earthhack.installer.srg2notch;

import org.objectweb.asm.tree.ClassNode;
import me.earthhack.installer.srg2notch.remappers.*;
import me.earthhack.installer.util.core.*;
import org.objectweb.asm.tree.*;

public class ASMRemapper
{
    private final Remapper[] reMappers;

    public ASMRemapper() {
        (this.reMappers = new Remapper[5])[0] = (Remapper)new ClassRemapper();
        this.reMappers[1] = (Remapper)new FieldRemapper();
        this.reMappers[2] = (Remapper)new MethodRemapper();
        this.reMappers[3] = (Remapper)new InstructionRemapper();
        this.reMappers[4] = (Remapper)new AnnotationRemapper();
    }

    public byte[] transform(final byte[] clazz, final Mapping mapping) {
        ClassNode cn;
        try {
            cn = AsmUtil.read(clazz, new int[0]);
        }
        catch (final IllegalArgumentException e) {
            return clazz;
        }
        for (final Remapper remapper : this.reMappers) {
            remapper.remap(cn, mapping);
        }
        return AsmUtil.write(cn, new int[0]);
    }
}
