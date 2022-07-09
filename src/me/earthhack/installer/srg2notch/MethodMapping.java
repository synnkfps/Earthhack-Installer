package me.earthhack.installer.srg2notch;

public class MethodMapping
{
    private final String owner;
    private final String name;
    private final String desc;

    public MethodMapping(final String owner, final String name, final String desc) {
        this.owner = owner;
        this.name = name;
        this.desc = desc;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }
}
