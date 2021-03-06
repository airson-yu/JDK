// CheckStyle: stop header check
// CheckStyle: stop line length check
// GENERATED CONTENT - DO NOT EDIT
// GENERATORS: org.graalvm.compiler.replacements.verifier.VerifierAnnotationProcessor, org.graalvm.compiler.replacements.verifier.PluginGenerator
package org.graalvm.compiler.nodes.java;

import jdk.vm.ci.meta.ResolvedJavaMethod;
import org.graalvm.compiler.serviceprovider.ServiceProvider;

import org.graalvm.compiler.nodes.ValueNode;
import org.graalvm.compiler.nodes.graphbuilderconf.GraphBuilderContext;
import org.graalvm.compiler.nodes.graphbuilderconf.GeneratedInvocationPlugin;
import org.graalvm.compiler.nodes.graphbuilderconf.InvocationPlugin;
import org.graalvm.compiler.nodes.graphbuilderconf.InvocationPlugins;
import org.graalvm.compiler.nodes.graphbuilderconf.NodeIntrinsicPluginFactory;

import jdk.vm.ci.meta.JavaKind;

@ServiceProvider(NodeIntrinsicPluginFactory.class)
public class PluginFactory_NewArrayNode implements NodeIntrinsicPluginFactory {

    //        class: org.graalvm.compiler.nodes.java.NewArrayNode
    //       method: newArray(java.lang.Class<?>,int,boolean)
    // generated-by: org.graalvm.compiler.replacements.verifier.GeneratedNodeIntrinsicPlugin$ConstructorPlugin
    private static final class NewArrayNode_newArray extends GeneratedInvocationPlugin {

        @Override
        public boolean execute(GraphBuilderContext b, ResolvedJavaMethod targetMethod, InvocationPlugin.Receiver receiver, ValueNode[] args) {
            jdk.vm.ci.meta.ResolvedJavaType arg0;
            if (args[0].isConstant()) {
                arg0 = b.getConstantReflection().asJavaType(args[0].asConstant());
            } else {
                return false;
            }
            ValueNode arg1 = args[1];
            boolean arg2;
            if (args[2].isConstant()) {
                arg2 = args[2].asJavaConstant().asInt() != 0;
            } else {
                return false;
            }
            org.graalvm.compiler.nodes.java.NewArrayNode node = new org.graalvm.compiler.nodes.java.NewArrayNode(arg0, arg1, arg2);
            b.addPush(JavaKind.Object, node);
            return true;
        }
    }

    @Override
    public void registerPlugins(InvocationPlugins plugins, InjectionProvider injection) {
        plugins.register(new NewArrayNode_newArray(), org.graalvm.compiler.nodes.java.NewArrayNode.class, "newArray", java.lang.Class.class, int.class, boolean.class);
    }
}
