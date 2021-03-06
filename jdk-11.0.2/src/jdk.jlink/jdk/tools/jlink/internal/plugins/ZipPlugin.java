/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package jdk.tools.jlink.internal.plugins;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.zip.Deflater;
import jdk.tools.jlink.internal.ResourcePoolManager;
import jdk.tools.jlink.internal.ResourcePoolManager.ResourcePoolImpl;
import jdk.tools.jlink.plugin.ResourcePool;
import jdk.tools.jlink.plugin.ResourcePoolBuilder;
import jdk.tools.jlink.plugin.ResourcePoolEntry;
import jdk.tools.jlink.plugin.Plugin;

/**
 *
 * ZIP Compression plugin
 */
public final class ZipPlugin implements Plugin {

    public static final String NAME = "zip";
    private Predicate<String> predicate;

    public ZipPlugin() {

    }

    ZipPlugin(String[] patterns) {
        this(ResourceFilter.includeFilter(Arrays.asList(patterns)));
    }

    ZipPlugin(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Category getType() {
        return Category.COMPRESSOR;
    }

    @Override
    public String getDescription() {
        return PluginsResourceBundle.getDescription(NAME);
    }

    @Override
    public boolean hasArguments() {
        return false;
    }

    @Override
    public String getArgumentsDescription() {
        return PluginsResourceBundle.getArgument(NAME);
    }

    @Override
    public void configure(Map<String, String> config) {
        predicate = ResourceFilter.includeFilter(config.get(NAME));
    }

    static byte[] compress(byte[] bytesIn) {
        Deflater deflater = new Deflater();
        deflater.setInput(bytesIn);
        ByteArrayOutputStream stream = new ByteArrayOutputStream(bytesIn.length);
        byte[] buffer = new byte[1024];

        deflater.finish();
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            stream.write(buffer, 0, count);
        }

        try {
            stream.close();
        } catch (IOException ex) {
            return bytesIn;
        }

        byte[] bytesOut = stream.toByteArray();
        deflater.end();

        return bytesOut;
    }

    @Override
    public ResourcePool transform(ResourcePool in, ResourcePoolBuilder out) {
        in.transformAndCopy((resource) -> {
            ResourcePoolEntry res = resource;
            if (resource.type().equals(ResourcePoolEntry.Type.CLASS_OR_RESOURCE)
                    && predicate.test(resource.path())) {
                byte[] compressed;
                compressed = compress(resource.contentBytes());
                res = ResourcePoolManager.newCompressedResource(resource,
                        ByteBuffer.wrap(compressed), getName(), null,
                        ((ResourcePoolImpl)in).getStringTable(), in.byteOrder());
            }
            return res;
        }, out);

        return out.build();
    }
}
