package com.yn.component;

public class NodeActivityAlias extends ComponentBasic {
    public NodeActivityAlias(String name) {
        super(name);
    }

    public NodeActivityAlias name(String name) {
        attrs.addAttr("android:name", name);
        return this;
    }

    public NodeActivityAlias targetActivity(String targetActivity) {
        attrs.addAttr("android:targetActivity", targetActivity);
        return this;
    }

    public NodeActivityAlias enabled(String enabled) {
        attrs.addAttr("android:enabled", enabled);
        return this;
    }

    public NodeActivityAlias exported(String exported) {
        attrs.addAttr("android:exported", exported);
        return this;
    }

    public NodeActivityAlias icon(String icon) {
        attrs.addAttr("android:icon", icon);
        return this;
    }

    public NodeActivityAlias label(String label) {
        attrs.addAttr("android:label", label);
        return this;
    }

    public NodeActivityAlias permission(String permission) {
        attrs.addAttr("android:permission", permission);
        return this;
    }
}
