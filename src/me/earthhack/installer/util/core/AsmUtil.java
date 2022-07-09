package me.earthhack.installer.util.core;

import org.objectweb.asm.*;
import java.util.*;
import org.objectweb.asm.tree.*;

public class AsmUtil
{
    public static ClassNode read(final byte[] clazz, final int... flags) {
        final ClassNode result = new ClassNode();
        final ClassReader reader = new ClassReader(clazz);
        reader.accept(result, toFlag(flags));
        return result;
    }

    public static byte[] write(final ClassNode classNode, final int... flags) {
        final ClassWriter writer = new ClassWriter(toFlag(flags));
        classNode.accept(writer);
        return writer.toByteArray();
    }

    public static byte[] writeNoSuperClass(final ClassNode classNode, final int... flags) {
        final ClassWriter writer = (ClassWriter)new NoSuperClassWriter(toFlag(flags));
        classNode.accept(writer);
        return writer.toByteArray();
    }

    public static MethodNode findMappedMethod(final ClassNode node, final String notch, final String notchDesc, final String searge, final String mcp, final String srgMcpDesc) {
        MethodNode result = findMethod(node, notch, notchDesc);
        if (result == null) {
            result = findMethod(node, searge, srgMcpDesc);
            if (result == null) {
                return findMethod(node, mcp, srgMcpDesc);
            }
        }
        return result;
    }

    public static MethodNode findMethod(final ClassNode node, final String name, final String description) {
        for (final MethodNode mn : node.methods) {
            if (mn.name.equals(name) && mn.desc.equals(description)) {
                return mn;
            }
        }
        return null;
    }

    public static FieldNode findField(final ClassNode node, final String... names) {
        for (final String name : names) {
            final FieldNode result = findField(node, name);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public static FieldNode findField(final ClassNode node, final String name) {
        for (final FieldNode field : node.fields) {
            if (field.name.equals(name)) {
                return field;
            }
        }
        return null;
    }

    public static int toFlag(final int... flags) {
        int flag = 0;
        for (final int f : flags) {
            flag |= f;
        }
        return flag;
    }
}
